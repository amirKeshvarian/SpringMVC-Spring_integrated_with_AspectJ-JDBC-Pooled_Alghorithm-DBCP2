<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 9/10/2023
  Time: 4:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/WEB-INF/MENU.jsp"/>
<form action="/person/save.do">
    <input type="text" name="personId" value="${requestScope.dbPerson.personId}" placeholder="personId" disabled>
    <br/>
    <input type="text" name="name" value="${requestScope.dbPerson.name}" placeholder="name"/>
    <br/>
    <input type="text" name="family" value="${requestScope.dbPerson.family}" placeholder="family"/>
    <br/>
    <input type="text" name="salary" value="${requestScope.dbPerson.salary}" placeholder="salary"/>
    <br/>
    <input type="text" name="recordVersion" value="${requestScope.dbPerson.recordVersion}" placeholder="recordVersion" disabled><!--can be hidden -->
    <br/>
    <input type="submit" value="SAVE"/>
</form>
<form action="/person/findOne.do">
    <input type="text" name="personId" placeholder="please enter personId...">
    <input type="submit" value="FIND">
</form>
<c:forEach items="${requestScope.list}" var="person">
    <table>
        <tr>
            <td>PERSON ID</td>
            <td>NAME</td>
            <td>FAMILY</td>
            <td>SALARY</td>
            <td>RECORD VERSION</td>
            <td>CHANGE</td>
            <td>REMOVE</td>
        </tr>
        <tr>
            <form action="/person/change.do">
                <td><input type="text" name="personId" value="${person.personId}" readonly/></td>
                <td><input type="text" name="name" value="${person.name}"/></td>
                <td><input type="text" name="family" value="${person.family}"/></td>
                <td><input type="text" name="salary" value="${person.salary}"/></td>
                <td><input type="text" name="recordVersion" value="${person.recordVersion}" readonly/></td>
                <td><input type="submit" value="EXECUTE"/></td>
                <td><input type="button" onclick="removePerson(${person.personId} ,${person.recordVersion})" value="EXECUTE"/></td>
            </form>
        </tr>
    </table>
</c:forEach>
<script>
    function removePerson(personId, recordVersion){
        if(window.confirm("are u sure ?"))
            window.location = "/person/remove.do?personId=" + personId +"&recordVersion=" +recordVersion;
    }
</script>

</body>
</html>
