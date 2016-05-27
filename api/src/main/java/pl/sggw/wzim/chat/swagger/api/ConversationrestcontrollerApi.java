package pl.sggw.wzim.chat.swagger.api;

import pl.sggw.wzim.chat.swagger.ApiException;
import pl.sggw.wzim.chat.swagger.ApiInvoker;
import pl.sggw.wzim.chat.swagger.Pair;

import pl.sggw.wzim.chat.swagger.model.*;

import java.util.*;

import pl.sggw.wzim.chat.swagger.model.ResponseError;
import pl.sggw.wzim.chat.swagger.model.ConversationResponse;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.util.Map;
import java.util.HashMap;
import java.io.File;

public class ConversationrestcontrollerApi {
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
   * Returns conversation with given user
   * 
   * @param id id
   * @param xAuthToken Authorization token
   * @return ConversationResponse
   */
  public ConversationResponse  getUsingGET (Long id, String xAuthToken) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'id' is set
    if (id == null) {
       throw new ApiException(400, "Missing the required parameter 'id' when calling getUsingGET");
    }
    

    // create path and map variables
    String localVarPath = "/conversations/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

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
        return (ConversationResponse) ApiInvoker.deserialize(localVarResponse, "", ConversationResponse.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      throw ex;
    }
  }
  
}
