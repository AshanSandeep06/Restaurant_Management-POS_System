package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class AdminDashBoardFormController {

    public Label lblDate;
    public Label lblTime;
    public AnchorPane adminContext;
    public AnchorPane optionContext;
    public Label lblFoodItem;
    public Button btnFoodItem;
    public Label lblViewFAndEmp;
    public Label lblReports;
    public Label lblPackages;
    public Label lblCustomer;
    public Label lblEmployee;
    public Label lblDiscount;
    public Label lblChangePwd;
    public Button btnEmployee;
    public Button btnCustomer;
    public Button btnPackages;
    public Button btnDiscount;
    public Button btnFoodsAndEmps;
    public Button btnChangePassword;
    public Button btnReports;
    public Label lblEmpSalaryManage;
    public Button btnSalaryManagement;
    public Label lblEmpAttendance;
    public Button btnEmpAttendance;
    public static Label StaticlblTime = new Label();

    public void initialize() throws IOException {
        loadDateAndTime();
        optionContext.getChildren().clear();
        optionContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/MainForm.fxml")));
    }

    private void loadDateAndTime(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        lblDate.setText(dateFormat.format(date));

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, (e) -> {
            LocalTime currentTime = LocalTime.now();
            int hour = currentTime.getHour();
            if (hour >= 12) {
                lblTime.setText(currentTime.getHour() + ":" +
                        currentTime.getMinute() + ":" +
                        currentTime.getSecond() + " PM");

                StaticlblTime.setText(lblTime.getText());

            } else {
                lblTime.setText(currentTime.getHour() + ":" +
                        currentTime.getMinute() + ":" +
                        currentTime.getSecond() + " AM");

                StaticlblTime.setText(lblTime.getText());

            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void foodItemOnAction(ActionEvent event) throws IOException {
        lblFoodItem.setStyle("-fx-background-color : #0097e6");
        btnFoodItem.setStyle("-fx-background-color : #0097e6");

        lblEmployee.setStyle("-fx-background-color : null");
        btnEmployee.setStyle("-fx-background-color : null");
        lblCustomer.setStyle("-fx-background-color : null");
        btnCustomer.setStyle("-fx-background-color : null");
        lblPackages.setStyle("-fx-background-color : null");
        btnPackages.setStyle("-fx-background-color : null");
        lblDiscount.setStyle("-fx-background-color : null");
        btnDiscount.setStyle("-fx-background-color : null");
        /*lblViewFAndEmp.setStyle("-fx-background-color : null");
        btnFoodsAndEmps.setStyle("-fx-background-color : null");*/
        lblChangePwd.setStyle("-fx-background-color : null");
        btnChangePassword.setStyle("-fx-background-color : null");
        lblReports.setStyle("-fx-background-color : null");
        btnReports.setStyle("-fx-background-color : null");
        btnEmpAttendance.setStyle("-fx-background-color : null");
        lblEmpAttendance.setStyle("-fx-background-color : null");
        btnSalaryManagement.setStyle("-fx-background-color : null");
        lblEmpSalaryManage.setStyle("-fx-background-color : null");

        optionContext.getChildren().clear();
        optionContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/FoodItemForm.fxml")));
    }

    public void EmployeeOnAction(ActionEvent event) throws IOException {

        lblEmployee.setStyle("-fx-background-color : #0097e6");
        btnEmployee.setStyle("-fx-background-color : #0097e6");

        lblFoodItem.setStyle("-fx-background-color : null");
        btnFoodItem.setStyle("-fx-background-color : null");
        lblCustomer.setStyle("-fx-background-color : null");
        btnCustomer.setStyle("-fx-background-color : null");
        lblPackages.setStyle("-fx-background-color : null");
        btnPackages.setStyle("-fx-background-color : null");
        lblDiscount.setStyle("-fx-background-color : null");
        btnDiscount.setStyle("-fx-background-color : null");
        /*lblViewFAndEmp.setStyle("-fx-background-color : null");
        btnFoodsAndEmps.setStyle("-fx-background-color : null");*/
        lblChangePwd.setStyle("-fx-background-color : null");
        btnChangePassword.setStyle("-fx-background-color : null");
        lblReports.setStyle("-fx-background-color : null");
        btnReports.setStyle("-fx-background-color : null");
        btnEmpAttendance.setStyle("-fx-background-color : null");
        lblEmpAttendance.setStyle("-fx-background-color : null");
        btnSalaryManagement.setStyle("-fx-background-color : null");
        lblEmpSalaryManage.setStyle("-fx-background-color : null");

        optionContext.getChildren().clear();
        optionContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml")));
    }

    public void CustomerOnAction(ActionEvent event) throws IOException {
        lblCustomer.setStyle("-fx-background-color : #0097e6");
        btnCustomer.setStyle("-fx-background-color : #0097e6");

        lblFoodItem.setStyle("-fx-background-color : null");
        btnFoodItem.setStyle("-fx-background-color : null");
        lblEmployee.setStyle("-fx-background-color : null");
        btnEmployee.setStyle("-fx-background-color : null");
        lblPackages.setStyle("-fx-background-color : null");
        btnPackages.setStyle("-fx-background-color : null");
        lblDiscount.setStyle("-fx-background-color : null");
        btnDiscount.setStyle("-fx-background-color : null");
        /*lblViewFAndEmp.setStyle("-fx-background-color : null");
        btnFoodsAndEmps.setStyle("-fx-background-color : null");*/
        lblChangePwd.setStyle("-fx-background-color : null");
        btnChangePassword.setStyle("-fx-background-color : null");
        lblReports.setStyle("-fx-background-color : null");
        btnReports.setStyle("-fx-background-color : null");
        btnEmpAttendance.setStyle("-fx-background-color : null");
        lblEmpAttendance.setStyle("-fx-background-color : null");
        btnSalaryManagement.setStyle("-fx-background-color : null");
        lblEmpSalaryManage.setStyle("-fx-background-color : null");

        optionContext.getChildren().clear();
        optionContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageCustomerForm.fxml")));
    }

    public void PackagesOnAction(ActionEvent event) throws IOException {
        lblPackages.setStyle("-fx-background-color : #0097e6");
        btnPackages.setStyle("-fx-background-color : #0097e6");

        lblFoodItem.setStyle("-fx-background-color : null");
        btnFoodItem.setStyle("-fx-background-color : null");
        lblCustomer.setStyle("-fx-background-color : null");
        btnCustomer.setStyle("-fx-background-color : null");
        lblEmployee.setStyle("-fx-background-color : null");
        btnEmployee.setStyle("-fx-background-color : null");
        lblDiscount.setStyle("-fx-background-color : null");
        btnDiscount.setStyle("-fx-background-color : null");
        /*lblViewFAndEmp.setStyle("-fx-background-color : null");
        btnFoodsAndEmps.setStyle("-fx-background-color : null");*/
        lblChangePwd.setStyle("-fx-background-color : null");
        btnChangePassword.setStyle("-fx-background-color : null");
        lblReports.setStyle("-fx-background-color : null");
        btnReports.setStyle("-fx-background-color : null");
        btnEmpAttendance.setStyle("-fx-background-color : null");
        lblEmpAttendance.setStyle("-fx-background-color : null");
        btnSalaryManagement.setStyle("-fx-background-color : null");
        lblEmpSalaryManage.setStyle("-fx-background-color : null");

        optionContext.getChildren().clear();
        optionContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManagePackageForm.fxml")));
    }

    public void DiscountOnAction(ActionEvent event) throws IOException {
        lblDiscount.setStyle("-fx-background-color : #0097e6");
        btnDiscount.setStyle("-fx-background-color : #0097e6");

        lblFoodItem.setStyle("-fx-background-color : null");
        btnFoodItem.setStyle("-fx-background-color : null");
        lblCustomer.setStyle("-fx-background-color : null");
        btnCustomer.setStyle("-fx-background-color : null");
        lblPackages.setStyle("-fx-background-color : null");
        btnPackages.setStyle("-fx-background-color : null");
        lblEmployee.setStyle("-fx-background-color : null");
        btnEmployee.setStyle("-fx-background-color : null");
        /*lblViewFAndEmp.setStyle("-fx-background-color : null");
        btnFoodsAndEmps.setStyle("-fx-background-color : null");*/
        lblChangePwd.setStyle("-fx-background-color : null");
        btnChangePassword.setStyle("-fx-background-color : null");
        lblReports.setStyle("-fx-background-color : null");
        btnReports.setStyle("-fx-background-color : null");
        btnEmpAttendance.setStyle("-fx-background-color : null");
        lblEmpAttendance.setStyle("-fx-background-color : null");
        btnSalaryManagement.setStyle("-fx-background-color : null");
        lblEmpSalaryManage.setStyle("-fx-background-color : null");

        optionContext.getChildren().clear();
        optionContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ManageDiscountForm.fxml")));
    }

    /*public void viewFoodsAndEmployeesOnAction(ActionEvent event) throws IOException {
        lblViewFAndEmp.setStyle("-fx-background-color : #0097e6");
        btnFoodsAndEmps.setStyle("-fx-background-color : #0097e6");

        lblFoodItem.setStyle("-fx-background-color : null");
        btnFoodItem.setStyle("-fx-background-color : null");
        lblCustomer.setStyle("-fx-background-color : null");
        btnCustomer.setStyle("-fx-background-color : null");
        lblPackages.setStyle("-fx-background-color : null");
        btnPackages.setStyle("-fx-background-color : null");
        lblDiscount.setStyle("-fx-background-color : null");
        btnDiscount.setStyle("-fx-background-color : null");
        lblEmployee.setStyle("-fx-background-color : null");
        btnEmployee.setStyle("-fx-background-color : null");
        lblChangePwd.setStyle("-fx-background-color : null");
        btnChangePassword.setStyle("-fx-background-color : null");
        lblReports.setStyle("-fx-background-color : null");
        btnReports.setStyle("-fx-background-color : null");

        optionContext.getChildren().clear();
        optionContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ViewFoodsAndEmployeesForm.fxml")));
    }*/

    public void changePasswordOnAction(ActionEvent event) throws IOException {
        lblChangePwd.setStyle("-fx-background-color : #0097e6");
        btnChangePassword.setStyle("-fx-background-color : #0097e6");

        lblFoodItem.setStyle("-fx-background-color : null");
        btnFoodItem.setStyle("-fx-background-color : null");
        lblCustomer.setStyle("-fx-background-color : null");
        btnCustomer.setStyle("-fx-background-color : null");
        lblPackages.setStyle("-fx-background-color : null");
        btnPackages.setStyle("-fx-background-color : null");
        lblDiscount.setStyle("-fx-background-color : null");
        btnDiscount.setStyle("-fx-background-color : null");
        /*lblViewFAndEmp.setStyle("-fx-background-color : null");
        btnFoodsAndEmps.setStyle("-fx-background-color : null");*/
        lblEmployee.setStyle("-fx-background-color : null");
        btnEmployee.setStyle("-fx-background-color : null");
        lblReports.setStyle("-fx-background-color : null");
        btnReports.setStyle("-fx-background-color : null");
        btnEmpAttendance.setStyle("-fx-background-color : null");
        lblEmpAttendance.setStyle("-fx-background-color : null");
        btnSalaryManagement.setStyle("-fx-background-color : null");
        lblEmpSalaryManage.setStyle("-fx-background-color : null");

        optionContext.getChildren().clear();
        optionContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ChangePasswordForm.fxml")));
    }

    public void viewReportsOnAction(ActionEvent event) throws IOException {
        lblReports.setStyle("-fx-background-color : #0097e6");
        btnReports.setStyle("-fx-background-color : #0097e6");

        lblFoodItem.setStyle("-fx-background-color : null");
        btnFoodItem.setStyle("-fx-background-color : null");
        lblCustomer.setStyle("-fx-background-color : null");
        btnCustomer.setStyle("-fx-background-color : null");
        lblPackages.setStyle("-fx-background-color : null");
        btnPackages.setStyle("-fx-background-color : null");
        lblDiscount.setStyle("-fx-background-color : null");
        btnDiscount.setStyle("-fx-background-color : null");
        /*lblViewFAndEmp.setStyle("-fx-background-color : null");
        btnFoodsAndEmps.setStyle("-fx-background-color : null");*/
        lblChangePwd.setStyle("-fx-background-color : null");
        btnChangePassword.setStyle("-fx-background-color : null");
        lblEmployee.setStyle("-fx-background-color : null");
        btnEmployee.setStyle("-fx-background-color : null");
        btnEmpAttendance.setStyle("-fx-background-color : null");
        lblEmpAttendance.setStyle("-fx-background-color : null");
        btnSalaryManagement.setStyle("-fx-background-color : null");
        lblEmpSalaryManage.setStyle("-fx-background-color : null");

        optionContext.getChildren().clear();
        optionContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/FinancialReportsForm.fxml")));

        /*Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Manage Employee");
        stage.initOwner((Stage)optionContext.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ChangePasswordForm.fxml"))));
        stage.show();*/
    }

    public void logOutOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) adminContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
    }

    public void EmployeeAttendanceOnAction(ActionEvent event) throws IOException {
        lblEmpAttendance.setStyle("-fx-background-color : #0097e6");
        btnEmpAttendance.setStyle("-fx-background-color : #0097e6");

        lblFoodItem.setStyle("-fx-background-color : null");
        btnFoodItem.setStyle("-fx-background-color : null");
        lblCustomer.setStyle("-fx-background-color : null");
        btnCustomer.setStyle("-fx-background-color : null");
        lblPackages.setStyle("-fx-background-color : null");
        btnPackages.setStyle("-fx-background-color : null");
        lblDiscount.setStyle("-fx-background-color : null");
        btnDiscount.setStyle("-fx-background-color : null");
        /*lblViewFAndEmp.setStyle("-fx-background-color : null");
        btnFoodsAndEmps.setStyle("-fx-background-color : null");*/
        lblChangePwd.setStyle("-fx-background-color : null");
        btnChangePassword.setStyle("-fx-background-color : null");
        lblEmployee.setStyle("-fx-background-color : null");
        btnEmployee.setStyle("-fx-background-color : null");
        btnSalaryManagement.setStyle("-fx-background-color : null");
        lblEmpSalaryManage.setStyle("-fx-background-color : null");
        lblReports.setStyle("-fx-background-color : null");
        btnReports.setStyle("-fx-background-color : null");

        optionContext.getChildren().clear();
        optionContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/EmployeeAttendenceForm.fxml")));
    }

    public void payrollOnAction(ActionEvent event) throws IOException {
        lblEmpSalaryManage.setStyle("-fx-background-color : #0097e6");
        btnSalaryManagement.setStyle("-fx-background-color : #0097e6");

        lblFoodItem.setStyle("-fx-background-color : null");
        btnFoodItem.setStyle("-fx-background-color : null");
        lblCustomer.setStyle("-fx-background-color : null");
        btnCustomer.setStyle("-fx-background-color : null");
        lblPackages.setStyle("-fx-background-color : null");
        btnPackages.setStyle("-fx-background-color : null");
        lblDiscount.setStyle("-fx-background-color : null");
        btnDiscount.setStyle("-fx-background-color : null");
        /*lblViewFAndEmp.setStyle("-fx-background-color : null");
        btnFoodsAndEmps.setStyle("-fx-background-color : null");*/
        lblChangePwd.setStyle("-fx-background-color : null");
        btnChangePassword.setStyle("-fx-background-color : null");
        lblEmployee.setStyle("-fx-background-color : null");
        btnEmployee.setStyle("-fx-background-color : null");
        btnEmpAttendance.setStyle("-fx-background-color : null");
        lblEmpAttendance.setStyle("-fx-background-color : null");
        lblReports.setStyle("-fx-background-color : null");
        btnReports.setStyle("-fx-background-color : null");

        optionContext.getChildren().clear();
        optionContext.getChildren().add(FXMLLoader.load(getClass().getResource("../view/EmployeeSalaryManagementForm.fxml")));
    }
}

