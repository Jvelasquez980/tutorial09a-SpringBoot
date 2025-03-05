package com.tuto.tutorial09a.repositories;

import com.tuto.tutorial09a.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

