package platform.exception;

/**
 * 如果对已被审批不同过的订单条目进行支付，抛出此错误
 * @author fan
 */
public class CannotPayDenyItemException extends RuntimeException {

	public CannotPayDenyItemException(){
		super();
	}
	
	public CannotPayDenyItemException(String message){
		super(message);
	}
	
}
