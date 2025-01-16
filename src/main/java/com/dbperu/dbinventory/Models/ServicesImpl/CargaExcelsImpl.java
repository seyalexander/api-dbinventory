package com.dbperu.dbinventory.Models.ServicesImpl;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbperu.dbinventory.Models.Dao.CagarExcelsRepository;
import com.dbperu.dbinventory.Models.Entity.CargaExcels;
import com.dbperu.dbinventory.Models.Services.ICargaExcelsService;

@Service
public class CargaExcelsImpl implements ICargaExcelsService {

    private final CagarExcelsRepository cagarExcelsRepository;

    @Autowired
    public CargaExcelsImpl(CagarExcelsRepository cagarExcelsRepository) {
        this.cagarExcelsRepository = cagarExcelsRepository;
    }

    @Override
    public void saveAll(List<CargaExcels> cargaExcels) {
        cagarExcelsRepository.saveAll(cargaExcels);
    }


    @Override
    public List<CargaExcels> cargarDatosDesdeExcel(byte[] archivoExcel) throws Exception {
        List<CargaExcels> listaCargaExcels = new ArrayList<>();

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(archivoExcel);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            boolean isFirstRow = true;
            
            for (Row row : sheet) {
                if (row == null || row.getLastCellNum() < 15) { 
                    continue;
                }

                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                CargaExcels cargaExcels = mapRowToCargaExcels(row);
                if (cargaExcels != null) {
                    listaCargaExcels.add(cargaExcels);
                }
            }
        }

        return listaCargaExcels;
    }

    private CargaExcels mapRowToCargaExcels(Row row) {
        if (row == null) {
            return null; 
        }

        CargaExcels cargaExcels = new CargaExcels();
        
        boolean isEmptyRow = true;        
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && !getCellValueAsString(cell).trim().isEmpty()) {
                isEmptyRow = false;
                break;
            }
        }
        
        if (isEmptyRow) {
            return null; 
        }

        cargaExcels.setAlmacen(getCellValueAsString(row.getCell(1)));
        cargaExcels.setSucursal(getCellValueAsString(row.getCell(2)));
        cargaExcels.setZona(getCellValueAsString(row.getCell(3)));
        cargaExcels.setPasillo(getCellValueAsString(row.getCell(4)));
        cargaExcels.setRack(getCellValueAsString(row.getCell(5)));
        cargaExcels.setUbicacion(getCellValueAsString(row.getCell(6)));
        cargaExcels.setEsagrupado(getCellValueAsString(row.getCell(7)));
        cargaExcels.setCodigogrupo(getCellValueAsString(row.getCell(8)));
        cargaExcels.setCodigoproducto(getCellValueAsString(row.getCell(9)));
        cargaExcels.setCodigobarra(getCellValueAsString(row.getCell(10)));
        cargaExcels.setDescripcionProducto(getCellValueAsString(row.getCell(11)));
        cargaExcels.setUnidad(getCellValueAsString(row.getCell(12)));
        cargaExcels.setStockL(getCellValueAsDouble(row.getCell(13)));
        cargaExcels.setStockF(getCellValueAsDouble(row.getCell(14)));

        return cargaExcels;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return ""; 
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();  
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());  
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());  
                }
            
            case ERROR:
                return "Error en la celda"; 
                
            case BLANK:
            case _NONE:
            default:
                return ""; 
        }
    }

    private double getCellValueAsDouble(Cell cell) {
        if (cell == null) {
            return 0.0;  
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();  
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue().trim());  
                } catch (NumberFormatException e) {
                    return 0.0; 
                }
            case BLANK:
            case _NONE:
                return 0.0;  
            default:
                return 0.0; 
        }
    }

    
 
}
