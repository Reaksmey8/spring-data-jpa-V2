package co.istad.springdatajpa.repository;

import co.istad.springdatajpa.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
