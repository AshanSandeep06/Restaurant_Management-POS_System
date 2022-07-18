package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeAttendenceFormController {
    public Label lblAttendanceID;
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtEmpId;
    public JFXTextField txtEmpName;
    public JFXTextField txtAddress;
    public JFXTextField txtNic;
    public JFXTextField txtWorkingHours;
    public JFXTextField txtContact;
    public JFXTextField txtPost;
    public ComboBox<String> cmbWorkingTypes;
    public TableView<AttendanceTM> tblAttendance;
    public TableColumn colAttendanceId;
    public TableColumn colEmployeeID;
    public TableColumn colEmployeeName;
    public TableColumn colWorkingType;
    public TableColumn colWorkingHours;
    public TableColumn colDate;
    public TableColumn colPost;
    public Label lblWorkingHours;
    public JFXButton btnAddAttendance;
    public JFXButton btnDeleteAttendance;
    public JFXButton btnModifyAttendance;
    public Label lblEmployeeId;
    public Button btnReport;
    public JFXButton btnAttendanceReport;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    ObservableList<AttendanceTM> attendanceTmList = FXCollections.observableArrayList();

    public void initialize(){

        // NumberFormat nf = NumberFormat.getInstance();
        // nf.setMinimumFractionDigits(2);

        btnAddAttendance.setDisable(true);
        btnModifyAttendance.setDisable(true);
        btnDeleteAttendance.setDisable(true);
        btnAttendanceReport.setDisable(true);
        /*System.out.println(AdminDashBoardFormController.StaticlblTime.getText());*/

        cmbWorkingTypes.getItems().addAll("Full Day","Half Day");
        lastAttendanceId();
        loadAttendanceTableData();

        colAttendanceId.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colWorkingType.setCellValueFactory(new PropertyValueFactory<>("workingType"));
        colWorkingHours.setCellValueFactory(new PropertyValueFactory<>("workingHours"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("attendDate"));
        colPost.setCellValueFactory(new PropertyValueFactory<>("jobRole"));

        cmbWorkingTypes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setWorkingHours(newValue);
            }
        });

        tblAttendance.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                btnModifyAttendance.setDisable(false);
                btnDeleteAttendance.setDisable(false);
                btnAttendanceReport.setDisable(false);
                setAttendanceData(newValue);
            }
        });
    }

    private void setAttendanceData(AttendanceTM tm){
        Employee employee = new Employee();
        try{
            employee = new EmployeeController().getEmployee(tm.getEmployeeId());
        }catch (SQLException | ClassNotFoundException e){

        }
        lblAttendanceID.setText(tm.getAttendanceId());
        txtEmpId.setText(tm.getEmployeeId());
        txtEmpName.setText(tm.getEmployeeName());
        txtNic.setText(employee.getEmployeeNIC());
        txtPost.setText(tm.getJobRole());
        txtAddress.setText(employee.getEmployeeAddress());
        txtContact.setText(employee.getEmployeeContactNumber());

        txtWorkingHours.setText(df.format(employee.getWorkingHours()));

        cmbWorkingTypes.setValue(tm.getWorkingType());
        lblWorkingHours.setText(df.format(tm.getWorkingHours()));
    }

    private void setWorkingHours(String selectedType){
        if(selectedType.equalsIgnoreCase("Full Day")){
            lblWorkingHours.setText(txtWorkingHours.getText());
        }else{
            /*lblWorkingHours.setText(String.format("%.2f",Integer.parseInt(txtWorkingHours.getText())/2+0.30));*/
            double value = Double.parseDouble(txtWorkingHours.getText());
            double x=value;
            if(x%2==0){
                lblWorkingHours.setText(df.format(value/2));
            }else{
                lblWorkingHours.setText(df.format(value/2-0.20));
            }
        }
    }

    private void loadAttendanceTableData(){
        try{
            ArrayList<Attendance> list = new EmployeeAttendanceCrudController().getAllAttendances();
            attendanceTmList.clear();
            for (Attendance att : list){
                attendanceTmList.add(new AttendanceTM(
                        att.getAttendanceId(),
                        att.getEmployeeId(),
                        att.getEmployeeName(),
                        att.getWorkingType(),
                        att.getWorkingHours(),
                        att.getAttendDate(),
                        att.getJobRole()
                ));
            }

            tblAttendance.setItems(attendanceTmList);
            tblAttendance.refresh();

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    private void lastAttendanceId(){
        try {
            String attendanceId = new EmployeeAttendanceCrudController().getLastAttendanceId();
            String finalId = "A-000001";

            if (attendanceId != null) {

                String[] splitString = attendanceId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "A-00000" + id;
                } else if (id < 100) {
                    finalId = "A-0000" + id;
                } else if(id < 1000){
                    finalId = "A-000" + id;
                }else if(id < 10000){
                    finalId = "A-00" + id;
                }else if(id < 100000){
                    finalId = "A-0" + id;
                }else if(id < 1000000){
                    finalId = "A-" + id;
                }
                lblAttendanceID.setText(finalId);
            } else {
                lblAttendanceID.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void employeeSearchOnAction(ActionEvent event) {
        try {
            if (!txtEmpId.getText().isEmpty()) {
                if (!lblEmployeeId.getText().startsWith("*Invalid")) {
                    Employee e1 = new EmployeeController().getEmployee(txtEmpId.getText());
                    if (e1 != null) {
                        cmbWorkingTypes.setValue(null);
                        lblWorkingHours.setText("0");
                        txtEmpName.setText(e1.getEmployeeName());
                        txtNic.setText(e1.getEmployeeNIC());
                        txtPost.setText(e1.getJobRole());
                        txtAddress.setText(e1.getEmployeeAddress());
                        txtContact.setText(e1.getEmployeeContactNumber());
                        txtWorkingHours.setText(String.valueOf(e1.getWorkingHours()));

                        txtEmpId.setStyle("-fx-border-color: null");
                        lblEmployeeId.setText("");
                        btnAddAttendance.setDisable(false);
                        btnAttendanceReport.setDisable(false);
                        lastAttendanceId();
                    } else {
                        lastAttendanceId();
                        clearAll();
                        btnAddAttendance.setDisable(true);
                        btnAttendanceReport.setDisable(true);
                        new Alert(Alert.AlertType.ERROR, "No Employee exists for this Id..!",ButtonType.OK).show();
                    }
                } else {
                    lastAttendanceId();
                    clearAll();
                    btnAddAttendance.setDisable(true);
                    btnAttendanceReport.setDisable(true);
                    new Alert(Alert.AlertType.ERROR, "Invalid Employee ID..!").show();
                }
            } else {
                lastAttendanceId();
                clearAll();
                btnAddAttendance.setDisable(true);
                btnAttendanceReport.setDisable(true);
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void clearAll(){
        txtEmpId.clear();
        txtEmpName.clear();
        txtNic.clear();
        txtPost.clear();
        txtAddress.clear();
        txtContact.clear();
        txtWorkingHours.clear();

        cmbWorkingTypes.setValue(null);
        lblWorkingHours.setText("0");
    }
    // System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

    public void addAttendanceOnAction(ActionEvent event) {
        try{
            if(!lblAttendanceID.getText().isEmpty() && !txtEmpId.getText().isEmpty() && cmbWorkingTypes.getValue()!=null && !lblWorkingHours.getText().isEmpty()){
                if(!lblEmployeeId.getText().startsWith("*Invalid")){
                    Attendance attendance = new Attendance(
                            lblAttendanceID.getText(),
                            txtEmpId.getText(),
                            txtEmpName.getText(),
                            new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                            cmbWorkingTypes.getValue(),
                            Double.parseDouble(lblWorkingHours.getText()),
                            txtPost.getText()
                    );
                    boolean attendanceIsAdded = new EmployeeAttendanceCrudController().addAttendance(attendance);
                    if(attendanceIsAdded){
                        new Alert(Alert.AlertType.CONFIRMATION,"Attendance added successfully..!").show();
                        clearAll();
                        lastAttendanceId();
                        loadAttendanceTableData();
                        tblAttendance.refresh();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"Something went wrong,Please try again..!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR,"Invalid fields,Please enter valid data & try again..!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Empty fields try again..!").show();
            }
            btnAddAttendance.setDisable(true);
            btnAttendanceReport.setDisable(true);
        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void deleteAttendanceOnAction(ActionEvent event) {
        try{
            if(!lblAttendanceID.getText().isEmpty() && !txtEmpId.getText().isEmpty() && cmbWorkingTypes.getValue()!=null && !lblWorkingHours.getText().isEmpty() && !attendanceTmList.isEmpty() && !tblAttendance.getItems().isEmpty()) {
                if (!lblEmployeeId.getText().startsWith("*Invalid")) {
                    AttendanceTM selectedItem = tblAttendance.getSelectionModel().getSelectedItem();
                    if(selectedItem!=null){
                        boolean attendanceIsDeleted = new EmployeeAttendanceCrudController().deleteAttendance(selectedItem.getAttendanceId());
                        if(attendanceIsDeleted){
                            new Alert(Alert.AlertType.CONFIRMATION,"Attendance deleted successfully..!").show();
                            clearAll();
                            lastAttendanceId();
                            loadAttendanceTableData();
                            tblAttendance.refresh();
                        }else{
                            new Alert(Alert.AlertType.ERROR,"Something went wrong,Please try again..!").show();
                        }

                    }else{
                        new Alert(Alert.AlertType.ERROR,"Can't delete attendance therefore,Select Item from the table..!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR,"Invalid fields,Please enter valid data & try again..!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Empty fields try again..!").show();
            }
            btnDeleteAttendance.setDisable(true);

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void modifyAttendanceOnAction(ActionEvent event) {
        try{
            if(!lblAttendanceID.getText().isEmpty() && !txtEmpId.getText().isEmpty() && cmbWorkingTypes.getValue()!=null && !lblWorkingHours.getText().isEmpty() && !attendanceTmList.isEmpty()) {
                if (!lblEmployeeId.getText().startsWith("*Invalid")) {
                    AttendanceTM tm = tblAttendance.getSelectionModel().getSelectedItem();
                    if(tm!=null){
                        Attendance attendance = new Attendance(
                                lblAttendanceID.getText(),
                                txtEmpId.getText(),
                                txtEmpName.getText(),
                                new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                                cmbWorkingTypes.getValue(),
                                Double.parseDouble(lblWorkingHours.getText()),
                                txtPost.getText()
                        );
                        boolean attendanceIsUpdated = new EmployeeAttendanceCrudController().updateAttendance(attendance);
                        if(attendanceIsUpdated){
                            new Alert(Alert.AlertType.CONFIRMATION,"Attendance updated successfully..!").show();
                            clearAll();
                            lastAttendanceId();
                            loadAttendanceTableData();
                            tblAttendance.refresh();
                        }else{
                            new Alert(Alert.AlertType.ERROR,"Something went wrong,Please try again..!").show();
                        }
                    }else{
                        new Alert(Alert.AlertType.ERROR,"Can't update attendance therefore,Select Item from the table..!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR,"Invalid fields,Please enter valid data & try again..!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Empty fields try again..!").show();
            }
            btnModifyAttendance.setDisable(true);
        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void checkEmployeeIdOnAction(KeyEvent keyEvent) {
        String value = "^([E]{1}[-]([0-9]{3,4}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtEmpId.getText());
        if (matcher.matches()) {
            lblEmployeeId.setText(" ");
            txtEmpId.setStyle("-fx-border-color: null");
        }else{
            lblEmployeeId.setText("*Invalid Employee Id");
            txtEmpId.setStyle("-fx-border-color: RED");
        }
    }


    public void attendanceReportOnAction(MouseEvent event) {
        String employeeId = txtEmpId.getText();
        HashMap paramMap = new HashMap();
        paramMap.put("id",employeeId);
        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("../view/reports/EmployeeAttendanceReport.jasper"));
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport,paramMap,connection);
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clearAllOnAction(ActionEvent event) {
        txtEmpId.clear();
        txtEmpName.clear();
        txtNic.clear();
        txtPost.clear();
        txtAddress.clear();
        txtContact.clear();
        txtWorkingHours.clear();

        cmbWorkingTypes.setValue(null);
        lblWorkingHours.setText("0");

        txtEmpId.setStyle("-fx-border-color: null");
        lblEmployeeId.setText("");

        lastAttendanceId();

        btnAddAttendance.setDisable(true);
        btnAttendanceReport.setDisable(true);

        btnModifyAttendance.setDisable(true);
        btnDeleteAttendance.setDisable(true);
    }
}
