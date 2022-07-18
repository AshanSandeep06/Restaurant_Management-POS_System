package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Employee;
import model.EmployeeTM;
import validation.Validation;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeFormController {

    public TextField txtEmpId;
    public TextField txtEmpName;
    public TextField txtEmpNIC;
    public TextField txtEmpAddress;
    public ComboBox<String> cmbJobRole;
    public TextField txtBikeNumber;
    public TextField txtLisceneNo;
    public TableView<EmployeeTM> tblEmployeeDetails;
    public TableColumn colEmpId;
    public TableColumn colEmpName;
    public TableColumn colEmpNIC;
    public TableColumn colEmpAddress;
    public TableColumn colEmpContact;
    public TableColumn colEmpWoHours;
    public TextField txtEmpContactNumber;
    public TextField txtEmpWorkHours;
    public TextField txtEmpUserName;
    public TextField txtEmpPassword;
    public TableColumn colEmpPost;
    public Label lblEmployee;
    public Label lblEmployeeName;
    public Label lblNIC;
    public Label lblEmployeeAddress;
    public Label lblContactNumber;
    public Label lblWorkingHours;
    public Label lblUserName;
    public Label lblPassword;
    public Label lblBikeNumber;
    public Label lblLisceneNumber;
    public Label lblComboBoxJobRole;
    public JFXButton btnAddEmployee;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    ObservableList<EmployeeTM> tblObList = FXCollections.observableArrayList();

    Pattern idPattern = Pattern.compile("^([E]{1}[-]([0-9]{3,4}))$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,25}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern NICPattern = Pattern.compile("^([0-9]{9}.([V]{1}))$");
    Pattern contactPattern = Pattern.compile("^(0){1}[0-9]{9}$");
    Pattern workingHours = Pattern.compile("^[1-9][0-9]{0,1}(.30)?$");
    Pattern userNamePattern = Pattern.compile("^[A-Za-z0-9@_]{5,10}$");
    Pattern PasswordPattern = Pattern.compile("^[A-Za-z0-9]{5,20}$");
    Pattern bikeNumberPattern = Pattern.compile("^([A-Z0-9]{2,3}[-]([0-9]{4}))$");
    Pattern liceneNumberPattern = Pattern.compile("^([B]{1}.([0-9]{6}))$");

    public void lastEmpId() {
        try {
            String empId = EmployeeController.getLastEmployeeId();
            String finalId = "E-001";

            if (empId != null) {

                String[] splitString = empId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "E-00" + id;
                } else if (id < 100) {
                    finalId = "E-0" + id;
                } else {
                    finalId = "E-" + id;
                }
                txtEmpId.setText(finalId);
            } else {
                txtEmpId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        cmbJobRole.getItems().addAll("Cashier", "Rider", "Chef", "Waiter", "Kitchen Helper");
        cmbJobRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("Rider")) {
                    txtBikeNumber.setDisable(false);
                    txtLisceneNo.setDisable(false);
                    txtEmpUserName.setDisable(true);
                    txtEmpPassword.setDisable(true);
                } else if(newValue.equals("Cashier")) {
                    txtBikeNumber.setDisable(true);
                    txtLisceneNo.setDisable(true);
                    txtEmpUserName.setDisable(false);
                    txtEmpPassword.setDisable(false);
                }else{
                    txtBikeNumber.setDisable(true);
                    txtLisceneNo.setDisable(true);
                    txtEmpUserName.setDisable(true);
                    txtEmpPassword.setDisable(true);
                }
            }
        });

        map.put(txtEmpId, idPattern);
        map.put(txtEmpName, namePattern);
        map.put(txtEmpNIC, NICPattern);
        map.put(txtEmpAddress, addressPattern);
        map.put(txtEmpContactNumber, contactPattern);
        map.put(txtEmpWorkHours, workingHours);
        map.put(txtEmpUserName, userNamePattern);
        map.put(txtEmpPassword, PasswordPattern);
        map.put(txtBikeNumber, bikeNumberPattern);
        map.put(txtLisceneNo, liceneNumberPattern);

        txtEmpId.setDisable(true);
        lastEmpId();

        colEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmpNIC.setCellValueFactory(new PropertyValueFactory<>("employeeNIC"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        colEmpContact.setCellValueFactory(new PropertyValueFactory<>("employeeContactNumber"));
        colEmpWoHours.setCellValueFactory(new PropertyValueFactory<>("workingHours"));
        colEmpPost.setCellValueFactory(new PropertyValueFactory<>("jobRole"));

        loadTableData();

        tblEmployeeDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setData(newValue);
            }
        });
    }

    private void setData(EmployeeTM tm) {
        try {
            if (tm.getJobRole().equals("Cashier")) {
                Employee e = new EmployeeController().getCashiers(tm);
                if (e != null) {
                    txtEmpId.setText(e.getEmployeeID());
                    txtEmpName.setText(e.getEmployeeName());
                    txtEmpAddress.setText(e.getEmployeeAddress());
                    txtEmpNIC.setText(e.getEmployeeNIC());
                    txtEmpContactNumber.setText(e.getEmployeeContactNumber());
                    cmbJobRole.setValue(e.getJobRole());
                    txtEmpWorkHours.setText(String.valueOf(e.getWorkingHours()));
                    txtEmpUserName.setText(e.getUserName());
                    txtEmpPassword.setText(e.getPassword());
                    txtBikeNumber.clear();
                    txtLisceneNo.clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
                }
            } else if (tm.getJobRole().equals("Rider")) {
                Employee e = new EmployeeController().getRiders(tm);
                if (e != null) {
                    txtEmpId.setText(e.getEmployeeID());
                    txtEmpName.setText(e.getEmployeeName());
                    txtEmpAddress.setText(e.getEmployeeAddress());
                    txtEmpNIC.setText(e.getEmployeeNIC());
                    txtEmpContactNumber.setText(e.getEmployeeContactNumber());
                    cmbJobRole.setValue(e.getJobRole());
                    txtEmpWorkHours.setText(String.valueOf(e.getWorkingHours()));
                    txtBikeNumber.setText(e.getBikeNo());
                    txtLisceneNo.setText(e.getDrivingLicenseNumber());
                    txtEmpUserName.clear();
                    txtEmpPassword.clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
                }
            } else {
                Employee e = new EmployeeController().getOthers(tm);
                if (e != null) {
                    txtEmpId.setText(e.getEmployeeID());
                    txtEmpName.setText(e.getEmployeeName());
                    txtEmpAddress.setText(e.getEmployeeAddress());
                    txtEmpNIC.setText(e.getEmployeeNIC());
                    txtEmpContactNumber.setText(e.getEmployeeContactNumber());
                    cmbJobRole.setValue(e.getJobRole());
                    txtEmpWorkHours.setText(String.valueOf(e.getWorkingHours()));
                    txtBikeNumber.clear();
                    txtLisceneNo.clear();
                    txtEmpUserName.clear();
                    txtEmpPassword.clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {

        }
    }

    /*private Object validate() {
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    //textFieldKey.getParent().setStyle("-fx-border-color: red");
                }
                return textFieldKey;
            }
            //textFieldKey.getParent().setStyle("-fx-border-color: green");
        }
        return true;
    }*/

    private Object validate() {
        for (TextField key : map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()){
                //if the input is not matching
                return key;
            }
        }
        return true;
    }

    public void clearAll() {
        txtEmpId.clear();
        txtEmpName.clear();
        txtEmpNIC.clear();
        txtEmpAddress.clear();
        txtEmpContactNumber.clear();
        txtEmpWorkHours.clear();
        txtEmpUserName.clear();
        txtEmpPassword.clear();
        cmbJobRole.getSelectionModel().clearSelection();
        txtBikeNumber.clear();
        txtLisceneNo.clear();

        setBorders(txtEmpId,txtEmpName,txtEmpNIC,txtEmpAddress,txtEmpContactNumber,txtEmpWorkHours,txtEmpUserName,txtEmpPassword);
        txtBikeNumber.setStyle("-fx-border-color: null");
        txtLisceneNo.setStyle("-fx-border-color: null");
        lblEmployee.setText("");
        lblEmployeeName.setText("");
        lblNIC.setText("");
        lblEmployeeAddress.setText("");
        lblContactNumber.setText("");
        lblWorkingHours.setText("");
        lblUserName.setText("");
        lblPassword.setText("");
        lblBikeNumber.setText("");
        lblLisceneNumber.setText("");
    }

    public void loadTableData() {
        try {
            ArrayList<EmployeeTM> list = new EmployeeController().loadAllEmployees();
            tblObList.clear();
            for (EmployeeTM tm : list) {
                tblObList.add(new EmployeeTM(
                        tm.getEmployeeID(),
                        tm.getEmployeeName(),
                        tm.getEmployeeAddress(),
                        tm.getEmployeeNIC(),
                        tm.getEmployeeContactNumber(),
                        tm.getJobRole(),
                        tm.getWorkingHours()
                ));
            }

            tblEmployeeDetails.setItems(tblObList);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void resetOnAction(ActionEvent event) {
        clearAll();
        lastEmpId();
    }

    public void addEmployeeOnAction(ActionEvent event) {
        try {
            if (cmbJobRole.getSelectionModel().getSelectedItem() == ("Cashier")) {
                //
                if (!txtEmpId.getText().isEmpty() && !txtEmpName.getText().isEmpty() && !txtEmpNIC.getText().isEmpty() && !txtEmpAddress.getText().isEmpty() && !txtEmpContactNumber.getText().isEmpty() && !txtEmpWorkHours.getText().isEmpty() && !txtEmpUserName.getText().isEmpty() && !txtEmpPassword.getText().isEmpty() && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                    if (!lblEmployee.getText().startsWith("Invalid") && !lblEmployeeName.getText().startsWith("Invalid") && !lblNIC.getText().startsWith("Invalid") && !lblEmployeeAddress.getText().startsWith("Invalid") && !lblContactNumber.getText().startsWith("Invalid") && !lblWorkingHours.getText().startsWith("Invalid") && !lblUserName.getText().startsWith("Invalid") && !lblPassword.getText().startsWith("Invalid") && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                        boolean employeeIsSaved = new EmployeeController().addEmployee(new Employee(txtEmpId.getText(), txtEmpNIC.getText(), txtEmpName.getText(), txtEmpAddress.getText(), txtEmpContactNumber.getText(), cmbJobRole.getValue(), Integer.parseInt(txtEmpWorkHours.getText()), txtEmpUserName.getText(), txtEmpPassword.getText()));
                        if (employeeIsSaved) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully..!").show();
                            clearAll();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Failed try again..!").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Please input valid data..!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Empty fields..!").show();
                }
            } else if (cmbJobRole.getSelectionModel().getSelectedItem() == ("Rider")) {
                if (!txtEmpId.getText().isEmpty() && !txtEmpName.getText().isEmpty() && !txtEmpNIC.getText().isEmpty() && !txtEmpAddress.getText().isEmpty() && !txtEmpContactNumber.getText().isEmpty() && !txtEmpWorkHours.getText().isEmpty() && !txtBikeNumber.getText().isEmpty() && !txtLisceneNo.getText().isEmpty() && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                    if (!lblEmployee.getText().startsWith("Invalid") && !lblEmployeeName.getText().startsWith("Invalid") && !lblNIC.getText().startsWith("Invalid") && !lblEmployeeAddress.getText().startsWith("Invalid") && !lblContactNumber.getText().startsWith("Invalid") && !lblWorkingHours.getText().startsWith("Invalid") && !lblBikeNumber.getText().startsWith("Invalid") && !lblLisceneNumber.getText().startsWith("Invalid") && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                        boolean employeeIsSaved = new EmployeeController().addEmployee(new Employee(txtEmpId.getText(), txtEmpNIC.getText(), txtEmpName.getText(), txtEmpAddress.getText(), txtEmpContactNumber.getText(), cmbJobRole.getValue(), Integer.parseInt(txtEmpWorkHours.getText()), txtBikeNumber.getText(), txtLisceneNo.getText(), 0));
                        if (employeeIsSaved) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully..!").show();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Failed try again..!").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Please input valid data..!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Empty fields,try again..!").show();
                }

            } else {
                if (cmbJobRole.getSelectionModel().getSelectedItem() == null) {
                    new Alert(Alert.AlertType.ERROR, "Input job Role & try again..!").show();
                } else {
                    //
                    if (!txtEmpId.getText().isEmpty() && !txtEmpName.getText().isEmpty() && !txtEmpNIC.getText().isEmpty() && !txtEmpAddress.getText().isEmpty() && !txtEmpContactNumber.getText().isEmpty() && !txtEmpWorkHours.getText().isEmpty() && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                        if (!lblEmployee.getText().startsWith("Invalid") && !lblEmployeeName.getText().startsWith("Invalid") && !lblNIC.getText().startsWith("Invalid") && !lblEmployeeAddress.getText().startsWith("Invalid") && !lblContactNumber.getText().startsWith("Invalid") && !lblWorkingHours.getText().startsWith("Invalid") && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                            boolean employeeIsSaved = new EmployeeController().addEmployee(new Employee(txtEmpId.getText(), txtEmpNIC.getText(), txtEmpName.getText(), txtEmpAddress.getText(), txtEmpContactNumber.getText(), cmbJobRole.getValue(), Integer.parseInt(txtEmpWorkHours.getText())));
                            if (employeeIsSaved) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully..!").show();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Failed try again..!").show();
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Invalid data please input valid data & try again..!").show();
                        }
                    }  else {
                        new Alert(Alert.AlertType.ERROR, "Empty fields..!").show();
                    }
                }
            }
            clearAll();
            lastEmpId();
            loadTableData();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeOnAction(ActionEvent event) {
        // UpdateEmployee
        Validation validation = new Validation();
        try {
            if (cmbJobRole.getSelectionModel().getSelectedItem() == ("Cashier")) {
                if (!txtEmpId.getText().isEmpty() && !txtEmpName.getText().isEmpty() && !txtEmpNIC.getText().isEmpty() && !txtEmpAddress.getText().isEmpty() && !txtEmpContactNumber.getText().isEmpty() && !txtEmpWorkHours.getText().isEmpty() && !txtEmpUserName.getText().isEmpty() && !txtEmpPassword.getText().isEmpty() && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                    if (!lblEmployee.getText().startsWith("Invalid") && !lblEmployeeName.getText().startsWith("Invalid") && !lblNIC.getText().startsWith("Invalid") && !lblEmployeeAddress.getText().startsWith("Invalid") && !lblContactNumber.getText().startsWith("Invalid") && !lblWorkingHours.getText().startsWith("Invalid") && !lblUserName.getText().startsWith("Invalid") && !lblPassword.getText().startsWith("Invalid") && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                        boolean employeeIsUpdated = new EmployeeController().updateEmployee(new Employee(txtEmpId.getText(), txtEmpNIC.getText(), txtEmpName.getText(), txtEmpAddress.getText(), txtEmpContactNumber.getText(), cmbJobRole.getValue(), Integer.parseInt(txtEmpWorkHours.getText()), txtEmpUserName.getText(), txtEmpPassword.getText()));
                        if (employeeIsUpdated) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully..!").show();
                            clearAll();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Failed try again..!").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Please input valid data..!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Empty fields try again..!").show();
                }
            } else if (cmbJobRole.getSelectionModel().getSelectedItem() == ("Rider")) {
                // Rider
                if (!txtEmpId.getText().isEmpty() && !txtEmpName.getText().isEmpty() && !txtEmpNIC.getText().isEmpty() && !txtEmpAddress.getText().isEmpty() && !txtEmpContactNumber.getText().isEmpty() && !txtEmpWorkHours.getText().isEmpty() && !txtBikeNumber.getText().isEmpty() && !txtLisceneNo.getText().isEmpty() && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                    if (!lblEmployee.getText().startsWith("Invalid") && !lblEmployeeName.getText().startsWith("Invalid") && !lblNIC.getText().startsWith("Invalid") && !lblEmployeeAddress.getText().startsWith("Invalid") && !lblContactNumber.getText().startsWith("Invalid") && !lblWorkingHours.getText().startsWith("Invalid") && !lblBikeNumber.getText().startsWith("Invalid") && !lblLisceneNumber.getText().startsWith("Invalid") && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                        boolean employeeIsUpdated = new EmployeeController().updateEmployee(new Employee(txtEmpId.getText(), txtEmpNIC.getText(), txtEmpName.getText(), txtEmpAddress.getText(), txtEmpContactNumber.getText(), cmbJobRole.getValue(), Integer.parseInt(txtEmpWorkHours.getText()), txtBikeNumber.getText(), txtLisceneNo.getText(), 0));
                        if (employeeIsUpdated) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully..!").show();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Failed try again..!").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Please input valid data..!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Empty fields try again..!").show();
                }

            } else {
                if (cmbJobRole.getSelectionModel().getSelectedItem() == null) {
                    new Alert(Alert.AlertType.ERROR, "Select Job Role & try again..!").show();
                } else {
                   if (!txtEmpId.getText().isEmpty() && !txtEmpName.getText().isEmpty() && !txtEmpNIC.getText().isEmpty() && !txtEmpAddress.getText().isEmpty() && !txtEmpContactNumber.getText().isEmpty() && !txtEmpWorkHours.getText().isEmpty() && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                        if (!lblEmployee.getText().startsWith("Invalid") && !lblEmployeeName.getText().startsWith("Invalid") && !lblNIC.getText().startsWith("Invalid") && !lblEmployeeAddress.getText().startsWith("Invalid") && !lblContactNumber.getText().startsWith("Invalid") && !lblWorkingHours.getText().startsWith("Invalid") && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                            boolean employeeIsUpdated = new EmployeeController().updateEmployee(new Employee(txtEmpId.getText(), txtEmpNIC.getText(), txtEmpName.getText(), txtEmpAddress.getText(), txtEmpContactNumber.getText(), cmbJobRole.getValue(), Integer.parseInt(txtEmpWorkHours.getText())));
                            if (employeeIsUpdated) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully..!").show();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Failed try again..!").show();
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Please input valid data..!").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Empty fields try again..!").show();
                    }
                }
            }
            clearAll();
            lastEmpId();
            loadTableData();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void removeEmployeeOnAction(ActionEvent event) {
        Validation validation = new Validation();
        try {
            if (cmbJobRole.getSelectionModel().getSelectedItem() == ("Cashier")) {
                if (!txtEmpId.getText().isEmpty() && !txtEmpName.getText().isEmpty() && !txtEmpNIC.getText().isEmpty() && !txtEmpAddress.getText().isEmpty() && !txtEmpContactNumber.getText().isEmpty() && !txtEmpWorkHours.getText().isEmpty() && !txtEmpUserName.getText().isEmpty() && !txtEmpPassword.getText().isEmpty() && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                    if (!lblEmployee.getText().startsWith("Invalid") && !lblEmployeeName.getText().startsWith("Invalid") && !lblNIC.getText().startsWith("Invalid") && !lblEmployeeAddress.getText().startsWith("Invalid") && !lblContactNumber.getText().startsWith("Invalid") && !lblWorkingHours.getText().startsWith("Invalid") && !lblUserName.getText().startsWith("Invalid") && !lblPassword.getText().startsWith("Invalid") && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                        boolean employeeIsDeleted = new EmployeeController().deleteEmployee(tblEmployeeDetails.getSelectionModel().getSelectedItem().getEmployeeID(), cmbJobRole.getValue());
                        if (employeeIsDeleted) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully..!").show();
                            clearAll();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Failed try again..!").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Please input valid data..!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Empty fields try again..!").show();
                }
            } else if (cmbJobRole.getSelectionModel().getSelectedItem() == ("Rider")) {
                // Rider
                if (!txtEmpId.getText().isEmpty() && !txtEmpName.getText().isEmpty() && !txtEmpNIC.getText().isEmpty() && !txtEmpAddress.getText().isEmpty() && !txtEmpContactNumber.getText().isEmpty() && !txtEmpWorkHours.getText().isEmpty() && !txtBikeNumber.getText().isEmpty() && !txtLisceneNo.getText().isEmpty() && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                    if (!lblEmployee.getText().startsWith("Invalid") && !lblEmployeeName.getText().startsWith("Invalid") && !lblNIC.getText().startsWith("Invalid") && !lblEmployeeAddress.getText().startsWith("Invalid") && !lblContactNumber.getText().startsWith("Invalid") && !lblWorkingHours.getText().startsWith("Invalid") && !lblBikeNumber.getText().startsWith("Invalid") && !lblLisceneNumber.getText().startsWith("Invalid") && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                        boolean employeeIsUpdated = new EmployeeController().deleteEmployee(tblEmployeeDetails.getSelectionModel().getSelectedItem().getEmployeeID(), cmbJobRole.getValue());
                        if (employeeIsUpdated) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully..!").show();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Failed try again..!").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Please input valid data..!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Empty fields try again..!").show();
                }

            } else {
                if (cmbJobRole.getSelectionModel().getSelectedItem() == null) {
                    new Alert(Alert.AlertType.ERROR, "Incorrect try again..!").show();
                } else {
                    // kitchen helper, chef
                    if (!txtEmpId.getText().isEmpty() && !txtEmpName.getText().isEmpty() && !txtEmpNIC.getText().isEmpty() && !txtEmpAddress.getText().isEmpty() && !txtEmpContactNumber.getText().isEmpty() && !txtEmpWorkHours.getText().isEmpty() && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                        if (!lblEmployee.getText().startsWith("Invalid") && !lblEmployeeName.getText().startsWith("Invalid") && !lblNIC.getText().startsWith("Invalid") && !lblEmployeeAddress.getText().startsWith("Invalid") && !lblContactNumber.getText().startsWith("Invalid") && !lblWorkingHours.getText().startsWith("Invalid") && cmbJobRole.getSelectionModel().getSelectedItem() != null) {
                            boolean employeeIsUpdated = new EmployeeController().deleteEmployee(tblEmployeeDetails.getSelectionModel().getSelectedItem().getEmployeeID(), cmbJobRole.getValue());
                            if (employeeIsUpdated) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully..!").show();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Failed try again..!").show();
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Please input valid data..!").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Empty fields try again..!").show();
                    }
                }
            }
            clearAll();
            lastEmpId();
            loadTableData();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void checkEmployeeId(KeyEvent keyEvent) {
        String value = "^([E]{1}[-]([0-9]{3,4}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtEmpId.getText());
        if (matcher.matches()) {
            txtEmpId.getParent().setStyle("-fx-border-color: GREEN");
            lblEmployee.setText("");
        } else {
            txtEmpId.getParent().setStyle("-fx-border-width: 1");
            txtEmpId.getParent().setStyle("-fx-border-color: red");
            lblEmployee.setText("Invalid employee Id");
        }
    }

    public void checkEmployeeName(KeyEvent keyEvent) {
        String value = "^[A-z ]{3,25}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtEmpName.getText());
        if (matcher.matches()) {
            txtEmpName.getParent().setStyle("-fx-border-color: GREEN");
            lblEmployeeName.setText("");
        } else {
            txtEmpName.getParent().setStyle("-fx-border-width: 1");
            txtEmpName.getParent().setStyle("-fx-border-color: red");
            lblEmployeeName.setText("Invalid employee Name");
        }
    }

    public void checkEmployeeNIC(KeyEvent keyEvent) {
        String value = "^([0-9]{9}.([V]{1}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtEmpNIC.getText());
        if (matcher.matches()) {
            txtEmpNIC.getParent().setStyle("-fx-border-color: GREEN");
            lblNIC.setText("");
        } else {
            txtEmpNIC.getParent().setStyle("-fx-border-width: 1");
            txtEmpNIC.getParent().setStyle("-fx-border-color: red");
            lblNIC.setText("Invalid NIC");
        }
    }

    public void checkEmployeeAddress(KeyEvent keyEvent) {
        String value = "^[A-z0-9 ,/]{4,20}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtEmpAddress.getText());
        if (matcher.matches()) {
            txtEmpAddress.getParent().setStyle("-fx-border-color: GREEN");
            lblEmployeeAddress.setText("");
        } else {
            txtEmpAddress.getParent().setStyle("-fx-border-width: 1");
            txtEmpAddress.getParent().setStyle("-fx-border-color: red");
            lblEmployeeAddress.setText("Invalid address");
        }
    }

    public void checkBikeNumber(KeyEvent keyEvent) {
        String value = "^([A-Z0-9]{2,3}[-]([0-9]{4}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtBikeNumber.getText());
        if (matcher.matches()) {
            txtBikeNumber.setStyle("-fx-border-color: GREEN");
            lblBikeNumber.setText("");
        } else {
            txtBikeNumber.setStyle("-fx-border-width: 1");
            txtBikeNumber.setStyle("-fx-border-color: red");
            lblBikeNumber.setText("Invalid Bike Number");
        }
    }

    public void checkLisceneNO(KeyEvent keyEvent) {
        String value = "^([B]{1}.([0-9]{6}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtLisceneNo.getText());
        if (matcher.matches()) {
            txtLisceneNo.setStyle("-fx-border-color: GREEN");
            lblLisceneNumber.setText("");
        } else {
            txtLisceneNo.setStyle("-fx-border-width: 1");
            txtLisceneNo.setStyle("-fx-border-color: red");
            lblLisceneNumber.setText("Invalid License Number");
        }
    }

    public void checkEmployeeNumber(KeyEvent keyEvent) { //"^[0][7][1,2,4,5,6,7,8]{1}[0-9]{7}$"
        String value = "^(0){1}[0-9]{9}$";     //"^([0-9]{10})$"
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtEmpContactNumber.getText());
        if (matcher.matches()) {
            txtEmpContactNumber.getParent().setStyle("-fx-border-color: GREEN");
            lblContactNumber.setText("");
        } else {
            txtEmpContactNumber.getParent().setStyle("-fx-border-width: 1");
            txtEmpContactNumber.getParent().setStyle("-fx-border-color: red");
            lblContactNumber.setText("Invalid Mobile Number");
        }
    }

    public void checkWorkingHours(KeyEvent keyEvent) {
        String value = "^[1-9][0-9]{0,1}(.30)?$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtEmpWorkHours.getText());
        if (matcher.matches()) {
            txtEmpWorkHours.getParent().setStyle("-fx-border-color: GREEN");
            lblWorkingHours.setText("");
        } else {
            txtEmpWorkHours.getParent().setStyle("-fx-border-width: 1");
            txtEmpWorkHours.getParent().setStyle("-fx-border-color: red");
            lblWorkingHours.setText("Invalid working hours");
        }
    }

    public void checkUserName(KeyEvent keyEvent) {
        String value = "^[A-Za-z0-9@_]{5,10}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtEmpUserName.getText());
        if (matcher.matches()) {
            txtEmpUserName.getParent().setStyle("-fx-border-color: GREEN");
            lblUserName.setText("");
        } else {
            txtEmpUserName.getParent().setStyle("-fx-border-width: 1");
            txtEmpUserName.getParent().setStyle("-fx-border-color: red");
            lblUserName.setText("Invalid UserName");
        }
    }

    public void checkPassword(KeyEvent keyEvent) {
        String value = "^[A-Za-z0-9]{5,20}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtEmpPassword.getText());
        if (matcher.matches()) {
            txtEmpPassword.getParent().setStyle("-fx-border-color: GREEN");
            lblPassword.setText("");
        } else {
            txtEmpPassword.getParent().setStyle("-fx-border-width: 1");
            txtEmpPassword.getParent().setStyle("-fx-border-color: red");
            lblPassword.setText("Invalid Password");
        }
    }

    public void ActionOne(KeyEvent keyEvent) {
        Object response = validate();

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                //
            }
        }
    }

    public void ActionTwo(KeyEvent keyEvent) {
        Object response = validate();

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                //
            }
        }
    }

    public void setBorders(TextField... textFields){
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }

    public void txtBikeNoOnAction(ActionEvent event) {
        txtLisceneNo.requestFocus();
    }
}
