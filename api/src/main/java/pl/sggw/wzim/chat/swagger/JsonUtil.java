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
    
    if ("BaseResponse".equalsIgnoreCase(className)) {
      return new TypeToken<List<BaseResponse>>(){}.getType();
    }
    
    if ("DataResponseUserEntity".equalsIgnoreCase(className)) {
      return new TypeToken<List<DataResponseUserEntity>>(){}.getType();
    }
    
    if ("DataResponsestring".equalsIgnoreCase(className)) {
      return new TypeToken<List<DataResponsestring>>(){}.getType();
    }
    
    if ("ResponseError".equalsIgnoreCase(className)) {
      return new TypeToken<List<ResponseError>>(){}.getType();
    }
    
    if ("UserDto".equalsIgnoreCase(className)) {
      return new TypeToken<List<UserDto>>(){}.getType();
    }
    
    if ("UserEntity".equalsIgnoreCase(className)) {
      return new TypeToken<List<UserEntity>>(){}.getType();
    }
    
    return new TypeToken<List<Object>>(){}.getType();
  }

  public static Type getTypeForDeserialization(Class cls) {
    String className = cls.getSimpleName();
    
    if ("BaseResponse".equalsIgnoreCase(className)) {
      return new TypeToken<BaseResponse>(){}.getType();
    }
    
    if ("DataResponseUserEntity".equalsIgnoreCase(className)) {
      return new TypeToken<DataResponseUserEntity>(){}.getType();
    }
    
    if ("DataResponsestring".equalsIgnoreCase(className)) {
      return new TypeToken<DataResponsestring>(){}.getType();
    }
    
    if ("ResponseError".equalsIgnoreCase(className)) {
      return new TypeToken<ResponseError>(){}.getType();
    }
    
    if ("UserDto".equalsIgnoreCase(className)) {
      return new TypeToken<UserDto>(){}.getType();
    }
    
    if ("UserEntity".equalsIgnoreCase(className)) {
      return new TypeToken<UserEntity>(){}.getType();
    }
    
    return new TypeToken<Object>(){}.getType();
  }

};
