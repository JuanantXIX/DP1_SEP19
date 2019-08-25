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


<h3><spring:message code="section.title"/>: </h3><jstl:out value="${section.title }"/>
<br/>
<h3><spring:message code="section.summary"/>: </h3><jstl:out value="${section.summary }"/>
<br/>
<h3><spring:message code="section.pictures"/>: </h3>
<jstl:forEach items ="${section.pictures }" var="image">
<img src="${image }" alt="Image not available">
<br/>
</jstl:forEach>
<br/>
<h3><spring:message code="section.tutorial"/>: </h3><jstl:out value="${section.tutorial.title }"/>
<br/>