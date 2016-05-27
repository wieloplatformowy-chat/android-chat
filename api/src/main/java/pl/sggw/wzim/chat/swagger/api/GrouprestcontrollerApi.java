package pl.sggw.wzim.chat.swagger.api;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.ApiInvoker;
import pl.sggw.wzim.chat.swagger.Pair;

import pl.sggw.wzim.chat.swagger.model.*;

import java.util.*;

import pl.sggw.wzim.chat.swagger.model.ResponseError;
import pl.sggw.wzim.chat.swagger.model.CreateGroupResponse;
import pl.sggw.wzim.chat.swagger.model.RestResponse;
import pl.sggw.wzim.chat.swagger.model.InviteToGroupParams;
import pl.sggw.wzim.chat.swagger.model.ConversationResponse;
import pl.sggw.wzim.chat.swagger.model.RenameGroupParams;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class GrouprestcontrollerApi {
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
   * Creates a new group with the logged user assigned
   * 
   * @param xAuthToken Authorization token
   * @return CreateGroupResponse
   */
  public CreateGroupResponse  createUsingGET (String xAuthToken) throws ApiException {
    Object localVarPostBody = null;
    

    // create path and map variables
    String localVarPath = "/groups/create".replaceAll("\\{format\\}","json");

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
        return (CreateGroupResponse) ApiInvoker.deserialize(localVarResponse, "", CreateGroupResponse.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Invites a user to the group.
   * 
   * @param params params
   * @param xAuthToken Authorization token
   * @return RestResponse
   */
  public RestResponse  inviteUsingPOST (InviteToGroupParams params, String xAuthToken) throws ApiException {
    Object localVarPostBody = params;
    
    // verify the required parameter 'params' is set
    if (params == null) {
       throw new ApiException(400, "Missing the required parameter 'params' when calling inviteUsingPOST");
    }
    

    // create path and map variables
    String localVarPath = "/groups/invite".replaceAll("\\{format\\}","json");

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
   * Lists all groups of logged user
   * 
   * @param xAuthToken Authorization token
   * @return List<ConversationResponse>
   */
  public List<ConversationResponse>  myUsingGET1 (String xAuthToken) throws ApiException {
    Object localVarPostBody = null;
    

    // create path and map variables
    String localVarPath = "/groups/my".replaceAll("\\{format\\}","json");

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
        return (List<ConversationResponse>) ApiInvoker.deserialize(localVarResponse, "array", ConversationResponse.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Changes name of given group
   * 
   * @param params params
   * @param xAuthToken Authorization token
   * @return RestResponse
   */
  public RestResponse  renameUsingPOST (RenameGroupParams params, String xAuthToken) throws ApiException {
    Object localVarPostBody = params;
    
    // verify the required parameter 'params' is set
    if (params == null) {
       throw new ApiException(400, "Missing the required parameter 'params' when calling renameUsingPOST");
    }
    

    // create path and map variables
    String localVarPath = "/groups/rename".replaceAll("\\{format\\}","json");

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
  
}
