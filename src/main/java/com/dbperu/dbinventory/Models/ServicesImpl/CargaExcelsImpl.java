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
                if (isFirstRow) {
                    isFirstRow = false; 
                    continue;
                }

                CargaExcels cargaExcels = mapRowToCargaExcels(row);

                listaCargaExcels.add(cargaExcels);
            }
        }

        return listaCargaExcels;
    }

    private CargaExcels mapRowToCargaExcels(Row row) {
        if (row == null) {
            return new CargaExcels(); // Retorna un objeto vacío si la fila es nula
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
            case BLANK:
            case _NONE:
                return ""; 
            case ERROR:
                return "Error en la celda";
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
    
    
    /*public List<CargaExcels> cargarDatosDesdeExcel(byte[] archivoExcel) throws Exception {
    	 List<CargaExcels> listaCargaExcels = new ArrayList();
    	 ByteArrayInputStream inputStream = new ByteArrayInputStream(archivoExcel);
    	 Workbook workbook = new XSSFWorkbook(inputStream);


    	 Sheet sheet = workbook.getSheetAt(0);

         boolean isFirstRow = true;

         for (Row row : sheet) {
             if (isFirstRow) {
                 isFirstRow = false;
                 continue;
             }

             CargaExcels cargaExcels = new CargaExcels();

             
             Cell cell1 = row.getCell(1); 
             if (cell1 != null) {
                 switch (cell1.getCellType()) {
                     case STRING:
                         cargaExcels.setAlmacen(cell1.getStringCellValue().trim());
                         break;

                     case NUMERIC:
                         cargaExcels.setAlmacen(String.valueOf((int) cell1.getNumericCellValue()));
                         break;

                     case ERROR:
                         cargaExcels.setAlmacen("Error en la celda");
                         break;
                     
                     case BLANK: 
                     case _NONE:
                     default: 
                         cargaExcels.setAlmacen("");
                         break;
                 }
             } else { 
                 cargaExcels.setAlmacen("");
                 continue;
             }

             
             
             if (row.getCell(2) != null) {
         	    Cell cell = row.getCell(2);

         	    if (cell.getCellType() == CellType.STRING) {
         	        cargaExcels.setSucursal(cell.getStringCellValue());
         	    } else if (cell.getCellType() == CellType.BLANK || cell.getStringCellValue().trim().isEmpty()) {
         	        cargaExcels.setSucursal("");
         	    } else if (cell.getCellType() == CellType.NUMERIC) {
         	        cargaExcels.setSucursal(String.valueOf((int) cell.getNumericCellValue()));
         	    } else {
         	        cargaExcels.setSucursal("");
         	    }
         	} else {
         	    cargaExcels.setSucursal("");
         	}



             if (row.getCell(3) != null) {
            	    Cell cell = row.getCell(3);
            	    switch (cell.getCellType()) {
            	        case STRING:
            	            cargaExcels.setZona(cell.getStringCellValue());
            	            break;
            	        case NUMERIC:
            	            cargaExcels.setZona(String.valueOf((int) cell.getNumericCellValue()));
            	            break;
            	        case BLANK:
            	            cargaExcels.setZona("");
            	            break;
            	        default:
            	            throw new IllegalArgumentException("Tipo de dato no válido en la celda de zona");
            	    }
            	} else {
            	    cargaExcels.setZona("");
            	}


             if (row.getCell(4) != null) {
         	    Cell cell = row.getCell(4);
         	    switch (cell.getCellType()) {
         	        case STRING:
         	            cargaExcels.setPasillo(cell.getStringCellValue());
         	            break;
         	        case NUMERIC:
         	            cargaExcels.setPasillo(String.valueOf((int) cell.getNumericCellValue()));
         	            break;
         	        case BLANK:
         	            cargaExcels.setPasillo("");
         	            break;
         	        default:
         	            throw new IllegalArgumentException("Tipo de dato no válido en la celda de pasillo");
         	    }
         	} else {
         	    cargaExcels.setPasillo("");
         	}
             
             if (row.getCell(5) != null) {
           	    Cell cell = row.getCell(5);
           	    switch (cell.getCellType()) {
           	        case STRING:
           	            cargaExcels.setRack(cell.getStringCellValue());
           	            break;
           	        case NUMERIC:
           	            cargaExcels.setRack(String.valueOf((int) cell.getNumericCellValue()));
           	            break;
           	        case BLANK:
           	            cargaExcels.setRack("");
           	            break;
           	        default:
           	            throw new IllegalArgumentException("Tipo de dato no válido en la celda de es agrupado");
           	    }
           	} else {
           	    cargaExcels.setRack("");
           	}
             
             
             if (row.getCell(6) != null) {
            	    Cell cell = row.getCell(6);
            	    switch (cell.getCellType()) {
            	        case STRING:
            	            cargaExcels.setUbicacion(cell.getStringCellValue());
            	            break;
            	        case NUMERIC:
            	            cargaExcels.setUbicacion(String.valueOf((int) cell.getNumericCellValue()));
            	            break;
            	        case BLANK:
            	            cargaExcels.setUbicacion("");
            	            break;
            	        default:
            	            throw new IllegalArgumentException("Tipo de dato no válido en la celda de es agrupado");
            	    }
            	} else {
            	    cargaExcels.setUbicacion("");
            	}

             //cargaExcels.setRack(row.getCell(5).getStringCellValue());
             //cargaExcels.setUbicacion(row.getCell(6).getStringCellValue());

             if (row.getCell(7) != null) {
          	    Cell cell = row.getCell(7);
          	    switch (cell.getCellType()) {
          	        case STRING:
          	            cargaExcels.setEsagrupado(cell.getStringCellValue());
          	            break;
          	        case NUMERIC:
          	            cargaExcels.setEsagrupado(String.valueOf((int) cell.getNumericCellValue()));
          	            break;
          	        case BLANK:
          	            cargaExcels.setEsagrupado("");
          	            break;
          	        default:
          	            throw new IllegalArgumentException("Tipo de dato no válido en la celda de es agrupado");
          	    }
          	} else {
          	    cargaExcels.setEsagrupado("");
          	}


             if (row.getCell(8) != null) {
           	    Cell cell = row.getCell(8);
           	    switch (cell.getCellType()) {
           	        case STRING:
           	            cargaExcels.setCodigogrupo(cell.getStringCellValue());
           	            break;
           	        case NUMERIC:
           	            cargaExcels.setCodigogrupo(String.valueOf((int) cell.getNumericCellValue()));
           	            break;
           	        case BLANK:
           	            cargaExcels.setCodigogrupo("");
           	            break;
           	        default:
           	            throw new IllegalArgumentException("Tipo de dato no válido en la celda de código grupo");
           	    }
           	} else {
           	    cargaExcels.setCodigogrupo("");
           	}


             if (row.getCell(9) != null) {
            	    Cell cell = row.getCell(9);
            	    switch (cell.getCellType()) {
            	        case STRING:
            	            cargaExcels.setCodigoproducto(cell.getStringCellValue());
            	            break;
            	        case NUMERIC:
            	            cargaExcels.setCodigoproducto(String.valueOf((int) cell.getNumericCellValue()));
            	            break;
            	        case BLANK:
            	            cargaExcels.setCodigoproducto("");
            	            break;
            	        default:
            	            throw new IllegalArgumentException("Tipo de dato no válido en la celda de código producto");
            	    }
            	} else {
            	    cargaExcels.setCodigoproducto("");
            	}


             if (row.getCell(10) != null) {
         	    Cell cell = row.getCell(10);
         	    switch (cell.getCellType()) {
         	        case STRING:
         	            cargaExcels.setCodigobarra(cell.getStringCellValue());
         	            break;
         	        case NUMERIC:
         	            cargaExcels.setCodigobarra(String.valueOf((int) cell.getNumericCellValue()));
         	            break;
         	        case BLANK:
         	            cargaExcels.setCodigobarra("");
         	            break;
         	        default:
         	            throw new IllegalArgumentException("Tipo de dato no válido en la celda de código de barras");
         	    }
         	} else {
         	    cargaExcels.setCodigobarra("");
         	}


             if (row.getCell(11) != null) {
            	    Cell cell = row.getCell(11);

            	    switch (cell.getCellType()) {
            	        case STRING:
            	            cargaExcels.setDescripcionProducto(cell.getStringCellValue().trim());
            	            break;

            	        case NUMERIC:
            	            cargaExcels.setDescripcionProducto(String.valueOf((int) cell.getNumericCellValue()));
            	            break;

            	        case BLANK:
            	        case _NONE:
            	            cargaExcels.setDescripcionProducto("");
            	            break;

            	        default:
            	            cargaExcels.setDescripcionProducto("");
            	            break;
            	    }
            	} else {
            	    cargaExcels.setDescripcionProducto("");
            	}
             

             if (row.getCell(12) != null) {
           	    Cell cell = row.getCell(12);

           	    if (cell.getCellType() == CellType.STRING) {
           	        cargaExcels.setUnidad(cell.getStringCellValue());
           	    } else if (cell.getCellType() == CellType.BLANK || cell.getStringCellValue().trim().isEmpty()) {
           	        cargaExcels.setUnidad("");
           	    } else if (cell.getCellType() == CellType.NUMERIC) {
           	        cargaExcels.setUnidad(String.valueOf((int) cell.getNumericCellValue()));
           	    } else {
           	        cargaExcels.setUnidad("");
           	    }
           	} else {
           	    cargaExcels.setUnidad("");
           	}


             if (row.getCell(13) != null) {
          	    Cell cell = row.getCell(13);
          	    switch (cell.getCellType()) {
          	        case STRING:
          	            cargaExcels.setStockL(cell.getNumericCellValue());
          	            break;
          	        case NUMERIC:
          	        	cargaExcels.setStockL(cell.getNumericCellValue());
          	            break;
          	        case BLANK:
          	            cargaExcels.setStockL(0.0);
          	            break;
          	        default:
          	            throw new IllegalArgumentException("Tipo de dato no válido en la celda de stock L");
          	    }
          	} else {
          	    cargaExcels.setStockL(0.0);
          	}

             if (row.getCell(14) != null) {
           	    Cell cell = row.getCell(14);
           	    switch (cell.getCellType()) {
           	        case STRING:
           	            cargaExcels.setStockF(cell.getNumericCellValue());
           	            break;
           	        case NUMERIC:
           	        	cargaExcels.setStockF(cell.getNumericCellValue());
           	            break;
           	        case BLANK:
           	            cargaExcels.setStockF(0.0);
           	            break;
           	        default:
           	            throw new IllegalArgumentException("Tipo de dato no válido en la celda de stock F");
           	    }
           	} else {
           	    cargaExcels.setStockF(0.0);
           	}


             listaCargaExcels.add(cargaExcels);
         }

         workbook.close();



         return listaCargaExcels;
    }*/
}
