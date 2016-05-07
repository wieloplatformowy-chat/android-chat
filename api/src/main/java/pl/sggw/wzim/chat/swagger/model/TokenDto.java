package pl.sggw.wzim.chat.swagger.model;


import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class TokenDto  {
  
  @SerializedName("token")
  private String token = null;

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class TokenDto {\n");
    
    sb.append("  token: ").append(token).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
