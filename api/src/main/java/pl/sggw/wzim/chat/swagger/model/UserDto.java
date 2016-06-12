package pl.sggw.wzim.chat.swagger.model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import io.swagger.annotations.*;
import com.google.gson.annotations.SerializedName;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;

@ApiModel(description = "")
public class UserDto  {

  private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

  @SerializedName("email")
  private String email = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("password")
  private String password = null;
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
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public Bitmap getAvatar() { return avatar != null ? avatar : getGravatar(); }
  public void setAvatar(Bitmap avatar) { this.avatar = avatar; }

  public UserDto(){ getAvatar(); }

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
    sb.append("class UserDto {\n");
    
    sb.append("  email: ").append(email).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  password: ").append(password).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
