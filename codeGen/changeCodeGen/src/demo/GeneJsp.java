package demo;

import java.util.List;

public class GeneJsp {

	public static final int listJsp = 1;
	public static final int addEditJsp = 2;
	public static final int viewJsp = 3;	
	
	/**
	 * 根据类名返回Action类的文件的全路径名
	 * @param className
	 * @return
	 */
	public String getJspFileName(String className,int jspType){
		if(className==null||className.trim().length()<=0)
			return "";
		String beanName = GeneUtil.lowerFirst(className);
		String fileName = "";
		if(jspType==GeneJsp.listJsp)
		  fileName = GeneUtil.FilesDirDest_jsp+beanName+"_list.jsp";
		else if(jspType==GeneJsp.addEditJsp)
		  fileName = GeneUtil.FilesDirDest_jsp+beanName+"_addEdit.jsp";
		else if(jspType==GeneJsp.viewJsp)
		  fileName = GeneUtil.FilesDirDest_jsp+beanName+"_view.jsp";
		return fileName;
	}
	
	// 读取模板文件并生成Jsp文件
	public String printAllJspFile(String className,SqlTemplateVO vo,StringBuffer bufferResource){
		if(className==null||className.trim().length()==0)
			return "类文件为空！";
		StringBuffer errs = new StringBuffer();
		if(bufferResource==null)
		   bufferResource = new StringBuffer();
        // 生成list的jsp
		errs.append(printListJspFile(className,vo,bufferResource));
		// 生成addEdit的jsp
		errs.append(printAddEditJspFile(className,vo));
		// 生成view的jsp
		errs.append(printViewJspFile(className,vo));
		
		return errs.toString();
	}
	
	// 生成并保存list的jsp页面
	public String printListJspFile(String className,SqlTemplateVO vo,StringBuffer bufferResource){
		StringBuffer errs = new StringBuffer();
		if(className==null||className.trim().length()==0)
		    return "className定义为空！";
		String listTempleJsp = GeneUtil.readTemplate(GeneUtil.jsp_listTemplate);
		String listColumnNamesTempleJsp = GeneUtil.readTemplate(GeneUtil.jsp_listColumnNames_Template);
		String listColumnsTempleJsp = GeneUtil.readTemplate(GeneUtil.jsp_listColumns_Template);
		
		StringBuffer columnNamesStr = new StringBuffer();
		StringBuffer columnsStr = new StringBuffer();
		
		if(bufferResource==null)
			bufferResource = new StringBuffer();
		
		List columnsList_nopk = vo.getColumnVO_all_noPK();
		if(columnsList_nopk==null||columnsList_nopk.size()==0)
			errs.append("没有找到"+className+"的字段属性！");
		// 生成columnNames
		for(int i=0;i<columnsList_nopk.size();i++){
			String listColumnNamesTemp = listColumnNamesTempleJsp;
			String listColumnTemp = listColumnsTempleJsp;
			String property = ((ColumnVO)columnsList_nopk.get(i)).getProperty();
			
			listColumnNamesTemp = listColumnNamesTemp.replaceAll("\\{\\[#property#\\]\\}",property);
			listColumnNamesTemp = listColumnNamesTemp.replaceAll("\\{\\[#Project#\\]\\}",GeneUtil.Project);
			listColumnNamesTemp = listColumnNamesTemp.replaceAll("\\{\\[#jspName#\\]\\}",GeneUtil.lowerFirst(className));
			listColumnTemp = listColumnTemp.replaceAll("\\{\\[#property#\\]\\}",property);
			
			columnNamesStr.append(listColumnNamesTemp).append("\n");
			columnsStr.append(listColumnTemp).append("\n");
		}
		
		listTempleJsp = listTempleJsp.replaceAll("\\{\\[#Project#\\]\\}",GeneUtil.Project);
		listTempleJsp = listTempleJsp.replaceAll("\\{\\[#jspName#\\]\\}",GeneUtil.lowerFirst(className));
		listTempleJsp = listTempleJsp.replaceAll("\\{\\[#actionName#\\]\\}",GeneUtil.lowerFirst(className));
		listTempleJsp = listTempleJsp.replaceAll("\\{\\[#className#\\]\\}",className);
		if(GeneUtil.subFolder!=null&&GeneUtil.subFolder.trim().length()>0){
           listTempleJsp = listTempleJsp.replaceAll("\\{\\[#folder#\\]\\}",GeneUtil.subFolder);   
		}
		if(vo.getPkProperty()!=null)
		   listTempleJsp = listTempleJsp.replaceAll("\\{\\[#pkName#\\]\\}",vo.getPkProperty());
		listTempleJsp = listTempleJsp.replaceAll("\\{\\[#listColumnNamesDefines#\\]\\}",columnNamesStr.toString());
		listTempleJsp = listTempleJsp.replaceAll("\\{\\[#listColumnsDefines#\\]\\}",columnsStr.toString());
		
		// 粘帖资源文件
		bufferResource.append(columnNamesStr.toString()).append("\n\n");
		// write list jsp 
		GeneUtil.writeFile(getJspFileName(className,GeneJsp.listJsp),listTempleJsp);
		return errs.toString();
	}
	
	// 生成并保存addEdit的jsp页面
	public String printAddEditJspFile(String className,SqlTemplateVO vo){
		StringBuffer errs = new StringBuffer();
		if(className==null||className.trim().length()==0)
		    return "className定义为空！";
		String addEditTempleJsp = GeneUtil.readTemplate(GeneUtil.jsp_addEdit_Template);
		String addEditColumnsTempleJsp = GeneUtil.readTemplate(GeneUtil.jsp_addEditColumns_Template);
		
		StringBuffer columnsStr = new StringBuffer();
		
		List columnsList_nopk = vo.getColumnVO_all_noPK();
		if(columnsList_nopk==null||columnsList_nopk.size()==0)
			errs.append("没有找到"+className+"的字段属性！");
		// 生成columnNames
		for(int i=0;i<columnsList_nopk.size();i++){
			String addEditColumnTemp = addEditColumnsTempleJsp;
			String property = ((ColumnVO)columnsList_nopk.get(i)).getProperty();
			
			addEditColumnTemp = addEditColumnTemp.replaceAll("\\{\\[#Project#\\]\\}",GeneUtil.Project);
			addEditColumnTemp = addEditColumnTemp.replaceAll("\\{\\[#actionName#\\]\\}",GeneUtil.lowerFirst(className));
			addEditColumnTemp = addEditColumnTemp.replaceAll("\\{\\[#property#\\]\\}",property);
			
			columnsStr.append(addEditColumnTemp).append("\n");
		}
		
		addEditTempleJsp = addEditTempleJsp.replaceAll("\\{\\[#Project#\\]\\}",GeneUtil.Project);
		addEditTempleJsp = addEditTempleJsp.replaceAll("\\{\\[#jspName#\\]\\}",GeneUtil.lowerFirst(className));
		addEditTempleJsp = addEditTempleJsp.replaceAll("\\{\\[#actionName#\\]\\}",GeneUtil.lowerFirst(className));
		addEditTempleJsp = addEditTempleJsp.replaceAll("\\{\\[#className#\\]\\}",className);
		if(GeneUtil.subFolder!=null&&GeneUtil.subFolder.trim().length()>0)
			addEditTempleJsp = addEditTempleJsp.replaceAll("\\{\\[#folder#\\]\\}",GeneUtil.subFolder);
		if(vo.getPkProperty()!=null)
		   addEditTempleJsp = addEditTempleJsp.replaceAll("\\{\\[#pkName#\\]\\}",vo.getPkProperty());
		addEditTempleJsp = addEditTempleJsp.replaceAll("\\{\\[#addEditColumnsDefines#\\]\\}",columnsStr.toString());
		
		// write list jsp 
		GeneUtil.writeFile(getJspFileName(className,GeneJsp.addEditJsp),addEditTempleJsp);
		return errs.toString();
	}
	
	// 生成并保存view的jsp页面
	public String printViewJspFile(String className,SqlTemplateVO vo){
		StringBuffer errs = new StringBuffer();
		if(className==null||className.trim().length()==0)
		    return "className定义为空！";
		String viewTempleJsp = GeneUtil.readTemplate(GeneUtil.jsp_view_Template);
		String viewColumnsTempleJsp = GeneUtil.readTemplate(GeneUtil.jsp_viewColumns_Template);
		
		StringBuffer columnsStr = new StringBuffer();
		
		List columnsList_nopk = vo.getColumnVO_all_noPK();
		if(columnsList_nopk==null||columnsList_nopk.size()==0)
			errs.append("没有找到"+className+"的字段属性！");
		// 生成columnNames
		for(int i=0;i<columnsList_nopk.size();i++){
			String viewColumnTemp = viewColumnsTempleJsp;
			String property = ((ColumnVO)columnsList_nopk.get(i)).getProperty();
			
			viewColumnTemp = viewColumnTemp.replaceAll("\\{\\[#Project#\\]\\}",GeneUtil.Project);
			viewColumnTemp = viewColumnTemp.replaceAll("\\{\\[#actionName#\\]\\}",GeneUtil.lowerFirst(className));
			viewColumnTemp = viewColumnTemp.replaceAll("\\{\\[#property#\\]\\}",property);
			
			columnsStr.append(viewColumnTemp).append("\n");
		}
		
		viewTempleJsp = viewTempleJsp.replaceAll("\\{\\[#Project#\\]\\}",GeneUtil.Project);
		viewTempleJsp = viewTempleJsp.replaceAll("\\{\\[#jspName#\\]\\}",GeneUtil.lowerFirst(className));
		viewTempleJsp = viewTempleJsp.replaceAll("\\{\\[#actionName#\\]\\}",GeneUtil.lowerFirst(className));
		viewTempleJsp = viewTempleJsp.replaceAll("\\{\\[#className#\\]\\}",className);
		viewTempleJsp = viewTempleJsp.replaceAll("\\{\\[#viewColumnsDefines#\\]\\}",columnsStr.toString());
		if(GeneUtil.subFolder!=null&&GeneUtil.subFolder.trim().length()>0)
			viewTempleJsp = viewTempleJsp.replaceAll("\\{\\[#folder#\\]\\}",GeneUtil.subFolder);
		
		// write list jsp 
		GeneUtil.writeFile(getJspFileName(className,GeneJsp.viewJsp),viewTempleJsp);
		return errs.toString();
	}
	
	/**
	 * 生成所有文件的Jsp的java文件
	 */
	public void doAll(){
		String[] classNames = GeneUtil.getClassNames();
		StringBuffer errs = new StringBuffer("GeneJsp日至：\n");
		
		StringBuffer bufferResource = new StringBuffer();
		if(classNames!=null&&classNames.length>0)
		for(int i=0;i<classNames.length;i++){
			SqlTemplateVO vo = GeneUtil.getSqlTemplateVO(classNames[i]);
			errs.append(printAllJspFile(classNames[i],vo,bufferResource));
			errs.append("\n");
		}
		// write resource files
		GeneUtil.writeFile(GeneUtil.jsp_columns_file, bufferResource.toString());
		
		System.out.println(errs.toString());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneJsp jsp = new GeneJsp();
        jsp.doAll();
	}

}
