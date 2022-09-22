package com.repository;


import com.dto.response.StudentResponse;
import com.entities.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select s from Student s where s.company=?1")
    List<Student> getStudentByCompanyId(Long courseId);

    @Query("select new com.dto.response.StudentResponse(s.id , s.firstName," +
            "s.lastName,s.phoneNumber,s.email,s.studyFormat) from Student s")
    List<StudentResponse> getAllStudents();


    @Query("select count(s) from Student s where s.firstName =?1")
    Long getCountOfStudentsByFirstName(@Param("firstName") String firstName);


    @Query("select s from Student s where upper(s.firstName ) like concat('%',:text, '%')" +
            "and upper(s.lastName) like concat('%',:text, '%') ")
    List<Student> searchStudentByFirstNameAndLastName(@Param("text") String text, Pageable pageable);


    @Query("select count(s) from Student s where s.company.id = ?1")
    Long countStudentsByCompanyId(@Param("companyId") Long companyId);


}
