Notes:
   a. �������ģ��Ȩ�ޱ仯����Ҫ��������resources, resourcesValue, role_resc�������ݣ���ִ�� changeSecurity_Resource.sql
   b. the comment prefix - length cannot exceed 4, like this: ----
   c. when truncate table, try to truncate child table first then parent table, if still has problem, then wrap truncate sql with
      SET FOREIGN_KEY_CHECKS=0;   and  SET FOREIGN_KEY_CHECKS=1;

��ִ�н�����䣺
1. platform.sql  -- ����platform������user��
2. security.sql  -- ��ȫȨ����Դ��
3. netTest.sql
4. P_InsPapQue_Common.sql 
5. P_InsertPaperQues.sql -- �洢���̣��õ� P_InsPapQue_Common
6. P_SelPaperQues.sql    -- ѡ��paper question�Ĵ洢���̣��õ� P_InsertPaperQues

��ִ�в����ʼ���ݵ�sql:
7. data_baseInit.sql  -- �����������ݣ�������i18n, system parameters ��
        
		i18n
		countryregion
		emailtemplate

		sysconst
		sysconstval
		sysconstitem
		sysconstitemval
		sysparam
		sysfunctionitem

8. data_security.sql  -- ����Ȩ����Դ����

		resources
		resourcesvalue
		roles
		rolesvalue
		role_resc

9. data_business.sql  -- ��ʼ���������ݣ�����: ��ʼ��Ա��category��

		---- first super user ----
		user        -- add first user: _superalexander, pass: alex@20130504
		user_role   -- add role for first supreAdmin
		usercontactinfo  -- add one record for first superadmin
		CustInfoEx   -- add one record for first superadmin
		CustStatus   -- add one record for first superadmin
		superuser   -- add one super user e.g: _superalexander
		useraccountsetting  -- add one record for first superAdmin

		---- ϵͳĬ��Ŀ¼ ----
		productcategory
		productcategoryvalue
		sysproductcate
		newscategory  -- ��ʼ�Լ�������

