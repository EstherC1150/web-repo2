package co.yedam.student.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.yedam.student.service.StudentService;
import co.yedam.student.service.StudentVO;
import co.yedam.student.serviceImpl.StudentServiceImpl;

@WebServlet("/addStudent.do")
public class AddStudentServlet extends HttpServlet {
	// init -> service -> destroy
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8"); //한글처리
		
		String sid = req.getParameter("sid");
		String sname = req.getParameter("name");
		String pass = req.getParameter("pass");
		String dept = req.getParameter("dept");
		String birth = req.getParameter("birth");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// System.out.println('값'+ sid+sname+pass+dept+birth);

		StudentVO vo = new StudentVO();
		vo.setStudentId(sid);
		vo.setStudentName(sname);
		vo.setStudentPassword(pass);
		vo.setStudentDept(dept);
		try {
			vo.setStudentBirthday(sdf.parse(birth));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		StudentService svc = new StudentServiceImpl();
		if (svc.addStudent(vo)) {
			// {"retCode" : "OK"}
			resp.getWriter().print("{\"retCode\" : \"OK\"}");
		} else {
			// {"retCode" : "NG"}
			resp.getWriter().print("{\"retCode\" : \"NG\"}");
		}
	}
}
