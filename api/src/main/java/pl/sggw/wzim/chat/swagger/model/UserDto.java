package pl.sggw.wzim.chat.swagger.model;


import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class UserDto  {
  
  @SerializedName("name")
  private String name = null;
  @SerializedName("password")
  private String password = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDto {\n");
    
    sb.append("  name: ").append(name).append("\n");
    sb.append("  password: ").append(password).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
