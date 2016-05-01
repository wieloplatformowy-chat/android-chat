package pl.sggw.wzim.chat.swagger.model;


import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class ResponseError  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("message")
  private String message = null;
  @SerializedName("name")
  private String name = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseError {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  message: ").append(message).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
