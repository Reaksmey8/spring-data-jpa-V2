package co.istad.springdatajpa.service;

import co.istad.springdatajpa.domain.Product;
import co.istad.springdatajpa.dto.CreateProductRequest;
import co.istad.springdatajpa.dto.PatchProductRequest;
import co.istad.springdatajpa.dto.ProductResponse;
import co.istad.springdatajpa.dto.UpdateProductRequest;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;

public interface ProductService {

    // decare methods
    void deleteProductByCode(String code);

    ProductResponse patchByCode(PatchProductRequest patchProductRequest, String code);

    ProductResponse updateByCode(UpdateProductRequest updateProductRequest, String code);

    ProductResponse createNew(CreateProductRequest createProductRequest);

    ProductResponse getProductByCode(String code);
    Page<ProductResponse> getProducts(int pageNumber, int pageSize);

}
