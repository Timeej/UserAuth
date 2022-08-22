package rs.ac.singidunum.icr.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "user_status")
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String status;

    public UserStatus() {}

    public UserStatus(String status) {
        this.status = status;
    }

    public UserStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
