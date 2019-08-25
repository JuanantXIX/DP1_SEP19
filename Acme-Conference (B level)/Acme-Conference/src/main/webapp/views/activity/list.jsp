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


<h3><spring:message code="activity.tutorial.header"/></h3>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="tutorials" requestURI="${requestURI}" id="row">

<display:column property="title" titleKey="activity.title"/>
<display:column property="speakers" titleKey="activity.speakers"/>
<display:column property="startDate" titleKey="activity.startDate"/>
<display:column property="endDate" titleKey="activity.endDate"/>
<display:column property="room" titleKey="activity.room"/>
<display:column><a href="comment/activity/list.do?activityId=${row.id }"><spring:message code="comment.link.show"/></a></display:column>

<security:authorize access="hasRole('ADMIN')">

<display:column><a href="tutorial/administrator/show.do?tutorialId=${row.id }"><spring:message code="tutorial.show"/></a></display:column>
<display:column><a href="tutorial/administrator/edit.do?tutorialId=${row.id }"><spring:message code="activity.edit"/></a></display:column>
<display:column><a href="tutorial/administrator/delete.do?tutorialId=${row.id }"><spring:message code="tutorial.delete"/></a></display:column>
</security:authorize>
<display:column><a href="section/list.do?tutorialId=${row.id }"><spring:message code="tutorial.show.sections"/></a></display:column>
</display:table>

<security:authorize access="hasRole('ADMIN')">
<a href="tutorial/administrator/create.do?conferenceId=${conferenceId }"><spring:message code="tutorial.create"/></a>
</security:authorize>

<br/>
<h3><spring:message code="activity.panel.header"/></h3>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="panels" requestURI="${requestURI}" id="row">

<display:column property="title" titleKey="activity.title" />
<display:column property="speakers" titleKey="activity.speakers" />
<display:column property="startDate" titleKey="activity.startDate" />
<display:column property="endDate" titleKey="activity.endDate" />
<display:column property="room" titleKey="activity.room" />
<display:column><a href="comment/activity/list.do?activityId=${row.id }"><spring:message code="comment.link.show"/></a></display:column>

<security:authorize access="hasRole('ADMIN')">

<display:column><a href="activity/administrator/show.do?activityId=${row.id }"><spring:message code="activity.show"/></a>
</display:column>
<display:column><a href="panel/administrator/edit.do?panelId=${row.id }"><spring:message code="panel.edit"/></a></display:column>
<display:column><a href="panel/administrator/delete.do?panelId=${row.id }"><spring:message code="panel.delete"/></a></display:column>
</security:authorize>
</display:table>

<security:authorize access="hasRole('ADMIN')">
<a href="panel/administrator/create.do?conferenceId=${conferenceId }"><spring:message code="panel.create"/></a>
</security:authorize>
<h3><spring:message code="activity.presentation.header"/></h3>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="presentations" requestURI="${requestURI}" id="row">

<display:column property="title" titleKey="activity.title" />
<display:column property="speakers" titleKey="activity.speakers" />
<display:column property="startDate" titleKey="activity.startDate" />
<display:column property="endDate" titleKey="activity.endDate" />
<display:column property="room" titleKey="activity.room" />
<display:column><a href="comment/activity/list.do?activityId=${row.id }"><spring:message code="comment.link.show"/></a></display:column>

<security:authorize access="hasRole('ADMIN')">

<display:column><a href="presentation/administrator/show.do?presentationId=${row.id }"><spring:message code="activity.show"/></a>
</display:column>
<display:column><a href="presentation/administrator/edit.do?presentationId=${row.id }"><spring:message code="activity.edit"/></a></display:column>
<display:column><a href="presentation/administrator/delete.do?presentationId=${row.id }"><spring:message code="activity.delete"/></a></display:column>
</security:authorize>
</display:table>

<security:authorize access="hasRole('ADMIN')">
<a href="presentation/administrator/create.do?conferenceId=${conferenceId }"><spring:message code="presentation.create"/></a>
</security:authorize>
<br/>