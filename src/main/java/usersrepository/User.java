package usersrepository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigDecimal id;
    private String name;
    private String email;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static User getUserByName(EntityManager entityManager, String name) {
        name = "%" + name + "%";
        //String queryStr = "select u from User u where u.name LIKE ?1";
        // sample exploit: b' OR u.name LIKE '%25m%25
        String queryStr = "SELECT u FROM User u WHERE u.name LIKE '" + name + "'";
        try {
            TypedQuery<User> query = entityManager.createQuery(queryStr, User.class);
            //TypedQuery<User> query = entityManager.createQuery(queryStr, User.class);
            //query.setParameter(1, name);

            List<User> users = query.getResultList();

            if(users.size() > 0) {
                return users.get(0);
            } else {
                return new User();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
