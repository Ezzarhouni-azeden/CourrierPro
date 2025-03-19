package com.courrierpro.services.Impl;

import com.courrierpro.entities.User;
import com.courrierpro.entitiesDTO.UserDTO;
import com.courrierpro.repositories.UserRepository;
import com.courrierpro.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    // Seul CHEF_SERVICE peut voir tous les utilisateurs
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    public List<UserDTO> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User Authenticated: " + authentication.getName());
        System.out.println("User Roles: " + authentication.getAuthorities());

        List<User> users = utilisateurRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Seul CHEF_SERVICE peut voir un utilisateur par ID
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    public Optional<UserDTO> getUserById(Integer id) {
        Optional<User> user = utilisateurRepository.findById(id);
        return user.map(this::convertToDTO);
    }
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getRole(),
                null  // On ne retourne jamais le mot de passe dans un DTO
        );
    }

    // Seul CHEF_SERVICE peut ajouter un utilisateur
    public UserDTO addUser(UserDTO userDTO) {
        User user = new User();
        user.setLastname(userDTO.getLastname());
        user.setFirstname(userDTO.getFirstname());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        // Hachage du mot de passe
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user = utilisateurRepository.save(user);
        return convertToDTO(user);
    }

    // Seul CHEF_SERVICE peut mettre à jour un utilisateur
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setLastname(userDTO.getLastname());
        user.setFirstname(userDTO.getFirstname());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        return convertToDTO(utilisateurRepository.save(user));
    }

    // Seul CHEF_SERVICE peut supprimer un utilisateur
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    public void deleteUser(Integer id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        utilisateurRepository.deleteById(id);
    }

    // TOUS les utilisateurs peuvent changer leur mot de passe
    @PreAuthorize("isAuthenticated()")
    public boolean changePassword(Integer id, String newPassword) {
        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        User user = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        utilisateurRepository.save(user);
        return true;
    }
}
