package com.dbperu.dbinventory.Controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbperu.dbinventory.Models.Entity.CargaExcels;
import com.dbperu.dbinventory.Models.Services.ICargaExcelsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"*"})
public class DescargaPlantillaExcelController {

    @Value("${plantilla.path}")
    private String plantillaPath;

    @GetMapping("/descargarPlantilla")
    public ResponseEntity<FileSystemResource> descargarPlantilla() {
        File plantilla = new File(plantillaPath + "dbinventoryPlantilla_v1.xlsx");
        if (!plantilla.exists()) {
            throw new RuntimeException("La plantilla no se encuentra disponible");
        }

        FileSystemResource resource = new FileSystemResource(plantilla);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + plantilla.getName())
                .body(resource);
    }


    @Autowired
    private ICargaExcelsService cargaExcelsService;

    @PostMapping("/cargarExcel")
    public ResponseEntity<Map<String, Object>> cargarExcel(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("rucempresa") String rucempresa,
                                                           @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<CargaExcels> cargaExcelsList = procesarExcel(file, rucempresa, id);

            cargaExcelsService.saveAll(cargaExcelsList);

            response.put("message", "Datos cargados correctamente");
            response.put("count", cargaExcelsList.size());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException | InvalidFormatException e) {
            response.put("message", "Error al procesar el archivo");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<CargaExcels> procesarExcel(MultipartFile file, String rucempresa, Long id) throws IOException, InvalidFormatException {
        List<CargaExcels> cargaExcelsList = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        boolean isFirstRow = true;
        double nroItemCounter = 1;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (isFirstRow) {
                isFirstRow = false;
                continue; 
            }

            CargaExcels cargaExcels = mapRowToCargaExcels(row);
            if (cargaExcels != null) {
                cargaExcels.setRucempresa(rucempresa);
                cargaExcels.setId(id);
                cargaExcels.setNroitem(nroItemCounter++);
                cargaExcelsList.add(cargaExcels);
            }
        }

        workbook.close();
        return cargaExcelsList;
    }
    
    private CargaExcels mapRowToCargaExcels(Row row) {
        if (row == null) {
            return null;
        }

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

        CargaExcels cargaExcels = new CargaExcels();
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
            default:
                return 0.0;
        }
    }
    



}
