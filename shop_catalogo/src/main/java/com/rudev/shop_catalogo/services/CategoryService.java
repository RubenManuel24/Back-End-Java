package com.rudev.shop_catalogo.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rudev.shop_catalogo.dto.CategoryDTO;
import com.rudev.shop_catalogo.entities.Category;
import com.rudev.shop_catalogo.repositories.CategoryRepository;
import com.rudev.shop_catalogo.services.exceptions.DataIntegritytException;
import com.rudev.shop_catalogo.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {
    
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable){
		Page<Category> listCategory = categoryRepository.findAll(pageable);
		return listCategory.map(x -> new CategoryDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> catgory = categoryRepository.findById(id);
		Category catgory1 = catgory.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return new CategoryDTO(catgory1);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
		   Category category = categoryRepository.getReferenceById(id);
		   category.setName(dto.getName());
		   category = categoryRepository.save(category);
		   return new CategoryDTO(category);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void deleteById(Long id) {
		if(!categoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Resource not found");
		}
		try {
			categoryRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegritytException("Integrity Exception");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
