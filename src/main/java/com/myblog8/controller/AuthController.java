package com.myblog8.controller;

import com.myblog8.entity.Role;
import com.myblog8.entity.User;
import com.myblog8.payload.LoginDto;
import com.myblog8.payload.SignUpDto;
import com.myblog8.repository.RoleRepository;
import com.myblog8.repository.UserRepository;
import com.myblog8.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

//signinimplementation.
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;//as we have created bean in confif classwe can write in autowired

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepository;
   @Autowired
   private AuthenticationManager authenticationManager;
    // http://localhost:8080/api/auth/signing
    @Autowired
    private JwtTokenProvider tokenProvider;
    @PostMapping("/signing")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);//this whole method validates username and password
// get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    // http://localhost:8080/api/auth/signup

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

        Boolean emailExist = userRepo.existsByEmail(signUpDto.getEmail());
        if(emailExist){
            return new ResponseEntity<>("Email id Exists",HttpStatus.BAD_REQUEST);
        }
        Boolean emailUsername = userRepo.existsByUsername(signUpDto.getUsername());
        if(emailUsername){
            return new ResponseEntity<>("username already exists",HttpStatus.BAD_REQUEST);
        }
        User user =new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
       user.setUsername(signUpDto.getUsername());
       user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

       //by default for all signup it create ADMIN Role
       Role roles =roleRepository.findByName("ROLE_ADMIN").get();
        Set<Role> role= new HashSet<>();
        role.add(roles);
        user.setRoles(role);
        User save = userRepo.save(user);

return new ResponseEntity<>("User is registered", HttpStatus.CREATED);
    }

}