Notes:
   a. 如果功能模块权限变化，需要重新生成resources, resourcesValue, role_resc表中数据，则执行 changeSecurity_Resource.sql
   b. the comment prefix - length cannot exceed 4, like this: ----
   c. when truncate table, try to truncate child table first then parent table, if still has problem, then wrap truncate sql with
      SET FOREIGN_KEY_CHECKS=0;   and  SET FOREIGN_KEY_CHECKS=1;

先执行建表语句：
1. platform.sql  -- 基本platform表，包括user等
2. security.sql  -- 安全权限资源表
3. netTest.sql
4. P_InsPapQue_Common.sql 
5. P_InsertPaperQues.sql -- 存储过程，用到 P_InsPapQue_Common
6. P_SelPaperQues.sql    -- 选择paper question的存储过程，用到 P_InsertPaperQues

再执行插入初始数据的sql:
7. data_baseInit.sql  -- 基础参数数据，包括：i18n, system parameters 等
        
		i18n
		countryregion
		emailtemplate

		sysconst
		sysconstval
		sysconstitem
		sysconstitemval
		sysparam
		sysfunctionitem

8. data_security.sql  -- 基础权限资源数据

		resources
		resourcesvalue
		roles
		rolesvalue
		role_resc

9. data_business.sql  -- 初始化基础数据，包括: 初始人员，category等

		---- first super user ----
		user        -- add first user: _superalexander, pass: alex@20130504
		user_role   -- add role for first supreAdmin
		usercontactinfo  -- add one record for first superadmin
		CustInfoEx   -- add one record for first superadmin
		CustStatus   -- add one record for first superadmin
		superuser   -- add one super user e.g: _superalexander
		useraccountsetting  -- add one record for first superAdmin

		---- 系统默认目录 ----
		productcategory
		productcategoryvalue
		sysproductcate
		newscategory  -- 初始自己创建的

