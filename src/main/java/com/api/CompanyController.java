package com.api;
import com.dto.requests.CompanyRequest;
import com.dto.response.CompanyResponse;
import com.responseView.CompanyResponseView;
import com.services.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/company")
@PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
@Tag(name = "Company API ", description = " User with role admin can get all companies, create, update, get by id, delete company ")

@RequiredArgsConstructor

public class CompanyController {
    private final CompanyService companyService;


    @PostMapping("/saveCompany")
    @PreAuthorize("hasAnyAuthority('ADMIN','STUDENT')")
    public CompanyResponse saveCompany(@RequestBody CompanyRequest companyRequest) {
        return companyService.saveCompany(companyRequest);
    }


    @PutMapping("/{id}")
    public CompanyResponse updateCompanyById(@PathVariable Long id,
                                             @RequestBody CompanyRequest companyRequest) {
        return companyService.updateCompanyById(id,companyRequest);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public CompanyResponse getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }


    @DeleteMapping("/{id}")
    public CompanyResponse deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompanyById(id);
    }


    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public List<CompanyResponse> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/pagination")
    public CompanyResponseView getAllCompanyPagination(@RequestParam(name = "text",required = false)
                                                       String text,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        return companyService.getAllCompaniesPagination(text, page, size);
    }
}
