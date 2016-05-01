package pl.sggw.wzim.chat.swagger.api;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.ApiInvoker;
import pl.sggw.wzim.chat.swagger.Pair;

import pl.sggw.wzim.chat.swagger.model.*;

import java.util.*;

import pl.sggw.wzim.chat.swagger.model.UserDto;
import pl.sggw.wzim.chat.swagger.model.DataResponsestring;
import pl.sggw.wzim.chat.swagger.model.BaseResponse;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class UserrestcontrollerApi {
  String basePath = "https://chatbackend-chat22.rhcloud.com:80/";
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
   * login
   * 
   * @param userDto userDto
   * @return DataResponsestring
   */
  public DataResponsestring  loginUsingPOST (UserDto userDto) throws ApiException {
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
        return (DataResponsestring) ApiInvoker.deserialize(localVarResponse, "", DataResponsestring.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
  /**
   * register
   * 
   * @param userDto userDto
   * @return BaseResponse
   */
  public BaseResponse  registerUsingPOST (UserDto userDto) throws ApiException {
    Object localVarPostBody = userDto;
    
    // verify the required parameter 'userDto' is set
    if (userDto == null) {
       throw new ApiException(400, "Missing the required parameter 'userDto' when calling registerUsingPOST");
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
        return (BaseResponse) ApiInvoker.deserialize(localVarResponse, "", BaseResponse.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
}
