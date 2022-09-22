package com.api;
import com.dto.requests.CompanyRequest;
import com.dto.response.CompanyResponse;
import com.responseView.CompanyResponseView;
import com.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/company")
@PreAuthorize("hasAuthority('ADMIN')")
//@Tag("Company API : User with role admin can get all companies, create, update, get by id, get by name, delete company or get size of students, " +
//        "and with role teacher can get all companies, get company by id and name and get students' size")

@RequiredArgsConstructor

public class CompanyController {
    private final CompanyService companyService;


    @PostMapping("/saveCompany")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CompanyResponse saveCompany(@RequestBody CompanyRequest companyRequest) {
        return companyService.saveCompany(companyRequest);
    }

    @PutMapping("/{id}")
    public CompanyResponse updateCompanyById(@PathVariable Long id,
                                             @RequestBody CompanyRequest companyRequest) {
        return companyService.updateCompanyById(id,companyRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public CompanyResponse getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @DeleteMapping("/{id}")
    public CompanyResponse deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompanyById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
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
