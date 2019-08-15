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


<form:form action="topic/administrator/edit.do" modelAttribute="topic">

<form:hidden path="id"/>
<form:hidden path="version" />
		
		<form:label path="name">
			<spring:message code="topic.name" />* :
		</form:label>
		<form:input path="name" placeholder ="EXAMPLE, EJEMPLO"/>
		<form:errors cssClass="error" path="name" />
		<br/><br/>


	<input type="submit" name="save"
		value="<spring:message code="utils.save" />" />&nbsp;
	
	<input type="button" name="cancel"	
	onclick="javascript: window.location.replace('topic/administrator/list.do')"
	value="<spring:message code="utils.cancel" />" />

</form:form>
