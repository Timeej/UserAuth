package rs.ac.singidunum.icr.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.icr.persistence.model.User;
import rs.ac.singidunum.icr.persistence.model.UserAuth;

import javax.persistence.*;
import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("package rs.ac.singidunum.icr.persistence.dao.UserRepository");
    EntityManager entityManager = emf.createEntityManager();*/

    User findByIdEquals(int id);
    User findByEmailEquals(String email);
    User findByEmailAndPasswordEquals (String email, String password);
    User findByEmailAndStatusIdEquals(String email, int statusId);
    @Query(value = "SELECT new User (u.id, u.email, u.password, u.firstName, u.lastName, u.statusId) FROM User u WHERE u.email = :username and u.statusId = :status")
    User getWebshopUserData(
            @Param("username") String username,
            @Param("status") int status
    );

    /*@Transactional
    public void insertUserAuth(int userId, String applicationId, String identity, String accessToken, String refreshToken) {
        entityManager.createNativeQuery("INSERT INTO user_auth(user_id, application_id, identity, identity_creation_time, access_token, access_token_expiration_time, access_token_creation_time, refresh_token)" +
                        "values(:userId, :applicationId, :identity , systimestamp, :accessToken, systimestamp + interval '30' minuta, systimestamp, :refreshToken)")
                .setParameter("userId", userId)
                .set

    }*/
}
