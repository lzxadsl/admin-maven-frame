package com.admin.junit;

import com.admin.basic.utils.ImportExcelUtil;

/**
 * 
 * @author lzx
 * @version 1.0
 * @create_date 下午8:33:38
 */
public class ImportExcel {

	public static void main(String[] args) {
		String filePath = "D:/backup/desktop/数据标准.xlsx";
		ImportExcelUtil imp = new ImportExcelUtil();
		imp.setRowIndex(2);
		imp.setSheetIndex(1);
		String[][] fldMapping = {		 
				{"标准标号","std_No","0"},
				{"标准主题","std_Area","1"},
				{"标准大类","big_Class","2"},
				{"标准小类","sub_Class","3"},
				{"标准中文名称","std_Chn_Nm","4"},
				{"标准英文名称","std_Eng_Nm","5"},
				{"业务规则","std_Bsn_Rus","6"},
				{"业务定义","std_Bsn_Def","7"},
				{"数据格式","data_Formate","8"},
				{"代码编码规则","code_Rule","9"},
				{"取值范围","value_Range","10"},
				{"数据敏感性分级","data_Lvl","11"},
				{"数据定义部门","deptment_Nm","12"}
		};
		String jsonStr = imp.readExcel(filePath, fldMapping);
		System.out.println(jsonStr);
	}
}
