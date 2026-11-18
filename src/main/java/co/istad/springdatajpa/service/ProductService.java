package co.istad.springdatajpa.service;

import co.istad.springdatajpa.domain.Product;
import co.istad.springdatajpa.dto.CreateProductRequest;
import co.istad.springdatajpa.dto.PatchProductRequest;
import co.istad.springdatajpa.dto.ProductResponse;
import co.istad.springdatajpa.dto.UpdateProductRequest;
import co.istad.springdatajpa.repository.ProductRepository;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;

public interface ProductService {

    // decare methods
    Page<ProductResponse> getAllProducts(int pageNumber, int pageSize);
    ProductResponse getProductByCode(String code);

    ProductResponse createNewProduct(CreateProductRequest createProductRequest);

    void deleteProductByCode(String code);

    ProductResponse updateProductByCode(UpdateProductRequest updateProductRequest, String code);

    ProductResponse patchByCode(PatchProductRequest patchProductRequest, String code);
}
