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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="reports" requestURI="${requestURI}" id="row">
	
<display:column property="originalityScore" titleKey="report.originalityScore" />
<display:column property="qualityScore" titleKey="report.qualityScore" />
<display:column property="readabilityScore" titleKey="report.readabilityScore" />
<display:column titleKey="report.decision" >
<jstl:if test="${row.decision == 'ACCEPT' }">
<spring:message code="report.accept"/>

</jstl:if>
<jstl:if test="${row.decision == 'REJECT' }">
<spring:message code="report.reject"/>

</jstl:if>
<jstl:if test="${row.decision == 'BORDER-LINE' }">
<spring:message code="report.borderline"/>

</jstl:if>

</display:column>
<display:column property="comments" titleKey="report.comments" />
<security:authorize access="hasRole('REVIEWER')">
<display:column><a href="report/reviewer/show.do?reportId=${row.id }"><spring:message code="report.show"/></a>
</display:column>
</security:authorize>
<security:authorize access="hasRole('AUTHOR')">
<display:column><a href="report/author/show.do?reportId=${row.id }"><spring:message code="report.show"/></a>
</display:column>
</security:authorize>
</display:table>
<br/>
