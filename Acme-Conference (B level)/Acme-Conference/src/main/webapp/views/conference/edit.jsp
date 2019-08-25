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


<form:form action="conference/administrator/edit.do" modelAttribute="conference">

<form:hidden path="id"/>
<form:hidden path="version" />
<form:hidden path="administrator"/>
		
		<form:label path="title">
			<spring:message code="conference.title" />* :
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br/><br/>
		
		<form:label path="acronym">
			<spring:message code="conference.acronym" />* :
		</form:label>
		<form:input path="acronym" />
		<form:errors cssClass="error" path="acronym" />
		<br/><br/>

		<form:label path="venue">
			<spring:message code="conference.venue" />* :
		</form:label>
		<form:input path="venue" />
		<form:errors cssClass="error" path="venue" />
		<br/><br/>
		
		<form:label path="submissionDeadline">
			<spring:message code="conference.submissionDeadline" />* :
		</form:label>
		<form:input path="submissionDeadline" placeholder="dd-MM-yyyy HH:mm"/>
		<form:errors cssClass="error" path="submissionDeadline" />
		<br/><br/>
		
		<form:label path="notificationDeadline">
			<spring:message code="conference.notificationDeadline" />* :
		</form:label>
		<form:input path="notificationDeadline" placeholder="dd-MM-yyyy HH:mm"/>
		<form:errors cssClass="error" path="notificationDeadline" />
		<br/><br/>
		
		<form:label path="cameraReadyDeadline">
			<spring:message code="conference.cameraReadyDeadline" />* :
		</form:label>
		<form:input path="cameraReadyDeadline" placeholder="dd-MM-yyyy HH:mm"/>
		<form:errors cssClass="error" path="cameraReadyDeadline" />
		<br/><br/>
		
		<form:label path="startDate">
			<spring:message code="conference.startDate" />* :
		</form:label>
		<form:input path="startDate" placeholder="dd-MM-yyyy HH:mm"/>
		<form:errors cssClass="error" path="startDate"/>
		<br/><br/>
		
		<form:label path="endDate">
			<spring:message code="conference.endDate" />* :
		</form:label>
		<form:input path="endDate" placeholder="dd-MM-yyyy HH:mm"/>
		<form:errors cssClass="error" path="endDate" />
		<br/><br/>
		
		<form:label path="summary">
			<spring:message code="conference.summary" />* :
		</form:label>
		<form:input path="summary" />
		<form:errors cssClass="error" path="summary" />
		<br/><br/>
		
		<form:label path="fee">
			<spring:message code="conference.fee" />* :
		</form:label>
		<form:input path="fee" />
		<form:errors cssClass="error" path="fee" />
		<br/><br/>
		
		<jstl:if test="${language=='en'}" >
			<spring:message code="conference.category" />:*
			<form:select multiple="false" id="category" path="category">
			<form:options items="${categories}" itemLabel="title[0]" itemValue="id"/>
			</form:select>
			   <br />
			   
			<br />
		</jstl:if>

		<jstl:if test="${language=='es'}">
		<spring:message code="conference.category" />:*
			<form:select multiple="false" id="category" path="category">
			<form:options items="${categories}" itemLabel="title[1]" itemValue="id"/>
			</form:select>
			   <br />
			   
			<br />
		</jstl:if>
		
		<form:label path="finalMode">
		<spring:message code="conference.finalMode" />* :
	    </form:label>
	    <form:select path="finalMode">
		<form:option value="true"><spring:message code="utils.yes"/></form:option>
		<form:option value="false"><spring:message code="utils.no"/></form:option>
		</form:select>
		<br/><br/>
			
	<input type="submit" name="save"
		value="<spring:message code="conference.edit.save" />" />&nbsp;
	
	<input type="button" name="cancel"	
	onclick="javascript: window.location.replace('conference/administrator/list.do?listingType=0')"
	value="<spring:message code="conference.edit.cancel" />" />


</form:form>
