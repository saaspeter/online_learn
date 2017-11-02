package netTest.product.vo;

import platform.logic.Container;

public class Productbase extends ProductMini implements Container{

    protected Double productprice;
    
    protected Short paytype;
    
    protected Integer uselimitnum;
    
    protected Short cantry;
    
    protected Short tryusetype;

    protected Integer tryuselimitnum;
    
    protected Short isneedapprove;
    
    protected Short promotable;
    
    protected Short isopen;
    
    protected Short status;
    
    protected String logoimage;

    protected String productdesc;

    // below is satis information of product
    /** 题目题目数量
     * @deprecated 暂时不用，因为学员不能直接使用题库题目
     *  **/
    protected Long warequesnum;
    /** 学习资料文档数量 **/
    protected Integer learncontdocnum;
    /** 学习资料多媒体资源数量 **/
    protected Integer learncontmedianum;
    /** 试卷数量
     * @deprecated 暂时不用，因为学员不能直接使用试卷，必须通过考试或练习
     *  **/
    protected Integer papernum;
    /** 练习数量 **/
    protected Integer exercisenum;
    /** 考试数量 **/
    protected Integer testinfonum;
    /** 正在学习本课程人数 **/
    protected Integer currentlearnusernum;

	/** 所有学过本课程人数 **/
    protected Integer alllearnedusernum;
    
    // 不对应db字段
    protected String shopcode;
    
    /** 是否是系统推荐的产品, 1为系统推荐的产品，0为不限制 **/
    protected Short issysgoodproduct;
    /** 对应goodproduct中的scope字段 **/
    protected Short goodproductscope;

    public Double getProductprice() {
        return productprice;
    }

    public void setProductprice(Double productprice) {
        this.productprice = productprice;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc;
    }

    public Short getPaytype() {
        return paytype;
    }

    public void setPaytype(Short paytype) {
        this.paytype = paytype;
    }

	public Short getIsneedapprove() {
		return isneedapprove;
	}

	public void setIsneedapprove(Short isneedapprove) {
		this.isneedapprove = isneedapprove;
	}

	public Integer getUselimitnum() {
		return uselimitnum;
	}

	public void setUselimitnum(Integer uselimitnum) {
		this.uselimitnum = uselimitnum;
	}

	public Short getCantry() {
		return cantry;
	}

	public void setCantry(Short cantry) {
		this.cantry = cantry;
	}

	public Integer getTryuselimitnum() {
		return tryuselimitnum;
	}

	public void setTryuselimitnum(Integer tryuselimitnum) {
		this.tryuselimitnum = tryuselimitnum;
	}

	public Short getTryusetype() {
		return tryusetype;
	}

	public void setTryusetype(Short tryusetype) {
		this.tryusetype = tryusetype;
	}

	public Short getPromotable() {
		return promotable;
	}

	public void setPromotable(Short promotable) {
		this.promotable = promotable;
	}

	public Short getIsopen() {
		return isopen;
	}

	public void setIsopen(Short isopen) {
		this.isopen = isopen;
	}

	public Short getIssysgoodproduct() {
		return issysgoodproduct;
	}

	public void setIssysgoodproduct(Short issysgoodproduct) {
		this.issysgoodproduct = issysgoodproduct;
	}
	
	/**
	 * 产品是否有附属信息
	 * @return
	 */
	public boolean hasExtData(){
		if(this.getProductdesc()!=null&&this.getProductdesc().trim().length()>0)
		{
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 产品是否有附属信息
	 * @return
	 */
	public boolean hasSatisData(){
		if(this.getExercisenum()!=null||this.getLearncontdocnum()!=null
			||this.getLearncontmedianum()!=null||this.getPapernum()!=null
			||this.getTestinfonum()!=null||this.getWarequesnum()!=null
			||this.getCurrentlearnusernum()!=null||this.getAlllearnedusernum()!=null)
		{
			return true;
		}else{
			return false;
		}
	}

	public Integer getExercisenum() {
		if(exercisenum==null)
			exercisenum = 0;
		return exercisenum;
	}

	public void setExercisenum(Integer exercisenum) {
		this.exercisenum = exercisenum;
	}

	public Integer getLearncontdocnum() {
		if(learncontdocnum==null)
			learncontdocnum = 0;
		return learncontdocnum;
	}

	public void setLearncontdocnum(Integer learncontdocnum) {
		this.learncontdocnum = learncontdocnum;
	}

	public Integer getLearncontmedianum() {
		if(learncontmedianum==null)
			learncontmedianum = 0;
		return learncontmedianum;
	}

	public void setLearncontmedianum(Integer learncontmedianum) {
		this.learncontmedianum = learncontmedianum;
	}

	public Integer getPapernum() {
		if(papernum==null)
			papernum = 0;
		return papernum;
	}

	public void setPapernum(Integer papernum) {
		this.papernum = papernum;
	}

	public Integer getTestinfonum() {
		if(testinfonum==null)
			testinfonum = 0;
		return testinfonum;
	}

	public void setTestinfonum(Integer testinfonum) {
		this.testinfonum = testinfonum;
	}

	public Long getWarequesnum() {
		if(warequesnum==null)
			warequesnum = 0l;
		return warequesnum;
	}

	public void setWarequesnum(Long warequesnum) {
		this.warequesnum = warequesnum;
	}

	public Long getContainerID() {
		return productbaseid;
	}

	public String getContainerType() {
		return ProductMini.ObjectType;
	}
	
	public boolean isSameShop(Long shopid){
		if(shopid!=null && shopid.equals(this.shopid)){
			return true;
		}else {
			return false;
		}
	}

	public String getShopcode() {
		return shopcode;
	}

	public void setShopcode(String shopcode) {
		this.shopcode = shopcode;
	}
	
    public Integer getCurrentlearnusernum() {
		return currentlearnusernum;
	}

	public void setCurrentlearnusernum(Integer currentlearnusernum) {
		this.currentlearnusernum = currentlearnusernum;
	}

	public Integer getAlllearnedusernum() {
		return alllearnedusernum;
	}

	public void setAlllearnedusernum(Integer alllearnedusernum) {
		this.alllearnedusernum = alllearnedusernum;
	}

	public Short getGoodproductscope() {
		return goodproductscope;
	}

	public void setGoodproductscope(Short goodproductscope) {
		this.goodproductscope = goodproductscope;
	}

	public String getLogoimage() {
		return logoimage;
	}

	public void setLogoimage(String logoimage) {
		this.logoimage = logoimage;
	}
    
}