package platform.logic;

import platform.vo.Shop;

public class ContainerFactory {
	
	private static ContainerFactory instance;
	
	public static ContainerFactory getInstance(){
		if(instance==null){
			instance = new ContainerFactory();
		}
		return instance;
	}

	public Container createContainer(String containerID, String containerType, Object containerObj){
		if(containerID!=null&&containerID.trim().length()>0&&containerType!=null
				&&containerType.trim().length()>0){
		    return new DefaultContainer(containerID, containerType);
		}else {
			return null;
		}
	}
	
	private class DefaultContainer implements Container{
		private Long containerID; 
		private String containerType;
		
		public DefaultContainer(String containerID, String containerType){
			this.containerID = new Long(containerID);
			this.containerType = containerType;
		}

		public Long getContainerID() {
			return containerID;
		}

		public String getContainerType() {
			return containerType;
		}
		
		public boolean isSameShop(Long shopid){
			boolean ret = false;
			if(Shop.ObjectType.equals(containerType)){
				ret = (containerID!=null)?containerID.equals(shopid):false;
			}else {
				// TODO load container object, then call isSameShop of this object
			}
			return ret;
		}
		
	}
	
}
