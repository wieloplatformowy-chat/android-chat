package pl.sggw.wzim.chat.swagger.model;

import pl.sggw.wzim.chat.swagger.model.ResponseError;

import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class BaseResponse  {
  
  @SerializedName("error")
  private ResponseError error = null;
  @SerializedName("success")
  private Boolean success = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  public ResponseError getError() {
    return error;
  }
  public void setError(ResponseError error) {
    this.error = error;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Boolean getSuccess() {
    return success;
  }
  public void setSuccess(Boolean success) {
    this.success = success;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class BaseResponse {\n");
    
    sb.append("  error: ").append(error).append("\n");
    sb.append("  success: ").append(success).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
