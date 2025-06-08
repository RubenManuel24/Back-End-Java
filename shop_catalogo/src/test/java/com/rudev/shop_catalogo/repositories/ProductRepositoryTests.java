package com.rudev.shop_catalogo.repositories;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.rudev.shop_catalogo.entities.Product;

@DataJpaTest
public class ProductRepositoryTests {
	
	long existingId;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
	}
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		productRepository.deleteById(existingId);
		Optional<Product> result = productRepository.findById(existingId);
		
		Assertions.assertFalse(result.isPresent());
		
	}
}
