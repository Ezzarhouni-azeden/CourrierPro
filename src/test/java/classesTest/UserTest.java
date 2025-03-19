package classesTest;

import com.courrierpro.entities.Role;
import com.courrierpro.entities.User;
import com.courrierpro.entitiesDTO.UserDTO;
import com.courrierpro.repositories.UserRepository;
import com.courrierpro.services.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.lenient;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)class UserTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock du SecurityContext et Authentication
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        // Stub du SecurityContext pour éviter NullPointerException
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getName()).thenReturn("CHEF_SERVICE");

        // Autorités simulées
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("CHEF_SERVICE"));
        doReturn(authorities).when(authentication).getAuthorities();
        // Appliquer le contexte de sécurité mocké
        SecurityContextHolder.setContext(securityContext);

        // Initialisation des utilisateurs
        user = new User();
        user.setId(1);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("hashedPassword");
        user.setRole(Role.CHEF_SERVICE);

        userDTO = new UserDTO(1, "John", "Doe", "john.doe@example.com", Role.CHEF_SERVICE, "password123");
    }


    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserDTO> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John", users.get(0).getFirstname());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<UserDTO> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstname());
    }

    @Test
    void testAddUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO savedUser = userService.addUser(userDTO);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstname());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO updatedUser = new UserDTO(1, "John", "Doe", "updated@example.com", Role.CHEF_SERVICE, null);
        UserDTO result = userService.updateUser(1, updatedUser);

        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1);

        assertDoesNotThrow(() -> userService.deleteUser(1));
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testChangePassword() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("newHashedPassword");

        boolean result = userService.changePassword(1, "newPassword123");

        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }
}
