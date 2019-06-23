<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="audits" id="row" requestURI="${requestURI}"
               pagesize="5" class="displaytag">


    <spring:message code="audit.moment" var="moment"/>
    <display:column property="moment" title="${moment}"/>

    <spring:message code="audit.text" var="text"/>
    <display:column property="text" title="${text}"/>

    <spring:message code="audit.score" var="score"/>
    <display:column property="score" title="${score}"/>

    <security:authorize access="hasRole('COMPANY')">
        <spring:message code="audit.xxxxs" var="xxxxs"/>
        <display:column title="${xxxxs}" >
            <jstl:if test="${row.isFinal && b}">
                <a href="xxxx/company/list.do?auditId=${row.id}">
                    <spring:message code="audit.xxxxs"/></a>
            </jstl:if>
        </display:column>

        <spring:message code="xxxx.create" var="create"/>
        <display:column title="${create}" >
            <jstl:if test="${row.isFinal && b}">
                <a href="xxxx/company/create.do?auditId=${row.id}">
                    <spring:message code="xxxx.create"/></a>
            </jstl:if>
        </display:column>
    </security:authorize>

</display:table>

<acme:cancel url="position/listNotLogged.do" code="audit.back"/>
