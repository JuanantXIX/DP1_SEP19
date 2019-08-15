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


<form:form action="section/administrator/edit.do" modelAttribute="section">

<form:hidden path="id"/>
<form:hidden path="version" />
<form:hidden path="tutorial"/>
		
		<form:label path="title">
			<spring:message code="section.title" />* :
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br/><br/>
		
		<form:label path="summary">
			<spring:message code="section.summary" />* :
		</form:label>
		<form:input path="summary" />
		<form:errors cssClass="error" path="summary" />
		<br/><br/>

		<form:label path="pictures">
			<spring:message code="section.pictures" />:
		</form:label>
		<form:input path="pictures" />
		<form:errors cssClass="error" path="pictures" />
		<br/><br/>
		
		
			
	<input type="submit" name="save"
		value="<spring:message code="section.edit.save" />" />&nbsp;
	
	<input type="button" name="cancel"	
	onclick="javascript: window.location.replace('section/administrator/list.do?tutorialId=${section.tutorial.id}')"
	value="<spring:message code="section.edit.cancel" />" />

</form:form>
