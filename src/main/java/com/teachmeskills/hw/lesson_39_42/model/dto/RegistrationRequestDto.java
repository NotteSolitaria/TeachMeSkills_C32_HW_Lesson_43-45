package com.teachmeskills.hw.lesson_39_42.model.dto;

import com.teachmeskills.hw.lesson_39_42.annotation.AgeAnnotation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class RegistrationRequestDto {
    @NotNull
    @Size(min = 2, max = 20)
    private String first_name;

    @NotNull
    @Size(min = 2, max = 20)
    private String second_name;

    @AgeAnnotation
    private Integer age;

    @Email
    private String email;

    @NotNull
    @NotBlank
    private String login;

    @NotNull
    @NotBlank
    private String password;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
