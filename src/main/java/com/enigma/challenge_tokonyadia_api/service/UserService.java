package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.challenge_tokonyadia_api.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId(String id);
}
