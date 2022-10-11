package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.model.Fund;

public interface FundRepository extends CrudRepository<Fund, Long> {
}
