package org.example.shopcarcasas.repository;

import org.example.shopcarcasas.dto.entity.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository  extends CrudRepository<Producto, String> {

}
