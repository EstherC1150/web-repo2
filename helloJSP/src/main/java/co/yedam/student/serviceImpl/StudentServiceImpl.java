package co.yedam.student.serviceImpl;

import java.util.List;

import co.yedam.student.service.StudentService;
import co.yedam.student.service.StudentVO;

public class StudentServiceImpl implements StudentService {
	StudentDAO dao = new StudentDAO();

	//이전에는 ↓ 여기에다가 쿼리를 다 넣었는데, 단순히 기능 호출을 하도록
	@Override
	public boolean addStudent(StudentVO vo) {
		return dao.insert(vo) == 1;
	}

	@Override
	public boolean editStudent(StudentVO vo) {
		return dao.update(vo) == 1;
	}

	@Override
	public boolean removeStudent(String sid) {
		return dao.delete(sid) == 1;
	}

	@Override
	public List<StudentVO> listStudent() {
		return dao.list();
	}

	@Override
	public StudentVO getStudent(String sid) {
		return dao.select(sid);
	}

}
