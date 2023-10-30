package com.enigma.challenge_tokonyadia_api.repository;

import com.enigma.challenge_tokonyadia_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
