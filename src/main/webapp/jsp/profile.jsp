<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


	<div>
		<h3>Profile data:</h3>
		
		<form id="singleUploadForm" name="singleUploadForm">
			<label for="photo">Photo of person:</label> <br>
			<img id="photo" src="${student.imagePath}" width="300"> <br>
			
			<label for="firstName">First name: </label>
			<input type="text" id="firstName" name="firstName" value="${student.firstName}" readonly /> <br>
			
			<label for="lastName">Last name:</label>
			<input type="text" id="lastName" name="lastName" value="${student.lastName}" readonly /> <br>
			
			<label for="age">age:</label>
			<input type="text" id="age" name="age" required value="${student.age}" readonly /> <br>
		</form>
	
		<div>
			<div id="singleFileUploadError"></div>
			<div id="singleFileUploadSuccess"></div>
		</div>
	</div>

	<script src="/js/file_manager.js"></script>

</body>
</html>