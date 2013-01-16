<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
<link href="css.css" rel="stylesheet" type="text/css" />
<script src="http://cdn.qplus.com/js/qplus.api.js"></script>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script>
	qplus.onReady(function() {
		qplus.on("app.pushParam", function(json){
		    if(json.pushParam!=null&&json.pushParam!=""){
		    	callParam = decodeURIComponent(json.pushParam);
		    	pas = callParam.split("\-");
		    	if(pas.length==3){
		    		if(pas[1] == "0"){
		    			$.get("logMsg", {type:"fromShare",random:Math.random(),fromShare : callParam,app_openid:$("#uId").val()});
		    		} else {
		    			window.location.href="<%=basePath%>/viewList?app_openid=${app_openid}&imageId="+pas[1]+"&suid="+pas[2]+"&app_openkey="+$("#openkey").val();
		    		}
		    	} 
		    }
		});
		
		qplus.system.getVersion(function(result){
			cpage = $("#currentPage").val();
			if(cpage==null || cpage=="" || cpage=="1"){
	    		$.get("logMsg", {type:"version",version : result.version,random:Math.random(),app_openid:$("#uId").val()});
			}
		});
	});
	
	function qPlusShareV2(id, msg, title, pic, shareBtn){
		app_openid = $("#uId").val();
		msg = msg+"★更多搞笑图片，点击:http://url.cn/64KFMQ★"
		qplus.app.share({
			msg : msg,
			title : title,
			param:"fromshare-"+id+"-"+app_openid,
			pic : pic,
			shareBtn : shareBtn,
			shareTo: ['qzone','wblog','buddy']
		},function (json) {
		   if(!json.errCode){
			  incrShare(id);
		   }
		});
	}
	
	function qPlusShare(id, msg, title, pic, shareBtn){
		qplus.system.getVersion(function(result){
			qpVersion = result.version;
			strs = qpVersion.split("\.");
			version = strs[0]+"."+strs[1];
			sVersion=parseFloat(version);
			if(sVersion < 3.7){
				qPlusShareV1(id,msg,title,pic,shareBtn);
			} else {
				qPlusShareV2(id,msg,title,pic,shareBtn);
			}
		});
	}
	
	function qPlusShareV1(id,msg,title,pic,shareBtn){
		app_openid = $("#uId").val();
		msg = msg+"★更多搞笑图片，点击:http://url.cn/64KFMQ★"
		qplus.system.shareApp({
			msg:msg,
			title:title,
			param:"fromshare-"+id+"-"+app_openid,
			pic:pic,
			shareBtn:shareBtn
		});
		incrShare(id);
	}
	
	function incrShare(id){
		app_openid = $("#uId").val();
		app_openkey = $("#openkey").val();
		$.get("incrShare", {id : id,random:Math.random(),app_openid:app_openid,app_openkey:app_openkey},function(result){
			var vcount = $("#img_share_"+id).html();
			count = parseInt(vcount)+1;
			$("#img_share_"+id).html(count);
		});
	}
	
</script>

</head>
<body>
	<div class="warp">

		<div class="top">
			<c:forEach items="${tagList }" var="tag">
				<c:choose>
					<c:when test="${tag.id eq 0 }">
						<a href="<%=path %>/viewList?tagId=${tag.id}&app_openid=${app_openid}&app_openkey=${app_openkey}"
						<c:if test="${param.tagId eq tag.id }">class="act"</c:if>>${tag.tagName}</a>
					</c:when>
					<c:otherwise>
						<a href="<%=path %>/viewList?tagId=${tag.id}&orderBy=${param.orderBy}&app_openid=${app_openid}&app_openkey=${app_openkey}"
						<c:if test="${param.tagId eq tag.id }">class="act"</c:if>>${tag.tagName}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>

		<div class="tab">
			<a href="<%=path%>/viewList?orderBy=new&tagId=${param.tagId}&app_openid=${app_openid}&app_openkey=${app_openkey}" <c:if test="${orderBy eq 'new'}">class="act"</c:if> >最新</a>
			<a href="<%=path%>/viewList?orderBy=hot&tagId=${param.tagId}&app_openid=${app_openid}&app_openkey=${app_openkey}" <c:if test="${orderBy eq 'hot'}">class="act"</c:if> >最热</a>
		</div>

		<c:forEach items="${imageResult.value }" var="image">
			<div class="concent">
				<em><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
						value="${image.gmtCreate }" /></em>
				<h2>${image.imageDesc }</h2>
				<p>
					<img src="${image.imageUrl }" />
				</p>
				<span><b>分享<font id="img_share_${image.id }">${image.shareCount }</font>次</b><a
					href="javascript:qPlusShare('${image.id }','#搞笑GIF图~#${image.imageDesc }','搞笑GIF图-分享','${image.imageUrl }','分享')"></a></span>
			</div>
		</c:forEach>

		<div class="page">
			<input type="hidden" id="uId" name="app_openid" value="${app_openid }" />
			<input type="hidden" id="currentPage" name="currentPage" value="${imageResult.currentPage}">
			<input type="hidden" id="openkey" name="app_openkey" value="${app_openkey }">
			<a href="<%=path %>/viewList?tagId=${param.tagId}&pageNum=1&app_openid=${app_openid}&&app_openkey=${app_openkey}">首页</a>
			
			<c:if test="${imageResult.totalPage lt 6 }">
				<c:forEach begin="1" end="${imageResult.totalPage }" var="num">
					<a href="<%=path %>/viewList?tagId=${param.tagId}&pageNum=${num}&app_openid=${app_openid}&app_openkey=${app_openkey}"
						<c:if test="${imageResult.currentPage eq num }">class="act"</c:if>>${num}</a>
				</c:forEach>
			</c:if>
			
			<c:if test="${imageResult.totalPage gt 5}">
				<c:set var="begin" value="${imageResult.currentPage <= 3 ? 1: imageResult.currentPage-2 }"></c:set>
				<c:set var="varEnd" value="${imageResult.currentPage <= 3 ? 5: imageResult.currentPage+2 }"></c:set>
				<c:set var="end" value="${varEnd > imageResult.totalPage ?  imageResult.totalPage:varEnd }"></c:set>
				<c:forEach begin="${begin }"
					end="${end }" var="num">
					<a href="<%=path %>/viewList?tagId=${param.tagId}&pageNum=${num}&orderBy=${orderBy}&app_openid=${app_openid}&&app_openkey=${app_openkey}"
						<c:if test="${imageResult.currentPage eq num }">class="act"</c:if>>${num}</a>
				</c:forEach>
			</c:if>
			
			<a
				href="<%=path %>/viewList?tagId=${param.tagId}&pageNum=${imageResult.totalPage}&app_openid=${app_openid}&&app_openkey=${app_openkey}">未页</a>
		</div>
	</div>
</body>
</html>
