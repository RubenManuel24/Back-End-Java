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
import com.rudev.shop_catalogo.dto.ProductDTO;
import com.rudev.shop_catalogo.entities.Category;
import com.rudev.shop_catalogo.entities.Product;
import com.rudev.shop_catalogo.repositories.CategoryRepository;
import com.rudev.shop_catalogo.repositories.ProductRepository;
import com.rudev.shop_catalogo.services.exceptions.DataIntegritytException;
import com.rudev.shop_catalogo.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
    
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRespository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable){
		Page<Product> listProduct = productRepository.findAll(pageable);
		return listProduct.map(x -> new ProductDTO(x, x.getCategories()));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		Product product1 = product.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return new ProductDTO(product1, product1.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = productRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = productRepository.save(entity);
		   return new ProductDTO(entity);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void deleteById(Long id) {
		//if(!productRepository.existsById(id)) {
			//throw new ResourceNotFoundException("Resource not found");
		//}
		try {
			productRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegritytException("Integrity Exception");
		}
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.setDate(dto.getDate());
		
		entity.getCategories().clear();
		
		for(CategoryDTO catDTO : dto.getCategories()) {
			Category cat = categoryRespository.getReferenceById(catDTO.getId());
			entity.getCategories().add(cat);
		}
	}
	
}





