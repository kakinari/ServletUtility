package com.kakinari.servlet.filter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet({"/TestServlet", "/foo/TestServlet", "/bar/TestServlet"})
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CharSequence CONTENT = "{\n" + 
			"	\"err_message\":\"\",\n" + 
			"	\"status\":1,\n" + 
			"	\"info\":{\n" + 
			"	\"title\":\"\\u203b\\u7d76\\u5bfe\\u6975\\u79d8\\u203b\\u898b\\u305f\\u3089\\u5373\\u523b\\u6d88\\u3057\\u3066\\u3010\\u3042\\u306a\\u305f\\u306e\\u5bbf\\u547d\\uff0f\\u751f\\u307e\\u308c\\u5909\\u308f\\u308b\\u6642\\u3011\",\n" + 
			"	\"body\":\"\\u203b\\u7d76\\u5bfe\\u6975\\u79d8\\u3067\\u898b\\u3066\\u203b\\u3042\\u306a\\u305f\\u306e\\u3059\\u3079\\u3066\\u3092\\u6700\\u6df1\\u90e8\\u307e\\u3067\\u898b\\u629c\\u304d\\u307e\\u3059\\u3002\\u524d\\u4e16\\u304b\\u3089\\u523b\\u307e\\u308c\\u3057\\u3042\\u306a\\u305f\\u306e\\u672c\\u5f53\\u306e\\u5bbf\\u547d\\u3001\\u9b42\\u306e\\u6210\\u9577\\u3001\\u305d\\u3057\\u3066\\u904b\\u547d\\u304c\\u751f\\u307e\\u308c\\u5909\\u308f\\u308b\\u6642\\u2026\\u2026\\u4ed6\\u306e\\u5360\\u3044\\u3067\\u306f\\u3042\\u308a\\u3048\\u306a\\u3044\\u307b\\u3069\\u201c\\u3042\\u306a\\u305f\\u81ea\\u8eab\\u201d\\u3092\\u5fb9\\u5e95\\u7684\\u306b\\u89e3\\u660e\\u3057\\u307e\\u3059\\uff01\",\n" + 
			"	\"items\":[{\n" + 
			"	\"title\":\"\\u6700\\u6df1\\u90e8\\u3000\\u9b42\\u6e90\\u5c64\\u3000\\u524d\\u4e16\\u304c\\u3042\\u306a\\u305f\\u306e\\u9b42\\u306b\\u523b\\u3093\\u3060\\u5bbf\\u547d\"\n" + 
			"},{\n" + 
			"	\"title\":\"\\u3042\\u306a\\u305f\\u306e\\u9b42\\u304c\\u5b8c\\u6210\\u3057\\u3001\\u5145\\u305f\\u3055\\u308c\\u308b\\u518d\\u751f\\u671f\"\n" + 
			"},{\n" + 
			"	\"title\":\"\\u3042\\u306a\\u305f\\u306e\\u9b42\\u304c\\u73fe\\u72b6\\u3092\\u6253\\u7834\\u3057\\u751f\\u307e\\u308c\\u5909\\u308f\\u308b\\u7834\\u58ca\\u671f\"\n" + 
			"}]\n" + 
			"}\n" + 
			"}\n";

    /**
     * Default constructor. 
     */
    public TestServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(CONTENT);
//		response.getOutputStream().print((String) CONTENT);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
