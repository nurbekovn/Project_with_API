//package com.services;
//
//import com.dto.requests.AuthRequest;
//import com.dto.requests.UserRegisterRequest;
//import com.dto.response.AuthResponse;
//import com.dto.response.UserRegisterResponse;
//import com.entities.Student;
//import com.entities.User;
//import com.jwt.JwtTokenFilter;
//import com.jwt.JwtTokenUtil;
//import com.repository.StudentRepository;
//import com.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//    private final UserRepository userRepository;
//    private final AuthenticationManager authenticationManager;
//    private final StudentRepository studentRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtTokenFilter jwtTokenFilter;
//    private final JwtTokenUtil jwtTokenUtil;
//
//
//    public AuthResponse login(AuthRequest authRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authRequest.getEmail(),
//                        authRequest.getPassword()
//                )
//        );
//
//        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(
//                        () -> new BadCredentialsException("Bad credentials"));
//        String token = jwtTokenUtil.generateToken(user.getEmail());
//        return new AuthResponse(user.getUsername(), token, user.getRole());
//    }
//
//
//    public UserRegisterResponse register(UserRegisterRequest request) {
//        request.setPassword(passwordEncoder.encode(request.getPassword()));
//        Student student = new Student(request.getFirstName(), request.getLastName(),
//                request.getPhoneNumber(),new User());
//        Student savedStudent = studentRepository.save(student);
//        String token = jwtTokenUtil.generateToken(savedStudent.getUser().getEmail());
//
//        return new UserRegisterResponse(
//                savedStudent.getFirstName(),
//                savedStudent.getLastName(),
//                savedStudent.getUser().getEmail(),
//                token,
//                savedStudent.getUser().getRole());
//
//    }
//}
