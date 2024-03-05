package org.example.web_lab4_back.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final String CHARACTERS = """
            ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789<>?:@{!$%^&*()_+£$
            """;

    private static final String PEPPER = "^hE!c&D@+c";

    private String randomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private String getHash(String input){
        byte[] inputBytes = input.getBytes();
        MessageDigest md;
        try{
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {throw new RuntimeException(e);}
        byte[] hashBytes = md.digest(inputBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public void signIn(String login, String password) {
        if(userRepository.findUserDataByLogin(login) != null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Login is not free");
        }
        if(password.isBlank()){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Password must be not blank");
        }
        String salt = this.randomString();
        String cookedPass = PEPPER + password + salt;
        var user = new UserData(login, this.getHash(cookedPass), salt);
        userRepository.save(user);
    }

    public String logIn(String login, String password) {
        if(password.isBlank()){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Password must be not blank");
        }
        UserData user = userRepository.findUserDataByLogin(login);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login is not valid");
        }
        String cookedPass = PEPPER + password + user.getSalt();
        if(!user.getPassword().equals(this.getHash(cookedPass))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }
        return login;
    }
}
