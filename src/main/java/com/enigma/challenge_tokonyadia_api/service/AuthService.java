package com.enigma.wmb.service;

import com.enigma.wmb.dto.request.AuthRequest;
import com.enigma.wmb.dto.response.LoginResponse;
import com.enigma.wmb.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest request);
    LoginResponse login(AuthRequest request);
}
