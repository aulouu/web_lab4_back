package org.example.web_lab4_back.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
public class Dot implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Float x;
    private Float y;
    private Float r;
    private Boolean success;
    private String curTime;
    private String execTime;
    private String ownerLogin;

    public Dot(float x, float y, float r, String ownerLogin){
        this.x = x;
        this.y = y;
        this.r = r;
        this.success = checkSuccess(x, y, r);
        this.curTime = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
        this.ownerLogin = ownerLogin;
    }

    public static boolean validateInput(float x, float y, float r){
        if (x > 5 || x < -5) return false;
        if (y > 3 || y < -3) return false;
        return !(r > 5) && !(r < 0);
    }

    public static boolean checkSuccess(float x, float y, float r){
        if (x < 0 && y > 0 && (x * x + y * y > r * r)) return false;
        if (x > 0 && y < 0 && (y < x - r / 2)) return false;
        if (x < 0 && y < 0 && (x < -r || y < -r)) return false;
        if (x > 0 && y > 0) return false;
        if (x == 0 && (y > r || y < -r)) return false;
        if (y == 0 && (x > r / 2 || x < -r)) return false;
        return true;
    }
}
