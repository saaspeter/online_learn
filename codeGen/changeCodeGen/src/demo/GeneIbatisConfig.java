package demo;

import java.io.File;

public class GeneIbatisConfig {

	public void doAll(){
		File fileDir = new File(GeneUtil.FilesDirDest_sql);
		File[] files = fileDir.listFiles();
		
		if(files!=null&&files.length>0){
            String ibatisConfigTemplate = GeneUtil.readTemplate(GeneUtil.ibatisConfigTemplate);
			StringBuffer buffer = new StringBuffer();
            for(int i=0;i<files.length;i++){
            	String fileName = files[i].getName();
	            if(fileName!=null&&fileName.trim().length()>0)
	            	buffer.append("  <sqlMap resource=\"")
	            	.append(GeneUtil.sqlMapPath).append(fileName)
	            	.append("\"/>")
	            	.append("\n");
            }
            // 替换{[#sqlMapDefine#]}
            ibatisConfigTemplate = ibatisConfigTemplate.replaceAll("\\{\\[#sqlMapDefine#\\]\\}", buffer.toString());
			
			GeneUtil.writeFile(GeneUtil.FileDirDest_ibatisConfig+"iBatis-config.xml",ibatisConfigTemplate);
		    System.out.println("所有操作已完毕！");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneIbatisConfig geneIbatisConfig = new GeneIbatisConfig();
		geneIbatisConfig.doAll();
	}

}
