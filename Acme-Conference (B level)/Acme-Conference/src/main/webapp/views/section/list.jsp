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

<jsp:useBean id="now" class="java.util.Date"/>


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="sections" requestURI="${requestURI}" id="row">

<display:column property="title" titleKey="section.title" />
<display:column property="summary" titleKey="section.summary" />
<display:column property="pictures" titleKey="section.pictures" />
<display:column><a href="section/show.do?sectionId=${row.id }"><spring:message code="section.show"/></a>
</display:column>
<security:authorize access="hasRole('ADMIN')">

<jstl:if test="${tutorial.endDate ge now }">

<display:column><a href="section/administrator/edit.do?sectionId=${row.id }"><spring:message code="section.edit"/></a></display:column>
<display:column><a href="section/administrator/delete.do?sectionId=${row.id }"><spring:message code="section.delete"/></a></display:column>
</jstl:if>
</security:authorize>
</display:table>
<security:authorize access="hasRole('ADMIN')">
<jstl:if test="${tutorial.endDate ge now }">

<a href="section/administrator/create.do?tutorialId=${tutorialId }"><spring:message code="section.create"/></a>
</jstl:if>
</security:authorize>
<br/>
