package demo;

public class GeneAction {

	/**
	 * 根据类名返回Action类的文件的全路径名
	 * @param className
	 * @return
	 */
	public String getActionFileName(String className){
		if(className==null||className.trim().length()<=0)
			return "";
		return GeneUtil.FilesDirDest_action+className+"Action.java";
	}
	
	// 读取模板文件并生成Action文件
	public String printActionFile(String className,String pk){
		if(className==null||className.trim().length()==0)
			return "类文件为空！";
		StringBuffer errs = new StringBuffer();
		String ActionTemplate = GeneUtil.readTemplate(GeneUtil.actionTemplate);	
		if(ActionTemplate!=null&&ActionTemplate.trim().length()>0){
		// replace the {[#Project#]}
			ActionTemplate = ActionTemplate.replaceAll("\\{\\[#Project#\\]\\}",GeneUtil.Project);
        // replace the {[#className#]}
			ActionTemplate = ActionTemplate.replaceAll("\\{\\[#className#\\]\\}",className);
	    // replace the {[#ProjectWeb#]}
			ActionTemplate = ActionTemplate.replaceAll("\\{\\[#ProjectWeb#\\]\\}",GeneUtil.ProjectWeb);
		    // replace the {[#PackageDao#]}
			ActionTemplate = ActionTemplate.replaceAll("\\{\\[#PackageDao#\\]\\}",GeneUtil.PackageDao);
		    // replace the {[#PackageDto#]}
			ActionTemplate = ActionTemplate.replaceAll("\\{\\[#PackageDto#\\]\\}",GeneUtil.PackageDto);
		    // replace the {[#PackageVO#]}
			ActionTemplate = ActionTemplate.replaceAll("\\{\\[#PackageVO#\\]\\}",GeneUtil.PackageVO);
	    // replace the {[#pkInMethod#]}
			if(pk!=null&&pk.trim().length()>0){
				   String pkInMethod = GeneUtil.upperFirst(pk);
				   ActionTemplate = ActionTemplate.replaceAll("\\{\\[#pkInMethod#\\]\\}",pkInMethod);
			}
		
        // 写入Action文件
			if(!GeneUtil.writeFile(getActionFileName(className), ActionTemplate))
				errs.append("写Action文件"+className+"Action时出错！");
		}else
			errs.append("读取Action表单文件模板失败！");
		return errs.toString();
	}
	
	/**
	 * 生成所有文件的Action的java文件
	 */
	public void doAll(){
		String[] classNames = GeneUtil.getClassNames();
		StringBuffer errs = new StringBuffer("GeneAction日至：\n");
		if(classNames!=null&&classNames.length>0)
		for(int i=0;i<classNames.length;i++){
			String pk = GeneUtil.getPK(classNames[i]);
			errs.append(printActionFile(classNames[i],pk));
			errs.append("\n");
		}
		System.out.println(errs.toString());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneAction action = new GeneAction();
        action.doAll();
	}

}
