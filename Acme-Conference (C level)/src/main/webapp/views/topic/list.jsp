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
	name="topics" requestURI="${requestURI}" id="row">
<jstl:if test="${language=='en'}" >
<display:column property="name[0]" titleKey="topic.name.english" />
<display:column property="name[1]" titleKey="topic.name.spanish" />
</jstl:if>

<jstl:if test="${language=='es'}">
<display:column property="name[1]" titleKey="topic.name.spanish" />
<display:column property="name[0]" titleKey="topic.name.english" />
</jstl:if>
<display:column><a href="topic/administrator/show.do?topicId=${row.id }"><spring:message code="topic.show"/></a>
</display:column>
<security:authorize access="hasRole('ADMIN')">
<display:column><a href="topic/administrator/edit.do?topicId=${row.id }"><spring:message code="topic.edit"/></a></display:column>
<display:column><a href="topic/administrator/delete.do?topicId=${row.id }"><spring:message code="topic.delete"/></a></display:column>
</security:authorize>
</display:table>
<a href="topic/administrator/create.do"><spring:message code="topic.create"/></a>
<br/>
