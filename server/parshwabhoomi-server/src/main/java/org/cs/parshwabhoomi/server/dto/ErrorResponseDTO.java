/**
 * 
 */
package org.cs.parshwabhoomi.server.dto;

/**
 * @author saurabh
 *
 */
public class ErrorResponseDTO extends AbstractResponseDTO {

	public ErrorResponseDTO() {
		super();
	}
	
	public ErrorResponseDTO(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	private int code;
	private String message;
	
	public enum HTTP_STATUS_CODE {
		BAD_REQUEST(4400),
		UNAUTHORIZED(4401),
		FORBIDDEN(4403),
		NOT_FOUND(4404),
		CONFLICT(4409),
		INTERNAL_SERVER_ERROR(5500);
		
		
		private int code;
		
		private HTTP_STATUS_CODE(int code) {
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}
		
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

	
}
