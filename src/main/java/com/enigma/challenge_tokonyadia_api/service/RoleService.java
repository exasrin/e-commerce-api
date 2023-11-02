package com.enigma.challenge_tokonyadia_api.service;


import com.enigma.challenge_tokonyadia_api.entity.Role;

public interface RoleService {
    Role getOrSave(Role role);
}
