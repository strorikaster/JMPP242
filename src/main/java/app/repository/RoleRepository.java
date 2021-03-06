package app.repository;

import app.model.Role;
import app.model.User;

import java.util.List;

public interface RoleRepository {
    List<Role> getAllRoles();
    Role show(int id);
    void save(Role role);
    void update(Role updatedRole);
    void delete(int id);
   // Role getRoleByName(String name);
}
