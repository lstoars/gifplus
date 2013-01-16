<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>push消息</title>
</head>
<body>
	<form action="pushMessage" method="post">
		发送类型：<select name="action">
			<option value="byOne">单个</option>
			<option value="all">全部</option>
			<option value="byPage">分页</option>
		</select><br/>
		页范围：<input type="text" name="pageNum"><br/>		
		内容：<input type="text" name="text"><br/>
		用户：<input type="text" name="uId"><br/>
		<input type="submit" value="提交" name="sub">
	</form>
</body>
</html>