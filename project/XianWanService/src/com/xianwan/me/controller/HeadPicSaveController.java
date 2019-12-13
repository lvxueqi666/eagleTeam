package com.xianwan.me.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.xianwan.me.service.HeadPicService;



/**
 * Servlet implementation class HeadPicSaveController
 */
@WebServlet("/HeadPicSaveController")
public class HeadPicSaveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HeadPicSaveController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HeadPicService headPicService = new HeadPicService();
		
		String userId = request.getHeader("userId");
		//id取值为当前时间戳+HP+userId
		String id = (new java.util.Date().getTime())/1000 + "HP"+ userId;
		java.sql.Date uploadTime = new java.sql.Date(new java.util.Date().getTime());
		
		InputStream in = request.getInputStream();
		
		String address = "http://49.233.142.163:8080/images/"+id+".jpg";
		System.out.println("Path:"+address);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		byte[] pic = null;
		int n = -1;
		while((n = in.read(bytes)) != -1) {
			out.write(bytes, 0, n);
			out.flush();
		}	
		pic = out.toByteArray();
		in.close();
		out.close();
		sendPic(id,pic);
		headPicService.saveHeadPic(id, address, userId, uploadTime);
		response.getWriter().write("头像上传成功！");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void sendPic(String PicName,byte[] bytes) {
		//实例化一个Jersey
		Client client = new Client();
		//保存图片服务器的请求路径
        String url = "http://49.233.142.163:8080/images/"+PicName+".jpg";
        System.out.println("url:"+url);
		//设置请求路径
		WebResource resource = client.resource(url);
		//发送post get put 
		//resource.put(String.class, p);
		resource.put(bytes);
		System.out.println("成功发送");

	}
	
}
