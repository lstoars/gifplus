<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加图片</title>
</head>
<body>
	<form action="<%=path%>/imageAdd?action=add" method="post">
		分类： <select name="tagId">
			<c:forEach items="${tagList }" var="tag">
				<option value="${tag.id }">${tag.tagName }</option>
			</c:forEach>
		</select><br/>
		URL:<input type="text" name="url" width="150"><br/>
		描述：<input type="text" name="desc"><br/>
		<input type="submit" name="but" value="添加">
	</form>
</body>
</html>