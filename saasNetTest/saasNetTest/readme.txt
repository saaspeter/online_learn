
1. 关于action出现的异常的处理，如果是通用异常或系统级异常，则直接由在struts配置文件中配置的异常处理类来处理。
    如果是应用程序跑出来的异常，需要有特殊的文字描述说明给用户看得，用action提供的方法forwardErrorPage来转向错误页面。
2. 缓存方案用ehcache，目前的缓存的方法只能用简单的参数类型，不能用复合类和集合类型参数

3. SysparamConstant.syscode由各个系统启动时初始化


------------------------- springside security 配置改动 -------------------------
CasProxyTicketValidatorEx
org.acegisecurity.providers.cas.ticketvalidator.CasProxyTicketValidator


CasAuthenProviderEx
org.acegisecurity.providers.cas.CasAuthenticationProvider


CasProcessingShopFilter
org.acegisecurity.ui.cas.CasProcessingFilter

------------------------ struts 标签的改动 -------------------------------
1. 对于下拉菜单，如果需要显示国家语言，则需要用：localeListSet="true"
   <html:select name="shopForm" property="queryVO.shopstatus" >
		<html:optionsSaas localeListSet="true"/>
   </html:select>
   
   对于bean write:  showLocaleName="true" 
   <bean:writeSaas showLocaleName="true" name="newscategoryForm" property="defaultlocaleid"/>

--------------////////////// 常用系统链接地址 //////////////----------------
---------------- netTest 常用系统链接 ---------------------
1. 选择单位的链接：

   function selOrg(){
	   var url = "/org/orgbase.do?method=toTree&orgType=1&urlType=2&selectType=1";
	   newDiv(url,1,1,300,400);
   }
	    
   function selectOrg_CB(ids,names,param){
       if(ids!=null&&ids!=""){
          document.getElementById("createorgidId").value=ids;
          document.getElementById("createorgnameId").value=names;
       }
       clearDiv();
   }

考试创建单位:  <input id="createorgnameId" type="text" name="queryVO.createorgname" value="<bean:write name="testinfoForm" property="queryVO.createorgname"/>" readonly="readonly" onclick="selOrg()" />
           <input id="createorgidId" type="hidden" name="queryVO.createorgid" value="<bean:write name="testinfoForm" property="queryVO.createorgid"/>" />

-------------------------- 部署相关 -------------------------
1. 对于struts标签中需要用到<% %>中有引号的地方，在tomcat5是不报错的，但是升级到tomcat6就会报
   is quoted with " which must be escaped when used within the value的错误。
   此时需要在tomcat下conf目录下的catalina.properties文件中添加一行
        org.apache.jasper.compiler.Parser.STRICT_QUOTE_ESCAPING=false
   
2. 系统的权限数据: resources, resourcesValue, role_resc表，类ResourceSQLGeneTool用来生成这些数据

---------------------- web video play installtion ---------------------
current, we use mediaelementjs player
1. first add below into tomcat/conf/web.xml file
   	<mime-mapping>
        <extension>ogg</extension>
        <mime-type>video/ogg</mime-type>
    </mime-mapping>
	
	<mime-mapping>
        <extension>webm</extension>
        <mime-type>video/webm</mime-type>
    </mime-mapping>

