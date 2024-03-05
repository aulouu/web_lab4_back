package org.example.web_lab4_back.user;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserData {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String login;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String salt;

    public UserData(String login, String password, String salt) {
        this.login = login;
        this.password = password;
        this.salt = salt;
    }
}
