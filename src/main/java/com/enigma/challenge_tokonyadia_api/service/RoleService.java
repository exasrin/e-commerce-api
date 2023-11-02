package com.enigma.challenge_tokonyadia_api.service;

import com.enigma.wmb.entity.Role;

public interface RoleService {
    Role getOrSave(Role role);
}
