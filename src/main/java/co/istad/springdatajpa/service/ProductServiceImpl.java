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
public class ProductServiceImpl implements ProductService {
    // inject dependency
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse getProductByCode(String code) {

        return productRepository.findById(code)
                .map(productMapper::productToProductResponse)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product code not found"
                        )
                );
    }

    @Override
    public Page<ProductResponse> getAllProducts(int pageNumber, int pageSize) {
        if (pageSize <= 0) {
            pageSize = 10;
        }
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return productRepository.findAll(pageable)
                .map(productMapper::productToProductResponse);
    }

    @Override
    public ProductResponse createNewProduct(CreateProductRequest createProductRequest) {
        // TODO:
        // 1. Validate categoryId (exists or not)
        Category category = categoryRepository.findById(createProductRequest.categoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "CategoryId not found"
                ));
        // 2. transfer data from DTO to Entity
        Product product = new Product();
        product.setName(createProductRequest.name());
        product.setPrice(createProductRequest.price());
        product.setQty(createProductRequest.qty());
        product.setDescription(createProductRequest.description());
        product.setCategory(category);
        // 3. System data
        product.setCode(GeneratedUtil.generateRandomCode());
        product.setIsAvailable(Boolean.TRUE);
        // 4. Save into database
        product = productRepository.save(product);
        // 5. Transfer data from Entity to DTO
        return productMapper.productToProductResponse(product);
    }

    @Override
    public void deleteProductByCode(String code) {
        Product product = productRepository.findById(code)
                .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Product code not found"
                    )
                );
        productRepository.delete(product);
    }

    @Override
    public ProductResponse updateProductByCode(UpdateProductRequest updateProductRequest, String code) {
        // TODO:
        //  1. check code(exists or not) 2. convert updateResToProductEntity 3. save into(convert Entity to response)
        Product product = productRepository.findById(code)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product code not found"
                        )
                );

        productMapper.updateProductResponseToProduct(updateProductRequest, product);

        product = productRepository.save(product);
        return productMapper.productToProductResponse(product);
//        return productMapper.productToProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse patchByCode(PatchProductRequest patchProductRequest, String code) {
        Product product = productRepository.findById(code)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Product code not found"
                        )
                );
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
}