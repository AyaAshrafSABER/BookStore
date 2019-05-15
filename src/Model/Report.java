package Model;

import Controller.DbConnect;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;



public class Report {

    Connection con;
    Statement stmt;
    ResultSet rs;
    DbConnect db = DbConnect.getInstance();

    public void totalSales () {
        String reportSource =  System.getProperty("user.dir") +"/report/TotalSales.jrxml";
        String reportDest = System.getProperty("user.dir")+"/src/report/TotalSales.html";

        Map<String, Object> params = new HashMap<>();

        try {
            Connection con;

            Class.forName("com.mysql.cj.jdbc.Driver");
            con=db.getConnection();


            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);

            JasperViewer.viewReport(jasperPrint, false);
        }

        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getTop5Customer() {
        String reportSource =  System.getProperty("user.dir") + "/report/Top5Customer.jrxml";
        System.out.println(reportSource);
        String reportDest = System.getProperty("user.dir") +"/report/Top5Customer.html";

        Map<String, Object> params = new HashMap<String, Object>();

        try {
            Connection con;

            Class.forName("com.mysql.cj.jdbc.Driver");
            con=db.getConnection();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);

            JasperViewer.viewReport(jasperPrint, false);
        }

        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getTop10Books() {
        String reportSource =  System.getProperty("user.dir") +"/report/Top10SellingBook.jrxml";
        String reportDest = System.getProperty("user.dir") +"/src/report/Top10SellingBook.html";

        Map<String, Object> params = new HashMap<String, Object>();

        try {
            Connection con;

            Class.forName("com.mysql.cj.jdbc.Driver");
            con=db.getConnection();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportDest);

            JasperViewer.viewReport(jasperPrint, false);
        }

        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}