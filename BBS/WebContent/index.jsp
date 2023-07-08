<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>당신의 점수를 기록해주세요</title>
</head>
<body>
점수를 기록해주세요(순서(PK),닉네임,지폐수) *순서가 중복되면 성공화면이 뜨지 않습니다*
<form action ="./userScore.jsp" method ="post">
   <input type ="text" name ="ID">
   <input type ="text" name ="userID">
   <input type ="text" name ="score">
   <input type ="submit" value ="등록">
   
</form>
</body>
</html>