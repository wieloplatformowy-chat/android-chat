package pl.sggw.wzim.chat.swagger.model;


import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class OnlineResponse  {
  
  @SerializedName("online")
  private Boolean online = null;

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Boolean getOnline() {
    return online;
  }
  public void setOnline(Boolean online) {
    this.online = online;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class OnlineResponse {\n");
    
    sb.append("  online: ").append(online).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
