package com.api;

import com.dto.requests.StudentRequest;
import com.dto.response.StudentResponse;
import com.responseView.StudentResponseView;
import com.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/save")
    public StudentResponse saveStudent(@RequestBody StudentRequest studentRequest) {
        return studentService.saveStudent(studentRequest);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }


    @PutMapping("/{id}")
    public StudentResponse updateStudentById(@PathVariable Long id,
                                             @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudentById(id, studentRequest);
    }


    @DeleteMapping("/{id}")
    public StudentResponse deleteStudentById(@PathVariable Long id) {
        return studentService.deleteStudentById(id);
    }


    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public List<StudentResponse> getAllStudent() {
        return studentService.getAllStudent();
    }


    @PostMapping("/{studentId}/{courseId}/assignStudentToCourse")
    public String assignStudentToCourse(@PathVariable Long studentId,
                                        @PathVariable Long courseId) {
        return studentService.assignStudentToCourse(studentId, courseId);
    }

    @GetMapping("/{firstName}/countOfStudents")
    public Long getCountOfStudents(@PathVariable String firstName) {
        return studentService.getCountOfStudentsByFirstName(firstName);
    }


    @GetMapping("/{companyId}/countS")
    public Long countStudentsByCompanyId(@PathVariable Long companyId) {
        return studentService.getCountStudentsByCompanyId(companyId);
    }


    @GetMapping("/studentPagination")
    public StudentResponseView getAllStudentsPagination(@RequestParam(name = "text", required = false)
                                                        String text,
                                                        @RequestParam int page,
                                                        @RequestParam int size) {
        return studentService.getAllStudentsPagination(text, page, size);
    }
}
