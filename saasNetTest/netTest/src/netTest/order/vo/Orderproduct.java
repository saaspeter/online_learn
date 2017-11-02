package netTest.order.vo;

import netTest.common.vo.NettestBaseVO;

public class Orderproduct extends NettestBaseVO{

	private Long orderid;
		
	private String ordercode;
    
    private Long userid;

    private Double productprice;
   
    private Double cost;
    
    private Integer quantity;
    
    private Short status;
    
    private Short paytype;
    
    private Integer uselimitnum;

    public final static String ObjectType = "orderproduct";


    public Double getProductprice() {
        return productprice;
    }


    public void setProductprice(Double productprice) {
        this.productprice = productprice;
    }


	public Long getOrderid() {
		return orderid;
	}


	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	
    /**
     * 得到该订单条目的花费:单价*数量*折扣
     * @return
     */
    public Double getSpendcost(){
    	int quantityTemp = 1;
    	double result = 0;
    	if(quantity!=null&&quantity.intValue()>0)
    		quantityTemp = quantity.intValue();
    	if(productprice!=null)
    	    result = productprice.doubleValue()*quantityTemp;
    	return new Double(result);
    }
	
    /**
     * 根据业务数据判断两个订单产品是否相同
     * @param orderitem
     * @return
     */
    public boolean equals(Orderproduct vo){
    	if(vo==null)
    		return false;
    	if((this.getOrderid()!=null&&this.getOrderid().equals(vo.getOrderid())
    		||this.getOrderid()==null&&vo.getOrderid()==null)
    		&&this.getProductbaseid()!=null
    		&&this.getProductbaseid().equals(vo.getProductbaseid()))
    	    return true;
    	else
    		return false;
    }


	public Double getCost() {
		return cost;
	}


	public void setCost(Double cost) {
		this.cost = cost;
	}


	public Integer getQuantity() {
		if(quantity==null)
		   return 1;
		else
		   return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public Short getPaytype() {
		return paytype;
	}


	public void setPaytype(Short paytype) {
		this.paytype = paytype;
	}


	public Integer getUselimitnum() {
		return uselimitnum;
	}


	public void setUselimitnum(Integer uselimitnum) {
		this.uselimitnum = uselimitnum;
	}


	public Short getStatus() {
		return status;
	}


	public void setStatus(Short status) {
		this.status = status;
	}


	public String getShopname() {
		return shopname;
	}


	public void setShopname(String shopname) {
		this.shopname = shopname;
	}


	@Override
	protected String getObjectType() {
		return ObjectType;
	}
    
}