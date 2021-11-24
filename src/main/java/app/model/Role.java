package app.model;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Objects;

// Этот класс реализует интерфейс GrantedAuthority, в котором необходимо переопределить только один метод getAuthority() (возвращает имя роли).
// Имя роли должно соответствовать шаблону: «ROLE_ИМЯ», например, ROLE_USER.
@Entity
@Table(name = "tab_roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
//@ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//private Set<User> users;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name/*, Set<User> users*/) {
        this.id = id;
        this.name = name;
        //this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return name;
    }

    public void setRole(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
    if (this == o) {
        return true;
    }
    if (o == null || getClass() != o.getClass()) {
        return false;
    }
    Role role = (Role) o;
    return Objects.equals(id, role.id) &&
            Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return name;
    }
}
