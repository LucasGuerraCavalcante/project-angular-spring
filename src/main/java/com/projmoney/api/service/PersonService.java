package com.projmoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projmoney.api.model.Person;
import com.projmoney.api.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	public Person update(Long id, Person person) {
		Person newPerson = personRepository.findOne(id);
		if (newPerson == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(person, newPerson, "id");
		return personRepository.save(newPerson);
		
	}
}
