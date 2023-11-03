package co.yedam.common;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import co.yedam.board.service.BoardService;
import co.yedam.board.service.BoardVO;
import co.yedam.board.serviceImpl.BoardDAO;
import co.yedam.board.serviceImpl.BoardServiceImpl;
import co.yedam.student.service.StudentService;
import co.yedam.student.service.StudentVO;
import co.yedam.student.serviceImpl.StudentServiceImpl;

public class MainExe {
	public static void main(String[] args) {
		
		BoardService svc = new BoardServiceImpl();
		svc.boardList().forEach(st -> System.out.println(st));
		
		BoardDAO dao = new BoardDAO();
		BoardVO vo = new BoardVO();
		vo.setTitle("test");
		vo.setWriter("user03");
		vo.setContent("content3");
		if (dao.update(vo) == 1) {
			System.out.println("OK");
		} else {
			System.out.println("NG");
		}
//		//학생아이디, 비밀번호, 이름, 학과, 생일
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		StudentVO vo = new StudentVO();
//		vo.setStudentId("newbie");
//		vo.setStudentName("신입생");
//		vo.setStudentPassword("1234");
//		vo.setStudentDept("영문학과");
//		try {
//			vo.setStudentBirthday(sdf.parse("2001-01-01")); //parse --> date값을 가져온다
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		StudentService svc = new StudentServiceImpl();
//		svc.listStudent().forEach(student -> System.out.println(student));
//		
//		System.out.println("단건조회: " + svc.getStudent(vo.getStudentId()));
	}
	

	
}
