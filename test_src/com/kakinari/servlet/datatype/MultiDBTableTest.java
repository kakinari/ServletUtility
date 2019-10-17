package com.kakinari.servlet.datatype;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MultiDBTableTest
 */
@WebServlet("/MultiDBTableTest")
public class MultiDBTableTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MultiDBTableTest() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		MultiDBTable table = new MultiDBTable(
				request.getParameter("action"),
				request.getParameter("context"),
				request.getParameterValues("target")
		);
//		System.out.println(table.showInsertQueryString("item_master", null));
//		System.out.println(table.showUpdateQueryString("item_master", "item_number,", null));
		response.getWriter().append(table.toJSONString(true));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
