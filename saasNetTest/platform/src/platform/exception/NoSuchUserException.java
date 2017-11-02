package platform.exception;

/**
 * 找不到用户的异常
 * @author fan
 *
 */
public class NoSuchUserException extends RuntimeException {

	public NoSuchUserException(){
		super();
	}
	
	public NoSuchUserException(String message){
		super(message);
	}
	
}
