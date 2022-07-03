package sdapi.controllers.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
	
	List<FieldMessage> fieldMessage = new ArrayList<>();

	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return fieldMessage;
	}

	public void addError(String fieldName, String message) {
		fieldMessage.add(new FieldMessage(fieldName,message));
	}

}
