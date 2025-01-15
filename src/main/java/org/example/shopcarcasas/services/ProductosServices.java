package org.example.shopcarcasas.services;

import org.example.shopcarcasas.dto.entity.Producto;
import org.example.shopcarcasas.exeption.MsExepcion;

import java.io.InputStream;
import java.util.List;

public interface ProductosServices {
    void importProductosToExcel(InputStream inputStream) throws MsExepcion;

    List<Producto> getProducto();
}
