package com.blume.blume.producto.domain.ports;

import com.blume.blume.producto.domain.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Producto createProduct(Producto producto);
    Optional<Producto> updateProduct(Producto producto);
    Optional<Producto> getProduct(Long id);
    List<Producto> getAllProducts();
    Boolean deleteProduct(Long id);
}
