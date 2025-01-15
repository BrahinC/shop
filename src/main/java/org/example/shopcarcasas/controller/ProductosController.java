package org.example.shopcarcasas.controller;

import org.example.shopcarcasas.dto.entity.Producto;
import org.example.shopcarcasas.services.ProductosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductosController {
    @Autowired
    ProductosServices services;

    @PostMapping("/exel")
    public ResponseEntity<String> importTower(@RequestParam("file") MultipartFile file ) {
        try {
            services.importProductosToExcel(file.getInputStream());
            return ResponseEntity.ok("Importación exitosa");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la importación");
        }
    }

    @GetMapping
    public  ResponseEntity<List<Producto>> getProducto(){

        List<Producto> responce = services.getProducto();
        return new ResponseEntity<>(responce, HttpStatus.OK);
    }

}
