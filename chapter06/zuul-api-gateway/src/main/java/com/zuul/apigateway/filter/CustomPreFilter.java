package com.zuul.apigateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class CustomPreFilter extends ZuulFilter {

	private static Logger _logger = LoggerFactory.getLogger(ZuulFilter.class);
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		HttpServletRequest request =  RequestContext.getCurrentContext().getRequest();
		_logger.info("*******************  REQUEST STARTED **********************************");
		_logger.info("Port :"+ request.getLocalPort());
		_logger.info("HTTP Method :"+ request.getMethod());
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
