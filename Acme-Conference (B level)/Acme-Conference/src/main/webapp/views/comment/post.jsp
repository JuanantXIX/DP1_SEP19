<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${returnURL }" modelAttribute="commentForm">
<form:hidden path="relationId"/>
<form:hidden path="moment"/>

		
		<form:label path="title">
			<spring:message code="comment.title" />* :
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br/><br/>
		
		<form:label path="author">
			<spring:message code="comment.author" />* :
		</form:label>
		<form:input path="author" />
		<form:errors cssClass="error" path="author" />
		<br/><br/>
		
		<form:label path="text">
			<spring:message code="comment.text" />* :
		</form:label>
		<form:input path="text" />
		<form:errors cssClass="error" path="text" />
		<br/><br/>
			
	<input type="submit" name="save"
		value="<spring:message code="conference.edit.save" />" />&nbsp;
	
	<input type="button" name="cancel"	
	onclick="javascript: window.location.replace('${backURL}')"
	value="<spring:message code="conference.edit.cancel" />" />


</form:form>
