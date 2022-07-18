package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Login;
import model.Password;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;

public class LoginFormController {

    public RadioButton rdbAdminLogin;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public AnchorPane context;
    public static String cashierName;

    public void txtUserNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    public void loginOnAction(ActionEvent event) {
        /*try {
            if (rdbAdminLogin.isSelected()) {
                if (checkIsEmpty()) {
                    //new Alert(Alert.AlertType.ERROR, "Please enter the username & password..!", ButtonType.CLOSE).show();
                    Notifications notificationBuilder = Notifications.create()
                            .title("Login Failed..!")
                            .text("Please enter the username & password..!")
                            .graphic(new ImageView(new Image("/assets/icons8-help-100.png")))
                            .hideAfter(Duration.seconds(4))
                            .position(Pos.CENTER);
                    //notificationBuilder.showConfirm();
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
//                    return;


                } else {
                    // See from login table if the user name & password are correct
                    Password admin = new LoginController().getLogin(txtUserName.getText(), txtPassword.getText(), "Admin");
                    if (admin == null) {
                        //new Alert(Alert.AlertType.ERROR, "Invalid UserName & Password..!").show();
                        Notifications notificationBuilder = Notifications.create()
                                .title("Login Failed..!")
                                .text("Invalid UserName & Password..!")
                                .graphic(new ImageView(new Image("/assets/icons8-help-100.png")))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.CENTER);
                        //notificationBuilder.showConfirm();
                        notificationBuilder.darkStyle();
                        notificationBuilder.show();


                    } else {
                        // insert code redirect to admin dashBoard
                        Stage stage = (Stage) context.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdminDashBoardForm.fxml"))));
                        stage.setTitle("Admin DashBoard");
                        stage.show();
                        Notifications notificationBuilder = Notifications.create()
                                .title("Login Successfully..!")
                                .text("You have Successfully login to the System..!")
                                .graphic(new ImageView(new Image("/assets/icons8-ok-100.png")))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.BOTTOM_RIGHT);
                        notificationBuilder.darkStyle();
                        notificationBuilder.show();
                    }
                }
            } else {
                if (checkIsEmpty()) {
                    // new Alert(Alert.AlertType.ERROR, "Please enter the username & password..!", ButtonType.CLOSE).show();
                    Notifications notificationBuilder = Notifications.create()
                            .title("Login Failed..!")
                            .text("Please enter the username & password..!")
                            .graphic(new ImageView(new Image("/assets/icons8-help-100.png")))
                            .hideAfter(Duration.seconds(4))
                            .position(Pos.CENTER);
                    //notificationBuilder.showConfirm();
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
                } else {
                    // See from login table if the user name & password are correct
                    Password cashier = new LoginController().getLogin(txtUserName.getText(), txtPassword.getText(), "Cashier");
                    if (cashier == null) {
                        // new Alert(Alert.AlertType.ERROR, "Invalid UserName & Password..!").show();
                        Notifications notificationBuilder = Notifications.create()
                                .title("Login Failed..!")
                                .text("Invalid UserName & Password..!")
                                .graphic(new ImageView(new Image("/assets/icons8-help-100.png")))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.CENTER);
                        //notificationBuilder.showConfirm();
                        notificationBuilder.darkStyle();
                        notificationBuilder.show();
                    } else {
                        // insert code redirect to cashier dashBoard
                        cashierName=cashier.getEmployeeName();
                        Stage stage = (Stage) context.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CashierDashBoardForm.fxml"))));
                        stage.setTitle("Cashier DashBoard");
                        stage.show();
                        Notifications notificationBuilder = Notifications.create()
                                .title("Login Successfully..!")
                                .text("You have Successfully login to the System..!")
                                .graphic(new ImageView(new Image("/assets/icons8-ok-100.png")))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.BOTTOM_RIGHT);
                        notificationBuilder.darkStyle();
                        notificationBuilder.show();
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }*/

        try{
            if(checkIsEmpty()){
                //new Alert(Alert.AlertType.ERROR, "Please enter the username & password..!", ButtonType.CLOSE).show();
                Notifications notificationBuilder = Notifications.create()
                        .title("Login Failed..!")
                        .text("Please enter the username & password..!")
                        .graphic(new ImageView(new Image("/assets/icons8-help-100.png")))
                        .hideAfter(Duration.seconds(4))
                        .position(Pos.CENTER);
                //notificationBuilder.showConfirm();
                notificationBuilder.darkStyle();
                notificationBuilder.show();
            }else{
                Login login = new LoginController().getLogin(txtUserName.getText(),txtPassword.getText());
                if(login!=null){
                    if(login.getJobRole().equals("Admin")){
                        // insert code redirect to admin dashBoard
                        Stage stage = (Stage) context.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdminDashBoardForm.fxml"))));
                        stage.setTitle("Admin DashBoard");
                        stage.show();
                        Notifications notificationBuilder = Notifications.create()
                                .title("Login Successfully..!")
                                .text("You have Successfully login to the System..!")
                                .graphic(new ImageView(new Image("/assets/icons8-ok-100.png")))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.BOTTOM_RIGHT);
                        notificationBuilder.darkStyle();
                        notificationBuilder.show();
                    }else{
                        // insert code redirect to cashier dashBoard
                        cashierName=login.getEmployeeName();
                        Stage stage = (Stage) context.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CashierDashBoardForm.fxml"))));
                        stage.setTitle("Cashier DashBoard");
                        stage.show();
                        Notifications notificationBuilder = Notifications.create()
                                .title("Login Successfully..!")
                                .text("You have Successfully login to the System..!")
                                .graphic(new ImageView(new Image("/assets/icons8-ok-100.png")))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.BOTTOM_RIGHT);
                        notificationBuilder.darkStyle();
                        notificationBuilder.show();
                    }
                }else{
                    //new Alert(Alert.AlertType.ERROR, "Invalid UserName & Password..!").show();
                    Notifications notificationBuilder = Notifications.create()
                            .title("Login Failed..!")
                            .text("Invalid UserName & Password..!")
                            .graphic(new ImageView(new Image("/assets/icons8-help-100.png")))
                            .hideAfter(Duration.seconds(4))
                            .position(Pos.CENTER);
                    //notificationBuilder.showConfirm();
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
                }
            }


        }catch (SQLException | ClassNotFoundException | IOException e){

        }
    }

    private boolean checkIsEmpty() {
        return txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty();
    }
}
