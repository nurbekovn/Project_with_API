package com.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class InstructorResponse {
    private Long  id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String specialization;

}
