<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>書籍の追加｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/thumbnail.js"></script>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />       
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h3>貸出履歴一覧</h3>
        <div class=bookList>
            <table class="table table-bordered" style="text-align: center">
                <thead>
                    <tr class="table-primary">
                        <th>書籍名</th>
                        <th>貸出日</th>
                        <th>返却日</th>
                    </tr>
                </thead>
                <c:forEach var="rentBookInfo" items="${rentLog}">
                    <tbody>
                        <tr>
                            <td>
                                <form name="rentLog" method="post" action="<%=request.getContextPath()%>/details">
                                    <a href="javascript:void(0)" onclick="this.parentNode.submit();">${rentBookInfo.title}</a> <input type="hidden" name="bookId" value="${rentBookInfo.bookId}">
                                </form>
                            </td>
                            <td>${rentBookInfo.rentDate}</td>
                            <td>${rentBookInfo.returnDate}</td>
                        </tr>
                    </tbody>
                </c:forEach>
            </table>
        </div>
    </main>