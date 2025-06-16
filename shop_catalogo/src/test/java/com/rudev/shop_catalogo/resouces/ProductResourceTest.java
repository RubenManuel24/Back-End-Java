package com.rudev.shop_catalogo.resouces;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.rudev.shop_catalogo.dto.ProductDTO;
import com.rudev.shop_catalogo.services.ProductService;
import com.rudev.shop_catalogo.services.exceptions.ResourceNotFoundException;
import com.rudev.shop_catalogo.tests.Factory;

@WebMvcTest(ProductResource.class)
public class ProductResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	private long existId;
	private long nonExistId;
	private ProductDTO produtcDTO;
	private PageImpl<ProductDTO> page;
	
	@BeforeEach
	void setUp() throws Exception{
		
		existId = 1L;
		nonExistId = 2L;
		produtcDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(produtcDTO));
		
		Mockito.when(productService.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(productService.findById(existId)).thenReturn(produtcDTO);
		Mockito.when(productService.findById(nonExistId)).thenThrow(ResourceNotFoundException.class);
		
	}
	
	@Test
	public void findAllPagedShouldReturnPage() throws Exception {
		
	  ResultActions result = mockMvc.perform(get("/products"));
	  
	  result.andExpect(status().isOk())
	        .andExpect(content()
	        .contentType(MediaType.APPLICATION_JSON) );
	}
	
	@Test
	public void findByIdShouldReturnProductWhenIdExist() throws Exception{
		
		ResultActions result = mockMvc.perform(get("/products/{id}", existId));
		
		result.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").exists());

		
	}
	
	@Test
	public void findByIdShouldReturnNotFoundExceptionWhenIdExist() throws Exception {
		
		ResultActions result = mockMvc.perform(get("/products/{id}", nonExistId));
		
		result.andExpect(status().isNotFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		
	}
	
}
