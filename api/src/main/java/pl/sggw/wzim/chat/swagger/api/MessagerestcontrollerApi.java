package pl.sggw.wzim.chat.swagger.api;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.ApiInvoker;
import pl.sggw.wzim.chat.swagger.Pair;

import pl.sggw.wzim.chat.swagger.model.*;

import java.util.*;

import pl.sggw.wzim.chat.swagger.model.MessageResponse;
import pl.sggw.wzim.chat.swagger.model.ResponseError;
import pl.sggw.wzim.chat.swagger.model.RestResponse;
import pl.sggw.wzim.chat.swagger.model.SendMessageParams;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class MessagerestcontrollerApi {
  String basePath = "http://chatbackend-chat22.rhcloud.com:80/";
  ApiInvoker apiInvoker = ApiInvoker.getInstance();

  public void addHeader(String key, String value) {
    getInvoker().addDefaultHeader(key, value);
  }

  public ApiInvoker getInvoker() {
    return apiInvoker;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public String getBasePath() {
    return basePath;
  }

  
  /**
   * Return 20 messages from the conversation older than given message
   * 
   * @param conversationId conversationId
   * @param messageId messageId
   * @param xAuthToken Authorization token
   * @return List<MessageResponse>
   */
  public List<MessageResponse>  beforeUsingGET (Long conversationId, Long messageId, String xAuthToken) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'conversationId' is set
    if (conversationId == null) {
       throw new ApiException(400, "Missing the required parameter 'conversationId' when calling beforeUsingGET");
    }
    
    // verify the required parameter 'messageId' is set
    if (messageId == null) {
       throw new ApiException(400, "Missing the required parameter 'messageId' when calling beforeUsingGET");
    }
    

    // create path and map variables
    String localVarPath = "/messages/before/{conversationId}/{messageId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "conversationId" + "\\}", apiInvoker.escapeString(conversationId.toString())).replaceAll("\\{" + "messageId" + "\\}", apiInvoker.escapeString(messageId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    // form params
    Map<String, String> localVarFormParams = new HashMap<String, String>();

    

    
    localVarHeaderParams.put("X-Auth-Token", ApiInvoker.parameterToString(xAuthToken));
    

    String[] localVarContentTypes = {
      "application/json"
    };
    String localVarContentType = localVarContentTypes.length > 0 ? localVarContentTypes[0] : "application/json";

    if (localVarContentType.startsWith("multipart/form-data")) {
      // file uploading
      MultipartEntityBuilder localVarBuilder = MultipartEntityBuilder.create();
      

      localVarPostBody = localVarBuilder.build();
    } else {
      // normal form params
      
    }

    try {
      String localVarResponse = apiInvoker.invokeAPI(basePath, localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarContentType);
      if(localVarResponse != null){
        return (List<MessageResponse>) ApiInvoker.deserialize(localVarResponse, "array", MessageResponse.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Return last 20 messages from the conversation
   * 
   * @param id id
   * @param xAuthToken Authorization token
   * @return List<MessageResponse>
   */
  public List<MessageResponse>  lastUsingGET (Long id, String xAuthToken) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'id' is set
    if (id == null) {
       throw new ApiException(400, "Missing the required parameter 'id' when calling lastUsingGET");
    }
    

    // create path and map variables
    String localVarPath = "/messages/last/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    // form params
    Map<String, String> localVarFormParams = new HashMap<String, String>();

    

    
    localVarHeaderParams.put("X-Auth-Token", ApiInvoker.parameterToString(xAuthToken));
    

    String[] localVarContentTypes = {
      "application/json"
    };
    String localVarContentType = localVarContentTypes.length > 0 ? localVarContentTypes[0] : "application/json";

    if (localVarContentType.startsWith("multipart/form-data")) {
      // file uploading
      MultipartEntityBuilder localVarBuilder = MultipartEntityBuilder.create();
      

      localVarPostBody = localVarBuilder.build();
    } else {
      // normal form params
      
    }

    try {
      String localVarResponse = apiInvoker.invokeAPI(basePath, localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarContentType);
      if(localVarResponse != null){
        return (List<MessageResponse>) ApiInvoker.deserialize(localVarResponse, "array", MessageResponse.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Sends a message within the conversation
   * 
   * @param params params
   * @param xAuthToken Authorization token
   * @return RestResponse
   */
  public RestResponse  sendUsingPOST (SendMessageParams params, String xAuthToken) throws ApiException {
    Object localVarPostBody = params;
    
    // verify the required parameter 'params' is set
    if (params == null) {
       throw new ApiException(400, "Missing the required parameter 'params' when calling sendUsingPOST");
    }
    

    // create path and map variables
    String localVarPath = "/messages/send".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    // form params
    Map<String, String> localVarFormParams = new HashMap<String, String>();

    

    
    localVarHeaderParams.put("X-Auth-Token", ApiInvoker.parameterToString(xAuthToken));
    

    String[] localVarContentTypes = {
      "application/json"
    };
    String localVarContentType = localVarContentTypes.length > 0 ? localVarContentTypes[0] : "application/json";

    if (localVarContentType.startsWith("multipart/form-data")) {
      // file uploading
      MultipartEntityBuilder localVarBuilder = MultipartEntityBuilder.create();
      

      localVarPostBody = localVarBuilder.build();
    } else {
      // normal form params
      
    }

    try {
      String localVarResponse = apiInvoker.invokeAPI(basePath, localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarContentType);
      if(localVarResponse != null){
        return (RestResponse) ApiInvoker.deserialize(localVarResponse, "", RestResponse.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Return ID&#39;s of groups with unread messages
   * 
   * @param xAuthToken Authorization token
   * @return List<Long>
   */
  public List<Long>  unreadUsingGET (String xAuthToken) throws ApiException {
    Object localVarPostBody = null;
    

    // create path and map variables
    String localVarPath = "/messages/unread".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    // form params
    Map<String, String> localVarFormParams = new HashMap<String, String>();

    

    
    localVarHeaderParams.put("X-Auth-Token", ApiInvoker.parameterToString(xAuthToken));
    

    String[] localVarContentTypes = {
      "application/json"
    };
    String localVarContentType = localVarContentTypes.length > 0 ? localVarContentTypes[0] : "application/json";

    if (localVarContentType.startsWith("multipart/form-data")) {
      // file uploading
      MultipartEntityBuilder localVarBuilder = MultipartEntityBuilder.create();
      

      localVarPostBody = localVarBuilder.build();
    } else {
      // normal form params
      
    }

    try {
      String localVarResponse = apiInvoker.invokeAPI(basePath, localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarContentType);
      if(localVarResponse != null){
        return (List<Long>) ApiInvoker.deserialize(localVarResponse, "array", Long.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
}
