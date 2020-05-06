package com.xianwan.ketang.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.ketang.entity.GoodsEntity;
import com.xianwan.ketang.service.GoodService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class SearchForAndroid
 */
@WebServlet("/SearchForAndroid")
public class SearchForAndroid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchForAndroid() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String introduce = request.getParameter("introduce");
		List<GoodsEntity> goodsEntities = new ArrayList<>();
		GoodService gs = new GoodService();
		
			goodsEntities = gs.searchIntroduce(introduce);
			
			JSONArray jsonArray = JSONArray.fromObject( goodsEntities );
			
			System.out.println(jsonArray.size());
			response.getWriter().print(jsonArray.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
