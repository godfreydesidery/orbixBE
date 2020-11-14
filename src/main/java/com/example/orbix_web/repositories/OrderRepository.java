package com.example.orbix_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orbix_web.models.Customer;

@Repository
public interface OrderRepository extends JpaRepository<Customer, Long> {

}
