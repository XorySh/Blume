package com.blume.blume.producto.infrastructure.adapters;

import com.blume.blume.common.exceptions.ResourceNotFoundException;
import com.blume.blume.producto.domain.entities.Producto;
import com.blume.blume.producto.domain.ports.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@AllArgsConstructor
public class ProductAdapter implements ProductRepository {

    private final ProductCrudAdapter crudAdapter;

    @Override
    public Producto createProduct(Producto producto) {
        return this.crudAdapter.save(producto);
    }

    @Override
    public Optional<Producto> updateProduct(Producto producto) {
        if (crudAdapter.existsById(producto.getId())){
            Producto updatedProduct = crudAdapter.save(producto);
            return Optional.of(updatedProduct);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Producto> getProduct(Long id) {
        Producto producto = crudAdapter.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Producto no encontrado")
        );
        return Optional.of(producto);
    }

    @Override
    public List<Producto> getAllProducts() {
        return crudAdapter.findAll();
    }

    @Override
    public Boolean deleteProduct(Long id) {
        if (crudAdapter.existsById(id)){
            crudAdapter.deleteById(id);
            return true;
        }
        return false;
    }
}
