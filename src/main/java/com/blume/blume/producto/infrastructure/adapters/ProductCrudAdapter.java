package com.blume.blume.producto.infrastructure.adapters;

import com.blume.blume.producto.domain.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCrudAdapter extends JpaRepository<Producto, Long> {

}
