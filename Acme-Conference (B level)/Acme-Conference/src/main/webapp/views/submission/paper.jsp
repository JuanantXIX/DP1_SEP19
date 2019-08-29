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


<form:form action="submission/author/paper.do" modelAttribute="submission">

<form:hidden path="id"/>
<form:hidden path="version" />
<form:hidden path="paper" />
<form:hidden path="ticker" />
<form:hidden path="moment" />
<form:hidden path="status" />
<form:hidden path="author" />
<form:hidden path="conference" />
<form:hidden path="reviewer"/>
<form:hidden path="statusVisible" />



		
		<form:label path="cameraReadyPaper.title">
			<spring:message code="submission.paper.title" />* :
		</form:label>
		<form:input path="cameraReadyPaper.title"/>
		<form:errors cssClass="error" path="cameraReadyPaper.title" />
		<br/><br/>

		<form:label path="cameraReadyPaper.authors">
			<spring:message code="submission.paper.authors" />* :
		</form:label>
		<form:input path="cameraReadyPaper.authors"/>
		<form:errors cssClass="error" path="cameraReadyPaper.authors" />
		<br/><br/>

		<form:label path="cameraReadyPaper.summary">
			<spring:message code="submission.paper.summary" />* :
		</form:label>
		<form:input path="cameraReadyPaper.summary"/>
		<form:errors cssClass="error" path="cameraReadyPaper.summary" />
		<br/><br/>

		<form:label path="cameraReadyPaper.document">
			<spring:message code="submission.paper.document" />* :
		</form:label>
		<form:input path="cameraReadyPaper.document"/>
		<form:errors cssClass="error" path="cameraReadyPaper.document" />
		<br/><br/>

			<input type="submit" name="save"
		value="<spring:message code="utils.save" />" />&nbsp;
	
	<input type="button" name="cancel"	
	onclick="javascript: window.location.replace('submission/author/list.do')"
	value="<spring:message code="utils.cancel" />" />

</form:form>
