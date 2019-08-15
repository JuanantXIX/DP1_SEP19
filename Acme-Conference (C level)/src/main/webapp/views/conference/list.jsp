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

<spring:message code="search.conference"/>
<br/>
<form action="search/search.do">
	<label><spring:message code="search.keyword"/></label>
	<input type ="text" name="keyword"/>
	<button type="submit" name="search">Search</button>
</form>


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
<display:column><a href="conference/show.do?conferenceId=${row.id }"><spring:message code="conference.show"/></a></display:column>
<display:column><a href="activity/list.do?d-16544-p=1&conferenceId=${row.id }"><spring:message code="activity.conference.link"/></a></display:column>

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

<security:authorize access="hasRole('ADMIN')">
<a href="conference/administrator/create.do"><spring:message code="conference.create"/></a>
</security:authorize>
