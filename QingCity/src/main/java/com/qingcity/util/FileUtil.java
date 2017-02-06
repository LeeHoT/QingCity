package com.qingcity.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author leehotin
 * @Date 2017年2月6日 下午6:00:48
 * @Description Excel工具类，没有硬编码，修改时不要添加硬编码代码
 */
public class FileUtil {

	private static final String JSON_PATH = "src/main/resources/config/properties/json/";
	private static final String EXCEL_PATH = "src/main/resources/config/properties/excel/";

	public static String getJsonPath() {
		return JSON_PATH;
	}

	public static String getExcelPath() {
		return EXCEL_PATH;
	}

	private FileUtil() {
	};

	/**
	 * 读取文件内容，，主要用于读取json文件
	 * 
	 * @param Path
	 *            文件路径
	 * @return
	 */
	public static String ReadFile(String path) {
		BufferedReader reader = null;
		String laststr = "";
		try (FileInputStream fileInputStream = new FileInputStream(path);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8")) {
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(laststr);
		return laststr;
	}

	/**
	 * 根据文件格式类型，创建不同的文件 xls 格式的为 excel 2003 xlsx 格式的为 excel 2007 版本以上的
	 * 
	 * @param filename
	 *            带有后缀的文件名
	 * @return 2003/2007版本的WorkBook
	 */
	public static Workbook readWorkBook(String filename) {
		String fileType = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
		// Excel列的样式，主要是为了解决Excel数字科学计数的问题
		CellStyle cellStyle;
		Workbook wb = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(EXCEL_PATH + filename);
			if ("xls".equals(fileType)) {
				wb = new HSSFWorkbook(fis);// excel 2003
				HSSFDataFormat dataFormat = (HSSFDataFormat) wb.createDataFormat();
				cellStyle = wb.createCellStyle();
				// 设置Excel列的样式为文本
				cellStyle.setDataFormat(dataFormat.getFormat("@"));
				System.out.println("2003");
			} else if ("xlsx".equals(fileType)) {
				wb = new XSSFWorkbook(fis);// excel 2007+
				XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
				cellStyle = wb.createCellStyle();
				// 设置Excel列的样式为文本
				cellStyle.setDataFormat(dataFormat.getFormat("@"));
				System.out.println("2007");
			} else {
				throw new Exception("读取的不是excel文件");
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("没有找到该路径: " + filename + " 下的文件");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return wb;
	}

	/**
	 * 创建一个2007版本的excel表格
	 * 
	 * @param filename
	 * @return 返回2003或者2007版本的WorkBook
	 */
	public static Workbook createWorkBook(String filename) {
		FileOutputStream fos = null;
		Workbook wb = null;
		try {
			fos = new FileOutputStream("src/main/resources/config/properties/excel/" + filename);
			wb = new XSSFWorkbook();
			wb.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return wb;
	}

	/**
	 * 读取excel 表格中的内容，
	 * 
	 * @param filename
	 *            excel 文件存储路径 ，绝对路径
	 * @return 返回 hashMap key 值为sheet表格的名字 value 为每个sheet表格中的所有数据, 以json字符串的形式保存
	 * @throws IOException
	 */
	public static LinkedHashMap<String, String> readExcel(String filename) throws IOException {
		// 返回的map
		LinkedHashMap<String, String> excelMap = new LinkedHashMap<>();
		Workbook wb = readWorkBook(filename);
		// sheet表个数
		int sheetsCounts = wb.getNumberOfSheets();
		// 遍历每一个sheet
		for (int i = 0; i < sheetsCounts; i++) {
			Sheet sheet = wb.getSheetAt(i);
			// 一个sheet表对于一个List
			LinkedList<Object> list = new LinkedList<Object>();
			// 将第一行的列值作为整个json的key
			String[] cellNames;
			// 取第一行列的值作为key
			Row fisrtRow = sheet.getRow(0);
			// 如果第一行就为空，则是空sheet表，该表跳过
			if (null == fisrtRow) {
				continue;
			}
			// 得到第一行有多少列
			int curCellNum = fisrtRow.getLastCellNum();
			// 根据第一行的列数来生成列头数组
			cellNames = new String[curCellNum];
			// 单独处理第一行，取出第一行的每个列值放在数组中，就得到了整张表的JSON的key
			for (int m = 0; m < curCellNum; m++) {
				Cell cell = fisrtRow.getCell(m);
				// 设置该列的样式是字符串
				// cell.setCellStyle(cellStyle);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				// 取得该列的字符串值
				cellNames[m] = cell.getStringCellValue();
			}
			System.out.println();
			// 从第二行起遍历每一行
			int rowNum = sheet.getLastRowNum();
			for (int j = 1; j <= rowNum; j++) {
				// 一行数据对于一个Map
				LinkedHashMap<String, Object> rowMap = new LinkedHashMap<String, Object>();
				// 取得某一行
				Row row = sheet.getRow(j);
				int cellNum = row.getLastCellNum();
				// 遍历每一列
				for (int k = 0; k < cellNum; k++) {
					Cell cell = row.getCell(k);
					// 保存该单元格的数据到该行中
					rowMap.put(cellNames[k], getCellValue(cell));
				}
				// 保存该行的数据到该表的List中
				list.add(rowMap);
			}
			// 将该sheet表的表名为key，List转为json后的字符串为Value进行存储
			Gson gson = new Gson();
			excelMap.put(sheet.getSheetName(), gson.toJson(list));

		}
		return excelMap;
	}

	/**
	 * 获取excel 单元格中的内容
	 * 
	 * @param cell
	 *            单元格对象
	 * @return 返回当前单元格里的内容。并转换成字符串的形式
	 */
	private static String getCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) { // 日期类型
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
					value = sdf.format(date);
				} else {
					Integer data = (int) cell.getNumericCellValue();
					value = data.toString();
				}
				break;
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				Boolean data = cell.getBooleanCellValue();
				value = data.toString();
				break;
			case Cell.CELL_TYPE_ERROR:
				System.out.println("单元格内容出现错误");
				break;
			case Cell.CELL_TYPE_FORMULA:
				value = String.valueOf(cell.getNumericCellValue());
				if (value.equals("NaN")) {// 如果获取的数据值非法,就将其装换为对应的字符串
					value = cell.getStringCellValue().toString();
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				System.out.println("单元格内容 为空值 ");
				break;
			default:
				value = cell.getStringCellValue().toString();
				break;
			}
		}
		return value;
	}

	/**
	 * 将json 字符串写入文件 写入是未换行。所有数据均在一行。手动阅读不方便
	 * 
	 * @param str
	 *            json 字符串
	 * @param fileName
	 *            新建文件的文件名，含有后缀
	 * @throws IOException
	 */
	public static void string2JsonFile(String str, String fileName) throws IOException {
		String path = JSON_PATH + fileName;
		try (OutputStream os = new FileOutputStream(path);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"))) {
			bw.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

}