package org.slstudio.acs;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-29
 * Time: ÉÏÎç12:22
 */
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rootPath = req.getSession().getServletContext().getRealPath("");
        String fileName = rootPath + File.separator +"WEB-INF"+File.separator+"classes"+File.separator+"log4j.properties";
        FileReader fr = new FileReader(fileName);
        int c = -1;
        StringBuffer sb = new StringBuffer();
        while ((c = fr.read())!= -1){
           sb.append(c);
        }
        fr.close();

        String responseStr = "";
        responseStr += "<html><body>";
        responseStr += sb.toString();
        responseStr += "</body></html>";
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html");
        resp.setContentLength(responseStr.length());
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        pw.write(responseStr);
        pw.flush();
        pw.close();

    }
}
