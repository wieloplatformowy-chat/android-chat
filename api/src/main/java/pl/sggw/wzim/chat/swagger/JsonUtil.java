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
    
    if ("ConversationResponse".equalsIgnoreCase(className)) {
      return new TypeToken<List<ConversationResponse>>(){}.getType();
    }
    
    if ("CreateGroupResponse".equalsIgnoreCase(className)) {
      return new TypeToken<List<CreateGroupResponse>>(){}.getType();
    }
    
    if ("InviteToGroupParams".equalsIgnoreCase(className)) {
      return new TypeToken<List<InviteToGroupParams>>(){}.getType();
    }
    
    if ("LoginParams".equalsIgnoreCase(className)) {
      return new TypeToken<List<LoginParams>>(){}.getType();
    }
    
    if ("MessageResponse".equalsIgnoreCase(className)) {
      return new TypeToken<List<MessageResponse>>(){}.getType();
    }
    
    if ("OnlineResponse".equalsIgnoreCase(className)) {
      return new TypeToken<List<OnlineResponse>>(){}.getType();
    }
    
    if ("RegisterParams".equalsIgnoreCase(className)) {
      return new TypeToken<List<RegisterParams>>(){}.getType();
    }
    
    if ("RenameGroupParams".equalsIgnoreCase(className)) {
      return new TypeToken<List<RenameGroupParams>>(){}.getType();
    }
    
    if ("ResponseError".equalsIgnoreCase(className)) {
      return new TypeToken<List<ResponseError>>(){}.getType();
    }
    
    if ("RestResponse".equalsIgnoreCase(className)) {
      return new TypeToken<List<RestResponse>>(){}.getType();
    }
    
    if ("SearchUserParams".equalsIgnoreCase(className)) {
      return new TypeToken<List<SearchUserParams>>(){}.getType();
    }
    
    if ("SendMessageParams".equalsIgnoreCase(className)) {
      return new TypeToken<List<SendMessageParams>>(){}.getType();
    }
    
    if ("TokenResponse".equalsIgnoreCase(className)) {
      return new TypeToken<List<TokenResponse>>(){}.getType();
    }
    
    if ("UserResponse".equalsIgnoreCase(className)) {
      return new TypeToken<List<UserResponse>>(){}.getType();
    }
    
    return new TypeToken<List<Object>>(){}.getType();
  }

  public static Type getTypeForDeserialization(Class cls) {
    String className = cls.getSimpleName();
    
    if ("ConversationResponse".equalsIgnoreCase(className)) {
      return new TypeToken<ConversationResponse>(){}.getType();
    }
    
    if ("CreateGroupResponse".equalsIgnoreCase(className)) {
      return new TypeToken<CreateGroupResponse>(){}.getType();
    }
    
    if ("InviteToGroupParams".equalsIgnoreCase(className)) {
      return new TypeToken<InviteToGroupParams>(){}.getType();
    }
    
    if ("LoginParams".equalsIgnoreCase(className)) {
      return new TypeToken<LoginParams>(){}.getType();
    }
    
    if ("MessageResponse".equalsIgnoreCase(className)) {
      return new TypeToken<MessageResponse>(){}.getType();
    }
    
    if ("OnlineResponse".equalsIgnoreCase(className)) {
      return new TypeToken<OnlineResponse>(){}.getType();
    }
    
    if ("RegisterParams".equalsIgnoreCase(className)) {
      return new TypeToken<RegisterParams>(){}.getType();
    }
    
    if ("RenameGroupParams".equalsIgnoreCase(className)) {
      return new TypeToken<RenameGroupParams>(){}.getType();
    }
    
    if ("ResponseError".equalsIgnoreCase(className)) {
      return new TypeToken<ResponseError>(){}.getType();
    }
    
    if ("RestResponse".equalsIgnoreCase(className)) {
      return new TypeToken<RestResponse>(){}.getType();
    }
    
    if ("SearchUserParams".equalsIgnoreCase(className)) {
      return new TypeToken<SearchUserParams>(){}.getType();
    }
    
    if ("SendMessageParams".equalsIgnoreCase(className)) {
      return new TypeToken<SendMessageParams>(){}.getType();
    }
    
    if ("TokenResponse".equalsIgnoreCase(className)) {
      return new TypeToken<TokenResponse>(){}.getType();
    }
    
    if ("UserResponse".equalsIgnoreCase(className)) {
      return new TypeToken<UserResponse>(){}.getType();
    }
    
    return new TypeToken<Object>(){}.getType();
  }

};
