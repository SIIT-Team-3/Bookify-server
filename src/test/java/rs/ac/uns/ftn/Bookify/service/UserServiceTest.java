package rs.ac.uns.ftn.Bookify.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.uns.ftn.Bookify.enumerations.NotificationType;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.User;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IUserRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private IUserRepository userRepository;

    @Test
    public void getTest(){
        Guest user = new Guest(new HashMap<NotificationType, Boolean>(), new ArrayList<Accommodation>());
        user.setId(1L);
        user.setEmail("guest@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBlocked(false);
        user.setPhone("8452793522");
        user.setDeleted(false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.get(1L);
        assertEquals(user, result);

    }
}
