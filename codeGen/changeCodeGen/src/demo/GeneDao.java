/**
 * 生成dao和daoImpl包中的内容
 */
package demo;

public class GeneDao {

	public static final String ProjectConstant = GeneUtil.upperFirst(GeneUtil.Project)+"Constant";
	
	/**
	 * 根据类名返回Dao类的文件的全路径名
	 * @param className
	 * @return
	 */
	public String getDaoFileName(String className){
		if(className==null||className.trim().length()<=0)
			return "";
		return GeneUtil.FilesDirDest_dao+className+"Dao.java";
	}
	
	/**
	 * 根据类名返回DaoImpl实现类的文件的全路径名
	 * @param className
	 * @return
	 */
	public String getDaoImplFileName(String className){
		if(className==null||className.trim().length()<=0)
			return "";
		return GeneUtil.FilesDirDest_daoImpl+className+"DaoImpl.java";
	}
	
	public String printDaoLayerFile(String className,String pk){
		StringBuffer errs = new StringBuffer();
		
		if(className == null||className.trim().length()<=0)
			return className+"类名为空！";
		if(pk == null||pk.trim().length()<=0)
			errs.append(className).append("主键为空！");
		
		String daoTemplate = null;
		String daoImplTemplate = null;
		if(GeneUtil.hasBlobs(className)){
			daoTemplate = GeneUtil.readTemplate(GeneUtil.daoTemplate_withBlobs);
			daoImplTemplate = GeneUtil.readTemplate(GeneUtil.daoImplTemplate_withBlobs);
		}else{
			daoTemplate = GeneUtil.readTemplate(GeneUtil.daoTemplate_withoutBlobs);
			daoImplTemplate = GeneUtil.readTemplate(GeneUtil.daoImplTemplate_withoutBlobs);	
		}
		
		if(daoTemplate!=null&&daoImplTemplate!=null&&
				daoTemplate.trim().length()>0&&daoImplTemplate.trim().length()>0){
			// 替换{[#PackageDao#]}
			daoTemplate = daoTemplate.replaceAll("\\{\\[#PackageDao#\\]\\}",GeneUtil.PackageDao);
			daoImplTemplate = daoImplTemplate.replaceAll("\\{\\[#PackageDao#\\]\\}",GeneUtil.PackageDao);
	        // 替换{[#PackageDaoImpl#]}
			daoImplTemplate = daoImplTemplate.replaceAll("\\{\\[#PackageDaoImpl#\\]\\}",GeneUtil.PackageDaoImpl);
	        // 替换{[#PackageBase#]}
			daoTemplate = daoTemplate.replaceAll("\\{\\[#PackageBase#\\]\\}",GeneUtil.PackageBase);
			daoImplTemplate = daoImplTemplate.replaceAll("\\{\\[#PackageBase#\\]\\}",GeneUtil.PackageBase);
			// 替换{[#PackageCommon#]}
			daoTemplate = daoTemplate.replaceAll("\\{\\[#PackageCommon#\\]\\}",GeneUtil.PackageCommon);
			daoImplTemplate = daoImplTemplate.replaceAll("\\{\\[#PackageCommon#\\]\\}",GeneUtil.PackageCommon);
	        // 替换{[#PackageBean#]}
			daoImplTemplate = daoImplTemplate.replaceAll("\\{\\[#PackageBean#\\]\\}",GeneUtil.PackageBean);
	        // 替换{[#className#]}
			daoTemplate = daoTemplate.replaceAll("\\{\\[#className#\\]\\}",className);
			daoImplTemplate = daoImplTemplate.replaceAll("\\{\\[#className#\\]\\}",className);
	        // 替换{[#PackageVO#]}
			daoTemplate = daoTemplate.replaceAll("\\{\\[#PackageVO#\\]\\}",GeneUtil.PackageVO);
			daoImplTemplate = daoImplTemplate.replaceAll("\\{\\[#PackageVO#\\]\\}",GeneUtil.PackageVO);
	        // 替换{[#PackageDto#]}
			daoTemplate = daoTemplate.replaceAll("\\{\\[#PackageDto#\\]\\}",GeneUtil.PackageDto);
			daoImplTemplate = daoImplTemplate.replaceAll("\\{\\[#PackageDto#\\]\\}",GeneUtil.PackageDto);
			// 替换{[#pkInMethod#]}
			if(pk!=null&&pk.trim().length()>0){
			   String pkInMethod = GeneUtil.upperFirst(pk);
			   daoTemplate = daoTemplate.replaceAll("\\{\\[#pkInMethod#\\]\\}",pkInMethod);
			   daoImplTemplate = daoImplTemplate.replaceAll("\\{\\[#pkInMethod#\\]\\}",pkInMethod);
			}
	        // 替换{[#beanName#]}
			daoImplTemplate = daoImplTemplate.replaceAll("\\{\\[#beanName#\\]\\}",GeneUtil.lowerFirst(className)+"Dao");
			// 替换{[#ProjectConstant#]}
			daoImplTemplate = daoImplTemplate.replaceAll("\\{\\[#ProjectConstant#\\]\\}",GeneDao.ProjectConstant);
			
	       // 写入dao文件
			if(!GeneUtil.writeFile(getDaoFileName(className), daoTemplate))
				errs.append("写dao文件"+className+"Dao时出错！");
			if(!GeneUtil.writeFile(getDaoImplFileName(className), daoImplTemplate))
				errs.append("写daoImpl文件"+className+"DaoImpl时出错！");
		}else
            errs.append("读取daoTemplate和daoImplTemplate时出错！");

        return errs.toString();
	}
	
	/**
	 * 生成所有文件的dao的java文件
	 */
	public void doAll(){
		String[] classNames = GeneUtil.getClassNames();
		StringBuffer errs = new StringBuffer("日至：\n");
		if(classNames!=null&&classNames.length>0)
		for(int i=0;i<classNames.length;i++){
			String pk = GeneUtil.getPK(classNames[i]);
			errs.append(printDaoLayerFile(classNames[i],pk));
			errs.append("\n");
		}
		System.out.println(errs.toString());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        GeneDao geneDao = new GeneDao();
        geneDao.doAll();

	}

}
