//student.js

//페이지 로딩되면서 바로 실행
fetch('../studentList.do')
	.then(resolve => resolve.json())
	.then(result => {
		console.log(result);
		let tbody = document.querySelector('#list');
		result.forEach(student => {
			tbody.append(makeTr(student));
		})
	})
	.catch(err => console.log('error=>', err));

//등록버튼 이벤트		
document.querySelector('#addBtn').addEventListener('click', addCallback);

//수정버튼 이벤트, 서블릿(db변경) -> 화면변경
document.querySelector('#modBtn').addEventListener('click', modCallback);




//callback함수
function addCallback(e) {
	//학생아이디 입력값
	let sid = document.querySelector('input[name=sid]').value;
	let sname = document.querySelector('input[name=sname]').value;
	let pass = document.querySelector('input[name=pass]').value;
	let dept = document.querySelector('select[name=dept]').value;
	let birth = document.querySelector('input[name=birth]').value;

	let param = `sid=${sid}&name=${sname}&pass=${pass}&dept=${dept}&birth=${birth}`;
	console.log(param);

	//ajax호출
	//fetch('../addStudent.do?' + param)
	//get: url패턴. 값의제한
	//post: 파라미터 볼 수 없음, 표현 X, 값 무제한, content-type지정, 주소표시 칠 수 없음
	fetch('../addStudent.do', {
		method: 'post',
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		body: param
	}).then(resolve => resolve.json())
		.then(result => {
			if (result.retCode == 'OK') {
				alert('성공');
				let tr = makeTr({ studentId: sid, studentName: sname, studentBirthday: birth });
				document.querySelector('#list').append(tr);
			} else {
				alert('실패');
			}
		})
		.catch(err => console.log('error => ', err));
} //end of addCallback

function modCallback() {
	let id = document.querySelector('.modal-body input[name=sid]').value;
	let sname = document.querySelector('.modal-body input[name=name]').value;
	let pass = document.querySelector('.modal-body input[name=pass]').value;
	let birth = document.querySelector('.modal-body input[name=birth]').value;
	let param = `id=${id}&name=${sname}&pass=${pass}&birth=${birth}`;

	fetch('../editStudent.do', {
		method: 'post',
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		body: param
	}).then(resolve => resolve.json())
		.then(result => {
			console.log(result);
			if (result.retCode == 'OK') {
				alert('성공');
				//result.vo.studentId;
				let targetTr = document.querySelector('tr[data-sid=' + result.vo.studentId + ']');
				let newTr = makeTr(result.vo);
				let parentElem = document.querySelector('#list');
				parentElem.replaceChild(newTr, targetTr);
				document.getElementById("myModal").style.display = 'none';
			} else {
				alert('실패');
			}
		})
		.catch(err => console.log('error => ', err));
} //end of modCallback



//생성함수
function makeTr(obj) {
	let showFields = ['studentId', 'studentName', 'studentBirthday'];
	let tr = document.createElement('tr');
	tr.setAttribute('data-sid', obj.studentId);
	tr.addEventListener('dblclick', showModal);

	for (let prop of showFields) {
		let td = document.createElement('td');
		td.innerHTML = obj[prop];
		//in 했을때
		//td.innerHTML = obj[showFields[prop]];
		tr.append(td);
	}
	//삭제버튼
	let td = document.createElement('td');
	let btn = document.createElement('button');
	btn.setAttribute('data-sid', obj.studentId);
	btn.innerHTML = '삭제';
	btn.addEventListener('click', function(e) {
		//ajax호출 -> 서블릿실행
		fetch('../delStudent.do?sid=' + obj.studentId)
			.then(resolve => resolve.json())
			.then(result => {
				console.log(result);
				if (result.retCode == 'OK') {
					alert('삭제성공');
					tr.remove();
				} else {
					alert('삭제실패');
				}
			})
			.catch(err => console.log('error: ', err));
	})
	td.append(btn);
	tr.append(td);
	return tr;
} //end of makeTr

//모달 보여주기
function showModal(e) {
	console.log(e.target.parentElement, this);
	let id = this.children[0].innerHTML;
	id = this.dataset.sid; //'data-sid': std1

	var modal = document.getElementById("myModal");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}

	fetch("../getStudent.do?sid=" + id)
		.then(resolve => resolve.json())
		.then(result => {

			modal.style.display = "block";

			//let data = { id: "std1", name: "홍길동", pass: "1234", birth: "1999-09-09" };

			modal.querySelector('h2').innerHTML = result.studentName;
			modal.querySelector('input[name=pass]').value = result.studentPassword;
			modal.querySelector('input[name=name]').value = result.studentName;
			modal.querySelector('input[name=birth]').value = result.studentBirthday;
			modal.querySelector('input[name=sid]').value = result.studentId;
		})
}
