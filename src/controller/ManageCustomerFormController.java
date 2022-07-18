package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Customer;
import model.CustomerTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageCustomerFormController {

    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerContact;
    public JFXTextField txtCustomerAddress;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colCusId;
    public TableColumn colCusName;
    public TableColumn colCusContact;
    public TableColumn colCusAddress;
    public Label lblCustomerId;
    public Label lblCusName;
    public Label lblContactNumber;
    public Label lblCusAddress;
    public JFXButton btnUpdate;
    public JFXButton btnRemove;
    public Label lblFirstCustomer;
    public Label lblSecondCustomer;
    public Label lblThirdCustomer;

    ObservableList<CustomerTM> obList = FXCollections.observableArrayList();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public ArrayList<Label> lblArrayList = new ArrayList<>();

    public void initialize(){
        btnRemove.setDisable(true);
        btnUpdate.setDisable(true);
        colCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCusContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        lblArrayList.add(lblCustomerId);
        lblArrayList.add(lblCusName);
        lblArrayList.add(lblContactNumber);
        lblArrayList.add(lblCusAddress);

        loadAllCustomerData();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                setData(newValue.getCustomerId());
            }
        });

        Pattern cIdPattern = Pattern.compile("^([C]([0-9]{3,4}))$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,25}$");
        Pattern contactPattern = Pattern.compile("^(0){1}[0-9]{9}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,45}$");

        map.put(txtCustomerId,cIdPattern);
        map.put(txtCustomerName,namePattern);
        map.put(txtCustomerContact,contactPattern);
        map.put(txtCustomerAddress,addressPattern);

        try{
            ArrayList<Customer> list = new CustomerCrudController().getTopThreeCustomers();
            String firstName = list.get(0).getName();
            String firstId = list.get(0).getCustomerId();
            lblFirstCustomer.setText(firstName+" ("+firstId+")");

            String secondName = list.get(1).getName();
            String secondId = list.get(1).getCustomerId();
            lblSecondCustomer.setText(secondName+" ("+secondId+")");

            String thirdName = list.get(2).getName();
            String thirdId = list.get(2).getCustomerId();
            lblThirdCustomer.setText(thirdName+" ("+thirdId+")");

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    private void setData(String selectedCustomerId){
        try{
            Customer c1 = new CustomerCrudController().getCustomer(selectedCustomerId);
            if (c1 != null) {
                txtCustomerId.setText(c1.getCustomerId());
                txtCustomerName.setText(c1.getName());
                txtCustomerAddress.setText(c1.getAddress());
                txtCustomerContact.setText(c1.getContactNumber());
                btnUpdate.setDisable(false);
                btnRemove.setDisable(false);
            } else {
                clearAll();
                new Alert(Alert.AlertType.ERROR, "No customer exists for this customerId..!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    private void loadAllCustomerData(){
        try{
            ArrayList<Customer> arrayList=new CustomerCrudController().getAllCustomers();
            obList.clear();
            for(Customer c1 : arrayList){
                obList.add(new CustomerTM(
                        c1.getCustomerId(),
                        c1.getName(),
                        c1.getAddress(),
                        c1.getContactNumber()
                ));
            }
            tblCustomer.setItems(obList);

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void clearAll() {
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerContact.clear();

        txtCustomerId.setStyle("-fx-border-color: null");
        txtCustomerName.setStyle("-fx-border-color: null");
        txtCustomerAddress.setStyle("-fx-border-color: null");
        txtCustomerContact.setStyle("-fx-border-color: null");
        lblCustomerId.setText("");
        lblCusName.setText("");
        lblCusAddress.setText("");
        lblContactNumber.setText("");

        btnUpdate.setDisable(true);
        btnRemove.setDisable(true);
    }

    public void txtSearchCustomerOnAction(ActionEvent event) {
        try {
            if (!txtCustomerId.getText().isEmpty()) {
                if (!lblCustomerId.getText().startsWith("Invalid")) {
                    Customer c1 = new CustomerCrudController().getCustomer(txtCustomerId.getText());
                    if (c1 != null) {
                        txtCustomerId.setText(c1.getCustomerId());
                        txtCustomerName.setText(c1.getName());
                        txtCustomerAddress.setText(c1.getAddress());
                        txtCustomerContact.setText(c1.getContactNumber());
                    } else {
                        clearAll();
                        new Alert(Alert.AlertType.ERROR, "No customer exists for this customerId..!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid CustomerId..!").show();
                    clearAll();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Empty field try again..!").show();
                clearAll();
            }
            loadAllCustomerData();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void SearchCustomerOnAction(ActionEvent event) {
        try {
            if (!txtCustomerId.getText().isEmpty()) {
                if (!lblCustomerId.getText().startsWith("Invalid")) {
                    Customer c1 = new CustomerCrudController().getCustomer(txtCustomerId.getText());
                    if (c1 != null) {
                        txtCustomerId.setText(c1.getCustomerId());
                        txtCustomerName.setText(c1.getName());
                        txtCustomerAddress.setText(c1.getAddress());
                        txtCustomerContact.setText(c1.getContactNumber());
                        btnRemove.setDisable(false);
                        btnUpdate.setDisable(false);
                    } else {
                        clearAll();
                        new Alert(Alert.AlertType.ERROR, "No customer exists for this customerId..!").show();
                    }
                } else {
                    clearAll();
                    new Alert(Alert.AlertType.ERROR, "Invalid CustomerId..!").show();
                }
            } else {
                clearAll();
                new Alert(Alert.AlertType.ERROR, "Empty field try again..!").show();
            }
            loadAllCustomerData();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCustomerOnAction(ActionEvent event) {
        try{
            if(!txtCustomerId.getText().isEmpty() && !txtCustomerName.getText().isEmpty() && !txtCustomerContact.getText().isEmpty() && !txtCustomerAddress.getText().isEmpty()){
                if(!lblCustomerId.getText().startsWith("Invalid") && !lblCusName.getText().startsWith("Invalid") && !lblContactNumber.getText().startsWith("Invalid") && !lblCusAddress.getText().startsWith("Invalid")){
                    boolean customerIsUpdated=new CustomerCrudController().updateCustomer(new Customer(txtCustomerId.getText(),txtCustomerName.getText(),txtCustomerAddress.getText(),txtCustomerContact.getText()));
                    if(customerIsUpdated){
                        new Alert(Alert.AlertType.CONFIRMATION,"Customer updated successfully..!").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"Try again..!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR,"Invalid data please,enter valid data..!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Empty fields, check fields again..!").show();
            }
            loadAllCustomerData();
            /*tblCustomer.refresh();*/

        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void removeCustomerOnAction(ActionEvent event) {
        try{
            if(!txtCustomerId.getText().isEmpty() && !txtCustomerName.getText().isEmpty() && !txtCustomerContact.getText().isEmpty() && !txtCustomerAddress.getText().isEmpty()){
                if(!lblCustomerId.getText().startsWith("Invalid") && !lblCusName.getText().startsWith("Invalid") && !lblContactNumber.getText().startsWith("Invalid") && !lblCusAddress.getText().startsWith("Invalid")){
                    boolean customerIsDeleted=new CustomerCrudController().deleteCustomer(txtCustomerId.getText());
                    if(customerIsDeleted){
                        new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted successfully..!").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR,"Try again..!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR,"Invalid data please,enter valid data..!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Empty fields, check fields again..!").show();
            }
            loadAllCustomerData();

        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public void checkCustomerName(KeyEvent keyEvent) {
        String value = "^[A-z ]{3,40}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtCustomerName.getText());
        if (matcher.matches()) {
            txtCustomerName.setStyle("-fx-border-color: null");
            lblCusName.setText("");

            if(!txtCustomerId.getText().isEmpty() && !txtCustomerName.getText().isEmpty() && !txtCustomerAddress.getText().isEmpty() && !txtCustomerContact.getText().isEmpty()){
                if (txtCustomerId.getStyle()!="-fx-border-color: red" && txtCustomerContact.getStyle()!="-fx-border-color: red" && txtCustomerAddress.getStyle()!="-fx-border-color: red" && txtCustomerName.getStyle()!="-fx-border-color: red") {
                    btnUpdate.setDisable(false);
                    btnRemove.setDisable(false);
                }
            }


        } else {
            txtCustomerName.setStyle("-fx-border-width: 6");
            txtCustomerName.setStyle("-fx-border-color: red");
            lblCusName.setText("Invalid Customer Name");
            btnRemove.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }

    public void checkCustomerContactNumber(KeyEvent keyEvent) {  //^[0][7][1,2,4,5,6,7,8]{1}[0-9]{7}$
        String value = "^(0){1}[0-9]{9}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtCustomerContact.getText());
        if (matcher.matches()) {
            txtCustomerContact.setStyle("-fx-border-color: null");
            lblContactNumber.setText("");

            if(!txtCustomerId.getText().isEmpty() && !txtCustomerName.getText().isEmpty() && !txtCustomerAddress.getText().isEmpty() && !txtCustomerContact.getText().isEmpty()){
                if (txtCustomerId.getStyle()!="-fx-border-color: red" && txtCustomerContact.getStyle()!="-fx-border-color: red" && txtCustomerAddress.getStyle()!="-fx-border-color: red" && txtCustomerName.getStyle()!="-fx-border-color: red") {
                    btnUpdate.setDisable(false);
                    btnRemove.setDisable(false);
                }
            }


        } else {
            txtCustomerContact.setStyle("-fx-border-width: 6");
            txtCustomerContact.setStyle("-fx-border-color: red");
            lblContactNumber.setText("Invalid Mobile Number");
            btnRemove.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }

    public void checkCustomerId(KeyEvent keyEvent) {
        String value = "^([C]{1}([0-9]{3,4}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtCustomerId.getText());
        if (matcher.matches()) {
            txtCustomerId.setStyle("-fx-border-color: null");
            lblCustomerId.setText("");

            if(!txtCustomerId.getText().isEmpty() && !txtCustomerName.getText().isEmpty() && !txtCustomerAddress.getText().isEmpty() && !txtCustomerContact.getText().isEmpty()){
                if (txtCustomerId.getStyle()!="-fx-border-color: red" && txtCustomerContact.getStyle()!="-fx-border-color: red" && txtCustomerAddress.getStyle()!="-fx-border-color: red" && txtCustomerName.getStyle()!="-fx-border-color: red") {
                    btnUpdate.setDisable(false);
                    btnRemove.setDisable(false);
                }
            }


        } else {
            txtCustomerId.setStyle("-fx-border-width: 6");
            txtCustomerId.setStyle("-fx-border-color: red");
            lblCustomerId.setText("Invalid Customer Id");
            btnRemove.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }

    public void checkAddress(KeyEvent keyEvent) {
        String value = "^[A-z0-9 ,/]{4,50}$";   //^([A-Z a-z 0-9 , / -]{3,40})$
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtCustomerAddress.getText());
        if (matcher.matches()) {
            txtCustomerAddress.setStyle("-fx-border-color: null");
            lblCusAddress.setText("");

            if(!txtCustomerId.getText().isEmpty() && !txtCustomerName.getText().isEmpty() && !txtCustomerAddress.getText().isEmpty() && !txtCustomerContact.getText().isEmpty()){
                if (txtCustomerId.getStyle()!="-fx-border-color: red" && txtCustomerContact.getStyle()!="-fx-border-color: red" && txtCustomerAddress.getStyle()!="-fx-border-color: red" && txtCustomerName.getStyle()!="-fx-border-color: red") {
                    btnUpdate.setDisable(false);
                    btnRemove.setDisable(false);
                }
            }

        } else {
            txtCustomerAddress.setStyle("-fx-border-width: 6");
            txtCustomerAddress.setStyle("-fx-border-color: red");
            lblCusAddress.setText("Invalid address");
            btnRemove.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }

    public void resetOnAction(ActionEvent event) {
        txtCustomerId.clear();
        clearAll();
    }

    /*public void allPressedOnAction(MouseEvent keyEvent) {
        if(!txtCustomerId.getText().isEmpty() && !txtCustomerName.getText().isEmpty() && !txtCustomerAddress.getText().isEmpty() && !txtCustomerContact.getText().isEmpty()){
            if (txtCustomerId.getStyle()!="-fx-border-color: red" && txtCustomerContact.getStyle()!="-fx-border-color: red" && txtCustomerAddress.getStyle()!="-fx-border-color: red" && txtCustomerName.getStyle()!="-fx-border-color: red") {
                btnUpdate.setDisable(false);
                btnRemove.setDisable(false);
            }
        }
    }*/

    /*public void allTextFieldsMouseOnAction(KeyEvent mouseEvent) {
        if(!txtCustomerId.getText().isEmpty() && !txtCustomerName.getText().isEmpty() && !txtCustomerAddress.getText().isEmpty() && !txtCustomerContact.getText().isEmpty()){
            if (txtCustomerId.getStyle()!="-fx-border-color: red" && txtCustomerContact.getStyle()!="-fx-border-color: red" && txtCustomerAddress.getStyle()!="-fx-border-color: red" && txtCustomerName.getStyle()!="-fx-border-color: red") {
                btnUpdate.setDisable(false);
                btnRemove.setDisable(false);
            }
        }
    }*/

    /*public void checkAllFieldsOnAction(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnUpdate,btnRemove);
    }*/
}
