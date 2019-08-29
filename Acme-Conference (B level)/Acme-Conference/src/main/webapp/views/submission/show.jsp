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


<h3><spring:message code="submission.ticker"/>: </h3><jstl:out value="${submission.ticker }"/>
<br/>
<h3><spring:message code="submission.moment"/>: </h3><jstl:out value="${submission.moment }"/>
<br/>
<h3><spring:message code="submission.status"/>: </h3>
<security:authorize access = "hasRole('AUTHOR')">
<jstl:if test = "${submission.statusVisible == true }">
<jstl:if test="${submission.status == 'ACCEPTED' }">
<spring:message code="submission.status.accepted" />
</jstl:if>
<jstl:if test="${submission.status == 'REJECTED' }">
<spring:message code="submission.status.rejected" />
</jstl:if>
</jstl:if>
<jstl:if test = "${submission.statusVisible == false }">
<spring:message code="submission.not.visible" />
</jstl:if>
</security:authorize>
<security:authorize access = "hasRole('ADMIN') || hasRole('REVIEWER')">
<jstl:if test="${submission.status == 'ACCEPTED' }">
<spring:message code="submission.status.accepted" />
</jstl:if>
<jstl:if test="${submission.status == 'REJECTED' }">
<spring:message code="submission.status.rejected" />
</jstl:if>
<jstl:if test="${submission.status == 'UNDER-REVIEW' }">
<spring:message code="submission.not.visible" />
</jstl:if></security:authorize>

<br/>
<h3><spring:message code="submission.paper"/>: </h3>
<div class="data" style="background: #DDD;">
<h4><spring:message code = "submission.paper.title"/>: </h4>
<jstl:out value="${submission.paper.title }"/><br/>
<h4><spring:message code = "submission.paper.authors"/>: </h4>
<jstl:out value="${submission.paper.authors }"/><br/>
<h4><spring:message code = "submission.paper.summary"/>: </h4>
<jstl:out value="${submission.paper.summary }"/><br/>
<h4><spring:message code = "submission.paper.document"/>: </h4>
<jstl:out value="${submission.paper.document }"/><br/>


</div>
<br/>
<jstl:if test="${not empty submission.cameraReadyPaper }">
<h3><spring:message code="submission.cameraReadyPaper"/>: </h3>
<div class="data" style="background: #DDD;">
<h4><spring:message code = "submission.paper.title"/>: </h4>
<jstl:out value="${submission.cameraReadyPaper.title }"/><br/>
<h4><spring:message code = "submission.paper.authors"/>: </h4>
<jstl:out value="${submission.cameraReadyPaper.authors }"/><br/>
<h4><spring:message code = "submission.paper.summary"/>: </h4>
<jstl:out value="${submission.cameraReadyPaper.summary }"/><br/>
<h4><spring:message code = "submission.paper.document"/>: </h4>
<jstl:out value="${submission.cameraReadyPaper.document }"/><br/>
</div>
</jstl:if>
<br/>