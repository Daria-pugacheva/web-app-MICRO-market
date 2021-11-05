package ru.gb.pugacheva.webapp.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.pugacheva.webapp.api.dtos.ProfileDto;
import ru.gb.pugacheva.webapp.api.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.auth.model.User;
import ru.gb.pugacheva.webapp.auth.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
//@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ProfileDto aboutCurrentUser (@RequestHeader String username){
        User user = userService.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException
                ("Не удалось найти в базе пользователя с именем " + username));
        return new ProfileDto(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

}
