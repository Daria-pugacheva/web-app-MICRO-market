package ru.gb.pugacheva.webapp.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.pugacheva.webapp.api.dtos.ProfileDto;
import ru.gb.pugacheva.webapp.api.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.core.model.User;
import ru.gb.pugacheva.webapp.core.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ProfileDto aboutCurrentUser (Principal principal){
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException
                ("Не удалось найти в базе пользователя с именем " + principal.getName()));
        return new ProfileDto(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail());
    }


}
