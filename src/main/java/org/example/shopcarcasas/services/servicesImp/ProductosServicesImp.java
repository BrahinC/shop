package org.example.shopcarcasas.services.servicesImp;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.shopcarcasas.dto.entity.Producto;
import org.example.shopcarcasas.dto.entity.Stock;
import org.example.shopcarcasas.exeption.MsExepcion;
import org.example.shopcarcasas.repository.ProductosRepository;
import org.example.shopcarcasas.repository.StockRepository;
import org.example.shopcarcasas.services.ProductosServices;
import org.example.shopcarcasas.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductosServicesImp implements ProductosServices  {
    private static final Logger log = LoggerFactory.getLogger(ProductosServices.class);

    @Autowired
    ProductosRepository productosRepository;

    @Autowired
    StockRepository stockRepository;

    @Override
    public void importProductosToExcel(InputStream inputStream) throws MsExepcion {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);

                // Verificar si la fila es nula o está vacía
                if (row == null || Utils.isRowEmpty(row)) {
                    continue; // Ignorar filas vacías
                }

                Producto producto = new Producto();
                Stock stock = new Stock();

                // Leer ID y nombre del producto
                String id = Utils.getStringCellValue(row.getCell(0)).replace("\"", "");
                log.info("Leyendo Producto ID: " + id);
                producto.setId(id);

                String nombreProducto = Utils.getStringCellValue(row.getCell(1)).replace("\"", "");;
                log.info("Leyendo Producto Nombre: " + nombreProducto);
                producto.setNombreProducto(nombreProducto);

                // Leer valores de compra y venta
                Long valorCompra = Utils.getLongCellValue(row.getCell(2));
                log.info("Leyendo Producto Valor Compra: " + valorCompra);
                producto.setValorCompra(valorCompra);

                Long valorVenta = Utils.getLongCellValue(row.getCell(3));
                log.info("Leyendo Producto Valor Venta: " + valorVenta);
                producto.setValorVenta(valorVenta);

                // Salta la foto
                producto.setTienda(Utils.getStringCellValue(row.getCell(5)));
                log.info("Leyendo Producto tienda: " + producto.getTienda());
                producto.setModel(Utils.getStringCellValue(row.getCell(6)).replace("\"", ""));

                // Manejar fecha de compra
                Cell dateCell = row.getCell(7);
                if (dateCell != null) {
                    if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                        producto.setFechaCompra(dateCell.getDateCellValue());
                        log.info("Fecha de compra: " + producto.getFechaCompra());
                    } else {
                        String dateString = Utils.getStringCellValue(dateCell); // Asume formato: dd-MM-yyyy
                        log.info("Fecha en formato texto: " + dateString);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = formatter.parse(dateString);
                        producto.setFechaCompra(date);
                    }
                }

                Optional<Producto> findProducto = productosRepository.findById(producto.getId());
                producto.setTipoProducto(Utils.getStringCellValue(row.getCell(8)).replace("\"", ""));

                if (findProducto.isPresent()) {
                    stock.setId(findProducto.get().getStock().getId());
                    stock.setSock(Utils.getLongCellValue(row.getCell(9)));
                } else {
                    stock.setId(null);
                    stock.setSock(Utils.getLongCellValue(row.getCell(9)));
                }

                producto.setStock(stock);
                producto.setTotalCostoCompra(Utils.getLongCellValue(row.getCell(10)));
                log.info("Leyendo Producto total compra : " +  producto.getTotalCompra());
                producto.setGananciasPorVenta(Utils.getLongCellValue(row.getCell(11)));
                log.info("Leyendo Ganancias Por Venta: " +  producto.getGananciasPorVenta());
                producto.setVentaPorProcentaje(Utils.getLongCellValue(row.getCell(12)));
                log.info("Leyendo Venta Por Procentaje : " +  producto.getVentaPorProcentaje());
                producto.setPorcentajeGananciaa(Utils.getLongCellValue(row.getCell(13)));
                log.info("Leyendo Porcentaje Gananciaa : " +  producto.getPorcentajeGananciaa());
                producto.setGananciasStimadaPorPorcentaje(Utils.getLongCellValue(row.getCell(14)));
                log.info("Leyendo Ganancias Stimada PorPorcentaje : " +  producto.getGananciasStimadaPorPorcentaje());
                producto.setIdProducto(Utils.getStringCellValue(row.getCell(15)));
                log.info("Leyendo Id Producto : " +  producto.getIdProducto());
                producto.setValorPorcentage(false);

                // Guardar producto y stock
                log.info("Producto guardado: " + producto.toString());
                stockRepository.save(stock);
                productosRepository.save(producto);
            }
        } catch (Exception e) {
            log.error("Error en la importación de Excel: " + e.getMessage());
            throw new MsExepcion("Error en la importación: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<Producto> getProducto() {
        return (List<Producto>) productosRepository.findAll();
    }
}
