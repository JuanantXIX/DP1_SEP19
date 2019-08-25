<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>



<form:form action="message/administrator/broadcast.do" modelAttribute="messageForm">
		<form:hidden path="recipient"/>
		<form:hidden path="broadcast"/>
		<form:hidden path="conferenceId"/>
		<form:hidden path="type"/>
		   
		<form:label path="subject">
			<spring:message code="message.subject" />:*
		</form:label>
		<form:input path="subject" />
		<form:errors cssClass="error" path="subject" />
		<br />	
		
		<form:label path="body">
			<spring:message code="message.body" />:*
		</form:label>
		<form:textarea path="body" />
		<form:errors cssClass="error" path="body" />
		<br />
		
		<spring:message code="message.topic" />:*
		<form:select multiple="false" id="topic" path="topic">
		    <form:options items="${topics}" itemLabel="name" itemValue="id"/>
		    </form:select>
		   <br />
		   
		<br />
		
		<input type="submit" name = "send" value = "<spring:message code ="message.send" /> " />		
		<input type="button" name="cancel" value = "<spring:message code ="message.cancel" /> "onclick="javascript:relativeRedir('message/list.do');" />
		
		
	
	
</form:form>