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


<form:form action="category/administrator/edit.do" modelAttribute="category">

<form:hidden path="id"/>
<form:hidden path="version" />
		
		<form:label path="title">
			<spring:message code="category.title" />* :
		</form:label>
		<form:input path="title" placeholder ="EXAMPLE, EJEMPLO"/>
		<form:errors cssClass="error" path="title" />
		<br/><br/>
		
		<jstl:if test="${language=='en'}" >
			<spring:message code="category.parents" />:*
			<form:select multiple="true" id="parents" path="parents">
			<form:options items="${parents}" itemLabel="title[0]" itemValue="id"/>
			</form:select>
			   <br />
			   
			<br />
		</jstl:if>

	<jstl:if test="${language=='es'}">
	<spring:message code="category.parents" />:*
			<form:select multiple="true" id="parents" path="parents">
			<form:options items="${parents}" itemLabel="title[1]" itemValue="id"/>
			</form:select>
			   <br />
			   
			<br />
		</jstl:if>
		
	<jstl:if test="${language=='en'}" >
			<spring:message code="category.children" />:*
			<form:select multiple="true" id="children" path="children">
			<form:options items="${children}" itemLabel="title[0]" itemValue="id"/>
			</form:select>
			   <br />
			   
			<br />
		</jstl:if>

	<jstl:if test="${language=='es'}">
	<spring:message code="category.children" />:*
			<form:select multiple="true" id="children" path="children">
			<form:options items="${children}" itemLabel="title[1]" itemValue="id"/>
			</form:select>
			   <br />
			   
			<br />
		</jstl:if>
			


	<input type="submit" name="save"
		value="<spring:message code="utils.save" />" />&nbsp;
	
	<input type="button" name="cancel"	
	onclick="javascript: window.location.replace('category/administrator/list.do')"
	value="<spring:message code="utils.cancel" />" />

</form:form>
