package com.example.backend;

import com.example.backend.model.Todo;
import com.example.backend.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	@Autowired
	private TodoService todoService;

	@Autowired
	private com.example.backend.controller.TodoController todoController;

	@Test
	void contextLoads() {
	}

	@Test
	void testCreateTodoViaControllerWithDefaultSwaggerInputs() {
		Todo inputFromSwagger = new Todo(0L, 0L, "New Task", false);
		Todo saved = todoController.create(inputFromSwagger);
		org.junit.jupiter.api.Assertions.assertNotNull(saved.getId());
		org.junit.jupiter.api.Assertions.assertNotEquals(0L, saved.getId());
		org.junit.jupiter.api.Assertions.assertEquals("New Task", saved.getTitle());
	@Test
	void testUpdateCompletedViaController() {
		Todo created = todoController.create(new Todo(null, null, "Patch Task", false));
		org.junit.jupiter.api.Assertions.assertFalse(created.isCompleted());

		var response = todoController.updateCompleted(created.getId(), true);
		org.junit.jupiter.api.Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
		org.junit.jupiter.api.Assertions.assertNotNull(response.getBody());
		org.junit.jupiter.api.Assertions.assertTrue(response.getBody().isCompleted());
		org.junit.jupiter.api.Assertions.assertEquals("Patch Task", response.getBody().getTitle());
	}
}


