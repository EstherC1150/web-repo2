<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="co.yedam.board.service.MemberVO"%>
<%@page import="java.util.List"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../layout/menu.jsp"></jsp:include>
<jsp:include page="../layout/header.jsp"></jsp:include>


<table class="table">
	<thead>
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>연락처</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${memberList }" var="member">
			<tr>
				<td>${member.mid }</td>
				<td>${member.name }</td>
				<td>${member.phone }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="../layout/footer.jsp"></jsp:include>
