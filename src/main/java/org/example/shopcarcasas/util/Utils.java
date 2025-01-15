package org.example.shopcarcasas.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    private Utils() {
        super();
    }

    // Método auxiliar para verificar si una fila está vacía
    public static boolean isRowEmpty(Row row) {
        for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
            Cell cell = row.getCell(cellIndex);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false; // La fila tiene al menos una celda no vacía
            }
        }
        return true; // Todas las celdas están vacías
    }

    public static String getStringCellValue(Cell cell) {
        if (cell == null) {
            log.warn("Celda vacía encontrada");
            return "";
        }
        log.info("Tipo de celda: " + cell.getCellType());
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case FORMULA:
                // Evaluar la fórmula y devolver el resultado como texto
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                return    String.valueOf(evaluator.evaluate(cell).formatAsString());
            default:
                return "";
        }
    }

    public static BigDecimal getBigDecimalCellValue(Cell cell) {
        if (cell == null) {
            return BigDecimal.ZERO;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return BigDecimal.valueOf(cell.getNumericCellValue());
            case STRING:
                try {
                    return new BigDecimal(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    log.warn("Valor no numérico encontrado en la celda: " + cell.getStringCellValue());
                    return BigDecimal.ZERO;
                }

            case FORMULA:
                // Evaluar la fórmula y devolver el resultado como texto
                FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                return new  BigDecimal (evaluator.evaluate(cell).formatAsString());

            case BLANK:
                return BigDecimal.ZERO;

            default:
                return BigDecimal.ZERO;
        }
    }


    public static Long getLongCellValue(Cell cell) {
        if (cell == null) {
            return 0L;
        }
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return (long) cell.getNumericCellValue();
                case STRING:
                      // Elimina símbolos de moneda y puntos, y convierte a Long
                            String stringValue = cell.getStringCellValue()
                        .replace("$", "") // Eliminar símbolo de moneda
                        .replace(".", "") // Eliminar separador de miles
                        .trim();
                return Long.parseLong(stringValue);
                case FORMULA:
                    FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    CellValue cellValue = evaluator.evaluate(cell);
                    if (cellValue.getCellType() == CellType.NUMERIC) {
                        return (long) cellValue.getNumberValue();
                    } else if (cellValue.getCellType() == CellType.STRING) {
                        return Long.parseLong(cellValue.getStringValue().split("\\.")[0]);
                    } else {
                        return 0L;
                    }
                default:
                    return 0L;
            }
        } catch (Exception e) {
            log.warn("Error al convertir la celda a Long: " + e.getMessage());
            return 0L;
        }
    }

}
