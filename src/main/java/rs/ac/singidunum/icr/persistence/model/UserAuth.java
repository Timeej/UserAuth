package rs.ac.singidunum.icr.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_auth")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"tokens"})
    private User user;

    @Column(name = "application_id")
    private String applicationId;

    private String identity;

    @Column(name = "identity_expiration_time")
    private Timestamp identityExpirationTime;

    @Column(name = "identity_creation_time")
    private Timestamp identityCreationTime;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "access_token_expiration_time")
    private Timestamp accessTokenExpirationTime;

    @Column(name = "access_token_creation_time")
    private Timestamp accessTokenCreationTime;

    @Column(name = "refresh_token")
    private String refreshToken;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Timestamp getIdentityExpirationTime() {
        return identityExpirationTime;
    }

    public void setIdentityExpirationTime(Timestamp identityExpirationTime) {
        this.identityExpirationTime = identityExpirationTime;
    }

    public Timestamp getIdentityCreationTime() {
        return identityCreationTime;
    }

    public void setIdentityCreationTime(Timestamp identityCreationTime) {
        this.identityCreationTime = identityCreationTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Timestamp getAccessTokenExpirationTime() {
        return accessTokenExpirationTime;
    }

    public void setAccessTokenExpirationTime(Timestamp accessTokenExpirationTime) {
        this.accessTokenExpirationTime = accessTokenExpirationTime;
    }

    public Timestamp getAccessTokenCreationTime() {
        return accessTokenCreationTime;
    }

    public void setAccessTokenCreationTime(Timestamp accessTokenCreationTime) {
        this.accessTokenCreationTime = accessTokenCreationTime;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserAuth() {};

    public UserAuth(User user, String applicationId, String identity, Timestamp identityCreationTime, String accessToken, Timestamp accessTokenExpirationTime, Timestamp accessTokenCreationTime, String refreshToken) {
        this.setUser(user);
        this.setApplicationId(applicationId);
        this.setIdentity(identity);
        this.setIdentityCreationTime(identityCreationTime);
        this.setAccessToken(accessToken);
        this.setAccessTokenExpirationTime(accessTokenExpirationTime);
        this.setAccessTokenCreationTime(accessTokenCreationTime);
        this.setRefreshToken(refreshToken);
    }

    public UserAuth(int id, User user, String applicationId, String identity, Timestamp identityCreationTime, String accessToken, Timestamp accessTokenExpirationTime, Timestamp accessTokenCreationTime, String refreshToken) {
        this.setId(id);
        this.setUser(user);
        this.setApplicationId(applicationId);
        this.setIdentity(identity);
        this.setIdentityCreationTime(identityCreationTime);
        this.setAccessToken(accessToken);
        this.setAccessTokenExpirationTime(accessTokenExpirationTime);
        this.setAccessTokenCreationTime(accessTokenCreationTime);
        this.setRefreshToken(refreshToken);
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "id=" + id +
                ", user=" + user +
                ", applicationId=" + applicationId +
                ", identity='" + identity + '\'' +
                ", identityExpirationTime=" + identityExpirationTime +
                ", identityCreationTime=" + identityCreationTime +
                ", accessToken='" + accessToken + '\'' +
                ", accessTokenExpirationTime=" + accessTokenExpirationTime +
                ", accessTokenCreationTime=" + accessTokenCreationTime +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
