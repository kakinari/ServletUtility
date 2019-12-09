package com.kakinari.servlet.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kakinari.servlet.wrapper.ReadableServletResponse;

import net.arnx.jsonic.JSON;

/**
 * Servlet Filter implementation class BaseFilter
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }
		,urlPatterns = { "/*" })
public class TestFilter extends Object implements Filter {
       
    /**
     * @see Object#Object()
     */
    public TestFilter() {
        super();
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ServletResponse wrapper = response;
		if (
			((HttpServletRequest)request).getRequestURI().endsWith("/TestServlet") &&
			request.getParameter("foo") != null)
			wrapper = new ReadableServletResponse((HttpServletResponse) response);
		chain.doFilter(request, wrapper);
		if (wrapper instanceof ReadableServletResponse) {
			ReadableServletResponse editable = (ReadableServletResponse) wrapper;
			String data = editable.toString();
//			System.out.println(data);
			Map<?,?> map = JSON.decode(data, Map.class);
			data = JSON.encode(map, true);
			data = data.replace("body", "content");
//			System.out.println(data);
			editable.getResponse().getWriter().print(data);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
