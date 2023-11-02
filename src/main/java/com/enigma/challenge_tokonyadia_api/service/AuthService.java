package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.dto.request.AuthRequest;
import com.enigma.challenge_tokonyadia_api.dto.response.LoginResponse;
import com.enigma.challenge_tokonyadia_api.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest request);
    LoginResponse login(AuthRequest request);
}
