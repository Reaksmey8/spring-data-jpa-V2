package co.istad.springdatajpa.service;

import co.istad.springdatajpa.domain.Category;
import co.istad.springdatajpa.domain.Product;
import co.istad.springdatajpa.dto.CreateProductRequest;
import co.istad.springdatajpa.dto.PatchProductRequest;
import co.istad.springdatajpa.dto.ProductResponse;
import co.istad.springdatajpa.dto.UpdateProductRequest;
import co.istad.springdatajpa.mapper.ProductMapper;
import co.istad.springdatajpa.repository.CategoryRepository;
import co.istad.springdatajpa.repository.ProductRepository;
import co.istad.springdatajpa.util.GeneratedUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    // inject dependency
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public void deleteProductByCode(String code) {
        Product product = productRepository.findById(code)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product code not found"
                        )
                );
        productRepository.delete(product);
    }

    @Override
    public ProductResponse patchByCode(PatchProductRequest patchProductRequest, String code) {
        Product product = productRepository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product Code not found"
                ));

        // 1. validate categoryId (id exists or not)

       if (patchProductRequest.categoryId() != null){
           Category category = categoryRepository
                   .findById(patchProductRequest.categoryId())
                   .orElseThrow(
                           () -> new ResponseStatusException(
                                   HttpStatus.NOT_FOUND,
                                   "categoryId not found"
                           )
                   );
           product.setCategory(category);
       }

        productMapper.patchProductResponseToProduct(patchProductRequest, product);
        product = productRepository.save(product);
        return productMapper.productToProductResponse(product);

    }

    @Override
    public ProductResponse updateByCode(UpdateProductRequest updateProductRequest, String code) {
        // TODO:
        Product product = productRepository.findById(code)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product Code Not Found"
                        )
                );
        productMapper.updateProductResponseToProduct(updateProductRequest, product);

        product = productRepository.save(product);

        return productMapper.productToProductResponse(product);
    }

    @Override
    public ProductResponse createNew(CreateProductRequest createProductRequest) {
        // TODO: write business logic
        // 1. validate categoryId (id exists or not)
        Category category = categoryRepository
                .findById(createProductRequest.categoryId())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "categoryId not found"
                        )
                );
        // 2. Transfer data from DTO to Entity
        Product product = new Product();
        product.setName(createProductRequest.name());
        product.setPrice(createProductRequest.price());
        product.setQty(createProductRequest.qty());
        product.setDescription(createProductRequest.description());
        product.setCategory(category);
        // 3. system data
        product.setCode(GeneratedUtil.generateRandomCode());
        product.setIsAvailable(Boolean.TRUE);
        // 4. Save into database
        product = productRepository.save(product);
        // 5. Tranfer data from Entity to DTO
        return productMapper.productToProductResponse(product);
    }

    @Override
    public ProductResponse getProductByCode(String code) {
        return productRepository.findById(code) // need to map cus it's proResponse not proEntity
                .map(productMapper::productToProductResponse)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product Code Not Found"
                        )
                );
    }

    @Override
    public Page<ProductResponse> getProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return productRepository.findAll(pageable)
                .map(productMapper::productToProductResponse);
    }
}
