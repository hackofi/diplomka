<%--
  Created by IntelliJ IDEA.
  User: pcejk
  Date: 05.11.2016
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>DEfekty v systemu</title>

    <title>Person Page</title>
    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #ccc;
        }

        .tg td {
            font-family: Arial, sans-serif;
            font-size: 14px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #fff;
        }

        .tg th {
            font-family: Arial, sans-serif;
            font-size: 14px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #ccc;
            color: #333;
            background-color: #f0f0f0;
        }

        .tg .tg-4eph {
            background-color: #f9f9f9
        }
    </style>
</head>
<body>
<h1>Defecty v systemu</h1>
<h3>C:\workspace\IntelliJ\diplomka\src\main\webapp\WEB-INF\jsp\defect.jsp</h3>

<h3>Defects List</h3>
<c:if test="${!empty defectList}">
    <table class="tg">
        <tr>
            <th width="80">Defect ID</th>
            <th width="80">Defect affectsversion</th>
            <th width="120">Defect Description</th>
        </tr>
        <c:forEach items="${defectList}" var="defect">
            <tr>
                <td>${defect.id}</td>
                <td>${defect.affectsVersion}</td>
                <td>${defect.description}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>