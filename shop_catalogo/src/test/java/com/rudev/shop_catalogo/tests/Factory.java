package com.rudev.shop_catalogo.tests;

import java.time.Instant;

import com.rudev.shop_catalogo.dto.ProductDTO;
import com.rudev.shop_catalogo.entities.Category;
import com.rudev.shop_catalogo.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		
		Product result = new Product(1L, "Notebook Dell XPS", 8500.00, Instant.parse("2021-01-01T10:00:00Z"), "Notebook potente e leve.", "https://img.com/dellxps.jpg");
		result.getCategories().add(new Category(2L, "Eletrônicos"));
		return result;
	}
	
	public static ProductDTO createProductDTO() {
		
		Product product = createProduct();
		ProductDTO result = new ProductDTO(product, product.getCategories());
		return result;
		
	}

}
