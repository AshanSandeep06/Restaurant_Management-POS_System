package validation;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public boolean orderID(TextField textField){
        Pattern compile = Pattern.compile("^[O ][-][ 0-9]{2,8}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }
    public boolean discountIDValidation(TextField textField){
        Pattern compile = Pattern.compile("^[D][- ]*[0-9]{4}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }

    public boolean DateValidation(TextField textField){
        Pattern compile = Pattern.compile("^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }


    public boolean packageNameValidation(TextField textField){
        Pattern compile = Pattern.compile("^[A-Za-z0-9 /#_]+$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }
    public boolean mobileNumberValidation(TextField textField){
        Pattern compile = Pattern.compile("^[0-9]{5,15}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }
    public boolean nameValidation(TextField textField){
        Pattern compile = Pattern.compile("^[A-Za-z ]{3,50}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }
    public boolean nicValidation(TextField textField){
        Pattern compile = Pattern.compile("^[0-9]{3,15}[A-Za-z]*$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }

    public boolean bikeNo(TextField textField){
        Pattern compile = Pattern.compile("^[A-Za-z0-9]{2,3}[ -][A-Za-z0-9]{4}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }
    public boolean drivingLicense(TextField textField){
        Pattern compile = Pattern.compile("^[A-Za-z]*[0-9]{5,30}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }

    public boolean workingHours(TextField textField){
        Pattern compile = Pattern.compile("^[0-9]{1,2}[-][0-9]{1,2}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }
    public boolean userName(TextField textField){
        Pattern compile = Pattern.compile("^[A-Za-z0-9@_#]{5,20}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }
    public boolean password(TextField textField){
        Pattern compile = Pattern.compile("^[A-Za-z0-9]{5,20}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }

    public boolean addressValidation(TextField textField, String type){
        if(type.equals("Customer")){
            if (textField.getText() == null){
                return true;
            }else{
                Pattern compile = Pattern.compile("^[0-9\\\\\\/ ,a-zA-Z]*[ ,]*[0-9\\\\\\/, a-zA-Z]*$");
                Matcher matcher = compile.matcher(textField.getText());
                if (matcher.matches()){
                    textField.setStyle("-fx-border-color: green");
                    return true;
                }else{
                    textField.setStyle("-fx-border-color: red");
                    return false;
                }
            }

        }else{
            Pattern compile = Pattern.compile("^[0-9\\\\\\/ ,a-zA-Z]*[ ,]*[0-9\\\\\\/, a-zA-Z]*$");
            Matcher matcher = compile.matcher(textField.getText());
            if (matcher.matches()){
                textField.setStyle("-fx-border-color: green");
                return true;
            }else{
                textField.setStyle("-fx-border-color: red");
                return false;
            }
        }
    }

    public  boolean idValidation(TextField textField){
        Pattern compile = Pattern.compile("^[A-Za-z0-9- /]{3}$");  //1,20
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }
    public  boolean descriptionValidation(TextField textField){
        Pattern compile = Pattern.compile("^[A-Za-z0-9- /&]{1,50}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }

    public boolean priceValidation(TextField textField){
        Pattern compile = Pattern.compile("^[1-9][0-9]+[.]*+[0-9]{1,2}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }

    public boolean sizeAndPortion(TextField textField){
        Pattern compile = Pattern.compile("^[A-Za-z0-9\"\\/ -]{1,10}$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }
    public boolean quantityValidation(TextField textField){
        Pattern compile = Pattern.compile("^[0-9]+$");
        Matcher matcher = compile.matcher(textField.getText());
        if (matcher.matches()){
            textField.setStyle("-fx-border-color: green");
            return true;
        }else{
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }
}
