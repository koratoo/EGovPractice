<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="${contextPath}/">My Spring EGov 4.0ver</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="${contextPath}/">Home</a></li>
        <li><a href="boardMain.do">게시판</a></li>
        <li><a href="#">Page 2</a></li>
      
      
      </ul>
	  <!-- 로그인한 상태 -->	
      <c:if test="${!empty mvo}">
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">회원관리 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="${contextPath}/memUpdateForm.do"><span class="glyphicon glyphicon-wrench">&nbsp;회원정보수정</a></li>
            <li><a href="${contextPath}/memImageForm.do"><span class="glyphicon glyphicon-picture">&nbsp;사진등록</a></li>
            <li><a href="${contextPath}/memLogout.do"><span class="glyphicon glyphicon-log-out">&nbsp;로그아웃</a></li>
      </c:if> 
          
      <!-- 로그인이 안된상태 -->
     <c:if test="${empty mvo}"> 
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">접속하기 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="${contextPath}/memLoginForm.do"><span class="glyphicon glyphicon-log-in">&nbsp;로그인</span></a></li>
            <li><a href="${contextPath}/memJoin.do"><span class="glyphicon glyphicon-user">&nbsp;회원가입</span></a></li>
          </ul>
        </li>
      </ul>
        </li>
      </c:if>
       <c:if test="${!empty mvo}">
			<c:if test="${mvo.memProfile eq '' }">
				<li><img class="img-circle" src="${contextPath}/resources/images/person.png" style="width:50px;height:50px;"/>${mvo.memName }님 방문을 환영합니다.</li>
	 		</c:if>
			<c:if test="${mvo.memProfile ne '' }">
				<li><img class="img-circle" src="${contextPath}/resources/upload/${mvo.memProfile}" style="width:50px;height:50px;"/>${mvo.memName }님 방문을 환영합니다.</li>
	 		</c:if>
	     </c:if>
      </ul>
    </div>
  </div>
</nav>