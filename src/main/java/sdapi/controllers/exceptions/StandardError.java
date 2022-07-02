package sdapi.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StandardError{

	private Long timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;

}
