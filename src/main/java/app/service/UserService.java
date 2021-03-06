package app.service;
import app.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User show(Long id);
    void save(User user);
    void update(User updatedUser);
    void delete(Long id);
}
