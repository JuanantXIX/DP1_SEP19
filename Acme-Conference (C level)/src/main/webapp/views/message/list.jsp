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


<h3><spring:message code ="message.sent.title"/></h3>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="sent" requestURI="${requestURI}" id="row">

<display:column property="subject" titleKey="message.subject" />
<display:column property="moment" titleKey="message.moment" />
<jstl:if test="${language=='en'}" >
<display:column titleKey="message.topic" sortable="true" sortName="name [0]" headerClass="sortable">
<jstl:out value="${row.topic.name[0]}"/>
</display:column>

</jstl:if>

<jstl:if test="${language=='es'}">
<display:column titleKey="message.topic" sortable="true" sortName="name [1]" headerClass="sortable" >
<jstl:out value="${row.topic.name[1]}"/>
</display:column></jstl:if>
<display:column property="body" titleKey="message.body" />
<display:column titleKey="message.sender" sortable="true" headerClass="sortable"> 
<jstl:out value="${row.sender.name.concat(' ').concat(row.sender.middleName).concat(' ').concat(row.sender.surname)}"/>
</display:column>
<display:column titleKey="message.recipient" sortable="true" headerClass="sortable"> 
<jstl:out value="${row.recipient.name.concat(' ').concat(row.recipient.middleName).concat(' ').concat(row.recipient.surname)}"/>
</display:column>
<display:column><a href="message/show.do?messageId=${row.id }"><spring:message code="message.show"/></a></display:column>
<display:column><a href="message/delete.do?messageId=${row.id }"><spring:message code="message.delete"/></a></display:column>

</display:table>
<br/><br/>
<h3><spring:message code ="message.received.title"/></h3>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="received" requestURI="${requestURI}" id="messageSent" sort="list">

<display:column property="subject" titleKey="message.subject" />
<display:column property="moment" titleKey="message.moment" />
<jstl:if test="${language=='en'}" >
<display:column titleKey="message.topic" sortable="true" sortName="name [0]" headerClass="sortable">
<jstl:out value="${messageSent.topic.name[0]}"/>
</display:column>

</jstl:if>

<jstl:if test="${language=='es'}">
<display:column titleKey="message.topic" sortable="true" sortName="name [1]" headerClass="sortable" >
<jstl:out value="${messageSent.topic.name[1]}"/>
</display:column></jstl:if>
<display:column property="body" titleKey="message.body" />
<display:column titleKey="message.sender" sortable="true" headerClass="sortable"> 
<jstl:out value="${messageSent.sender.name.concat(' ').concat(messageSent.sender.middleName).concat(' ').concat(messageSent.sender.surname)}"/>
</display:column>
<display:column titleKey="message.recipient" sortable="true" headerClass="sortable"> 
<jstl:out value="${messageSent.recipient.name.concat(' ').concat(messageSent.recipient.middleName).concat(' ').concat(messageSent.recipient.surname)}"/>
</display:column>
<display:column><a href="message/show.do?messageId=${messageSent.id }"><spring:message code="message.show"/></a></display:column>
<display:column><a href="message/delete.do?messageId=${messageSent.id }"><spring:message code="message.delete"/></a></display:column>
</display:table>
<br/>
<a href="message/send.do"><spring:message code="message.send.link"/></a>
