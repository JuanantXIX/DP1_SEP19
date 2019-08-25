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


<form:form action="customisation/administrator/edit.do" modelAttribute="customisation">

<form:hidden path="id"/>
<form:hidden path="version" />
		
		<form:label path="systemName">
			<spring:message code="customisation.systemName" />* :
		</form:label>
		<form:input path="systemName"/>
		<form:errors cssClass="error" path="systemName" />
		<br/><br/>

		<form:label path="bannerUrl">
			<spring:message code="customisation.bannerUrl" />* :
		</form:label>
		<form:input path="bannerUrl"/>
		<form:errors cssClass="error" path="bannerUrl" />
		<br/><br/>

		<form:label path="welcomeMessageEng">
			<spring:message code="customisation.welcomeMessageEng" />* :
		</form:label>
		<form:input path="welcomeMessageEng"/>
		<form:errors cssClass="error" path="welcomeMessageEng" />
		<br/><br/>

		<form:label path="welcomeMessageEsp">
			<spring:message code="customisation.welcomeMessageEsp" />* :
		</form:label>
		<form:input path="welcomeMessageEsp"/>
		<form:errors cssClass="error" path="welcomeMessageEsp" />
		<br/><br/>

		<form:label path="phoneNumberCode">
			<spring:message code="customisation.phoneNumberCode" />* :
		</form:label>
		<form:input path="phoneNumberCode"/>
		<form:errors cssClass="error" path="phoneNumberCode" />
		<br/><br/>

		<form:label path="creditCardMakes">
			<spring:message code="customisation.creditCardMakes" />* :
		</form:label>
		<form:input path="creditCardMakes"/>
		<form:errors cssClass="error" path="creditCardMakes" />
		<br/><br/>


	<input type="submit" name="save"
		value="<spring:message code="utils.save" />" />&nbsp;
	
	<input type="button" name="cancel"	
	onclick="javascript: window.location.replace('welcome/index.do')"
	value="<spring:message code="utils.cancel" />" />

</form:form>
