package com.rudev.shop_catalogo.repositories;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.rudev.shop_catalogo.entities.Product;
import com.rudev.shop_catalogo.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	long existingId;
	long countTotalProduct;
	long idProductNotExist;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		countTotalProduct = 15L;
		idProductNotExist = 30L;
		
	}
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		productRepository.deleteById(existingId);
		Optional<Product> result = productRepository.findById(existingId);
		
		Assertions.assertFalse(result.isPresent());
		
	}
	
	@Test
	public void saveShouldPersistWithAutoIcrementWhenIdIsNull() {
		
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = productRepository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProduct + 1, product.getId());
		
	}
	
	@Test
	public void findByIdShouldReturnOptionalNotEmptyWhenIdExist() {
		
		Optional<Product> result = productRepository.findById(existingId);
		Assertions.assertTrue(result.isPresent());;
		
	}
	
	
	@Test
	public void findByIdShouldReturnOptionalEmptyWhenDoesIdNotExist() {
		
		Optional<Product> result = productRepository.findById(idProductNotExist);
		Assertions.assertTrue(result.isEmpty());;
	}
	
}
