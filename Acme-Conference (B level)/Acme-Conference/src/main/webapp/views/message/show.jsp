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


<h3><spring:message code = "message.subject"/></h3>
<jstl:out value="${mes.subject }"/> <br/><br/>
<h3><spring:message code = "message.moment"/></h3>
<jstl:out value="${mes.moment }"/> <br/><br/>
<h3><spring:message code = "message.topic"/></h3>
<jstl:if test="${language=='en'}" >
<jstl:out value="${mes.topic.name[0]}"/>
</jstl:if>

<jstl:if test="${language=='es'}">
<jstl:out value="${mes.topic.name[1]}"/>
</jstl:if><h3><spring:message code = "message.body"/></h3>
<jstl:out value="${mes.body }"/> <br/><br/>
<h3><spring:message code = "message.sender"/></h3>
<jstl:out value="${mes.sender.name }"/> <br/><br/>
<h3><spring:message code = "message.recipient"/></h3>
<jstl:out value="${mes.recipient.name }"/> <br/><br/>