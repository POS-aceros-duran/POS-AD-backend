package com.aceros_duran.pos.authenticationProvider.repositories;

import com.aceros_duran.pos.authenticationProvider.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUsername(String username);
}
