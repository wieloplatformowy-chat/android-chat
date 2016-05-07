package pl.sggw.wzim.chat.swagger.api;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.ApiInvoker;
import pl.sggw.wzim.chat.swagger.Pair;

import pl.sggw.wzim.chat.swagger.model.*;

import java.util.*;

import pl.sggw.wzim.chat.swagger.model.UserWithoutPasswordDto;
import pl.sggw.wzim.chat.swagger.model.ResponseError;
import pl.sggw.wzim.chat.swagger.model.RestResponse;
import pl.sggw.wzim.chat.swagger.model.IdDto;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class FriendrestcontrollerApi {
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
   * Lists all friends of logged user
   * 
   * @param xAuthToken Authorization token
   * @return List<UserWithoutPasswordDto>
   */
  public List<UserWithoutPasswordDto>  myUsingGET (String xAuthToken) throws ApiException {
    Object localVarPostBody = null;
    

    // create path and map variables
    String localVarPath = "/friends/my".replaceAll("\\{format\\}","json");

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
        return (List<UserWithoutPasswordDto>) ApiInvoker.deserialize(localVarResponse, "array", UserWithoutPasswordDto.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * Adds friend for logged user
   * 
   * @param idDto idDto
   * @param xAuthToken Authorization token
   * @return RestResponse
   */
  public RestResponse  registerUsingPOST (IdDto idDto, String xAuthToken) throws ApiException {
    Object localVarPostBody = idDto;
    
    // verify the required parameter 'idDto' is set
    if (idDto == null) {
       throw new ApiException(400, "Missing the required parameter 'idDto' when calling registerUsingPOST");
    }
    

    // create path and map variables
    String localVarPath = "/friends/add".replaceAll("\\{format\\}","json");

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
