<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
h1 {text-align:center;}
h2 {text-align:center;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<style>
.main {
    margin-left: 0px; /* Same width as the sidebar + left position in px */
    font-size: 16px; /* Increased text to enable scrolling */
    padding: 0px 10px;
    text-align: center;
}

</style>
<title>Update Account</title>
</head>

<h1>Update Information</h1>
<br>
<br>
<h2>Please update your account information below:</h2>
<br>
<br>

<div class="main">

<form action="${pageContext.request.contextPath}/updateaccount" method="post">
<p>First Name: <input type="text" name="fname" value="${first}"/></p>
<p>Last Name: <input type="text" name="lname" value="${last}"/></p>
<p>Username: ${username}</p>
<p>Password: <input type="text"  name="password" value="${password}"/></p>
<p>Email: <input type="text" name="email" value="${email}"/></p>
<p>Strategy: 
	<select name="strategy">
		<option value="Moderate">Moderate </option>
		<option value="Aggressive">Aggressive</option>
		<option value="Conservative">Conservative</option>
	</select>
</p>
	<br> <br>
	<button type="submit" name="button" value="button1"name="button">Submit</button>
</form>
<br>
<br>




<c:if test="${not empty success}" >
	<c:if test="${success}" >
		<text>Account Update Was Successful </text>
	</c:if>
	
	<c:if test="${not success}" >
		<text>Account Update Failed </text>
	</c:if>
	
</c:if>

<br>
<br>
* Indicates a required field.
<br>
<br>
<a href="${pageContext.request.contextPath}/portfolio">Cancel Update</a>

</div>
<script>
function turnTextIntoInputField(inputId) {
    console.log(inputId);
    var inputIdWithHash = "#" + inputId;
    var elementValue = $(inputIdWithHash).text();
    $(inputIdWithHash).replaceWith('<input name="test" id="' + inputId + '" type="text" value="' + elementValue + '">');

    $(document).click(function (event) {
        if (!$(event.target).closest(inputIdWithHash).length) {
            $(inputIdWithHash).replaceWith('<p id="' + inputId + '" onclick="turnTextIntoInputField(\'' + inputId + '\')">' + elementValue + '</p>');
        }
    });
}
</script>

</body>
</html>