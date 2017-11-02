package demo;

import java.util.ArrayList;
import java.util.List;

public class SqlTemplateVO {

//	类名：{[#className#]}
	private String className;
	
//	表名：{[#tableName#]}
	private String tableName;
	
//	主键：{[#pk#]},是数据表中的pk名称
	private String pk;
	
//	主键值：{[#pkValue#]}
	private String pkValue;
	
//  主键值在类中的属性
	private String pkProperty;
	
//	select字段名列表：{[#columnsList_select#]}    ||例如：userID,name,account等，每个字段各占一行
	private String columnsList_select;
	
//	select字段（withoutBlobs）：{[#columns_withoutBlobs_List_select#]}
	private String columns_withoutBlobs_List_select;
	
//	插入字段值：{[#columnValuesList_insert#]}    ||例如：#userid:BIGINT#, #name:VARCHAR#, #account:VARCHAR#, #nickname:VARCHAR#,
	private String columnValuesList_insert;
	
//	包名：{{#packageName#}}   ||例如：platform.vo  
	private String packageName = GeneUtil.PackageVO;
	
//	包名(Query)：{{#packageQuery#}}    ||例如：platform.voQuery
	private String packageQuery = GeneUtil.PackageDto;
	
//	result(withoutBlobs)的集合：{[#result_withoutBlobs#]}    ||例如：<result column="userID" property="userid" jdbcType="BIGINT"/>集合
	private String result_withoutBlobs;
	
//	result(Blobs)的集合：{[#result_Blobs#]}    ||例如：<result column="notes" property="notes" jdbcType="LONGVARCHAR"/>集合
	private String result_Blobs;
	
//	result(all)的集合：{[#results#]}    ||例如：<result column="notes" property="notes" jdbcType="LONGVARCHAR"/>集合
	private String results;
	
//	whereClause：{[#where_Clause#]}    ||例如：<isNotNull prepend="and" property="userid"><![CDATA[ userid=#userid:BIGINT# ]]></isNotNull> 
	private String where_Clause;
	
//	updateSetClause：{[#updateSetClaus#]}     ||例如：<isNotNull prepend="," property="name">name = #name:VARCHAR#</isNotNull>，
//  记住不含主键
	private String updateSetClause;
	
	// 记录所有字段的信息
	private List columnVO_all = new ArrayList();
	
	// 记录不是blob字段的信息
	private List columnVO_withoutBlobs = new ArrayList();
	
	// 记录是Blob类型的字段
	private List columnVO_Blobs = new ArrayList();

	public SqlTemplateVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 返回除了主键以外的ColumnVO的集合
	public List getColumnVO_all_noPK(){
		List listRtn = new ArrayList();
		String pkPropertyTemp = "";
		for(int i=0;i<columnVO_all.size();i++){
			pkPropertyTemp = ((ColumnVO)columnVO_all.get(i)).getProperty();
			if(pkPropertyTemp!=null&&pkProperty!=null&&(pkPropertyTemp.trim().equals(pkProperty.trim()))){
				continue;
			}else{
				listRtn.add(columnVO_all.get(i));
			}
		}
		return listRtn;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getColumns_withoutBlobs_List_select() {
		return columns_withoutBlobs_List_select;
	}

	public void setColumns_withoutBlobs_List_select(
			String columns_withoutBlobs_List_select) {
		this.columns_withoutBlobs_List_select = columns_withoutBlobs_List_select;
	}

	public String getColumnsList_select() {
		return columnsList_select;
	}

	public void setColumnsList_select(String columnsList_select) {
		this.columnsList_select = columnsList_select;
	}

	public String getColumnValuesList_insert() {
		return columnValuesList_insert;
	}

	public void setColumnValuesList_insert(String columnValuesList_insert) {
		this.columnValuesList_insert = columnValuesList_insert;
	}

	public List getColumnVO_all() {
		return columnVO_all;
	}

	public void setColumnVO_all(List columnVO_all) {
		this.columnVO_all = columnVO_all;
	}

	public List getColumnVO_Blobs() {
		return columnVO_Blobs;
	}

	public void setColumnVO_Blobs(List columnVO_Blobs) {
		this.columnVO_Blobs = columnVO_Blobs;
	}

	public List getColumnVO_withoutBlobs() {
		return columnVO_withoutBlobs;
	}

	public void setColumnVO_withoutBlobs(List columnVO_withoutBlobs) {
		this.columnVO_withoutBlobs = columnVO_withoutBlobs;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageQuery() {
		return packageQuery;
	}

	public void setPackageQuery(String packageQuery) {
		this.packageQuery = packageQuery;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getPkValue() {
		return pkValue;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

	public String getResult_Blobs() {
		return result_Blobs;
	}

	public void setResult_Blobs(String result_Blobs) {
		this.result_Blobs = result_Blobs;
	}

	public String getResult_withoutBlobs() {
		return result_withoutBlobs;
	}

	public void setResult_withoutBlobs(String result_withoutBlobs) {
		this.result_withoutBlobs = result_withoutBlobs;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getUpdateSetClause() {
		return updateSetClause;
	}

	public void setUpdateSetClause(String updateSetClause) {
		this.updateSetClause = updateSetClause;
	}

	public String getWhere_Clause() {
		return where_Clause;
	}

	public void setWhere_Clause(String where_Clause) {
		this.where_Clause = where_Clause;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getPkProperty() {
		return pkProperty;
	}

	public void setPkProperty(String pkProperty) {
		this.pkProperty = pkProperty;
	}
	
}
