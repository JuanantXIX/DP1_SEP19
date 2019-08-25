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


<form:form action="finder/author/submit.do" modelAttribute="finder">

<form:hidden path="id"/>
<form:hidden path="version" />
<form:hidden path="conferences" />
		
		<form:label path="keyword">
			<spring:message code="finder.keyword" />* :
		</form:label>
		<form:input path="keyword"/>
		<form:errors cssClass="error" path="keyword" />
		<br/><br/>

		<jstl:if test="${language=='en'}" >

		<form:label path="category">
			<spring:message code="finder.category" />* :
		</form:label>
			<form:select multiple="false" id="category" path="category">
		<form:options items="${categories}" itemLabel="title[0]" itemValue="id"/>
		</form:select>
		<form:errors cssClass="error" path="category" />
		<br/><br/>

</jstl:if>

		<jstl:if test="${language=='es'}" >

		<form:label path="category">
			<spring:message code="finder.category" />* :
		</form:label>
			<form:select multiple="false" id="category" path="category">
		<form:options items="${categories}" itemLabel="title[1]" itemValue="id"/>
		</form:select>
		<form:errors cssClass="error" path="category" />
		<br/><br/>

</jstl:if>
		<form:label path="startDate">
			<spring:message code="finder.startDate" />* :
		</form:label>
		<form:input path="startDate"/>
		<form:errors cssClass="error" path="startDate" />
		<br/><br/>

		<form:label path="endDate">
			<spring:message code="finder.endDate" />* :
		</form:label>
		<form:input path="endDate"/>
		<form:errors cssClass="error" path="endDate" />
		<br/><br/>

		<form:label path="maximumFee">
			<spring:message code="finder.maximumFee" />* :
		</form:label>
		<form:input path="maximumFee"/>
		<form:errors cssClass="error" path="maximumFee" />
		<br/><br/>

		<input type="submit" name="save"
		value="<spring:message code="finder.send" />" />&nbsp;
	
	<input type="button" name="cancel"	
	onclick="javascript: window.location.replace('welcome/index.do')"
	value="<spring:message code="utils.cancel" />" />

</form:form>

<jstl:if test="${message2 != null }">
<jstl:out value="${message2 }"/>
</jstl:if>

<br/><br/><br/><br/><br/>

<h3><spring:message code="finder.conferences"/></h3>



<!-- Below, all the conference table data. See conference/list.do -->



<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="conferences" requestURI="${requestURI}" id="row">


<display:column property="title" titleKey="conference.title" />
<display:column property="acronym" titleKey="conference.acronym" />
<display:column property="venue" titleKey="conference.venue" />
<display:column property="submissionDeadline" titleKey="conference.submissionDeadline" />
<display:column property="notificationDeadline" titleKey="conference.notificationDeadline" />
<display:column property="cameraReadyDeadline" titleKey="conference.cameraReadyDeadline" />
<display:column property="startDate" titleKey="conference.startDate" />
<display:column property="endDate" titleKey="conference.endDate" />
<display:column property="summary" titleKey="conference.summary" />
<display:column property="fee" titleKey="conference.fee" />
<jstl:if test="${language=='en'}" >

<display:column property = "category.title[0]" titleKey = "conference.category"/>
</jstl:if>


	<jstl:if test="${language=='es'}" >
<display:column property = "category.title[1]" titleKey = "conference.category"/>
</jstl:if>
<security:authorize access="hasRole('ADMIN')">
<display:column titleKey="conference.finalMode" >
<jstl:if test="${row.finalMode == false }">
<spring:message code="utils.no"/>
</jstl:if>
<jstl:if test="${row.finalMode == true }">
<spring:message code="utils.yes"/>
</jstl:if>

</display:column>

<display:column><a href="message/administrator/broadcast.do?broadcastType=1&conferenceId=${row.id }"><spring:message code="message.admin.submissions"/></a></display:column>
<display:column><a href="message/administrator/broadcast.do?broadcastType=0&conferenceId=${row.id }"><spring:message code="message.admin.register"/></a></display:column>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
<display:column><a href="conference/administrator/show.do?conferenceId=${row.id }"><spring:message code="conference.show"/></a></display:column>
</security:authorize>
<security:authorize access="!hasRole('ADMIN')">
<display:column><a href="conference/show.do?conferenceId=${row.id }"><spring:message code="conference.show"/></a></display:column>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
<display:column><a href="activity/administrator/list.do?d-16544-p=1&conferenceId=${row.id }"><spring:message code="activity.conference.link"/></a></display:column>
</security:authorize>
<security:authorize access="!hasRole('ADMIN')">
<display:column><a href="activity/list.do?d-16544-p=1&conferenceId=${row.id }"><spring:message code="activity.conference.link"/></a></display:column>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
<display:column>
<jstl:if test="${row.finalMode == false }">
<a href="conference/administrator/edit.do?conferenceId=${row.id }"><spring:message code="conference.edit"/></a>
</jstl:if>
</display:column>
<display:column>
<jstl:if test="${row.finalMode == false }">
<a href="conference/administrator/delete.do?conferenceId=${row.id }"><spring:message code="conference.delete"/></a>
</jstl:if>
</display:column>
</security:authorize>

<display:column>
<a href="comment/conference/list.do?conferenceId=${row.id }"><spring:message code="comment.link.show"/></a>
</display:column>


<security:authorize access="hasRole('AUTHOR')">
<display:column>
<jstl:if test="${row.startDate gt actualDate }">
<a href="registration/author/create.do?conferenceId=${row.id }"><spring:message code="registration.create"/></a>
</jstl:if>
</display:column>
<display:column>

<jstl:if test="${row.submissionDeadline gt actualDate }">
<a href="submission/author/create.do?conferenceId=${row.id }"><spring:message code="submission.create"/></a>
</jstl:if>
</display:column>

</security:authorize>
<br/>
</display:table>
