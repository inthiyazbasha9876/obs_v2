package com.ojas.obs.facadeimpl;

import static com.ojas.obs.constants.UtilConstants.SAVE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ojas.obs.facade.AppFacade;
import com.ojas.obs.request.AppRequest;
import com.ojas.obs.response.AppResponse;

@Service
public class AppFacadeImpl implements AppFacade {

	

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private Environment env;
	@Override
	public ResponseEntity<Object> setCustomer(AppRequest appRequest) throws  IOException, ParseException {

		AppResponse response=null;

		 //App applist = appRequest.getApplist();
		String str=null;
		
		
		
		if(appRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
			response=new AppResponse();
			
			
//			String fileName = "sample.txt";
//			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//			 
//			File file = new File(classLoader.getResource(fileName).getFile());
//			 
//			String content = new String(Files.readAllBytes(file.toPath()));
		
//			 ClassLoader classLoader = this.getClass().getClassLoader();
//			   File configFile=new File(classLoader.getResource("db.json").getFile());
//			  
//			   FileInputStream   inputStream = new FileInputStream(configFile);
//			   BufferedReader readerfile = new BufferedReader(new InputStreamReader(inputStream));
//			   String jsonfile= readerfile.toString();
			//byte[] bFile = Files.readAllBytes(Paths.get("//datatodb//src//main//resources//db.json"));
			//File file = new File("db.json");
	        String fileName = "db.json";
	        ClassLoader classLoader = new AppFacadeImpl().getClass().getClassLoader();
	 
	        File file = new File(classLoader.getResource(fileName).getFile());
	        
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(file);
			Object obj = parser.parse(reader);
			
			//Object obj = parser.parse(new FileReader(env.getProperty("config_path")));
			
			
			

			JSONObject jsonObject = (JSONObject) obj;
			 str = (String) jsonObject.get("database");
			ArrayList tables = (ArrayList) jsonObject.get("servicetable");
			
			String sql1 = "use " + str + ";";
			jdbcTemplate.execute(sql1);
			
			int countTable = 0;
			for (Object table : tables) {
				 countTable++;
				Object sarr = jsonObject.get("sheetmapping");
				JSONObject jsonObjectSheet = (JSONObject) sarr;
				String sheetName = (String) jsonObjectSheet.get(table);
				XSSFWorkbook xssfWorkbook = parseExcelFile(env.getProperty("file_excelfilename"));
				XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);
				
				
				
				Row row;
				int lastCellNum =0;
				for(int i=0;i<1;i++) {
					row=sheet.getRow(i);
					lastCellNum = row.getLastCellNum();
				}
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {

					row = sheet.getRow(i);

					
					ArrayList cellList = new ArrayList();
					for (int c = 0; c <lastCellNum; c++) {

						XSSFCell cell = (XSSFCell) row.getCell(c);

						 if (cell==null||cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {

							cellList.add(null + ",");

						}
						 else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {

							String value = cell.getStringCellValue();


							String lowerCase = value.toLowerCase();

							String valuefromjson = (String) jsonObject.get(lowerCase);
							if (valuefromjson == null) {
								valuefromjson = value;
							}

							Pattern pattern = Pattern.compile(".*[^0-9].*");
							Pattern pwdPattern = Pattern.compile("/^[ A-Za-z0-9_@./#&+-]*$/");
							System.out.println(!pattern.matcher(valuefromjson).matches());
							if (!pattern.matcher(valuefromjson).matches()) {
								Long intvalue = Long.parseLong(valuefromjson);
								cellList.add(intvalue + ",");

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

							
							
							if(object!=null) {
								cellList.add(object+",");
							}
							else if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
								cellList.add("'" + dateFormat.format(cell.getDateCellValue()) + "',");
							}
							else {

								long intvalue = (long) (cell.getNumericCellValue());

								cellList.add(intvalue + ",");

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
					jdbcTemplate.execute(sql1);
					String insertEnd = ");";
					insertQuery = insertQuery + insertEnd;
					String ss = insertQuery.substring(insertQuery.length() - 2, insertQuery.length());
					String dd = insertQuery.substring(0, insertQuery.length() - 3);
					String ff = dd + ss;
					System.out.println(ff);
					jdbcTemplate.execute(ff);
				}
				System.out.println("Inserted." + countTable + "Tables And Data");
				xssfWorkbook.close();
		}
			
			ArrayList tablesm = (ArrayList) jsonObject.get("mastertable");

			int countTablem = 0;
			for (Object tablemone : tablesm) {
				countTablem++;
				Object sarrm = jsonObject.get("sheetmapping");
				JSONObject jsonObjectSheetm = (JSONObject) sarrm;
				String sheetNamem = (String) jsonObjectSheetm.get(tablemone);

				XSSFWorkbook xssfWorkbookm = parseExcelFile(env.getProperty("file_excelfilename"));
				XSSFSheet sheetm = xssfWorkbookm.getSheet(sheetNamem);
				
				Row rowm;

				for (int i = 1; i <= sheetm.getLastRowNum(); i++) {

					rowm = sheetm.getRow(i);
					int cellCount = rowm.getPhysicalNumberOfCells();
					int lastCellNumm = rowm.getLastCellNum();
					ArrayList cellList = new ArrayList();
					for (int c = 0; c <= cellCount - 1; c++) {

						XSSFCell cell = (XSSFCell) rowm.getCell(c);

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

					String insertQuery = "insert into " + tablemone + " values(";
					for (Object object3 : cellList) {
						insertQuery = insertQuery + object3;

					}
					jdbcTemplate.execute(sql1);
					String insertEnd = ");";
					insertQuery = insertQuery + insertEnd;
					String ss = insertQuery.substring(insertQuery.length() - 2, insertQuery.length());
					String dd = insertQuery.substring(0, insertQuery.length() - 3);
					String ff = dd + ss;
					System.out.println(ff);
					jdbcTemplate.execute(ff);
					
				}

				System.out.println("Inserted." + countTablem + "Tables And Data");
				xssfWorkbookm.close();
				
			}
			reader.close();

		}
		String property = env.getProperty("file_excelfilename");
		Path excelfilepath = Paths.get(property);
		System.out.println("File exists : " + Files.exists(excelfilepath));
		Files.deleteIfExists(excelfilepath);
		
//		String property2 = env.getProperty("file_configfilename");
//		Path configfilepath = Paths.get(property2);
//		System.out.println("File exists : " + Files.exists(configfilepath));
//		Files.deleteIfExists(configfilepath);
		
		response.setMessage("Data dumped successfully");
		response.setStatusCode("200");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	public XSSFWorkbook parseExcelFile(String xlfile) {
		XSSFWorkbook wb = null;
		try {

			FileInputStream input = new FileInputStream(xlfile);
			wb = new XSSFWorkbook(input);
			input.close();
		} catch (Exception e) {
		}
		return wb;
	}

}

