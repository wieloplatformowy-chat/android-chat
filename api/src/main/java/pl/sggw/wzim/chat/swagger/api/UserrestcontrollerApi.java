package pl.sggw.wzim.chat.swagger.api;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.ApiInvoker;
import pl.sggw.wzim.chat.swagger.Pair;

import pl.sggw.wzim.chat.swagger.model.*;

import java.util.*;

import pl.sggw.wzim.chat.swagger.model.LoginParams;
import pl.sggw.wzim.chat.swagger.model.TokenResponse;
import pl.sggw.wzim.chat.swagger.model.ResponseError;
import pl.sggw.wzim.chat.swagger.model.RestResponse;
import pl.sggw.wzim.chat.swagger.model.RegisterParams;
import pl.sggw.wzim.chat.swagger.model.UserResponse;
import pl.sggw.wzim.chat.swagger.model.SearchUserParams;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class UserrestcontrollerApi {
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
   * Login user
   * 
   * @param userDto userDto
   * @return TokenResponse
   */
  public TokenResponse  loginUsingPOST (LoginParams userDto) throws ApiException {
    Object localVarPostBody = userDto;
    
    // verify the required parameter 'userDto' is set
    if (userDto == null) {
       throw new ApiException(400, "Missing the required parameter 'userDto' when calling loginUsingPOST");
    }
    

    // create path and map variables
    String localVarPath = "/user/login".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    // form params
    Map<String, String> localVarFormParams = new HashMap<String, String>();

    

    

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
        return (TokenResponse) ApiInvoker.deserialize(localVarResponse, "", TokenResponse.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Logout user
   * 
   * @param xAuthToken Authorization token
   * @return RestResponse
   */
  public RestResponse  logoutUsingGET (String xAuthToken) throws ApiException {
    Object localVarPostBody = null;
    

    // create path and map variables
    String localVarPath = "/user/logout".replaceAll("\\{format\\}","json");

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
   * Register user
   * 
   * @param registerParams registerParams
   * @return RestResponse
   */
  public RestResponse  registerUsingPOST (RegisterParams registerParams) throws ApiException {
    Object localVarPostBody = registerParams;
    
    // verify the required parameter 'registerParams' is set
    if (registerParams == null) {
       throw new ApiException(400, "Missing the required parameter 'registerParams' when calling registerUsingPOST1");
    }
    

    // create path and map variables
    String localVarPath = "/user/register".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    // header params
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    // form params
    Map<String, String> localVarFormParams = new HashMap<String, String>();

    

    

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
   * Search for users with given name and email
   * 
   * @param search search
   * @param xAuthToken Authorization token
   * @return List<UserResponse>
   */
  public List<UserResponse>  searchUsingPOST (SearchUserParams search, String xAuthToken) throws ApiException {
    Object localVarPostBody = search;
    
    // verify the required parameter 'search' is set
    if (search == null) {
       throw new ApiException(400, "Missing the required parameter 'search' when calling searchUsingPOST");
    }
    

    // create path and map variables
    String localVarPath = "/user/search".replaceAll("\\{format\\}","json");

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
        return (List<UserResponse>) ApiInvoker.deserialize(localVarResponse, "array", UserResponse.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Returns user associated with given token
   * 
   * @param xAuthToken Authorization token
   * @return UserResponse
   */
  public UserResponse  whoAmIUsingGET (String xAuthToken) throws ApiException {
    Object localVarPostBody = null;
    

    // create path and map variables
    String localVarPath = "/user/whoami".replaceAll("\\{format\\}","json");

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
        return (UserResponse) ApiInvoker.deserialize(localVarResponse, "", UserResponse.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
}
