package com.dto.requests;

import com.dto.response.LoginResponse;
import com.entities.Role;
import com.entities.User;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Login {
    public LoginResponse toLoginView(String token, String message, User user) {
        var loginResponse = new LoginResponse();
        if (user != null) {
            getAuthority(loginResponse, user.getRoles());
        }
        loginResponse.setMessage(message);
        loginResponse.setJwtToken(token);
        return loginResponse;
    }

    private void getAuthority(LoginResponse loginResponse, List<Role> roles) {
        Set<String> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(role.getRoleName());
        }
        loginResponse.setAuthorities(authorities);
    }
}
