package usersrepository;

import javax.persistence.*;
import java.math.BigDecimal;

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

        //String queryStr = "select id,name, email from user where id = ?1";
        // sample exploit: a' OR 1=1 LIMIT 1 --
        String queryStr = "SELECT id, name, email FROM user WHERE name LIKE '%" + name + "%' LIMIT 1";
        try {
            Query query = entityManager.createNativeQuery(queryStr);
            //query.setParameter(1, rollNo);

            Object[] columns = (Object[]) query.getSingleResult();

            User user = new User();
            user.setId((BigDecimal) columns[0]);
            user.setName((String)columns[1]);
            user.setEmail((String)columns[2]);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
