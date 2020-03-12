package com.projmoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projmoney.api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	
	
}
