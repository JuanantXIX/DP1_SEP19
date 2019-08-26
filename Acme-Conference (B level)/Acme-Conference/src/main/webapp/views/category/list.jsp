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
	name="categories" requestURI="${requestURI}" id="row">
<jstl:if test="${language=='en'}" >
<display:column property="title[0]" titleKey="category.title.english" />
<display:column property="title[1]" titleKey="category.title.spanish" />
<display:column titleKey="category.parents">
<jstl:forEach items="${row.parents }" var="parent">
<jstl:out value="| ${parent.title[0]} | "/>
</jstl:forEach>
</display:column>
<display:column titleKey="category.children">
<jstl:forEach items="${row.children }" var="child">
<jstl:out value="| ${child.title[0]} | "/>
</jstl:forEach>
</display:column>
</jstl:if>

<jstl:if test="${language=='es'}">
<display:column property="title[1]" titleKey="category.title.spanish" />
<display:column property="title[0]" titleKey="category.title.english" />
<display:column titleKey="category.parents">
<jstl:forEach items="${row.parents }" var="parent">
<jstl:out value="| ${parent.title[1]} | "/>
</jstl:forEach>
</display:column>
<display:column titleKey="category.children">
<jstl:forEach items="${row.children }" var="child">
<jstl:out value="| ${child.title[1] } | "/>
</jstl:forEach>
</display:column>
</jstl:if>

<display:column><a href="category/administrator/show.do?categoryId=${row.id }"><spring:message code="category.show"/></a>
</display:column>
<security:authorize access="hasRole('ADMIN')">
<display:column>
<jstl:if test="${row.title[0] != 'CONFERENCE' && row.title[1] != 'CONFERENCIA' }">
<a href="category/administrator/edit.do?categoryId=${row.id }"><spring:message code="category.edit"/></a>
</jstl:if>
</display:column>
<display:column>
<jstl:if test="${row.title[0] != 'CONFERENCE' && row.title[1] != 'CONFERENCIA' }">
<a href="category/administrator/delete.do?categoryId=${row.id }"><spring:message code="category.delete"/></a>
</jstl:if></display:column>
</security:authorize>
</display:table>
<a href="category/administrator/create.do"><spring:message code="category.create"/></a>
<br/>
