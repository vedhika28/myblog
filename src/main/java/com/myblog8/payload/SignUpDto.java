package com.myblog8.payload;

import com.myblog8.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

//pojo class
@Data
public class SignUpDto {


    private String name;

    private String username;

    private String email;

    private String password;






}