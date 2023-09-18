<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<!DOCTYPE html>
<html>
<head>
  <title>Spring MVC03</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  	$(document).ready(function(){
  		loadList();
  	});
  	function loadList(){
  		//서버와 통신 : 게시판 리스트 가져오기
  		$.ajax({
  			url : "board/all",
  			type : "get",
  			dataType : "json",
  			success : makeView,//콜백함수
  			error : function(){alert("error");}
  		});
  	}
  	function makeView(data){
  		var listHtml = "<table class='table table-bordered'>";
  		listHtml +="<tr align='center'>";
  		listHtml +="<td>번호</td>";
  		listHtml +="<td>제목</td>";
  		listHtml +="<td>작성자</td>";
  		listHtml +="<td>작성일</td>";
  		listHtml +="<td>조회수</td>";
  		listHtml +="</tr>";
  		$.each(data, function(index,obj){
  			listHtml +="<tr align='center'>";
  	  		listHtml +="<td>"+obj.idx+"</td>";
  	  		listHtml +="<td id='t"+obj.idx+"'><a href='javascript:goContent("+obj.idx+")'>"+obj.title+"</a></td>";
  	  		listHtml +="<td>"+obj.writer+"</td>";
  	  		listHtml +="<td>"+obj.indate.split(' ')[0]+"</td>";
  	  		listHtml +="<td id='cnt"+obj.idx+"'>"+obj.count+"</td>";
  	  		listHtml +="</tr>";
  	  		
  	  		listHtml +="<tr id='c"+obj.idx+"' style='display:none'>";
  	  		listHtml +="<td>내용</td>";
  	  		listHtml +="<td colspan='4'>";
  	  		listHtml +="<textarea id='ta"+obj.idx+"' rows='7' readonly class='form-control'></textarea>";  	  
  	  		listHtml +="<br/>"
  	  		listHtml +="<span id='ub"+obj.idx+"'><button class='btn btn-success btn-sm' onclick='goUpdateForm("+obj.idx+")'>수정화면</button></span>&nbsp;"
  	  		listHtml +="<button class='btn btn-warning btn-sm' onclick='goDelete("+obj.idx+")'>삭제</button>&nbsp;"
  	  		listHtml +="</td>";
  	  		listHtml +="</tr>";
  		});
  		
  		listHtml+="<tr>";
  		listHtml+="<td colspan='5'>";
  		listHtml+="<button class='btn btn-primary btn-sm' onclick='goForm()'>글쓰기</button>";
  		listHtml+="</td>";
  		listHtml+="</tr>";
  		listHtml+="</table>";
  		$("#view").html(listHtml);
  		$("#view").css("display","block");
  		$("#wform").css("display","none");
  	}
  	
  	function goForm(){
  		$("#view").css("display","none");
  		$("#wform").css("display","block");
  	}
  	function goList(){
  		console.log('goList작동!')
  		$("#view").css("display","block");
  		$("#wform").css("display","none");
  	}
  	function goInsert(){
  /* 	var title = $("#title").val();
  		var content = $("#content").val();
  		var writer = $("#writer").val(); */
  
  		//form 안의 데이터를 직렬화 시켜서 fData에 담아줌
  		var fData = $("#frm").serialize();
		$.ajax({
			url : "board/new",
			type : "post",
			data : fData,
			success : loadList,
			error : function(){alert("error");}
		});
		//폼 초기화
		/* $("#title").val("");
  		$("#content").val("");
  		$("#writer").val("");  */
  		
  		//글 쓴 뒤 취소버튼을 누르는 효과
  		$("#fclear").trigger("click");
  	}/*goInsert*/
  	
  	function goContent(idx){
  		if($("#c"+idx).css("display")=="none"){
  			
  			$.ajax({
  				url : "board/"+idx,
  				type : "get",
  				dataType : "json",
  				success : function(data){
  					$("#ta"+idx).val(data.content);
  				},
  				error : function(){alert("error")}
  			});
  			
	  		$("#c"+idx).css("display","table-row");//보이게	
  			$("#ta"+idx).attr("readonly",true);

  		}else{
  			$("#c"+idx).css("display","none");//감추게
  			
  			/* 닫힐 때 조회수 업데이트 */
  			$.ajax({
  				url:"board/count/"+idx,
  				type:"put",
  				dataType:"json",
  				success : function(data){
  					$("#cnt"+idx).text(data.count);
  				},
  				error : function(){alert("error")}
  			});
  		}
  	}
  	function goDelete(idx){
  		$.ajax({
  			url:"board/"+idx,
  			type:"delete",
  			success:loadList,
  			error:function(){alert("error");}
  		});
  	}
  	function goUpdateForm(idx){
		$("#ta"+idx).attr("readonly",false);//1
		
		var title = $("#t"+idx).text();
		var newInput = "<input type='text' id='nt"+idx+"' class='form-control' value='"+title+"'/>";
  		$("#t"+idx).html(newInput);//2
  		
  		var newButton="<button class='btn btn-primary btn-sm' onclick='goUpdate("+idx+")'>수정</button>";
  		$("#ub"+idx).html(newButton);//3
  	}
  	function goUpdate(idx){
  		var title = $("#nt"+idx).val();
  		var content = $("#ta"+idx).val();
  		$.ajax({
  			url:"board/update",
  			type:"put",
  			contentType:'application/json;charset=utf-8',
  			data:JSON.stringify({"idx":idx,"title":title,"content":content}),
  			success:loadList,
  			error:function(){alert("error");}
  		});
  	}
  </script>
</head>
<body> 
 
<div class="container">
  <h2>Spring MVC03</h2>
  <div class="panel panel-default">
    <div class="panel-heading">BOARD</div>
    <div class="panel-body" id="view"> </div>
    <div class="panel-body" id="wform" style="display:none">
    <form action="boardInsert.do" id="frm" method="post">
    	<table class="table table-bordered table-hover">
    		<tr>
    			<td>제목</td>
    			<td><input type="text" id="title" name="title" class="form-control"/></td>
    		</tr>
    		<tr>
    			<td>내용</td>
    			<td><textarea  rows="7" id="content" name="content" class="form-control" ></textarea></td>
    		</tr>
    		<tr>
    			<td>작성자</td>
    			<td><input type="text" id="writer" name="writer" class="form-control"/></td>
    		</tr>
    		<tr>
    			<td colspan="2" align="center">
    				<button type="button" class="btn btn-success btn-sm" onclick="goInsert()">등록</button>
    				<button type="reset" class="btn btn-warning btn-sm" id="fclear">취소</button>
    				<button type="button" class="btn btn-info btn-sm" onclick="goList()">리스트</button>
    			</td>
    		</tr>
    	</table>
    </form>
    </div>
    <div class="panel-footer">인프런 강의 수강생</div>
  </div>
</div>

</body>
</html>