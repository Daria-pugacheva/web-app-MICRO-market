package ru.gb.pugacheva.webapp.core.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.pugacheva.webapp.core.model.Order;
import ru.gb.pugacheva.webapp.core.model.Product;

import javax.persistence.NamedEntityGraph;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long> {

    @Query("select o from Order o where o.username = :username")
    @EntityGraph(value = "orders.for-front")
    List<Order> findAllByUsername (String username);

    Optional <Order> findOneByIdAndUsername (Long id, String username);

    @Modifying
    @Query("update Order o set o.status= 'Оплачено' where o.id = :id" )
    void setPaidOrderStatus (Long id);
}
