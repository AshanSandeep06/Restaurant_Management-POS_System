package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Food;
import model.Package;
import model.PackageDetail;
import model.PackageDetailTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagePackageFormController {
    public ComboBox<Package> cmbPackageCode;
    public TextField txtPackageCode;
    public TextField txtPackageName;
    public ComboBox<Food> cmbFoodItem;
    public TextField txtQty;
    public TextField txtPackagePrice;
    public TableView<PackageDetailTM> tblFood;
    public TableColumn colFoodCode;
    public TableColumn colFoodDes;
    public TableColumn colFoodUnitPrice;
    public TableColumn colFoodQty;
    public TableColumn colTotalCost;
    public TableColumn colFoodOption;
    public AnchorPane context;
    public TextField txtFoodType;
    public TextField txtFoodName;
    public Label lblPackagePriceValidate;
    public Label lblQtyValidation;
    public Label lblTotalCost;
    public Label lblPackageName;
    public AnchorPane anchorContext;
    public ComboBox<String> cmbSelectTable;
    public JFXButton btnDeletePackage;
    public JFXButton btnUpdatePackage;
    public JFXButton btnSavePackage;
    public JFXButton btnAddItem;

    ObservableList<PackageDetailTM> tmList = FXCollections.observableArrayList();

    ObservableList<PackageDetailTM> tms = FXCollections.observableArrayList();
    ObservableList<PackageDetailTM> packTms = FXCollections.observableArrayList();
    ArrayList<PackageDetail> arrayList = new ArrayList<>();
    ObservableList<PackageDetailTM> items = FXCollections.observableArrayList();

    private void loadFoodsData() {
        try {
            cmbFoodItem.setItems(new PackageController().getFoods());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getLastPackageId() {
        try {
            String packageId = new PackageController().getPackageId();
            String finalId = "P-001";

            if (packageId != null) {
                String[] splitString = packageId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "P-00" + id;
                } else if (id < 100) {
                    finalId = "P-0" + id;
                } else {
                    finalId = "P-" + id;
                }
                txtPackageCode.setText(finalId);
            } else {
                txtPackageCode.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setPackageData(Package pack) {
        try {
            tms.clear();
            txtPackageCode.setText(pack.getPackageId());
            txtPackageName.setText(pack.getPackageName());
            txtPackagePrice.setText(String.valueOf(pack.getPackagePrice()));
            ArrayList<PackageDetail> arrayList = new PackageController().getPackageDetails(pack.getPackageId());

            String description;
            for (PackageDetail detail : arrayList) {
                description = new ItemCrudController().getFoodDescription(detail.getFoodId());
                double totalCost = detail.getQty() * detail.getUnitPrice();
                Button btn = new Button("Delete");
                PackageDetailTM tm = new PackageDetailTM(detail.getFoodId(), description, detail.getFoodType(), detail.getUnitPrice(), detail.getQty(), totalCost, btn);

                btn.setOnAction(event -> {
                    try {
                        boolean isDeleted = new PackageController().deletePackageDetail(pack.getPackageId(), tm.getFoodId(), tm.getQty());
                        new Alert(Alert.AlertType.CONFIRMATION,"Food item was removed from the package..!").show();
                        if(isDeleted){
                            tms.remove(tm);
                            ViewPackageItemsFormController.detailTbl.refresh();
                        }
                        //ViewPackageItemsFormController.detailTbl.getItems().remove(tm);
                        calculate();

                    } catch (SQLException | ClassNotFoundException e) {

                    }
                });

                tms.add(tm);
                packTms.add(tm);
            }
            calculate();
            ViewPackageItemsFormController.detailTbl.setItems(tms);
            ViewPackageItemsFormController.detailTbl.refresh();
            /*tms = null;
            tms = FXCollections.observableArrayList();*/


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void calculate() {
        double total = 0;
        for (PackageDetailTM tm : tms) {
            total += tm.getTotalCost();
        }
        lblTotalCost.setText(String.valueOf(total));
    }

    public void initialize() {
        btnUpdatePackage.setDisable(true);
        btnDeletePackage.setDisable(true);
        btnSavePackage.setDisable(true);
        btnAddItem.setDisable(true);
        cmbSelectTable.getItems().addAll("Add to cart Table", "Package details Table");

        try{
            cmbSelectTable.setValue("Add to cart Table");
            Parent load = FXMLLoader.load(getClass().getResource("../view/AddToCartTableForm.fxml"));
            anchorContext.getChildren().add(load);
        }catch (IOException e){

        }
        cmbSelectTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("Package details Table")) {
                    try {
                        anchorContext.getChildren().clear();
                        Parent load = FXMLLoader.load(getClass().getResource("../view/ViewPackageItemsForm.fxml"));
                        anchorContext.getChildren().add(load);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        cmbPackageCode.getSelectionModel().clearSelection();
                        getLastPackageId();
                        txtPackageName.clear();
                        txtPackagePrice.clear();
                        anchorContext.getChildren().clear();
                        Parent load = FXMLLoader.load(getClass().getResource("../view/AddToCartTableForm.fxml"));
                        anchorContext.getChildren().add(load);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        txtPackageCode.setDisable(true);
        getLastPackageId();
        loadFoodsData();
        loadPackages();

        cmbFoodItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setData(newValue);
            }
        });

        cmbPackageCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setPackageData(newValue);
            }
        });

        /*colFoodCode.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colFoodDes.setCellValueFactory(new PropertyValueFactory<>("foodDescription"));
        colFoodUnitPrice.setCellValueFactory(new PropertyValueFactory<>("uniPrice"));
        colFoodQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colFoodOption.setCellValueFactory(new PropertyValueFactory<>("btn"));*/
    }

    private void loadPackages() {
        try {

            cmbPackageCode.setItems(new PackageController().loadPackageCode());

        } catch (SQLException | ClassNotFoundException e) {

        }
    }

    private void setData(Food f1) {
        txtFoodType.setText(f1.getFoodType());
        txtFoodName.setText(f1.getDescription());
    }

    private PackageDetailTM isIn(String foodId) {
        for (PackageDetailTM tm : items) {
            if (tm.getFoodId().equals(foodId)) {
                return tm;
            }
        }
        return null;
    }

    public void addFoodItemOnAction(ActionEvent event) {
        if (!txtPackageCode.getText().isEmpty() && !txtPackageName.getText().isEmpty() && !txtQty.getText().isEmpty() && !cmbFoodItem.getSelectionModel().isEmpty() && !txtFoodType.getText().isEmpty() && !txtFoodName.getText().isEmpty()) {
            if (!lblQtyValidation.getText().startsWith("Invalid") && !lblPackageName.getText().startsWith("Invalid")) {
                double unitPrice = cmbFoodItem.getSelectionModel().getSelectedItem().getUnitPrice();
                int qty = Integer.parseInt(txtQty.getText());
                double totalCost = unitPrice * qty;

                PackageDetailTM isExist = isExists(cmbFoodItem.getValue().getFoodId());

                if (cmbFoodItem.getValue().getQtyOnHand() > 0) {
                    if (isExist != null) {

                        isExist.setQty(isExist.getQty() + qty);
                        isExist.setTotalCost(isExist.getTotalCost() + totalCost);

                    } else {
                        Button btn = new Button("Delete");

                        PackageDetailTM tm = new PackageDetailTM(
                                cmbFoodItem.getValue().getFoodId(),
                                txtFoodName.getText(),
                                txtFoodType.getText(),
                                unitPrice,
                                qty,
                                totalCost,
                                btn
                        );

                        try {
                            if (cmbSelectTable.getValue().equals("Package details Table") && cmbPackageCode.getValue() != null) {
                                arrayList.add(new PackageDetail(tm.getFoodId(), tm.getFoodType(), tm.getQty(), tm.getUniPrice()));
                                items = ViewPackageItemsFormController.detailTbl.getItems();

                                PackageDetailTM isE = isIn(tm.getFoodId());
                                if (isE != null) {
                                    isE.setQty(isE.getQty() + tm.getQty());
                                    isE.setTotalCost(isE.getTotalCost() + tm.getTotalCost());
                                    Package p1 = cmbPackageCode.getValue();
                                    new PackageController().savePackageDetails(p1.getPackageId(), arrayList);
                                    arrayList = null;
                                    arrayList = new ArrayList<PackageDetail>();

                                    /*2022-03-16 edited*/
                                    /*boolean b = new PackageController().updateQty(tm.getFoodId(), tm.getQty());*/
                                } else {
                                    items.add(new PackageDetailTM(tm.getFoodId(), tm.getFoodDescription(), tm.getFoodType(), tm.getUniPrice(), tm.getQty(), tm.getTotalCost(), tm.getBtn()));
                                    ViewPackageItemsFormController.detailTbl.setItems(items);
                                    ViewPackageItemsFormController.detailTbl.refresh();
                                    Package p1 = cmbPackageCode.getValue();
                                    boolean b = new PackageController().savePackageDetails(p1.getPackageId(), arrayList);
                                    arrayList = null;
                                    arrayList = new ArrayList<PackageDetail>();
                                }
                                ViewPackageItemsFormController.detailTbl.refresh();
                                //calculate();
                                cal();
                                return;
                            }
                        } catch (SQLException | ClassNotFoundException e) {

                        }

                        //new AddToCartTableFormController().addCart(tm);

                        btn.setOnAction(e -> {
                            tmList.remove(tm);
                            calculateTotal();
                        });

                        tmList.add(tm);
                        //tblFood.setItems(tmList);
                        AddToCartTableFormController.tbl.setItems(tmList);
                    }
                    AddToCartTableFormController.tbl.refresh();
                    calculateTotal();
                } else {
                    new Alert(Alert.AlertType.ERROR, "This item has zero quantity," +
                            " Therefore Choose another item..!", ButtonType.CLOSE).show();
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Check Fields Again..!", ButtonType.CLOSE).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Empty Fields try again..!", ButtonType.CLOSE).show();
        }
    }

    private void cal() {
        double total = 0;
        for (PackageDetailTM tm : items) {
            total += tm.getTotalCost();
        }
        lblTotalCost.setText(String.valueOf(total));
    }

    private PackageDetailTM isExists(String foodId) {
        for (PackageDetailTM tm : tmList) {
            if (tm.getFoodId().equals(foodId)) {
                return tm;
            }
        }
        return null;
    }

    private void calculateTotal() {
        double total = 0;
        for (PackageDetailTM tm : tmList) {
            total += tm.getTotalCost();
        }
        lblTotalCost.setText(String.valueOf(total));
    }

    public void savePackageOnAction(ActionEvent event) {
        if (!txtPackageCode.getText().isEmpty() && !txtPackageName.getText().isEmpty() && !txtPackagePrice.getText().isEmpty() && !tmList.isEmpty()) {
            if (!lblPackageName.getText().startsWith("Invalid") && !lblPackagePriceValidate.getText().startsWith("Invalid")) {
                ArrayList<PackageDetail> list = new ArrayList<>();
                for (PackageDetailTM tm : tmList) {
                    list.add(new PackageDetail(
                            tm.getFoodId(),
                            tm.getFoodType(),
                            tm.getQty(),
                            tm.getUniPrice()
                    ));
                }

                Package pack = new Package(txtPackageCode.getText(), txtPackageName.getText(), Double.parseDouble(txtPackagePrice.getText()), list);
                boolean packageIsSaved = new PackageController().addPackage(pack);
                if (packageIsSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Package saved successfully..!", ButtonType.OK).show();

                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Try again..!", ButtonType.CLOSE).show();
                }

            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Invalid Fields check Again..!", ButtonType.CLOSE).show();
            }
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Empty fields try again..!", ButtonType.CLOSE).show();
        }
        clearAll();
        getLastPackageId();
        loadPackages();
    }

    public void updatePackageOnAction(ActionEvent event) {
        try {
            if (!txtPackageCode.getText().isEmpty() && !txtPackageName.getText().isEmpty() && !txtPackagePrice.getText().isEmpty()) {
                if (!lblPackageName.getText().startsWith("Invalid") && !lblPackagePriceValidate.getText().startsWith("Invalid")) {
                    Package pack = new Package(txtPackageCode.getText(), txtPackageName.getText(), Double.parseDouble(txtPackagePrice.getText()));
                    boolean isUpdated = new PackageController().updatePackage(pack);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Package Updated successfully..!", ButtonType.OK).show();
                    } else {
                        new Alert(Alert.AlertType.CONFIRMATION, "Try again..!", ButtonType.CLOSE).show();
                    }
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Invalid Fields check Again..!", ButtonType.CLOSE).show();
                }
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
            //clearAll();
            //getLastPackageId();
            loadPackages();
            ViewPackageItemsFormController.detailTbl.refresh();
        } catch (SQLException | ClassNotFoundException e) {

        }
    }

    public void deletePackageOnAction(ActionEvent event) {
        try{
            if (!txtPackageCode.getText().isEmpty() && !txtPackageName.getText().isEmpty() && !txtPackagePrice.getText().isEmpty()) {
                if (!lblPackageName.getText().startsWith("Invalid") && !lblPackagePriceValidate.getText().startsWith("Invalid")) {
                    ArrayList<PackageDetail> detailArrayList = new PackageController().getPackageDetails(txtPackageCode.getText());
                    Package pack = new Package(txtPackageCode.getText(), txtPackageName.getText(), Double.parseDouble(txtPackagePrice.getText()), detailArrayList);
                    boolean isDeleted = new PackageController().deletePackage(pack);
                    if (isDeleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Package Deleted successfully..!", ButtonType.OK).show();
                    } else {
                        new Alert(Alert.AlertType.CONFIRMATION, "Try again..!", ButtonType.CLOSE).show();
                    }
                }else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Invalid Fields check Again..!", ButtonType.CLOSE).show();
                }
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
            clearAll();
            txtPackageCode.clear();
            //getLastPackageId();
            ViewPackageItemsFormController.detailTbl.refresh();
            ViewPackageItemsFormController.detailTbl.getItems().clear();
            loadPackages();
        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void clearAllOnAction(ActionEvent event) {
        clearAll();
        getLastPackageId();
    }

    private void clearAll() {
        txtPackagePrice.setStyle("-fx-border-color: null");
        txtQty.setStyle("-fx-border-color: null");
        txtPackageName.setStyle("-fx-border-color: null");

        lblPackagePriceValidate.setText("");
        lblQtyValidation.setText("");
        lblPackageName.setText("");

        cmbPackageCode.getSelectionModel().clearSelection();
        txtPackageName.clear();
        txtPackagePrice.clear();
        cmbFoodItem.getSelectionModel().clearSelection();
        txtFoodType.clear();
        txtFoodName.clear();
        txtQty.clear();
        AddToCartTableFormController.tbl.getItems().clear();
        tmList.clear();
        AddToCartTableFormController.tbl.refresh();
        lblTotalCost.setText("0");

        btnUpdatePackage.setDisable(true);
        btnDeletePackage.setDisable(true);
        btnSavePackage.setDisable(true);
        btnAddItem.setDisable(true);

    }

    public void qtyPlusOnAction(ActionEvent event) {
        try {
            if (Integer.parseInt(txtQty.getText()) < 150) {
                int i = Integer.parseInt(txtQty.getText());
                i++;
                txtQty.setText(String.valueOf(i));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.CONFIRMATION,"Please input a number").show();
        }
    }

    public void qtyMinusOnAction(ActionEvent event) {
        try {
            if (Integer.parseInt(txtQty.getText()) > 0) {
                int i = Integer.parseInt(txtQty.getText());
                i--;
                txtQty.setText(String.valueOf(i));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.CONFIRMATION,"Please input a number").show();
        }
    }

    public void checkQuantity(KeyEvent keyEvent) {
        try{
            Matcher matcher=null;
            String value = "^[1-9][0-9]{0,3}$";
            Pattern compile = Pattern.compile(value);
            matcher = compile.matcher(txtQty.getText());
            if (matcher.matches()) {
                txtQty.setStyle("-fx-border-color: null");
                lblQtyValidation.setText("");

                if(cmbFoodItem.getValue()!=null){
                    btnAddItem.setDisable(false);
                }

                if(!txtPackageCode.getText().isEmpty() && !txtPackageName.getText().isEmpty() && !txtPackagePrice.getText().isEmpty() ){
                    if (txtPackageCode.getStyle()!="-fx-border-color: red" && txtPackageName.getStyle()!="-fx-border-color: red" && txtPackagePrice.getStyle()!="-fx-border-color: red") {
                        btnSavePackage.setDisable(false);
                        btnUpdatePackage.setDisable(false);
                        //btnDeletePackage.setDisable(false);
                    }
                }
            } else {
                btnAddItem.setDisable(true);
                txtQty.setStyle("-fx-border-width: 1");
                txtQty.setStyle("-fx-border-color: red");
                lblQtyValidation.setText("Invalid Qty");
                btnUpdatePackage.setDisable(true);
                //btnDeletePackage.setDisable(true);
                btnSavePackage.setDisable(true);
            }
        }catch (Exception e){

        }
    }

    public void checkPackagePrice(KeyEvent keyEvent) {
        String value = "^[1-9][0-9]*(.[0-9]{2})?$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtPackagePrice.getText());
        if (matcher.matches()) {
            txtPackagePrice.setStyle("-fx-border-color: null");
            lblPackagePriceValidate.setText("");

            if(!txtPackageCode.getText().isEmpty() && !txtPackageName.getText().isEmpty() && !txtPackagePrice.getText().isEmpty() ){
                if (txtPackageCode.getStyle()!="-fx-border-color: red" && txtPackageName.getStyle()!="-fx-border-color: red" && txtPackagePrice.getStyle()!="-fx-border-color: red") {
                    btnSavePackage.setDisable(false);
                    btnUpdatePackage.setDisable(false);
                    //btnDeletePackage.setDisable(false);
                }
            }
        } else {
            txtPackagePrice.setStyle("-fx-border-width: 1");
            txtPackagePrice.setStyle("-fx-border-color: red");
            lblPackagePriceValidate.setText("Invalid Price");
            btnUpdatePackage.setDisable(true);
            //btnDeletePackage.setDisable(true);
            btnSavePackage.setDisable(true);
        }
    }

    public void checkPackageName(KeyEvent keyEvent) {
        String value="^[A-z0-9 -]{3,45}$";
        //String value = "^[A-Za-z0-9 -]+$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtPackageName.getText());
        if (matcher.matches()) {
            txtPackageName.setStyle("-fx-border-color: null");
            lblPackageName.setText("");

            if(!txtPackageCode.getText().isEmpty() && !txtPackageName.getText().isEmpty() && !txtPackagePrice.getText().isEmpty() ){
                if (txtPackageCode.getStyle()!="-fx-border-color: red" && txtPackageName.getStyle()!="-fx-border-color: red" && txtPackagePrice.getStyle()!="-fx-border-color: red") {
                    btnSavePackage.setDisable(false);
                    btnUpdatePackage.setDisable(false);
                    //btnDeletePackage.setDisable(false);
                }
            }

        } else {
            txtPackageName.setStyle("-fx-border-width: 1");
            txtPackageName.setStyle("-fx-border-color: red");
            lblPackageName.setText("Invalid Name");
            btnUpdatePackage.setDisable(true);
            //btnDeletePackage.setDisable(true);
            btnSavePackage.setDisable(true);
        }
    }

    public void deleteItemOnAction(ActionEvent event) {
        /*try {
            PackageDetailTM tm = ViewPackageItemsFormController.detailTbl.getSelectionModel().getSelectedItem();
            boolean isDeleted = new PackageController().deletePackageDetail(ViewPackageItemsFormController.detailTbl.getSelectionModel().getSelectedItem().getFoodId(), ViewPackageItemsFormController.detailTbl.getSelectionModel().getSelectedItem().getQty());
            if (isDeleted) {
                tms.remove(tm);
                ViewPackageItemsFormController.detailTbl.refresh();
                calculate();

                ViewPackageItemsFormController.detailTbl.setItems(tms);
                ViewPackageItemsFormController.detailTbl.refresh();
            }
        } catch (SQLException | ClassNotFoundException e1) {
            System.out.println(e1.getMessage());
        }*/

       /* try {
            for(PackageDetailTM tm : packTms){
                if(tm.getFoodId().equals(ViewPackageItemsFormController.detailTbl.getSelectionModel().getSelectedItem().getFoodId())){
                    System.out.println("Ashan Sandeep - 21");
                    boolean isDeleted = new PackageController().deletePackageDetail(tm.getFoodId(),tm.getQty());
                    //tms.remove(tm);
                    ViewPackageItemsFormController.detailTbl.getItems().remove(tm);
                    calculate();
                    break;
                    //ViewPackageItemsFormController.detailTbl.setItems(tms);
                }
            }
            ViewPackageItemsFormController.detailTbl.refresh();
        } catch (SQLException | ClassNotFoundException e1) {
            System.out.println(e1.getMessage());
        }*/
    }

    public void cmbPackageCodeOnAction(ActionEvent event) {
        btnDeletePackage.setDisable(false);
    }

    public void addFoodItemEnable(Event event) {
        if(cmbFoodItem.getValue()!=null){
            if(!txtQty.getText().isEmpty()){
                if(!lblQtyValidation.getText().startsWith("Invalid")){
                    btnAddItem.setDisable(false);
                }else{
                    btnAddItem.setDisable(true);
                }
            }else{
                btnAddItem.setDisable(true);
            }
        }else{
            btnAddItem.setDisable(true);
        }
    }
}
