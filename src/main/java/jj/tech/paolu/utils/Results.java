package jj.tech.paolu.utils;

public class Results {
	public Boolean isOk;
	public String  message;
	public Object data;
	
	
	public Results() {
	}
	
	public Results(Boolean isOk, String  message, Object data) {
		this.isOk=isOk;
		this.message=message;
		this.data=data;
	} 
	
	public static Results SUCCESS(){
		return new Results(true,"", null);
	}
	
	public static Results SUCCESS(String message){
		return new Results(true, message==null ? "" : message, null);
	}
	
	public static Results SUCCESS(String message, Object data){
		return new Results(true, message==null ? "" : message, data);
	}
	public static Results SUCCESS(Object data){
		return new Results(true, "" , data);
	}
	
	
	
	public static Results FALSE(){
		return new Results(false,"", null);
	}
	public static Results FALSE(String message){
		return new Results(false, message==null ? "" : message, null);
	}
	public static Results FALSE(String message, Object data){
		return new Results(false, message==null ? "" : message, data);
	}
	public static Results FALSE(Object data){
		return new Results(true, "" , data);
	}

	
	
	
	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
