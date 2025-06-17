package com.projet.localed.repositories;

public interface UserRepository extends JpaRepository{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
