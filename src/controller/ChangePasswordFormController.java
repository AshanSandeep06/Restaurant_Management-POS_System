package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Login;
import util.ValidationUtil;

import javax.security.auth.callback.LanguageCallback;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.rgb;

public class ChangePasswordFormController {

    public ComboBox<Login> cmbUserName;
    public TextField txtCurrentPwd;
    public TextField txtEnterNewPwd;
    public TextField txtConfirmNewPwd;
    public JFXTextField txtJobRole;
    public JFXButton btnChangePwd;
    public Label lblEnterNewPwd;
    public Label lblConfirmNewPwd;
    public static Label lbl1=new Label();
    public static Label lbl2=new Label();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        //txtJobRole.setDisable(true);
        //txtCurrentPwd.setDisable(true);
        lbl1=lblEnterNewPwd;
        lbl2=lblConfirmNewPwd;

        btnChangePwd.setDisable(true);


        loadUserNames();

        cmbUserName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                txtCurrentPwd.setText(newValue.getPassword());
                txtJobRole.setText(newValue.getJobRole());
            }
        });

        Pattern newPwdPattern = Pattern.compile("^[A-z]{5,15}[0-9]{3,5}$");
        Pattern confirmNewPwdPattern = Pattern.compile("^[A-z]{5,15}[0-9]{3,5}$");

        map.put(txtEnterNewPwd,newPwdPattern);
        map.put(txtConfirmNewPwd,confirmNewPwdPattern);
    }

    private void loadUserNames(){
        try{
            cmbUserName.setItems(new LoginController().getUserNames());

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void changePasswordOnAction(ActionEvent event) {
        try{
            if(cmbUserName.getValue()!=null && !txtJobRole.getText().isEmpty() && !txtCurrentPwd.getText().isEmpty() && !txtEnterNewPwd.getText().isEmpty() && !txtConfirmNewPwd.getText().isEmpty()){
                if (checkCurrentPassword(txtCurrentPwd.getText(),cmbUserName.getValue().getUserName())){
                    if (txtEnterNewPwd.getText().equals(txtConfirmNewPwd.getText())){
                        if(txtEnterNewPwd.getStyle()!="-fx-border-color: red" && txtConfirmNewPwd.getStyle()!="-fx-border-color: red"){
                            if(new LoginController().changePassword(cmbUserName.getValue().getUserName(),txtConfirmNewPwd.getText())){
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Password change successfully", ButtonType.CLOSE);
                                alert.show();
                                clear();
                            }else{
                                new Alert(Alert.AlertType.ERROR, "try again", ButtonType.OK).show();
                            }
                        }else{
                            new Alert(Alert.AlertType.ERROR, "Invalid Password, try again..!", ButtonType.OK).show();
                        }
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Password doesn't match..!", ButtonType.OK).show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Current Password is incorrect..!", ButtonType.OK).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields, try again.!", ButtonType.OK).show();
            }
            loadUserNames();
            clear();
        }catch (SQLException | ClassNotFoundException e){
            //e.printStackTrace();
        }
    }

    private boolean checkCurrentPassword(String currentPassword,String userName){
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT password FROM Login WHERE userName=?");
            stm.setString(1,userName);
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()){
                if (resultSet.getString(1).equals(currentPassword)){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    public void passwordOnAction(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnChangePwd);

        if(!txtEnterNewPwd.getText().isEmpty() && !txtConfirmNewPwd.getText().isEmpty()){
            if(txtEnterNewPwd.getStyle()=="-fx-border-color: red" | txtConfirmNewPwd.getStyle()=="-fx-border-color: red"){
                lblConfirmNewPwd.setTextFill(rgb(255, 0, 0));
                lblConfirmNewPwd.setText("*Invalid Password");
                btnChangePwd.setDisable(true);
             }else{
                if(txtEnterNewPwd.getText().equals(txtConfirmNewPwd.getText())){
                    lblConfirmNewPwd.setTextFill(rgb(21, 152, 127));
                    lblConfirmNewPwd.setText("✔ Password is right");
                    btnChangePwd.setDisable(false);
                }else{
                    lblConfirmNewPwd.setTextFill(rgb(255, 0, 0));
                    lblConfirmNewPwd.setText("*Password doesn't match");
                    btnChangePwd.setDisable(true);
                }
            }
        }else{
            btnChangePwd.setDisable(true);
        }
    }

    public void txtNewOnAction(ActionEvent event) {
        Object response =  ValidationUtil.validate(map,btnChangePwd);
        //if the response is a text field
        //that means there is a error
        if (response instanceof TextField) {
            TextField textField = (TextField) response;
            textField.requestFocus();// if there is a error just focus it
        } else if(response instanceof Boolean) {
            txtConfirmNewPwd.requestFocus();
        }
    }

    public void txtConfirmOnAction(ActionEvent event) {
        Object response =  ValidationUtil.validate(map,btnChangePwd);
        if (response instanceof TextField) {
            TextField textField = (TextField) response;
            textField.requestFocus();
        } else if(response instanceof Boolean) {
            btnChangePwd.setDisable(true);
        }
    }

    public void clear(){
        cmbUserName.getSelectionModel().clearSelection();
        txtJobRole.clear();
        txtCurrentPwd.clear();
        txtEnterNewPwd.clear();
        txtConfirmNewPwd.clear();
        lblConfirmNewPwd.setText(" ");
        lblEnterNewPwd.setText(" ");

        txtEnterNewPwd.setStyle("-fx-border-color: null");
        txtConfirmNewPwd.setStyle("-fx-border-color: null");

        btnChangePwd.setDisable(true);
    }

    public void clearAllOnAction(ActionEvent event) {
        clear();
    }
}
