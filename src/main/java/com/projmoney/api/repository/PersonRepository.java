package com.projmoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projmoney.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
