<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    var _contextPath = '${contextPath}';
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>REST-TEST</title>
    <script type="text/javascript"
            src="${contextPath}/assets/js/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${contextPath}/assets/js/urlRest.js">
    </script>
</head>
<body bossanalyoptype="add">
<div style="float: left; padding-top: 20px;">
    <span>url：</span><input style="width: 400px;" id="reqUrl" type="text"><br>
    <br> <span>方式：</span><select id="reqMethod">
    <option
            value="GET" selected="selected">GET
    </option>
    <option value="POST">POST</option>
</select><br> <br>

    <div id="paramDiv">
        <div>参数：</div>
			<textarea id="reqParam" style="width: 435px; height: 295px;"
                      placeholder="编码前的参数"></textarea>
    </div>
</div>
<div style="float: left; padding-left: 50px; padding-top: 80px;">
    <button id="clearLeft" value="清空左侧">清空左侧</button>
    <br> <br>
    <button id="clearRight" value="清空右侧">清空右侧</button>
    <br> <br>
    <button id="sub" value="提交">提交</button>
    <br> <br> <input id="isAutoEncode" checked="true"
                     type="checkbox">自动加密<br> <br> <input
        id="isAutoDecode" checked="true" type="checkbox">自动解码<br>
    <br> <input id="isAutoFormat" checked="true" type="checkbox">自动格式化<br>
    <br>
</div>
<div style="float: left; padding-left: 50px;">
    <span>返回结果：</span><br>

    <div>
        <textarea id="resp" style="width: 500px; height: 400px;"></textarea>
        <br>
        <button id="decode">base64解码</button>
        <button id="encode">base64编码</button>
        <button id="format">JSON格式化</button>
        <button id="removeWhite">JSON反格式化</button>
    </div>
</div>
<div style="clear: left;"></div>
</html>