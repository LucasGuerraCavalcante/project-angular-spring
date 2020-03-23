package com.projmoney.api.resource;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projmoney.api.event.ResourceCreatedEvent;
import com.projmoney.api.model.Category;
import com.projmoney.api.repository.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
	 
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<?> list() {
		List<Category> categories = categoryRepository.findAll();
		return !categories.isEmpty() ? ResponseEntity.ok(categories) : ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Category> create(@Valid @RequestBody Category category, HttpServletResponse response) {
		Category newCategory = categoryRepository.save(category);
		
		publisher.publishEvent(new ResourceCreatedEvent(this, response, newCategory.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
	}
	  
	@GetMapping("/{id}")
	public Category findById(@PathVariable Long id) {
		return categoryRepository.findOne(id);
	}

}
