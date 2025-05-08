package com.rudev.shop_catalogo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rudev.shop_catalogo.dto.CategoryDTO;
import com.rudev.shop_catalogo.entities.Category;
import com.rudev.shop_catalogo.repositories.CategoryRepository;
import com.rudev.shop_catalogo.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
    
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> listCategory = categoryRepository.findAll();
		return listCategory.stream().map(x -> new CategoryDTO(x)).toList();
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> catgory = categoryRepository.findById(id);
		Category catgory1 = catgory.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return new CategoryDTO(catgory1);
	}

	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
	}
	
}
