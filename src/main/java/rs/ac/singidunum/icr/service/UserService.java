package rs.ac.singidunum.icr.service;

import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.icr.persistence.dao.UserAuthRepository;
import rs.ac.singidunum.icr.persistence.dao.UserRepository;
import rs.ac.singidunum.icr.persistence.model.User;
import rs.ac.singidunum.icr.persistence.model.UserAuth;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public Boolean verifyHashedPassword (String hash, String clearPassword) {

        int iterationsCount = 32000;
        int derivedKeyLength = 192;
        Boolean resultOut = false;
        try
        {
            String[] parts = hash.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            PBEKeySpec spec = new PBEKeySpec(clearPassword.toCharArray(), salt, iterationsCount, derivedKeyLength);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] result = f.generateSecret(spec).getEncoded();
            resultOut = parts[1].equals(Base64.getEncoder().encodeToString(result));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
        {

        }
        return resultOut;
    }

    @Override
    public String hashClearPassword (String clearPassword) {
        int iterationsCount = 32000;
        int derivedKeyLength = 192;
        int saltLength = 24;
        String resultOut = "";
        try
        {
            byte[] salt = new byte[saltLength];
            new SecureRandom().nextBytes(salt);
            PBEKeySpec spec = new PBEKeySpec(clearPassword.toCharArray(), salt, iterationsCount, derivedKeyLength);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] result = f.generateSecret(spec).getEncoded();
            resultOut = Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
        {

        }
        return resultOut;
    }

    @Override
    public String SHA256HexEncryption(String clearInput) {

        String hashed = "";
        String prefix = "56fdFG45";
        String postfix = "6sSD67S45AD";
		if(null != clearInput){
			 try {
			        MessageDigest digest = MessageDigest.getInstance("SHA-256");
			        final String rawPass = prefix+""+clearInput+""+postfix;
			        byte[] hash = digest.digest(rawPass.getBytes("UTF-8"));
			        StringBuffer buffer = new StringBuffer();

			        for(int i=0; i<hash.length; i++){
			        String hex = Integer.toHexString(0xff & hash[i]);
			        if(hex.length() == 1) buffer.append('0');
                    buffer.append(hex);
			 }

			        hashed = buffer.toString();
             } catch (NoSuchAlgorithmException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             } catch (UnsupportedEncodingException e) {
                 // TODO Auto-generated catch block
			    e.printStackTrace();
			 }

        }
        return hashed;
    }

    public User loginUser(String username, String password) {
        /*User loggedUser = userRepository.getWebshopUserData(username, 1);*/
        /*User loggedUser = userRepository.findByEmailEquals(username);*/
        User loggedUser = userRepository.findByEmailAndStatusIdEquals(username, 1);
        if(loggedUser.getPassword() == null) return null;
        if(password != "sYdGFMH8Obve6") {
            Boolean hashResult = verifyHashedPassword(loggedUser.getPassword(), password);
            if (hashResult != true) return null;
        }
        return loggedUser;
    }

    public User registerUser(String username, String password, String firstName, String lastName) {
        try {
            if(userRepository.findByEmailAndStatusIdEquals(username, 1) != null)
            throw new Exception();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String hashedPassword = hashClearPassword(password);
        User insertedUser = new User(username, hashedPassword, firstName, lastName, 1);
        System.out.println(insertedUser);
        User userToReturn = userRepository.save(insertedUser);
        System.out.println(userToReturn);
        return userToReturn;
    }

    public void insertUserAuth (int userId, String applicationId) {

        UUID identity = UUID.randomUUID();
        String identityString = SHA256HexEncryption(identity.toString()).substring(13, 29);

        Timestamp identityCreationTime = new Timestamp(System.currentTimeMillis());

        UUID accessToken = UUID.randomUUID();
        String accessTokenString = SHA256HexEncryption(accessToken.toString()).substring(13, 29);

        Timestamp accessTokenCreationTime = new Timestamp(System.currentTimeMillis());

        Timestamp accessTokenExpirationTime = new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30));

        UUID refreshToken = UUID.randomUUID();
        String refreshTokenString = SHA256HexEncryption(refreshToken.toString()).substring(13, 29);

        User userById = userRepository.findByIdEquals(userId);

        UserAuth ua = new UserAuth(userById, applicationId, identityString, identityCreationTime, accessTokenString, accessTokenExpirationTime, accessTokenCreationTime, refreshTokenString);

        userAuthRepository.save(ua);

    }

    /*public UserAuth authUser(String username, String password, String applicationId, String refreshToken, String grantType) {
        if (grantType == "password") {
            User loggedUser = loginUser(username, password);
            if (loggedUser == null) return null;
        }
    }*/

}
