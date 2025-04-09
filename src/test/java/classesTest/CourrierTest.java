package classesTest;

import com.courrierpro.entities.*;
import com.courrierpro.entitiesDTO.*;
import com.courrierpro.repositories.CourrierRepository;
import com.courrierpro.repositories.PieceJointeRepository;
import com.courrierpro.repositories.UserRepository;
import com.courrierpro.services.CourrierService;
import com.courrierpro.services.Impl.CourrierServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourrierTest {

    @Mock
    private CourrierRepository courrierRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PieceJointeRepository pieceJointeRepository;

    @InjectMocks
    private CourrierServiceImpl courrierService;

    private Courrier courrier;
    private User user;
    private PieceJointe pieceJointe;
    private MultipartFile fichier;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialisation de l'objet courrier
        courrier = new Courrier();
        courrier.setIdCourrier(1L);
        courrier.setExpediteur("Expediteur");
        courrier.setDestination("Destination");
        courrier.setObjet("Objet");
        courrier.setValide(false); // Assurez-vous que la validation est à false par défaut
        courrier.setCharge(null);  // Aucun utilisateur affecté au départ

        // Initialisation de l'objet user
        user = new User();
        user.setId(1);
        user.setRole(Role.CHARGE);

        // Initialisation de l'objet PieceJointe
        pieceJointe = new PieceJointe();
        pieceJointe.setId(1L);
        pieceJointe.setNomFichier("file.pdf");

        // Initialisation de l'objet MultipartFile
        fichier = mock(MultipartFile.class);
    }

    // Test de la création d'un courrier entrant
    @Test
    void testCreateCourrierArrivee() {
        CourrierArriveeDTO courrierArriveeDTO = new CourrierArriveeDTO();
        when(courrierRepository.save(any(Courrier.class))).thenReturn(courrier);

        CourrierArriveeDTO result = courrierService.createCourrierArrivee(courrierArriveeDTO);

        assertNotNull(result);
    }

    // Test de la mise à jour d'un courrier entrant
    @Test
    void testUpdateCourrierArrivee() {
        CourrierArriveeDTO courrierArriveeDTO = new CourrierArriveeDTO();
        courrierArriveeDTO.setIdCourrier(1L);

        when(courrierRepository.findById(1L)).thenReturn(Optional.of(courrier));

        CourrierArriveeDTO result = courrierService.updateCourrierArrivee(1L, courrierArriveeDTO);

        assertNotNull(result);
        assertEquals(1L, result.getIdCourrier());
    }

    // Test de la suppression d'un courrier entrant
    @Test
    void testDeleteCourrierArrivee() {
        when(courrierRepository.existsById(1L)).thenReturn(true);

        courrierService.deleteCourrierArrivee(1L);

        verify(courrierRepository, times(1)).deleteById(1L);
    }

    // Test de la création d'un courrier sortant
    @Test
    void testCreateCourrierDepart() {
        CourrierDepartDTO courrierDepartDTO = new CourrierDepartDTO();

        when(courrierRepository.save(any(Courrier.class))).thenReturn(courrier);

        CourrierDepartDTO result = courrierService.createCourrierDepart(courrierDepartDTO);

        assertNotNull(result);
    }

    // Test de la mise à jour d'un courrier sortant
    @Test
    void testUpdateCourrierDepart() {
        CourrierDepartDTO courrierDepartDTO = new CourrierDepartDTO();
        courrierDepartDTO.setIdCourrier(1L);

        when(courrierRepository.findById(1L)).thenReturn(Optional.of(courrier));

        CourrierDepartDTO result = courrierService.updateCourrierDepart(1L, courrierDepartDTO);

        assertNotNull(result);
        assertEquals(1L, result.getIdCourrier());
    }

    // Test de la suppression d'un courrier sortant
    @Test
    void testDeleteCourrierDepart() {
        when(courrierRepository.existsById(1L)).thenReturn(true);

        courrierService.deleteCourrierDepart(1L);

        verify(courrierRepository, times(1)).deleteById(1L);
    }

    // Test de la création d'un courrier de réponse
    @Test
    void testCreateCourrierReponse() {
        CourrierReponseDTO courrierReponseDTO = new CourrierReponseDTO();

        when(courrierRepository.save(any(Courrier.class))).thenReturn(courrier);

        CourrierReponseDTO result = courrierService.createCourrierReponse(courrierReponseDTO);

        assertNotNull(result);
    }

    // Test de la mise à jour d'un courrier de réponse
    @Test
    void testUpdateCourrierReponse() {
        CourrierReponseDTO courrierReponseDTO = new CourrierReponseDTO();
        courrierReponseDTO.setIdCourrier(1L);

        when(courrierRepository.findById(1L)).thenReturn(Optional.of(courrier));

        CourrierReponseDTO result = courrierService.updateCourrierReponse(1L, courrierReponseDTO);

        assertNotNull(result);
        assertEquals(1L, result.getIdCourrier());
    }

    // Test de la suppression d'un courrier de réponse
    @Test
    void testDeleteCourrierReponse() {
        when(courrierRepository.existsById(1L)).thenReturn(true);

        courrierService.deleteCourrierReponse(1L);

        verify(courrierRepository, times(1)).deleteById(1L);
    }

    // Test de l'ajout d'une pièce jointe
    @Test
    void testAjouterPieceJointe() throws IOException {
        when(courrierRepository.findById(1L)).thenReturn(Optional.of(courrier));
        when(fichier.getOriginalFilename()).thenReturn("file.pdf");
        when(fichier.getBytes()).thenReturn(new byte[]{1, 2, 3, 4});

        PieceJointeDTO pieceJointeDTO = courrierService.ajouterPieceJointe(1L, fichier);

        assertNotNull(pieceJointeDTO);
        assertEquals("file.pdf", pieceJointeDTO.getNomFichier());
    }

    // Test du téléchargement de la pièce jointe
    @Test
    void testTelechargerPieceJointe() {
        when(pieceJointeRepository.findById(1L)).thenReturn(Optional.of(pieceJointe));

        byte[] fichier = courrierService.telechargerPieceJointe(1L);

        assertNotNull(fichier);
    }

    // Test de l'affectation d'un courrier
    @Test
    void testAffecterCourrier() {
        when(courrierRepository.findById(1L)).thenReturn(Optional.of(courrier));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        CourrierDTO result = courrierService.affecterCourrier(1L, 1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getChargeId());
    }

    // Test de la validation d'un courrier
    @Test
    void testValiderCourrier() {
        when(courrierRepository.findById(1L)).thenReturn(Optional.of(courrier));

        CourrierDTO result = courrierService.validerCourrier(1L);

        assertNotNull(result);
        assertTrue(result.isValide());
    }

    // Test du rejet d'un courrier
    @Test
    void testRejeterCourrier() {
        when(courrierRepository.findById(1L)).thenReturn(Optional.of(courrier));

        CourrierDTO result = courrierService.rejeterCourrier(1L);

        assertNotNull(result);
        assertFalse(result.isValide());
    }
}
