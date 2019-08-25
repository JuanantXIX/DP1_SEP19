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

<h3><spring:message code="conference.title"/>: </h3><jstl:out value="${conference.title }"/>
<br/>
<h3><spring:message code="conference.acronym"/>: </h3><jstl:out value="${conference.acronym }"/>
<br/>
<h3><spring:message code="conference.venue"/>: </h3><jstl:out value="${conference.venue }"/>
<br/>
<h3><spring:message code="conference.submissionDeadline"/>: </h3><jstl:out value="${conference.submissionDeadline }"/>
<br/>
<h3><spring:message code="conference.notificationDeadline"/>: </h3><jstl:out value="${conference.notificationDeadline }"/>
<br/>
<h3><spring:message code="conference.cameraReadyDeadline"/>: </h3><jstl:out value="${conference.cameraReadyDeadline }"/>
<br/>
<h3><spring:message code="conference.startDate"/>: </h3><jstl:out value="${conference.startDate }"/>
<br/>
<h3><spring:message code="conference.endDate"/>: </h3><jstl:out value="${conference.endDate }"/>
<br/>
<h3><spring:message code="conference.summary"/>: </h3><jstl:out value="${conference.summary }"/>
<br/>
<h3><spring:message code="conference.fee"/>: </h3><jstl:out value="${conference.fee }"/>
<br/>
	<jstl:if test="${language=='en'}" >

<h3><spring:message code="conference.category"/>: </h3><jstl:out value="${conference.category.title[0] }"/>
</jstl:if>


	<jstl:if test="${language=='es'}" >
<h3><spring:message code="conference.category"/>: </h3><jstl:out value="${conference.category.title[1] }"/>
</jstl:if>
<br/>