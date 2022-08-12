package ru.gbank.pabw.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gbank.pabw.core.converters.ProductConverter;
import ru.gbank.pabw.model.dto.ProductDto;
import ru.gbank.pabw.core.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с юанковскими продуктами")
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping("")
    @Operation(summary = "Запрос получения продуктов для всех счетов", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = List.class)))})
    public List<ProductDto> findAll() {
        return productService.findAll().stream()
                .map(productConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/credit")
    @Operation(summary = "Запрос получения продуктов для всех счетов", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = List.class)))})
    public List<ProductDto> findAllCredit() {
        return productService.findAllCreditTypeProduct().stream()
                .map(productConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/debit")
    @Operation(summary = "Запрос получения продуктов для всех счетов", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = List.class)))})
    public List<ProductDto> findAllDebit() {
        return productService.findAllDebitTypeProduct().stream()
                .map(productConverter::entityToDto)
                .collect(Collectors.toList());
    }


}
