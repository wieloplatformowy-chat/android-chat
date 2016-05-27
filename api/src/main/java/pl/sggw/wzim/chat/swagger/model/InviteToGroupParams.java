package pl.sggw.wzim.chat.swagger.model;

import java.util.*;

import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class InviteToGroupParams  {
  
  @SerializedName("groupId")
  private Long groupId = null;
  @SerializedName("userIds")
  private List<Long> userIds = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Long getGroupId() {
    return groupId;
  }
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public List<Long> getUserIds() {
    return userIds;
  }
  public void setUserIds(List<Long> userIds) {
    this.userIds = userIds;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class InviteToGroupParams {\n");
    
    sb.append("  groupId: ").append(groupId).append("\n");
    sb.append("  userIds: ").append(userIds).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
