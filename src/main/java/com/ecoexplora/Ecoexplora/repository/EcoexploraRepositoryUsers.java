package com.ecoexplora.Ecoexplora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecoexplora.Ecoexplora.model.Users;

public interface EcoexploraRepositoryUsers extends JpaRepository<Users,Integer> {
    Optional<Users> findByUser(String user);
    boolean existsByUser(String user);
}
