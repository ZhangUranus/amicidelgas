package org.apache.jsp.error;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.ofbiz.base.util.*;

public final class error_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html");
      response.addHeader("X-Powered-By", "JSP/2.0");
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
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>Open For Business Message</title>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
 String errorMsg = (String) request.getAttribute("_ERROR_MESSAGE_"); 
      out.write("\r\n");
      out.write("\r\n");
      out.write("<body bgcolor=\"#FFFFFF\">\r\n");
      out.write("<div align=\"center\">\r\n");
      out.write("  <br/>\r\n");
      out.write("  <table width=\"100%\" border=\"1\" height=\"200\">\r\n");
      out.write("    <tr>\r\n");
      out.write("      <td>\r\n");
      out.write("        <table width=\"100%\" border=\"0\" height=\"200\">\r\n");
      out.write("          <tr bgcolor=\"#CC6666\"> \r\n");
      out.write("            <td height=\"45\"> \r\n");
      out.write("              <div align=\"center\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"4\" color=\"#FFFFFF\"><b>:ERROR MESSAGE:</b></font></div>\r\n");
      out.write("            </td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr> \r\n");
      out.write("            <td>\r\n");
      out.write("              <div align=\"left\"><font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"2\">");
      out.print(UtilFormatOut.replaceString(errorMsg, "\n", "<br/>"));
      out.write("</font></div>\r\n");
      out.write("            </td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("      </td>\r\n");
      out.write("    </tr>\r\n");
      out.write("  </table>\r\n");
      out.write("</div>\r\n");
      out.write("<div align=\"center\"></div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
