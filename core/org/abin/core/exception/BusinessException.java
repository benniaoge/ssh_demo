/**
 * 
 */
package org.abin.core.exception;

/**
 * @author ZhangBin
 *
 */
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String target;
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, String target) {
		super(message);
		this.target = target;
	}
	
	public BusinessException(String message, Throwable cause){
		super(message, cause);
	}
	
	public BusinessException(String message, Throwable cause, String target) {
		super(message,cause);
		this.target = target;
	}
	
	public String getTarget() {
		return target;
	}

}
