<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<style type="text/css">
    .INDIGO {
        background-color: Indigo;
    }

    .PAPAYAWHIP {
        background-color: PapayaWhip;
    }

    .DARKSLATEGREY {
        background-color: DarkSlateGrey;
    }
</style>

<display:table name="xxxxs" id="row" requestURI="${requestURI}"
               pagesize="5" class="displaytag">

    <jstl:choose>
        <jstl:when test="${row.moment > haceUnMes }">
            <jstl:set var="css" value="INDIGO"/>
        </jstl:when>
        <jstl:when test="${row.moment < haceUnMes && row.moment>haceDosMeses}">
            <jstl:set var="css" value="DARKSLATEGREY"/>
        </jstl:when>
        <jstl:otherwise>
            <jstl:set var="css" value="PAPAYAWHIP" />
        </jstl:otherwise>
    </jstl:choose>

    <jstl:if test="${lang=='es'}">
    <spring:message code="xxxx.moment" var="columnTitle"/>
    <display:column title="${columnTitle}" property="moment" class="${css}" format="{0,date,dd-MM-yy HH:mm}">
    </display:column>
    </jstl:if>

    <jstl:if test="${lang=='en'}">
        <spring:message code="xxxx.moment" var="columnTitle"/>
        <display:column title="${columnTitle}" property="moment" class="${css}" format="{0,date,yy/MM/dd HH:mm}">
        </display:column>
    </jstl:if>

    <spring:message code="xxxx.body" var="body"/>
    <display:column title="${body}" class="${css}">
        <jstl:out value="${row.body}"/>
    </display:column>

    <spring:message code="xxxx.picture" var="picture"/>
    <display:column title="${picture}" class="${css}">
        <a href="${row.picture}">
            <spring:message code="xxxx.picture"/></a>
    </display:column>

    <security:authorize access="hasRole('COMPANY')">
        <spring:message code="xxxx.isFinal" var="isFinal"/>
        <display:column title="${isFinal}" class="${css}">
            <jstl:if test="${row.isFinal eq true}">
                <spring:message code="xxxx.final"/>
            </jstl:if>
            <jstl:if test="${row.isFinal eq false}">
                <spring:message code="xxxx.draft"/>
            </jstl:if>
        </display:column>

        <spring:message code="xxxx.show" var="show"/>
        <display:column title="${show}" class="${css}">
            <a href="xxxx/company/show.do?xxxxId=${row.id}">
                <spring:message code="xxxx.show"/></a>
        </display:column>

        <spring:message code="xxxx.edit" var="edit"/>
        <display:column title="${edit}" class="${css}">
            <jstl:if test="${!row.isFinal}">
                <a href="xxxx/company/edit.do?xxxxId=${row.id}">
                    <spring:message code="xxxx.edit"/></a>
            </jstl:if>
        </display:column>

        <spring:message code="xxxx.delete" var="delete"/>
        <display:column title="${delete}" class="${css}">
            <jstl:if test="${!row.isFinal}">
                <a href="xxxx/company/delete.do?xxxxId=${row.id}">
                    <spring:message code="xxxx.delete"/></a>
            </jstl:if>
        </display:column>

    </security:authorize>

    <security:authorize access="hasRole('AUDITOR')">
        <spring:message code="xxxx.show" var="show"/>
        <display:column title="${show}" class="${css}">
            <a href="xxxx/auditor/show.do?xxxxId=${row.id}">
                <spring:message code="xxxx.show"/></a>
        </display:column>

        <spring:message code="audit.show" var="audit"/>
        <display:column title="${audit}" class="${css}">
            <a href="audit/auditor/show.do?auditId=${row.audit.id}">
                <spring:message code="audit.show"/></a>
        </display:column>
    </security:authorize>

</display:table>

<security:authorize access="hasRole('COMPANY')">
    <acme:cancel url="/" code="xxxx.back"/>
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">
    <acme:cancel url="/" code="xxxx.back"/>
</security:authorize>