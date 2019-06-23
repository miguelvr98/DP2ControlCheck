<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:showtext fieldset="true" code="xxxx.moment" value="${moment}"/>
<acme:showtext fieldset="true" code="xxxx.body" value="${xxxx.body}"/>
<fieldset><legend><spring:message code="xxxx.picture"/></legend>
<a href="${row.picture}">
    <spring:message code="xxxx.picture"/></a>
</fieldset>

<security:authorize access="hasRole('COMPANY')">
    <acme:cancel url="xxxx/company/list.do?auditId${xxxx.audit.id}" code="xxxx.back"/>
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">
    <acme:cancel url="xxxx/auditor/list.do" code="xxxx.back"/>
</security:authorize>