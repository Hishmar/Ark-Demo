package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.model.Investor;

public interface InvestorRepository extends CrudRepository<Investor, Long> {
}