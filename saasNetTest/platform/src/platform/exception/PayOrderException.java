package platform.exception;

/**
 * 支付订单过程中的错误，可以用于回滚
 * @author fan
 *
 */
public class PayOrderException extends RuntimeException {

	public PayOrderException(){
		super();
	}
	
	public PayOrderException(String message){
		super(message);
	}
	
}
