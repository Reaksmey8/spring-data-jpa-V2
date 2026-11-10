package co.istad.springdatajpa.mapper;

import co.istad.springdatajpa.domain.Product;
import co.istad.springdatajpa.dto.PatchProductRequest;
import co.istad.springdatajpa.dto.ProductResponse;
import co.istad.springdatajpa.dto.UpdateProductRequest;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

//@Component
@Mapper(componentModel = "spring")
public interface ProductMapper {

    // Using mapstruct
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchProductResponseToProduct(PatchProductRequest patchProductRequest,
                                       @MappingTarget Product product);

    void updateProductResponseToProduct(UpdateProductRequest updateProductRequest,
                                        @MappingTarget Product product);

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse productToProductResponse(Product product);


//    public void updateProductResponseToProduct(UpdateProductRequest updateProductRequest, Product product){
//        product.setName(updateProductRequest.name());
//        product.setPrice(updateProductRequest.price());
//        product.setQty(updateProductRequest.qty());
//        product.setDescription(updateProductRequest.description());
//    }

//    public ProductResponse productToProductResponse(Product product){
//
//        return ProductResponse.builder()
//                .code(product.getCode())
//                .name(product.getName())
//                .price(product.getPrice())
//                .qty(product.getQty())
//                .description(product.getDescription())
//                .isAvailable(product.getIsAvailable())
//                .categoryName(product.getCategory().getName())
//                .build();
//    }


}
