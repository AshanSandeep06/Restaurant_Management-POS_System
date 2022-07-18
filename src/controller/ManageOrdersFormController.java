package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.*;
import net.sf.jasperreports.engine.JRException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageOrdersFormController {

    public Tab tabDineIn;
    public TextField txtSearchOrderId;
    public Text lblCustomerId;
    public TableView<CartTM> tblDineInAndTakeAway;
    public TableColumn colFoodId;
    public TableColumn colFoodDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQuantity;
    public TableColumn colTotalCost;
    /*public ComboBox<Food> cmbMeal;
    public ComboBox<Food> cmbBeverage;
    public ComboBox<Food> cmbPizza;
    public ComboBox<Food> cmbBurger;
    public ComboBox<Food> cmbPackage;*/
    public TextField txtQuantity;
    public JFXButton btnAddItem;
    public Text lblTotal;
    public ComboBox<String> cmbOrderType;
    public TextField txtCash;
    public Text lblBalance;
    public Text lblCustomerName;
    public TextField txtSearchDeliveryOrderId;
    public Text lblDeliveryCusName;
    public TableView<CartTM> tblDelivery;
    public TableColumn colDeFoodId;
    public TableColumn colDeFoodDescription;
    public TableColumn colDeFoodUnitPrice;
    public TableColumn colDeliveryQty;
    public TableColumn colDeliveryTotalCost;
    /*public ComboBox<Food> cmbDeMeal;
    public ComboBox<Food> cmbDeliveryBeverage;
    public ComboBox<Food> cmbDePizza;
    public ComboBox<Food> cmbDeBurger;
    public ComboBox<Food> cmbDePackage;*/
    public TextField txtDeQty;
    public JFXButton btnAddDelivery;
    public Text lblSubTotal;
    public Text lblGrandTotal;
    public Text lblDeliveryCharges;
    public ComboBox<Employee> cmbDriver;
    public TextArea txtAddress;
    public TextField txtDeliveryCash;
    public Text lblDeliveryBalance;
    public Label lblOrderIdValidation;

    public ObservableList<CartTM> dineTakeTmList = FXCollections.observableArrayList();
    public ObservableList<CartTM> deliveryTmList = FXCollections.observableArrayList();
    public Label lblQuantityValidate;
    public ComboBox<Food> cmbFood;
    public ComboBox<String> cmbFoodType;
    public JFXTextField txtFoodDescription;
    public JFXTextField txtFoodQtyOnHand;
    public JFXTextField txtFoodUnitPrice;
    public JFXTextField txtDiscountPrice;
    public ComboBox<Food> cmbDeliveryFood;
    public ComboBox<String> cmbDeliveryFoodType;
    public JFXTextField txtDeliveryDescription;
    public JFXTextField txtDeQtyOnHand;
    public JFXTextField txtDeliUnitPrice;
    public JFXTextField txtDeliDiscountedPrice;
    public Label lblDeliQuantityValidate;
    public JFXButton btnModify;
    public Label lblCash;
    public JFXButton btnInvoice;
    public JFXButton btnCancel;
    public JFXButton btnRemoveItem;
    public JFXButton btnDeliveryInvoice;
    public JFXButton btnDeliveryModify;
    public JFXButton btnDeliveryCancel;
    public JFXButton btnDeliRemoveItem;
    public Label lblDeliveryOrderId;
    public Label lblDeliveryCash;
    public Label lblDeliveryQuantity;

    ArrayList<CartTM> deleteArrayList = new ArrayList<>();
    ArrayList<CartTM> addArrayList = new ArrayList<>();

    public void initialize(){
        try{
            cmbDriver.setItems(new EmployeeController().getDrivers());

        }catch (SQLException | ClassNotFoundException e){

        }
        btnModify.setDisable(true);
        btnCancel.setDisable(true);
        btnInvoice.setDisable(true);
        btnAddItem.setDisable(true);
        btnRemoveItem.setDisable(true);

        btnAddDelivery.setDisable(true);
        btnDeliveryModify.setDisable(true);
        btnDeliveryCancel.setDisable(true);
        btnDeliveryInvoice.setDisable(true);
        btnDeliRemoveItem.setDisable(true);

        /*lblQuantityValidate.setText("*This field is required");
        lblDeliQuantityValidate.setText("*This field is required");*/

        cmbOrderType.setItems(FXCollections.observableArrayList("Dine-in", "Take-away"));
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colFoodDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        colDeFoodId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colDeFoodDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDeFoodUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDeliveryQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDeliveryTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        cmbFoodType.getItems().addAll("Meal","Pizza","Burger","Drink","Package");
        cmbDeliveryFoodType.getItems().addAll("Meal","Pizza","Burger","Drink","Package");

        /*try{
            cmbDeMeal.setItems(new ItemCrudController().getMeals("Meal"));
            cmbDePizza.setItems(new ItemCrudController().getPizza("Pizza"));
            cmbDeBurger.setItems(new ItemCrudController().getBurgers("Burger"));
            cmbDeliveryBeverage.setItems(new ItemCrudController().getDrinks("Drink"));
            cmbDePackage.setItems(new ItemCrudController().getPackages());
            cmbDriver.setItems(new EmployeeController().getDrivers());
        }catch (SQLException | ClassNotFoundException e){

        }*/
        cmbFoodType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtQuantity.clear();
            btnAddItem.setDisable(true);
            txtQuantity.setStyle("-fx-border-color: null");
            lblQuantityValidate.setText(null);

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


        /*try{
            cmbDeMeal.setItems(new ItemCrudController().getMeals("Meal"));
            cmbDePizza.setItems(new ItemCrudController().getPizza("Pizza"));
            cmbDeBurger.setItems(new ItemCrudController().getBurgers("Burger"));
            cmbDeliveryBeverage.setItems(new ItemCrudController().getDrinks("Drink"));
            cmbDePackage.setItems(new ItemCrudController().getPackages());
            cmbDriver.setItems(new EmployeeController().getDrivers());
        }catch (SQLException | ClassNotFoundException e){

        }*/
        cmbDeliveryFoodType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            txtDeQty.clear();
            btnAddDelivery.setDisable(true);
            txtDeQty.setStyle("-fx-border-color: null");
            lblDeliQuantityValidate.setText(null);

            txtDeliveryDescription.clear();
            txtDeQtyOnHand.clear();
            txtDeliUnitPrice.clear();
            txtDeliDiscountedPrice.clear();
            try{
                if(newValue!=null){
                    if(newValue.equals("Meal")){
                        txtDeQtyOnHand.setDisable(false);
                        txtDeQty.setDisable(false);
                        txtDeliDiscountedPrice.setDisable(false);

                        cmbDeliveryFood.setItems(new ItemCrudController().getMeals("Meal"));
                    }else if(newValue.equals("Pizza")){
                        txtDeQtyOnHand.setDisable(false);
                        txtDeQty.setDisable(false);
                        txtDeliDiscountedPrice.setDisable(false);

                        cmbDeliveryFood.setItems(new ItemCrudController().getPizza("Pizza"));
                    }else if(newValue.equals("Burger")){
                        txtDeQtyOnHand.setDisable(false);
                        txtDeQty.setDisable(false);
                        txtDeliDiscountedPrice.setDisable(false);

                        cmbDeliveryFood.setItems(new ItemCrudController().getBurgers("Burger"));
                    }else if(newValue.equals("Drink")){
                        txtDeQtyOnHand.setDisable(false);
                        txtDeQty.setDisable(false);
                        txtDeliDiscountedPrice.setDisable(false);

                        cmbDeliveryFood.setItems(new ItemCrudController().getDrinks("Drink"));
                    }else{
                        cmbDeliveryFood.setItems(new ItemCrudController().getPackages());
                        txtDeQtyOnHand.setText("1");
                        txtDeQty.setText("1");
                        txtDeQtyOnHand.setDisable(true);
                        txtDeQty.setDisable(true);
                        txtDeliDiscountedPrice.setText("0");
                        txtDeliDiscountedPrice.setDisable(true);
                    }
                }

            }catch (Exception e){

            }
        });

        cmbFood.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                txtQuantity.clear();
                btnAddItem.setDisable(true);
                txtQuantity.setStyle("-fx-border-color: null");
                lblQuantityValidate.setText(null);
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

        cmbDeliveryFood.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                txtDeQty.clear();
                btnAddDelivery.setDisable(true);
                txtDeQty.setStyle("-fx-border-color: null");
                lblDeliQuantityValidate.setText(null);
                if(cmbDeliveryFoodType.getValue()=="Package"){
                    txtDeQty.setText("1");
                    btnAddDelivery.setDisable(false);
                }
                txtDeliveryDescription.setText(newValue.getDescription());
                txtDeQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
                if(cmbDeliveryFoodType.getValue()=="Package"){
                    txtDeQtyOnHand.setText("1");
                    //btnAddItem.setDisable(false);
                }
                txtDeliUnitPrice.setText(String.valueOf(String.format("%.2f", newValue.getUnitPrice())));
                try{
                    txtDeliDiscountedPrice.setText(String.valueOf(String.format("%.2f",new ItemCrudController().getDiscountedPrice(cmbDeliveryFood.getValue().getFoodId()))));
                }catch (SQLException | ClassNotFoundException e){

                }
            }
        });
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

    public void txtSearchOrderIdOnAction(ActionEvent event) {
        try{
            if(cmbOrderType.getValue()!=null && !txtSearchOrderId.getText().isEmpty()){
                if(OrderIdValidationOnAction(null)){
                    tblDineInAndTakeAway.getItems().clear();
                    Order order = new OrderCrudController().getOrderDetail(txtSearchOrderId.getText(),cmbOrderType.getValue());
                    if(order != null){
                        if(new OrderCrudController().orderIsNotPaid(order.getOrderId(),cmbOrderType.getValue())){
                            lblCustomerId.setText(order.getCustomerId());
                            lblCustomerName.setText(order.getCustomerName());
                            lblTotal.setText(String.valueOf(order.getSubTotal()));

                            for(OrderDetail detail : order.getOrderDetails()){
                                dineTakeTmList.add(new CartTM(
                                        detail.getFoodId(),
                                        detail.getFoodType(),
                                        detail.getFoodDescription(),
                                        detail.getUnitPrice(),
                                        detail.getQty(),
                                        detail.getQty()*detail.getUnitPrice()
                                ));
                            }
                            tblDineInAndTakeAway.setItems(dineTakeTmList);
                            tblDineInAndTakeAway.refresh();
                            btnModify.setDisable(false);
                            btnCancel.setDisable(false);
                            btnInvoice.setDisable(false);
                            btnRemoveItem.setDisable(false);
                            txtCash.setDisable(false);
                        }else{
                            lblTotal.setText("0");
                            btnModify.setDisable(true);
                            btnCancel.setDisable(true);
                            btnInvoice.setDisable(true);
                            btnRemoveItem.setDisable(true);
                            txtCash.setDisable(true);
                            new Alert(Alert.AlertType.ERROR, "This order's payment has been paid by customer..!", ButtonType.CLOSE).show();
                        }
                    }else{
                        btnModify.setDisable(true);
                        btnCancel.setDisable(true);
                        btnInvoice.setDisable(true);
                        btnRemoveItem.setDisable(true);
                        txtCash.setDisable(true);
                        lblTotal.setText("0");
                        new Alert(Alert.AlertType.ERROR,"No Orders exist for this Order Id..!", ButtonType.OK).show();
                    }
                }else{
                    btnModify.setDisable(true);
                    btnCancel.setDisable(true);
                    btnInvoice.setDisable(true);
                    btnRemoveItem.setDisable(true);
                    txtCash.setDisable(true);
                    lblTotal.setText("0");
                    new Alert(Alert.AlertType.ERROR, "Invalid Order Id..!", ButtonType.CLOSE).show();
                }
            }else{
                btnModify.setDisable(true);
                btnCancel.setDisable(true);
                btnInvoice.setDisable(true);
                btnRemoveItem.setDisable(true);
                txtCash.setDisable(true);
                lblTotal.setText("0");
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
//            calculateTotal();
            /*txtCash.setStyle("-fx-border-color: null");
            lblCash.setText("");*/

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()+"..!", ButtonType.CLOSE).show();
        }
    }

    public void dineTakeTab(Event event) {
        clearDineTakeAway();
        addArrayList.clear();
        deleteArrayList.clear();
    }

    private void clearDineTakeAway(){
        cmbOrderType.getSelectionModel().clearSelection();
        txtSearchOrderId.clear();
        lblCustomerId.setText(null);
        lblCustomerName.setText(null);
        tblDineInAndTakeAway.getItems().clear();
        lblTotal.setText(null);
        lblBalance.setText(null);
        txtCash.clear();
        txtQuantity.clear();
        cmbFoodType.getSelectionModel().clearSelection();
        cmbFood.setItems(null);
        btnInvoice.setDisable(true);
        btnModify.setDisable(true);
        btnCancel.setDisable(true);
        btnAddItem.setDisable(true);
        btnRemoveItem.setDisable(true);

        txtFoodDescription.clear();
        txtFoodQtyOnHand.clear();
        txtFoodUnitPrice.clear();
        txtDiscountPrice.clear();

        txtSearchOrderId.setStyle("-fx-border-color: null");
        txtCash.setStyle("-fx-border-color: null");
        txtQuantity.setStyle("-fx-border-color: null");

        lblOrderIdValidation.setText("");
        lblCash.setText("");
        lblQuantityValidate.setText("");
    }

    private void clearDeliveryTab(){
        txtSearchDeliveryOrderId.clear();
        lblDeliveryCusName.setText(null);
        tblDelivery.getItems().clear();
        cmbDriver.setValue(null);
        txtAddress.clear();
        lblSubTotal.setText(null);
        lblDeliveryCharges.setText(null);
        lblGrandTotal.setText(null);
        txtDeQty.clear();
        txtDeliveryCash.clear();
        lblDeliveryBalance.setText(null);
        cmbDeliveryFoodType.setValue(null);
        cmbDeliveryFood.setValue(null);

        txtDeliveryDescription.clear();
        txtDeQtyOnHand.clear();
        txtDeliUnitPrice.clear();
        txtDeliDiscountedPrice.clear();

        btnDeliveryInvoice.setDisable(true);
        btnDeliveryModify.setDisable(true);
        btnDeliveryCancel.setDisable(true);
        btnAddDelivery.setDisable(true);
        btnDeliRemoveItem.setDisable(true);

        txtSearchDeliveryOrderId.setStyle("-fx-border-color: null");
        txtDeliveryCash.setStyle("-fx-border-color: null");
        txtDeQty.setStyle("-fx-border-color: null");

        lblDeliveryOrderId.setText("");
        lblDeliveryCash.setText("");
        lblDeliQuantityValidate.setText("");
    }

    /*public void cmbOnHide(Event event) {
        if(tabDineIn.isSelected()){
            tblDineInAndTakeAway.getSelectionModel().clearSelection();
            if(!cmbFood.getSelectionModel().isEmpty()){
                cmbFood.setDisable(true);
            }
        }else{
            tblDelivery.getSelectionModel().clearSelection();
            if(!cmbDeliveryFood.getSelectionModel().isEmpty()){
                cmbDeliveryFood.setDisable(true);
            }
        }
    }*/

    public void addItemOnAction(ActionEvent event) {
        //
        try{
            if(!txtQuantity.getText().isEmpty() && cmbFood.getValue()!=null && !tblDineInAndTakeAway.getItems().isEmpty()){
                if(dineTakeCheckQuantityOnAction(null)){
                    if(cmbFoodType.getValue()=="Package") {
                        String packageId = cmbFood.getValue().getFoodId();
                        ArrayList<PackageDetailTM> arrayList = new ItemCrudController().getPackageItems(packageId);

                        for (PackageDetailTM tm : arrayList) {
                            if(new ItemCrudController().getQtyOfFoodId(tm.getFoodId()) > 0) {
                                CartTM cart = isExists(tm.getFoodId());
                                int qty = 0;
                                if(cart!=null){
                                    qty = cart.getQty();
                                }
                                if(new ItemCrudController().getQtyOfFoodId(tm.getFoodId()) >= qty+ tm.getQty()){
                            double totalCost = tm.getUniPrice() * tm.getQty();
                            CartTM isExist = isExists(tm.getFoodId());
                            if (isExist != null) {
                                /*CartTM ex ;
                                ex=new CartTM(isExist.getFoodId(),isExist.getFoodType(),isExist.getDescription(),isExist.getUnitPrice(), tm.getQty(), tm.getQty()*isExist.getUnitPrice());*/
                                isExist.setQty(isExist.getQty() + tm.getQty());
                                isExist.setTotalCost(isExist.getTotalCost() + totalCost);
                                /*addArrayList.add(ex);*/
                            } else {
                                CartTM cartTM = new CartTM(
                                        tm.getFoodId(),
                                        new ItemCrudController().getFoodType(tm.getFoodId()),
                                        tm.getFoodDescription(),
                                        tm.getUniPrice(),
                                        tm.getQty(),
                                        totalCost
                                );
                                dineTakeTmList.add(cartTM);
                                addArrayList.add(cartTM);
                            }
                            totalCost = 0;
                                }else{
                                    return;
                                }
                            }else{
                                return;
                            }
                        }
                        tblDineInAndTakeAway.setItems(dineTakeTmList);
                        tblDineInAndTakeAway.refresh();
                        calculateTotal();
                    }else{
                        if(cmbFood.getValue().getQtyOnHand() > 0) {
                            CartTM cart = isExists(cmbFood.getValue().getFoodId());
                            int q = 0;
                            if(cart!=null){
                                q = cart.getQty();
                            }
                            int qty = Integer.parseInt(txtQuantity.getText());
                            if(new ItemCrudController().getQtyOfFoodId(cmbFood.getValue().getFoodId()) >= q+qty){
                            double unitPrice = Double.parseDouble(txtFoodUnitPrice.getText());
                            double totalCost = unitPrice * qty;

                            CartTM isExist = isExists(cmbFood.getValue().getFoodId());

                            if (isExist != null) {
                               /* CartTM ex;
                                ex = new CartTM(isExist.getFoodId(), isExist.getFoodType(), isExist.getDescription(), isExist.getUnitPrice(), qty, isExist.getUnitPrice() * qty);*/
                                isExist.setQty(isExist.getQty() + qty);
                                isExist.setTotalCost(isExist.getTotalCost() + totalCost);
                                /*addArrayList.add(ex);*/
                            } else {
                                CartTM tm = new CartTM(
                                        cmbFood.getValue().getFoodId(),
                                        cmbFoodType.getValue(),
                                        txtFoodDescription.getText(),
                                        unitPrice,
                                        qty,
                                        totalCost
                                );

                                dineTakeTmList.add(tm);
                                addArrayList.add(tm);
                            }
                            tblDineInAndTakeAway.setItems(dineTakeTmList);
                            tblDineInAndTakeAway.refresh();
                            calculateTotal();


                        }else{
                                return;
                            }
                        }else{
                            new Alert(Alert.AlertType.ERROR, "This item has zero quantity," +
                                    " Therefore Choose another item..!", ButtonType.CLOSE).show();
                        }
                    }
                    txtQuantity.clear();
                    btnAddItem.setDisable(true);

                    if(cmbFoodType.getValue().equals("Package")){
                        btnAddItem.setDisable(false);
                        txtQuantity.setText("1");
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid fields try again..!", ButtonType.CLOSE).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
        }catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException e){
            /*System.out.println(e.getMessage());*/
        }
    }

    private CartTM isExists(String foodId) {
        if(tabDineIn.isSelected()){
            for (CartTM tm : dineTakeTmList) {
                if (tm.getFoodId().equals(foodId)) {
                    return tm;
                }
            }
            return null;
        }else{
            for (CartTM tm : deliveryTmList) {
                if (tm.getFoodId().equals(foodId)) {
                    return tm;
                }
            }
            return null;
        }
    }


    public void getInvoiceDineInAndTakeOnAction(ActionEvent event) {
        if(cmbOrderType.getValue()!=null && !txtSearchOrderId.getText().isEmpty() && !dineTakeTmList.isEmpty()) {
            if (OrderIdValidationOnAction(null)) {
               try{
                   new ReportController().printInvoice(dineTakeTmList, txtSearchOrderId.getText(), lblCustomerName.getText(), new CustomerController().getCustomerNumberOnOrderID(txtSearchOrderId.getText()), cmbOrderType.getValue(), Double.parseDouble(lblTotal.getText()), 0.00, Double.parseDouble(lblTotal.getText()));
               }catch (SQLException | ClassNotFoundException e){

               }
            }
        }
    }

    public void modifyOrderOnAction(ActionEvent event) {
        /*for(CartTM tm : addArrayList){
            System.out.println("Added Items : "+tm.getFoodId()+"----------"+tm.getQty()+"-------------"+tm.getTotalCost());
        }

        for(CartTM tm : deleteArrayList){
            System.out.println("Deleted Items : "+tm.getFoodId()+"----------"+tm.getQty()+"-------------"+tm.getTotalCost());
        }

        for(int i=0; i<deleteArrayList.size(); i++){
            for(int j=0; j<addArrayList.size(); j++){
                if(deleteArrayList.get(i).getFoodId().equals(addArrayList.get(j).getFoodId())){
                    addArrayList.remove(j);
                }
            }
        }

        System.out.println("-=============================================");

        for(CartTM tm : addArrayList){
            System.out.println("Added Items : "+tm.getFoodId()+"----------"+tm.getQty()+"-------------"+tm.getTotalCost());
        }

        for(CartTM tm : deleteArrayList){
            System.out.println("Deleted Items : "+tm.getFoodId()+"----------"+tm.getQty()+"-------------"+tm.getTotalCost());
        }*/
        try{
            if(cmbOrderType.getValue()!=null && !txtSearchOrderId.getText().isEmpty() && !dineTakeTmList.isEmpty()) {
                if (OrderIdValidationOnAction(null)) {
                    ArrayList<OrderDetail> detailArrayList = new ArrayList<>();
                    for(CartTM tm : dineTakeTmList){
                        detailArrayList.add(new OrderDetail(
                                txtSearchOrderId.getText(),
                                tm.getFoodId(),
                                tm.getFoodType(),
                                tm.getDescription(),
                                tm.getUnitPrice(),
                                tm.getQty()
                        ));
                    }

                    Order order = new Order(txtSearchOrderId.getText(),lblCustomerId.getText(),lblCustomerName.getText(),null,null,cmbOrderType.getValue(),Double.parseDouble(lblTotal.getText()),0.00,Double.parseDouble(lblTotal.getText()),"Non-Paid",detailArrayList);
                    boolean orderIsUpdated = new OrderCrudController().updateOrder(order);
                    if(orderIsUpdated){
                        new Alert(Alert.AlertType.CONFIRMATION, "Order is Updated..!", ButtonType.OK).show();
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Try again,Something went wrong..!", ButtonType.OK).show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid Order ID try again..!", ButtonType.OK).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields,Please input data to the fields..!", ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        btnModify.setDisable(true);
    }

    public void cancelOrderOnAction(ActionEvent event) {
        try{
            if(cmbOrderType.getValue()!=null && !txtSearchOrderId.getText().isEmpty() && !dineTakeTmList.isEmpty()) {
                if (OrderIdValidationOnAction(null) && !lblOrderIdValidation.getText().startsWith("*Invalid")) {
                    boolean orderIsDeleted = new OrderCrudController().deleteOrder(txtSearchOrderId.getText());
                    if(orderIsDeleted){
                        new Alert(Alert.AlertType.CONFIRMATION, "Order Cancelled Successfully..!", ButtonType.OK).show();
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Something went wrong please try Again..!", ButtonType.CLOSE).show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid fields can't cancel order..!", ButtonType.CLOSE).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!", ButtonType.CLOSE).show();
            }

        }catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException e){

        }
        btnCancel.setDisable(true);
        clearDineTakeAway();
    }

    public void commitsThePaymentOnAction(ActionEvent event) {
        if(!cmbOrderType.getSelectionModel().getSelectedItem().isEmpty() && !txtSearchOrderId.getText().isEmpty() && !dineTakeTmList.isEmpty()){
            if(OrderIdValidationOnAction(null)){
                if(!txtCash.getText().isEmpty()){
                    if(!lblCash.getText().startsWith("*Invalid")){
                        double totalAmount = Double.parseDouble(lblTotal.getText());
                        double paidAmount = Double.parseDouble(txtCash.getText());
                        double balance = paidAmount - totalAmount;

                        try{
                            lblBalance.setText(String.format("%.2f", balance));
                            Transaction transaction = new Transaction(
                                        lastTransactionId(),
                                        lblCustomerId.getText(),
                                        txtSearchOrderId.getText(),
                                        new EmployeeController().getCashierId(LoginFormController.cashierName),
                                        totalAmount,
                                        paidAmount,
                                        balance
                            );

                            boolean transactionSucceeded = new TransactionController().commitsTheTransaction(transaction);
                            if(transactionSucceeded){
                                if(new OrderCrudController().orderIsPaid(txtSearchOrderId.getText())){
                                    new ReportController().printBill(dineTakeTmList, txtSearchOrderId.getText(), lblCustomerName.getText(), new CustomerController().getCustomerNumberOnOrderID(txtSearchOrderId.getText()), cmbOrderType.getValue(), Double.parseDouble(lblTotal.getText()), 0.00, Double.parseDouble(lblTotal.getText()), Double.parseDouble(txtCash.getText()), Double.parseDouble(lblBalance.getText()));
                                    //ygv
                                    txtCash.setStyle("-fx-border-color: null");
                                }else{
                                    txtCash.setStyle("-fx-border-color: RED");
                                    new Alert(Alert.AlertType.ERROR, "Transaction failed..!", ButtonType.CLOSE).show();
                                }
                            }else{
                                txtCash.setStyle("-fx-border-color: RED");
                                new Alert(Alert.AlertType.ERROR, "Transaction failed..!, try again", ButtonType.CLOSE).show();
                            }


                        }catch (SQLException | ClassNotFoundException e){

                        }
                    }else{
                        txtCash.setStyle("-fx-border-color: RED");
                        new Alert(Alert.AlertType.ERROR, "Invalid Cash value", ButtonType.CLOSE).show();
                    }
                }else{
                    txtCash.setStyle("-fx-border-color: RED");
                    new Alert(Alert.AlertType.ERROR, "Cash is empty..!Please input cash..!", ButtonType.OK).show();
                }
            }else{
                txtCash.setStyle("-fx-border-color: RED");
                new Alert(Alert.AlertType.ERROR, "Invalid Order ID..!", ButtonType.CLOSE).show();
            }
        }else{
            txtCash.setStyle("-fx-border-color: RED");
            new Alert(Alert.AlertType.ERROR, "Empty fields therefore,can't make payment..!", ButtonType.OK).show();
        }
        clearDineTakeAway();
    }

    public void removeItemOnAction(ActionEvent event) {
        try{
            if(!tblDineInAndTakeAway.getItems().isEmpty()){
                CartTM tm = tblDineInAndTakeAway.getSelectionModel().getSelectedItem();
                if(tm != null){
                    for(CartTM cartTM : dineTakeTmList){
                        if(cartTM.getFoodId().equals(tm.getFoodId())){
                            deleteArrayList.add(cartTM);
                            dineTakeTmList.remove(cartTM);
                            break;
                        }
                    }
                    tblDineInAndTakeAway.setItems(dineTakeTmList);
                    tblDineInAndTakeAway.refresh();
                    calculateTotal();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Select Item from table..!",ButtonType.OK).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Table is empty therefore,can't delete data from table",ButtonType.CLOSE).show();
            }
            btnRemoveItem.setDisable(true);
        }catch (Exception e){

        }
    }

    private void calculateTotal() {
        double subTot = 0;
        double deliveryCharges = 0;
        double grandTotal = 0;
        if (tabDineIn.isSelected()) {
            for (CartTM tm : dineTakeTmList) {
                subTot += tm.getTotalCost();
            }
            lblTotal.setText(String.valueOf(subTot));
        } else {
            for (CartTM tm : deliveryTmList) {
                subTot += tm.getTotalCost();
            }
            lblSubTotal.setText(String.format("%.2f", subTot));
            deliveryCharges = subTot * 2 / 100;
            grandTotal = subTot + deliveryCharges;
            lblDeliveryCharges.setText(String.format("%.2f", deliveryCharges));
            lblGrandTotal.setText(String.format("%.2f", grandTotal));
        }
    }

    public void clearComboOnAction(ActionEvent event) {
        cmbFood.setDisable(false);
        cmbFoodType.setValue(null);
        cmbFood.setItems(null);
    }

    public void deliveryTab(Event event) {
        clearDeliveryTab();
        addArrayList.clear();
        deleteArrayList.clear();
    }

    public String customerName=null;
    public String customerId = null;
    public String riderId = null;
    public String riderName = null;

    public void txtSearchDeliveryOrderOnAction(ActionEvent event) {
        //
        try{
            if(!txtSearchDeliveryOrderId.getText().isEmpty()){
                if(deliveryOrderIdValidationOnAction(null)){
                    tblDelivery.getItems().clear();
                    Order order = new OrderCrudController().getOrderDetail(txtSearchDeliveryOrderId.getText(),"Delivery");
                    if(order != null){
                        if(new OrderCrudController().orderIsNotPaid(order.getOrderId(),"Delivery")){
                            Delivery delivery = new DeliveryCrudController().getDeliveryDetails(txtSearchDeliveryOrderId.getText());

                            lblDeliveryCusName.setText(order.getCustomerName());
                            customerName = order.getCustomerName();
                            customerId = order.getCustomerId();
                            lblSubTotal.setText(String.format("%.2f", order.getSubTotal()));
                            lblDeliveryCharges.setText(String.format("%.2f", order.getDeliveryCharges()));
                            lblGrandTotal.setText(String.format("%.2f", order.getGrandTotal()));
                            cmbDriver.setValue(new Employee(
                                    delivery.getRiderId(),
                                    delivery.getRiderName()
                            ));
                            riderId = delivery.getRiderId();
                            riderName = delivery.getRiderName();
                            txtAddress.setText(new CustomerCrudController().getCustomer(order.getCustomerId()).getAddress());

                            for(OrderDetail detail : order.getOrderDetails()){
                                deliveryTmList.add(new CartTM(
                                        detail.getFoodId(),
                                        detail.getFoodType(),
                                        detail.getFoodDescription(),
                                        detail.getUnitPrice(),
                                        detail.getQty(),
                                        detail.getQty()*detail.getUnitPrice()
                                ));
                            }
                            tblDelivery.setItems(deliveryTmList);
                            tblDelivery.refresh();
                            btnDeliveryModify.setDisable(false);
                            btnDeliveryCancel.setDisable(false);
                            btnDeliveryInvoice.setDisable(false);
                            txtDeliveryCash.setDisable(false);
                            /*txtDeliveryCash.setStyle("-fx-border-color: null");
                            lblDeliveryCash.setText("");*/
                            btnDeliRemoveItem.setDisable(false);
                        }else{
                            lblTotal.setText("0");
                            btnDeliveryModify.setDisable(true);
                            btnDeliveryCancel.setDisable(true);
                            btnDeliveryInvoice.setDisable(true);
                            txtDeliveryCash.setDisable(true);
                            btnDeliRemoveItem.setDisable(true);
                            new Alert(Alert.AlertType.ERROR, "This order's payment has been paid by customer..!", ButtonType.CLOSE).show();
                        }
                    }else{
                        btnDeliveryModify.setDisable(true);
                        btnDeliveryCancel.setDisable(true);
                        btnDeliveryInvoice.setDisable(true);
                        txtDeliveryCash.setDisable(true);
                        btnDeliRemoveItem.setDisable(true);
                        lblTotal.setText("0");
                        new Alert(Alert.AlertType.ERROR,"No Orders exist for this Order Id..!", ButtonType.OK).show();
                    }
                }else{
                    btnDeliveryModify.setDisable(true);
                    btnDeliveryCancel.setDisable(true);
                    btnDeliveryInvoice.setDisable(true);
                    txtDeliveryCash.setDisable(true);
                    btnDeliRemoveItem.setDisable(true);
                    lblTotal.setText("0");
                    new Alert(Alert.AlertType.ERROR, "Invalid Order Id..!", ButtonType.CLOSE).show();
                }
            }else{
                btnDeliveryModify.setDisable(true);
                txtDeliveryCash.setDisable(true);
                btnDeliveryCancel.setDisable(true);
                btnDeliveryInvoice.setDisable(true);
                btnDeliRemoveItem.setDisable(true);
                lblTotal.setText("0");
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
//            calculateTotal();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()+"..!", ButtonType.CLOSE).show();
        }
    }

    public void addDeliveryItemOnAction(ActionEvent event) {
        try{
            if(!txtDeQty.getText().isEmpty() && cmbDeliveryFood.getValue()!=null && !tblDelivery.getItems().isEmpty()){
                //
                if(deliveryCheckQuantityOnAction(null)){
                    if(cmbDeliveryFoodType.getValue()=="Package") {
                        String packageId = cmbDeliveryFood.getValue().getFoodId();
                        ArrayList<PackageDetailTM> arrayList = new ItemCrudController().getPackageItems(packageId);

                        for (PackageDetailTM tm : arrayList) {
                            if(new ItemCrudController().getQtyOfFoodId(tm.getFoodId()) > 0) {
                                CartTM cart = isExists(tm.getFoodId());
                                int qty = 0;
                                if(cart!=null){
                                    qty = cart.getQty();
                                }
                                if(new ItemCrudController().getQtyOfFoodId(tm.getFoodId()) >= qty+ tm.getQty()){
                            double totalCost = tm.getUniPrice() * tm.getQty();
                            CartTM isExist = isExists(tm.getFoodId());
                            if (isExist != null) {
                               /* CartTM ex ;
                                ex=new CartTM(isExist.getFoodId(),isExist.getFoodType(),isExist.getDescription(),isExist.getUnitPrice(), tm.getQty(), tm.getQty()*isExist.getUnitPrice());*/
                                isExist.setQty(isExist.getQty() + tm.getQty());
                                /*System.out.println(tm.getQty());*/
                                isExist.setTotalCost(isExist.getTotalCost() + totalCost);
                                /*addArrayList.add(ex);*/
                            } else {
                                CartTM cartTM = new CartTM(
                                        tm.getFoodId(),
                                        // Methana food type eka package kyla ganne decision ekk ganna eka hodaida kyla..>!
                                        new ItemCrudController().getFoodType(tm.getFoodId()),
                                        tm.getFoodDescription(),
                                        tm.getUniPrice(),
                                        tm.getQty(),
                                        totalCost
                                );
                                deliveryTmList.add(cartTM);
                                addArrayList.add(cartTM);
                            }
                            totalCost = 0;
                                }else{
                                    return;
                                }
                            }else{
                                return;
                            }
                        }
                        tblDelivery.setItems(deliveryTmList);
                        tblDelivery.refresh();
                        calculateTotal();
                    }else{
                        if(cmbDeliveryFood.getValue().getQtyOnHand() > 0) {
                            CartTM cart = isExists(cmbDeliveryFood.getValue().getFoodId());
                            int q = 0;
                            if(cart!=null){
                                q = cart.getQty();
                            }
                            int qty = Integer.parseInt(txtDeQty.getText());
                            if(new ItemCrudController().getQtyOfFoodId(cmbDeliveryFood.getValue().getFoodId()) >= q+qty){
                            double unitPrice = Double.parseDouble(txtDeliUnitPrice.getText());
                            double totalCost = unitPrice * qty;

                            CartTM isExist = isExists(cmbDeliveryFood.getValue().getFoodId());
                            /* */

                            if (isExist != null) {
                               /* CartTM ex ;
                                ex=new CartTM(isExist.getFoodId(),isExist.getFoodType(),isExist.getDescription(),isExist.getUnitPrice(), qty, isExist.getUnitPrice()*qty);*/
                                isExist.setQty(isExist.getQty() + qty);
                                isExist.setTotalCost(isExist.getTotalCost() + totalCost);
                                /*addArrayList.add(ex);*/
                            } else {
                                CartTM tm = new CartTM(
                                        cmbDeliveryFood.getValue().getFoodId(),
                                        cmbDeliveryFoodType.getValue(),
                                        txtDeliveryDescription.getText(),
                                        unitPrice,
                                        qty,
                                        totalCost
                                );

                                deliveryTmList.add(tm);
                                addArrayList.add(tm);
                            }
                            tblDelivery.setItems(deliveryTmList);
                            tblDelivery.refresh();
                            calculateTotal();

                        }
                        }else{
                            new Alert(Alert.AlertType.ERROR, "This item has zero quantity," +
                                    " Therefore Choose another item..!", ButtonType.CLOSE).show();
                        }
                    }
                    txtDeQty.setText(null);
                    btnAddDelivery.setDisable(true);

                    if(cmbDeliveryFoodType.getValue().equals("Package")){
                        btnAddDelivery.setDisable(false);
                        txtDeQty.setText("1");
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid fields try again..!", ButtonType.CLOSE).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
        }catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException e){
            /*System.out.println(e.getMessage());*/
        }
    }

    public void getInvoiceDeliveryOnAction(ActionEvent event) {
        //
        if(!txtSearchDeliveryOrderId.getText().isEmpty() && !deliveryTmList.isEmpty()) {
            if (deliveryOrderIdValidationOnAction(null)) {
                try{
                    new ReportController().printInvoice(deliveryTmList, txtSearchDeliveryOrderId.getText(), lblDeliveryCusName.getText(), new CustomerController().getCustomerNumberOnOrderID(txtSearchDeliveryOrderId.getText()), "Delivery", Double.parseDouble(lblSubTotal.getText()), Double.parseDouble(lblDeliveryCharges.getText()), Double.parseDouble(lblGrandTotal.getText()));


                }catch (SQLException | ClassNotFoundException e){

                }
            }
        }
    }

    public void modifyDeliveryOrderOnAction(ActionEvent event) {
        //
        try{
            if(!txtSearchDeliveryOrderId.getText().isEmpty() && !deliveryTmList.isEmpty()) {
                if (deliveryOrderIdValidationOnAction(null)) {
                    ArrayList<OrderDetail> detailArrayList = new ArrayList<>();
                    for(CartTM tm : deliveryTmList){
                        detailArrayList.add(new OrderDetail(
                                txtSearchDeliveryOrderId.getText(),
                                tm.getFoodId(),
                                tm.getFoodType(),
                                tm.getDescription(),
                                tm.getUnitPrice(),
                                tm.getQty()
                        ));
                    }

                    Order order = new Order(txtSearchDeliveryOrderId.getText(),customerId,lblDeliveryCusName.getText(),null,null,"Delivery",Double.parseDouble(lblSubTotal.getText()),Double.parseDouble(lblDeliveryCharges.getText()),Double.parseDouble(lblGrandTotal.getText()),"Non-Paid",detailArrayList);
                    boolean orderIsUpdated = new OrderCrudController().updateOrder(order);
                    if(orderIsUpdated){
                        if(new DeliveryCrudController().updateDelivery(txtSearchDeliveryOrderId.getText(),cmbDriver.getValue().getEmployeeID(),cmbDriver.getValue().getEmployeeName())){
                            new Alert(Alert.AlertType.CONFIRMATION, "Order is Updated..!", ButtonType.OK).show();

                        }else{
                            new Alert(Alert.AlertType.ERROR, "Try again,Something went wrong..!", ButtonType.OK).show();
                        }
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Try again,Something went wrong..!", ButtonType.OK).show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid Order ID try again..!", ButtonType.OK).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields,Please input data to the fields..!", ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        btnDeliveryModify.setDisable(true);
    }

    public void cancelDeliveryOrderOnAction(ActionEvent event) {
        //
        try{
            if(!txtSearchDeliveryOrderId.getText().isEmpty() && !deliveryTmList.isEmpty()) {
                if (deliveryOrderIdValidationOnAction(null) && !lblDeliveryOrderId.getText().startsWith("*Invalid")) {
                    boolean orderIsDeleted = new OrderCrudController().deleteOrder(txtSearchDeliveryOrderId.getText());
                    if(orderIsDeleted){
                        new Alert(Alert.AlertType.CONFIRMATION, "Order Cancelled Successfully..!", ButtonType.OK).show();
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Something went wrong please try Again..!", ButtonType.CLOSE).show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid fields therefore, can't cancel order..!", ButtonType.CLOSE).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields try again..!", ButtonType.CLOSE).show();
            }

        }catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException e){

        }
        btnDeliveryCancel.setDisable(true);
        clearDeliveryTab();
    }

    public void clearDeliveryComboOnAction(ActionEvent event) {
        cmbDeliveryFood.setDisable(false);
        cmbDeliveryFoodType.setValue(null);
        cmbDeliveryFood.setValue(null);
    }

    public void commitsDeliveryPaymentOnAction(ActionEvent event) {
        //
        if( !txtSearchDeliveryOrderId.getText().isEmpty() && !deliveryTmList.isEmpty()){
            if(deliveryOrderIdValidationOnAction(null)){
                if(!txtDeliveryCash.getText().isEmpty() ){
                    if(!lblDeliveryCash.getText().startsWith("*Invalid")){
                        double totalAmount = Double.parseDouble(lblGrandTotal.getText());
                        double paidAmount = Double.parseDouble(txtDeliveryCash.getText());
                        double balance = paidAmount - totalAmount;

                        try{
                            lblDeliveryBalance.setText(String.format("%.2f", balance));
                            Transaction transaction = new Transaction(
                                    lastTransactionId(),
                                    customerId,
                                    txtSearchDeliveryOrderId.getText(),
                                    new EmployeeController().getCashierId(LoginFormController.cashierName),
                                    totalAmount,
                                    paidAmount,
                                    balance
                            );

                            boolean transactionSucceeded = new TransactionController().commitsTheTransaction(transaction);
                            if(transactionSucceeded){
                                if(new OrderCrudController().orderIsPaid(txtSearchDeliveryOrderId.getText())){
                                    new ReportController().printBill(deliveryTmList, txtSearchDeliveryOrderId.getText(), lblDeliveryCusName.getText(), new CustomerController().getCustomerNumberOnOrderID(txtSearchDeliveryOrderId.getText()), "Delivery", Double.parseDouble(lblSubTotal.getText()), Double.parseDouble(lblDeliveryCharges.getText()), Double.parseDouble(lblGrandTotal.getText()), Double.parseDouble(txtDeliveryCash.getText()), Double.parseDouble(lblDeliveryBalance.getText()));
                                    //ygv
                                    txtDeliveryCash.setStyle("-fx-border-color: null");
                                }else{
                                    txtDeliveryCash.setStyle("-fx-border-color: RED");
                                    new Alert(Alert.AlertType.ERROR, "Transaction failed..!", ButtonType.CLOSE).show();
                                }
                            }else{
                                txtDeliveryCash.setStyle("-fx-border-color: RED");
                                new Alert(Alert.AlertType.ERROR, "Transaction failed..!, try again", ButtonType.CLOSE).show();
                            }

                        }catch (SQLException | ClassNotFoundException e){

                        }
                    }else{
                        txtDeliveryCash.setStyle("-fx-border-color: RED");
                        new Alert(Alert.AlertType.ERROR, "Invalid data value", ButtonType.CLOSE).show();
                    }
                }else{
                    txtDeliveryCash.setStyle("-fx-border-color: RED");
                    new Alert(Alert.AlertType.ERROR, "Cash is empty..!Please input cash..!", ButtonType.OK).show();
                }
            }else{
                txtDeliveryCash.setStyle("-fx-border-color: RED");
                new Alert(Alert.AlertType.ERROR, "Invalid Order ID..!", ButtonType.CLOSE).show();
            }
        }else{
            txtDeliveryCash.setStyle("-fx-border-color: RED");
            new Alert(Alert.AlertType.ERROR, "Empty fields therefore,can't make payment..!", ButtonType.OK).show();
        }
        clearDeliveryTab();
    }

    public void removeDeliveryItemOnAction(ActionEvent event) {
        //
        try{
            if(!tblDelivery.getItems().isEmpty()){
                CartTM tm = tblDelivery.getSelectionModel().getSelectedItem();
                if(tm != null){
                    for(CartTM cartTM : deliveryTmList){
                        if(cartTM.getFoodId().equals(tm.getFoodId())){
                            deleteArrayList.add(cartTM);
                            deliveryTmList.remove(cartTM);
                            break;
                        }
                    }
                    tblDelivery.setItems(deliveryTmList);
                    tblDelivery.refresh();
                    calculateTotal();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Select Item from table..!",ButtonType.OK).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Table is empty therefore,can't delete data from table",ButtonType.CLOSE).show();
            }
            btnDeliRemoveItem.setDisable(true);
        }catch (Exception e){

        }
    }

    public boolean OrderIdValidationOnAction(KeyEvent keyEvent) {
        String value = "^(OI-)[0-9]{5}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtSearchOrderId.getText());
        if (matcher.matches()) {
            lblOrderIdValidation.setText(" ");
            txtSearchOrderId.setStyle("-fx-border-color: GREEN");
            return true;
        }else{
            lblOrderIdValidation.setText("*Invalid Order ID");
            txtSearchOrderId.setStyle("-fx-border-color: RED");
            return false;
        }
    }

    public boolean deliveryOrderIdValidationOnAction(KeyEvent keyEvent) {
        String value = "^(OI-)[0-9]{5}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtSearchDeliveryOrderId.getText());
        if (matcher.matches()) {
            lblDeliveryOrderId.setText(" ");
            txtSearchDeliveryOrderId.setStyle("-fx-border-color: GREEN");
            return true;
        }else{
            lblDeliveryOrderId.setText("*Invalid Order ID");
            txtSearchDeliveryOrderId.setStyle("-fx-border-color: RED");
            return false;
        }
    }

    public boolean dineTakeCheckQuantityOnAction(KeyEvent keyEvent) {
        try{
            String value = "^[1-9][0-9]{0,3}$";
            Pattern compile = Pattern.compile(value);
            Matcher matcher = compile.matcher(txtQuantity.getText());
            if (matcher.matches()) {
                lblQuantityValidate.setText(" ");
                txtQuantity.setStyle("-fx-border-color: GREEN");
                if(!txtFoodDescription.getText().isEmpty() && !txtFoodQtyOnHand.getText().isEmpty() && !txtFoodUnitPrice.getText().isEmpty() && !txtDiscountPrice.getText().isEmpty()){
                    btnAddItem.setDisable(false);
                }
                return true;
            }else{
                btnAddItem.setDisable(true);
                if(txtQuantity.getText().isEmpty()){
                    lblQuantityValidate.setText("*This field is required");
                }else{
                    lblQuantityValidate.setText("*Invalid Quantity");
                }
                txtQuantity.setStyle("-fx-border-color: RED");
                return false;
            }
        }catch (Exception e){

        }
        return false;
    }

    public boolean deliveryCheckQuantityOnAction(KeyEvent keyEvent) {
        try{
            String value = "^[1-9][0-9]{0,3}$";
            Pattern compile = Pattern.compile(value);
            Matcher matcher = compile.matcher(txtDeQty.getText());
            if (matcher.matches()) {
                lblDeliQuantityValidate.setText(" ");
                txtDeQty.setStyle("-fx-border-color: GREEN");
                if(!txtDeliveryDescription.getText().isEmpty() && !txtDeQtyOnHand.getText().isEmpty() && !txtDeliUnitPrice.getText().isEmpty() && !txtDeliDiscountedPrice.getText().isEmpty()){
                    btnAddDelivery.setDisable(false);
                }
                return true;
            }else{
                btnAddDelivery.setDisable(true);
                if(txtDeQty.getText().isEmpty()){
                    lblDeliQuantityValidate.setText("*This field is required");
                } else{
                    lblDeliQuantityValidate.setText("*Invalid Quantity");
                }
                txtDeQty.setStyle("-fx-border-color: RED");
                return false;
            }
        }catch (Exception e){

        }
        return false;
    }

    public void checkCashOnAction(KeyEvent keyEvent) {
        String value = "^[1-9][0-9]*(.[0-9]{2})?$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtCash.getText());
        if (matcher.matches()) {
            if(Double.parseDouble(txtCash.getText()) >= Double.parseDouble(lblTotal.getText())){
                lblCash.setText(" ");
                txtCash.setStyle("-fx-border-color: GREEN");
            }else{
                lblCash.setText("*Invalid,Cash should be larger than grand total");
                txtCash.setStyle("-fx-border-color: RED");
            }
        } else {
            lblCash.setText("*Invalid Cash Amount");
            txtCash.setStyle("-fx-border-color: RED");
        }
    }

    public void checkDeliveryCashOnAction(KeyEvent keyEvent) {
        String value = "^[1-9][0-9]*(.[0-9]{2})?$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtDeliveryCash.getText());
        if (matcher.matches()) {
            if(Double.parseDouble(txtDeliveryCash.getText()) >= Double.parseDouble(lblGrandTotal.getText())){
                lblDeliveryCash.setText(" ");
                txtDeliveryCash.setStyle("-fx-border-color: GREEN");
            }else{
                lblDeliveryCash.setText("*Invalid,Cash should be larger than grand total");
                txtDeliveryCash.setStyle("-fx-border-color: RED");
            }
        } else {
            lblDeliveryCash.setText("*Invalid Cash Amount");
            txtDeliveryCash.setStyle("-fx-border-color: RED");
        }
    }

    public void tblOnAction(MouseEvent mouseEvent) {
        btnRemoveItem.setDisable(false);
        if(tblDineInAndTakeAway.getItems().isEmpty()){
            btnRemoveItem.setDisable(true);
        }
    }

    public void deliveryTblOnAction(MouseEvent mouseEvent) {
        btnDeliRemoveItem.setDisable(false);
        if(tblDelivery.getItems().isEmpty()){
            btnDeliRemoveItem.setDisable(true);
        }
    }
}
