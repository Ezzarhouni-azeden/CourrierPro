package com.courrierpro.entitiesDTO;

import com.courrierpro.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;  // Enum Role (CHEF_SERVICE, EMPLOYE)
    private String password; // Seulement utilisé lors de la création ou modification
}
