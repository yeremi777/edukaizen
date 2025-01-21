package com.kokuu.edukaizen.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.kokuu.edukaizen.entities.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String profileImage;
    private String title;
    private String biography;
    private JsonNode socialMedia;
    private String externalService;
    private String externalServiceUserId;
    private Date validatedAsInstructorAt;
    private Role role;
}
