package pl.sggw.wzim.chat.swagger.model;

import java.sql.Timestamp;

import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class MessageResponse  {
  
  @SerializedName("conversationId")
  private Long conversationId = null;
  @SerializedName("date")
  private Timestamp date = null;
  @SerializedName("id")
  private Long id = null;
  @SerializedName("messaage")
  private String messaage = null;
  @SerializedName("userId")
  private Long userId = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Long getConversationId() {
    return conversationId;
  }
  public void setConversationId(Long conversationId) {
    this.conversationId = conversationId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Timestamp getDate() {
    return date;
  }
  public void setDate(Timestamp date) {
    this.date = date;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getMessaage() {
    return messaage;
  }
  public void setMessaage(String messaage) {
    this.messaage = messaage;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageResponse {\n");
    
    sb.append("  conversationId: ").append(conversationId).append("\n");
    sb.append("  date: ").append(date).append("\n");
    sb.append("  id: ").append(id).append("\n");
    sb.append("  messaage: ").append(messaage).append("\n");
    sb.append("  userId: ").append(userId).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
