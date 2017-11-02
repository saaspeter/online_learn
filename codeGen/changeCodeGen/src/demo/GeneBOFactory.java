package demo;

public class GeneBOFactory {

	public void doAll(){
		String[] classNames = GeneUtil.getClassNames();
		
		if(classNames!=null&&classNames.length>0){
            String bOFactoryTemplate = GeneUtil.readTemplate(GeneUtil.bOFactoryTemplate);
			StringBuffer buffer = new StringBuffer();
			StringBuffer bufferImport = new StringBuffer();
			
            for(int i=0;i<classNames.length;i++){
            	String className = classNames[i];
	            if(className!=null&&className.trim().length()>0)
	            	buffer.append("  public static ").append(className).append("Dao get").append(className).append("Dao() throws Exception{").append("\n")
	            	.append("    return ").append(className).append("DaoImpl.getInstance();").append("\n")
	            	.append("  }").append("\n")
	            	.append("\n");
	            
	               bufferImport.append("import ").append(GeneUtil.Project).append(".dao.").append(className).append("Dao;").append("\n")
	               .append("import ").append(GeneUtil.Project).append(".daoImpl.").append(className).append("DaoImpl;").append("\n");
            }
            // 替换{[#Project#]}
            bOFactoryTemplate = bOFactoryTemplate.replaceAll("\\{\\[#Project#\\]\\}", GeneUtil.Project);
            // 替换{[#methodDefines#]}
            bOFactoryTemplate = bOFactoryTemplate.replaceAll("\\{\\[#methodDefines#\\]\\}", buffer.toString());
            // 替换{[#importDefines#]}
            bOFactoryTemplate = bOFactoryTemplate.replaceAll("\\{\\[#importDefines#\\]\\}", bufferImport.toString());
            
			GeneUtil.writeFile(GeneUtil.FilesDirDest_logicImpl+"BOFactory.java",bOFactoryTemplate);
		    System.out.println("生成BOFactory所有操作已完毕！");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneBOFactory geneBOFactory = new GeneBOFactory();
		geneBOFactory.doAll();
	}

}
