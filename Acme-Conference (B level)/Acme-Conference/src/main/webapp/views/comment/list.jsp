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

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="comments" requestURI="${requestURI}" id="row">


<display:column property="title" titleKey="comment.title" />
<display:column property="moment" titleKey="comment.moment" />
<display:column property="author" titleKey="comment.author" />
<display:column property="text" titleKey="comment.text" />
<display:column><a href="${showURL }?commentId=${row.id }"><spring:message code="comment.show"/></a></display:column>
</display:table>

<a href="${creationURL }?id=${relationId }"><spring:message code="comment.create"/></a>

