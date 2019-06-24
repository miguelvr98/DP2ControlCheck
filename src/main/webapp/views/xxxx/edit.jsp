<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="mokejima/company/edit.do" modelAttribute="xxxx">

    <form:hidden path="id" readOnly = "true"/>
    <input type="hidden" name="auditId" value="${auditId}" readonly>

    <acme:textarea code="xxxx.body" path="body"/>
    <br>

    <acme:textarea code="xxxx.picture" path="picture" />
    <jstl:if test="${not empty pictureError }">
        <p class="error">${pictureError }</p>
    </jstl:if>
    <br/>

    <spring:message code="xxxx.isFinal"/>
    <form:select path="isFinal" multiple="false">
        <form:option value="0"><spring:message code="xxxx.draft"/></form:option>
        <form:option value="1"><spring:message code="xxxx.final"/></form:option>
    </form:select>
    <form:errors class="error" path="isFinal"/>
    <br>

    <acme:submit name="save" code="xxxx.save"/>

    <acme:cancel url="mokejima/company/list.do?auditId=${auditId}" code="xxxx.cancel"/>

</form:form>