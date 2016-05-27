package pl.sggw.wzim.chat.swagger.model;


import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class RenameGroupParams  {
  
  @SerializedName("groupId")
  private Long groupId = null;
  @SerializedName("newName")
  private String newName = null;

  
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
  public String getNewName() {
    return newName;
  }
  public void setNewName(String newName) {
    this.newName = newName;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class RenameGroupParams {\n");
    
    sb.append("  groupId: ").append(groupId).append("\n");
    sb.append("  newName: ").append(newName).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
