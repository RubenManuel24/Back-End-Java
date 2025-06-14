package com.rudev.shop_catalogo.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rudev.shop_catalogo.repositories.CategoryRepository;
import com.rudev.shop_catalogo.repositories.ProductRepository;

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
	 
	 @BeforeEach
	 void setUp() throws Exception{
		 existedId = 1L;
		 notExistedId = 2L;
		 dependentId = 3L;
		 
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
