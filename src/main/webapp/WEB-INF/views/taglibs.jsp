<%-- <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <spring:url value="/resources/css/style.css" var="stylesCss" />
 <spring:url value="/resources/js/front.js" var="frontJS" />
 <spring:url value="/resources/js/jquery-2.2.2.min.js" var="jQuery" />
 <spring:url value="/resources/images" var="images" />
 <c:set var="contextPath" value="${pageContext.request.contextPath}" />
 --%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- 
<spring:url value="/static/css/bootstrap.css" var="bootstrapCss" />
<spring:url value="/static/css/styles.css" var="stylesCss" />
 --%>
