package util;

import com.jfoenix.controls.JFXButton;
import controller.ChangePasswordFormController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static Object validate(LinkedHashMap<TextField, Pattern> map, JFXButton... btn) {
        for (TextField key : map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()){
                //if the input is not matching
                addError(key,btn);
                return key;
            }
            removeError(key,btn);
        }
        return true;
    }

    private static void removeError(TextField txtField,JFXButton... button) {
        txtField.setStyle("-fx-border-width: 2");
        txtField.setStyle("-fx-border-color: green");
        for(JFXButton btn : button){
            btn.setDisable(false);
        }
        ChangePasswordFormController.lbl1.setText(" ");
        ChangePasswordFormController.lbl2.setText(" ");
    }

    private static void addError(TextField txtField,JFXButton... button) {
        if (txtField.getText().length() > 0) {
            txtField.setStyle("-fx-border-width: 2");
            txtField.setStyle("-fx-border-color: red");
        }
        for(JFXButton btn : button){
            btn.setDisable(true);
        }
    }
}
