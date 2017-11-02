package platform.social.dto;

import platform.social.vo.Comments;

public class CommentsQuery extends Comments{
	
	/** 是否只搜索顶级的comment, 如果为 1 说明只搜索parent级 **/
	private String searchparent;
	
	/** comment评论对象所属对象的id,例如:当objectType是learncontent时，该字段是productid **/
	private String objectparentid;
	/** objectid的字符串 **/
	private String objectidStr;
	/** parent commentid 字符串 **/
	private String parentcommentidStr;

    private String order_By_Clause;

    public CommentsQuery() {
       super();
    }

    public String getOrder_By_Clause() {
       return order_By_Clause;
    }

    public void setOrder_By_Clause(String order_By_Clause) {
       this.order_By_Clause = order_By_Clause;
    }

	public String getObjectparentid() {
		return objectparentid;
	}

	public void setObjectparentid(String objectparentid) {
		this.objectparentid = objectparentid;
	}

	public String getObjectidStr() {
		return objectidStr;
	}

	public void setObjectidStr(String objectidStr) {
		this.objectidStr = objectidStr;
	}

	public String getSearchparent() {
		return searchparent;
	}

	public void setSearchparent(String searchparent) {
		this.searchparent = searchparent;
	}

	public String getParentcommentidStr() {
		return parentcommentidStr;
	}

	public void setParentcommentidStr(String parentcommentidStr) {
		this.parentcommentidStr = parentcommentidStr;
	}
   
}
