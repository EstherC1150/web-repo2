<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <style>
#list span {
	margin: 8px;
}
.pagination {
  display: inline-block;
}

.pagination a {
  color: black;
  float: left;
  padding: 8px 16px;
  text-decoration: none;
}

.pagination a.active {
  background-color: #4CAF50;
  color: white;
}

.pagination a:hover:not(.active) {background-color: #ddd;}
</style>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<%-- ${bno } --%>

<h3>상세화면(조회화면)</h3>
<form action="modifyForm.do" name="myFrm">
	<input type="hidden" name="bno" value="${bno.boardNo }">
	<table class="table">
		<tr>
			<th>글번호</th>
			<td class="boardNo">${bno.boardNo }</td>
			<th>작성일시</th>
			<td><fmt:formatDate value="${bno.writeDate }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
		</tr>
		<tr>
			<th>글제목</th>
			<td colspan="3">${bno.title }</td>
		</tr>
		<tr>
			<td colspan="4"><textarea rows="5" cols="40"
					class="form-control">${bno.content }</textarea></td>
		</tr>
		<tr>
			<th>이미지</th>
			<td colspan="3">
				<c:if test="${!empty bno.image }">
					<img style="align: center;" width="100px" src="images/${bno.image }">
				</c:if>
			</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${bno.writer }</td>
			<th>조회수</th>
			<td>${bno.viewCnt }</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
			<c:choose>
				<c:when test="${!empty logId && logId == bno.writer }">
					<input type="submit" class="btn btn-primary" value="수정">
					<input type="button" class="btn btn-warning" value="삭제"> 
				</c:when>
				<c:otherwise>					
					<input disabled type="submit" class="btn btn-primary" value="수정">
					<input disabled type="button" class="btn btn-warning" value="삭제">
				</c:otherwise>
			</c:choose>
			</td>
		</tr>
	</table>
</form>

<h3>댓글등록</h3>
<table class="table">
	<tr>
		<th>댓글내용</th>
		<td><input type="text" class="form-control" id="content"></td>
		<td><button class="btn btn-outline-info" id="addReply">댓글등록</button></td>
	</tr>
</table>

<h3>댓글목록</h3>
<ul id="list">
	<li style="display: none;" id="template"><span>11</span><b>첫번째
			글입니다</b><span>user01</span><span>2023-10-10</span><button class="btn btn-outline-secondary">삭제</button></li>
</ul>

<div class="pagination">
</div>

<p>
	<a href="boardList.do">목록으로</a>
</p>
<script>
		document.querySelector('input[type=button]').addEventListener('click', function(e){
		//console.log(document.forms.myFrm.action = 'removeForm.do');
			document.forms.myFrm.action = 'removeForm.do';
			document.forms.myFrm.submit();
		});
		
		//댓글목록
		let bno = "${bno.boardNo}";
		let writer = "${logId}";
		bno = document.querySelector('.boardNo').innerHTML;
		let page = 1;
		
		function showList(pg = 1) {
			document.querySelectorAll('#list li:not(:nth-of-type(1))').forEach(li => li.remove());
			
			fetch('replyList.do?bno='+ bno + '&page=' + pg)
			.then(resolve => resolve.json())
			.then(result => {
				console.log(result);
				if (pg < 0) { //등록시
					page = Math.ceil(result.dto.total / 5);
					//page = result.dto.endPage;
					showList(page);
					return;
				}
				if (pg > Math.ceil(result.dto.total / 5)) {
					page = Math.ceil(result.dto.total / 5);
					showList(page);
				}
				if(result.dto.total == 0) {
					return;
				}
				result.list.forEach(reply => {
					let li = makeRow(reply);
					//ul > li 생성
					document.querySelector('#list').append(li);
					
				})
				//page 생성
				console.log(result.dto);
				makePaging(result.dto);
			})
			.catch(err => console.log(err));
		}
		showList(); //제일 처음에 실행하기
		
		//페이지 링크 생성
		function makePaging(dto={}) {
			document.querySelector('.pagination').innerHTML = '';
			
			if(dto.prev) {
				let aTag = document.createElement('a');
				aTag.setAttribute('href', dto.startPage-1);
				aTag.innerHTML = "&laquo;";
				document.querySelector('.pagination').append(aTag);
			}
			for (let i = dto.startPage; i <= dto.endPage; i++) {
				let aTag = document.createElement('a');
				aTag.setAttribute('href', i);
				aTag.innerHTML = i;
				
				//active녹색
				if (i == page) {
					aTag.className = 'active';
				}
				document.querySelector('.pagination').append(aTag);
			}
			if(dto.next) {
				let aTag = document.createElement('a');
				aTag.setAttribute('href', dto.endPage+1);
				aTag.innerHTML = "&raquo;";
				document.querySelector('.pagination').append(aTag);
			}
			
			//a에 클릭이벤트 등록
			document.querySelectorAll('.pagination a').forEach(elem => {
				elem.addEventListener('click', function(e) {
					e.preventDefault(); //form, a => 링크기능 차단
					page = elem.getAttribute('href');
					showList(page);
				})
			})
		}
		
		//등록버튼에 대한 이벤트
		document.querySelector('#addReply').addEventListener('click', function (e) {
			let reply = document.querySelector('#content').value;
			if (!bno || writer == 'null' || !reply) {
				alert("값을 확인하세요");
				return;
			}
			//ajax. bno/writer/reply => 전달
			fetch('addReply.do', {
				method: 'post',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				body: 'bno='+ bno + '&reply=' + reply + '&replyer=' + writer
			})
			.then(resolve => resolve.json())
			.then(result => {
				if (result.retCode == 'OK') {
					//document.querySelector('#list').append(makeRow(result.vo));
					showList(-1);
				} else {
					alert('Error.');
				}
			})
		})
		
		function makeRow(reply) {
			
			function deleteCallback(e) {
				console.log(e.target.parentElement)
				//삭제
				if (writer != reply.replyer) {
					alert('권한이 없습니다')
					return;
				}
				fetch('removeReply.do?rno=' + reply.replyNo)
				.then(resolve => resolve.json())
				.then(result => {
					if(result.retCode = 'OK') {
						alert('Success!!');
						e.target.parentElement.remove();
						showList(page);
					} else {
						alert('Error!!');
					}
				})
				.catch(err => console.log(err));
			}
			
			
			let temp = document.querySelector('#template').cloneNode(true);
			temp.style.display = 'block';
			console.log(temp);
			temp.querySelector('span:nth-of-type(1)').innerHTML = reply.replyNo;
			temp.querySelector('b').innerHTML = reply.reply;
			temp.querySelector('span:nth-of-type(2)').innerHTML = reply.replyer;
			temp.querySelector('span:nth-of-type(3)').innerHTML = reply.replyDate;
			temp.querySelector('button').addEventListener('click', deleteCallback);
			return temp;
		}
		
		
	</script>