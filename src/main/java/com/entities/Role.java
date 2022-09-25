//package com.entities;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "roles")
//@Getter
//@Setter
//@NoArgsConstructor
//public class Role implements GrantedAuthority {
//    @Id
//    @SequenceGenerator(name = "role_gen",sequenceName = "role_seq",allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "role_gen")
//    private Long id;
//    private String roleName;
//
//    @ManyToMany(mappedBy = "roles",cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
//    private List<User> users ;
//    @Override
//    public String getAuthority() {
//        return roleName;
//    }
//}
