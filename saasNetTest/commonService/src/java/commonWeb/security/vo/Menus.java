package commonWeb.security.vo;

public class Menus extends Resources{

    private String title;

    private String target;

    private String onclick;

    private String onmouseover;

    private String onmouseout;

    private String image;

    private String altimage;

    private String tooltip;

    private String page;

    private String width;

    private String height;

    private String forward;

    private String action;

    private Short menutype;

    public Menus(){
    	
    }
    
    public Menus(Resources vo){
    	if(vo instanceof Menus)
    	{
    		Menus temp = ((Menus)vo);
    		this.action=temp.getAction();
    		this.altimage=temp.getAltimage();
    		this.descn=temp.getDescn();
    		this.forward=temp.getForward();
    		this.height=temp.getHeight();
    		this.id=temp.getId();
    		this.image=temp.getImage();
    		this.menutype=temp.getMenutype();
    		this.name=temp.getName();
    		this.onclick=temp.getOnclick();
    		this.onmouseout=temp.getOnmouseout();
    		this.onmouseover=temp.getOnmouseover();
    		this.ordNo=temp.getOrdNo();
    		this.page=temp.getPage();
    		this.resString=temp.getResString();
    		this.resType=temp.getResType();
    		this.operType=temp.getOperType();
    		this.status=temp.getStatus();
    		this.syscode=temp.getSyscode();
    		this.target=temp.getTarget();
    		this.title=temp.getTitle();
    		this.tooltip=temp.getTooltip();
    		this.pid=temp.getPid();
    		this.path = temp.getPath();
    		this.rescfold = temp.getRescfold();
    		this.linkid=temp.getLinkid();
    		this.visible=temp.getVisible();
    		this.pidName=temp.getPidName();
    		this.width=temp.getWidth();
    	}else if(vo!=null){
    		this.descn=vo.getDescn();
    		this.id=vo.getId();
    		this.name=vo.getName();
    		this.ordNo=vo.getOrdNo();
    		this.resString=vo.getResString();
    		this.resType=vo.getResType();
    		this.operType=vo.getOperType();
    		this.status=vo.getStatus();
    		this.syscode=vo.getSyscode();
    		this.pid=vo.getPid();
    		this.path = vo.getPath();
    		this.rescfold = vo.getRescfold();
    		this.linkid=vo.getLinkid();
    		this.visible=vo.getVisible();
    		this.pidName=vo.getPidName();
    	}
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnmouseover() {
        return onmouseover;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getOnmouseout() {
        return onmouseout;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAltimage() {
        return altimage;
    }

    public void setAltimage(String altimage) {
        this.altimage = altimage;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Short getMenutype() {
        return menutype;
    }

    public void setMenutype(Short menutype) {
        this.menutype = menutype;
    }
    
    /**
     * 判断菜单的属性是否为空，仅仅判断菜单的属性
     * @return
     */
    public boolean isMenuEmpty(){
    	boolean ret = true;
    	if(title!=null&&title.trim().length()>0)
    		return false;
    	if(target!=null&&target.trim().length()>0)
    		return false;
    	if(onclick!=null&&onclick.trim().length()>0)
    		return false;
    	if(onmouseover!=null&&onmouseover.trim().length()>0)
    		return false;
    	if(onmouseout!=null&&onmouseout.trim().length()>0)
    		return false;
    	if(image!=null&&image.trim().length()>0)
    		return false;
    	if(altimage!=null&&altimage.trim().length()>0)
    		return false;
    	if(tooltip!=null&&tooltip.trim().length()>0)
    		return false;
    	if(page!=null&&page.trim().length()>0)
    		return false;
    	if(width!=null&&width.trim().length()>0)
    		return false;
    	if(height!=null&&height.trim().length()>0)
    		return false;
    	if(forward!=null&&forward.trim().length()>0)
    		return false;
    	if(action!=null&&action.trim().length()>0)
    		return false;
    	if(menutype!=null)
    		return false;
    	
    	return ret;	
    }
    
}