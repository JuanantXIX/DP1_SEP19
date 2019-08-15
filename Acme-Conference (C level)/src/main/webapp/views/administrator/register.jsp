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

<script language='javascript' type='text/javascript'>
			var re = /^\+\d{1,3} \(\d{1,3}\) \d{4,}$/;
			var re2 = /^\+\d{1,3} \d{4,}$/;
			var re3 = /^\d{4,}$/;
			function check(input) {
				var OK = re.exec(input.value);
				var OK2 = re2.exec(input.value);
				var OK3 = re3.exec(input.value);
				if (!(OK || OK2 || OK3)) {
					alert("<spring:message code="author.phone.confirm" />");
				}
			}
		</script>
		
<form:form action="administrator/register.do" modelAttribute="administrator">

<form:hidden path="userAccount.authorities" />
<form:hidden path="id"/>
<form:hidden path="version" />


	<fieldset>
		<legend align="left">
			<spring:message code="author.edit.data" />
		</legend>
		
		<form:label path="name">
			<spring:message code="author.name" />* :
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br/><br/>
		
		<form:label path="middleName">
			<spring:message code="author.middleName" />:
		</form:label>
		<form:input path="middleName" />
		<form:errors cssClass="error" path="middleName" />
		<br/><br/>
		
		<form:label path="surname">
			<spring:message code="author.surname" />* :
		</form:label>
		<form:input path="surname" />
		<form:errors cssClass="error" path="surname" />
		<br/><br/>
		
		<form:label path="photo">
			<spring:message code="author.photo" />:
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
		<br/><br/>
		
		<form:label path="email">
			<spring:message code="author.email" />* :
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
		<br/><br/>
		
		<form:label path="phoneNumber">
			<spring:message code="author.phoneNumber" />* :
		</form:label>
		<form:input path="phoneNumber" onchange="phoneRegexpCheck(this)"/>
		<form:errors cssClass="error" path="phoneNumber" />
		<br/><br/>
		
		<form:label path="address">
			<spring:message code="author.address" />:
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
	<br/><br/>
	
	
	</fieldset>
	
	<fieldset>
		<legend align="left">
			<spring:message code="author.userAccount.fieldset" />
		</legend>
		
		<form:label path="userAccount.username">
			<spring:message code="author.userAccount.username" />* :
		</form:label>
		<form:input path="userAccount.username" />
		<form:errors cssClass="error" path="userAccount.username" />

		<br/><br/>

		<form:label path="userAccount.password">
			<spring:message code="author.userAccount.password" />* :
		</form:label>
		<form:password path="userAccount.password" />
<form:errors cssClass="error" path="userAccount.password" />
		
		
	</fieldset>
	
	
	<input type="submit" name="save"
		value="<spring:message code="administrator.save" />" />&nbsp;
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('welcome/index.do')"
value="<spring:message code="author.cancel" />" />

</form:form>
