package com.courrierpro.services;

import com.courrierpro.entitiesDTO.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Integer id);
    UserDTO addUser(UserDTO userDTO);
    UserDTO updateUser(Integer id, UserDTO userDTO);
    void deleteUser(Integer id);
    boolean changePassword(Integer id, String newPassword);
}
