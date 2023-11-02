package co.yedam.student.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import co.yedam.common.DataSource;
import co.yedam.student.service.StudentVO;

public class StudentDAO {
	// 실제 DB insert,update,select,delete...
	// 원래 serviceImpl에 했었지만... 조금 다른 방식으로...

	DataSource ds = DataSource.getInstance();
	Connection conn;
	PreparedStatement psmt;
	ResultSet rs;

	void close() {
		try {
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(StudentVO vo) {
		String sql = "insert into student(student_id, student_name,"
				+ "student_password, student_dept, student_birthday)" + "values (?,?,?,?,?)";
		conn = ds.getConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 2012-03-05로 출력하고 싶을때, Nov-23-2012 이것을 --> SimpleDateFormat
		// Date -> String : sdf.format()
		// String -> Date : sdf.parse()

		System.out.println(sdf.format(vo.getStudentBirthday()));
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getStudentId());
			psmt.setString(2, vo.getStudentName());
			psmt.setString(3, vo.getStudentPassword());
			psmt.setString(4, vo.getStudentDept());
			psmt.setString(5, sdf.format(vo.getStudentBirthday()));
			int r = psmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return 0; // 처리가 된 건수가 없음 : 에러
	}

	// 수정 : update
	public int update(StudentVO vo) {
		String sql = "update student set student_name = ?, student_password = ? "
				+ ", student_dept = nvl(?, student_dept), student_birthday =? " + "where student_id = ?";
		conn = ds.getConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getStudentName());
			psmt.setString(2, vo.getStudentPassword());
			psmt.setString(3, vo.getStudentDept());
			psmt.setString(4, sdf.format(vo.getStudentBirthday()));
			psmt.setString(5, vo.getStudentId());
			int r = psmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return 0;
	}

	// 삭제 : delete
	public int delete(String sid) {
		String sql = "delete from student where student_id = ?";
		conn = ds.getConnection();
		try {
			int rowNo = 1;
			psmt = conn.prepareStatement(sql);
			psmt.setString(rowNo++, sid);
			int r = psmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return 0;
	}

	// 목록 : list
	public List<StudentVO> list() {
		String sql = "select * from student order by student_id";
		conn = ds.getConnection();

		List<StudentVO> list = new ArrayList<StudentVO>();
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				StudentVO vo = new StudentVO();
				vo.setStudentBirthday(rs.getDate("student_birthday"));
				vo.setStudentDept(rs.getString("student_dept"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentPassword(rs.getString("student_password"));
				vo.setStudentId(rs.getString("student_id"));

				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	// 조회 : select
	public StudentVO select(String sid) {
		StudentVO vo = null;
		String sql = "select * from student where student_id =?";
		conn = ds.getConnection();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, sid);
			ResultSet rs = psmt.executeQuery();

			if (rs.next()) {
				vo = new StudentVO();
				vo.setStudentBirthday(rs.getDate("student_birthday"));
				vo.setStudentDept(rs.getString("student_dept"));
				vo.setStudentId(rs.getString("student_id"));
				vo.setStudentName(rs.getString("student_name"));
				vo.setStudentPassword(rs.getString("student_password"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return vo;
	}
}
