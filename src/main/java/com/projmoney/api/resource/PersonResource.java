package com.projmoney.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projmoney.api.event.ResourceCreatedEvent;
import com.projmoney.api.model.Person;
import com.projmoney.api.repository.PersonRepository;

@RestController
@RequestMapping("/people")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<Person> criar(@Valid @RequestBody Person person, HttpServletResponse response) {
		Person newPerson = personRepository.save(person);
			
		publisher.publishEvent(new ResourceCreatedEvent(this, response, newPerson.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newPerson);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Person> buscarPeloCodigo(@PathVariable Long id) {
		Person person = personRepository.findOne(id);
		 return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
	}
	
}