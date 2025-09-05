package arithlang;

public class DynamicError extends RuntimeException {
	// see docs: https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html
	public DynamicError(String message) { // constructor
		super(message);
	}
}
