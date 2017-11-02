package netTest.order.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import platform.logic.Container;
import platform.vo.Shop;
import commonTool.base.BaseVOBase;


public class Custorder extends BaseVOBase implements Container{

    private Long orderid;
    
    private Long shopid;
    
    private String shopname;

    private Long userid;

    private String ordercode;

    private String ordername;

    private Short ordertype;

    private Date ordertime;

    private Long baseaccountid;

    private Double fullcost;

    private Date fullpaytime;

    private Short payway;

    private Short paystatus;

    private Short orderstatus;
    
    /** 订单产品的集合 **/
    private List<Orderproduct> orderProdList;
    
    /** 订单审核者ID，如果为-1，则说明不是个人自动审核的，可能是系统自动审核订单 **/
    private Long operatorid;
    
    private Date approvedate;
    
    private String notes;
    
    private String appnotes;
    
    /** user's displayname **/
    private String userdisplayname;
    
    
    public final static String ObjectType = "custorder";


    public Long getOrderid() {
        return orderid;
    }


    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public Short getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Short ordertype) {
        this.ordertype = ordertype;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public Long getBaseaccountid() {
        return baseaccountid;
    }

    public void setBaseaccountid(Long baseaccountid) {
        this.baseaccountid = baseaccountid;
    }

    public Double getFullcost() {
        return this.fullcost;
    }

    public void setFullcost(Double fullcost) {
        this.fullcost = fullcost;
    }

    public Date getFullpaytime() {
        return fullpaytime;
    }

    public void setFullpaytime(Date fullpaytime) {
        this.fullpaytime = fullpaytime;
    }

    public Short getPayway() {
        return payway;
    }

    public void setPayway(Short payway) {
        this.payway = payway;
    }

    public Short getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(Short paystatus) {
        this.paystatus = paystatus;
    }

    public Short getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(Short orderstatus) {
        this.orderstatus = orderstatus;
    }


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public Date getApprovedate() {
		return approvedate;
	}


	public void setApprovedate(Date approvedate) {
		this.approvedate = approvedate;
	}

	public List<Orderproduct> getOrderProdList() {
		if(this.orderProdList==null){
			List<Orderproduct> listTemp = new ArrayList<Orderproduct>();
			this.setOrderProdList(listTemp);
		    return listTemp;
		}else
			return this.orderProdList;
	}

	public void setOrderProdList(List<Orderproduct> orderProdList) {
		this.orderProdList = orderProdList;
	}
	
	/**
	 * 判断该订单中是否包含传入的订单产品
	 * @param orderitem
	 * @return 该订单条目在订单集合中的位置
	 */
	public int hasOrderproduct(Orderproduct vo){
		if(this.orderProdList==null||this.orderProdList.size()==0)
			return -1;
		Orderproduct voTemp = null;
		for(int i=0;i<this.orderProdList.size();i++){
			voTemp = (Orderproduct)this.orderProdList.get(i);
			if(voTemp!=null&&vo.equals(voTemp))
				return i;
		}
		return -1;
	}
	
	/**
	 * 将订单产品加入到订单的产品列表中
	 * @param orderitem
	 * @return true:成功加入订单条目
	 * false:不能加入该订单条目，因为有重复订单条目
	 */
	public boolean addOrderproduct(Orderproduct vo){
		List list = this.getOrderProdList();
		if(list==null){
			list = new ArrayList();
			this.setOrderProdList(list);
		}
		if(hasOrderproduct(vo)>-1){
			return false;
		}else{
			list.add(vo);
			fullcost = ((fullcost==null)?0:fullcost)+vo.getSpendcost();
		}
		return true;
	}
	
	/**
	 * 将订单产品的集合加入到客户订单产品集合中
	 * 如果该订单产品已经存在其中则不添加该订单产品
	 * @param prodList
	 * @return 成功加入的商品订单产品数
	 */
	public int addOrderproductList(List prodList){
		if(prodList==null||prodList.size()<1)
			return 0;
		int count = 0;
		Orderproduct voTemp = null;
		for(int i=0;i<prodList.size();i++){
			voTemp = (Orderproduct)prodList.get(i);
			if(voTemp!=null&&hasOrderproduct(voTemp)>-1)
			   if(this.addOrderproduct(voTemp))
				  count++;
		}
		return count;
	}
	
	
	/**
	 * 从订单条目集合中删除一个订单条条目
	 * @param orderitem
	 * @return
	 */
	public boolean delOrderproduct(Orderproduct vo){
		List list = this.getOrderProdList();
		if(list==null)
			return true;
        int index = this.hasOrderproduct(vo);
        if(index==-1)
        	return true;
        list.remove(index);
        fullcost = getFullcost()-vo.getSpendcost();
        return true;
	}
	
	/**
	 * 从客户订单中删除选中的订单产品集合
	 * @param itemList
	 * @return
	 */
	public int delOrderproductList(List prodList){
		if(prodList==null||prodList.size()<1)
			return 0;
		int count = 0;
		Orderproduct voTemp = null;
		for(int i=0;i<prodList.size();i++){
			voTemp = (Orderproduct)prodList.get(i);
			if(voTemp!=null&&this.delOrderproduct(voTemp))
				count++;
		}
		return count;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getAppnotes() {
		return appnotes;
	}

	public void setAppnotes(String appnotes) {
		this.appnotes = appnotes;
	}

	@Override
	protected String getObjectType() {
		return ObjectType;
	}


	public String getUserdisplayname() {
		return userdisplayname;
	}


	public void setUserdisplayname(String userdisplayname) {
		this.userdisplayname = userdisplayname;
	}

	public Long getContainerID() {
		return shopid;
	}


	public String getContainerType() {
		return Shop.ObjectType;
	}


	public Long getOperatorid() {
		return operatorid;
	}


	public void setOperatorid(Long operatorid) {
		this.operatorid = operatorid;
	}
	
	public boolean isSameShop(Long shopid){
		if(shopid!=null && shopid.equals(this.shopid)){
			return true;
		}else {
			return false;
		}
	}

}