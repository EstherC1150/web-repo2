package co.yedam.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.yedam.admin.web.MemberListControl;
import co.yedam.board.web.BoardListControl;
import co.yedam.board.web.GetBoardControl;
import co.yedam.reply.web.AddReplyControl;
import co.yedam.reply.web.ReplyListControl;


public class FrontController extends HttpServlet {
	
	//init -> service
	Map<String, Command> map = new HashMap<>();
	@Override
	public void init(ServletConfig config) throws ServletException {

		map.put("/boardList.do", new BoardListControl());
		map.put("/memberList.do", new MemberListControl());
		map.put("/getBoard.do", new GetBoardControl());
		
		map.put("/replyList.do", new ReplyListControl());
		map.put("/addReply.do", new AddReplyControl());
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//System.out.println("FrontController");
		//요청정보의 한글 인코딩방식
		req.setCharacterEncoding("UTF-8");
		
		String uri = req.getRequestURI(); //http://localhost:8080/helloJSP/??.do --> hello부터 ~ 뒤까지 값을 가져오는 URI
		String context = req.getServletContext().getContextPath(); //helloJSP 프로젝트를 가져오는
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
