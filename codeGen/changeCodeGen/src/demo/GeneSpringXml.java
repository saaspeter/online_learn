package demo;

public class GeneSpringXml {

	public void doAll(){
		String[] classNames = GeneUtil.getClassNames();
		if(classNames!=null&&classNames.length>0){
            String springTemplate = GeneUtil.readTemplate(GeneUtil.springTemplate);
			StringBuffer buffer = new StringBuffer();
            for(int i=0;i<classNames.length;i++){
				String beantemplate = GeneUtil.readTemplate(GeneUtil.springBeanTemplate);
	            // 替换{[#beanName#]}
				beantemplate = beantemplate.replaceAll("\\{\\[#beanName#\\]\\}", GeneUtil.lowerFirst(classNames[i])+"Dao");
				// 替换{[#className#]}
				beantemplate = beantemplate.replaceAll("\\{\\[#className#\\]\\}", classNames[i]);
				// 替换{[#PackageDaoImpl#]}
				beantemplate = beantemplate.replaceAll("\\{\\[#PackageDaoImpl#\\]\\}", GeneUtil.PackageDaoImpl);
			    buffer.append(beantemplate).append("\n");
            }
            springTemplate = springTemplate.replaceAll("\\{\\[#daoBeanDefine#\\]\\}", buffer.toString());
			GeneUtil.writeFile(GeneUtil.FileDirDest_spring+"applicationContext.xml",springTemplate);
		    System.out.println("所有操作已完毕！");
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneSpringXml geneSpringXml = new GeneSpringXml();
		geneSpringXml.doAll();
	}

}
