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
			var regexp1 = /^\+\d{1,3} \(\d{1,3}\) \d{4,}$/;
			var regexp2 = /^\+\d{1,3} \d{4,}$/;
			var regexp3 = /^\d{4,}$/;
			
			function phoneRegexpCheck(input) {
				var pass1 = regexp1.exec(input.value);
				var pass2 = regexp2.exec(input.value);
				var pass3 = regexp3.exec(input.value);
				if (!(pass1 || pass2 || pass3)) {
					alert("<spring:message code="reviewer.phone.confirm" />");
				}
			}
		</script>
		
<form:form action="reviewer/reviewer/edit.do" modelAttribute="reviewer">

<form:hidden path="userAccount.authorities" />
<form:hidden path="userAccount.id" />
<form:hidden path="userAccount.version" />
<form:hidden path="id"/>
<form:hidden path="version" />


	<fieldset>
		<legend align="left">
			<spring:message code="reviewer.edit.data" />
		</legend>
		
		<form:label path="name">
			<spring:message code="reviewer.name" />* :
		</form:label>
		<form:input path="name" />
		<form:errors cssClass="error" path="name" />
		<br/><br/>
		
		<form:label path="middleName">
			<spring:message code="reviewer.middleName" />:
		</form:label>
		<form:input path="middleName" />
		<form:errors cssClass="error" path="middleName" />
		<br/><br/>
		
		<form:label path="surname">
			<spring:message code="reviewer.surname" />* :
		</form:label>
		<form:input path="surname" />
		<form:errors cssClass="error" path="surname" />
		<br/><br/>
		
		<form:label path="photo">
			<spring:message code="reviewer.photo" />:
		</form:label>
		<form:input path="photo" />
		<form:errors cssClass="error" path="photo" />
		<br/><br/>
		
		<form:label path="email">
			<spring:message code="reviewer.email" />* :
		</form:label>
		<form:input path="email" />
		<form:errors cssClass="error" path="email" />
		<br/><br/>
		
		<form:label path="phoneNumber">
			<spring:message code="reviewer.phoneNumber" />:
		</form:label>
		<form:input path="phoneNumber" onchange="phoneRegexpCheck(this)"/>
		<form:errors cssClass="error" path="phoneNumber" />
		<br/><br/>
		
		<form:label path="address">
			<spring:message code="reviewer.address" />:
		</form:label>
		<form:input path="address" />
		<form:errors cssClass="error" path="address" />
		<br/><br/>
		
		<form:label path="keywords">
			<spring:message code="reviewer.keywords" />:
		</form:label>
		<form:input path="keywords" />
		<form:errors cssClass="error" path="keywords" />
	<br/><br/>
	
	
	</fieldset>
	
	<fieldset>
		<legend align="left">
			<spring:message code="reviewer.userAccount.fieldset" />
		</legend>
		
		<form:label path="userAccount.username">
			<spring:message code="reviewer.userAccount.username" />* :
		</form:label>
		<form:input path="userAccount.username" />
		<form:errors cssClass="error" path="userAccount.username" />

		<br/><br/>

		<form:label path="userAccount.password">
			<spring:message code="reviewer.userAccount.password" />* :
		</form:label>
		<form:password path="userAccount.password" />
<form:errors cssClass="error" path="userAccount.password" />
		
		
	</fieldset>
	
	
	<input type="submit" name="save"
		value="<spring:message code="reviewer.edit.save" />" />&nbsp;
	<input type="button" name="cancel"
		onclick="javascript: window.location.replace('welcome/index.do')"
value="<spring:message code="reviewer.edit.cancel" />" />

</form:form>
