package co.yedam.board.web;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.yedam.board.service.BoardService;
import co.yedam.board.service.BoardVO;
import co.yedam.board.serviceImpl.BoardServiceImpl;
import co.yedam.common.Command;

public class BoardListControl implements Command {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		// 서블릿, jsp : 서블릿(컨트롤 : 데이터처리) -> jsp(뷰)

		BoardService svc = new BoardServiceImpl();
		List<BoardVO> list = svc.boardList();

		req.setAttribute("list", list);

		// 페이지요청(boardList.do) -> 요청재지정(board/boardList.jsp)
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/board/boardList.jsp");
		try {
			rd.forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
