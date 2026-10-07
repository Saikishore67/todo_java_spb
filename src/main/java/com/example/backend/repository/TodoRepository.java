package com.example.backend.repository;

import com.example.backend.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// public class TodoRepository {
    
// }

@Repository 
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // "TodoRepository should have all the functionality provided by JpaRepository, for a Todo entity whose primary key is Long."
    // No additional methods needed for basic CRUD – JpaRepository already provides:
    //   List<Todo> findAll();
    //   Todo save(Todo entity);
    //   Todo findById(Long id);
    //   void deleteById(Long id);
    //   boolean existsById(Long id);
    //   long count();
}
