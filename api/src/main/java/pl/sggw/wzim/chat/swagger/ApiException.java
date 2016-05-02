package pl.sggw.wzim.chat.swagger;

public class ApiException extends Exception {
  Integer code = 0;
  String message = null;

  public ApiException() {}

  public ApiException(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }
  
  public void setCode(Integer code) {
    this.code = code;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
}