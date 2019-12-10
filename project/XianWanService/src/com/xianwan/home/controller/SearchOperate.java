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
import com.xianwan.home.service.SearchService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class SearchOperate
 */
@WebServlet("/SearchOperate")
public class SearchOperate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchOperate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String tag = request.getParameter("content");
		SearchService ss = new SearchService();
		List<Commodity> listCommodity = new ArrayList<>();
		
		if(tag != null) {
			listCommodity = ss.queryCommodityForSearch(tag);
			JSONArray jsonArray = JSONArray.fromObject( listCommodity );
			System.out.println(jsonArray.size());
			System.out.println("查询了"+tag);
			response.getWriter().print(jsonArray.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
