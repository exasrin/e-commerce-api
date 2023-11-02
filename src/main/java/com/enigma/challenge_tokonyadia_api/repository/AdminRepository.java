package com.enigma.challenge_tokonyadia_api.repository;

import com.enigma.challenge_tokonyadia_api.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
}
