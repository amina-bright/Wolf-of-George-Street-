<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<link rel="stylesheet" href="http://malihu.github.io/custom-scrollbar/jquery.mCustomScrollbar.min.css">
  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
<link rel="shortcut icon" href="">

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="jquery-3.0.0.min.js" type="text/javascript" ></script>
  <script type="jquery-1.11.0.min.js"></script>
  <script type="bootstrap.min.js"></script>
<style>
.sidenav {
    width: 130px;
    position: fixed;
    z-index: 1;
    top: 20px;
    left: 10px;
    background: #eee;
    overflow-x: hidden;
    padding: 8px 0;
    margin: 50px 0px;
}

.sidenav a {
    padding: 6px 8px 6px 16px;
    text-decoration: none;
    font-size: 25px;
    color: #2196F3;
    display: block;
}

.sidenav a:hover {
    color: #064579;
}

.main {
    margin-left: 140px; /* Same width as the sidebar + left position in px */
    font-size: 16px; /* Increased text to enable scrolling */
    padding: 20px 10px;
    text-align: center;
}

@media screen and (max-height: 450px) {
    .sidenav {padding-top: 15px;}
    .sidenav a {font-size: 18px;}
}

.topnav {
    background-color: #333;
    overflow: hidden;
}

.topnav a:hover {
    background-color: #ddd;
    color: black;
}

.topnav a {
    float: right;
    color: #f2f2f2;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    height:15px;
    font-size: 17px;
}
.image_off, #home:hover .image_on{
   display:none
}
.image_on, #home:hover .image_off{
   display:block
}
</style>
<title>Wolf of George Street</title>
</head>
<body>
<div style="font-family:Times New Roman">
<nav class="navbar navbar-inverse navbar-fixed-top" style="font-family:Times New Roman">
		      <div class="container">
		        <div class="navbar-header">
		          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
		            <span class="sr-only">Toggle navigation</span>
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
		          </button>
		        </div>
		        <div id="navbar" class="navbar-collapse collapse" style="font:Times New Roman">
		          <ul class="nav navbar-nav">
                <li><a class="navbar-brand" href="#" >WoGS</a></li>
		            <li><a href=#>Help</a></li>
                <li><a href="#">Alerts</a></li>

		          </ul>
		        </div><!--/.nav-collapse -->

		      </div>
		    </nav>
</div>
<div class="main">
<P>
Please Login Below
</P>
	<div class = "logo"> 
		<img onmouseover="artImg(this)" onmouseout="normalImg(this)" id="wolf" src="file:///C:/Users/aval9/Documents/GitHub/Wolf-of-George-Street-/WolfOfGeorgeStreet/WebContent/jsps/imgs/wolfwhite.jpg">
		<a id="home"><img class="image_on" src="file:///C:/Users/aval9/Documents/GitHub/Wolf-of-George-Street-/WolfOfGeorgeStreet/WebContent/jsps/imgs/wolfmove.gif" alt="logo" /><img class="image_off" src="file:///C:/Users/aval9/Documents/GitHub/Wolf-of-George-Street-/WolfOfGeorgeStreet/WebContent/jsps/imgs/wolfwhite.jpg" alt="logo" /></a>
	</div>

<form action="${pageContext.request.contextPath}/" method="post">
	<input type="text" name="username" placeholder="Username" size=50> 
	<br>
	<input type="password" name="password" placeholder="Password" size=50> 
	<br>
	<button type="submit" name="button" value="button1">Submit</button>
</form>
<c:if test="${not empty success}" >
	<br>
	<c:if test="${not success}" >
		Login Failed
	</c:if>
	
</c:if>

<br>
<br>
<a href="${pageContext.request.contextPath}/createaccount">Create An Account</a>

</div>
<script>
function altImg(x) {
    x = file:///C:/Users/aval9/Documents/GitHub/Wolf-of-George-Street-/WolfOfGeorgeStreet/WebContent/jsps/imgs/wolfwhite.jpg
    x.style.width = "64px";
}

function normalImg(x) {
    x.style.height = "32px";
    x.style.width = "32px";
}
</script>
</body>
</html>