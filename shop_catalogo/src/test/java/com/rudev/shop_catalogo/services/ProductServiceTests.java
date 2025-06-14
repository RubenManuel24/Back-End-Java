package com.rudev.shop_catalogo.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
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
	 
	 @BeforeEach
	 void setUp() throws Exception{
		 existedId = 1L;
		 
		 notExistedId = 100000L;
		 
		 Mockito.doNothing().when(productRepository).deleteById(existedId);
		 
		 Mockito.doThrow(EmptyResultDataAccessException.class ).when(productRepository).deleteById(notExistedId);
	 }
	 
	 @Test
	 public void deleteShouldDoNothingWhenIdExists() {
		 
		 Assertions.assertDoesNotThrow(() -> {
			 productService.deleteById(existedId);
		 });
		 
		 Mockito.verify(productRepository, Mockito.times(1)).deleteById(existedId);
		 
	 }
	 
	 
	 
	 
	
}
