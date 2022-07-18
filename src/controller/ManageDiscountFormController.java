package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Discount;
import model.DiscountTM;
import model.Food;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageDiscountFormController {

    public JFXTextField txtDiscountId;
    public JFXTextField txtDiscountPrice;
    public JFXTextField txtStartedDate;
    public JFXTextField txtClosedDate;
    public ComboBox<Food> cmbFoodItem;
    public TableView<DiscountTM> tblDiscount;
    public TableColumn colDiscountId;
    public TableColumn colFoodItem;
    public TableColumn colDiscountPrice;
    public TableColumn colStartedDate;
    public TableColumn colClosedDate;
    public Label lblDiscountId;
    public JFXTextField txtFoodName;
    public Label lblDiscountIdTick;
    public Label lblDiscountPriceTick;
    public Label lblStartDateTick;
    public Label lblClosedDateTick;
    public Label lblDiscountPrice;
    public Label lblStartedDate;
    public Label lblClosedDate;
    public JFXButton btnAddDiscount;
    public JFXButton btnUpdateDiscount;
    public JFXButton btnDeleteDiscount;

    public void initialize() {
        txtDiscountId.setDisable(true);
        btnAddDiscount.setDisable(true);
        btnDeleteDiscount.setDisable(true);
        btnUpdateDiscount.setDisable(true);

        colDiscountId.setCellValueFactory(new PropertyValueFactory<>("discountId"));
        colDiscountPrice.setCellValueFactory(new PropertyValueFactory<>("discountPrice"));
        colFoodItem.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        colStartedDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colClosedDate.setCellValueFactory(new PropertyValueFactory<>("closeDate"));

        loadFoodsData();
        getLastDiscountId();
        loadTableData();

        cmbFoodItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtFoodName.setText(newValue.getDescription());
            }
        });

        tblDiscount.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    txtDiscountId.setText(newValue.getDiscountId());
                    txtDiscountPrice.setText(String.valueOf(newValue.getDiscountPrice()));
                    txtStartedDate.setText(newValue.getStartDate());
                    txtClosedDate.setText(newValue.getCloseDate());
                    txtFoodName.setText(newValue.getFoodName());
                    cmbFoodItem.setValue(new DiscountController().getFoodId(txtFoodName.getText()));
                    btnDeleteDiscount.setDisable(false);
                    btnUpdateDiscount.setDisable(false);

                } catch (SQLException | ClassNotFoundException e) {

                }
            }
        });
    }

    private void loadFoodsData() {
        try {
            cmbFoodItem.setItems(new PackageController().getFoods());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getLastDiscountId() {
        try {
            String discountId = new DiscountController().getDiscountId();
            String finalId = "D-0001";

            if (discountId != null) {
                String[] splitString = discountId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "D-000" + id;
                } else if (id < 100) {
                    finalId = "D-00" + id;
                } else if (id < 1000) {
                    finalId = "D-0" + id;
                } else {
                    finalId = "D-" + id;
                }
                txtDiscountId.setText(finalId);
            } else {
                txtDiscountId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadTableData() {
        try {
            tblDiscount.setItems(new DiscountController().getDiscountData());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addDiscountOnAction(ActionEvent event) {
        try {
            if (!txtFoodName.getText().isEmpty() && !txtDiscountId.getText().isEmpty() && !txtDiscountPrice.getText().isEmpty() && !txtStartedDate.getText().isEmpty() && !txtClosedDate.getText().isEmpty()) {
                if (!lblDiscountId.getText().startsWith("*Invalid") && !lblDiscountPrice.getText().startsWith("*Invalid") && !lblStartedDate.getText().startsWith("*Invalid") && !lblClosedDate.getText().startsWith("*Invalid")) {
                    Discount discount = new Discount(txtDiscountId.getText(), Double.parseDouble(txtDiscountPrice.getText()), cmbFoodItem.getValue().getFoodId(), txtStartedDate.getText(), txtClosedDate.getText());
                    boolean isAdded = new DiscountController().addDiscount(discount);
                    if (isAdded) {
                        // store data to table
                        new Alert(Alert.AlertType.CONFIRMATION, "Discount added Successfully..!", ButtonType.OK).show();
                        loadTableData();
                        clearAll();
                        getLastDiscountId();
                    } else {

                        new Alert(Alert.AlertType.ERROR, "Can't add data..!", ButtonType.CLOSE).show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid data fields try again..!", ButtonType.CLOSE).show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
            clearAll();
            getLastDiscountId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.CLOSE).show();
        }
    }

    public void updateDiscountOnAction(ActionEvent event) {
        try {
            if (!txtFoodName.getText().isEmpty() && !txtDiscountId.getText().isEmpty() && !txtDiscountPrice.getText().isEmpty() && !txtStartedDate.getText().isEmpty() && !txtClosedDate.getText().isEmpty()) {
                if (!lblDiscountId.getText().startsWith("*Invalid") && !lblDiscountPrice.getText().startsWith("*Invalid") && !lblStartedDate.getText().startsWith("*Invalid") && !lblClosedDate.getText().startsWith("*Invalid")) {
                    double preDiscountPrice = new DiscountController().getDiscountPrice(txtDiscountId.getText());
                    if (preDiscountPrice != 0.00) {
                        double disPrice = Double.parseDouble(txtDiscountPrice.getText()) - preDiscountPrice;
                        Discount discount = new Discount(txtDiscountId.getText(), Double.parseDouble(txtDiscountPrice.getText()), cmbFoodItem.getValue().getFoodId(), txtStartedDate.getText(), txtClosedDate.getText(), disPrice);
                        boolean isUpdated = new DiscountController().updateDiscount(discount);
                        if (isUpdated) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Discount Updated Successfully..!", ButtonType.OK).show();
                            loadTableData();
                            clearAll();
                            getLastDiscountId();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Can't update data..!", ButtonType.CLOSE).show();
                        }
                    }

                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid data fields try again..!", ButtonType.CLOSE).show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
            clearAll();
            getLastDiscountId();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.CLOSE).show();
        }
    }

    public void deleteDiscountOnAction(ActionEvent event) {
        try{
            if (!txtFoodName.getText().isEmpty() && !txtDiscountId.getText().isEmpty() && !txtDiscountPrice.getText().isEmpty() && !txtStartedDate.getText().isEmpty() && !txtClosedDate.getText().isEmpty()) {
                if (!lblDiscountId.getText().startsWith("*Invalid") && !lblDiscountPrice.getText().startsWith("*Invalid") && !lblStartedDate.getText().startsWith("*Invalid") && !lblClosedDate.getText().startsWith("*Invalid")) {
                    if(!tblDiscount.getSelectionModel().isEmpty()){

                        if(new DiscountController().deleteDiscount(txtDiscountId.getText(),cmbFoodItem.getValue().getFoodId(),Double.parseDouble(txtDiscountPrice.getText()))){

                            new Alert(Alert.AlertType.CONFIRMATION, "Discount deleted Successfully..!", ButtonType.OK).show();
                            loadTableData();
                            clearAll();
                            getLastDiscountId();

                        }else{
                            new Alert(Alert.AlertType.ERROR, "Can't delete data..!", ButtonType.CLOSE).show();
                        }

                    }else{
                        new Alert(Alert.AlertType.ERROR, "Table is Empty, try again..!", ButtonType.CLOSE).show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid data fields try again..!", ButtonType.CLOSE).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
            clearAll();
            getLastDiscountId();
        }catch (Exception e){
           new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.CLOSE).show();
        }
    }

    public void clearAllOnAction(ActionEvent event) {
        clearAll();
        getLastDiscountId();
    }

    public void clearAll() {
        cmbFoodItem.getSelectionModel().clearSelection();
        txtDiscountId.clear();
        txtDiscountId.setStyle("-fx-border-color: null");
        lblDiscountId.setText("");
        lblDiscountIdTick.setText("");

        txtDiscountPrice.clear();
        txtDiscountPrice.setStyle("-fx-border-color: null");
        lblDiscountPrice.setText("");
        lblDiscountPriceTick.setText("");

        txtFoodName.clear();

        txtStartedDate.clear();
        txtStartedDate.setStyle("-fx-border-color: null");
        lblStartedDate.setText("");
        lblStartDateTick.setText("");

        txtClosedDate.clear();
        txtClosedDate.setStyle("-fx-border-color: null");
        lblClosedDate.setText("");
        lblClosedDateTick.setText("");

        btnAddDiscount.setDisable(true);
        btnDeleteDiscount.setDisable(true);
        btnUpdateDiscount.setDisable(true);

        cmbFoodItem.setValue(null);
    }

    public void checkDiscountId(KeyEvent keyEvent) {
        String value = "^(D-){1}[0-9]{4}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtDiscountId.getText());
        if (matcher.matches()) {
            txtDiscountId.setStyle("-fx-border-width: 3");
            txtDiscountId.setStyle("-fx-border-color: #31b531");
            lblDiscountIdTick.setText("✔");
            lblDiscountId.setText(" ");

            lblDiscountPriceTick.setText("");
            lblStartDateTick.setText("");
            lblClosedDateTick.setText("");

            txtDiscountPrice.setStyle("-fx-border-color: null");
            txtStartedDate.setStyle("-fx-border-color: null");
            txtClosedDate.setStyle("-fx-border-color: null");

            btnAddDiscount.setDisable(false);
            btnUpdateDiscount.setDisable(false);

            if (lblDiscountPrice.getText().startsWith("*Invalid")  || txtDiscountPrice.getText().length()==0) {
                txtDiscountPrice.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblStartedDate.getText().startsWith("*Invalid")  || txtStartedDate.getText().length()==0) {
                txtStartedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblClosedDate.getText().startsWith("*Invalid") || txtClosedDate.getText().length()==0) {
                txtClosedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }

            //checkAllFields();
           // btnAddDiscount.setDisable(false);


        } else {
            txtDiscountId.setStyle("-fx-border-width: 3");
            txtDiscountId.setStyle("-fx-border-color: red");
            lblDiscountId.setText("*Invalid discount Id");
            lblDiscountIdTick.setText("");

          //checkAllFields();
           // btnAddDiscount.setDisable(true);

            lblDiscountPriceTick.setText("");
            lblStartDateTick.setText("");
            lblClosedDateTick.setText("");

            txtDiscountPrice.setStyle("-fx-border-color: null");
            txtStartedDate.setStyle("-fx-border-color: null");
            txtClosedDate.setStyle("-fx-border-color: null");

            btnAddDiscount.setDisable(true);
            btnUpdateDiscount.setDisable(true);

            if (lblDiscountPrice.getText().startsWith("*Invalid")  || txtDiscountPrice.getText().length()==0) {
                txtDiscountPrice.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblStartedDate.getText().startsWith("*Invalid")  || txtStartedDate.getText().length()==0) {
                txtStartedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblClosedDate.getText().startsWith("*Invalid") || txtClosedDate.getText().length()==0) {
                txtClosedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }

        }
    }

    public void checkDiscountPrice(KeyEvent keyEvent) {
        String value = "^[1-9]{1}[0-9]{0,5}([.][0-9]{2})?$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtDiscountPrice.getText());
        if (matcher.matches()) {
            txtDiscountPrice.setStyle("-fx-border-width: 3");
            txtDiscountPrice.setStyle("-fx-border-color: #31b531");
            lblDiscountPriceTick.setText("✔");
            lblDiscountPrice.setText(" ");

            lblDiscountIdTick.setText("");
            lblStartDateTick.setText("");
            lblClosedDateTick.setText("");

            //checkAllFields();
            //btnAddDiscount.setDisable(false);

            txtDiscountId.setStyle("-fx-border-color: null");
            txtStartedDate.setStyle("-fx-border-color: null");
            txtClosedDate.setStyle("-fx-border-color: null");

            btnAddDiscount.setDisable(false);
            btnUpdateDiscount.setDisable(false);

            if (lblDiscountId.getText().startsWith("*Invalid") || txtDiscountId.getText().length()==0) {
                txtDiscountId.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblStartedDate.getText().startsWith("*Invalid") || txtStartedDate.getText().length()==0) {
                txtStartedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblClosedDate.getText().startsWith("*Invalid") || txtClosedDate.getText().length()==0) {
                txtClosedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
        } else {
            txtDiscountPrice.setStyle("-fx-border-width: 3");
            txtDiscountPrice.setStyle("-fx-border-color: red");
            //txtDiscountId.setPromptText("*Invalid discount Id");
            //txtDiscountId.setStyle("-fx-prompt-text-fill: red");
            lblDiscountPrice.setText("*Invalid discount Price");
            lblDiscountPriceTick.setText("");

            lblDiscountIdTick.setText("");
            lblStartDateTick.setText("");
            lblClosedDateTick.setText("");

           //checkAllFields();
           // btnAddDiscount.setDisable(true);

            txtDiscountId.setStyle("-fx-border-color: null");
            txtStartedDate.setStyle("-fx-border-color: null");
            txtClosedDate.setStyle("-fx-border-color: null");

            btnAddDiscount.setDisable(true);
            btnUpdateDiscount.setDisable(true);

            if (lblDiscountId.getText().startsWith("*Invalid") || txtDiscountId.getText().length()==0) {
                txtDiscountId.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblStartedDate.getText().startsWith("*Invalid") || txtStartedDate.getText().length()==0) {
                txtStartedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblClosedDate.getText().startsWith("*Invalid") || txtClosedDate.getText().length()==0) {
                txtClosedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
        }
    }
    public void checkStartedDate(KeyEvent keyEvent) {
        String value = "^(20)[0-9]{2}(-|/)[0-9]{1,2}(-|/)[0-9]{2}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtStartedDate.getText());
        if (matcher.matches()) {
            txtStartedDate.setStyle("-fx-border-width: 3");
            txtStartedDate.setStyle("-fx-border-color: #31b531");
            lblStartDateTick.setText("✔");
            lblStartedDate.setText(" ");

            lblDiscountIdTick.setText("");
            lblDiscountPriceTick.setText("");
            lblClosedDateTick.setText("");

           //checkAllFields();
           // btnAddDiscount.setDisable(false);

            txtDiscountId.setStyle("-fx-border-color: null");
            txtDiscountPrice.setStyle("-fx-border-color: null");
            txtClosedDate.setStyle("-fx-border-color: null");

            btnAddDiscount.setDisable(false);
            btnUpdateDiscount.setDisable(false);

            if (lblDiscountId.getText().startsWith("*Invalid")|| txtDiscountId.getText().length()==0) {
                txtDiscountId.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblDiscountPrice.getText().startsWith("*Invalid")|| txtDiscountPrice.getText().length()==0) {
                txtDiscountPrice.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblClosedDate.getText().startsWith("*Invalid")|| txtClosedDate.getText().length()==0) {
                txtClosedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
        } else {
            txtStartedDate.setStyle("-fx-border-width: 3");
            txtStartedDate.setStyle("-fx-border-color: red");
            //txtDiscountId.setPromptText("*Invalid discount Id");
            //txtDiscountId.setStyle("-fx-prompt-text-fill: red");
            lblStartedDate.setText("*Invalid Date type");
            lblStartDateTick.setText("");

            lblDiscountIdTick.setText("");
            lblDiscountPriceTick.setText("");
            lblClosedDateTick.setText("");

           //checkAllFields();
           // btnAddDiscount.setDisable(true);

            txtDiscountId.setStyle("-fx-border-color: null");
            txtDiscountPrice.setStyle("-fx-border-color: null");
            txtClosedDate.setStyle("-fx-border-color: null");

            btnAddDiscount.setDisable(true);
            btnUpdateDiscount.setDisable(true);

            if (lblDiscountId.getText().startsWith("*Invalid")|| txtDiscountId.getText().length()==0) {
                txtDiscountId.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblDiscountPrice.getText().startsWith("*Invalid")|| txtDiscountPrice.getText().length()==0) {
                txtDiscountPrice.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblClosedDate.getText().startsWith("*Invalid")|| txtClosedDate.getText().length()==0) {
                txtClosedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
        }

    }

    public void checkClosedDate(KeyEvent keyEvent) {
        String value = "^(20)[0-9]{2}(-|/)[0-9]{1,2}(-|/)[0-9]{2}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtClosedDate.getText());
        if (matcher.matches()) {
            txtClosedDate.setStyle("-fx-border-width: 3");
            txtClosedDate.setStyle("-fx-border-color: #31b531");
            lblClosedDateTick.setText("✔");
            lblClosedDate.setText(" ");

            txtClosedDate.setStyle("-fx-border-color: null");
            lblClosedDateTick.setText("");

            lblDiscountIdTick.setText("");
            lblDiscountPriceTick.setText("");
            lblStartDateTick.setText("");

            //checkAllFields();
            //btnAddDiscount.setDisable(false);

            txtDiscountId.setStyle("-fx-border-color: null");
            txtDiscountPrice.setStyle("-fx-border-color: null");
            txtStartedDate.setStyle("-fx-border-color: null");

            btnAddDiscount.setDisable(false);
            btnUpdateDiscount.setDisable(false);

            if (lblDiscountId.getText().startsWith("*Invalid")|| txtDiscountId.getText().length()==0) {
                txtDiscountId.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblDiscountPrice.getText().startsWith("*Invalid")|| txtDiscountPrice.getText().length()==0) {
                txtDiscountPrice.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblStartedDate.getText().startsWith("*Invalid")|| txtStartedDate.getText().length()==0) {
                txtStartedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
        } else {
            txtClosedDate.setStyle("-fx-border-width: 3");
            txtClosedDate.setStyle("-fx-border-color: red");
            //txtDiscountId.setPromptText("*Invalid discount Id");
            //txtDiscountId.setStyle("-fx-prompt-text-fill: red");
            lblClosedDate.setText("*Invalid Date type");
            lblClosedDateTick.setText("");

            lblDiscountIdTick.setText("");
            lblDiscountPriceTick.setText("");
            lblStartDateTick.setText("");

            //checkAllFields();
            //btnAddDiscount.setDisable(true);

            txtDiscountId.setStyle("-fx-border-color: null");
            txtDiscountPrice.setStyle("-fx-border-color: null");
            txtStartedDate.setStyle("-fx-border-color: null");

            btnAddDiscount.setDisable(true);
            btnUpdateDiscount.setDisable(true);

            if (lblDiscountId.getText().startsWith("*Invalid")|| txtDiscountId.getText().length()==0) {
                txtDiscountId.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblDiscountPrice.getText().startsWith("*Invalid")|| txtDiscountPrice.getText().length()==0) {
                txtDiscountPrice.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
            if (lblStartedDate.getText().startsWith("*Invalid")|| txtStartedDate.getText().length()==0) {
                txtStartedDate.setStyle("-fx-border-color: red");
                btnAddDiscount.setDisable(true);
                btnUpdateDiscount.setDisable(true);
            }
        }

    }

    /*public void checkAllFields() {
        btnAddDiscount.setDisable(txtFoodName.getText().isEmpty() || txtDiscountId.getStyle() != "-fx-border-color: null" || txtDiscountPrice.getStyle() != "-fx-border-color: null" || txtStartedDate.getStyle() != "-fx-border-color: null" || txtClosedDate.getStyle() != "-fx-border-color: null");

        *//*if(!txtFoodName.getText().isEmpty() && txtDiscountId.getStyle()!="-fx-border-color: red" && txtDiscountPrice.getStyle()!="-fx-border-color: red" && txtStartedDate.getStyle()!="-fx-border-color: red" && txtClosedDate.getStyle()!="-fx-border-color: red" || txtDiscountId.getStyle()!="-fx-border-color: #31b531" || txtDiscountPrice.getStyle()!="-fx-border-color: #31b531" || txtStartedDate.getStyle()!="-fx-border-color: #31b531" || txtClosedDate.getStyle()!="-fx-border-color: #31b531"){
            btnAddDiscount.setDisable(true);
        }else{
            btnAddDiscount.setDisable(false);
        }*//*


        *//*if(!lblDiscountId.getText().isEmpty()   && lblDiscountPrice.getText().isEmpty()  && !lblStartedDate.getText().isEmpty()  &&  !lblClosedDate.getText().isEmpty()) {
            System.out.println("ashan");
            if (!txtFoodName.getText().isEmpty() && !lblDiscountId.getText().startsWith("Invalid") && !lblDiscountPrice.getText().startsWith("Invalid") && !lblStartedDate.getText().startsWith("Invalid") && !lblClosedDate.getText().startsWith("Invalid")) {
                btnAddDiscount.setDisable(false);
                System.out.println("ssdsdsd");
            }
        }*//*

    }*/

    public void txtDiscountIdOnAction(ActionEvent event) {
        txtDiscountPrice.requestFocus();
    }

    public void txtDiscountPriceOnAction(ActionEvent event) {
        txtStartedDate.requestFocus();
    }

    public void txtStartedDateOnAction(ActionEvent event) {
        txtClosedDate.requestFocus();
    }

    public void txtClosedDateOnAction(ActionEvent event) {

    }
}
