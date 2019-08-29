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
	name="submissions" requestURI="${requestURI}" id="row">
	
<display:column property="ticker" titleKey="submission.ticker" />
<display:column property="moment" titleKey="submission.moment" />
<security:authorize access="hasRole('AUTHOR')">
<display:column titleKey="submission.status" >
<jstl:if test="${row.statusVisible == true }">
<jstl:if test="${row.status == 'ACCEPTED' }">
<spring:message code="submission.status.accepted"/>

</jstl:if>
<jstl:if test="${row.status == 'REJECTED' }">
<spring:message code="submission.status.rejected"/>

</jstl:if></jstl:if>
<jstl:if test="${row.statusVisible == false }">
<spring:message code="submission.not.visible"/>
</jstl:if>
</display:column>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
<display:column property = "status" titleKey="submission.status" />
</security:authorize>
<display:column property="paper.title" titleKey="submission.paper" />
<display:column property="cameraReadyPaper.title" titleKey="submission.cameraReadyPaper" />
<security:authorize access="hasRole('ADMIN')">
<display:column titleKey="submission.reviewer" >
<jstl:forEach items="${row.reviewer }" var="rev">
<jstl:out value ="|${rev.name }"/>
<jstl:out value ="${rev.middleName }"/>
<jstl:out value ="${rev.surname}|"/>
</jstl:forEach>
</display:column>
</security:authorize>
<security:authorize access="hasRole('AUTHOR')">
<display:column>
<jstl:if test="${row.status == 'ACCEPTED' && row.cameraReadyPaper == null }">
<a href="submission/author/paper.do?submissionId=${row.id }"><spring:message code="submission.crp.create"/></a>
</jstl:if>
</display:column>
<display:column>
<jstl:if test="${row.statusVisible == true }">
<jstl:if test="${row.status == 'ACCEPTED' || row.status == 'REJECTED'}">
<a href="report/author/list.do?submissionId=${row.id }"><spring:message code="report.show.authors"/></a>

</jstl:if>
</jstl:if>
</display:column>
<display:column><a href="submission/author/show.do?submissionId=${row.id }"><spring:message code="submission.show"/></a>
</display:column></security:authorize>
<security:authorize access="hasRole('ADMIN')">
<display:column><a href="submission/administrator/show.do?submissionId=${row.id }"><spring:message code="submission.show"/></a></display:column>
<display:column>
<jstl:if test="${row.status == 'UNDER-REVIEW' }">
<jstl:if test="${empty row.reviewer}">
<a href="submission/administrator/edit.do?submissionId=${row.id }"><spring:message code="submission.edit.reviewer"/></a>
</jstl:if>
</jstl:if>
</display:column>
</security:authorize>
<security:authorize access="hasRole('REVIEWER')">
<display:column><a href="submission/reviewer/show.do?submissionId=${row.id }"><spring:message code="submission.show"/></a></display:column>
<display:column>
<jstl:if test="${row.status == 'UNDER-REVIEW' }">

<a href="report/reviewer/create.do?submissionId=${row.id }"><spring:message code="report.create"/></a>
</jstl:if>
</display:column>
</security:authorize>
</display:table>
<security:authorize access="hasRole('ADMIN')">
<a href="submission/administrator/evaluate.do"><spring:message code="submission.evaluate"/></a>

<br/>
<a href="submission/administrator/notify.do"><spring:message code="submission.notify"/></a>

<br/>
<br/>

<spring:message code="submission.admin.footer.1"/><br/><spring:message code="submission.admin.footer.2"/>

</security:authorize>

<br/>
