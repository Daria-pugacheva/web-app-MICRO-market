package ru.gb.pugacheva.webapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;

}
