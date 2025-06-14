package com.rudev.shop_catalogo.services;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rudev.shop_catalogo.entities.Product;
import com.rudev.shop_catalogo.repositories.CategoryRepository;
import com.rudev.shop_catalogo.repositories.ProductRepository;
import com.rudev.shop_catalogo.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
     
	 @InjectMocks
     private ProductService productService;
	 
	 @Mock
	 private ProductRepository productRepository;
	 
	 @Mock
	 private CategoryRepository categoryRepository;
	 
	 private long existedId;
	 private long notExistedId;
	 private long dependentId;
	 private PageImpl<Product> page;
	 private Product product;
	 
	 @BeforeEach
	 void setUp() throws Exception{
		 existedId = 1L;
		 notExistedId = 2L;
		 dependentId = 3L;
		 product = Factory.createProduct();
		 page = new PageImpl<>(List.of(product));
		 
		 Mockito.when(productRepository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		 
		 Mockito.doNothing().when(productRepository).deleteById(existedId);
		 Mockito.when(productRepository.existsById(existedId)).thenReturn(true);
		 Mockito.when(productRepository.existsById(notExistedId)).thenReturn(false);
		 
		 Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
		  
		 Mockito.when(productRepository.existsById(dependentId)).thenReturn(true);
		
	 }
	 
	 @Test
	 public void deleteShouldThrowsDataBaseExceptionsWhenDepentId() {
		 
		 Assertions.assertThrows(Exception.class, () -> {
			 productService.deleteById(dependentId);
		 });
		 
	 }
	 
	 @Test
	 public void deleteShouldDoNothingWhenIdExists() {
		 
		 Assertions.assertDoesNotThrow(() -> {
			 productService.deleteById(existedId);
		 });
		 
	 }
	 
	 
	 
	 
	
}
