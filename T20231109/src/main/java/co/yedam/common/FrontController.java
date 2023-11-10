package co.yedam.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.yedam.product.web.ProductInfoControl;
import co.yedam.product.web.ProductListControl;

public class FrontController extends HttpServlet {
	
	//init -> service
	Map<String, Command> map = new HashMap<>();
	@Override
	public void init(ServletConfig config) throws ServletException {

		map.put("/productList.do", new ProductListControl());
		map.put("/productInfo.do", new ProductInfoControl());

	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String uri = req.getRequestURI(); 
		String context = req.getServletContext().getContextPath();
		String page = uri.substring(context.length());
		System.out.println(page);
		
//		String val = map.get(page);
		Command controller = map.get(page);
		controller.execute(req,  resp);
//		System.out.println(val);
		
//		if(page.equals("/second.do")) {
//			
//		} else if (page.equals("/FirstServlet.do")) {
//			
//		}
	}
}
