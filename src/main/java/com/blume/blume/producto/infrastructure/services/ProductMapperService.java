package com.blume.blume.producto.infrastructure.services;

import com.blume.blume.common.exceptions.ResourceNotFoundException;
import com.blume.blume.producto.application.services.ProductService;
import com.blume.blume.producto.domain.entities.Producto;
import com.blume.blume.producto.infrastructure.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductMapperService {

    private final ProductService productService;
    private final ModelMapper modelMapper = new ModelMapper();
    private static final String PRODUCT_NOT_FOUND = "No se pudo encontrar el producto";

    public ProductDTO saveProductDTO(ProductDTO productDTO){
        try{
            Producto producto = new Producto();
            modelMapper.map(productDTO, producto);
            Producto savedProduct = this.productService.saveProduct(producto);
            ProductDTO savedDTO = new ProductDTO();
            modelMapper.map(savedProduct,savedDTO);
            return savedDTO;
        }catch (Exception e){
            throw new ResourceNotFoundException("No se pudo guardar el producto");
        }

    }

    public ProductDTO getProductDTO(Long id) {
        if ( id != null ){
            Optional<Producto> producto = this.productService.getProductById(id);
            if (producto.isPresent()){
                Producto existingProduct = producto.get();
                return modelMapper.map(existingProduct, ProductDTO.class);
            }
            throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
        }
        throw new ResourceNotFoundException("El producto no existe");
    }

    public List<ProductDTO> getAllProductsDTO(){
        try{
            List<Producto> productos = this.productService.getAllProducts();

            List<ProductDTO> productDTOS = new ArrayList<>();
            for (Producto producto : productos){
                ProductDTO productDTO = new ProductDTO();
                modelMapper.map(producto, productDTO);
                productDTOS.add(productDTO);
            }
            return productDTOS;
        }catch (Exception e){
            throw new ResourceNotFoundException("No se pudo encontrar productos");
        }
    }

    public Boolean deleteProduct(Long id) {
        if (id != null){
            Optional<Producto> producto = this.productService.getProductById(id);

            if (producto.isPresent()){
                try{
                    return this.productService.deleteProductById(id);
                }catch (Exception e){
                    throw new ResourceNotFoundException("No se pudo eliminar el producto");
                }
            }
        }
        throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
    }

    public ProductDTO updateProductDTO(ProductDTO productDTO) {
        if (productDTO.getNombre() != null){
            Producto producto = new Producto();
            modelMapper.map(productDTO, producto);
            Optional<Producto> updatedProduct = this.productService.updateProduct(producto);
            if (updatedProduct.isPresent()){
                ProductDTO updatedDTO = new ProductDTO();
                modelMapper.map(updatedProduct.get(), updatedDTO);
                return updatedDTO;
            }

        }
        throw new ResourceNotFoundException("No se puede actualizar el producto");
    }


}
