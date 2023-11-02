package com.enigma.wmb.service.impl;

import com.enigma.wmb.constant.ERole;
import com.enigma.wmb.dto.request.AuthRequest;
import com.enigma.wmb.dto.response.LoginResponse;
import com.enigma.wmb.dto.response.RegisterResponse;
import com.enigma.wmb.entity.AppUser;
import com.enigma.wmb.entity.Customer;
import com.enigma.wmb.entity.Role;
import com.enigma.wmb.entity.UserCredential;
import com.enigma.wmb.repository.UserCredentialRepository;
import com.enigma.wmb.security.JwtUtil;
import com.enigma.wmb.service.AuthService;
import com.enigma.wmb.service.CustomerService;
import com.enigma.wmb.service.RoleService;
import com.enigma.wmb.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserCredentialRepository userCredentialRepository, PasswordEncoder passwordEncoder, CustomerService customerService, RoleService roleService, JwtUtil jwtUtil, ValidationUtil validationUtil, AuthenticationManager authenticationManager) {
        this.userCredentialRepository = userCredentialRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
        this.validationUtil = validationUtil;
        this.authenticationManager = authenticationManager;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        try {
            validationUtil.validate(request);
            // role
            Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_CUSTOMER).build());

            // user credential
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            // customer
            Customer customer = Customer.builder()
                    .userCredential(userCredential)
                    .build();
            customerService.createNew(customer);

            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .role(userCredential.getRole().getName().toString())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        }
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        validationUtil.validate(request);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername().toLowerCase(),
                request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // object AppUser
        AppUser appUser = (AppUser) authenticate.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }
}
