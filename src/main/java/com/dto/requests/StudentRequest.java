package com.dto.requests;
import com.enums.Study;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class StudentRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    private Study studyFormat;
    private String password;
    private Long companyId;


//    private String login;
//    private String password;
    // SEESION , COOKIES


}
