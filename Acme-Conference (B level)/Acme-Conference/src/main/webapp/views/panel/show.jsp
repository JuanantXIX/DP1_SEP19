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

<h3><spring:message code="activity.title"/>: </h3><jstl:out value="${panel.title }"/>
<br/>
<h3><spring:message code="activity.speakers"/>: </h3><jstl:out value="${panel.speakers }"/>
<br/>
<h3><spring:message code="activity.room"/>: </h3><jstl:out value="${panel.room }"/>
<br/>
<h3><spring:message code="activity.attachments"/>: </h3><jstl:out value="${panel.attachments }"/>
<br/>
<h3><spring:message code="activity.startDate"/>: </h3><jstl:out value="${panel.startDate }"/>
<br/>
<h3><spring:message code="activity.endDate"/>: </h3><jstl:out value="${panel.endDate }"/>
<br/>
<h3><spring:message code="activity.conference"/>: </h3><jstl:out value="${panel.conference.title }"/>