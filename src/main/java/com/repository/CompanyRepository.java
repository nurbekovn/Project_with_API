package com.repository;
import com.dto.response.CompanyResponse;
import com.entities.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("select new com.dto.response.CompanyResponse(c.id,c.companyName,c.locatedCountry) from Company c")
    List<CompanyResponse> getAllCompanies();

//  @Query("select  s from  Student  s where upper(s.name) like concat('%',:text,'%') " +
//            "or upper(s.email)  like concat('%',:text,'%') " +
//            "or upper(s.surname)  like concat('%',:text,'%')")
//    List<Student> searchByName(@Param("text")String text, Pageable pageable);

    @Query("select c from Company c where upper(c.companyName) like concat('%',:text,'%')")
    List<Company> searchByCompanyName(@Param("text")String text, Pageable pageable);

}
