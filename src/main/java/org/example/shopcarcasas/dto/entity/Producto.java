package org.example.shopcarcasas.dto.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;


@Entity
@Table(name = "productos")
@Data
public class Producto {
    @Id
    @Column(name = "id", nullable = false, length = 300)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stock")
    private Stock stock;

    @Column(name = "nombre_producto", length = 100)
    private String nombreProducto;

    @Column(name = "valor_compra")
    private long valorCompra;

    @Column(name = "valor_venta")
    private long valorVenta;

    @Column(name = "tienda", length = 30)
    private String tienda;

    @Column(name = "model", length = 40)
    private String model;

    @Column(name = "fecha_compra")
    private Date fechaCompra;

    @Column(name = "tipo_producto", length = 40)
    private String tipoProducto;

    @Column(name = "total_compra")
    private long totalCompra;

    @Column(name = "total_costo_compra")
    private long totalCostoCompra;

    @Column(name = "ganancias_por_venta")
    private long gananciasPorVenta;

    @Column(name = "venta_por_procentaje")
    private long ventaPorProcentaje;

    @Column(name = "porcentaje_gananciaa")
    private long porcentajeGananciaa;

    @Column(name = "ganancias_stimada_por_porcentaje")
    private long gananciasStimadaPorPorcentaje;

    @Column(name = "id_producto", length = 100)
    private String idProducto;

    @Column(name = "valor_porcentage")
    private Boolean valorPorcentage;

}