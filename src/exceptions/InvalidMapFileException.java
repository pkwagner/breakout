package exceptions;

public class InvalidMapFileException extends Exception {

	public InvalidMapFileException() {
		super("The loaded map-file is invalid");
	}

}
