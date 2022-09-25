package com.services;

import com.dto.requests.StudentRequest;
import com.dto.response.StudentResponse;
import com.entities.Company;
import com.entities.Course;
import com.entities.Student;
import com.entities.User;
import com.enums.Role;
import com.exceptions.NotFoundException;
import com.jwt.JwtTokenUtil;
import com.repository.CompanyRepository;
import com.repository.CourseRepository;
import com.repository.StudentRepository;
import com.responseView.StudentResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepo;
    private final CompanyRepository companyRepo;
    private final CourseRepository courseRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;


    public StudentResponse saveStudent(StudentRequest studentRequest) {
        Student student = new Student();
        User user = new User();
        String email = studentRequest.getEmail();
        if (studentRepo.existsByUserEmail(email)) {
            throw new BadCredentialsException("This email taken!!");
        }
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setPhoneNumber(studentRequest.getPhoneNumber());
        student.setEmail(studentRequest.getEmail());
        student.setStudyFormat(studentRequest.getStudyFormat());
        Company company = companyRepo.findById(studentRequest.getCompanyId()).orElseThrow(
                () -> new NotFoundException(String.format("Company with =%s id not found", studentRequest.getCompanyId())));
        company.addStudent(student);
        student.setCompany(company);
        user.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        user.setCreated(LocalDate.now());
        user.setRole(Role.STUDENT);
        user.setEmail(studentRequest.getEmail());
        student.setUser(user);
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
        String companyName = student.getCompany().getCompanyName();
        student.setCompany(null);
        student.setCourse(null);
        studentRepo.delete(student);
        return new StudentResponse(student.getId(), student.getFirstName(), student.getLastName(), student.getPhoneNumber(),
                student.getEmail(), student.getStudyFormat(), companyName);
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
        student.getUser().setEmail(studentRequest.getEmail());
        student.getUser().setRole(Role.STUDENT);
        student.getUser().setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        Company company = companyRepo.findById(studentRequest.getCompanyId()).orElseThrow(
                () -> new NotFoundException(String.format("Company with =%s id not found", studentRequest.getCompanyId())));
        student.setCompany(company);
        company.addStudent(student);
        return studentRepo.save(student);
    }


    public List<StudentResponse> getAllStudent() {
        List<StudentResponse> responses = new ArrayList<>();
        for (Student s : studentRepo.findAll()) {
            responses.add(mapToResponse(s));
        }
        return responses;
    }

    public List<StudentResponse> getAllStudents(List<Student> students) {
        List<StudentResponse> responses = new ArrayList<>();
        for (Student student : students) {
            responses.add(mapToResponse(student));
        }
        return responses;
    }

    public StudentResponseView getAllStudentsPagination(String text, int page, int size) {
        StudentResponseView studentResponseView = new StudentResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Student> students = studentRepo.findAll(pageable);
        studentResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        studentResponseView.setTotalPage(students.getTotalPages());
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
        return String.format("Student with : %s id  successfully assigned to Course" +
                "with : %s id ", studentId, courseId);
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
        response.setCompanyName(student.getCompany().getCompanyName());
        return response;
    }
}
