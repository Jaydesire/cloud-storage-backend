package com.cloud.cloudstorage.controllers;

import com.cloud.cloudstorage.models.Error;
import com.cloud.cloudstorage.models.JwtRequest;
import com.cloud.cloudstorage.models.JwtResponse;
import com.cloud.cloudstorage.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtProvider jwtTokenUtil;
    protected final Log logger = LogFactory.getLog(this.getClass());

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) {
        authenticate(jwtRequest.getLogin(), jwtRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtRequest.getLogin());
        final String token = jwtTokenUtil.generateToken(userDetails);
        logger.info("User " + userDetails.getUsername() + " has successfully logged in");
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect login or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        try {
            userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            logger.info("User " + username + " is not authenticated");
            throw new UsernameNotFoundException("User " + username + " is not authenticated");
        }
        logger.info("User " + username + " logout");
        return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Error> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(e.getMessage(), -32001));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(e.getMessage(), -32001));
    }
}
