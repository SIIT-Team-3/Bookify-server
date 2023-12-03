package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.*;

import java.util.Collection;

public interface IUserService {
    public Collection<UserDTO> getAll();
    public UserDetailDTO get(Long userId);
    public Long create(UserRegisteredDTO newUser);
    public UserDetailDTO update(UserDetailDTO updatedUser);
    public boolean changePassword(Long userId, PasswordUpdateDTO newPassword);
    public boolean resetPassword();
    public boolean activateUser(Long userId);
    public boolean login(UserCredentialsDTO userCredentials);
    public boolean deleteUser(Long userId);
    public boolean blockUser(Long userId);
    public Collection<UserDTO> searchUsers(String searchParam);
}