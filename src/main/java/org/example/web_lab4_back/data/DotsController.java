package org.example.web_lab4_back.data;

import org.example.web_lab4_back.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.List;

@RestController
@AllArgsConstructor
public class DotsController {
    @Autowired
    UserService userService;
    @Autowired
    private final DotsRepository dotsRepository;


    @GetMapping("/api/dots")
    public List<Dot> getDots(@RequestHeader("Authorization") String authorization){
        String[] userName = decode(authorization);
        String login = userName[0];
        String password = userName[1];

        return dotsRepository.getDotByOwnerLogin(userService.logIn(login, password));
    }

    @PostMapping("/api/dots")
    public Dot addDot(@RequestParam("x") float x, @RequestParam("y") float y, @RequestParam("r") float r, @RequestHeader("Authorization") String authorization){
        long timer = System.nanoTime();

        String[] userName = decode(authorization);
        String login = userName[0];
        String password = userName[1];

        String login1 = userService.logIn(login, password);
        if (!Dot.validateInput(x, y, r)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid x, y, r");
        }

        Dot dot = new Dot(x, y, r, login1);
        dot.setExecTime(String.valueOf(String.format("%.2f", ((System.nanoTime() - timer) * 0.000001))));

        dotsRepository.save(dot);
        return dot;
    }

    @DeleteMapping("/api/dots")
    public void deleteDots(@RequestHeader("Authorization") String authorization){
        String[] userName = decode(authorization);
        String login = userName[0];
        String password = userName[1];

        String login1 = userService.logIn(login, password);
        dotsRepository.deleteDotsByOwnerLogin(login1);
    }

    public String[] decode(String authorization) {
        String[] data;
        if(!authorization.startsWith("Basic")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid authentication method");
        }
        try{
            var base64 = authorization.substring(6);
            data =  new String(Base64.getDecoder().decode(base64)).split(":", 2);
            if(data.length != 2){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid base64");
        }
        return data;
    }
}
