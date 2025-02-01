package com.ageinghippy.cnclient_application.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    @JsonProperty("userId")
    @JsonAlias("id") //todo is this strictly necessary?
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
}
