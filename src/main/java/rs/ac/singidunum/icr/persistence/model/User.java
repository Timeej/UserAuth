package rs.ac.singidunum.icr.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*@JsonInclude(value = JsonInclude.Include.NON_NULL)*/
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "status_id")
    private int statusId;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserStatus userStatus;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @JsonIgnoreProperties({"user"})
    private List<UserAuth> tokens = new ArrayList<>();

    public User() {

    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public User(String email, String password, String firstName, String lastName, int statusId, String mobileNumber) {
        this.setEmail(email);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setStatusId(statusId);
        this.setMobileNumber(mobileNumber);
    }

    public User(int id, String email, String password, String firstName, String lastName, int statusId) {
        this.setId(id);
        this.setEmail(email);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setStatusId(statusId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    /*public String getUserStatus() {
        return userStatus.getStatus();
    }*/

    public UserStatus getUserStatus() {
        return userStatus;
    }

    /*public void setUserStatus(String status) {
        userStatus.setStatus(status);
    }*/

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userStatus='" + userStatus + '\'' +
                /*", status='" + this.getUserStatus() + '\'' +
                ", statusId='" + statusId + '\'' +*/
                '}';
    }
}
