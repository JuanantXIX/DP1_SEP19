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


<form:form action="submission/administrator/edit.do" modelAttribute="submission">

<form:hidden path="id"/>
<form:hidden path="version" />
<form:hidden path="cameraReadyPaper" />
<form:hidden path="ticker" />
<form:hidden path="moment" />
<form:hidden path="status" />
<form:hidden path="author" />
<form:hidden path="conference" />
<form:hidden path="paper" />
<form:hidden path="statusVisible" />

		
		<form:label path="reviewer">
			<spring:message code="submission.reviewer" />* :
		</form:label>
		<form:select multiple="true" id="reviewer" path="reviewer">
		    <form:options items="${reviewer}" itemLabel="name" itemValue="id"/>
		    </form:select>
		<form:errors cssClass="error" path="reviewer" />
		<br/><br/>

			<input type="submit" name="save"
		value="<spring:message code="utils.save" />" />&nbsp;
		
		<input type="submit" name="fill"
		value="<spring:message code="submission.fill" />" />&nbsp;
	
	<input type="button" name="cancel"	
	onclick="javascript: window.location.replace('submission/administrator/list.do')"
	value="<spring:message code="utils.cancel" />" />

</form:form>
