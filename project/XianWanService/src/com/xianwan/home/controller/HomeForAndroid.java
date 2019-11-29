package com.xianwan.home.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.home.entity.Commodity;
import com.xianwan.home.service.HomeService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class HomeForAndroid
 */
@WebServlet("/HomeForAndroid")
public class HomeForAndroid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeForAndroid() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String type = request.getParameter("type");
		HomeService hs = new HomeService();
		List<Commodity> listCommodity = new ArrayList<>();
		
		if(type != null) {
			listCommodity = hs.queryCommodity(type);
			JSONArray jsonArray = JSONArray.fromObject( listCommodity );
			System.out.println(jsonArray.size());
			response.getWriter().print(jsonArray.toString());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
