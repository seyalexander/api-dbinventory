package com.dbperu.dbinventory.Controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
                                                           @RequestParam("id") Long id) throws IOException, InvalidFormatException {
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
        rowIterator.next();

        double nroItemCounter = 1;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            CargaExcels cargaExcels = new CargaExcels();

            cargaExcels.setRucempresa(rucempresa);
            cargaExcels.setId(id);
            cargaExcels.setNroitem(nroItemCounter++);

            cargaExcels.setAlmacen(row.getCell(1).getStringCellValue());
            cargaExcels.setSucursal(row.getCell(2).getStringCellValue());

            if (row.getCell(3) != null) {
                Cell cell = row.getCell(3);
                if (cell.getCellType() == CellType.STRING) {
                    cargaExcels.setZona(cell.getStringCellValue());
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    cargaExcels.setZona(String.valueOf((int) cell.getNumericCellValue()));
                } else {
                    cargaExcels.setZona("");
                }
            }

            if (row.getCell(4) != null) {
                Cell cell = row.getCell(4);
                if (cell.getCellType() == CellType.STRING) {
                    cargaExcels.setPasillo(cell.getStringCellValue());
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    cargaExcels.setPasillo(String.valueOf((int) cell.getNumericCellValue()));
                } else {
                    cargaExcels.setPasillo("");
                }
            }

            cargaExcels.setRack(row.getCell(5).getStringCellValue());
            cargaExcels.setUbicacion(row.getCell(6).getStringCellValue());

            if (row.getCell(7) != null) {
                Cell cell = row.getCell(7);
                if (cell.getCellType() == CellType.STRING) {
                    cargaExcels.setEsagrupado(cell.getStringCellValue());
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    cargaExcels.setEsagrupado(String.valueOf((int) cell.getNumericCellValue()));
                } else {
                    cargaExcels.setEsagrupado("");
                }
            }

            if (row.getCell(8) != null) {
                Cell cell = row.getCell(8);
                if (cell.getCellType() == CellType.STRING) {
                    cargaExcels.setCodigogrupo(cell.getStringCellValue());
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    cargaExcels.setCodigogrupo(String.valueOf((int) cell.getNumericCellValue()));
                } else {
                    cargaExcels.setCodigogrupo("");
                }
            }

            if (row.getCell(9) != null) {
                Cell cell = row.getCell(9);
                if (cell.getCellType() == CellType.STRING) {
                    cargaExcels.setCodigoproducto(cell.getStringCellValue());
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    cargaExcels.setCodigoproducto(String.valueOf((int) cell.getNumericCellValue()));
                } else {
                    cargaExcels.setCodigoproducto("");
                }
            }

            if (row.getCell(10) != null) {
                Cell cell = row.getCell(10);
                if (cell.getCellType() == CellType.STRING) {
                    cargaExcels.setCodigobarra(cell.getStringCellValue());
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    cargaExcels.setCodigobarra(String.valueOf((int) cell.getNumericCellValue()));
                } else {
                    cargaExcels.setCodigobarra("");
                }
            }

            cargaExcels.setDescripcionProducto(row.getCell(11).getStringCellValue());
            cargaExcels.setUnidad(row.getCell(12).getStringCellValue());

            if (row.getCell(13) != null) {
                Cell cell = row.getCell(13);
                if (cell.getCellType() == CellType.STRING || cell.getCellType() == CellType.NUMERIC) {
                    cargaExcels.setStockL(cell.getNumericCellValue());
                } else {
                    cargaExcels.setStockL(0.0);
                }
            }

            if (row.getCell(14) != null) {
                Cell cell = row.getCell(14);
                if (cell.getCellType() == CellType.STRING || cell.getCellType() == CellType.NUMERIC) {
                    cargaExcels.setStockF(cell.getNumericCellValue());
                } else {
                    cargaExcels.setStockF(0.0);
                }
            }

            // Agregar el objeto a la lista
            cargaExcelsList.add(cargaExcels);
        }

        workbook.close();
        return cargaExcelsList;
    }


}
