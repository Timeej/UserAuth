package rs.ac.singidunum.icr.service;

import rs.ac.singidunum.icr.persistence.model.User;
import rs.ac.singidunum.icr.persistence.model.UserAuth;

import java.sql.Time;
import java.sql.Timestamp;

public interface IUserService {

    Boolean verifyHashedPassword (String hash, String clearPassword);

    String hashClearPassword (String clearPassword);

    String SHA256HexEncryption (String clearInput);

    User loginUser(String username, String password);

    void insertUserAuth (int userId, String applicationId);

    User registerUser(String username, String password, String firstName, String lastName, String mobileNumber);

    /*UserAuth authUser(String username, String password, String applicationId, String refreshToken, String grantType);*/
}
