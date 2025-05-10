package com.rudev.shop_catalogo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rudev.shop_catalogo.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
