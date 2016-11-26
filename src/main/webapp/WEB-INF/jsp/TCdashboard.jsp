<%--
  Created by IntelliJ IDEA.
  User: pcejka
  Date: 25.11.2016
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>

    <title>TC dashboard</title>
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
<h1>TC v systemu</h1>
<h3>C:\workspace\IntelliJ\diplomka\src\main\webapp\WEB-INF\jsp\TCdashboard.jsp</h3>

<c:url var="addAction" value="/tc"></c:url>


<h3>TC List</h3>
<c:if test="${!empty listTCMusters}">
    <table class="tg">
        <tr>
            <th width="40">TC ID</th>
            <th width="80">TC name</th>
            <th width="80">project id</th>
            <th width="40">tcMusters_id</th>
            <th width="40">tcInstances_id</th>
            <th width="40">Edit</th>
            <th width="40">Delete</th>
            <th width="40" border-color="red" bgcolor="#a52a2a" color="yellow">Run</th>
        </tr>
        <c:forEach items="${listTCMusters}" var="tc">
            <tr>
                <td>${tc.id}</td>
                <td>${tc.name}</td>
                <td>${tc.project_id}</td>
                <td>
                    <c:forEach items="${tc.tsMusters_id}" var="tsMuster">
                        ${tsMuster},
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${tc.tcInstances_id}" var="tsMuster">
                        ${tsMuster},
                    </c:forEach>
                </td>
                <td><a href="<c:url value='/tc/edit/${tc.id}' />">Edit</a></td>
                <td><a href="<c:url value='/tc/remove/${tc.id}' />">Delete</a></td>
                <td><a href="<c:url value='/tc/run/${tc.id}' />">Run</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
