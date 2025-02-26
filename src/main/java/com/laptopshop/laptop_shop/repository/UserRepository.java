package com.laptopshop.laptop_shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptopshop.laptop_shop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    User getById(Long id);

    List<User> findAll();

    void deleteById(Long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
