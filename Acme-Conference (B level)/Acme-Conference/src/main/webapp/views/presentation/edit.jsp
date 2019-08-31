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


<form:form action="presentation/administrator/edit.do" modelAttribute="presentation">

<form:hidden path="id"/>
<form:hidden path="version" />
<form:hidden path="conference"/>
<form:hidden path="endDate"/>
		
		<form:label path="title">
			<spring:message code="activity.title" />* :
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br/><br/>
		
		<form:label path="startDate">
			<spring:message code="activity.startDate" />* :
		</form:label>
		<form:input path="startDate" placeholder="dd-MM-yyyy HH:mm"/>
		<form:errors cssClass="error" path="startDate" />
		<br/><br/>

		<form:label path="duration">
			<spring:message code="activity.duration" />* :
		</form:label>
		<form:input path="duration" />
		<form:errors cssClass="error" path="duration" />
		<br/><br/>
		
		<form:label path="speakers">
			<spring:message code="activity.speakers" />* :
		</form:label>
		<form:input path="speakers" />
		<form:errors cssClass="error" path="speakers" />
		<br/><br/>		
		<form:label path="room">
			<spring:message code="activity.room" />* :
		</form:label>
		<form:input path="room" />
		<form:errors cssClass="error" path="room" />
		<br/><br/>
		
		<form:label path="attachments">
			<spring:message code="activity.attachments" />:
		</form:label>
		<form:input path="attachments"/>
		<form:errors cssClass="error" path="attachments" />
		<br/><br/>
		
		<spring:message code="presentation.paper" />* :
		<form:select multiple="false" id="paper" path="paper">
		    <form:options items="${papers}" itemLabel="title" itemValue="id"/>
		    </form:select>
		   <br />
		   
		<br />
		
			
	<input type="submit" name="save"
		value="<spring:message code="activity.edit.save" />" />
	
	<input type="button" name="cancel"		onclick="javascript: window.location.replace('activity/administrator/list.do?conferenceId=${presentation.conference.id}')"
 value="<spring:message code="activity.edit.cancel" />" />

</form:form>
