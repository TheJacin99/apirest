package es.itx.prices.exception;

public class CustomException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 107009007794814700L;

	public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}