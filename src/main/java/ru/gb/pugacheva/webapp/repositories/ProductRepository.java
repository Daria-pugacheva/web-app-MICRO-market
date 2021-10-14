package ru.gb.pugacheva.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.gb.pugacheva.webapp.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long>, JpaSpecificationExecutor <Product> {

    Optional<Product> findByTitle(String title);
}
