/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2017-05-25 00:00:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.basefragments;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class _005ffooter_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"F1\">\r\n");
      out.write("\t<div class=\"FN\">\r\n");
      out.write("\t\t<strong> TÀI KHOẢN</strong>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"FB1\">\r\n");
      out.write("\t\t<a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/login\"><p>Tài\r\n");
      out.write("\t\t\t\tkhoản của bạn</p></a> <a\r\n");
      out.write("\t\t\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/shoppingCart\"><p>Giỏ\r\n");
      out.write("\t\t\t\thàng</p></a>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<div class=\"F1\">\r\n");
      out.write("\t<div class=\"FN\">\r\n");
      out.write("\t\t<strong> THÔNG TIN</strong>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"FB1\">\r\n");
      out.write("\t\t<a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/about\"><p>Giới\r\n");
      out.write("\t\t\t\tthiệu My Shoe Shop</p></a> <a href=\"huongdanmuahang.html\"><p>Hướng\r\n");
      out.write("\t\t\t\tdẫn mua hàng</p></a>\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- <a href=\"giaohang_thanhtoan.html\"><p>Giao hàng\r\n");
      out.write("\t\t\t\t\t- Thanh toán</p></a> -->\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<div class=\"F1\">\r\n");
      out.write("\t<div class=\"FN\">\r\n");
      out.write("\t\t<strong> THƯƠNG HIỆU</strong>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"FB1\">\r\n");
      out.write("\t\t<a href=\"http://www.nike.com/us/en_us/\"><p>Nike</p></a> <a\r\n");
      out.write("\t\t\thref=\"http://www.adidas.com.vn/\"><p>Adidas</p></a> <a\r\n");
      out.write("\t\t\thref=\"http://www.nike.com/us/en_us/c/converse\"><p>Converse</p> </a> <a\r\n");
      out.write("\t\t\thref=\"https://www.vans.com/\"><p>Vans</p></a>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<div class=\"F1\">\r\n");
      out.write("\t<div class=\"FN\">\r\n");
      out.write("\t\t<strong> KẾT NỐI</strong>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"FB2\">\r\n");
      out.write("\t\t<a href=\"https://www.facebook.com/Shop-1904713549750858/\" title=\"\"><img\r\n");
      out.write("\t\t\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/images/logo7.png\"></a> <a\r\n");
      out.write("\t\t\thref=\"#\" title=\"\"><img\r\n");
      out.write("\t\t\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/images/logo9.png\"></a> <a\r\n");
      out.write("\t\t\thref=\"https://www.instagram.com/kune.store/\" title=\"\"><img\r\n");
      out.write("\t\t\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/images/logo8.png\"></a> <a\r\n");
      out.write("\t\t\thref=\"#\" title=\"\"><img\r\n");
      out.write("\t\t\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/images/logo10.png\"></a>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<hr>\r\n");
      out.write("<p\r\n");
      out.write("\tstyle=\"color: #00ffff; margin-left: 20px; margin-top: 25px; font-size: 20px\">\r\n");
      out.write("\t<strong>MY SHOE SHOP - Giày chính hãng</strong>\r\n");
      out.write("</p>\r\n");
      out.write("<div class=\"F2\">\r\n");
      out.write("\t<div class=\"F3\">\r\n");
      out.write("\t\t<img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/images/logo11.png\">\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"F4\">Quận Hai Bà Trưng Hà Nội</div>\r\n");
      out.write("</div>\r\n");
      out.write("<div class=\"F2\">\r\n");
      out.write("\t<div class=\"F3\">\r\n");
      out.write("\t\t<img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/images/logo12.png\">\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"F4\">.......</div>\r\n");
      out.write("</div>\r\n");
      out.write("<div class=\"F2\">\r\n");
      out.write("\t<div class=\"F3\">\r\n");
      out.write("\t\t<img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/images/logo13.png\">\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"F4\">........</div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
