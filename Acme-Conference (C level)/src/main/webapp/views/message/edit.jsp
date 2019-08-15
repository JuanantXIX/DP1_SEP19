<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>



<form:form action="message/send.do" modelAttribute="mes">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="sender"/>
		<form:hidden path="moment"/>
		<spring:message code="message.recipient" />:*
		<form:select multiple="false" id="recipient" path="recipient">
		    <form:options items="${recipients}" itemLabel="email" itemValue="id" />
		    </form:select>
		   <br />
		   
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
		
		<jstl:if test="${language=='en'}" >
			<spring:message code="message.topic" />:*
			<form:select multiple="false" id="topic" path="topic">
			<form:options items="${topics}" itemLabel="name[0]" itemValue="id"/>
			</form:select>
			   <br />
			   
			<br />
		</jstl:if>

	<jstl:if test="${language=='es'}">
	<spring:message code="message.topic" />:*
			<form:select multiple="false" id="topic" path="topic">
			<form:options items="${topics}" itemLabel="name[1]" itemValue="id"/>
			</form:select>
			   <br />
			   
			<br />
			</jstl:if>
				
		<input type="submit" name = "send" value = "<spring:message code ="message.send" /> " />		
		<security:authorize access="hasRole('ADMIN')">
		<input type="submit" name = "sendToAll" value = "<spring:message code ="message.sendToAll" /> " />
		<input type="submit" name = "sendToAllIncAdmins" value = "<spring:message code ="message.sendToAllIncAdmins" /> " />
		</security:authorize>
		

		<input type="button" name="cancel" value = "<spring:message code ="message.cancel" /> "onclick="javascript:relativeRedir('message/list.do');" />
		
		
	
	
</form:form>