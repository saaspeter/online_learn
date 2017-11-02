package platform.logic;

public interface Container {

	public String getContainerType();
	
	public Long getContainerID();
	
	public boolean isSameShop(Long shopid);
}
