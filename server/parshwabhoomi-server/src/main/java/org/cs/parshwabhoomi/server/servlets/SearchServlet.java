/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cs.parshwabhoomi.server.core.DefaultSearchService;

/**
 *
 * @author saurabh
 */
public class SearchServlet extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            DefaultSearchService service=null;
            try {
                service=new DefaultSearchService("Google");
                
                //Retrieve the username & the search query.
                String query=request.getParameter("query");
                String username=request.getParameter("username");
                
                //Retrieve the latitude & longitude sent from the GPS enabled device.
                String lat=request.getParameter("lat");
                String lng=request.getParameter("lng");
                
                if(query!=null && username!=null){
                    String searchResultsXML=service.getSearchResultsXMLV2For(query,username,lat,lng/*"18.51657820","73.84310780"*/);
                    out.println(searchResultsXML);
                }
                           
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
            
            
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
