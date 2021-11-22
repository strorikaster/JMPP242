package app.repository;

import app.model.Role;
import app.model.User;

import java.util.List;

public interface RoleRepository {
    List<Role> getAllRoles();
    Role show(Long id);
    void save(Role role);
    void update(Role updatedRole);
    void delete(Long id);
    Role getRoleByName(String name);
}
