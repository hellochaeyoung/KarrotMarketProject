package com.project.karrot.repository;

import com.project.karrot.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(" select c from Category c where c.categoryName = :name ")
    Optional<Category> findByName(@Param("name") String name);
}
