package app.repository;

import app.model.Role;
import app.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    EntityManager entityManager;


    @SuppressWarnings("unchecked")
    public List<Role> getAllRoles() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }

    public Role show(int id) {
        return entityManager.find(Role.class, id);
    }

    @Transactional
    public void save(Role role) {
        entityManager.persist(role);
        entityManager.flush();
    }

    @Transactional
    public void update(Role updatedRole) {
          entityManager.merge(updatedRole);
          entityManager.flush();
    }

    @Transactional
    public void delete(int id) {
        Role role = show(id);
        entityManager.remove(role);
        entityManager.flush();
    }

    @Override
    public Role getRoleByName(String name) {
        //return entityManager.find(Role.class, name);
        return entityManager.createQuery(
                "SELECT r FROM Role r WHERE r.name LIKE :roleName")
                .setParameter("roleName", name)
                .setMaxResults(1)
                .getResultList();
        }
}
