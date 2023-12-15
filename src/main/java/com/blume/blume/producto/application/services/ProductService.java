package com.blume.blume.producto.application.services;

import com.blume.blume.producto.domain.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Producto saveProduct(Producto producto);
    Optional<Producto> updateProduct(Producto producto);
    Optional<Producto> getProductById(Long id);
    List<Producto> getAllProducts();
    Boolean deleteProductById(Long id);
}
