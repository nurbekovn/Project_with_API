package com.repository;

import com.dto.response.InstructorResponse;
import com.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    List<Instructor> findInstructorsByCompanyId(Long companyId);

    @Query("select new com.dto.response.InstructorResponse(i.id ," +
            "i.firstName,i.lastName,i.phoneNumber,i.email,i.specialization)" +
            "from Instructor i")
    List<InstructorResponse> getAllInstructors();


    @Query("select count(i) from Instructor i where i.firstName =?1")
    Long getCountOfInstructors(@Param("firstName") String firstName);


    @Query("select count (i) from Instructor i where i.company.companyName =?1")
    Long countInstructorsByCompanyName(@Param("companyName") String  companyName);


}