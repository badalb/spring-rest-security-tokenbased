package com.test.security.config;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RestRequestWrapper extends HttpServletRequestWrapper{
    
	private final Map<String, String[]> additionalParameters;
    private Map<String, String[]> allParameters = null;

    public RestRequestWrapper(final HttpServletRequest request,final Map<String, String[]> additionalParams){
        super(request);
        additionalParameters = new TreeMap<String, String[]>();
        additionalParameters.putAll(additionalParams);
    }

    @Override
    public String getParameter(final String name){
        String[] strings = getParameterMap().get(name);
        if (strings != null){
            return strings[0];
        }
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap(){
        if (allParameters == null){
            allParameters = new TreeMap<String, String[]>();
            allParameters.putAll(super.getParameterMap());
            allParameters.putAll(additionalParameters);
        }
        return Collections.unmodifiableMap(allParameters);
    }

    @Override
    public Enumeration<String> getParameterNames(){
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(final String name){
        return getParameterMap().get(name);
    }
}