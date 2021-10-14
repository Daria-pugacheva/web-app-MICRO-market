package ru.gb.pugacheva.webapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.pugacheva.webapp.dtos.ProductDto;
import ru.gb.pugacheva.webapp.dtos.ProfileDto;
import ru.gb.pugacheva.webapp.exceptions.DataValidationException;
import ru.gb.pugacheva.webapp.exceptions.ResourceNotFoundException;
import ru.gb.pugacheva.webapp.model.Category;
import ru.gb.pugacheva.webapp.model.Product;
import ru.gb.pugacheva.webapp.model.User;
import ru.gb.pugacheva.webapp.services.CategoryService;
import ru.gb.pugacheva.webapp.services.ProductService;
import ru.gb.pugacheva.webapp.services.UserService;

import java.security.Principal;
import java.util.stream.Collectors;

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
