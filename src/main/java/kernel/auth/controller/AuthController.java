package kernel.auth.controller;

import com.thesis.thesis.misc.MessageResponse;
import kernel.auth.exception.BadRequestException;
import kernel.auth.exception.OAuth2AuthenticationProcessingException;
import kernel.auth.model.AuthProvider;
import kernel.auth.model.User;
import kernel.auth.model.UserBuilder;
import kernel.auth.payload.ApiResponse;
import kernel.auth.payload.AuthResponse;
import kernel.auth.payload.LoginRequest;
import kernel.auth.payload.RegisterRequest;
import kernel.auth.repository.UserRepository;
import kernel.auth.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenProvider.createToken(authentication);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (OAuth2AuthenticationProcessingException exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Bad credentials!"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email address already in use!");
        }

        User user = new UserBuilder()
                .setName(registerRequest.getName())
                .setEmail(registerRequest.getEmail())
                .setPassword(passwordEncoder.encode(registerRequest.getPassword()))
                .setProvider(AuthProvider.local)
                .createLocalUser();

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }
}
