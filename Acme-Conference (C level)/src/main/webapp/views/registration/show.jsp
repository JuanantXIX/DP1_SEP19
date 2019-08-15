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


<h3><spring:message code="registration.holderName"/>: </h3><jstl:out value="${registration.holderName }"/>
<br/>
<h3><spring:message code="registration.brandName"/>: </h3><jstl:out value="${registration.brandName }"/>
<br/>
<h3><spring:message code="registration.number"/>: </h3><jstl:out value="${registration.number }"/>
<br/>
<h3><spring:message code="registration.cvv"/>: </h3><jstl:out value="${registration.cvv }"/>
<br/>
<h3><spring:message code="registration.expirationMonth"/>: </h3><jstl:out value="${registration.expirationMonth }"/>
<br/>
<h3><spring:message code="registration.expirationYear"/>: </h3><jstl:out value="${registration.expirationYear }"/>
<br/>
<h3><spring:message code="registration.conference"/>: </h3><jstl:out value="${registration.conference.title }"/>
<br/>
<h3><spring:message code="registration.author"/>: </h3><jstl:out value="${registration.author.name }"/><jstl:out value=" ${registration.author.middleName }"/><jstl:out value=" ${registration.author.surname }"/>
