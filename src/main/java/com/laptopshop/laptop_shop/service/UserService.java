package com.laptopshop.laptop_shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.laptopshop.laptop_shop.domain.Role;
import com.laptopshop.laptop_shop.domain.User;
import com.laptopshop.laptop_shop.domain.dto.RegisterDTO;
import com.laptopshop.laptop_shop.repository.RoleRepository;
import com.laptopshop.laptop_shop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User handleSaveUser(User user) {
        User eric = this.userRepository.save(user);
        System.out.println("Save user" + eric);
        return eric;
    }

    public User getUserById(Long id) {
        return this.userRepository.getById(id);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    };

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

}
