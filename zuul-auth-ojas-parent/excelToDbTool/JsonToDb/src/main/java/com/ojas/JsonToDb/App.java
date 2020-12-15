package com.ojas.JsonToDb;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Hello world!
 *
 */
public class App {

	private static boolean validateArgs(String[] args) {

		if (args.length == 2) {
			if ((args[0].endsWith(".xlsx")) || (args[0].endsWith(".xls"))) {
				if (args[1].endsWith(".json")) {
					return true;
				}
			}
		}
		return false;

	}

	public static void parseConfigFile(String xlfile, String configFile) throws IOException, ParseException, SQLException {

		String driver = "com.mysql.cj.jdbc.Driver";
		String DB_URL = "jdbc:mysql://192.168.1.155/";
		String username = "dbubora";
		String password = "Ojas-1525";
		Statement statement = null;
		String str=null;
		try {
			Class.forName(driver);

			Connection connection = DriverManager.getConnection(DB_URL, username, password);

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(configFile));

			JSONObject jsonObject = (JSONObject) obj;
			 str = (String) jsonObject.get("database");
			ArrayList tables = (ArrayList) jsonObject.get("servicetable");

			statement = connection.createStatement();

			String sql = "CREATE DATABASE " + str;

			statement.executeUpdate(sql);
			String sql1 = "use " + str + ";";
			statement.execute(sql1);

			int countTable = 0;
			for (Object table : tables) {
				String s = (String) table;
				ArrayList<String> tableData = (ArrayList<String>) jsonObject.get(s);
				String query = "create table " + table + "(";
				for (String t : tableData) {
					query = query + t+",";
				}
				query = query + ");";
				String end = query.substring(query.length() - 2, query.length());
				String first = query.substring(0, query.length() - 3);
				String createquery = first + end;
				System.out.println(createquery);
				statement.execute(sql1);
				statement.execute(createquery);
				countTable++;

				Object sarr = jsonObject.get("sheetmapping");
				JSONObject jsonObjectSheet = (JSONObject) sarr;
				String sheetName = (String) jsonObjectSheet.get(table);

				XSSFWorkbook xssfWorkbook = parseExcelFile(xlfile);
				XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);
				
				Row row;
				int lastCellNum =0;
				for(int i=0;i<1;i++) {
					row=sheet.getRow(i);
					lastCellNum = row.getLastCellNum();
				}
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {

					row = sheet.getRow(i);
					//int cellCount = row.getPhysicalNumberOfCells();
					
					ArrayList cellList = new ArrayList();
					for (int c = 0; c <lastCellNum; c++) {

						XSSFCell cell = (XSSFCell) row.getCell(c);

						 if (cell==null||cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {

							cellList.add(null + ",");

						}
						 else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {

							String value = cell.getStringCellValue();
							// cellList.add("'" + value + "',");

							String lowerCase = value.toLowerCase();

							String valuefromjson = (String) jsonObject.get(lowerCase);
							if (valuefromjson == null) {
								valuefromjson = value;
							}
							// if(lastCellNum>=2) {
							Pattern pattern = Pattern.compile(".*[^0-9].*");
							Pattern pwdPattern = Pattern.compile("/^[ A-Za-z0-9_@./#&+-]*$/");
							System.out.println(!pattern.matcher(valuefromjson).matches());
							if (!pattern.matcher(valuefromjson).matches()) {
								Long intvalue = Long.parseLong(valuefromjson);
								cellList.add(intvalue + ",");
								// }
							} else if (!pwdPattern.matcher(valuefromjson).matches()) {
								cellList.add("'" + valuefromjson + "',");
							} else {
								cellList.add("'" + cell.getStringCellValue() + "',");
							}
						}

						else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
							double numericCellValue = cell.getNumericCellValue();
					
							int n=(int)numericCellValue;
							String object = (String)jsonObject.get(""+n);
							//String expe = xssfWorkbook.getNameAt(c).toString();
							
							
							if(object!=null) {
								cellList.add(object+",");
							}
							else if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
								cellList.add("'" + dateFormat.format(cell.getDateCellValue()) + "',");
							}
							else {
//								if(expe.equalsIgnoreCase("experience")) {
//									cellList.add(numericCellValue);
//								}
								long intvalue = (long) (cell.getNumericCellValue());
								//Double intvalue = (Double)(cell.getNumericCellValue());
								cellList.add(intvalue + ",");
								// cellList.add(cell.getNumericCellValue() + ",");
							}

						} 

						else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
							if (cell.getBooleanCellValue())
								cellList.add(1 + ",");
							else
								cellList.add(0 + ",");
						}

						else {

						}
					}

					String insertQuery = "insert into " + table + " values(";
					for (Object object3 : cellList) {
						insertQuery = insertQuery + object3;

					}
					statement.execute(sql1);
					String insertEnd = ");";
					insertQuery = insertQuery + insertEnd;
					String ss = insertQuery.substring(insertQuery.length() - 2, insertQuery.length());
					String dd = insertQuery.substring(0, insertQuery.length() - 3);
					String ff = dd + ss;
					System.out.println(ff);
					statement.execute(ff);
				}

				System.out.println("Inserted." + countTable + "Tables And Data");

			}

		} catch (Exception e) {
			//statement.execute("drop schema "+str);
			// TODO: handle exception
			e.printStackTrace();
			
		}
		parse(xlfile, configFile);
	}

	public static void parse(String xlfile, String configFile) throws IOException, ParseException {

	
		String driver = "com.mysql.cj.jdbc.Driver";
		String DB_URL = "jdbc:mysql://192.168.155/";

		String username = "dbubora";
		String password = "Ojas-1525";
		Statement statement = null;
		try {
			Class.forName(driver);

			Connection connection = DriverManager.getConnection(DB_URL, username, password);

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(configFile));

			JSONObject jsonObject = (JSONObject) obj;
			String str = (String) jsonObject.get("database");
			ArrayList tables = (ArrayList) jsonObject.get("mastertable");

			statement = connection.createStatement();

			String sql1 = "use " + str + ";";
			statement.execute(sql1);

			int countTable = 0;
			for (Object table : tables) {
				String s = (String) table;
				ArrayList<String> tableData = (ArrayList<String>) jsonObject.get(s);
				String query = "create table " + table + "(";
				for (String t : tableData) {
					query = query +t+",";
				}
				query = query + ");";
				
				String end = query.substring(query.length() - 2, query.length());
				String first = query.substring(0, query.length() - 3);
				String createquery = first + end;
				System.out.println(createquery);
				
				statement.execute(sql1);
				statement.execute(createquery);
				countTable++;

				Object sarr = jsonObject.get("sheetmapping");
				JSONObject jsonObjectSheet = (JSONObject) sarr;
				String sheetName = (String) jsonObjectSheet.get(table);

				XSSFWorkbook xssfWorkbook = parseExcelFile(xlfile);
				XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);

				Row row;

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {

					row = sheet.getRow(i);
					int cellCount = row.getPhysicalNumberOfCells();
					int lastCellNum = row.getLastCellNum();
					ArrayList cellList = new ArrayList();
					for (int c = 0; c <= cellCount - 1; c++) {

						XSSFCell cell = (XSSFCell) row.getCell(c);

						if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {

							cellList.add("'" + cell.getStringCellValue() + "',");

						}

						else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
							

							if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
								cellList.add("'" + dateFormat.format(cell.getDateCellValue()) + "',");
							} else {
								long intvalue = (long) (cell.getNumericCellValue());
								cellList.add(intvalue + ",");
								// cellList.add(cell.getNumericCellValue() + ",");
							}

						} else if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {

							cellList.add(null + ",");

						}

						else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
							if (cell.getBooleanCellValue())
								cellList.add(1 + ",");
							else
								cellList.add(0 + ",");
						}

						else {

						}
					}

					String insertQuery = "insert into " + table + " values(";
					for (Object object3 : cellList) {
						insertQuery = insertQuery + object3;

					}
					statement.execute(sql1);
					String insertEnd = ");";
					insertQuery = insertQuery + insertEnd;
					String ss = insertQuery.substring(insertQuery.length() - 2, insertQuery.length());
					String dd = insertQuery.substring(0, insertQuery.length() - 3);
					String ff = dd + ss;
					System.out.println(ff);
					statement.execute(ff);
				}

				System.out.println("Inserted." + countTable + "Tables And Data");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static XSSFWorkbook parseExcelFile(String xlfile) {
		XSSFWorkbook wb = null;
		try {

			FileInputStream input = new FileInputStream(xlfile);
			wb = new XSSFWorkbook(input);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return wb;
	}

	public static void main(String[] args) throws IOException, ParseException, SQLException {
		System.out.println("main");

		if (!validateArgs(args)) {

			return;
		}

		// Read the json file
		parseConfigFile(args[0], args[1]); // json file

	}



}
