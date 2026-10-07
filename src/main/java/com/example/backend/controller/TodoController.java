package com.example.backend.controller;

import com.example.backend.model.Todo;
import com.example.backend.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    // GET /api/todos → list all todos
    @GetMapping
    public List<Todo> getAll() {
        return service.findAll();   // delegate to service
    }

    // POST /api/todos → create a new todo
    @PostMapping
    public Todo create(@Valid @RequestBody Todo todo) {
        todo.setId(null);       // ensure Hibernate treats this as a new insert
        todo.setVersion(null);  // let Hibernate manage the version
        return service.save(todo);  // delegate to service (save handles insert OR update)
    }

    // PUT /api/todos/{id} → update an existing todo
    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id,
                                        @Valid @RequestBody Todo updated) {
        // Service method that throws if not found – we translate that to 404
        try {
            Todo existing = service.getRequiredById(id); // throws IllegalArgumentException if missing
            existing.setTitle(updated.getTitle());
            existing.setCompleted(updated.isCompleted());
            Todo saved = service.save(existing);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH /api/todos/{id}/completed?completed=true → alter only completed status
    @PatchMapping("/{id}/completed")
    public ResponseEntity<Todo> updateCompleted(
            @PathVariable Long id,
            @RequestParam boolean completed) {
        try {
            Todo saved = service.updateCompleted(id, completed);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /api/todos/{id} → delete a todo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean removed = service.deleteById(id);
        return removed ? ResponseEntity.noContent().build()
                        : ResponseEntity.notFound().build();
    }
}
  
  
  
  
// package com.example.backend.controller;
// import com.example.backend.model.Todo;
// import com.example.backend.repository.TodoRepository;
// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController 
// @RequestMapping("api/docs")
// @RequiredArgsConstructor 

// public class TodoController {
//     private final TodoRepository repository;

//     @GetMapping 
//     public List<Todo> getAll() {
//         return repository.findAll();
//     }

//     @PostMapping 
//     public Todo create(@Valid @RequestBody Todo todo) {
//         return repository.save(todo);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Todo> update(@PathVariable Long id, @Valid @RequestBody Todo updated) {
//         return repository.findById(id)
//         .map(todo -> {
//             todo.setTitle(updated.getTitle());
//             todo.setCompleted(updated.isCompleted());
//             return ResponseEntity.ok(repository.save(todo));
//         })
//         .orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> delete(@PathVariable Long id) {
//         return repository.findById(id)
//         .map(todo -> {
//             repository.delete(todo);
//             return ResponseEntity.noContent().<Void>build();
//         })
//         .orElseGet() -> ResponseEntity.notFound().build());
//     }
// }
