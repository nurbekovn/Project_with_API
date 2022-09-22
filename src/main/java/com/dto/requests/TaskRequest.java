package com.dto.requests;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDate;

@Getter
@Setter
public class TaskRequest {
    private String taskName;
    private String taskText;
    @CreatedDate
    private LocalDate deadline;
    private Long lessonId;
}
