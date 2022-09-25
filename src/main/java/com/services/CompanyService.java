package com.services;

import com.dto.requests.CompanyRequest;
import com.dto.response.CompanyResponse;
import com.responseView.CompanyResponseView;
import com.entities.Company;
import com.exceptions.NotFoundException;
import com.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyResponse saveCompany(CompanyRequest companyRequest) {
        Company company = new Company();
        company.setCompanyName(companyRequest.getCompanyName());
        company.setLocatedCountry(companyRequest.getLocatedCountry());
        Company company1 = companyRepository.save(company);
        return mapToResponse(company1);
    }

    public Company update(Company company, CompanyRequest companyRequest) {
        company.setCompanyName(companyRequest.getCompanyName());
        company.setLocatedCountry(companyRequest.getLocatedCountry());
        return companyRepository.save(company);
    }

    public CompanyResponse updateCompanyById(Long id, CompanyRequest companyRequest) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Company with =%s id not found", id)));
        String companyName = company.getCompanyName();
        String newCompanyName = companyRequest.getCompanyName();
        if (newCompanyName != null && !newCompanyName.equals(companyName)) {
            company.setCompanyName(newCompanyName);
        }
        String locatedCountry = company.getLocatedCountry();
        String newLocatedCountry = companyRequest.getLocatedCountry();
        if (newLocatedCountry != null && !newLocatedCountry.equals(locatedCountry)) {
            company.setLocatedCountry(newLocatedCountry);
        }
        Company company1 = update(company, companyRequest);
        return mapToResponse(company1);

    }

    public CompanyResponse getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Company with =%s id not found", id)));
        return new CompanyResponse(company.getId(),
                company.getCompanyName(), company.getLocatedCountry());
    }

    public CompanyResponse deleteCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Company with =%s id not found", id)));
        companyRepository.delete(company);
        return mapToResponse(company);

    }


    public List<CompanyResponse> getAllCompanies() {
        return companyRepository.getAllCompanies();
    }


    public List<CompanyResponse> getAllCompanies(List<Company> companies) {
        List<CompanyResponse> responses = new ArrayList<>();
        for (Company company : companies) {
            responses.add(mapToResponse(company));
        }
        return responses;
    }

    public CompanyResponseView getAllCompaniesPagination(String text, int page, int size) {
        CompanyResponseView companyResponseView = new CompanyResponseView();
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("companyName"));
        companyResponseView.setCompanyResponses(getAllCompanies(search(text, pageable)));

        List<CompanyResponse> companyResponses = new ArrayList<>();
        Page<Company> allCompanies = companyRepository.findAll(pageable);
        for (Company company : allCompanies) {
            companyResponses.add(mapToResponse(company));
        }
        companyResponseView.setCompanyResponses(companyResponses);
        companyResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        companyResponseView.setTotalPage(allCompanies.getTotalPages());
        return companyResponseView;
    }

    private List<Company> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return companyRepository.searchByCompanyName(text.toUpperCase(Locale.ROOT), pageable);
    }

    public CompanyResponse mapToResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setCompanyName(company.getCompanyName());
        companyResponse.setLocatedCountry(company.getLocatedCountry());
        return companyResponse;
    }
}
