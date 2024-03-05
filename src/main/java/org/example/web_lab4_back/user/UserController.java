package org.example.web_lab4_back.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping("/api/register")
    public void signIn(@RequestParam("login") String login, @RequestParam("password") String password) {
        userService.signIn(login, password);
    }

    @PostMapping("/api/login")
    public void logIn(@RequestParam("login") String login, @RequestParam("password") String password) {
        userService.logIn(login, password);
    }

}
