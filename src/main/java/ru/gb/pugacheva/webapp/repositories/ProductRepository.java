package ru.gb.pugacheva.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.pugacheva.webapp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {
}
