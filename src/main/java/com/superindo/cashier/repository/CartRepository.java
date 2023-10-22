package com.superindo.cashier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.superindo.cashier.model.Cart;
import com.superindo.cashier.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
	@Query("SELECT c FROM Cart c WHERE c.user = :user")
	List<Cart> findByUser(User user);

}
