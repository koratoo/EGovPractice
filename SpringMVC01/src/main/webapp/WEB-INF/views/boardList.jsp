<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Spring MVC01</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body> 
 
<div class="container">
  <h2>Spring MVC01 Board</h2>
  <div class="panel panel-default">
    <div class="panel-heading">BOARD</div>
    <div class="panel-body">
    	<table class="table table-bordered table-hover">
    		<tr>
    			<th>글 번호</th>
    			<th>글 제목</th>
    			<th>작성자</th>
    			<th>작성일</th>
    			<th>조회수</th>
    		</tr>
    		<c:forEach var="vo" items="${list}">
    		<tr>
    			<td>${vo.getIdx() }</td>
    			<td>${vo.title }</td>
    			<td>${vo.writer }</td>
    			<td>${vo.indate }</td>
    			<td>${vo.count }</td>
    		</tr>
    		</c:forEach>
    	</table>
    
    </div>
    <div class="panel-footer">인프런 강의 수강생</div>
  </div>
</div>

</body>
</html>