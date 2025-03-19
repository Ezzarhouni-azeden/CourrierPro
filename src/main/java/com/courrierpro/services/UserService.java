package com.courrierpro.services;

import com.courrierpro.entities.User;
import com.courrierpro.entitiesDTO.UserDTO;
import com.courrierpro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilisateurService {

    @Autowired
    private UserRepository utilisateurRepository;


    // Méthode pour convertir Utilisateur en UtilisateurDTO
    private UserDTO convertToDTO(User utilisateur) {
        return new UserDTO(utilisateur.getId(), utilisateur.getLastname(), utilisateur.getFirstname(), utilisateur.getEmail(), utilisateur.getPassword(), utilisateur.getRole());
    }

    // Méthode pour récupérer tous les utilisateurs
    public List<UserDTO> getAllUtilisateurs() {
        List<User> utilisateurs = utilisateurRepository.findAll();
        return utilisateurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Méthode pour récupérer un utilisateur par ID
    public Optional<UserDTO> getUtilisateurById(Integer id) {
        Optional<User> utilisateur = utilisateurRepository.findById(id);
        return utilisateur.map(this::convertToDTO);
    }

    // Méthode pour ajouter un nouvel utilisateur
    public UserDTO addUtilisateur(UserDTO utilisateurDTO) {
        User utilisateur = new User();
        utilisateur.setLastname(utilisateurDTO.getNom());
        utilisateur.setFirstname(utilisateurDTO.getPrenom());
        utilisateur.setEmail(utilisateurDTO.getEmail());
        utilisateur.setRole(utilisateurDTO.getRole());

        // Hachage du mot de passe avant de l'enregistrer
        String motDePasseHash = utilisateurDTO.getMotDePasse();
        utilisateur.setPassword(motDePasseHash);

        utilisateur = utilisateurRepository.save(utilisateur);
        return convertToDTO(utilisateur);
    }

    // Méthode pour mettre à jour un utilisateur
    public UserDTO updateUtilisateur(Integer id, UserDTO utilisateurDTO) {
        Optional<User> existingUtilisateur = utilisateurRepository.findById(id);
        if (existingUtilisateur.isPresent()) {
            User utilisateur = existingUtilisateur.get();
            utilisateur.setLastname(utilisateurDTO.getNom());
            utilisateur.setFirstname(utilisateurDTO.getPrenom());
            utilisateur.setEmail(utilisateurDTO.getEmail());
            utilisateur.setRole(utilisateurDTO.getRole());

            utilisateur = utilisateurRepository.save(utilisateur);
            return convertToDTO(utilisateur);
        }
        return null;
    }

    // Méthode pour supprimer un utilisateur
    public void deleteUtilisateur(Integer id) {
        utilisateurRepository.deleteById(id);
    }

    // Méthode pour changer le mot de passe d'un utilisateur
    public boolean changePassword(Integer id, String nouveauMotDePasse) {
        Optional<User> utilisateurOptional = utilisateurRepository.findById(id);
        if (utilisateurOptional.isPresent()) {
            User utilisateur = utilisateurOptional.get();

            // Hachage du nouveau mot de passe

            utilisateur.setPassword(nouveauMotDePasse); // Mise à jour du mot de passe haché
            utilisateurRepository.save(utilisateur);
            return true;
        }
        return false;  // Utilisateur non trouvé
    }
}
