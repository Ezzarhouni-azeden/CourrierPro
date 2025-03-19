package com.courrierpro.auth;

import com.courrierpro.config.JwtTokenProvider;
import com.courrierpro.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

private final AuthenticationService service;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;



    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {

  return ResponseEntity.ok(service.register(request));

    }




    @Operation(summary = "Authentification de l'utilisateur", responses = {
            @ApiResponse(responseCode = "200", description = "Authentification réussie", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Identifiants invalides")
    })

    @CrossOrigin(origins = "https://localhost:3000", allowCredentials = "true")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(user);
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(Map.of( "token", token,
                "role", user.getRole().name())
        );
    }


}
