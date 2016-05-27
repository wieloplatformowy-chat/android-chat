package pl.sggw.wzim.chat.swagger.model;


import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class SendMessageParams  {
  
  @SerializedName("conversationId")
  private Long conversationId = null;
  @SerializedName("message")
  private String message = null;

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Long getConversationId() {
    return conversationId;
  }
  public void setConversationId(Long conversationId) {
    this.conversationId = conversationId;
  }

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class SendMessageParams {\n");
    
    sb.append("  conversationId: ").append(conversationId).append("\n");
    sb.append("  message: ").append(message).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
