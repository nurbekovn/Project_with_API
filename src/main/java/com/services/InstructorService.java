package com.services;

import com.dto.requests.InstructorRequest;
import com.dto.response.CourseResponse;
import com.dto.response.InstructorResponse;
import com.entities.Company;
import com.entities.Course;
import com.entities.Instructor;
import com.exceptions.NotFoundException;
import com.repository.CompanyRepository;
import com.repository.CourseRepository;
import com.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepo;
    private final CompanyRepository companyRepo;
    private final CourseRepository courseRepo;

    public InstructorResponse saveInstructor(InstructorRequest instructorRequest) {
        Instructor instructor = new Instructor();
        instructor.setFirstName(instructorRequest.getFirstName());
        instructor.setLastName(instructorRequest.getLastName());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setPhoneNumber(instructorRequest.getPhoneNumber());
        instructor.setSpecialization(instructorRequest.getSpecialization());
        Company company = companyRepo.findById(instructorRequest.getCompanyId()).orElseThrow(
                () -> new NotFoundException(String.format("Company with =%s id not found", instructorRequest.getCompanyId())));
        instructor.setCompany(company);
        company.addInstructor(instructor);
        Instructor instructor1 = instructorRepo.save(instructor);
        return response(instructor1);
    }

    public InstructorResponse getInstructorById(Long id) {
        Instructor instructor = instructorRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Instructor with =%s id not found", id)));
        Instructor instructor1 = instructorRepo.save(instructor);
        return response(instructor1);
    }

    public Instructor update(Instructor instructor, InstructorRequest instructorRequest) {
        instructor.setFirstName(instructorRequest.getFirstName());
        instructor.setLastName(instructorRequest.getLastName());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setPhoneNumber(instructorRequest.getPhoneNumber());
        instructor.setSpecialization(instructorRequest.getSpecialization());
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
        return response(instructor1);
    }

    public InstructorResponse deleteInstructorById(Long id) {
        Instructor instructor = instructorRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Instructor with =%s id not found", id)));
        instructor.setCompany(null);
//        instructor.setCourses(List.of(null));
        instructor.addCourse(null);
        instructorRepo.delete(instructor);
        return new InstructorResponse(instructor.getId(), instructor.getFirstName(), instructor.getLastName(),
                instructor.getPhoneNumber(), instructor.getEmail(), instructor.getSpecialization());
    }

    public List<InstructorResponse> getAllInstructors() {
        return instructorRepo.getAllInstructors();
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
        return new InstructorResponse();
    }

    public String assign(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepo.findById(instructorId).orElseThrow(
                () -> new NotFoundException(String.format("Instructor with =%s id not found", instructorId)));
        Course course = courseRepo.findById(courseId).orElseThrow(
                () -> new NotFoundException(String.format("Course with =%s id not found", instructorId)));
        instructor.addCourse(course);
        course.addInstructor(instructor);
        instructorRepo.save(instructor);
        return String.format("Instructor with =%s id assigned to course", instructorId);
    }

    public InstructorResponse response(Instructor instructor) {
        InstructorResponse response = new InstructorResponse();
        response.setId(instructor.getId());
        response.setFirstName(instructor.getFirstName());
        response.setLastName(instructor.getLastName());
        response.setPhoneNumber(instructor.getPhoneNumber());
        response.setEmail(instructor.getEmail());
        response.setSpecialization(instructor.getSpecialization());
        return response;
    }

}