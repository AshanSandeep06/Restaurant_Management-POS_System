package controller;

import com.jfoenix.controls.JFXTextField;
import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeSalaryManagementFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtEmpId;
    public JFXTextField txtEmpName;
    public JFXTextField txtWorkingHours;
    public JFXTextField txtPost;
    public Button btnSalarySheet;
    public Label lblTotalSalary;
    public TableView<PaymentTM> tblSalary;
    public TableColumn colPaymentID;
    public TableColumn colEmpId;
    public TableColumn colEmpName;
    public TableColumn colPost;
    public TableColumn colWorkingHours;
    public TableColumn colTotalSalary;
    public TableColumn colPaymentDate;
    public Label lblPaymentId;
    public Button btnPaySalary;
    public Button btnCalculateSalary;
    public Label lblEmployeeId;

    ObservableList<PaymentTM> tmList = FXCollections.observableArrayList();

    public void initialize(){
        btnCalculateSalary.setDisable(true);
        btnPaySalary.setDisable(true);
        btnSalarySheet.setDisable(true);
        lastPaymentId();
        loadTableData();

        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colPost.setCellValueFactory(new PropertyValueFactory<>("post"));
        colWorkingHours.setCellValueFactory(new PropertyValueFactory<>("workingHours"));
        colTotalSalary.setCellValueFactory(new PropertyValueFactory<>("totalSalary"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        tblSalary.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                lblPaymentId.setText(newValue.getPaymentId());
                txtEmpId.setText(newValue.getEmployeeId());
                txtEmpName.setText(newValue.getEmployeeName());
                txtPost.setText(newValue.getPost());
                txtWorkingHours.setText(String.valueOf(newValue.getWorkingHours()));
                lblTotalSalary.setText(String.valueOf(newValue.getTotalSalary()));
                btnSalarySheet.setDisable(false);
            }
        });
    }

    private void loadTableData(){
        try{
            ArrayList<Payment> list = new EmployeeSalaryCrudController().getAllPayments();
            tmList.clear();
            for (Payment tm : list){
                tmList.add(new PaymentTM(
                        tm.getPaymentId(),
                        tm.getEmployeeId(),
                        tm.getEmployeeName(),
                        tm.getPaymentDate(),
                        tm.getPost(),
                        tm.getWorkingHours(),
                        tm.getTotalSalary()
                ));
            }

            tblSalary.setItems(tmList);
            tblSalary.refresh();

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    private void lastPaymentId(){
        try {
            String paymentId = new EmployeeSalaryCrudController().getLastPaymentId();
            String finalId = "P-0001";

            if (paymentId != null) {

                String[] splitString = paymentId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "P-000" + id;
                } else if (id < 100) {
                    finalId = "P-00" + id;
                }else if (id < 1000) {
                    finalId = "P-0" + id;
                }else if (id < 10000) {
                    finalId = "P-" + id;
                }
                lblPaymentId.setText(finalId);
            } else {
                lblPaymentId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void paySalaryOnAction(ActionEvent event) {
        try{
            if(!txtEmpId.getText().isEmpty() && !lblTotalSalary.getText().equals("0.00")){
                if(checkEmployeeIdOnAction(null)){
                    Payment payment = new Payment(
                            lblPaymentId.getText(),
                            txtEmpId.getText(),
                            txtEmpName.getText(),
                            new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                            txtPost.getText(),
                            Double.parseDouble(txtWorkingHours.getText()),
                            Double.parseDouble(lblTotalSalary.getText())
                    );

                    boolean payementIsCommited = new PaymentCrudController().commitPayment(payment);
                    if(payementIsCommited){
                        new Alert(Alert.AlertType.INFORMATION,"Payment has been paid to" +txtEmpId.getText()+"-"+txtEmpName.getText()+"..!").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"Something went wrong,try again..!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR,"Invalid EmployeeID,try again..!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Empty fields,try again..!").show();
            }
            lastPaymentId();
            loadTableData();
            tblSalary.refresh();
            clearAll();
            lblTotalSalary.setText("0.00");

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void printSalarySheetOnAction(ActionEvent event) {
        HashMap map = new HashMap();
        map.put("employeeId",txtEmpId.getText());
        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("../view/reports/SalaryReport.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, map, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void employeeSearchOnAction(ActionEvent event) {
        //
        try {
            if (!txtEmpId.getText().isEmpty()) {
                if (!lblEmployeeId.getText().startsWith("*Invalid")) {
                    Employee e1 = new EmployeeController().getEmployee(txtEmpId.getText());
                    if (e1 != null) {
                        txtEmpName.setText(e1.getEmployeeName());
                        txtPost.setText(e1.getJobRole());
                        calculateWorkedHours();

                        txtEmpId.setStyle("-fx-border-color: null");
                        lblEmployeeId.setText("");
                        btnCalculateSalary.setDisable(false);
                        btnPaySalary.setDisable(false);
                        btnSalarySheet.setDisable(false);
                        lastPaymentId();
                    } else {
                        lastPaymentId();
                        clearAll();
                        btnCalculateSalary.setDisable(true);
                        btnPaySalary.setDisable(true);
                        btnSalarySheet.setDisable(true);
                        new Alert(Alert.AlertType.ERROR, "No Employee exists for this Id..!",ButtonType.OK).show();
                    }
                } else {
                    lastPaymentId();
                    clearAll();
                    btnCalculateSalary.setDisable(true);
                    btnPaySalary.setDisable(true);
                    btnSalarySheet.setDisable(true);
                    new Alert(Alert.AlertType.ERROR, "Invalid Employee ID..!").show();
                }
            } else {
                lastPaymentId();
                clearAll();
                btnCalculateSalary.setDisable(true);
                btnPaySalary.setDisable(true);
                btnSalarySheet.setDisable(true);
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void calculateWorkedHours(){
        try{
            ArrayList<Double> workedHours = new PaymentCrudController().getWorkedHours(txtEmpId.getText());
            double totalHours = 0;

            for(Double s : workedHours){
                totalHours+=s;
            }
            /*txtWorkingHours.setText(String.valueOf(Math.round(totalHours)));*/

            txtWorkingHours.setText(String.format("%.2f",totalHours));
        }catch (SQLException | ClassNotFoundException | NullPointerException | NumberFormatException e){

        }
    }

    private void clearAll() {
        txtEmpId.clear();
        txtEmpName.clear();
        txtPost.clear();
        txtWorkingHours.clear();

        lblTotalSalary.setText("0.00");
    }

    public void CalculateSalaryOnAction(ActionEvent event) {
        if(!txtEmpId.getText().isEmpty() && !txtWorkingHours.getText().isEmpty() && !txtPost.getText().isEmpty()){
            if(!lblEmployeeId.getText().startsWith("*Invalid")){
                double hours = Double.parseDouble(txtWorkingHours.getText());
                String jobRole = txtPost.getText();

                if(jobRole.equals("Cashier")){
                    double salaryPerHour = 150.00;
                    double totalSalary = salaryPerHour*hours;
                    lblTotalSalary.setText(String.valueOf(totalSalary));
                }else if(jobRole.equals("Rider")){
                    double salaryPerHour = 100.00;
                    double totalSalary = salaryPerHour*hours;
                    lblTotalSalary.setText(String.valueOf(totalSalary));
                }else if(jobRole.equals("Chef")){
                    double salaryPerHour = 150.00;
                    double totalSalary = salaryPerHour*hours;
                    lblTotalSalary.setText(String.valueOf(totalSalary));
                }else if(jobRole.equals("Waiter")){
                    double salaryPerHour = 100.00;
                    double totalSalary = salaryPerHour*hours;
                    lblTotalSalary.setText(String.valueOf(totalSalary));
                }else{
                    double salaryPerHour = 50.00;
                    double totalSalary = salaryPerHour*hours;
                    lblTotalSalary.setText(String.valueOf(totalSalary));
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Invalid EmployeeId..!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Empty fields,try again later..!").show();
        }
    }

    public boolean checkEmployeeIdOnAction(KeyEvent keyEvent) {
        String value = "^([E]{1}[-]([0-9]{3,4}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtEmpId.getText());
        if (matcher.matches()) {
            lblEmployeeId.setText(" ");
            txtEmpId.setStyle("-fx-border-color: null");
            return true;
        }else{
            lblEmployeeId.setText("*Invalid Employee Id");
            txtEmpId.setStyle("-fx-border-color: RED");
            return false;
        }
    }

    public void clearAllOnAction(ActionEvent event) {
        txtEmpId.clear();
        txtEmpName.clear();
        txtPost.clear();
        txtWorkingHours.clear();

        lblTotalSalary.setText("0.00");

        txtEmpId.setStyle("-fx-border-color: null");
        lblEmployeeId.setText("");

        lastPaymentId();

        btnCalculateSalary.setDisable(true);
        btnPaySalary.setDisable(true);
        btnSalarySheet.setDisable(true);
    }
}
