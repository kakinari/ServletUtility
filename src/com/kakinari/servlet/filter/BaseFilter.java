package com.kakinari.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kakinari.servlet.wrapper.EditableServletRequest;
import com.kakinari.servlet.wrapper.ReadableServletResponse;


/**
 * Servlet Filter implementation class BaseFilter
 */

public abstract class BaseFilter implements Filter {
	abstract protected ServletRequest seRequestWrapper(ServletRequest request);
	abstract protected ServletResponse setResponseWrapper(ServletResponse response);
	abstract protected void doPreProcess(ServletRequest request, ServletResponse response);
	abstract protected void doPostProcess(ServletRequest request, ServletResponse response);

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ServletRequest inWrapper = seRequestWrapper(request);
		ServletResponse outWrapper = setResponseWrapper(response);
		doPreProcess(inWrapper, outWrapper);
		chain.doFilter(inWrapper, outWrapper);
		doPostProcess(inWrapper, outWrapper);
	}
	
	protected ServletRequest setEditableRequestWrapper(ServletRequest request) {
		return new EditableServletRequest((HttpServletRequest)  request);
	}
	
	protected ServletResponse setReadableResponseWrapper(ServletResponse response) {
		return new ReadableServletResponse((HttpServletResponse) response);
	}
	
	protected boolean isEditableRequest(ServletRequest request) {
		return request instanceof EditableServletRequest;
	}
	
	protected boolean isReadableResponser(ServletResponse response) {
		return response instanceof ReadableServletResponse;
	}
}
