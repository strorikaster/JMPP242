package app.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;


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
//    @ManyToMany(mappedBy = "roles")//(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
//    private Set<User> users;
@ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
private Set<User> users;

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

//    @Override
//    public String toString() {
//        return this.name;
//    }
//
//    public int hashCode() {
//        final int prime =  31;
//        int result = 1;
//
//        result = prime * result + (((id) == null) ? 0 : id.hashCode());
//        return result;
//    }
//
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        Role other = (Role) obj;
//        if (id == null) {
//            if (other.id != null) {
//                return false;
//            } else if (!id.equals(other.id)) {
//                return false;
//            }
//        }
//        return true;
//    }
}
