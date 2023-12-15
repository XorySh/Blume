package com.blume.blume.producto.infrastructure.controllers;

import com.blume.blume.producto.infrastructure.dto.ProductDTO;
import com.blume.blume.producto.infrastructure.services.ProductMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blume")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductMapperService productMapperService;


    /**
     * Crear un producto
     */
    @PostMapping("/management/products/create")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO){
        return productMapperService.saveProductDTO(productDTO);
    }

    /**
     * Obtener un Producto por Id
     */
    @GetMapping("/products/{productId}")
    public ProductDTO getProductById(@PathVariable Long productId) {
        return productMapperService.getProductDTO(productId);
    }

    /**
     * Obtener todos los productos
     */
    @GetMapping("/products/all")
    public List<ProductDTO> getAllProducts(){
        return productMapperService.getAllProductsDTO();
    }

    /**
     * Eliminar un producto
     */
    @DeleteMapping("/management/products/delete/{id}")
    public Boolean deleteProductById(@PathVariable Long id){
        return productMapperService.deleteProduct(id);
    }

    /**
     * Actualizar campos de un producto
     */
    @PatchMapping("/management/products/{id}")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO){
        return productMapperService.updateProductDTO(productDTO);
    }

}
