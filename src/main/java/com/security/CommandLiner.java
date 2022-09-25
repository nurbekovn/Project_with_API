//package com.security;
//import com.entities.User;
//import com.enums.Role;
//import com.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class CommandLiner {
//
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//
//    @Bean
//    public CommandLineRunner commandLiner() {
//        return (args) -> {
//            if (userRepository.findUserByAuthoritiesEAndEmail("zamir@gmail.com") == null) {
//                User user = new User("zamir@gmail.com", passwordEncoder.encode("zamir"), Role.ADMIN);
//                User user1 = new User(user.getEmail(), user.getPassword(), user.getRole());
//                userRepository.save(user1);
//            }
//        };
//    }
//}