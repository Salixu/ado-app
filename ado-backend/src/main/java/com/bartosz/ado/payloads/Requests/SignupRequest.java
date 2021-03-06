package com.bartosz.ado.payloads.Requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    private String name;
    private String surname;
    private String password;
    private String email;
    private Set<String> role;

}
