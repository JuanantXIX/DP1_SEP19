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


<h3><spring:message code="report.originalityScore"/>: </h3><jstl:out value="${report.originalityScore }"/>
<br/>
<h3><spring:message code="report.qualityScore"/>: </h3><jstl:out value="${report.qualityScore }"/>
<br/>
<h3><spring:message code="report.readabilityScore"/>: </h3><jstl:out value="${report.readabilityScore }"/>
<br/>
<h3><spring:message code="report.decision"/>: </h3>
<jstl:if test="${report.decision == 'ACCEPT' }">
<spring:message code="report.accept"/>

</jstl:if>
<jstl:if test="${report.decision == 'REJECT' }">
<spring:message code="report.reject"/>

</jstl:if>
<jstl:if test="${report.decision == 'BORDER-LINE' }">
<spring:message code="report.borderline"/>

</jstl:if>
<br/>
<h3><spring:message code="report.comments"/>: </h3><jstl:out value="${report.comments }"/>
<br/>
<h3><spring:message code="report.reviewer"/>: </h3><jstl:out value="${report.reviewer.name } ${report.reviewer.middleName} ${report.reviewer.surname }"/>
