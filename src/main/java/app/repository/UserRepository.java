package app.repository;

import app.model.User;
import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
    User show(Long id);
    void save(User myUser);
    void update(User updatedMyUser);
    void delete(Long id);
    User findByName(String name);
}
