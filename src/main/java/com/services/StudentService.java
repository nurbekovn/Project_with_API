package com.services;

import com.dto.requests.StudentRequest;
import com.dto.response.StudentResponse;
import com.entities.Company;
import com.entities.Course;
import com.entities.Student;
import com.exceptions.NotFoundException;
import com.repository.CompanyRepository;
import com.repository.CourseRepository;
import com.repository.StudentRepository;
import com.responseView.StudentResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepo;
    private final CompanyRepository companyRepo;
    private final CourseRepository courseRepo;

    public StudentResponse saveStudent(StudentRequest studentRequest) {
        Student student = new Student();
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setPhoneNumber(studentRequest.getPhoneNumber());
        student.setEmail(studentRequest.getEmail());
        student.setStudyFormat(studentRequest.getStudyFormat());
        Company company = companyRepo.findById(studentRequest.getCompanyId()).orElseThrow(
                () -> new NotFoundException(String.format("Company with =%s id not found", studentRequest.getCompanyId())));
        company.addStudent(student);
        student.setCompany(company);
        Student student1 = studentRepo.save(student);
        return mapToResponse(student1);
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Student with =%s id not found", id)));
        return mapToResponse(student);
    }

    public StudentResponse deleteStudentById(Long id) {
        Student student = studentRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Student with =%s id not found", id)));
        student.setCompany(null);
        student.setCourse(null);
        studentRepo.delete(student);
        return mapToResponse(student);
    }

    public StudentResponse updateStudentById(Long id, StudentRequest studentRequest) {
        Student student = studentRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Student with =%s id not found", id)));
        Student student1 = update(student, studentRequest);
        return mapToResponse(student1);
    }

    private Student update(Student student, StudentRequest studentRequest) {
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setPhoneNumber(studentRequest.getPhoneNumber());
        student.setEmail(studentRequest.getEmail());
        student.setStudyFormat(studentRequest.getStudyFormat());
        Company company = companyRepo.findById(studentRequest.getCompanyId()).orElseThrow(
                () -> new NotFoundException(String.format("Company with =%s id not found", studentRequest.getCompanyId())));
        student.setCompany(company);
        company.addStudent(student);
        return studentRepo.save(student);
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepo.getAllStudents();
    }

    public List<StudentResponse> getAllStudents(List<Student> students) {
        List<StudentResponse> responses = new ArrayList<>();
        for (Student student :students) {
            responses.add(mapToResponse(student));
        }
        return responses;
    }

    public StudentResponseView getAllStudentsPagination(String text, int page, int size) {
        StudentResponseView studentResponseView = new StudentResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        studentResponseView.setStudentResponses(getAllStudents(search(text, pageable)));
        return studentResponseView;
    }

    private List<Student> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return studentRepo.searchStudentByFirstNameAndLastName(text.toUpperCase(Locale.ROOT), pageable);
    }

    public String assignStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepo.findById(studentId).orElseThrow(
                () -> new NotFoundException(String.format("Student with =%s id not found", studentId)));
        Course course = courseRepo.findById(courseId).orElseThrow(
                () -> new NotFoundException(String.format("Course with -%s id not found", studentId)));
        student.setCourse(course);
        course.addStudent(student);
        studentRepo.save(student);
        return String.format("Student with : %s id assigned to Course", studentId);
    }

    public Long getCountOfStudentsByFirstName(String firstName) {
        return studentRepo.getCountOfStudentsByFirstName(firstName);
    }

    public Long getCountStudentsByCompanyId(Long companyId) {
        return studentRepo.countStudentsByCompanyId(companyId);
    }


    public StudentResponse mapToResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setEmail(student.getEmail());
        response.setPhoneNumber(student.getPhoneNumber());
        response.setStudyFormat(student.getStudyFormat());
        return response;
    }
}
