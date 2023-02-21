package com.kindlebit.pos.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.kindlebit.pos.models.ERole;
import com.kindlebit.pos.models.Role;
import com.kindlebit.pos.models.User;
import com.kindlebit.pos.repository.RoleRepository;
import com.kindlebit.pos.repository.UserRepository;
import com.kindlebit.pos.security.jwt.JwtUtils;
import com.kindlebit.pos.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.kindlebit.pos.payload.request.LoginRequest;
import com.kindlebit.pos.payload.request.SignupRequest;
import com.kindlebit.pos.payload.response.JwtResponse;
import com.kindlebit.pos.payload.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setPhoneNumber(signUpRequest.getPhoneNumber());
    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }





  //Testing for editing API


  @PutMapping("/edit-user")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> editUser(@Valid @RequestBody SignupRequest signUpRequest ,@RequestParam Long userId ) {

    Set<Role> roles = new HashSet<>();
     Optional<User> user = userRepository.findById(userId);

     if(!user.isPresent())
     {
       throw  new RuntimeException(" User not found !! ");
     }

Long id = userId;
String userName = (signUpRequest.getUsername() != null && signUpRequest.getUsername() !=" "? signUpRequest.getUsername(): user.get().getUsername() );

String email = (signUpRequest.getEmail() != null && signUpRequest.getEmail() !=" "? signUpRequest.getEmail(): user.get().getEmail());

String phoneNumber = (signUpRequest.getPhoneNumber() != null && signUpRequest.getPhoneNumber() !=" "? signUpRequest.getPhoneNumber(): user.get().getPhoneNumber());

String password = encoder.encode ((signUpRequest.getPassword() != null && signUpRequest.getPassword() !=" "? signUpRequest.getPassword(): user.get().getPassword()));

if((signUpRequest.getRole() != null && (!signUpRequest.getRole().isEmpty())))
    {
      Set<String> strRoles =signUpRequest.getRole();

      if (strRoles == null) {
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
      } else {
        strRoles.forEach(role -> {
          switch (role) {
            case "admin":
              Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                      .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
              roles.add(adminRole);

              break;
            case "mod":
              Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                      .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
              roles.add(modRole);
              break;
            default:
              Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                      .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
              roles.add(userRole);
          }
        });
      }
    }

User userDb=new User();
userDb.setId(id);
userDb.setUsername(userName);
userDb.setEmail(email);
userDb.setPassword(password);
userDb.setRoles(roles);
userDb.setPhoneNumber(phoneNumber);
userRepository.save(userDb);

return ResponseEntity.ok(new MessageResponse("User has been updated "));
  }








}
