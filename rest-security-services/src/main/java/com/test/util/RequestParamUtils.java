package com.test.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.service.exceptions.RequestParamParseException;
import com.test.service.models.RestParameter;

public class RequestParamUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestParamUtils.class);
	
	public static String getStringFromRequestParams(RestParameter[] requestParams){
		StringBuilder sb=new StringBuilder("");
		if(requestParams!=null){
			List<RestParameter> reqParamList=Arrays.asList(requestParams);
			Collections.sort(reqParamList);
			for(RestParameter requestParam:reqParamList){
				sb.append(requestParam.getParamName()).
					append("=").
					append(requestParam.getParamValue()).
					append(";");
			}
		}
		return sb.toString();
	}
	
	public static List<RestParameter> getParamListFromString(String requestParamString) throws RequestParamParseException{
		List<RestParameter> requestParamList=new ArrayList<RestParameter>();
		try{
			String[] requestParams = requestParamString.split(";");
			int order=1;
			for(String reqParam: requestParams){
				if(!reqParam.trim().equals("")){
					String[] req = reqParam.split(":");
					RestParameter rqParam=new RestParameter();
					rqParam.setParamName(req[0]);
					rqParam.setParamValue(req[1]);
					rqParam.setOrder(""+order++);
					requestParamList.add(rqParam);
				}
			}
		}catch(Exception exp){
			logger.error("Exception in getparamListFormString for:"+requestParamString+":"+exp.getMessage(),exp);
			throw new RequestParamParseException("RequestParam String Splitting Failed for:"+requestParamString,exp);
		}
		return requestParamList;
	}
	
	/*public static void main(String[] arg){
		List<RequestParam> requestParamList;
		try {
			requestParamList = getParamListFromString("asa:s:aa;asdasd:asd;");
			System.out.println("requestParamList:"+requestParamList);
		} catch (RequestParamParseException e) {
			e.printStackTrace();
		}
	}*/
}
