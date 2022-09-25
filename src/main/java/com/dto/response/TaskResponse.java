package com.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long  id;
    private String taskName;
    private String taskText;
    private LocalDate deadline;
    private Long lessonId;
    private String lessonName;
}
