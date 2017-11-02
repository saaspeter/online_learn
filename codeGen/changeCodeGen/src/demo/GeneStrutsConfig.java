package demo;

public class GeneStrutsConfig {

	public static final String formTemplateStr = "<form-bean name=\"{[#beanName#]}Form\" type=\"{[#ProjectWeb#]}.form.{[#className#]}Form\" />";
		
	// 读取模板文件并生成struts文件
	public String printStrutsFile(String className,StringBuffer bufferForm,StringBuffer bufferAction){
		if(className==null||className.trim().length()==0)
			return "类文件为空！";
		StringBuffer errs = new StringBuffer();

		if(bufferForm==null)
			bufferForm = new StringBuffer();

		if(bufferAction==null)
			bufferAction = new StringBuffer();
		
		String strutsFormTemplate = GeneStrutsConfig.formTemplateStr;
		String strutsActionTemplate = GeneUtil.readTemplate(GeneUtil.strutsConfig_Template);	
		
	    // replace the {[#beanName#]}
		strutsFormTemplate = strutsFormTemplate.replaceAll("\\{\\[#beanName#\\]\\}",GeneUtil.lowerFirst(className));
		// replace the {[#Project#]}
		strutsFormTemplate = strutsFormTemplate.replaceAll("\\{\\[#Project#\\]\\}",GeneUtil.Project);
        // replace the {[#ProjectWeb#]}
		strutsFormTemplate = strutsFormTemplate.replaceAll("\\{\\[#ProjectWeb#\\]\\}",GeneUtil.ProjectWeb);
        // replace the {[#className#]}
		strutsFormTemplate = strutsFormTemplate.replaceAll("\\{\\[#className#\\]\\}",className);
		bufferForm.append(strutsFormTemplate).append("\n");
		
		if(strutsActionTemplate!=null&&strutsActionTemplate.trim().length()>0){
		// replace the {[#Project#]}
			strutsActionTemplate = strutsActionTemplate.replaceAll("\\{\\[#Project#\\]\\}",GeneUtil.Project);
		// replace the {[#ProjectWeb#]}
			strutsActionTemplate = strutsActionTemplate.replaceAll("\\{\\[#ProjectWeb#\\]\\}",GeneUtil.ProjectWeb);
	    // replace the {[#beanName#]}
			strutsActionTemplate = strutsActionTemplate.replaceAll("\\{\\[#beanName#\\]\\}",GeneUtil.lowerFirst(className));
        // replace the {[#className#]}
			strutsActionTemplate = strutsActionTemplate.replaceAll("\\{\\[#className#\\]\\}",className);
			if(GeneUtil.subFolder!=null&&GeneUtil.subFolder.trim().length()>0)
				strutsActionTemplate = strutsActionTemplate.replaceAll("\\{\\[#folder#\\]\\}",GeneUtil.subFolder);
		}else
			errs.append("读取struts配置文件模板失败！");
		bufferAction.append(strutsActionTemplate).append("\n");
		
		return errs.toString();
	}
	
	/**
	 * 生成所有文件的form的java文件
	 */
	public void doAll(){
		String[] classNames = GeneUtil.getClassNames();
		StringBuffer errs = new StringBuffer("GeneStrutsConfig日至：\n");
		
		StringBuffer bufferForm = new StringBuffer();
		StringBuffer bufferAction = new StringBuffer();
		
		if(classNames!=null&&classNames.length>0)
		for(int i=0;i<classNames.length;i++){
			errs.append(printStrutsFile(classNames[i],bufferForm,bufferAction));
			errs.append("\n");
		}
		// write File
		String content = bufferForm.toString()+"\n\n"+bufferAction.toString();
		GeneUtil.writeFile(GeneUtil.struts_config_file, content);
		
		System.out.println(errs.toString());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneStrutsConfig config = new GeneStrutsConfig();
        config.doAll();
	}

}
