package netTest.orguser.logic;

import netTest.orguser.vo.Deptinfo;

public interface OrgLogic {

	/**
	 * 得到单位信息，这里的信息包括了单位的所有信息（上级单位名称）
	 * @param pk
	 * @return
	 */
	public abstract Deptinfo getDeptinfoByPK(Long pk);

	
	/**
	 * 根据shopid查找其对应的顶层单位
	 * @param shopid
	 * @return
	 */
	public Deptinfo qryTopDept(Long shopid);
	
	/**
	 * 生成形式如下的xml文件：
	  <?xml version="1.0"?>
		<tree>
			<tree text="Loads tree2.xml" src="tree2.xml"/>
			<tree text="Loads NOT_AVAILABLE.xml" src="NOT_AVAILABLE.xml"
				icon="images/xp/folder.png"/>
			<tree text="Loads emptytree.xml" src="emptytree.xml"
				icon="images/xp/folder.png"/>
			<tree text="Loaded Item 3" action="javascript:alert(3)" />
			<tree text="Loaded Item 4" action="javascript:alert(4)">
				<tree text="Loaded Item 4.1" action="javascript:alert(2.1)" />
				<tree text="Loaded Item 4.2">
					<tree text="Loaded Item 4.2.1">
						<tree text="Loaded Item 4.2.1.2"/>
						<tree text="Loaded Item 4.2.1.3"/>
					</tree>
					<tree text="Loaded Item 4.2.2"/>
				</tree>
			</tree>
			<tree text="WebFX Home" action="http://webfx.eae.net"
				icon="http://webfx.eae.net/images/favicon.gif"/>
	    </tree>
	 * @param pid:需要查看树的id,即要查询pid下级的数据
	 * @param action：要执行的js函数
	 * @param url：生成树的链接，即点击展开文件夹执行的生成树的命令
	 * @param target
	 * @param localeid
	 * @return
	 * @throws Exception
	 */
	public String createOrgTreeXml(Long pid,Long shopid,String action,String url,String target) throws Exception;

	
    /**
     * 查找该单位的所有上级单位，包括本单位。返回的单位的名称的字符串
     * @param orgbaseid
     * @return
     * @throws Exception
     */
    public String getTopOrgStr(String orgbaseidStr)throws Exception;
	
}