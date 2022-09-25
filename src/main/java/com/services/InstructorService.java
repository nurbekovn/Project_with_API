package com.services;

import com.dto.requests.InstructorRequest;
import com.dto.response.InstructorResponse;
import com.entities.Company;
import com.entities.Course;
import com.entities.Instructor;
import com.entities.User;
import com.enums.Role;
import com.exceptions.BadRequestException;
import com.exceptions.NotFoundException;
import com.repository.CompanyRepository;
import com.repository.CourseRepository;
import com.repository.InstructorRepository;
import com.responseView.InstructorResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepo;
    private final CompanyRepository companyRepo;
    private final CourseRepository courseRepo;

    private final PasswordEncoder passwordEncoder;

    public InstructorResponse saveInstructor(InstructorRequest instructorRequest) {
        Instructor instructor = new Instructor();
        User user = new User();
        String email = instructorRequest.getEmail();
        if (instructorRepo.existsByUserEmail(email)) {
            throw new BadRequestException(("this email  is already taken!"));
        }

        instructor.setFirstName(instructorRequest.getFirstName());
        instructor.setLastName(instructorRequest.getLastName());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setPhoneNumber(instructorRequest.getPhoneNumber());
        instructor.setSpecialization(instructorRequest.getSpecialization());

        user.setCreated(LocalDate.now());
        user.setEmail(instructorRequest.getEmail());
        user.setPassword(passwordEncoder.encode(instructorRequest.getPassword()));
        user.setRole(Role.INSTRUCTOR);
        instructor.setUser(user);

        Company company = companyRepo.findById(instructorRequest.getCompanyId()).orElseThrow(
                () -> new NotFoundException(String.format("Company with =%s id not found", instructorRequest.getCompanyId())));
        instructor.setCompany(company);
        company.addInstructor(instructor);
        Instructor instructor1 = instructorRepo.save(instructor);
        return mapToResponse(instructor1);
    }


    public InstructorResponse getInstructorById(Long id) {
        Instructor instructor = instructorRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Instructor with =%s id not found", id)));
        return mapToResponse(instructor);
    }

    public Instructor update(Instructor instructor, InstructorRequest instructorRequest) {
        instructor.setFirstName(instructorRequest.getFirstName());
        instructor.setLastName(instructorRequest.getLastName());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setPhoneNumber(instructorRequest.getPhoneNumber());
        instructor.setSpecialization(instructorRequest.getSpecialization());
        instructor.getUser().setEmail(instructorRequest.getEmail());
        instructor.getUser().setPassword(passwordEncoder.encode(instructorRequest.getPassword()));
        instructor.getUser().setRole(Role.INSTRUCTOR);
        Company company = companyRepo.findById(instructorRequest.getCompanyId()).orElseThrow(
                () -> new NotFoundException(String.format("Company with =%s id not found", instructorRequest.getCompanyId())));
        instructor.setCompany(company);
        company.addInstructor(instructor);
        return instructorRepo.save(instructor);

    }

    public InstructorResponse updateInstructorById(Long id, InstructorRequest instructorRequest) {
        Instructor instructor = instructorRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Instructor with =%s id not found", id)));
        Instructor instructor1 = update(instructor, instructorRequest);
        return mapToResponse(instructor1);
    }

    public InstructorResponse deleteInstructorById(Long id) {
        Instructor instructor = instructorRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Instructor with =%s id not found", id)));
        String companyName = instructor.getCompany().getCompanyName();
        instructor.setCompany(null);
        instructor.addCourse(null);
        instructorRepo.delete(instructor);
        return new InstructorResponse(instructor.getId(),
                instructor.getFirstName(), instructor.getLastName(), instructor.getPhoneNumber(),
                instructor.getEmail(), instructor.getSpecialization(), companyName);
    }


//    public List<InstructorResponse> getAllInstructors() {
//        return instructorRepo.getAllInstructors();
//    }


    public List<InstructorResponse> getAllInstructor() {
        List<InstructorResponse> responses = new ArrayList<>();
        for (Instructor instructor : instructorRepo.findAll()) {
            responses.add(mapToResponse(instructor));
        }
        return responses;
    }


    public InstructorResponseView getAllInstructorPagination(String text, int page, int size) {
        InstructorResponseView instructorResponseView = new InstructorResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Instructor> instructors = instructorRepo.findAll(pageable);
        instructorResponseView.setCurrentPage(instructorResponseView.getCurrentPage() + 1);
        instructorResponseView.setTotalPage(instructors.getTotalPages());
        instructorResponseView.setInstructorResponses(getAllInstructors(search(text, pageable)));
        return instructorResponseView;
    }


    private List<Instructor> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return instructorRepo.searchInstructorByFirstNameAndLastName(text.toUpperCase(Locale.ROOT), pageable);
    }

    public List<InstructorResponse> getAllInstructors(List<Instructor> instructors) {
        List<InstructorResponse> responses = new ArrayList<>();
        for (Instructor i : instructors) {
            responses.add(mapToResponse(i));
        }
        return responses;
    }


    public Long getCountOfInstructorsByCompanyName(String companyName) {
        return instructorRepo.countInstructorsByCompanyName(companyName);
    }


    public InstructorResponse assignInstructorToCourse(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepo.findById(instructorId).orElseThrow(
                () -> new NotFoundException(String.format("Instructor with =%s id not found", instructorId)));
        Course course = courseRepo.findById(courseId).orElseThrow(
                () -> new NotFoundException(String.format("Course with =%s id not found", instructorId)));
        instructor.addCourse(course);
        course.addInstructor(instructor);
        instructorRepo.save(instructor);
        return mapToResponse(instructor);
    }

    public String assign(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepo.findById(instructorId).orElseThrow(
                () -> new NotFoundException(String.format("Instructor with =%s id not found", instructorId)));
        Course course = courseRepo.findById(courseId).orElseThrow(
                () -> new NotFoundException(String.format("Course with =%s id not found", instructorId)));
        instructor.addCourse(course);
        course.addInstructor(instructor);
        instructorRepo.save(instructor);
        return String.format("Instructor with id =%s  assigned to course with id =%s ",instructorId,courseId);
    }

    public InstructorResponse mapToResponse(Instructor instructor) {
        InstructorResponse response = new InstructorResponse();
        response.setId(instructor.getId());
        response.setFirstName(instructor.getFirstName());
        response.setLastName(instructor.getLastName());
        response.setPhoneNumber(instructor.getPhoneNumber());
        response.setEmail(instructor.getEmail());
        response.setSpecialization(instructor.getSpecialization());
        response.setCompanyName(instructor.getCompany().getCompanyName());
        return response;
    }

}