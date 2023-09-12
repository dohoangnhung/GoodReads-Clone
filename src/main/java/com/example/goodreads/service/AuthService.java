package com.example.goodreads.service;

import com.example.goodreads.config.JwtService;
import com.example.goodreads.dto.AuthenticationResponse;
import com.example.goodreads.dto.LoginRequest;
import com.example.goodreads.dto.SignupRequest;
import com.example.goodreads.entity.Token;
import com.example.goodreads.entity.User;
import com.example.goodreads.entity.UserRole;
import com.example.goodreads.entity.VerificationToken;
import com.example.goodreads.repository.TokenRepository;
import com.example.goodreads.repository.UserRepository;
import com.example.goodreads.repository.VerificationTokenRepository;
import com.example.goodreads.validator.EmailValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final EmailValidator emailValidator;
    private final MailService mailService;

    public void signup(SignupRequest signupRequest) {
        // check whether the email is valid
        boolean validEmail = emailValidator.test(signupRequest.getEmail());
        if (!validEmail) {
            throw new IllegalStateException("Email not valid");
        }

        // check whether the email is already registered and validated
        Optional<User> userExists = userRepository.findByEmail(signupRequest.getEmail());
        if (userExists.isPresent() && userExists.get().getEnabled()) {
            throw new IllegalStateException("Email already taken");
        } else if (userExists.isPresent()) {
            User userNotEnabled = userExists.get();
            sendVerificationEmail(userNotEnabled);
        } else {
            User user = new User();
            user.setFirstName(signupRequest.getFirstName());
            user.setLastName(signupRequest.getLastName());
            user.setEmail(signupRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setUserRole(UserRole.USER);
            userRepository.save(user);

            sendVerificationEmail(user);
        }
    }

    public void verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Invalid token"));

        // check whether the account is activated already
        if (verificationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Account already activated");
        }
        // check whether the token expired
        LocalDateTime expiresAt = verificationToken.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        String email = verificationToken.getUser().getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User with email " + email + " not found"));
        user.setEnabled(true);
        userRepository.save(user);

        verificationToken.setConfirmedAt(LocalDateTime.now());
        verificationTokenRepository.save(verificationToken);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
                new IllegalStateException("User with email " + loginRequest.getEmail() + " not found"));

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // revoke and expire all the existing jwt tokens
        revokeAllUserJwtTokens(user);
        saveUserJwtToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                // create and save a new access token
                var accessToken = jwtService.generateToken(user);
                revokeAllUserJwtTokens(user);
                saveUserJwtToken(user, accessToken);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("No user found with email " + email));
    }

    private void revokeAllUserJwtTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void sendVerificationEmail(User user) {
        String token = generateVerificationToken(user);
        String verifyLink = "http://localhost:8080/api/auth/verify-account/" + token;
        String body = mailService.buildEmailContent(user.getFirstName(), verifyLink);
        mailService.sendMail(
                "Activate Your Goodreads Account",
                user.getEmail(),
                body);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken confirmationToken = new VerificationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        verificationTokenRepository.save(confirmationToken);
        return token;
    }

    private void saveUserJwtToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
