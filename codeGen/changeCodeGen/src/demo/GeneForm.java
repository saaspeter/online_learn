package demo;

public class GeneForm {

	/**
	 * 根据类名返回Form类的文件的全路径名
	 * @param className
	 * @return
	 */
	public String getFormFileName(String className){
		if(className==null||className.trim().length()<=0)
			return "";
		return GeneUtil.FilesDirDest_form+className+"Form.java";
	}
	
	// 读取模板文件并生成form文件
	public String printFormFile(String className){
		if(className==null||className.trim().length()==0)
			return "类文件为空！";
		StringBuffer errs = new StringBuffer();
		String formTemplate = GeneUtil.readTemplate(GeneUtil.formTemplate);	
		if(formTemplate!=null&&formTemplate.trim().length()>0){
		// replace the {[#Project#]}
			formTemplate = formTemplate.replaceAll("\\{\\[#Project#\\]\\}",GeneUtil.Project);
        // replace the {[#className#]}
			formTemplate = formTemplate.replaceAll("\\{\\[#className#\\]\\}",className);
        // replace the {[#ProjectWeb#]}
			formTemplate = formTemplate.replaceAll("\\{\\[#ProjectWeb#\\]\\}",GeneUtil.ProjectWeb);
        // replace the {[#PackageDto#]}
			formTemplate = formTemplate.replaceAll("\\{\\[#PackageDto#\\]\\}",GeneUtil.PackageDto);
        // replace the {[#PackageVO#]}
			formTemplate = formTemplate.replaceAll("\\{\\[#PackageVO#\\]\\}",GeneUtil.PackageVO);
		
        // 写入form文件
			if(!GeneUtil.writeFile(getFormFileName(className), formTemplate))
				errs.append("写form文件"+className+"Form时出错！");
		}else
			errs.append("读取Form表单文件模板失败！");
		return errs.toString();
	}
	
	/**
	 * 生成所有文件的form的java文件
	 */
	public void doAll(){
		String[] classNames = GeneUtil.getClassNames();
		StringBuffer errs = new StringBuffer("GeneForm日至：\n");
		if(classNames!=null&&classNames.length>0)
		for(int i=0;i<classNames.length;i++){
			errs.append(printFormFile(classNames[i]));
			errs.append("\n");
		}
		System.out.println(errs.toString());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneForm form = new GeneForm();
        form.doAll();
	}

}
