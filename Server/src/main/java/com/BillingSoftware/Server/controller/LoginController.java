package com.BillingSoftware.Server.controller;

import com.BillingSoftware.Server.io.AuthRequest;
import com.BillingSoftware.Server.io.AuthResponse;
import com.BillingSoftware.Server.service.AppUserDetailsService;
import com.BillingSoftware.Server.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;
    private final JWTUtil jwtUtil;

    @RequestMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request){
        authenticateUser(request.getEmail(),request.getPass());
        final UserDetails userDetails = appUserDetailsService.loadUserByUserName(request.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        //TODO:fetch the role from repository
        return  new AuthResponse(request.getEmail(),"USER",jwtToken);
    }

    private void authenticateUser(String email, String pass) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,pass));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"authentication fail");
        }
    }

    @PostMapping("/encode")
   public String encode(@RequestBody Map<String , String> request){
       return passwordEncoder.encode(request.get("pass"));
   }

}
