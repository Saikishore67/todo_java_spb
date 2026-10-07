package com.example.backend.service;

  import com.example.backend.model.Todo;
  import com.example.backend.repository.TodoRepository;
  import lombok.RequiredArgsConstructor;
  import org.springframework.stereotype.Service;

  import java.util.List;
  import java.util.Optional;

  /**
   * Service layer that encapsulates business logic for Todo operations.
   * Keeping the controller thin makes the code easier to test and maintain.
   */
  @Service                     // 1️⃣ Marks this class as a Spring-managed component
  @RequiredArgsConstructor    // 2️⃣ Lombok: generates a constructor with all final fields
  public class TodoService {

      private final TodoRepository repository; // 3️⃣ Injected Spring Data JPA repository

      /** Return every todo from the database. */
      public List<Todo> findAll() {
          return repository.findAll();
      }

      /** Save a new todo or update an existing one. */
      public Todo save(Todo todo) {
          // If todo.id is null Hibernate will INSERT; otherwise it will UPDATE.
          return repository.save(todo);
      }

      /** Retrieve a todo by its id, wrapped in Optional to explicitly show the possibility of absence. */
      public Optional<Todo> findById(Long id) {
          return repository.findById(id);
      }

      /** Delete the todo with the given id. Returns true if something was removed. */
      public boolean deleteById(Long id) {
          if (repository.existsById(id)) {
              repository.deleteById(id);
              return true;
          }
          return false;
      }

      /** Convenience method – useful if you want to throw a custom exception when a todo is missing. */
      public Todo getRequiredById(Long id) {
          return repository.findById(id)
                  .orElseThrow(() -> new IllegalArgumentException("Todo not found with id " + id));
      }

      /** Update only the completed status of an existing todo. */
      public Todo updateCompleted(Long id, boolean completed) {
          Todo existing = getRequiredById(id);
          existing.setCompleted(completed);
          return repository.save(existing);
      }
  }
