package com.xianwan.home.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xianwan.home.service.ShowLikeService;

/**
 * Servlet implementation class ShowLikeOperate
 */
@WebServlet("/ShowLikeOperate")
public class ShowLikeOperate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowLikeOperate() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String commodityId = request.getParameter("commodityId");
		String userAccount = request.getParameter("userAccount");
		String addOrCancel = request.getParameter("addOrCancel");
		String operate = request.getParameter("operate");
		ShowLikeService sls = new ShowLikeService();
		if(commodityId != null) {
			if(operate.equals("add")) {
				sls.modifyLikeCount(commodityId,addOrCancel);
				sls.addShowLike(Integer.parseInt(commodityId), userAccount);
			}else if(operate.equals("cancel")) {
				sls.cancelShowLike(Integer.parseInt(commodityId), userAccount);
			}else {
				if(sls.adjustIfExistShowLike(Integer.parseInt(commodityId), userAccount)) {
					response.getWriter().print("exist");
				}else {
					response.getWriter().print("unexist");
				}
			}
			
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
