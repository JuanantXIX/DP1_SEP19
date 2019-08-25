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


<h3><spring:message code="category.title.english"/>: </h3><jstl:out value="${category.title[0] }"/>
<br/>
<h3><spring:message code="category.title.spanish"/>: </h3><jstl:out value="${category.title[1] }"/>
<br/>
<h3><spring:message code="category.parents"/>: </h3>
	<jstl:if test="${language=='en'}" >
		<jstl:forEach items="${category.parents }" var="parent">
			<jstl:out value="${parent.title[0] }"/> <br/>
		</jstl:forEach>
		<h3><spring:message code="category.children"/>: </h3>
		<jstl:forEach items="${category.children }" var="child">
			<jstl:out value="${child.title[0] }"/> <br/>
		</jstl:forEach>

	</jstl:if>

	<jstl:if test="${language=='es'}" >
		<jstl:forEach items="${category.parents }" var="parent">
			<jstl:out value="${parent.title[1] }"/> <br/>
		</jstl:forEach>
		<h3><spring:message code="category.children"/>: </h3>
		<jstl:forEach items="${category.children }" var="child">
			<jstl:out value="${child.title[1] }"/> <br/>
		</jstl:forEach>
	</jstl:if>
				

<br/>

