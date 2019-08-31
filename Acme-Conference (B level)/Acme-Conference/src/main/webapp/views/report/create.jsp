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


<form:form action="report/reviewer/create.do" modelAttribute="report">

<form:hidden path="id"/>
<form:hidden path="version" />
<form:hidden path="submission" />
<form:hidden path="reviewer" />



		
		<form:label path="originalityScore">
			<spring:message code="report.originalityScore" />* :
		</form:label>
		<form:input path="originalityScore"/>
		<form:errors cssClass="error" path="originalityScore" />
		<br/><br/>

		<form:label path="qualityScore">
			<spring:message code="report.qualityScore" />* :
		</form:label>
		<form:input path="qualityScore"/>
		<form:errors cssClass="error" path="qualityScore" />
		<br/><br/>

		<form:label path="readabilityScore">
			<spring:message code="report.readabilityScore" />* :
		</form:label>
		<form:input path="readabilityScore"/>
		<form:errors cssClass="error" path="readabilityScore" />
		<br/><br/>

		<form:label path="decision">
			<spring:message code="report.decision" />* :
		</form:label>
		 <form:select path="decision" name="decision" id="decision">
			<form:option value="ACCEPT"><spring:message code="report.accept"/></form:option>
			<form:option value="REJECT"><spring:message code="report.reject"/></form:option>
			<form:option value="BORDER-LINE"><spring:message code="report.borderline"/></form:option>
		</form:select>
		<form:errors cssClass="error" path="decision" />
		<br/><br/>
		
		<form:label path="comments">
			<spring:message code="report.comments" />* :
		</form:label>
		<form:input path="comments"/>
		<form:errors cssClass="error" path="comments" />
		<br/><br/>

		
			<input type="submit" name="save"
		value="<spring:message code="utils.save" />" />&nbsp;
	
	<input type="button" name="cancel"	
	onclick="javascript: window.location.replace('submission/reviewer/list.do')"
	value="<spring:message code="utils.cancel" />" />

</form:form>
