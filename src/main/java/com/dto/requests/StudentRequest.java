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
    private int phoneNumber;
    private String email;
    @Enumerated(EnumType.ORDINAL)
    private Study studyFormat;
    private Long companyId;

//    private String login;
//    private String password;
    // SEESION , COOKIES


}
