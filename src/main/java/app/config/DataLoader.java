package app.config;

import app.service.UserService;
import app.model.Role;
import app.model.User;
import app.repository.UserRepository;
import app.service.RoleService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataLoader{

    private UserService userService;
    private RoleService roleService;

    public DataLoader (UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void fillDataBase() {
        Role roleAdmin = new Role();
        roleAdmin.setRole("ADMIN");
        roleService.save(roleAdmin);

        Role roleUser = new Role();
        roleAdmin.setRole("USER");
        roleService.save(roleUser);

        User user1  = new User();
        user1.setName("Alex");
        user1.setPassword("root");
        //user1.setFirstName("Alexey");
        user1.setSurName("Zotov");
        user1.setEmail("zotov@mail.ru");
        user1.setRoles(Set.of(roleService.getRoleByName("ADMIN")));

        userService.save(user1);


        User user2 = new User();
        user2.setName("Ivan");
        user2.setPassword("root");
        //user2.setFirstName("Ivan");
        user2.setSurName("Petrov");
        user2.setEmail("petrov@mail.ru");
        user2.setRoles(Set.of (roleService.getRoleByName("ADMIN"), roleService.getRoleByName("USER")));

        userService.save(user2);

    }
}