<%@ page language="java"
	import="java.util.ArrayList,pojo.Feed,pojo.Item"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Rss Reader</title>
<!-- Framework CSS -->
<link rel="stylesheet" href="css/blueprint/screen.css" type="text/css"
	media="screen, projection">
<link rel="stylesheet" href="css/blueprint/print.css" type="text/css"
	media="print">
<link rel="stylesheet"
	href="css/blueprint/plugins/fancy-type/screen.css" type="text/css"
	media="screen, projection">
<style type="text/css">
table, th, td {
	border: 1px solid black;
}
</style>
</head>
<body>
	<div class="container" style="padding: 50px 80px;">
		<div class="span-14">
			<form action="subscribe" method="post">
				Feed URL: <input type="text" name="url" /> <input type="submit"
					value="Add Subscription" />
			</form>
		</div>
		<c:if test="${sessionScope.feeds!=null}">
			<c:forEach items="${sessionScope.feeds}" var="feed">
				<div class="span-14" style="padding: 10px 0px">
					<table>
						<tr>
							<th style="background-color: DodgerBlue"><a class="links"
								href="${feed.getChannel().getLink()}">${feed.getChannel().getTitle()}</a>
							</th>
						</tr>
						<c:if test="${feed.getItems()!=null}">
							<c:forEach items="${feed.getItems()}" var="item" end="5">
								<tr>
									<th><a class="links" href="${item.getLink()}">${item.getTitle()}</a>
									</th>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>
			</c:forEach>
		</c:if>
	</div>
</body>
</html>