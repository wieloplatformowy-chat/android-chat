package pl.sggw.wzim.chat.swagger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import pl.sggw.wzim.chat.swagger.model.*;

public class JsonUtil {
  public static GsonBuilder gsonBuilder;

  static {
    gsonBuilder = new GsonBuilder();
    gsonBuilder.serializeNulls();
    gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
  }

  public static Gson getGson() {
    return gsonBuilder.create();
  }

  public static String serialize(Object obj){
    return getGson().toJson(obj);
  }

  public static <T> T deserializeToList(String jsonString, Class cls){
    return getGson().fromJson(jsonString, getListTypeForDeserialization(cls));
  }

  public static <T> T deserializeToObject(String jsonString, Class cls){
    return getGson().fromJson(jsonString, getTypeForDeserialization(cls));
  }

  public static Type getListTypeForDeserialization(Class cls) {
    String className = cls.getSimpleName();
    
    if ("IdDto".equalsIgnoreCase(className)) {
      return new TypeToken<List<IdDto>>(){}.getType();
    }
    
    if ("LoginDto".equalsIgnoreCase(className)) {
      return new TypeToken<List<LoginDto>>(){}.getType();
    }
    
    if ("PasswordDto".equalsIgnoreCase(className)) {
      return new TypeToken<List<PasswordDto>>(){}.getType();
    }
    
    if ("ResponseError".equalsIgnoreCase(className)) {
      return new TypeToken<List<ResponseError>>(){}.getType();
    }
    
    if ("RestResponse".equalsIgnoreCase(className)) {
      return new TypeToken<List<RestResponse>>(){}.getType();
    }
    
    if ("SearchUserDto".equalsIgnoreCase(className)) {
      return new TypeToken<List<SearchUserDto>>(){}.getType();
    }
    
    if ("TokenDto".equalsIgnoreCase(className)) {
      return new TypeToken<List<TokenDto>>(){}.getType();
    }
    
    if ("UserDto".equalsIgnoreCase(className)) {
      return new TypeToken<List<UserDto>>(){}.getType();
    }
    
    if ("UserWithoutPasswordDto".equalsIgnoreCase(className)) {
      return new TypeToken<List<UserWithoutPasswordDto>>(){}.getType();
    }
    
    return new TypeToken<List<Object>>(){}.getType();
  }

  public static Type getTypeForDeserialization(Class cls) {
    String className = cls.getSimpleName();
    
    if ("IdDto".equalsIgnoreCase(className)) {
      return new TypeToken<IdDto>(){}.getType();
    }
    
    if ("LoginDto".equalsIgnoreCase(className)) {
      return new TypeToken<LoginDto>(){}.getType();
    }
    
    if ("PasswordDto".equalsIgnoreCase(className)) {
      return new TypeToken<PasswordDto>(){}.getType();
    }
    
    if ("ResponseError".equalsIgnoreCase(className)) {
      return new TypeToken<ResponseError>(){}.getType();
    }
    
    if ("RestResponse".equalsIgnoreCase(className)) {
      return new TypeToken<RestResponse>(){}.getType();
    }
    
    if ("SearchUserDto".equalsIgnoreCase(className)) {
      return new TypeToken<SearchUserDto>(){}.getType();
    }
    
    if ("TokenDto".equalsIgnoreCase(className)) {
      return new TypeToken<TokenDto>(){}.getType();
    }
    
    if ("UserDto".equalsIgnoreCase(className)) {
      return new TypeToken<UserDto>(){}.getType();
    }
    
    if ("UserWithoutPasswordDto".equalsIgnoreCase(className)) {
      return new TypeToken<UserWithoutPasswordDto>(){}.getType();
    }
    
    return new TypeToken<Object>(){}.getType();
  }

};
