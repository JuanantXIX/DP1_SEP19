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


<form:form action="registration/author/edit.do" modelAttribute="registration">

<form:hidden path="id"/>
<form:hidden path="version" />
<form:hidden path="conference" />
<form:hidden path="author" />
		
		<form:label path="holderName">
			<spring:message code="registration.holderName" />* :
		</form:label>
		<form:input path="holderName"/>
		<form:errors cssClass="error" path="holderName" />
		<br/><br/>

		<form:label path="brandName">
			<spring:message code="registration.brandName" />* :
		</form:label>
			<form:options items="${makes}"/>
		<form:errors cssClass="error" path="brandName" />
		<br/><br/>

		<form:label path="number">
			<spring:message code="registration.number" />* :
		</form:label>
		<form:input path="number"/>
		<form:errors cssClass="error" path="number" />
		<br/><br/>

		<form:label path="expirationMonth">
			<spring:message code="registration.expirationMonth" />* :
		</form:label>
		<form:input path="expirationMonth"/>
		<form:errors cssClass="error" path="expirationMonth" />
		<br/><br/>

		<form:label path="expirationYear">
			<spring:message code="registration.expirationYear" />* :
		</form:label>
		<form:input path="expirationYear"/>
		<form:errors cssClass="error" path="expirationYear" />
		<br/><br/>

		<form:label path="cvv">
			<spring:message code="registration.cvv" />* :
		</form:label>
		<form:input path="cvv"/>
		<form:errors cssClass="error" path="cvv" />
		<br/><br/>


	<input type="submit" name="save"
		value="<spring:message code="utils.save" />" />&nbsp;
	
	<input type="button" name="cancel"	
	onclick="javascript: window.location.replace('registration/author/list.do')"
	value="<spring:message code="utils.cancel" />" />

</form:form>
