package pl.sggw.wzim.chat.swagger.model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@ApiModel(description = "")
public class UserResponse  {
  private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

  @SerializedName("email")
  private String email = null;
  @SerializedName("id")
  private Long id = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("avatar")
  private Bitmap avatar = null;
  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public Bitmap getAvatar() { return avatar != null ? avatar : getGravatar(); }
  public void setAvatar(Bitmap avatar) { this.avatar = avatar; }

  public UserResponse(){ getGravatar(); }

  private Bitmap getGravatar()
  {
    String avatarURL = GRAVATAR_URL + md5Hex(email.toLowerCase().trim()) + ".jpg";
    try
    {
      HttpURLConnection connection = (HttpURLConnection) new URL(avatarURL).openConnection();
      connection.connect();
      InputStream input = connection.getInputStream();
      avatar = BitmapFactory.decodeStream(input);
      return avatar;
    } catch (Exception e)
    {
      return null;
    }
  }

  private String md5Hex (String message) {
    try {
      MessageDigest md =
              MessageDigest.getInstance("MD5");
      return hex(md.digest(message.getBytes("CP1252")));
    } catch (NoSuchAlgorithmException e) {
    } catch (UnsupportedEncodingException e) {
    }
    return null;
  }

  private String hex(byte[] array) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < array.length; ++i) {
      sb.append(Integer.toHexString((array[i]
              & 0xFF) | 0x100).substring(1,3));
    }
    return sb.toString();
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserResponse {\n");
    
    sb.append("  email: ").append(email).append("\n");
    sb.append("  id: ").append(id).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
