package org.example.shopcarcasas.repository;

import org.example.shopcarcasas.dto.entity.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Stock,Long> {
}
