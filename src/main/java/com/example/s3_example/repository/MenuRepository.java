package com.example.s3_example.repository;

import com.example.s3_example.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
