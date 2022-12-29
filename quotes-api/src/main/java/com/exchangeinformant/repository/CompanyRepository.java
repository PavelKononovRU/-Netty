package com.exchangeinformant.repository;

import com.exchangeinformant.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    public Company findCompanyBySymbol(String stockName);
}