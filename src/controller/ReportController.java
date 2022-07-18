package controller;

import javafx.collections.ObservableList;
import model.CartTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;

public class ReportController {
    public void printKOT(ObservableList<CartTM> tmList,String orderId,String customerName,String mobileNumber, String orderType){
        HashMap map = new HashMap();
        map.put("orderId",orderId);
        map.put("customerName",customerName);
        map.put("mobileNumber",mobileNumber);
        map.put("orderType",orderType);

        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("../view/reports/KOT.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(tmList.toArray()));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void printBill(ObservableList<CartTM> tmList,String orderId,String customerName,String mobileNumber,String orderType,double subTotal,double deliveryCharges,double grandTotal,double cashAmount,double balanceAmount){
        HashMap map = new HashMap();
        map.put("orderId",orderId);
        map.put("customerName",customerName);
        map.put("mobileNumber",mobileNumber);
        map.put("orderType",orderType);
        map.put("subTotal",subTotal);
        map.put("deliveryCharges",deliveryCharges);
        map.put("grandTotal",grandTotal);
        map.put("cash",cashAmount);
        map.put("balance",balanceAmount);

        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("../view/reports/Payment Bill.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(tmList.toArray()));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void printInvoice(ObservableList<CartTM> tmList,String orderId,String customerName,String mobileNumber,String orderType,double subTotal,double deliveryCharges,double grandTotal){
        HashMap map = new HashMap();
        map.put("orderId",orderId);
        map.put("customerName",customerName);
        map.put("mobileNumber",mobileNumber);
        map.put("orderType",orderType);
        map.put("subTotal",subTotal);
        map.put("deliveryCharges",deliveryCharges);
        map.put("grandTotal",grandTotal);

        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("../view/reports/Order Invoice.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(tmList.toArray()));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
