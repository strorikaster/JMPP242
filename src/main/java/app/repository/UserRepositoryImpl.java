package app.repository;

import app.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;


    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    public User show(Long id) {
        return entityManager.find(User.class, id);
    }


    public void save(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }


    public void update(User updatedUser) {
          entityManager.merge(updatedUser);
          entityManager.flush();
    }


    public void delete(Long id) {
        User user = show(id);
        entityManager.remove(user);
        entityManager.flush();
    }

    public User findByName(String name) {
       return (User)entityManager.createQuery("SELECT u FROM User u where u.name = :name").setParameter("name", name).getSingleResult();
        //return entityManager.find(User.class, name);
    }
}
