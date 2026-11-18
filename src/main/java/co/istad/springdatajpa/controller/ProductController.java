package co.istad.springdatajpa.controller;

import co.istad.springdatajpa.dto.CreateProductRequest;
import co.istad.springdatajpa.dto.PatchProductRequest;
import co.istad.springdatajpa.dto.ProductResponse;
import co.istad.springdatajpa.dto.UpdateProductRequest;
import co.istad.springdatajpa.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    // inject service
    private final ProductService productService;

    @GetMapping
    public Page<ProductResponse> getProducts(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize
            ){
        return productService.getAllProducts(pageNumber, pageSize);
    }

    @GetMapping("/{code}")
    public ProductResponse getProductByCode(@PathVariable String code){

        log.info("getProductByCode: {} ", code);
        return productService.getProductByCode(code);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponse createNewProduct(
           @Valid @RequestBody CreateProductRequest createProductRequest){
        log.info("createProductRequest: {}" , createProductRequest);

        return productService.createNewProduct(createProductRequest);
    }

    @PutMapping("{code}")
    public ProductResponse updateByCode(
            @Valid @RequestBody UpdateProductRequest updateProductRequest,
            @PathVariable String code)
    {
        log.info("UpdateProductRequest: {} and code: {}", updateProductRequest, code);

        return productService.updateProductByCode(updateProductRequest, code);
    }

    @PatchMapping("{code}")
    public ProductResponse patchByCode(
            @Valid @RequestBody PatchProductRequest patchProductRequest,
            @PathVariable String code){
        log.info("updateProductByCode: {}, code: {}" , patchProductRequest, code);
        return productService.patchByCode(patchProductRequest, code);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{code}")
    public void deleteByCode(
            @PathVariable String code
    ){
        log.info("deleteByCode: {}", code);
        productService.deleteProductByCode(code);
    }
}
