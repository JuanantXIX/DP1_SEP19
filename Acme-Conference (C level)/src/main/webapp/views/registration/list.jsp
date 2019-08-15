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
	name="registrations" requestURI="${requestURI}" id="row">
	
<display:column property="holderName" titleKey="registration.holderName" />
<display:column property="brandName" titleKey="registration.brandName" />
<display:column property="number" titleKey="registration.number" />
<display:column property="conference.title" titleKey="registration.conference" />
<display:column><a href="registration/author/show.do?registrationId=${row.id }"><spring:message code="registration.show"/></a>
</display:column>
</display:table>
<br/>
