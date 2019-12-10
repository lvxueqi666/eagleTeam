package com.xianwan.me.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.xianwan.me.service.HeadPicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HeadPicController
 */
@WebServlet("/HeadPicSendToAndroidController")
public class HeadPicSendToAndroidController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HeadPicSendToAndroidController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("textml;charset=utf-8");
		
		HeadPicService headPicService = new HeadPicService();
		//得到用户Id
		String userId = request.getHeader("userId");
		String address = headPicService.queryHeadPic(userId);
		System.out.println("address:"+address);
		//将头像发送给android
		URL url = new URL(address);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();																																						
		OutputStream out = response.getOutputStream();
		byte[] bytes = new byte[1024];	
		int n = -1;
		while((n = in.read(bytes)) != -1) {
			out.write(bytes, 0, n);
			out.flush();
		}
		in.close();
		out.close();


	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);

	}
}

