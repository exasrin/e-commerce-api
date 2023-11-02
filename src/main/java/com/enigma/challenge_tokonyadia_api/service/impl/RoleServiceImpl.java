package com.enigma.challenge_tokonyadia_api.service.impl;

import com.enigma.wmb.entity.Role;
import com.enigma.wmb.repository.RoleRepository;
import com.enigma.wmb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getOrSave(Role role) {
        // jika ada kita get dari DB
        Optional<Role> optionalRole = roleRepository.findByName(role.getName());
        if (!optionalRole.isEmpty()) {
            return optionalRole.get();
        }
        // jika tidak ada kita create baru
        return roleRepository.save(role);
    }
}
