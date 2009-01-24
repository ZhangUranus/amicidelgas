package org.apache.jsp.entity;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.*;
import org.ofbiz.entity.model.*;
import org.ofbiz.entity.datasource.*;

public final class ModelInduceFromDb_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\r');
      out.write('\n');
      org.ofbiz.entity.GenericDelegator delegator = null;
      synchronized (request) {
        delegator = (org.ofbiz.entity.GenericDelegator) _jspx_page_context.getAttribute("delegator", PageContext.REQUEST_SCOPE);
        if (delegator == null){
          throw new java.lang.InstantiationException("bean delegator not found within scope");
        }
      }
      org.ofbiz.security.Security security = null;
      synchronized (request) {
        security = (org.ofbiz.security.Security) _jspx_page_context.getAttribute("security", PageContext.REQUEST_SCOPE);
        if (security == null){
          throw new java.lang.InstantiationException("bean security not found within scope");
        }
      }


if(security.hasPermission("ENTITY_MAINT", session)) {
  String helperName = request.getParameter("helperName");
  if(helperName == null || helperName.length() <= 0) {
    response.setContentType("text/html");

      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class='head3'><b>Please specify the helperName to induce from:</b></div>\r\n");
      out.write("<form action='' method=\"post\">\r\n");
      out.write("    <input type='TEXT' class='inputBox' size='40' name='helperName'>\r\n");
      out.write("    <input type=SUBMIT value='Induce!'>\r\n");
      out.write("</form>\r\n");

  } else {
      response.setContentType("text/xml");
      Collection messages = new LinkedList();
      GenericDAO dao = GenericDAO.getGenericDAO(helperName);
      List newEntList = dao.induceModelFromDb(messages);

      if(messages.size() > 0) {

      out.write("\r\n");
      out.write("ERRORS:\r\n");

        Iterator mIter = messages.iterator();
        while(mIter.hasNext()) {

      out.write('\r');
      out.write('\n');
      out.print((String)mIter.next());

        }
      }
      if(newEntList != null) {
        String title = "Entity of an Apache Open For Business Project (Apache OFBiz) Component";
        String description = "None";
        String copyright = "Copyright 2001-2006 The Apache Software Foundation";
        String author = "None";
        String version = "1.0";

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
      out.write("<!DOCTYPE entitymodel PUBLIC \"-//OFBiz//DTD Entity Model//EN\" \"http://www.ofbiz.org/dtds/entitymodel.dtd\">\r\n");
      out.write("<!--\r\n");
      out.write("Licensed to the Apache Software Foundation (ASF) under one\r\n");
      out.write("or more contributor license agreements.  See the NOTICE file\r\n");
      out.write("distributed with this work for additional information\r\n");
      out.write("regarding copyright ownership.  The ASF licenses this file\r\n");
      out.write("to you under the Apache License, Version 2.0 (the\r\n");
      out.write("\"License\"); you may not use this file except in compliance\r\n");
      out.write("with the License.  You may obtain a copy of the License at\r\n");
      out.write("\r\n");
      out.write("http://www.apache.org/licenses/LICENSE-2.0\r\n");
      out.write("\r\n");
      out.write("Unless required by applicable law or agreed to in writing,\r\n");
      out.write("software distributed under the License is distributed on an\r\n");
      out.write("\"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY\r\n");
      out.write("KIND, either express or implied.  See the License for the\r\n");
      out.write("specific language governing permissions and limitations\r\n");
      out.write("under the License.\r\n");
      out.write("-->\r\n");
      out.write("\r\n");
      out.write("<entitymodel>\r\n");
      out.write("  <!-- ========================================================= -->\r\n");
      out.write("  <!-- ======================== Defaults ======================= -->\r\n");
      out.write("  <!-- ========================================================= -->\r\n");
      out.write("    <title>");
      out.print(title);
      out.write("</title>\r\n");
      out.write("    <description>");
      out.print(description);
      out.write("</description>\r\n");
      out.write("    <copyright>");
      out.print(copyright);
      out.write("</copyright>\r\n");
      out.write("    <author>");
      out.print(author);
      out.write("</author>\r\n");
      out.write("    <version>");
      out.print(version);
      out.write("</version>\r\n");
      out.write("\r\n");
      out.write("  <!-- ========================================================= -->\r\n");
      out.write("  <!-- ======================== Data Model ===================== -->\r\n");
      out.write("  <!-- The modules in this file are as follows:                  -->\r\n");
      out.write("  <!-- ========================================================= -->\r\n");
      out.write("\r\n");
      out.write("  <!-- ========================================================= -->\r\n");
      out.write("  <!-- No Package Name -->\r\n");
      out.write("  <!-- ========================================================= -->\r\n");
 
  Iterator ecIter = newEntList.iterator();
  while(ecIter.hasNext()) {
    ModelEntity entity = (ModelEntity) ecIter.next();

      out.write("\r\n");
      out.write("    <entity entity-name=\"");
      out.print(entity.getEntityName());
      out.write('"');
if(!entity.getEntityName().equals(ModelUtil.dbNameToClassName(entity.getPlainTableName())) || !ModelUtil.javaNameToDbName(entity.getEntityName()).equals(entity.getPlainTableName()) ){
          
      out.write(" table-name=\"");
      out.print(entity.getPlainTableName());
      out.write('"');
}
      out.write("\r\n");
      out.write("            package-name=\"");
      out.print(entity.getPackageName());
      out.write('"');
if(entity.getDependentOn().length() > 0){
      out.write("\r\n");
      out.write("            dependent-on=\"");
      out.print(entity.getDependentOn());
      out.write('"');
}
if(!title.equals(entity.getTitle())){
      out.write("\r\n");
      out.write("            title=\"");
      out.print(entity.getTitle());
      out.write('"');
}
if(!copyright.equals(entity.getCopyright())){
      out.write("\r\n");
      out.write("            copyright=\"");
      out.print(entity.getCopyright());
      out.write('"');
}
if(!author.equals(entity.getAuthor())){
      out.write("\r\n");
      out.write("            author=\"");
      out.print(entity.getAuthor());
      out.write('"');
}
if(!version.equals(entity.getVersion())){
      out.write("\r\n");
      out.write("            version=\"");
      out.print(entity.getVersion());
      out.write('"');
}
      out.write('>');
if(!description.equals(entity.getDescription())){
      out.write("\r\n");
      out.write("      <description>");
      out.print(entity.getDescription());
      out.write("</description>");
}

  for (int y = 0; y < entity.getFieldsSize(); y++) {
    ModelField field = entity.getField(y);
      out.write("\r\n");
      out.write("      <field name=\"");
      out.print(field.getName());
      out.write('"');
if(!field.getColName().equals(ModelUtil.javaNameToDbName(field.getName()))){
      
      out.write(" col-name=\"");
      out.print(field.getColName());
      out.write('"');
}
      out.write(" type=\"");
      out.print(field.getType());
      out.write('"');
      out.write('>');

    for (int v = 0; v<field.getValidatorsSize(); v++) {
      String valName = (String) field.getValidator(v);
      
      out.write("<validate name=\"");
      out.print(valName);
      out.write('"');
      out.write('/');
      out.write('>');

    }
      out.write("</field>");

  }
  for (int y = 0; y < entity.getPksSize(); y++) {
    ModelField field = entity.getPk(y);
      out.write("\r\n");
      out.write("      <prim-key field=\"");
      out.print(field.getName());
      out.write('"');
      out.write('/');
      out.write('>');

  }
  if (entity.getRelationsSize() > 0) {
    for (int r = 0; r < entity.getRelationsSize(); r++) {
      ModelRelation relation = entity.getRelation(r);
      out.write("\r\n");
      out.write("      <relation type=\"");
      out.print(relation.getType());
      out.write('"');
if(relation.getTitle().length() > 0){
      out.write(" title=\"");
      out.print(relation.getTitle());
      out.write('"');
}
              
      out.write(" rel-entity-name=\"");
      out.print(relation.getRelEntityName());
      out.write('"');
      out.write('>');
for(int km=0; km<relation.getKeyMapsSize(); km++){ ModelKeyMap keyMap = relation.getKeyMap(km);
      out.write("\r\n");
      out.write("        <key-map field-name=\"");
      out.print(keyMap.getFieldName());
      out.write('"');
if(!keyMap.getFieldName().equals(keyMap.getRelFieldName())){
      out.write(" rel-field-name=\"");
      out.print(keyMap.getRelFieldName());
      out.write('"');
}
      out.write(' ');
      out.write('/');
      out.write('>');
}
      out.write("\r\n");
      out.write("      </relation>");

    }
  }
      out.write("\r\n");
      out.write("    </entity>");

  }
      out.write("\r\n");
      out.write("</entitymodel>\r\n");

      }
    } 
  }
else {
  
      out.write("ERROR: You do not have permission to use this page (ENTITY_MAINT needed)");

}

      out.write('\r');
      out.write('\n');
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
