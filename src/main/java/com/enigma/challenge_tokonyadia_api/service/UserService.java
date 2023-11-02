package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.wmb.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId(String id);
}
