package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.Customer;
import model.Reservation;
import model.ReservationTM;
import util.ValidationUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationFormController {
    public JFXDatePicker viewDatePicker;
    public TableView<ReservationTM> tblViewReservation;
    public TableColumn colResId;
    public TableColumn colCusName;
    public TableColumn colCusMobileNumber;
    public TableColumn colTime;
    public TableColumn colParticipants;
    public TextField txtAddResCusNumber;
    public TextField txtAddResCusName;
    public JFXButton btnAddCustomer;
    public Text lblReservationID;
    public JFXDatePicker addResDatePicker;
    public JFXTimePicker addResTimePicker;
    public TextField addResParticipants;
    public JFXButton btnAddReservation;
    public TextField txtAddResCusId;
    public TextField txtAddResCusAddress;
    public TextField txtManageCustomerName;
    public JFXDatePicker manageResDatePicker;
    public JFXTimePicker manageResTimePicker;
    public TextField manageResParticipants;
    public TableView<ReservationTM> tblManageReservation;
    public TableColumn colManageResId;
    public TableColumn colManageResDate;
    public TableColumn colManageResTime;
    public TableColumn colManageResParticipants;
    public TextField txtManageMobileNumber;
    public Label lblNumber;
    public Label lblCustomerId;
    public Label lblCusName;
    public Label lblCusAddress;
    public Label lblParticipants;
    public Label lblManageMobileNumber;
    public Label lblManageCusName;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public Label lblManageParticipants;

//    LinkedHashMap<TextField,Pattern> map = new LinkedHashMap<>();

    public void getLastReservationId(){
        try {
            String reservationId = new ReservationCrudController().getLastReservationId();
            String finalId = "R-000001";

            if (reservationId != null) {

                String[] splitString = reservationId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "R-00000" + id;
                } else if (id < 100) {
                    finalId = "R-0000" + id;
                } else if (id < 1000){
                    finalId = "R-000" + id;
                }else if(id<10000){
                    finalId = "R-00" + id;
                }else if(id<100000){
                    finalId = "R-0" + id;
                }else{
                    finalId = "R-" + id;
                }
                lblReservationID.setText(finalId);
            } else {
                lblReservationID.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        getLastReservationId();
        btnAddCustomer.setDisable(true);
        btnAddReservation.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        colResId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCusMobileNumber.setCellValueFactory(new PropertyValueFactory<>("customerMobileNumber"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("reservationTime"));
        colParticipants.setCellValueFactory(new PropertyValueFactory<>("totalParticipants"));

        colManageResId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colManageResDate.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        colManageResTime.setCellValueFactory(new PropertyValueFactory<>("reservationTime"));
        colManageResParticipants.setCellValueFactory(new PropertyValueFactory<>("totalParticipants"));

        tblManageReservation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                btnDelete.setDisable(false);
                manageResDatePicker.setValue(LocalDate.parse(newValue.getReservationDate()));
                // LocalTime.parse("4:38", DateTimeFormatter.ofPattern("H:m"));
                    /*String[] splitString = newValue.getReservationTime().split(" ");
                    String time = splitString[0];*/
                manageResTimePicker.setValue(LocalTime.parse(newValue.getReservationTime()));
                manageResParticipants.setText(String.valueOf(newValue.getTotalParticipants()));
            }
        });

        /*Pattern numberPattern = Pattern.compile("^(0){1}[0-9]{9}$");
        Pattern customerIdPattern = Pattern.compile("^([C]{1}([0-9]{3,4}))$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,40}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,50}$");

        map.put(txtAddResCusNumber,numberPattern);
        map.put(txtAddResCusId,customerIdPattern);
        map.put(txtAddResCusName,namePattern);
        map.put(txtAddResCusAddress,addressPattern);*/
    }

    public void clearView(Event event) {
        tblViewReservation.getItems().clear();
        viewDatePicker.getEditor().clear();
    }

    public void getReservations(Event event) {
        try{
            tblViewReservation.setItems(new ReservationCrudController().getReservationDetails(viewDatePicker.getValue().toString()));

        }catch (Exception e){

        }
        /*System.out.println(Date.valueOf(viewDatePicker.getValue().toString()));*/
    }

    public void clearReservation(Event event) {
        cancelReservation();
    }

    public void searchCustomerOnAction(ActionEvent event) {
        //
        try {
            if (!txtAddResCusNumber.getText().isEmpty()) {
                if (!lblNumber.getText().startsWith("*Invalid")) {
                    Customer c1 = new CustomerCrudController().getCustomerWithMobileNumber(txtAddResCusNumber.getText());
                    if (c1 != null) {
                        txtAddResCusId.setText(c1.getCustomerId());
                        txtAddResCusName.setText(c1.getName());
                        txtAddResCusAddress.setText(c1.getAddress());
                        txtAddResCusNumber.setText(c1.getContactNumber());

                        txtAddResCusId.setStyle("-fx-border-color: null");
                        txtAddResCusName.setStyle("-fx-border-color: null");
                        txtAddResCusAddress.setStyle("-fx-border-color: null");

                        lblCustomerId.setText("");
                        lblCusName.setText("");
                        lblCusAddress.setText("");

                        btnAddCustomer.setDisable(true);
                        /*btnAddReservation.setDisable(false);*/
                    } else {
//                        clearAll();
                        lastCustomerId();
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR, "No customer exists for this Mobile Number..! Do you want to add this customer",ButtonType.YES,ButtonType.NO).showAndWait();
                        if(buttonType.get().equals(ButtonType.YES)){
                            btnAddCustomer.setDisable(true);
                            btnAddReservation.setDisable(true);
                        }else{
                            clearAll();
                            btnAddCustomer.setDisable(true);
                            btnAddReservation.setDisable(true);
                        }
                        lastCustomerId();
                    }
                } else {
                    clearAll();
                    lastCustomerId();
                    btnAddReservation.setDisable(true);
                    btnAddCustomer.setDisable(true);
                    new Alert(Alert.AlertType.ERROR, "Invalid Mobile Number..!").show();
                }
            } else {
                clearAll();
                lastCustomerId();
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
                new Alert(Alert.AlertType.ERROR, "Empty field try again..!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void lastCustomerId() {
        try {
            String customerId = new CustomerCrudController().getLastCustomerId();
            String finalId = "C001";

            if (customerId != null) {

                String[] splitString = customerId.split("C");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "C00" + id;
                } else if (id < 100) {
                    finalId = "C0" + id;
                } else {
                    finalId = "C" + id;
                }
                txtAddResCusId.setText(finalId);
            } else {
                txtAddResCusId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearAll() {
        txtAddResCusNumber.clear();
        txtAddResCusId.clear();
        txtAddResCusAddress.clear();
        txtAddResCusName.clear();

        txtAddResCusNumber.setStyle("-fx-border-color: null");
        txtAddResCusId.setStyle("-fx-border-color: null");
        txtAddResCusAddress.setStyle("-fx-border-color: null");
        txtAddResCusName.setStyle("-fx-border-color: null");

        lblCustomerId.setText("");
        lblCusName.setText("");
        lblNumber.setText("");
        lblCusAddress.setText("");

        btnAddCustomer.setDisable(true);
        btnAddReservation.setDisable(true);
    }

    public void addCustomerOnAction(ActionEvent event) {
        //
        try{
            if(!txtAddResCusId.getText().isEmpty() && !txtAddResCusName.getText().isEmpty() && !txtAddResCusNumber.getText().isEmpty() && !txtAddResCusAddress.getText().isEmpty()) {
                if (!lblCustomerId.getText().startsWith("*Invalid") && !lblCusName.getText().startsWith("*Invalid") && !lblNumber.getText().startsWith("*Invalid") && !lblCusAddress.getText().startsWith("*Invalid")) {
                    boolean customerIsSaved = new CustomerCrudController().addCustomer(new Customer(txtAddResCusId.getText(),txtAddResCusName.getText(),txtAddResCusAddress.getText(),txtAddResCusNumber.getText()));
                    if(customerIsSaved){
                        new Alert(Alert.AlertType.CONFIRMATION,"Customer added successfully..!").show();
                        clearAll();
                        lastCustomerId();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"Try again..!",ButtonType.CLOSE).show();
                        clearAll();
                        lastCustomerId();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR,"Invalid data please,enter valid data..!").show();
                    clearAll();
                    lastCustomerId();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Empty fields, check fields again..!").show();
                clearAll();
                lastCustomerId();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,"Try again..!",ButtonType.CLOSE).show();
            clearAll();
            lastCustomerId();
        }
    }

    public void addReservationOnAction(ActionEvent event) {
        //
        try{
            if(!txtAddResCusId.getText().isEmpty() && !txtAddResCusName.getText().isEmpty() && !txtAddResCusNumber.getText().isEmpty() && !txtAddResCusAddress.getText().isEmpty() && !addResDatePicker.getEditor().getText().isEmpty() && !addResTimePicker.getEditor().getText().isEmpty() && !addResParticipants.getText().isEmpty()) {
                if(!lblCustomerId.getText().startsWith("*Invalid") && !lblCusName.getText().startsWith("*Invalid") && !lblNumber.getText().startsWith("*Invalid") && !lblCusAddress.getText().startsWith("*Invalid") && !lblParticipants.getText().startsWith("*Invalid")){
                    boolean reservationIsAdded = new ReservationCrudController().addReservation(new Reservation(lblReservationID.getText(),txtAddResCusId.getText(),addResDatePicker.getValue().toString(),addResTimePicker.getValue().toString(),Integer.parseInt(addResParticipants.getText())));
                    if(reservationIsAdded){
                        new Alert(Alert.AlertType.CONFIRMATION, "Reservation is completed..!", ButtonType.OK).show();
                        btnAddCustomer.setDisable(true);
                        btnAddReservation.setDisable(true);
                        clearAll();
                        addResDatePicker.setValue(null);
                        addResTimePicker.setValue(null);
                        addResParticipants.clear();
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.OK).show();
                        btnAddCustomer.setDisable(true);
                        btnAddReservation.setDisable(true);
                        /*clearAll();*/
                        addResDatePicker.setValue(null);
                        addResTimePicker.setValue(null);
                        addResParticipants.setText("");
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid Fields try again..!", ButtonType.CLOSE).show();
                    btnAddCustomer.setDisable(true);
                    btnAddReservation.setDisable(true);
                    /*clearAll();*/
                    addResDatePicker.setValue(null);
                    addResTimePicker.setValue(null);
                    addResParticipants.setText("");
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!", ButtonType.CLOSE).show();
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
                /*clearAll();*/
                addResDatePicker.setValue(null);
                addResTimePicker.setValue(null);
                addResParticipants.setText("");
            }
            getLastReservationId();

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void cancelReservationOnAction(ActionEvent event) {
        cancelReservation();
    }

    private void cancelReservation(){
        addResDatePicker.getEditor().clear();
        addResTimePicker.getEditor().clear();
        txtAddResCusNumber.clear();
        txtAddResCusId.clear();
        txtAddResCusName.clear();
        txtAddResCusAddress.clear();
        addResParticipants.clear();

        lblCustomerId.setText("");
        lblCusName.setText("");
        lblNumber.setText("");
        lblCusAddress.setText("");
        lblCustomerId.setText("");
        lblParticipants.setText("");

        txtAddResCusNumber.setStyle("-fx-border-color: null");
        txtAddResCusId.setStyle("-fx-border-color: null");
        txtAddResCusName.setStyle("-fx-border-color: null");
        txtAddResCusAddress.setStyle("-fx-border-color: null");
        addResParticipants.setStyle("-fx-border-color: null");

        btnAddCustomer.setDisable(true);
        btnAddReservation.setDisable(true);
    }

    public void clearManageReservation(Event event) {
        manageResParticipants.clear();
        txtManageCustomerName.clear();
        txtManageMobileNumber.clear();

        manageResDatePicker.getEditor().clear();
        manageResTimePicker.getEditor().clear();
        tblManageReservation.getItems().clear();

        lblManageMobileNumber.setText("");
        lblManageCusName.setText("");
        lblManageParticipants.setText("");

        manageResParticipants.setStyle("-fx-border-color: null");
        txtManageCustomerName.setStyle("-fx-border-color: null");
        txtManageMobileNumber.setStyle("-fx-border-color: null");

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void deleteReservationOnAction(ActionEvent event) {
        try{
            if(!txtManageMobileNumber.getText().isEmpty() && !txtManageCustomerName.getText().isEmpty() && !tblManageReservation.getItems().isEmpty()) {
                if (!lblManageMobileNumber.getText().startsWith("*Invalid") && !lblManageCusName.getText().startsWith("*Invalid")) {
                    ReservationTM tm = tblManageReservation.getSelectionModel().getSelectedItem();
                    if(tm!=null){
                    boolean reservationIsDeleted = new ReservationCrudController().deleteReservation(tblManageReservation.getSelectionModel().getSelectedItem().getReservationId());
                    if (reservationIsDeleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Reservation deleted Successfully..!", ButtonType.OK).show();
                        manageResDatePicker.getEditor().clear();
                        manageResTimePicker.getEditor().clear();
                        manageResParticipants.clear();
                        tblManageReservation.setItems(new ReservationCrudController().getReservationDetailBasedOnCustomer(manageCustomerId));
                        tblManageReservation.refresh();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Can't delete reservation, Please check why ?", ButtonType.OK).show();
                    }
                }else{
                        new Alert(Alert.AlertType.ERROR, "Select the reservation which is to be deleted..!", ButtonType.CLOSE).show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid fields try again later..!", ButtonType.CLOSE).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields try again later..!", ButtonType.CLOSE).show();
            }
            btnDelete.setDisable(true);

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void updateReservationOnAction(ActionEvent event) {
        try{
            if(!txtManageMobileNumber.getText().isEmpty() && !txtManageCustomerName.getText().isEmpty() && !manageResParticipants.getText().isEmpty() && !manageResDatePicker.getEditor().getText().isEmpty() && !manageResTimePicker.getEditor().getText().isEmpty()) {
                if (!lblManageMobileNumber.getText().startsWith("*Invalid") && !lblManageCusName.getText().startsWith("*Invalid") && !lblManageParticipants.getText().startsWith("*Invalid") ) {
                    String date = manageResDatePicker.getValue().toString();
                    String time = manageResTimePicker.getValue().toString();
                    ReservationTM tm = tblManageReservation.getSelectionModel().getSelectedItem();
                    if(tm!=null){
                    boolean reservationIsUpdated = new ReservationCrudController().updateReservation(new Reservation(tblManageReservation.getSelectionModel().getSelectedItem().getReservationId(), date, time, Integer.valueOf(manageResParticipants.getText())));
                    if (reservationIsUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Reservation Updated Successfully..!", ButtonType.OK).show();
                        manageResDatePicker.getEditor().clear();
                        manageResTimePicker.getEditor().clear();
                        manageResParticipants.clear();
                        tblManageReservation.setItems(new ReservationCrudController().getReservationDetailBasedOnCustomer(manageCustomerId));
                        tblManageReservation.refresh();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Reservation didn't update,Try again..!", ButtonType.CLOSE).show();
                    }
                }else{
                        new Alert(Alert.AlertType.ERROR, "Select the reservation which is to be updated..!", ButtonType.CLOSE).show();
                    }

                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid fields try again..!", ButtonType.CLOSE).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
            btnUpdate.setDisable(true);
        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void checkMobileNumberOnAction(KeyEvent keyEvent) {
        String value = "^(0){1}[0-9]{9}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtAddResCusNumber.getText());
        if (matcher.matches()) {
            lblNumber.setText("");
            txtAddResCusNumber.setStyle("-fx-border-color: green");
            txtAddResCusId.setStyle("-fx-border-color: null");
            txtAddResCusName.setStyle("-fx-border-color: null");
            txtAddResCusAddress.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(false);
            /*btnAddReservation.setDisable(false);*/

            boolean isTrue=false;
            try{
                isTrue = new CustomerCrudController().customerIsExists(txtAddResCusNumber.getText());
            }catch (SQLException | ClassNotFoundException e){

            }
            if(isTrue){
                btnAddCustomer.setDisable(true);
            }



            if (lblCustomerId.getText().startsWith("*Invalid") || txtAddResCusId.getText().length()==0) {
                if(txtAddResCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtAddResCusId.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }
            if (lblCusName.getText().startsWith("*Invalid") || txtAddResCusName.getText().length()==0) {
                if(txtAddResCusName.getText().length()==0){
                    lblCusName.setText("*This field is required");
                }
                txtAddResCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusName.setText(" ");
            }
            if (lblCusAddress.getText().startsWith("*Invalid")|| txtAddResCusAddress.getText().length()==0) {
                if(txtAddResCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtAddResCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }
        } else {
            lblNumber.setText("*Invalid Mobile Number");
            if(txtAddResCusNumber.getText().isEmpty()){
                lblNumber.setText("*This field is required");
            }
            txtAddResCusNumber.setStyle("-fx-border-color: red");
            txtAddResCusId.setStyle("-fx-border-color: null");
            txtAddResCusName.setStyle("-fx-border-color: null");
            txtAddResCusAddress.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(true);
            btnAddReservation.setDisable(true);

            if (lblCustomerId.getText().startsWith("*Invalid") || txtAddResCusId.getText().length()==0) {
                if(txtAddResCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtAddResCusId.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }
            if (lblCusName.getText().startsWith("*Invalid") || txtAddResCusName.getText().length()==0) {
                if(txtAddResCusName.getText().length()==0){
                    lblCusName.setText("*This field is required");
                }
                txtAddResCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusName.setText(" ");
            }
            if (lblCusAddress.getText().startsWith("*Invalid")|| txtAddResCusAddress.getText().length()==0) {
                if(txtAddResCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtAddResCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }
        }
    }

    public void checkCustomerName(KeyEvent keyEvent) {
        String value = "^[A-z ]{3,40}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtAddResCusName.getText());
        if (matcher.matches()) {
            lblCusName.setText("");
            txtAddResCusName.setStyle("-fx-border-color: green");
            txtAddResCusId.setStyle("-fx-border-color: null");
            txtAddResCusNumber.setStyle("-fx-border-color: null");
            txtAddResCusAddress.setStyle("-fx-border-color: null");


            btnAddCustomer.setDisable(false);
            /*btnAddReservation.setDisable(false);*/

            if (lblCustomerId.getText().startsWith("*Invalid") || txtAddResCusId.getText().length()==0) {
                if(txtAddResCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtAddResCusId.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }
            if (lblNumber.getText().startsWith("*Invalid") || txtAddResCusNumber.getText().length()==0) {
                if(txtAddResCusAddress.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtAddResCusNumber.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }
            if (lblCusAddress.getText().startsWith("*Invalid")|| txtAddResCusAddress.getText().length()==0) {
                if(txtAddResCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtAddResCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }
        } else {
            lblCusName.setText("*Invalid Customer Name");
            if(txtAddResCusName.getText().isEmpty()){
                lblCusName.setText("*This field is required");
            }
            txtAddResCusName.setStyle("-fx-border-color: red");
            txtAddResCusId.setStyle("-fx-border-color: null");
            txtAddResCusNumber.setStyle("-fx-border-color: null");
            txtAddResCusAddress.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(true);
            btnAddReservation.setDisable(true);

            if (lblCustomerId.getText().startsWith("*Invalid") || txtAddResCusId.getText().length()==0) {
                if(txtAddResCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtAddResCusId.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }
            if (lblNumber.getText().startsWith("*Invalid") || txtAddResCusNumber.getText().length()==0) {
                if(txtAddResCusAddress.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtAddResCusNumber.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }
            if (lblCusAddress.getText().startsWith("*Invalid")|| txtAddResCusAddress.getText().length()==0) {
                if(txtAddResCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtAddResCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }
        }
    }

    public void checkCustomerId(KeyEvent keyEvent) {
        String value = "^([C]{1}([0-9]{3,4}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtAddResCusId.getText());
        if (matcher.matches()) {
            lblCustomerId.setText("");
            txtAddResCusId.setStyle("-fx-border-color: green");
            txtAddResCusName.setStyle("-fx-border-color: null");
            txtAddResCusAddress.setStyle("-fx-border-color: null");
            txtAddResCusNumber.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(false);
            /*btnAddReservation.setDisable(false);*/


            if (lblCusName.getText().startsWith("*Invalid") || txtAddResCusName.getText().length()==0) {
                if(txtAddResCusName.getText().length()==0){
                    lblCusName.setText("*This field is required");
                }
                txtAddResCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusName.setText(" ");
            }

            if (lblCusAddress.getText().startsWith("*Invalid") || txtAddResCusAddress.getText().length()==0) {
                if(txtAddResCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtAddResCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }

            if (lblNumber.getText().startsWith("*Invalid")|| txtAddResCusNumber.getText().length()==0) {
                if(txtAddResCusNumber.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtAddResCusNumber.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }

        } else {
            lblCustomerId.setText("*Invalid Customer ID");
            if(txtAddResCusId.getText().isEmpty()){
                lblCustomerId.setText("*This field is required");
            }
            txtAddResCusId.setStyle("-fx-border-color: red");
            txtAddResCusName.setStyle("-fx-border-color: null");
            txtAddResCusAddress.setStyle("-fx-border-color: null");
            txtAddResCusNumber.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(true);
            btnAddReservation.setDisable(true);

            if (lblCusName.getText().startsWith("*Invalid") || txtAddResCusName.getText().length()==0) {
                if(txtAddResCusName.getText().length()==0){
                    lblCusName.setText("*This field is required");
                }
                txtAddResCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusName.setText(" ");
            }

            if (lblCusAddress.getText().startsWith("*Invalid") || txtAddResCusAddress.getText().length()==0) {
                if(txtAddResCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtAddResCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }

            if (lblNumber.getText().startsWith("*Invalid")|| txtAddResCusNumber.getText().length()==0) {
                if(txtAddResCusNumber.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtAddResCusNumber.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }
        }
    }

    public void checkCustomerAddress(KeyEvent keyEvent) {
        String value = "^[A-z0-9 ,/]{4,50}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtAddResCusAddress.getText());
        if (matcher.matches()) {
            lblCusAddress.setText("");
            txtAddResCusAddress.setStyle("-fx-border-color: green");
            txtAddResCusId.setStyle("-fx-border-color: null");
            txtAddResCusName.setStyle("-fx-border-color: null");
            txtAddResCusNumber.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(false);
            /*btnAddReservation.setDisable(false);*/


            if (lblCustomerId.getText().startsWith("*Invalid") || txtAddResCusId.getText().length()==0) {
                if(txtAddResCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtAddResCusId.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }

            if (lblCusName.getText().startsWith("*Invalid") || txtAddResCusName.getText().length()==0) {
                if(txtAddResCusName.getText().length()==0){
                    lblCusName.setText("*This field is required");
                }
                txtAddResCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusName.setText(" ");
            }

            if (lblNumber.getText().startsWith("*Invalid")|| txtAddResCusNumber.getText().length()==0) {
                if(txtAddResCusNumber.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtAddResCusNumber.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }

        } else {
            lblCusAddress.setText("*Invalid Customer Address");
            if(txtAddResCusAddress.getText().isEmpty()){
                lblCusAddress.setText("*This field is required");
            }
            txtAddResCusAddress.setStyle("-fx-border-color: red");
            txtAddResCusId.setStyle("-fx-border-color: null");
            txtAddResCusName.setStyle("-fx-border-color: null");
            txtAddResCusNumber.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(true);
            btnAddReservation.setDisable(true);

            if (lblCustomerId.getText().startsWith("*Invalid") || txtAddResCusId.getText().length()==0) {
                if(txtAddResCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtAddResCusId.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }

            if (lblCusName.getText().startsWith("*Invalid") || txtAddResCusName.getText().length()==0) {
                if(txtAddResCusName.getText().length()==0){
                    lblCusName.setText("*This field is required");
                }
                txtAddResCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblCusName.setText(" ");
            }

            if (lblNumber.getText().startsWith("*Invalid")|| txtAddResCusNumber.getText().length()==0) {
                if(txtAddResCusNumber.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtAddResCusNumber.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
                btnAddReservation.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }
        }
    }

    public void checkOnAction(KeyEvent keyEvent) {
        String value = "^[1-9][0-9]{0,1}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(addResParticipants.getText());
        if (matcher.matches()) {
            if(!txtAddResCusId.getText().isEmpty() && !txtAddResCusName.getText().isEmpty() && !txtAddResCusNumber.getText().isEmpty() && !txtAddResCusAddress.getText().isEmpty()) {
                if (!lblCustomerId.getText().startsWith("*Invalid") && !lblCusName.getText().startsWith("*Invalid") && !lblNumber.getText().startsWith("*Invalid") && !lblCusAddress.getText().startsWith("*Invalid")) {
                    if(btnAddCustomer.isDisabled() && !addResDatePicker.getEditor().getText().isEmpty() && !addResTimePicker.getEditor().getText().isEmpty()){
                        btnAddReservation.setDisable(false);
                    }
                }
            }
            lblParticipants.setText("");
            addResParticipants.setStyle("-fx-border-color: null");
        }else{
            lblParticipants.setText("*Invalid Value");
            btnAddReservation.setDisable(true);
            addResParticipants.setStyle("-fx-border-width: 2");
            addResParticipants.setStyle("-fx-border-color: RED");
        }
    }

    public void manageCusNameCheckOnAction(KeyEvent keyEvent) {
        /*String value = "^[A-z ]{3,40}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtManageCustomerName.getText());
        if (matcher.matches()) {
            lblManageCusName.setText("");
            txtManageCustomerName.setStyle("-fx-border-color: green");
            txtManageMobileNumber.setStyle("-fx-border-color: null");

            if (lblManageMobileNumber.getText().startsWith("*Invalid") || txtManageMobileNumber.getText().length()==0) {
                txtManageMobileNumber.setStyle("-fx-border-color: red");
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
            }else{
                btnUpdate.setDisable(true);
                btnDelete.setDisable(false);
            }
        } else {
            lblManageCusName.setText("*Invalid Mobile Number");
            txtManageCustomerName.setStyle("-fx-border-color: red");
            txtManageMobileNumber.setStyle("-fx-border-color: null");

            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            if (lblManageMobileNumber.getText().startsWith("*Invalid") || txtManageMobileNumber.getText().length()==0) {
                txtManageMobileNumber.setStyle("-fx-border-color: red");
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
            }
        }*/
    }

    public void manageMobileCheckOnAction(KeyEvent keyEvent) {
        String value = "^(0){1}[0-9]{9}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtManageMobileNumber.getText());
        if (matcher.matches()) {
            lblManageMobileNumber.setText("");
            txtManageMobileNumber.setStyle("-fx-border-color: green");

            /*btnUpdate.setDisable(false);
            btnDelete.setDisable(false);*/

         } else {
            lblManageMobileNumber.setText("*Invalid Mobile Number");
            txtManageMobileNumber.setStyle("-fx-border-color: red");

            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    String manageCustomerId = null;

    public void txtManageSearchMobileOnAction(ActionEvent event) {
        //
        try {
            if (!txtManageMobileNumber.getText().isEmpty()) {
                if (!lblManageMobileNumber.getText().startsWith("*Invalid")) {
                    Customer c1 = new CustomerCrudController().getCustomerWithMobileNumberIsExist(txtManageMobileNumber.getText());
                    if (c1 != null) {
                        txtManageMobileNumber.setText(c1.getContactNumber());
                        txtManageCustomerName.setText(c1.getName());
                        manageCustomerId = c1.getCustomerId();
                        tblManageReservation.setItems(new ReservationCrudController().getReservationDetailBasedOnCustomer(manageCustomerId));

                        txtManageMobileNumber.setStyle("-fx-border-color: null");
                        txtManageCustomerName.setStyle("-fx-border-color: null");

                        lblManageMobileNumber.setText("");
                        lblManageCusName.setText("");

                        manageResDatePicker.getEditor().clear();
                        manageResTimePicker.getEditor().clear();
                        manageResParticipants.clear();

                        btnUpdate.setDisable(true);
                        btnDelete.setDisable(true);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "No customer exists for this Mobile Number Or This customer doesn't have a reservation yet..!",ButtonType.OK).show();
                        btnUpdate.setDisable(true);
                        btnDelete.setDisable(true);
                        txtManageCustomerName.clear();
                        txtManageMobileNumber.clear();
                        tblManageReservation.getItems().clear();
                        manageResDatePicker.getEditor().clear();
                        manageResTimePicker.getEditor().clear();
                        manageResParticipants.clear();
                    }
                } else {
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                    txtManageCustomerName.clear();
                    txtManageMobileNumber.clear();
                    tblManageReservation.getItems().clear();
                    new Alert(Alert.AlertType.ERROR, "Invalid Mobile Number..!").show();
                    manageResDatePicker.getEditor().clear();
                    manageResTimePicker.getEditor().clear();
                    manageResParticipants.clear();
                }
            } else {
                btnUpdate.setDisable(true);
                btnDelete.setDisable(true);
                txtManageCustomerName.clear();
                txtManageMobileNumber.clear();
                tblManageReservation.getItems().clear();
                new Alert(Alert.AlertType.ERROR, "Empty field try again..!").show();
                manageResDatePicker.getEditor().clear();
                manageResTimePicker.getEditor().clear();
                manageResParticipants.clear();
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void manageCheckOnAction(KeyEvent keyEvent) {
        String value = "^[1-9][0-9]{0,1}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(manageResParticipants.getText());
        if (matcher.matches()) {
            if(!txtManageCustomerName.getText().isEmpty() && !txtManageMobileNumber.getText().isEmpty() && !tblManageReservation.getItems().isEmpty()) {
                if (!lblManageMobileNumber.getText().startsWith("*Invalid") && !lblManageCusName.getText().startsWith("*Invalid")){
                    if(!manageResDatePicker.getEditor().getText().isEmpty() && !manageResTimePicker.getEditor().getText().isEmpty() && !manageResParticipants.getText().isEmpty() && !lblManageParticipants.getText().startsWith("*Invalid")){
                        btnUpdate.setDisable(false);
                    }
                }
            }
            lblManageParticipants.setText("");
            manageResParticipants.setStyle("-fx-border-color: null");
        }else{
            lblManageParticipants.setText("*Invalid Value");
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);
            manageResParticipants.setStyle("-fx-border-width: 2");
            manageResParticipants.setStyle("-fx-border-color: RED");
        }
    }

    public void addDatePickerOnAction(Event event) {
        if(!txtAddResCusId.getText().isEmpty() && !txtAddResCusName.getText().isEmpty() && !txtAddResCusNumber.getText().isEmpty() && !txtAddResCusAddress.getText().isEmpty()) {
            if (!lblCustomerId.getText().startsWith("*Invalid") && !lblCusName.getText().startsWith("*Invalid") && !lblNumber.getText().startsWith("*Invalid") && !lblCusAddress.getText().startsWith("*Invalid")) {
                if(btnAddCustomer.isDisabled() && !addResDatePicker.getEditor().getText().isEmpty() && !addResTimePicker.getEditor().getText().isEmpty() && !addResParticipants.getText().isEmpty() && !lblParticipants.getText().startsWith("*Invalid")){
                    btnAddReservation.setDisable(false);
                }
            }
        }else{
            btnAddReservation.setDisable(true);
        }
    }

    public void addTimePickerOnAction(Event event) {
        Pattern pattern = Pattern.compile("^[1-9][0-9]{0,1}(:)[0-9]{2}[ ](AM|PM)$");
        Matcher matcher = pattern.matcher(addResTimePicker.getEditor().getText());
        if(matcher.matches()){
            if(!txtAddResCusId.getText().isEmpty() && !txtAddResCusName.getText().isEmpty() && !txtAddResCusNumber.getText().isEmpty() && !txtAddResCusAddress.getText().isEmpty()) {
                if (!lblCustomerId.getText().startsWith("*Invalid") && !lblCusName.getText().startsWith("*Invalid") && !lblNumber.getText().startsWith("*Invalid") && !lblCusAddress.getText().startsWith("*Invalid")) {
                    if(btnAddCustomer.isDisabled() && !addResDatePicker.getEditor().getText().isEmpty() && !addResTimePicker.getEditor().getText().isEmpty() && !addResParticipants.getText().isEmpty() && !lblParticipants.getText().startsWith("*Invalid")){
                        btnAddReservation.setDisable(false);
                    }
                }
            }
        }else{
            btnAddReservation.setDisable(true);
        }
    }

    public void manageDatePickerOnAction(Event event) {
        if(!txtManageCustomerName.getText().isEmpty() && !txtManageMobileNumber.getText().isEmpty() && !tblManageReservation.getItems().isEmpty()) {
            if (!lblManageMobileNumber.getText().startsWith("*Invalid") && !lblManageCusName.getText().startsWith("*Invalid")){
                if(!manageResDatePicker.getEditor().getText().isEmpty() && !manageResTimePicker.getEditor().getText().isEmpty() && !manageResParticipants.getText().isEmpty() && !lblManageParticipants.getText().startsWith("*Invalid")){
                    btnUpdate.setDisable(false);
                }
            }
        }else{
            btnUpdate.setDisable(true);
        }
    }

    public void manageTimePickerOnAction(Event event) {
        Pattern pattern = Pattern.compile("^[1-9][0-9]{0,1}(:)[0-9]{2}[ ](AM|PM)$");
        Matcher matcher = pattern.matcher(manageResTimePicker.getEditor().getText());
        if(matcher.matches()){
            if(!txtManageCustomerName.getText().isEmpty() && !txtManageMobileNumber.getText().isEmpty() && !tblManageReservation.getItems().isEmpty()) {
                if (!lblManageMobileNumber.getText().startsWith("*Invalid") && !lblManageCusName.getText().startsWith("*Invalid")){
                    if(!manageResDatePicker.getEditor().getText().isEmpty() && !manageResTimePicker.getEditor().getText().isEmpty() && !manageResParticipants.getText().isEmpty() && !lblManageParticipants.getText().startsWith("*Invalid")){
                        btnUpdate.setDisable(false);
                    }
                }
            }
        }else{
            btnUpdate.setDisable(true);
        }
    }

    /*public void checkAllFieldsOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAddCustomer,btnAddReservation);
        if (response instanceof TextField) {
            TextField textField = (TextField) response;
            textField.requestFocus();
        } else if (response instanceof Boolean) {

        }

        boolean isTrue=false;
        try{
            isTrue = new CustomerCrudController().customerIsExists(txtAddResCusId.getText());
        }catch (SQLException | ClassNotFoundException e){

        }
        if(isTrue){
            btnAddCustomer.setDisable(true);
            btnAddReservation.setDisable(true);
        }
    }*/
}
