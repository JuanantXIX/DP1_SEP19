<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${customisation.bannerUrl}" alt="${customisation.systemName }" /></a></div>

<div>
<br/>
	<ul id="jMenu">
	<li><a class="fNiv"><spring:message	code="conference.master.title" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="conference/listForthcoming.do?d-16544-p=1"><spring:message code="conference.master.forthcoming" /></a></li>
					<li><a href="conference/listRunning.do?d-16544-p=1"><spring:message code="conference.master.running" /></a></li>
					<li><a href="conference/listPast.do?d-16544-p=1"><spring:message code="conference.master.past" /></a></li>
				</ul>
			</li>
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a href="administrator/register.do"><spring:message code="admin.register.header" /></a></li>
			<li><a href="administrator/edit.do"><spring:message code="admin.edit.header" /></a></li>
		
			<li><a class="fNiv"><spring:message	code="conference.administrator.header" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="conference/administrator/list.do?listingType=0"><spring:message code="conference.administrator.list.0" /></a></li>
					<li><a href="conference/administrator/list.do?listingType=1"><spring:message code="conference.administrator.list.1" /></a></li>
					<li><a href="conference/administrator/list.do?listingType=2"><spring:message code="conference.administrator.list.2" /></a></li>
					<li><a href="conference/administrator/list.do?listingType=3"><spring:message code="conference.administrator.list.3" /></a></li>
					<li><a href="conference/administrator/list.do?listingType=4"><spring:message code="conference.administrator.list.4" /></a></li>
				</ul>
			</li>
					<li><a href="topic/administrator/list.do"><spring:message code="topic.header.link" /></a></li>
					<li><a href="customisation/administrator/edit.do"><spring:message code="customisation.header.link" /></a></li>
					<li><a href="submission/administrator/list.do"><spring:message code="submission.header" /></a></li>
					<li><a href="dashboard/administrator/list.do"><spring:message code="dashboard.header" /></a></li>
			
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		
		<li><a class="fNiv"><spring:message	code="author.register.header" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="author/register.do"><spring:message code="author.register.header.link" /></a></li>
					<li><a href="reviewer/register.do"><spring:message code="reviewer.register.header.link" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('AUTHOR')">
		<li><a href="author/author/edit.do"><spring:message code="author.edit.commit" /> </a></li>
		<li><a href="submission/author/list.do"><spring:message code="submission.header" /> </a></li>
		<li><a href="registration/author/list.do"><spring:message code="registration.header" /> </a></li>
		
		</security:authorize>
		
		<security:authorize access="hasRole('REVIEWER')">
		<li><a href="reviewer/reviewer/edit.do"><spring:message code="reviewer.edit.save" /> </a></li>
		<li><a href="submission/reviewer/list.do"><spring:message code="submission.header" /> </a></li>
		<li><a href="report/reviewer/list.do"><spring:message code="report.header" /> </a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
		<li><a href="message/list.do"><spring:message code="message.header" /> </a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

