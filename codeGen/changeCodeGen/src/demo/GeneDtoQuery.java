/**
 * 生成查询用的voQuery对象，根据ＶＯ包中的类生成
 */
package demo;

public class GeneDtoQuery {

    public void doAll(){
    	String[] classNames = GeneUtil.getClassNames();
		if(classNames!=null&&classNames.length>0){
            for(int i=0;i<classNames.length;i++){
            	String dtoQueryTemplate = GeneUtil.readTemplate(GeneUtil.dtoQueryTemplate);
	            // 替换{[#PackageDto#]}
            	dtoQueryTemplate = dtoQueryTemplate.replaceAll("\\{\\[#PackageDto#\\]\\}", GeneUtil.PackageDto);
				// 替换{[#PackageVO#]}
            	dtoQueryTemplate = dtoQueryTemplate.replaceAll("\\{\\[#PackageVO#\\]\\}", GeneUtil.PackageVO);
				// 替换{[#className#]}
            	dtoQueryTemplate = dtoQueryTemplate.replaceAll("\\{\\[#className#\\]\\}", classNames[i]); 
			    
				GeneUtil.writeFile(GeneUtil.FilesDirDest_dtoQuery+classNames[i]+"Query.java",dtoQueryTemplate);
            }
		    System.out.println("所有操作已完毕！");
		}
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneDtoQuery geneDtoQuery = new GeneDtoQuery();
		geneDtoQuery.doAll();
	}	
	
}
