package com.blume.blume.producto.application.services;

import com.blume.blume.producto.domain.entities.Producto;
import com.blume.blume.producto.domain.ports.ProductRepository;

import java.util.List;
import java.util.Optional;

public record ProductServiceImpl(ProductRepository productRepository) implements ProductService {

    @Override
    public Producto saveProduct(Producto producto) {
        return this.productRepository.createProduct(producto);
    }

    @Override
    public Optional<Producto> updateProduct(Producto producto) {
        return this.productRepository.updateProduct(producto);
    }

    @Override
    public Optional<Producto> getProductById(Long id) {
        return this.productRepository.getProduct(id);
    }

    @Override
    public List<Producto> getAllProducts() {
        return this.productRepository.getAllProducts();
    }

    @Override
    public Boolean deleteProductById(Long id) {
        return this.productRepository.deleteProduct(id);
    }
}
