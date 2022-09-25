package com.services;
import com.dto.requests.UserRequest;
import com.dto.response.UserResponse;
import com.entities.User;
import com.jwt.JwtTokenUtil;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;



@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public UserResponse create(UserRequest request) {
        User user = mapToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return mapToResponse(userRepository.save(user));
    }


    public UserResponse login(UserRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new BadCredentialsException("bad credentials!"));
        return new UserResponse(user.getId(), user.getEmail(), jwtTokenUtil.generateToken(user.getEmail()),
                user.getRole());
    }


    public User mapToEntity(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .isActive(true)
                .created(LocalDate.now())
                .build();
    }


    public UserResponse mapToResponse(User user) {
        String token = jwtTokenUtil.generateToken(user.getEmail());
        return UserResponse.builder()
                .token(token)
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }
}
