package com.xianwan.diandi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.diandi.entity.Album;
import com.xianwan.diandi.service.AlbumService;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class AlbumForDiandi
 */
@WebServlet("/AlbumForDiandi")
public class AlbumForDiandi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlbumForDiandi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String userAccount = request.getParameter("userAccount");
		AlbumService as = new AlbumService();
		List<List<Album>> list = new ArrayList<List<Album>>();
		System.out.println("点点滴滴");
		
		if(userAccount != null) {
			list = as.queryAlbumByUserAccount(userAccount);
			JSONArray jsonArray = JSONArray.fromObject( list );
			System.out.println(jsonArray.size());
			System.out.println("点点滴滴haha" + userAccount);
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
