package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity                     // Marks this class as a JPA entity
@Table(name = "todos")      // Explicit table name
@Getter                     // Lombok: generate getters
@Setter                     // Lombok: generate setters
@NoArgsConstructor          // Lombok: no‑arg constructor (required by JPA)
@AllArgsConstructor         // Lombok: all‑args constructor
public class Todo {

    @Id                                 // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SQLite generates the id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Generated ID", example = "1")
    private Long id;                    // Identifier field

    @Version                            // Enables optimistic locking (prevents StaleObjectStateException)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Optimistic locking version", example = "0")
    private Long version;               // Hibernate will manage this column

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    @Column(nullable = false, length = 200) // Title column, not null, max 200 chars
    private String title;              // Todo description

    private boolean completed = false; // Completion flag, defaults to false
}