package com.admin.basic.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author lzx
 * EXCEL导入
 */
public class ImportExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ImportExcelUtil.class);
	
	private int sheetIndex = 0;//sheet下标，默认读取第一个
	private int rowIndex = 1;//默认从第一行读取 
	private int colIndex = 0;//默认从第零列读取
	private String dateFormat = "yyyy-MM-dd";//日期格式
	
	private static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	private static final String OFFICE_EXCEL_2007_POSTFIX = "xlsx";
	
	private final static ObjectMapper mapper = new ObjectMapper(); 
	
	
	/**
	 * 读取Excel
	 * @param filePath 文件路径
	 * @param fldMapping 字段映射{Excel标题名，po字段名，在excel中的列数（0表示第一列）}
	 * @return 返回JSON字符串
	 */
	public String readExcel(String filePath,String[][] fldMapping){
		if(filePath.endsWith(OFFICE_EXCEL_2007_POSTFIX)){
			return readForXlsx07(filePath, fldMapping);
		}
		else if(filePath.endsWith(OFFICE_EXCEL_2003_POSTFIX)){
			return readForXls03(filePath, fldMapping);
		}
		else{
			logger.debug("该文件不是EXCEL格式！");
			return null;
		}
	}
	
	/**
	 * 读取Excel
	 * @param <T> java对象class
	 * @param filePath文件路径
	 * @param fldMapping 字段映射{Excel标题名，po字段名，在excel中的列数（0表示第一列）}
	 * @return 返回List
	 */
	public <T> List<T> readExcel(String filePath,String[][] fldMapping,Class<T> clazz){
		String retResult = null;
		List<T> list = null;
		try {
			if(filePath.endsWith(OFFICE_EXCEL_2007_POSTFIX)){
				retResult = readForXlsx07(filePath, fldMapping);
				JavaType javaType = getCollectionType(ArrayList.class,clazz);
				list = mapper.readValue(retResult, javaType);
			}
			else if(filePath.endsWith(OFFICE_EXCEL_2003_POSTFIX)){
				retResult = readForXls03(filePath, fldMapping);
				JavaType javaType = getCollectionType(ArrayList.class,clazz);
				list = mapper.readValue(retResult, javaType);
			}
			else{
				logger.debug("该文件不是EXCEL格式！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	/**
	 * 读取xls格式 EXCEL
	 */
	private String readForXls03(String filePath,String[][] fldMapping){
		//System.out.println("--------03-------");
		String retResult = null;
		try {
			InputStream in = new FileInputStream(filePath);
			HSSFWorkbook book = new HSSFWorkbook(in);
			HSSFSheet sheet = book.getSheetAt(sheetIndex);
			ArrayNode jsonAry = mapper.createArrayNode();
			//循环行
			for(int rowNum = rowIndex; rowNum <= sheet.getLastRowNum(); rowNum++ ){
				HSSFRow row = sheet.getRow(rowNum);
				ObjectNode jsonObj = mapper.createObjectNode();
				//循环列
				for(int cellNum = colIndex; cellNum < fldMapping.length; cellNum++){
					HSSFCell cell = row.getCell(Integer.valueOf(fldMapping[cellNum][2]));
					jsonObj.put(fldMapping[cellNum][1],getValue(cell));
					
				}
				jsonAry.add(jsonObj);
			}
			retResult = jsonAry.toString();
		} catch (FileNotFoundException e) {
			logger.debug(filePath+" (系统找不到指定的文件)");
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retResult;
	}
	/**
	 * 读取xlsx格式 EXCEL
	 */
	private String readForXlsx07(String filePath,String[][] fldMapping){
		//System.out.println("--------07-------");
		String retResult = null;
		ArrayNode jsonAry = null;
		try {
			InputStream in = new FileInputStream(filePath);
			XSSFWorkbook book = new XSSFWorkbook(in);
			XSSFSheet sheet = book.getSheetAt(sheetIndex);
			jsonAry = mapper.createArrayNode();
			//循环行
			for(int rowNum = rowIndex; rowNum <= sheet.getLastRowNum(); rowNum++ ){
				XSSFRow row = sheet.getRow(rowNum);
				ObjectNode jsonObj = mapper.createObjectNode();
				//循环列
				for(int cellNum = colIndex; cellNum < fldMapping.length; cellNum++){
					XSSFCell cell = row.getCell(Integer.valueOf(fldMapping[cellNum][2]));
					jsonObj.put(fldMapping[cellNum][1],getValue(cell));
				}
				jsonObj.put("rowNum",rowNum);
				jsonAry.add(jsonObj);
			}
			retResult = jsonAry.toString();
		} catch (FileNotFoundException e) {
			logger.debug(filePath+" (系统找不到指定的文件)");
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retResult;
	}
	
	private String getValue(HSSFCell cell){
		if(cell == null){
			return "";
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
			return String.valueOf(cell.getBooleanCellValue());
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				Date date = cell.getDateCellValue();
				DateFormat formater = new SimpleDateFormat(dateFormat);
				return formater.format(date);
			}
			else{
				Double d = cell.getNumericCellValue();
				DecimalFormat df = new DecimalFormat("#.#########");
				return String.valueOf(df.format(d));
			}
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
			Double d = cell.getNumericCellValue();
			DecimalFormat df = new DecimalFormat("#.#########");
			return String.valueOf(df.format(d));
		}
		else{
			return String.valueOf(cell.getStringCellValue());
		}
	}
	
	private String getValue(XSSFCell cell){
		if(cell == null){
			return "";
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
			return String.valueOf(cell.getBooleanCellValue());
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				Date date = cell.getDateCellValue();
				DateFormat formater = new SimpleDateFormat(dateFormat);
				return formater.format(date);
			}
			else{
				Double d = cell.getNumericCellValue();
				DecimalFormat df = new DecimalFormat("#.#########");
				return String.valueOf(df.format(d));
			}
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
			Double d = cell.getNumericCellValue();
			DecimalFormat df = new DecimalFormat("#.#########");
			return String.valueOf(df.format(d));
		}
		else{
			return String.valueOf(cell.getStringCellValue());
		}
	}
	/**
	 * sheet下标
	 */
	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
	/**
	 * 从第几行读取，默认重第一行开始读取（第零行一般是标题）
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	/**
	 * 从第几列读取，默认从第零列开始读取
	 */
	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}
	/**
	 * 设置日期格式,默认格式yyyy-MM-dd
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	/**   
     * 获取泛型的集合 Type  
     * @param collectionClass 泛型的Collection   
     * @param elementClasses 元素类 （集合中元素的类型）  
     * @return JavaType Java类型   
     */   
     private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
         return mapper.getTypeFactory().constructParametrizedType(collectionClass, collectionClass, elementClasses);   
     }  
}
