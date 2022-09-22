package com.responseView;

import com.dto.response.TaskResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskResponseView {
    List<TaskResponse> taskResponses;
}
