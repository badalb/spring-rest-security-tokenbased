package com.test.view.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.config.message.util.MessagesUtil;
import com.test.persistence.entities.UserEnckey;
import com.test.service.RestUserService;
import com.test.service.models.RestParameter;
import com.test.util.RequestParamUtils;
import com.test.view.rest.presentation.SecretKeyResponse;
import com.test.view.rest.presentation.SubscriptionResponse;
import com.test.view.rest.presentation.ViewMapper;

@RestController
@RequestMapping({ "/api/v1/", "" })
public class SubscriptionController {

	@Autowired
	private RestUserService restUserService;

	@Autowired
	private ViewMapper<SubscriptionResponse> subscriptionViewMapper;
	
	@Autowired
	private ViewMapper<SecretKeyResponse> secretKeyViewMapper;
	
	

	@Autowired
	private MessagesUtil messagesUtil;

	@RequestMapping(value = "/subscribe/{appId}", method = RequestMethod.POST)
	public SubscriptionResponse subscribe(@PathVariable String appId) {
		SubscriptionResponse response = null;
		UserEnckey userEnckey = restUserService.Subscribe(appId);
		if (userEnckey != null) {
			response = subscriptionViewMapper.map(userEnckey,SubscriptionResponse.class);
			response.setStatusCode(HttpStatus.OK + "");
			response.setMessage(messagesUtil.getMessage("rest.subscribe.success"));
		} else {
			response = new SubscriptionResponse();
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR + "");
			response.setMessage(messagesUtil.getMessage("rest.subscribe.failure"));
		}
		return response;
	}

	@RequestMapping(value = "/unsubscribe/{accessKey}", method = RequestMethod.POST)
	public SubscriptionResponse unsubscribe(@PathVariable String accessKey) {
		SubscriptionResponse response = new SubscriptionResponse();
		response.setAccessKey(accessKey);
		boolean successflag = restUserService.UnSubscribe(accessKey);
		if (successflag) {
			response.setStatusCode(HttpStatus.OK + "");
			response.setMessage(messagesUtil.getMessage("rest.unsubscribe.success"));
		} else {
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR + "");
			response.setMessage(messagesUtil.getMessage("rest.unsubscribe.failure"));
		}
		return response;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public SubscriptionResponse validate(
			@RequestHeader(value = "accessKey") String accessKey,
			@RequestHeader(value = "hashMessage") String hashMessage,
			@RequestBody(required=false) RestParameter[] reqParams) {
		
		SubscriptionResponse response = new SubscriptionResponse();
		response.setAccessKey(accessKey);
		UserEnckey userEnckey = restUserService.validateSubscriber(accessKey);
		if (userEnckey == null) {
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR + "");
			response.setMessage(messagesUtil.getMessage("rest.validate.accesskeyfail"));
			return response;
		}
		String reqParamString = RequestParamUtils.getStringFromRequestParams(reqParams);
		boolean validationFlag = restUserService.validateMessageHash(userEnckey.getHmacKey(), hashMessage, reqParamString);
		if (validationFlag == false) {
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR + "");
			response.setMessage(messagesUtil.getMessage("rest.validate.failure"));
			return response;
		}
		response.setStatusCode(HttpStatus.OK + "");
		response.setMessage(messagesUtil.getMessage("rest.validate.success"));
		return response;
	}
	
	@RequestMapping(value = "/getsecretkey/{accessKey}", method = RequestMethod.POST)
	public SecretKeyResponse getSecretkey(@PathVariable String accessKey) {
		SecretKeyResponse response = null;
		UserEnckey userEnckey = restUserService.findByAccessKey(accessKey);
		if (userEnckey != null) {
			response = secretKeyViewMapper.map(userEnckey,SecretKeyResponse.class);
			response.setStatusCode(HttpStatus.OK + "");
			response.setMessage(messagesUtil.getMessage("rest.secretkey.success"));
		} else {
			response = new SecretKeyResponse();
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR + "");
			response.setMessage(messagesUtil.getMessage("rest.secretkey.failure"));
		}
		return response;
	}

}
