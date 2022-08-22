package rs.ac.singidunum.icr.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.singidunum.icr.persistence.model.UserAuth;

import java.sql.Timestamp;

public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {

    @Query(value = "SELECT ua.identity, ua.identityExpirationTime, ua.identityCreationTime, ua.accessToken, ua.accessTokenExpirationTime, ua.accessTokenCreationTime, ua.refreshToken from UserAuth ua where ua.user.id = :userId and ua.applicationId = :applicationId")
    UserAuth getTokensByUserIdAndApplicationId(
            @Param("userId") int userId,
            @Param("applicationId") String applicationId
    );


    /*@Query(value = "SELECT ua.identity, ua.identityExpirationTime, ua.identityCreationTime, ua.accessToken, ua.accessTokenExpirationTime, ua.accessTokenCreationTime, ua.refreshToken from UserAuth ua where ua.refreshToken = :refreshToken")
    UserAuth getUserTokenBy*/

    UserAuth findByRefreshTokenEquals(String refreshToken);

    @Modifying
    @Query("UPDATE UserAuth ua SET ua.identity = :identity, ua.identityCreationTime = current_timestamp, ua.accessToken = :accessToken, ua.accessTokenExpirationTime = :accessTokenExpirationTime, ua.accessTokenCreationTime = current_timestamp where ua.user.id = :userId and ua.applicationId = :applicationId")
    void updateTokensByUserAndApplicationId(
            @Param("accessTokenExpirationTime") Timestamp accessTokenExpirationTime,
            @Param("userId") int userId,
            @Param("applicationId") String applicationId
            );

    @Modifying
    @Query("UPDATE UserAuth ua SET ua.identity = :identity, ua.identityCreationTime = current_timestamp, ua.accessToken = :accessToken, ua.accessTokenExpirationTime = :accessTokenExpirationTime, ua.accessTokenCreationTime = current_timestamp where ua.refreshToken = :refreshToken")
    void updateTokensByRefreshToken(
            @Param("accessTokenExpirationTime") Timestamp accessTokenExpirationTime,
            @Param("refreshToken") String refreshToken
    );
}
