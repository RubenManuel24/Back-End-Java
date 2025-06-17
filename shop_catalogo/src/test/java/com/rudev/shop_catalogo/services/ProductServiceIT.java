package com.rudev.shop_catalogo.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.rudev.shop_catalogo.repositories.ProductRepository;

@SpringBootTest
public class ProductServiceIT {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	private long existingId ;
	private long nonExistingId;
	private long countTotalProduct = 14;
	
	@BeforeEach
	 void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 100000L;
		countTotalProduct = 15;
	}
	
	@Test
	public void deleteShouldDeleteResourceWhenExistId() {
		
		productService.deleteById(existingId);
		
		Assertions.assertEquals(countTotalProduct - 1, productRepository.count());
	}
	
}
