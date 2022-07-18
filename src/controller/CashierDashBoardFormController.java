package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import model.*;
import model.Package;
import org.controlsfx.control.PropertySheet;
import sun.invoke.empty.Empty;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CashierDashBoardFormController {

    public AnchorPane context;
    public Text lblOrderId;
    public Text lblCashierName;
    public Text lblDate;
    public Text lblTime;
    public ComboBox<String> cmbFoodType;
    public AnchorPane foodTypeContext;
    public TextField txtMobileNumber;
    public TextField txtCusId;
    public TextField txtCusName;
    public TextField txtCusAddress;
    public ComboBox<String> cmbOrderType;
    public ComboBox<Employee> cmbDriver;
    public TableView<CartTM> tblCart;
    public TableColumn colFoodId;
    public TableColumn colFoodDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotalCost;
    public Label lblTotalCost;
    public ComboBox<Food> cmbFood;
    public TextField txtFoodDescription;
    public TextField txtFoodQtyOnHand;
    public TextField txtFoodUnitPrice;
    public TextField txtQuantity;
    public Text lblSubTotal;
    public Text lblDeliveryCharges;
    public Text lblGrandTotal;
    public Text lblBalance;
    public Label lblNumber;
    public Label lblCustomerId;
    public Label lblCustomerName;
    public Label lblCusAddress;
    public JFXButton btnAddCustomer;
    public JFXTextField txtDiscountPrice;
    public TextField txtCash;
    public Label lblFoodDescription;
    public Label lblQtyOnHand;
    public Label lblUnitPrice;
    public Label lblDiscountedPrice;
    public Label lblQuantity;
    public Label lblCash;
    public JFXButton btnAddItem;
    public JFXButton btnCancelOrder;
    public JFXButton btnPlaceOrder;
    public Label lblCash1;
    public JFXButton btnRemoveItem;
    public JFXButton btnSearchCustomer;

    ObservableList<CartTM> tmList = FXCollections.observableArrayList();

    ArrayList<CartTM> copyOftmList = new ArrayList<>();

    LinkedHashMap<TextField,Pattern> map = new LinkedHashMap<>();

    public void lastCustomerId(){
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
                txtCusId.setText(finalId);
            } else {
                txtCusId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void lastOrderId(){
        try {
            String orderId = new OrderCrudController().getLastOrderId();
            String finalId = "OI-00001";

            if (orderId != null) {

                String[] splitString = orderId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "OI-0000" + id;
                } else if (id < 100) {
                    finalId = "OI-000" + id;
                } else if(id<1000) {
                    finalId = "OI-00" + id;
                }else if(id<10000) {
                    finalId = "OI-0" + id;
                }else{
                    finalId = "OI-" + id;
                }
                lblOrderId.setText(finalId);
            } else {
                lblOrderId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String lastTransactionId(){
        try {
            String transactionId = new TransactionController().getLastTransactionId();
            String finalId = "T-00001";

            if (transactionId != null) {

                String[] splitString = transactionId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "T-0000" + id;
                } else if (id < 100) {
                    finalId = "T-000" + id;
                } else if (id < 1000){
                    finalId = "T-00" + id;
                }else if(id<10000){
                    finalId = "T-0" + id;
                }else{
                    finalId = "T-" + id;
                }
                return finalId;
            } else {
                return finalId;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initialize(){
        btnRemoveItem.setDisable(true);
        btnSearchCustomer.setDisable(true);

        Pattern QuantityPattern = Pattern.compile("^[1-9][0-9]{0,3}$");

        map.put(txtQuantity,QuantityPattern);

        lblQuantity.setText("*This field is required");
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colFoodDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        lastOrderId();
        lastCustomerId();
        btnAddCustomer.setDisable(true);
        btnAddItem.setDisable(true);
        txtCash.setDisable(true);

        cmbFoodType.getItems().addAll("Meal","Pizza","Burger","Drink","Package");
        cmbOrderType.getItems().addAll("Dine-in","Take-away","Delivery");
        try{
            cmbDriver.setItems(new EmployeeController().getDrivers());

        }catch (SQLException | ClassNotFoundException e){

        }

        cmbOrderType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                if(newValue.equals("Delivery")){
                    cmbDriver.setDisable(false);
                }else{
                    cmbDriver.getSelectionModel().clearSelection();
                    cmbDriver.setDisable(true);
                }
            }
        });

        cmbFoodType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtQuantity.setText(null);
            btnAddItem.setDisable(true);
            txtQuantity.setStyle("-fx-border-color: null");
            lblQuantity.setText(null);

            txtFoodDescription.clear();
            txtFoodQtyOnHand.clear();
            txtFoodUnitPrice.clear();
            txtDiscountPrice.clear();
            try{
                if(newValue!=null){
                    if(newValue.equals("Meal")){
                        txtFoodQtyOnHand.setDisable(false);
                        txtQuantity.setDisable(false);
                        txtDiscountPrice.setDisable(false);

                        cmbFood.setItems(new ItemCrudController().getMeals("Meal"));
                    }else if(newValue.equals("Pizza")){
                        txtFoodQtyOnHand.setDisable(false);
                        txtQuantity.setDisable(false);
                        txtDiscountPrice.setDisable(false);

                        cmbFood.setItems(new ItemCrudController().getPizza("Pizza"));
                    }else if(newValue.equals("Burger")){
                        txtFoodQtyOnHand.setDisable(false);
                        txtQuantity.setDisable(false);
                        txtDiscountPrice.setDisable(false);

                        cmbFood.setItems(new ItemCrudController().getBurgers("Burger"));
                    }else if(newValue.equals("Drink")){
                        txtFoodQtyOnHand.setDisable(false);
                        txtQuantity.setDisable(false);
                        txtDiscountPrice.setDisable(false);

                        cmbFood.setItems(new ItemCrudController().getDrinks("Drink"));
                    }else{
                        cmbFood.setItems(new ItemCrudController().getPackages());
                        txtFoodQtyOnHand.setText("1");
                        txtQuantity.setText("1");
                        txtFoodQtyOnHand.setDisable(true);
                        txtQuantity.setDisable(true);
                        txtDiscountPrice.setText("0");
                        txtDiscountPrice.setDisable(true);
                    }
                }

            }catch (Exception e){

            }
        });

        lblCashierName.setText(LoginFormController.cashierName);
        loadDateAndTime();

        cmbFood.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                txtQuantity.setText(null);
                btnAddItem.setDisable(true);
                txtQuantity.setStyle("-fx-border-color: null");
                lblQuantity.setText(null);
                if(cmbFoodType.getValue()=="Package"){
                    txtQuantity.setText("1");
                    btnAddItem.setDisable(false);
                }
                txtFoodDescription.setText(newValue.getDescription());
                txtFoodQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
                if(cmbFoodType.getValue()=="Package"){
                    txtFoodQtyOnHand.setText("1");
                    //btnAddItem.setDisable(false);
                }
                txtFoodUnitPrice.setText(String.valueOf(String.format("%.2f", newValue.getUnitPrice())));
                try{
                    txtDiscountPrice.setText(String.valueOf(String.format("%.2f",new ItemCrudController().getDiscountedPrice(cmbFood.getValue().getFoodId()))));
                }catch (SQLException | ClassNotFoundException e){

                }
            }
        });
    }

    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, (e) -> {
            LocalTime currentTime = LocalTime.now();
            int hour = currentTime.getHour();
            if (hour >= 12) {
                lblTime.setText(currentTime.getHour() + ":" +
                        currentTime.getMinute() + ":" +
                        currentTime.getSecond() + " PM");
            } else {
                lblTime.setText(currentTime.getHour() + ":" +
                        currentTime.getMinute() + ":" +
                        currentTime.getSecond() + " AM");
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void logOutOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.show();
    }

    public void manageOrdersOnAction(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Manage Orders");
        stage.initOwner((Stage)context.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageOrdersForm.fxml"))));
        stage.show();
    }

    public void reservationOnAction(ActionEvent event) throws IOException {
        Stage stage=new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Manage Reservation");
        stage.initOwner((Stage)context.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ReservationForm.fxml"))));
        stage.show();
    }

    public void addNewCustomerOnAction(ActionEvent event) {
        try{
            if(!txtCusId.getText().isEmpty() && !txtCusName.getText().isEmpty() && !txtMobileNumber.getText().isEmpty() && !txtCusAddress.getText().isEmpty()) {
                if (!lblCustomerId.getText().startsWith("*Invalid") && !lblCustomerName.getText().startsWith("*Invalid") && !lblNumber.getText().startsWith("*Invalid") && !lblCusAddress.getText().startsWith("*Invalid")) {
                    boolean customerIsSaved = new CustomerCrudController().addCustomer(new Customer(txtCusId.getText(),txtCusName.getText(),txtCusAddress.getText(),txtMobileNumber.getText()));
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

    public void mobileNumberSearchOnAction(ActionEvent event) {
        try {
            if (!txtMobileNumber.getText().isEmpty()) {
                if (!lblNumber.getText().startsWith("*Invalid")) {
                    Customer c1 = new CustomerCrudController().getCustomerWithMobileNumber(txtMobileNumber.getText());
                    if (c1 != null) {
                        txtCusId.setText(c1.getCustomerId());
                        txtCusName.setText(c1.getName());
                        txtCusAddress.setText(c1.getAddress());
                        txtMobileNumber.setText(c1.getContactNumber());

                        txtMobileNumber.setStyle("-fx-border-color: null");
                        txtCusId.setStyle("-fx-border-color: null");
                        txtCusAddress.setStyle("-fx-border-color: null");
                        txtCusName.setStyle("-fx-border-color: null");

                        lblCustomerId.setText("");
                        lblCustomerName.setText("");
                        lblCusAddress.setText("");

                        btnAddCustomer.setDisable(true);

                    } else {
                        String number = txtMobileNumber.getText();
//                        clearAll();
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR, "No customer exists for this Mobile Number..! Do you want to add this customer",ButtonType.YES,ButtonType.NO).showAndWait();
                        if(buttonType.get().equals(ButtonType.YES)){
                            /*btnAddCustomer.setDisable(true);*/
                        }else{
                            clearAll();
                        }
                        lastCustomerId();
                    }
                } else {
                    clearAll();
                    lastCustomerId();
                    btnAddCustomer.setDisable(true);
                    new Alert(Alert.AlertType.ERROR, "Invalid Mobile Number..!").show();
                }
            } else {
                clearAll();
                lastCustomerId();
                btnAddCustomer.setDisable(true);
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void txtMobileNumberSearchOnAction(ActionEvent event) {
        try {
            if (!txtMobileNumber.getText().isEmpty()) {
                if (!lblNumber.getText().startsWith("*Invalid")) {
                    Customer c1 = new CustomerCrudController().getCustomerWithMobileNumber(txtMobileNumber.getText());
                    if (c1 != null) {
                        txtCusId.setText(c1.getCustomerId());
                        txtCusName.setText(c1.getName());
                        txtCusAddress.setText(c1.getAddress());
                        txtMobileNumber.setText(c1.getContactNumber());

                        lblCustomerId.setText("");
                        lblCustomerName.setText("");
                        lblCusAddress.setText("");

                        btnAddCustomer.setDisable(true);
                    } else {
//                        clearAll();
                        lastCustomerId();
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.ERROR, "No customer exists for this Mobile Number..! Do you want to add this customer",ButtonType.YES,ButtonType.NO).showAndWait();
                        if(buttonType.get().equals(ButtonType.YES)){
                            /*btnAddCustomer.setDisable(true);*/
                        }else{
                            clearAll();
                        }
                        lastCustomerId();
                    }
                } else {
                    clearAll();
                    lastCustomerId();
                    btnAddCustomer.setDisable(true);
                    new Alert(Alert.AlertType.ERROR, "Invalid Mobile Number..!").show();
                }
            } else {
                clearAll();
                lastCustomerId();
                btnAddCustomer.setDisable(true);
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void clearAll() {
        txtMobileNumber.clear();
        txtCusId.clear();
        txtCusAddress.clear();
        txtCusName.clear();

        txtMobileNumber.setStyle("-fx-border-color: null");
        txtCusId.setStyle("-fx-border-color: null");
        txtCusAddress.setStyle("-fx-border-color: null");
        txtCusName.setStyle("-fx-border-color: null");

        lblCustomerId.setText("");
        lblCustomerName.setText("");
        lblNumber.setText("");
        lblCusAddress.setText("");

        btnAddCustomer.setDisable(true);
    }

    public void removeItemOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(!tblCart.getItems().isEmpty()){
            CartTM tm = tblCart.getSelectionModel().getSelectedItem();
            if(tm != null) {
                /*String foodId=tm.getFoodId();
                int i=packageArray.size()-1;
                ArrayList<PackageDetail> arrayList = null;
                while(i!=-1){
                    System.out.println(packageArray.size());
                    System.out.println(packageArray.get(i));
                    arrayList = new ItemCrudController().getPackageItems(packageArray.get(i), "");
                    for(int j=0; j<arrayList.size(); j++){
                        if (foodId.equals(arrayList.get(j).getFoodId())) {
                            packageArray.remove(packageArray.get(i));

                            //i--;
                            break;
                        }
                    }
                  i--;
                }

                System.out.println("PkgDetail");
                for(String detail1 : packageArray){
                    System.out.println(detail1);
                }*/
                for(CartTM cartTM : tmList){
                    if(cartTM.getFoodId().equals(tm.getFoodId())){
                        tmList.remove(cartTM);
                        break;
                    }
                }
                tblCart.setItems(tmList);
                tblCart.refresh();
                calculateTotal();

                //2022-03-25
                btnRemoveItem.setDisable(true);

            }else{
                new Alert(Alert.AlertType.ERROR,"Select Item from table..!").show();
            }

        }else{
            btnRemoveItem.setDisable(true);
            new Alert(Alert.AlertType.ERROR,"Table is empty therefore,can't delete data from table").show();
        }
    }

    public void clearTableOnAction(ActionEvent event) {
        tblCart.getItems().clear();
        tmList.clear();
        packageArray.clear();
        calculateTotal();
    }

    public ArrayList<String> packageArray = new ArrayList<>();
    public ArrayList<PackageDetail> pkgDetail = new ArrayList<>();
    public ArrayList<ArrayList<PackageDetail>> packageArrayList = new ArrayList<>();

    public void addItemOnAction(ActionEvent event) {
        try {
            if (cmbFoodType.getValue() == "Package") {   //cmbFoodItem.getValue().getQtyOnHand() > 0
                try {
                    String packageId = cmbFood.getValue().getFoodId();
                    ArrayList<PackageDetailTM> arrayList = new ItemCrudController().getPackageItems(packageId);
                    //packageArray.add(new Package(packageId,txtFoodDescription.getText(),Double.parseDouble(txtFoodUnitPrice.getText()),new ItemCrudController().getPackageItems(packageId,"")));

                    packageArray.add(packageId);

                    /* This Place is edited 2022-03-16 Below Code*/
                    packageArrayList.add(new ItemCrudController().getPackage(packageId));

                    /* This Place is edited 2022-03-16 Below Code*/
                    /*double unitPrice = Double.parseDouble(txtFoodUnitPrice.getText());
                    int qty = Integer.parseInt(txtQuantity.getText());
                    double totalCost = unitPrice * qty;*/
                    /* ============================================ */

                    for (PackageDetailTM tm : arrayList) {
                        if(new ItemCrudController().getQtyOfFoodId(tm.getFoodId()) > 0) {
                                if(new ItemCrudController().getQtyOfFoodId(tm.getFoodId()) >= tm.getQty()){
                            double totalCost = tm.getUniPrice() * tm.getQty();
                            //String foodType = null;
                            CartTM isExist = isExists(tm.getFoodId());
                            if (isExist != null) {
                                isExist.setQty(isExist.getQty() + tm.getQty());
                                isExist.setTotalCost(isExist.getTotalCost() + totalCost);
                            } else {
                                tmList.add(new CartTM(
                                        tm.getFoodId(),

                                    /*// Methana food type eka package kyla ganne decision ekk ganna eka hodaida kyla..>!
                                    cmbFoodType.getValue(),*/
                                        new ItemCrudController().getFoodType(tm.getFoodId()),
                                        tm.getFoodDescription(),
                                        tm.getUniPrice(),
                                        tm.getQty(),
                                        totalCost
                                ));
                            }
                            tblCart.setItems(tmList);
                            tblCart.refresh();
                            totalCost = 0;
                        }else{
                                    return;
                                }
                        }else{
                            return;
                        }
                    }
                    calculateTotal();


                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                if(cmbFood.getValue().getQtyOnHand() > 0){
                double unitPrice = Double.parseDouble(txtFoodUnitPrice.getText());
                int qty = Integer.parseInt(txtQuantity.getText());
                double totalCost = unitPrice * qty;

                CartTM isExist = isExists(cmbFood.getValue().getFoodId());

                if (isExist != null) {
                    isExist.setQty(isExist.getQty() + qty);
                    isExist.setTotalCost(isExist.getTotalCost() + totalCost);

                    /* This Place is edited 2022-03-16 Below Code*/
                    /*copyOftmList.add(new CartTM(
                            cmbFood.getValue().getFoodId(),
                            cmbFoodType.getValue(),
                            txtFoodDescription.getText(),
                            unitPrice,
                            qty,
                            totalCost
                    ));*/
                    /* =========================================== */

                } else {
                    CartTM tm = new CartTM(
                            cmbFood.getValue().getFoodId(),
                            cmbFoodType.getValue(),
                            txtFoodDescription.getText(),
                            unitPrice,
                            qty,
                            totalCost
                    );
                    tmList.add(tm);
                }
                tblCart.setItems(tmList);
                tblCart.refresh();
                calculateTotal();
            }else{
                    new Alert(Alert.AlertType.ERROR, "This item has zero quantity," +
                            " Therefore Choose another item..!", ButtonType.CLOSE).show();
                }
        }

            txtQuantity.setText(null);
            btnAddItem.setDisable(true);

            if(cmbFoodType.getValue().equals("Package")){
                btnAddItem.setDisable(false);
            }

        }catch (Exception e){
           e.printStackTrace();
        }
    }

    private CartTM isExists(String foodId) {
        for (CartTM tm : tmList) {
            if (tm.getFoodId().equals(foodId)) {
                return tm;
            }
        }
        return null;
    }

    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : tmList) {
            total += tm.getTotalCost();
        }
        lblTotalCost.setText(String.valueOf(total));

        double subTot = 0;
        double deliveryCharges = 0;
        double grandTotal = 0;
        subTot = total;
        if (cmbOrderType.getValue() == "Delivery") {
            deliveryCharges = subTot * 2 / 100;   /* 5 */
        }
        grandTotal = subTot + deliveryCharges;
        lblSubTotal.setText(String.format("%.2f", subTot));
        lblDeliveryCharges.setText(String.format("%.2f", deliveryCharges));
        lblGrandTotal.setText(String.format("%.2f", grandTotal));

    }

    public void cancelOrderOnAction(ActionEvent event) {
        clearAll();
        btnPlaceOrder.setDisable(false);
        btnCancelOrder.setText("Cancel Order");
        btnCancelOrder.setStyle("-fx-background-color:  #e84118");
        txtCash.clear();
        txtCash.setDisable(true);
        lblBalance.setText(null);
        packageArray.clear();
        cmbOrderType.getSelectionModel().clearSelection();
        cmbDriver.getSelectionModel().clearSelection();
        clearTableOnAction(null);
        cmbFoodType.getSelectionModel().clearSelection();
        cmbFood.setItems(null);
        txtFoodDescription.setText(null);
        txtFoodQtyOnHand.setText(null);
        txtFoodUnitPrice.setText(null);
        txtDiscountPrice.setText(null);
        txtQuantity.setText(null);
        txtFoodDescription.setStyle("-fx-border-color: null");
        txtFoodQtyOnHand.setStyle("-fx-border-color: null");
        txtQuantity.setStyle("-fx-border-color: null");
        txtFoodUnitPrice.setStyle("-fx-border-color: null");
        txtDiscountPrice.setStyle("-fx-border-color: null");
        lastOrderId();
        calculateTotal();
    }

    public void clearOnAction(ActionEvent event) {
        cmbFoodType.getSelectionModel().clearSelection();
        cmbFood.setItems(null);
        txtFoodDescription.clear();
        txtFoodQtyOnHand.clear();
        txtFoodUnitPrice.clear();
        txtQuantity.clear();
        txtDiscountPrice.clear();
        lblQuantity.setText("*This field is required");
   }

    public void checkMobileNumberOnAction(KeyEvent keyEvent) {
        String value = "^(0){1}[0-9]{9}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtMobileNumber.getText());
        if (matcher.matches()) {
            lblNumber.setText("");
            txtMobileNumber.setStyle("-fx-border-color: green");
            txtCusId.setStyle("-fx-border-color: null");
            txtCusName.setStyle("-fx-border-color: null");
            txtCusAddress.setStyle("-fx-border-color: null");

            btnSearchCustomer.setDisable(false);

            btnAddCustomer.setDisable(false);

            boolean isTrue=false;
            try{
                isTrue = new CustomerCrudController().customerIsExists(txtMobileNumber.getText());
            }catch (SQLException | ClassNotFoundException e){

            }
            if(isTrue){
                btnAddCustomer.setDisable(true);
            }

            /*boolean isTrue=false;
            try{
                isTrue = new CustomerCrudController().customerIsExists(txtCusId.getText());
            }catch (SQLException | ClassNotFoundException e){

            }
            if(!isTrue){
                btnAddCustomer.setDisable(false);
            }*/

            if (lblCustomerId.getText().startsWith("*Invalid") || txtCusId.getText().length()==0) {
                if(txtCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtCusId.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }
            if (lblCustomerName.getText().startsWith("*Invalid") || txtCusName.getText().length()==0) {
                if(txtCusName.getText().length()==0){
                    lblCustomerName.setText("*This field is required");
                }
                txtCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCustomerName.setText(" ");
            }
            if (lblCusAddress.getText().startsWith("*Invalid")|| txtCusAddress.getText().length()==0) {
                if(txtCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }
        } else {
            btnSearchCustomer.setDisable(true);
            lblNumber.setText("*Invalid Mobile Number");
            if(txtMobileNumber.getText().isEmpty()){
                lblNumber.setText("*This field is required");
            }
            txtMobileNumber.setStyle("-fx-border-color: red");
            txtCusId.setStyle("-fx-border-color: null");
            txtCusName.setStyle("-fx-border-color: null");
            txtCusAddress.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(true);

            if (lblCustomerId.getText().startsWith("*Invalid") || txtCusId.getText().length()==0) {
                if(txtCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtCusId.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }
            if (lblCustomerName.getText().startsWith("*Invalid") || txtCusName.getText().length()==0) {
                if(txtCusName.getText().length()==0){
                    lblCustomerName.setText("*This field is required");
                }
                txtCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCustomerName.setText(" ");
            }
            if (lblCusAddress.getText().startsWith("*Invalid")|| txtCusAddress.getText().length()==0) {
                if(txtCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }
        }
    }

    public void checkCustomerId(KeyEvent keyEvent) {
        String value = "^([C]{1}([0-9]{3,4}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtCusId.getText());
        if (matcher.matches()) {
            lblCustomerId.setText("");
            txtCusId.setStyle("-fx-border-color: green");
            txtCusName.setStyle("-fx-border-color: null");
            txtCusAddress.setStyle("-fx-border-color: null");
            txtMobileNumber.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(false);

            /*boolean isTrue=false;
            try{
                isTrue = new CustomerCrudController().customerIsExists(txtCusId.getText());
            }catch (SQLException | ClassNotFoundException e){

            }
            if(!isTrue){
                btnAddCustomer.setDisable(false);
            }*/

            if (lblCustomerName.getText().startsWith("*Invalid") || txtCusName.getText().length()==0) {
                if(txtCusName.getText().length()==0){
                    lblCustomerName.setText("*This field is required");
                }
                txtCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCustomerName.setText(" ");
            }
            if (lblCusAddress.getText().startsWith("*Invalid") || txtCusAddress.getText().length()==0) {
                if(txtCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }
            if (lblNumber.getText().startsWith("*Invalid")|| txtMobileNumber.getText().length()==0) {
                if(txtMobileNumber.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtMobileNumber.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }
        } else {
            lblCustomerId.setText("*Invalid Customer ID");
            if(txtCusId.getText().isEmpty()){
                lblCustomerId.setText("*This field is required");
            }
            txtCusId.setStyle("-fx-border-color: red");
            txtCusName.setStyle("-fx-border-color: null");
            txtCusAddress.setStyle("-fx-border-color: null");
            txtMobileNumber.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(true);

            if (lblCustomerName.getText().startsWith("*Invalid") || txtCusName.getText().length()==0) {
                if(txtCusName.getText().length()==0){
                    lblCustomerName.setText("*This field is required");
                }
                txtCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCustomerName.setText(" ");
            }
            if (lblCusAddress.getText().startsWith("*Invalid") || txtCusAddress.getText().length()==0) {
                if(txtCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }
            if (lblNumber.getText().startsWith("*Invalid")|| txtMobileNumber.getText().length()==0) {
                if(txtMobileNumber.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtMobileNumber.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }
        }
    }

    public void checkCustomerName(KeyEvent keyEvent) {
        String value = "^[A-z ]{3,40}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtCusName.getText());
        if (matcher.matches()) {
            lblCustomerName.setText("");
            txtCusName.setStyle("-fx-border-color: green");
            txtCusId.setStyle("-fx-border-color: null");
            txtCusAddress.setStyle("-fx-border-color: null");
            txtMobileNumber.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(false);

           /* boolean isTrue=false;
            try{
                isTrue = new CustomerCrudController().customerIsExists(txtCusId.getText());
            }catch (SQLException | ClassNotFoundException e){

            }
            if(!isTrue){
                btnAddCustomer.setDisable(false);
            }*/

            if (lblCustomerId.getText().startsWith("*Invalid") || txtCusId.getText().length()==0) {
                if(txtCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtCusId.setStyle("-fx-border-color: red");
               btnAddCustomer.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }
            if (lblCusAddress.getText().startsWith("*Invalid") || txtCusAddress.getText().length()==0) {
                if(txtCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }
            if (lblNumber.getText().startsWith("*Invalid")|| txtMobileNumber.getText().length()==0) {
                if(txtMobileNumber.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtMobileNumber.setStyle("-fx-border-color: red");
                 btnAddCustomer.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }
        } else {
            lblCustomerName.setText("*Invalid Customer Name");
            txtCusName.setStyle("-fx-border-color: red");
            if(txtCusName.getText().isEmpty()){
                lblCustomerName.setText("*This field is required");
            }
            txtCusId.setStyle("-fx-border-color: null");
            txtCusAddress.setStyle("-fx-border-color: null");
            txtMobileNumber.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(true);

            if (lblCustomerId.getText().startsWith("*Invalid") || txtCusId.getText().length()==0) {
                if(txtCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtCusId.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }
            if (lblCusAddress.getText().startsWith("*Invalid") || txtCusAddress.getText().length()==0) {
                if(txtCusAddress.getText().length()==0){
                    lblCusAddress.setText("*This field is required");
                }
                txtCusAddress.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCusAddress.setText(" ");
            }
            if (lblNumber.getText().startsWith("*Invalid")|| txtMobileNumber.getText().length()==0) {
                if(txtMobileNumber.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtMobileNumber.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }
        }
    }

    public void checkCustomerAddress(KeyEvent keyEvent) {
        String value = "^[A-z0-9 ,/]{4,50}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtCusAddress.getText());
        if (matcher.matches()) {
            lblCusAddress.setText("");
            txtCusAddress.setStyle("-fx-border-color: green");
            txtCusId.setStyle("-fx-border-color: null");
            txtCusName.setStyle("-fx-border-color: null");
            txtMobileNumber.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(false);

            /*boolean isTrue=false;
            try{
                isTrue = new CustomerCrudController().customerIsExists(txtCusId.getText());
            }catch (SQLException | ClassNotFoundException e){

            }
            if(!isTrue){
                btnAddCustomer.setDisable(false);
            }*/

            if (lblCustomerId.getText().startsWith("*Invalid") || txtCusId.getText().length()==0) {
                if(txtCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtCusId.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }
            if (lblCustomerName.getText().startsWith("*Invalid") || txtCusName.getText().length()==0) {
                if(txtCusName.getText().length()==0){
                    lblCustomerName.setText("*This field is required");
                }
                txtCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCustomerName.setText(" ");
            }
            if (lblNumber.getText().startsWith("*Invalid")|| txtMobileNumber.getText().length()==0) {
                if(txtMobileNumber.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtMobileNumber.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }
        } else {
            lblCusAddress.setText("*Invalid Customer Address");
            if(txtCusAddress.getText().isEmpty()){
                lblCusAddress.setText("*This field is required");
            }
            txtCusAddress.setStyle("-fx-border-color: red");
            txtCusId.setStyle("-fx-border-color: null");
            txtCusName.setStyle("-fx-border-color: null");
            txtMobileNumber.setStyle("-fx-border-color: null");

            btnAddCustomer.setDisable(true);

            if (lblCustomerId.getText().startsWith("*Invalid") || txtCusId.getText().length()==0) {
                if(txtCusId.getText().length()==0){
                    lblCustomerId.setText("*This field is required");
                }
                txtCusId.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCustomerId.setText(" ");
            }
            if (lblCustomerName.getText().startsWith("*Invalid") || txtCusName.getText().length()==0) {
                if(txtCusName.getText().length()==0){
                    lblCustomerName.setText("*This field is required");
                }
                txtCusName.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblCustomerName.setText(" ");
            }
            if (lblNumber.getText().startsWith("*Invalid")|| txtMobileNumber.getText().length()==0) {
                if(txtMobileNumber.getText().length()==0){
                    lblNumber.setText("*This field is required");
                }
                txtMobileNumber.setStyle("-fx-border-color: red");
                btnAddCustomer.setDisable(true);
            }else{
                lblNumber.setText(" ");
            }
        }
    }

    /*public boolean validate(LinkedHashMap<TextField, Pattern> map){
        try{
            for (TextField key : map.keySet()) {
                Pattern pattern = map.get(key);
                if (!pattern.matcher(key.getText()).matches()){
                    if(key==txtQuantity){
                        lblQuantity.setText("*Invalid Quantity");
                        txtQuantity.setStyle("-fx-border-color: RED");
                    }
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }*/

    public void checkQuantityOnAction(KeyEvent keyEvent) {
        /*boolean validate = validate(map);
        if (validate) {
            lblQuantity.setText("");
            txtQuantity.setStyle("-fx-border-color: null");
            if(!txtFoodDescription.getText().isEmpty() && !txtFoodQtyOnHand.getText().isEmpty() && !txtFoodUnitPrice.getText().isEmpty() && !txtDiscountPrice.getText().isEmpty()){
                btnAddItem.setDisable(false);
            }
        } else {
            btnAddItem.setDisable(true);
            try {
                if (txtQuantity.getText().isEmpty()) {
                    lblQuantity.setText("*This field is required");
                }
            } catch (Exception e) {
                lblQuantity.setText("*This field is required");
            }
        }*/
        try{
            Pattern compile = Pattern.compile("^[1-9][0-9]{0,3}$");
            Matcher matcher = compile.matcher(txtQuantity.getText());
            if(matcher.matches()){
                lblQuantity.setText(" ");
                txtQuantity.setStyle("-fx-border-color: GREEN");
                if(!txtFoodDescription.getText().isEmpty() && !txtFoodQtyOnHand.getText().isEmpty() && !txtFoodUnitPrice.getText().isEmpty() && !txtDiscountPrice.getText().isEmpty()){
                    btnAddItem.setDisable(false);
                }
            }else{
                lblQuantity.setText("*Invalid Quantity");
                if (txtQuantity.getText().isEmpty()) {
                    lblQuantity.setText("*This field is required");
                }
                btnAddItem.setDisable(true);
                txtQuantity.setStyle("-fx-border-color: RED");
            }
        } catch (Exception e){
            lblQuantity.setText("*This field is required");
        }
    }

        /*if(!txtFoodDescription.getText().isEmpty() && !txtFoodUnitPrice.getText().isEmpty() && !txtFoodQtyOnHand.getText().isEmpty() && !txtDiscountPrice.getText().isEmpty() && !txtQuantity.getText().isEmpty()){
            if(!lblFoodDescription.getText().startsWith("*Invalid") && !lblQtyOnHand.getText().startsWith("*Invalid") && !lblUnitPrice.getText().startsWith("*Invalid") && !lblDiscountedPrice.getText().startsWith("*Invalid") && !lblQuantity.getText().startsWith("*Invalid")){
                btnAddItem.setDisable(false);
            }
        }*/


    public void increaseQtyOnAction(ActionEvent event) {
        CartTM selectedItem = tblCart.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            //if (selectedItem.getTotalCost() != 0) {
                selectedItem.setQty(selectedItem.getQty() + 1);
                selectedItem.setTotalCost(selectedItem.getQty() * selectedItem.getUnitPrice());
                tblCart.refresh();
                calculateTotal();
            //}
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Select Item From The Table .....", ButtonType.CLOSE).show();
        }
    }

    public void decreaseQtyOnAction(ActionEvent event) {
        CartTM selectedItem = tblCart.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.getQty() > 1){
                //if (selectedItem.getTotalCost() != 0) {
                    selectedItem.setQty(selectedItem.getQty() - 1);
                    selectedItem.setTotalCost(selectedItem.getQty() * selectedItem.getUnitPrice());
                    tblCart.refresh();
                    calculateTotal();
                //}
            }else{
                new Alert(Alert.AlertType.ERROR,"Your selected Item's quantity is reached one,therefore,if you want to remove that item from the table, then use Remove item Option").show();
            }
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Select Item From The Table .....", ButtonType.CLOSE).show();
        }
    }

    public void placeOrderOnAction(ActionEvent event) {
        /*for(ArrayList<PackageDetail> pack : packageArrayList){
            for(PackageDetail detail : pack){
                System.out.println(detail.getPackageId());
            }
        }

        System.out.println("---------------------------------------");

        for(CartTM tm : copyOftmList){
            System.out.println(tm.getFoodId()+" "+tm.getQty());
        }

        packageArrayList  /  copyOftmList*/
        try{
            if(cmbOrderType.getValue()!=null && !txtMobileNumber.getText().isEmpty() && !txtCusName.getText().isEmpty() && !txtCusId.getText().isEmpty() && !txtCusAddress.getText().isEmpty() && !lblOrderId.getText().isEmpty() && !lblCashierName.getText().isEmpty() && !tmList.isEmpty()){
                if(cmbOrderType.getValue().equals("Delivery")) {
                    if(cmbDriver.getValue()!=null){
                    if (!lblNumber.getText().startsWith("*Invalid") && !lblCustomerId.getText().startsWith("*Invalid") && !lblCustomerName.getText().startsWith("*Invalid") && !lblCusAddress.getText().startsWith("*Invalid") && !tmList.isEmpty()) {
                        ArrayList<OrderDetail> detailArrayList = new ArrayList<>();
                        for (CartTM tm : tmList) {
                            detailArrayList.add(new OrderDetail(
                                    lblOrderId.getText(),
                                    tm.getFoodId(),
                                    tm.getFoodType(),
                                    tm.getDescription(),
                                    tm.getUnitPrice(),
                                    tm.getQty()
                            ));
                        }

                        Order order = new Order(lblOrderId.getText(), txtCusId.getText(), txtCusName.getText(), lblDate.getText(), lblTime.getText(), cmbOrderType.getValue(), Double.parseDouble(lblSubTotal.getText()), Double.parseDouble(lblDeliveryCharges.getText()), Double.parseDouble(lblGrandTotal.getText()), "Non-Paid", detailArrayList);
                        /*tmList=null;
                        tmList=FXCollections.observableArrayList();
                        tblCart.setItems(null);
                        tblCart.refresh();*/
                        boolean orderIsSaved = new OrderCrudController().placeOrder(order);
                        if (orderIsSaved) {
                            if (new DeliveryCrudController().addDelivery(lblOrderId.getText(), cmbDriver.getValue().getEmployeeID(), cmbDriver.getValue().getEmployeeName())) {
                                new ReportController().printKOT(tmList, lblOrderId.getText(), txtCusName.getText(), txtMobileNumber.getText(), cmbOrderType.getValue());
                                new Alert(Alert.AlertType.CONFIRMATION, "Order has been placed..!", ButtonType.OK).show();
                                btnCancelOrder.setText("Next Order");
                                btnCancelOrder.setStyle("-fx-background-color : #2980b9");
                                btnPlaceOrder.setDisable(true);
                                txtCash.setDisable(false);
                            } else {
                                new Alert(Alert.AlertType.CONFIRMATION, "Order Placement failed, try again..!", ButtonType.OK).show();
                            }
                        } else {
                            new Alert(Alert.AlertType.CONFIRMATION, "Order Placement failed, try again..!", ButtonType.OK).show();
                        }


                    } else {
                        new Alert(Alert.AlertType.CONFIRMATION, "Invalid data fields, try again..!", ButtonType.OK).show();
                    }
                }else{
                        new Alert(Alert.AlertType.CONFIRMATION, "Please select the driver & try again..!", ButtonType.OK).show();
                    }

                }else{
                        //
                   if(!lblNumber.getText().startsWith("*Invalid") && !lblCustomerId.getText().startsWith("*Invalid") && !lblCustomerName.getText().startsWith("*Invalid") && !lblCusAddress.getText().startsWith("*Invalid") &&  !tmList.isEmpty()){
                            ArrayList<OrderDetail> detailArrayList = new ArrayList<>();
                            for(CartTM tm : tmList){
                                detailArrayList.add(new OrderDetail(
                                        lblOrderId.getText(),
                                        tm.getFoodId(),
                                        tm.getFoodType(),
                                        tm.getDescription(),
                                        tm.getUnitPrice(),
                                        tm.getQty()
                                ));
                            }
                            Order order = new Order(lblOrderId.getText(),txtCusId.getText(),txtCusName.getText(),lblDate.getText(),lblTime.getText(),cmbOrderType.getValue(),Double.parseDouble(lblSubTotal.getText()),Double.parseDouble(lblDeliveryCharges.getText()),Double.parseDouble(lblGrandTotal.getText()),"Non-Paid",detailArrayList);
                            boolean orderIsSaved = new OrderCrudController().placeOrder(order);
                            if(orderIsSaved){
                                new ReportController().printKOT(tmList, lblOrderId.getText(), txtCusName.getText(), txtMobileNumber.getText(), cmbOrderType.getValue());
                                new Alert(Alert.AlertType.CONFIRMATION, "Order has been placed..!", ButtonType.OK).show();
                                btnCancelOrder.setText("Next Order");
                                btnCancelOrder.setStyle("-fx-background-color : #2980b9");
                                btnPlaceOrder.setDisable(true);
                                txtCash.setDisable(false);
                            }else{
                                new Alert(Alert.AlertType.CONFIRMATION, "Order Placement failed, try again..!", ButtonType.OK).show();
                            }
                        }else{
                            new Alert(Alert.AlertType.CONFIRMATION, "Invalid data fields, try again..!", ButtonType.OK).show();
                        }
                    }
            }else{
                new Alert(Alert.AlertType.CONFIRMATION, "Select Order type,Fill the customer data correctly & try again..!", ButtonType.OK).show();
            }

        }catch (SQLException | ClassNotFoundException | NullPointerException | NumberFormatException e){
            e.printStackTrace();
        }
    }




    public void commitsThePaymentOnAction(ActionEvent event) {
        if(!txtCash.getText().isEmpty() && !lblCash.getText().startsWith("*Invalid")){
            double totalAmount = Double.parseDouble(lblGrandTotal.getText());
            double paidAmount = Double.parseDouble(txtCash.getText());
            double balance = paidAmount - totalAmount;

            try{
                lblBalance.setText(String.format("%.2f", balance));
                Transaction transaction = new Transaction(
                        lastTransactionId(),
                        txtCusId.getText(),
                        lblOrderId.getText(),
                        new EmployeeController().getCashierId(lblCashierName.getText()),
                        totalAmount,
                        paidAmount,
                        balance
                );
                boolean transactionSucceeded = new TransactionController().commitsTheTransaction(transaction);
                if(transactionSucceeded){
                    if(new OrderCrudController().orderIsPaid(lblOrderId.getText())){
                        new ReportController().printBill(tmList, lblOrderId.getText(), txtCusName.getText(), txtMobileNumber.getText(), cmbOrderType.getValue(), Double.parseDouble(lblSubTotal.getText()), Double.parseDouble(lblDeliveryCharges.getText()), Double.parseDouble(lblGrandTotal.getText()), Double.parseDouble(txtCash.getText()), Double.parseDouble(lblBalance.getText()));
                        new Alert(Alert.AlertType.CONFIRMATION, "Payment has been paid..!", ButtonType.OK).show();
                        btnCancelOrder.setText("Next Order");
                        btnCancelOrder.setStyle("-fx-background-color : #2980b9");
                        txtCash.setStyle("-fx-border-color: null");
                        txtCash.setDisable(true);
                    }else{
                        txtCash.setStyle("-fx-border-color: RED");
                        new Alert(Alert.AlertType.CONFIRMATION, "Transaction failed..!", ButtonType.OK).show();
                    }
                }else{
                    txtCash.setStyle("-fx-border-color: RED");
                    new Alert(Alert.AlertType.CONFIRMATION, "Transaction failed..!, try again", ButtonType.OK).show();
                }


            }catch (SQLException | ClassNotFoundException e){
                e.printStackTrace();
            }

        }else{
            txtCash.setStyle("-fx-border-color: RED");
            new Alert(Alert.AlertType.CONFIRMATION, "Error,Invalid input cash by user..!", ButtonType.OK).show();
        }
        //lastOrderId();
    }

    public void checkCashOnAction(KeyEvent keyEvent) {
        String value = "^[1-9][0-9]*(.[0-9]{2})?$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtCash.getText());
        if (matcher.matches()) {
            if(Double.parseDouble(txtCash.getText()) >= Double.parseDouble(lblGrandTotal.getText())){
                lblCash.setText("");
                lblCash1.setText("");
                txtCash.setStyle("-fx-border-color: GREEN");
            }else{
                lblCash.setText("*Invalid,Cash should be");
                lblCash1.setText("larger than grand total");
                txtCash.setStyle("-fx-border-color: RED");
            }
            //txtCash.setDisable(false);

        } else {
            lblCash.setText("*Invalid Cash Amount");
            lblCash1.setText("");
            txtCash.setStyle("-fx-border-color: RED");
            //txtCash.setDisable(true);
        }
    }

    public void tblOnAction(MouseEvent mouseEvent) {
        btnRemoveItem.setDisable(false);
        if(tblCart.getItems().isEmpty()){
            btnRemoveItem.setDisable(true);
        }
    }
}
