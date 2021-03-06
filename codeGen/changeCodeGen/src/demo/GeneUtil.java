package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class GeneUtil {
	
	public static final String FilePath_Real = "C:/workspace/peter";

	/**************     文件模板    ****************/	
	// 文件的模板
	public static final String sqlTemplate_withBlobs = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/sqlTemplate.xml";
	public static final String sqlTemplate_withoutBlobs = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/sqlTemplate_withoutBlobs.xml";
	
	public static final String daoTemplate_withBlobs = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/daoTemplate_withBlobs.java";
	public static final String daoImplTemplate_withBlobs = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/daoImplTemplate_withBlobs.java";
	public static final String daoTemplate_withoutBlobs = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/daoTemplate_withoutBlobs.java";
	public static final String daoImplTemplate_withoutBlobs = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/daoImplTemplate_withoutBlobs.java";
	
	public static final String springTemplate = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/springTemplate.xml";
	public static final String springBeanTemplate = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/springBeanTemplate.xml";
	public static final String dtoQueryTemplate = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/dtoQueryTemplate.java";
	public static final String ibatisConfigTemplate = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/ibatisConfigTemplate.xml"; 
	public static final String bOFactoryTemplate = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/BOFactoryTemplate.java"; 	
	
	public static final String formTemplate = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/formTemplate.java";
	public static final String actionTemplate = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/actionTemplate.java";
	
	public static final String jsp_listTemplate = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/listTemplate.jsp";
	public static final String jsp_listColumnNames_Template = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/listTemplate_columnNames.jsp";
	public static final String jsp_listColumns_Template = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/listTemplate_columns.jsp";
	
	public static final String jsp_addEdit_Template = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/addEditTemplate.jsp";
	public static final String jsp_addEditColumns_Template = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/addEditTemplate_columns.jsp";
	
	public static final String jsp_view_Template = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/viewTemplate.jsp";
	public static final String jsp_viewColumns_Template = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/viewTemplate_columns.jsp";
	
	public static final String strutsConfig_Template = FilePath_Real+"/codeGen/changeCodeGen/src/demo/template/strutsConfigTemplate.xml";
	
	
	
	
	/***************  需要配置的包名和目录部分    *********************/	
	// 定义工程名目录名 platform or netTest
	public static final String Project = "netTest";
	
	public static final String ProjectWeb = "netTestWeb";
	
	public static final String subFolder = "product";
	
	
	
	
    /***************    要生成的文件的文件夹   ***************/
	// 根据sql文件的文件夹
	public static final String FilesDirSource = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/sql22/";

	// 要生成文件的文件夹
	public static final String FilesDirDest_dtoQuery = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/dto/";
	public static final String FilesDirDest_sql = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/sql/";
	public static final String FilesDirDest_dao = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/dao/";
	public static final String FilesDirDest_daoImpl = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/dao/impl/";
	public static final String FilesDirDest_logicImpl = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/logic/impl/";
	public static final String FileDirDest_spring = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/bean/";
	public static final String FileDirDest_ibatisConfig = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/bean/";
	public static final String FilesDirDest_form = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/form/";
	public static final String FilesDirDest_action = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/action/";
	public static final String FilesDirDest_jsp = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/jsp/";

	// 生成的存放每个类字段名称的资源文件
	public static final String jsp_columns_file = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/struts/list.txt";
	public static final String struts_config_file = FilePath_Real+"/codeGen/"+Project+"/src/"+Project+"/struts/struts_config.xml";
	
	// 定义包名
	public static final String PackageCommon = Project+".base";
	public static final String PackageSql = Project + "." +subFolder + ".sql";
	public static final String PackageVO = Project + "." +subFolder + ".vo";
	public static final String PackageDto = Project + "." +subFolder + ".dto";
	public static final String PackageDao = Project + "." +subFolder + ".dao";
	public static final String PackageDaoImpl = Project + "." +subFolder + ".dao"+".impl";
	public static final String PackageBase = Project + "." +subFolder + ".base";
	public static final String PackageBean = Project + "." +subFolder + ".bean";
	
/***************    定义sqlMap文件	******************/
	public static final String sqlMapPath = Project+"/sql/";
	
/*****************    定义sql文件中的类型      ************************/	
	// 定义在ibatis中text类型字段的jdbcType
	public static final String BlobType = "LONGVARCHAR";
	public static final String PK_Type = "BIGINT";
	
	
	/**
	 * 根据sqlMao的xml文件名获得对应的类名称
	 * @param fileName
	 * @return
	 */
	public static String getClassNameFromSql(String fileName){
		if(fileName==null||fileName.trim().length()<=0||fileName.trim().endsWith("/"))
			return "";
		fileName = fileName.trim();
		if(fileName.lastIndexOf("/")!=-1)
		   fileName = fileName.substring(fileName.lastIndexOf("/")+1);
		fileName = GeneUtil.upperFirst(fileName);
        if(fileName.lastIndexOf("_SqlMap.xml")!=-1)
		   return fileName.substring(0, fileName.lastIndexOf("_SqlMap.xml"));
        else 
           return fileName;
	}
	
	/**
	 * 根据xml文件名获得对应的数据库表名称
	 * @param fileName
	 * @return
	 */
	public static String getTableNameFromSql(String fileName){
		if(fileName==null||fileName.trim().length()<=0||fileName.trim().endsWith("/"))
			return "";
		fileName = fileName.trim();
		if(fileName.lastIndexOf("/")!=-1)
		   fileName = fileName.substring(fileName.lastIndexOf("/")+1);
        if(fileName.lastIndexOf("_SqlMap.xml")!=-1)
		   return fileName.substring(0, fileName.lastIndexOf("_SqlMap.xml"));
        else 
           return fileName;
	}
	
	/**
	 * 返回指定目录下的文件
	 * @return
	 */
	public static File[] getFiles(){
		File fileDir = new File(FilesDirSource);
		File[] files = fileDir.listFiles();
		return files;
	}
	
	/**
	 * 根据文件夹下的sql文件生成类名数组
	 * @return
	 */
	public static String[] getClassNames(){
		File fileDir = new File(FilesDirSource);
		File[] files = fileDir.listFiles();
		if(files==null||files.length<=0)
			return null;
		String[] classNames = new String[files.length]; 
		for(int i=0;i<classNames.length;i++){
			classNames[i] = getClassNameFromSql(files[i].getName());
		}
		return classNames;
	}
	
	/**
	 * 根据文件名读取模板文件，返回文件的String
	 * @param fileName
	 * @return
	 */
	public static String readTemplate(String fileName){
		if(fileName==null||fileName.trim().length()<=0)
			return "";
		String line;  
		StringBuffer buffer = new StringBuffer();
		try {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			line = reader.readLine();       // 读取第一行
			while (line != null) {          // 如果 line 为空说明读完了
			    buffer.append(line);        // 将读到的内容添加到 buffer 中
			    buffer.append("\n");        // 添加换行符
			    line = reader.readLine();   // 读取下一行
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * 写文件
	 * @param fileName：文件全路径名
	 * @param fileContent
	 * @return
	 */
	public static boolean writeFile(String fileName,String fileContent){
		FileOutputStream os;
		try {
			os = new FileOutputStream(fileName);
            PrintStream ps = new PrintStream(os);  
            ps.print(fileContent);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 将字符串的首字母大写
	 * @param str
	 * @return
	 */
	public static String upperFirst(String str){
		if(str==null||str.trim().length()<=0)
			return str;
		String first = str.substring(0, 1);
		first = first.toUpperCase();
		return first+str.substring(1);
	}
	
	/**
	 * 将字符串的首字母小写
	 * @param str
	 * @return
	 */
	public static String lowerFirst(String str){
		if(str==null||str.trim().length()<=0)
			return str;
		String first = str.substring(0, 1);
		first = first.toLowerCase();
		return first+str.substring(1);
	}
	
	/**
	 * 根据类的名称判断该类对应的sqlMap文件是否有blob类型字段
	 * @param className
	 * @return
	 */
	public static boolean hasBlobs(String className){
		if(className==null||className.trim().length()<=0)
			return false;
		String fileName = GeneUtil.FilesDirSource+GeneUtil.lowerFirst(className)+"_SqlMap.xml";
System.out.println("文件名是："+fileName);
		File file = new File(fileName);
		return GeneSql.hasBlobs(file);
	}
	
	/**
	 * 根据类的名从abatro生成的sql文件中得到该类的pk属性
	 * @param className
	 * @return
	 */
	public static String getPK(String className){
		if(className==null||className.trim().length()<=0)
			return null;
		String fileName = GeneUtil.FilesDirSource+GeneUtil.lowerFirst(className)+"_SqlMap.xml";
System.out.println("文件名是："+fileName);
		File file = new File(fileName);
		if(file==null||!file.exists())
			return null;
		GeneSql geneSql = new GeneSql();
		return geneSql.getPK(file);
	}
	
	/**
	 * 根据类的名从abatro生成的sql文件中得到该类的SqlTemplateVO属性
	 * @param className
	 * @return
	 */
	public static SqlTemplateVO getSqlTemplateVO(String className){
		if(className==null||className.trim().length()<=0)
			return null;
		String fileName = GeneUtil.FilesDirSource+GeneUtil.lowerFirst(className)+"_SqlMap.xml";
System.out.println("文件名是22："+fileName);
		File file = new File(fileName);
		if(file==null||!file.exists())
			return null;
		GeneSql geneSql = new GeneSql();
		SqlTemplateVO vo = new SqlTemplateVO();
		geneSql.geneColumns(file, vo);
		return vo;
	}
	
	public void geneAllTypeFile(){
		GeneSql geneSql = new GeneSql();
		geneSql.doAll();
		
		GeneDtoQuery geneDtoQuery = new GeneDtoQuery();
		geneDtoQuery.doAll();
		
		GeneDao geneDao = new GeneDao();
		geneDao.doAll();
		
		GeneSpringXml geneSpringXml = new GeneSpringXml();
		geneSpringXml.doAll();
		
		GeneIbatisConfig geneIbatisConfig = new GeneIbatisConfig();
		geneIbatisConfig.doAll();
		
		GeneBOFactory geneBOFactory = new GeneBOFactory();
		geneBOFactory.doAll();
		
		GeneForm geneForm = new GeneForm();
		geneForm.doAll();
		
		GeneAction geneAction = new GeneAction();
		geneAction.doAll();
		
		GeneStrutsConfig geneStrutsConfig = new GeneStrutsConfig();
		geneStrutsConfig.doAll();
		
		GeneJsp geneJsp = new GeneJsp();
		geneJsp.doAll();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//          String[] classNames = GeneUtil.getClassNames();
//          for(int i=0;i<classNames.length;i++){
//        	  boolean hasBlob = GeneUtil.hasBlobs(classNames[i]);
//        	  if(hasBlob)
//        		  System.out.println("类"+classNames[i]+"中有blobs");
//        	  else
//        		  System.out.println("类"+classNames[i]+"中没有blobs：）");
//          }
        GeneUtil geneUtil = new GeneUtil();
        geneUtil.geneAllTypeFile();
	}

}
