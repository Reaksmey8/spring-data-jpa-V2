package co.istad.springdatajpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50)
        String name,

        @NotNull(message = "price is required")
        @Positive
        BigDecimal price,

        @NotNull(message = "quantity is required")
        @Positive
        Integer qty,

        String description,

        @NotNull(message = "categoryId is required")
        @Positive
        Integer categoryId
) {
}
