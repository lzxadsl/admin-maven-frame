package com.admin.junit;

import java.sql.SQLException;
import java.util.List;

import com.admin.basic.utils.XxlsAbstract;

public class BigExcelRead extends XxlsAbstract{

	@Override  
    public void optRows(int sheetIndex,int curRow, List<String> rowlist) throws SQLException {  
        System.out.println("请重写该方法！");  
    }  
  
    public static void main(String[] args) throws Exception {  
    	BigExcelRead howto = new BigExcelRead(){
    		@Override
    		public void optRows(int sheetIndex, int curRow,
    				List<String> rowlist) throws SQLException {
    			for (int i = 0; i < rowlist.size(); i++) {  
    	            System.out.print("'" + rowlist.get(i) + "',");  
    	        }  
    	        System.out.println();  
    		}
    	};  
        howto.processOneSheet("D:/backup/desktop/数据标准.xlsx",1);  
//      howto.processAllSheets("F:/new.xlsx");  
    }
}
