package com.rudev.shop_catalogo.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.rudev.shop_catalogo.dto.ProductDTO;
import com.rudev.shop_catalogo.entities.Product;
import com.rudev.shop_catalogo.repositories.CategoryRepository;
import com.rudev.shop_catalogo.repositories.ProductRepository;
import com.rudev.shop_catalogo.services.exceptions.ResourceNotFoundException;
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
		 
		 Mockito.when(productRepository.getReferenceById(existedId)).thenReturn(product);
		 Mockito.when(productRepository.save((Product)ArgumentMatchers.any())).thenReturn(product);
		 
		 Mockito.when(productRepository.findById(existedId)).thenReturn(Optional.of(product));
		 Mockito.when(productRepository.findById(notExistedId)).thenReturn(Optional.empty());
		 
		 Mockito.doNothing().when(productRepository).deleteById(existedId);
		 Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);

		 Mockito.when(productRepository.existsById(existedId)).thenReturn(true);
		 Mockito.when(productRepository.existsById(notExistedId)).thenReturn(false);
		 
		 Mockito.when(productRepository.existsById(dependentId)).thenReturn(true);
		
	 }
	 
	 @Test
	 public void findAllPagedShouldReturnPage() {
		 
		 Pageable pageable = PageRequest.of(0, 10);
		 Page<ProductDTO> result = productService.findAllPaged(pageable);
		 
		 Assertions.assertNotNull(result);
		 Mockito.verify(productRepository).findAll(pageable);
		  
	 }
	 
	 @Test
	 public void findByIdShouldReturnProductDTOWhenIdExist() {
		 
		 ProductDTO result = productService.findById(existedId);
		 Assertions.assertNotNull(result);
		 
		 Mockito.verify(productRepository).findById(existedId);
	 }
	 
	 @Test
	 public void findByIdShouldThrowsResourceNotFountExceptionWhenIdNotExist() {
		 
		 Assertions.assertThrows(Exception.class, () -> {
		        productService.findById(notExistedId);
		    });
		    
		    Mockito.verify(productRepository).findById(notExistedId);
		 
	 }
	 
	 @Test 
	 public void updateShouldReturnProductDTOWhenIdExist() {
		 
		 ProductDTO dto = Factory.createProductDTO();
		 
		 ProductDTO result = productService.update(existedId, dto);
		 Assertions.assertNotNull(result);
		 
		 Mockito.verify(productRepository).getReferenceById(existedId);
		 Mockito.verify(productRepository).save(Mockito.any());
	
	 }
	 
	 @Test
	 public void updateShouldReturnResourceNotFoundExceptionWhenIdNotExist() {
		 
		 ProductDTO dto = Factory.createProductDTO();
		 
		 Assertions.assertThrows(Exception.class, () -> {
			 productService.update(notExistedId, dto);
		 });
		
		Mockito.verify(productRepository, Mockito.never()).save(Mockito.any());
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
