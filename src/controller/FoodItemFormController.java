package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import model.Beverage;
import model.Burger;
import model.Meal;
import model.Pizza;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FoodItemFormController {

    public Tab burgerTab;
    public Tab mealTab;
    public Tab pizzaTab;
    public Tab drinkTab;
    public JFXComboBox<Meal> cmbMeal;
    public TextField txtMealId;
    public TextField txtMealQuantity;
    public TextField txtMealDescription;
    public TextField txtMealUnitPrice;
    public JFXComboBox<Pizza> cmbPizza;
    public TextField txtPizzaId;
    public TextField txtPizzaDescription;
    public TextField txtPizzaUnitPrice;
    public TextField txtPizzaQuantity;
    public JFXComboBox<Burger> cmbBurger;
    public TextField txtBurgerId;
    public TextField txtBurgerDescription;
    public TextField txtBurgerUnitPrice;
    public TextField txtBurgerQuantity;
    public JFXComboBox<Beverage> cmbBeverage;
    public TextField txtBevId;
    public TextField txtBevDescription;
    public TextField txtBevUnitPrice;
    public TextField txtBevQuantity;
    public Label lbl1;
    public Label lbl2;
    public Label lbl3;
    public Label lbl4;
    public Label lbl5;
    public Label lbl6;
    public Label lbl7;
    public Label lbl8;
    public Label lbl9;
    public Label lbl10;
    public Label lbl11;
    public Label lbl12;
    public Label lbl13;
    public Label lbl14;
    public Label lbl15;
    public Label lbl16;
    public JFXButton btnAddMeal;
    public JFXButton btnUpdateMeal;
    public JFXButton btnMealDelete;
    public JFXButton btnAddPizza;
    public JFXButton btnUpdatePizza;
    public JFXButton btnDeletePizza;
    public JFXButton btnAddBurger;
    public JFXButton btnUpdateBurger;
    public JFXButton btnDeleteBurger;
    public JFXButton btnAddBeverage;
    public JFXButton btnUpdateBeverage;
    public JFXButton btnDeleteBeverage;

    public void initialize() {
        btnAddMeal.setDisable(true);
        btnMealDelete.setDisable(true);
        btnUpdateMeal.setDisable(true);

        btnAddPizza.setDisable(true);
        btnUpdatePizza.setDisable(true);
        btnDeletePizza.setDisable(true);

        btnAddBurger.setDisable(true);
        btnUpdateBurger.setDisable(true);
        btnDeleteBurger.setDisable(true);

        btnAddBeverage.setDisable(true);
        btnUpdateBeverage.setDisable(true);
        btnDeleteBeverage.setDisable(true);

        setItemCodes();
        cmbMeal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setMealDetails(newValue);
            }
        });
        cmbPizza.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setPizzaDetails(newValue);
            }
        });
        cmbBurger.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setBurgerDetails(newValue);
            }
        });
        cmbBeverage.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setBeverageDetails(newValue);
            }
        });

        lastFoodId();
        txtMealId.setDisable(true);
        txtPizzaId.setDisable(true);
        txtBurgerId.setDisable(true);
        txtBevId.setDisable(true);
    }

    public void lastFoodId() {
        try {
            String foodId = new ItemCrudController().getLastFoodId();
            String finalId = "F001";

            if (foodId != null) {

                String[] splitString = foodId.split("F");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "F00" + id;
                } else if (id < 100) {
                    finalId = "F0" + id;
                } else {
                    finalId = "F" + id;
                }
                if (txtMealId != null && txtPizzaId != null && txtBurgerId != null && txtBevId != null) {
                    txtMealId.setText(finalId);
                    txtPizzaId.setText(finalId);
                    txtBurgerId.setText(finalId);
                    txtBevId.setText(finalId);
                }
            } else {
                if (txtMealId != null && txtPizzaId != null && txtBurgerId != null && txtBevId != null) {
                    txtMealId.setText(finalId);
                    txtPizzaId.setText(finalId);
                    txtBurgerId.setText(finalId);
                    txtBevId.setText(finalId);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setBeverageDetails(Beverage beverage) {
        try {
            Beverage b = new ItemCrudController().getFoodItem(beverage.getFoodId(), beverage.getFoodType());
            if (b != null) {
                txtBevId.setText(b.getFoodId());
                txtBevDescription.setText(b.getDescription());
                txtBevUnitPrice.setText(String.valueOf(b.getUnitPrice()));
                txtBevQuantity.setText(String.valueOf(b.getQtyOnHand()));
            } else {
                new Alert(Alert.AlertType.ERROR, "Empty Result..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setBurgerDetails(Burger burger) {
        try {
            Burger b = new ItemCrudController().getFoodItem(burger.getFoodId(), burger.getFoodType());
            if (b != null) {
                txtBurgerId.setText(b.getFoodId());
                txtBurgerDescription.setText(b.getDescription());
                txtBurgerUnitPrice.setText(String.valueOf(b.getUnitPrice()));
                txtBurgerQuantity.setText(String.valueOf(b.getQtyOnHand()));
            } else {
                new Alert(Alert.AlertType.ERROR, "Empty Result..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setPizzaDetails(Pizza pizza) {
        try {
            Pizza p = new ItemCrudController().getFoodItem(pizza.getFoodId(), pizza.getFoodType());
            if (p != null) {
                txtPizzaId.setText(p.getFoodId());
                txtPizzaDescription.setText(p.getDescription());
                txtPizzaUnitPrice.setText(String.valueOf(p.getUnitPrice()));
                txtPizzaQuantity.setText(String.valueOf(p.getQtyOnHand()));
            } else {
                new Alert(Alert.AlertType.ERROR, "Empty Result..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setMealDetails(Meal meal) {
        try {
            Meal m = new ItemCrudController().getFoodItem(meal.getFoodId(), meal.getFoodType());
            if (m != null) {
                txtMealId.setText(m.getFoodId());
                txtMealDescription.setText(m.getDescription());
                txtMealUnitPrice.setText(String.valueOf(m.getUnitPrice()));
                txtMealQuantity.setText(String.valueOf(m.getQtyOnHand()));
            } else {
                new Alert(Alert.AlertType.ERROR, "Empty Result..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemCodes() {
        try {
            cmbMeal.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes("Meal")));
            cmbPizza.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes("Pizza")));
            cmbBurger.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes("Burger")));
            cmbBeverage.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes("Drink")));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clearMealPage(Event event) {
        clearMeal();
        lastFoodId();
    }

    public void clearPizzaPage(Event event) {
        clearPizza();
        lastFoodId();
    }

    public void clearBurgerPage(Event event) {
        clearBurger();
        lastFoodId();
    }

    public void clearBeveragePage(Event event) {
        clearBeverage();
        lastFoodId();
    }

    public void mealAddOnAction(ActionEvent event) {
        if (txtMealQuantity.getStyle().equals("-fx-border-color: green")) {
            txtMealQuantity.setStyle("-fx-border-color: null");
        }
        if (!lbl1.getText().startsWith("Invalid") && !lbl2.getText().startsWith("Invalid") && !lbl3.getText().startsWith("Invalid") && !lbl4.getText().startsWith("Invalid")) {
            try {
                txtMealId.setStyle("-fx-border-color: null");
                txtMealDescription.setStyle("-fx-border-color: null");
                txtMealUnitPrice.setStyle("-fx-border-color: null");
                txtMealQuantity.setStyle("-fx-border-color: null");
                if(!txtMealId.getText().isEmpty() && !txtMealDescription.getText().isEmpty() && !txtMealUnitPrice.getText().isEmpty() && !txtMealQuantity.getText().isEmpty()){
                    if (new ItemCrudController().addMeal(new Meal(txtMealId.getText(), mealTab.getText(), txtMealDescription.getText(), Double.parseDouble(txtMealUnitPrice.getText()), Integer.parseInt(txtMealQuantity.getText())))) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully..!").show();
                        clearMeal();
                        txtMealId.setStyle("-fx-border-color: null");
                        txtMealDescription.setStyle("-fx-border-color: null");
                        txtMealUnitPrice.setStyle("-fx-border-color: null");
                        txtMealQuantity.setStyle("-fx-border-color: null");
                        lbl1.setText("");
                        lbl2.setText("");
                        lbl3.setText("");
                        lbl4.setText("");
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.OK).show();
                        clearMeal();
                        txtMealId.setStyle("-fx-border-color: null");
                        txtMealDescription.setStyle("-fx-border-color: null");
                        txtMealUnitPrice.setStyle("-fx-border-color: null");
                        txtMealQuantity.setStyle("-fx-border-color: null");
                        lbl1.setText("");
                        lbl2.setText("");
                        lbl3.setText("");
                        lbl4.setText("");
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Empty fields,try again..!").show();
                }
            } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                //e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Try again..!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
            /*txtMealId.setStyle("-fx-border-color: null");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealQuantity.setStyle("-fx-border-color: null");*/
            //lastFoodId();
            //clearMeal();
        }
        lastFoodId();

        try {
            cmbMeal.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes(mealTab.getText())));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void mealUpdateOnAction(ActionEvent event) {
        if (txtMealQuantity.getStyle().equals("-fx-border-color: green")) {
            txtMealQuantity.setStyle("-fx-border-color: null");
        }
        if (!lbl1.getText().startsWith("Invalid") && !lbl2.getText().startsWith("Invalid") && !lbl3.getText().startsWith("Invalid") && !lbl4.getText().startsWith("Invalid")) {
            try {
                txtMealId.setStyle("-fx-border-color: null");
                txtMealDescription.setStyle("-fx-border-color: null");
                txtMealUnitPrice.setStyle("-fx-border-color: null");
                txtMealQuantity.setStyle("-fx-border-color: null");
                if (!txtMealId.getText().isEmpty() && !txtMealDescription.getText().isEmpty() && !txtMealUnitPrice.getText().isEmpty() && !txtMealQuantity.getText().isEmpty()){
                if (new ItemCrudController().updateMeal(new Meal(txtMealId.getText(), mealTab.getText(), txtMealDescription.getText(), Double.parseDouble(txtMealUnitPrice.getText()), Integer.parseInt(txtMealQuantity.getText())), cmbMeal.getValue().getFoodId())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully..!").show();
                    clearMeal();
                    txtMealId.setStyle("-fx-border-color: null");
                    txtMealDescription.setStyle("-fx-border-color: null");
                    txtMealUnitPrice.setStyle("-fx-border-color: null");
                    txtMealQuantity.setStyle("-fx-border-color: null");
                    lbl1.setText("");
                    lbl2.setText("");
                    lbl3.setText("");
                    lbl4.setText("");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.OK).show();
                    clearMeal();
                    txtMealId.setStyle("-fx-border-color: null");
                    txtMealDescription.setStyle("-fx-border-color: null");
                    txtMealUnitPrice.setStyle("-fx-border-color: null");
                    txtMealQuantity.setStyle("-fx-border-color: null");
                    lbl1.setText("");
                    lbl2.setText("");
                    lbl3.setText("");
                    lbl4.setText("");
                }
            }else{
                    new Alert(Alert.AlertType.ERROR, "Empty fields,try again..!").show();
                }
            } catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException e) {
                //e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Try again..!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
            /*txtMealId.setStyle("-fx-border-color: null");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealQuantity.setStyle("-fx-border-color: null");*/
        }
        lastFoodId();
    }

    public void mealDeleteOnAction(ActionEvent event) {
            if (!txtMealId.getText().isEmpty() && !txtMealDescription.getText().isEmpty() && !txtMealUnitPrice.getText().isEmpty() && !txtMealQuantity.getText().isEmpty()){
                if (!lbl1.getText().startsWith("Invalid") && !lbl2.getText().startsWith("Invalid") && !lbl3.getText().startsWith("Invalid") && !lbl4.getText().startsWith("Invalid")) {
                    try {
                        if (new ItemCrudController().deleteMeal(cmbMeal.getValue().getFoodId())) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully..!").show();
                            clearMeal();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Try again..!").show();
                            clearMeal();
                        }
                    } catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException e) {
                        new Alert(Alert.AlertType.ERROR, "Try again..!").show();
                    }
                    lastFoodId();
                    try {
                        cmbMeal.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes(mealTab.getText())));
                    } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                        //e.printStackTrace();
                    }
                }else {
                     new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
            /*txtMealId.setStyle("-fx-border-color: null");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealQuantity.setStyle("-fx-border-color: null");*/
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Empty fields,try again..!").show();
            }
            lastFoodId();
      }

    public void mealClearSelectionOnAction(ActionEvent event) {
        clearMeal();
        lastFoodId();
        txtMealId.setStyle("-fx-border-color: null");
        txtMealDescription.setStyle("-fx-border-color: null");
        txtMealUnitPrice.setStyle("-fx-border-color: null");
        txtMealQuantity.setStyle("-fx-border-color: null");
        lbl1.setText("");
        lbl2.setText("");
        lbl3.setText("");
        lbl4.setText("");
        //System.out.println(txtMealId.getText());
    }

    public void pizzaAddOnAction(ActionEvent event) {
        if (txtPizzaQuantity.getStyle().equals("-fx-border-color: green")) {
            txtPizzaQuantity.setStyle("-fx-border-color: null");
        }
        if (!lbl5.getText().startsWith("Invalid") && !lbl6.getText().startsWith("Invalid") && !lbl7.getText().startsWith("Invalid") && !lbl8.getText().startsWith("Invalid")) {
            try {
                txtPizzaId.setStyle("-fx-border-color: null");
                txtPizzaDescription.setStyle("-fx-border-color: null");
                txtPizzaUnitPrice.setStyle("-fx-border-color: null");
                txtPizzaQuantity.setStyle("-fx-border-color: null");
                if (!txtPizzaId.getText().isEmpty() && !txtPizzaDescription.getText().isEmpty() && !txtPizzaUnitPrice.getText().isEmpty() && !txtPizzaQuantity.getText().isEmpty()){
                if (new ItemCrudController().addPizza(new Pizza(txtPizzaId.getText(), pizzaTab.getText(), txtPizzaDescription.getText(), Double.parseDouble(txtPizzaUnitPrice.getText()), Integer.parseInt(txtPizzaQuantity.getText())))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully..!").show();
                    clearPizza();
                    txtPizzaId.setStyle("-fx-border-color: null");
                    txtPizzaDescription.setStyle("-fx-border-color: null");
                    txtPizzaUnitPrice.setStyle("-fx-border-color: null");
                    txtPizzaQuantity.setStyle("-fx-border-color: null");
                    lbl5.setText("");
                    lbl6.setText("");
                    lbl7.setText("");
                    lbl8.setText("");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.OK).show();
                    txtPizzaId.setStyle("-fx-border-color: null");
                    txtPizzaDescription.setStyle("-fx-border-color: null");
                    txtPizzaUnitPrice.setStyle("-fx-border-color: null");
                    txtPizzaQuantity.setStyle("-fx-border-color: null");
                    lbl5.setText("");
                    lbl6.setText("");
                    lbl7.setText("");
                    lbl8.setText("");
                    clearPizza();
                }
            }else{
                    new Alert(Alert.AlertType.ERROR, "Empty fields..!").show();
                }
            } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                //e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Try again..!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
            /*txtMealId.setStyle("-fx-border-color: null");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealQuantity.setStyle("-fx-border-color: null");*/
            //lastFoodId();
            //clearPizza();
        }

        lastFoodId();
        try {
            cmbPizza.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes(pizzaTab.getText())));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void pizzaUpdateOnAction(ActionEvent event) {
        if (txtPizzaQuantity.getStyle().equals("-fx-border-color: green")) {
            txtPizzaQuantity.setStyle("-fx-border-color: null");
        }
        if (!lbl5.getText().startsWith("Invalid") && !lbl6.getText().startsWith("Invalid") && !lbl7.getText().startsWith("Invalid") && !lbl8.getText().startsWith("Invalid")) {
            try {
                txtPizzaId.setStyle("-fx-border-color: null");
                txtPizzaDescription.setStyle("-fx-border-color: null");
                txtPizzaUnitPrice.setStyle("-fx-border-color: null");
                txtPizzaQuantity.setStyle("-fx-border-color: null");
                if (!txtPizzaId.getText().isEmpty() && !txtPizzaDescription.getText().isEmpty() && !txtPizzaUnitPrice.getText().isEmpty() && !txtPizzaQuantity.getText().isEmpty()){
                    if (new ItemCrudController().updatePizza(new Pizza(txtPizzaId.getText(), pizzaTab.getText(), txtPizzaDescription.getText(), Double.parseDouble(txtPizzaUnitPrice.getText()), Integer.parseInt(txtPizzaQuantity.getText())), cmbPizza.getValue().getFoodId())) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully..!").show();
                        clearPizza();
                        txtPizzaId.setStyle("-fx-border-color: null");
                        txtPizzaDescription.setStyle("-fx-border-color: null");
                        txtPizzaUnitPrice.setStyle("-fx-border-color: null");
                        txtPizzaQuantity.setStyle("-fx-border-color: null");
                        lbl5.setText("");
                        lbl6.setText("");
                        lbl7.setText("");
                        lbl8.setText("");
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.OK).show();
                        clearPizza();
                        txtPizzaId.setStyle("-fx-border-color: null");
                        txtPizzaDescription.setStyle("-fx-border-color: null");
                        txtPizzaUnitPrice.setStyle("-fx-border-color: null");
                        txtPizzaQuantity.setStyle("-fx-border-color: null");
                        lbl5.setText("");
                        lbl6.setText("");
                        lbl7.setText("");
                        lbl8.setText("");
                    }
            }else{
                    new Alert(Alert.AlertType.ERROR, "Empty fields,try again..!").show();
                }
            } catch (SQLException | ClassNotFoundException | NumberFormatException| NullPointerException e) {
                //e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Try again..!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
            /*txtMealId.setStyle("-fx-border-color: null");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealQuantity.setStyle("-fx-border-color: null");*/
        }
        lastFoodId();
    }

    public void pizzaDeleteOnAction(ActionEvent event) {
        if (!txtPizzaId.getText().isEmpty() && !txtPizzaDescription.getText().isEmpty() && !txtPizzaUnitPrice.getText().isEmpty() && !txtPizzaQuantity.getText().isEmpty()) {
            if (!lbl1.getText().startsWith("Invalid") && !lbl2.getText().startsWith("Invalid") && !lbl3.getText().startsWith("Invalid") && !lbl4.getText().startsWith("Invalid")) {
                try {

                    if (new ItemCrudController().deletePizza(cmbPizza.getValue().getFoodId())) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully..!").show();
                        clearPizza();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Try again..!").show();
                        clearPizza();
                    }
                } catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException e) {
                    new Alert(Alert.AlertType.ERROR, "Try again..!").show();
                }
                try {
                    cmbPizza.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes(pizzaTab.getText())));
                } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                    //e.printStackTrace();
                }
            }else{
                new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Empty fields,try again..!").show();
        }
        lastFoodId();
    }

    public void pizzaClearSelectionOnAction(ActionEvent event) {
        clearPizza();
        lastFoodId();
        txtPizzaId.setStyle("-fx-border-color: null");
        txtPizzaDescription.setStyle("-fx-border-color: null");
        txtPizzaUnitPrice.setStyle("-fx-border-color: null");
        txtPizzaQuantity.setStyle("-fx-border-color: null");
        lbl5.setText("");
        lbl6.setText("");
        lbl7.setText("");
        lbl8.setText("");
    }

    public void burgerAddOnAction(ActionEvent event) {
        if (txtBurgerQuantity.getStyle().equals("-fx-border-color: green")) {
            txtBurgerQuantity.setStyle("-fx-border-color: null");
        }
        if (!lbl9.getText().startsWith("Invalid") && !lbl10.getText().startsWith("Invalid") && !lbl11.getText().startsWith("Invalid") && !lbl12.getText().startsWith("Invalid")) {
            try {
                txtBurgerId.setStyle("-fx-border-color: null");
                txtBurgerDescription.setStyle("-fx-border-color: null");
                txtBurgerUnitPrice.setStyle("-fx-border-color: null");
                txtBurgerQuantity.setStyle("-fx-border-color: null");
                if (!txtBurgerId.getText().isEmpty() && !txtBurgerDescription.getText().isEmpty() && !txtBurgerUnitPrice.getText().isEmpty() && !txtBurgerQuantity.getText().isEmpty()){
                    if (new ItemCrudController().addBurger(new Burger(txtBurgerId.getText(), burgerTab.getText(), txtBurgerDescription.getText(), Double.parseDouble(txtBurgerUnitPrice.getText()), Integer.parseInt(txtBurgerQuantity.getText())))) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully..!").show();
                        clearBurger();
                        txtBurgerId.setStyle("-fx-border-color: null");
                        txtBurgerDescription.setStyle("-fx-border-color: null");
                        txtBurgerUnitPrice.setStyle("-fx-border-color: null");
                        txtBurgerQuantity.setStyle("-fx-border-color: null");
                        lbl9.setText("");
                        lbl10.setText("");
                        lbl11.setText("");
                        lbl12.setText("");
                    } else {
                        clearBurger();
                        new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.OK).show();
                        txtBurgerId.setStyle("-fx-border-color: null");
                        txtBurgerDescription.setStyle("-fx-border-color: null");
                        txtBurgerUnitPrice.setStyle("-fx-border-color: null");
                        txtBurgerQuantity.setStyle("-fx-border-color: null");
                        lbl9.setText("");
                        lbl10.setText("");
                        lbl11.setText("");
                        lbl12.setText("");
                    }
            }else{
                    new Alert(Alert.AlertType.ERROR, "Empty fields..!").show();
                }
            } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                //e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Try again..!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
            /*txtMealId.setStyle("-fx-border-color: null");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealQuantity.setStyle("-fx-border-color: null");*/
        }

        try {
            cmbBurger.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes(burgerTab.getText())));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        lastFoodId();
    }

    public void burgerUpdateOnAction(ActionEvent event) {
        if (txtBurgerQuantity.getStyle().equals("-fx-border-color: green")) {
            txtBurgerQuantity.setStyle("-fx-border-color: null");
        }
        if (!lbl9.getText().startsWith("Invalid") && !lbl10.getText().startsWith("Invalid") && !lbl11.getText().startsWith("Invalid") && !lbl12.getText().startsWith("Invalid")) {
            try {
                txtBurgerId.setStyle("-fx-border-color: null");
                txtBurgerDescription.setStyle("-fx-border-color: null");
                txtBurgerUnitPrice.setStyle("-fx-border-color: null");
                txtBurgerQuantity.setStyle("-fx-border-color: null");
                if (!txtBurgerId.getText().isEmpty() && !txtBurgerDescription.getText().isEmpty() && !txtBurgerUnitPrice.getText().isEmpty() && !txtBurgerQuantity.getText().isEmpty()){
                    if (new ItemCrudController().updateBurger(new Burger(txtBurgerId.getText(), burgerTab.getText(), txtBurgerDescription.getText(), Double.parseDouble(txtBurgerUnitPrice.getText()), Integer.parseInt(txtBurgerQuantity.getText())), cmbBurger.getValue().getFoodId())) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully..!").show();
                        txtBurgerId.setStyle("-fx-border-color: null");
                        txtBurgerDescription.setStyle("-fx-border-color: null");
                        txtBurgerUnitPrice.setStyle("-fx-border-color: null");
                        txtBurgerQuantity.setStyle("-fx-border-color: null");
                        lbl9.setText("");
                        lbl10.setText("");
                        lbl11.setText("");
                        lbl12.setText("");
                        clearBurger();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.OK).show();
                        txtBurgerId.setStyle("-fx-border-color: null");
                        txtBurgerDescription.setStyle("-fx-border-color: null");
                        txtBurgerUnitPrice.setStyle("-fx-border-color: null");
                        txtBurgerQuantity.setStyle("-fx-border-color: null");
                        lbl9.setText("");
                        lbl10.setText("");
                        lbl11.setText("");
                        lbl12.setText("");
                        clearBurger();
                    }
            }else{
                    new Alert(Alert.AlertType.ERROR, "Empty fields,try again..!").show();
                }
            } catch (SQLException | ClassNotFoundException | NumberFormatException| NullPointerException e) {
                //e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Try again..!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
            /*txtMealId.setStyle("-fx-border-color: null");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealQuantity.setStyle("-fx-border-color: null");*/
        }
        lastFoodId();
    }

    public void burgerDeleteOnAction(ActionEvent event) {
        if (!lbl9.getText().startsWith("Invalid") && !lbl10.getText().startsWith("Invalid") && !lbl11.getText().startsWith("Invalid") && !lbl12.getText().startsWith("Invalid")) {
            if (!txtBurgerId.getText().isEmpty() && !txtBurgerDescription.getText().isEmpty() && !txtBurgerUnitPrice.getText().isEmpty() && !txtBurgerQuantity.getText().isEmpty()){
                try {
                    if (new ItemCrudController().deleteBurger(cmbBurger.getValue().getFoodId())) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully..!").show();
                        clearBurger();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Try again..!").show();
                        clearBurger();
                    }
                } catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException e) {
                    new Alert(Alert.AlertType.ERROR, "Try again..!").show();
                }
                lastFoodId();
                try {
                    cmbBurger.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes(burgerTab.getText())));
                } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                    //e.printStackTrace();
                }
            }else{
                new Alert(Alert.AlertType.WARNING, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
        }
        lastFoodId();
    }

    public void burgerClearSelectionOnAction(ActionEvent event) {
        clearBurger();
        lastFoodId();
        txtBurgerId.setStyle("-fx-border-color: null");
        txtBurgerDescription.setStyle("-fx-border-color: null");
        txtBurgerUnitPrice.setStyle("-fx-border-color: null");
        txtBurgerQuantity.setStyle("-fx-border-color: null");
        lbl9.setText("");
        lbl10.setText("");
        lbl11.setText("");
        lbl12.setText("");
    }

    public void beverageAddOnAction(ActionEvent event) {
        if (txtBevQuantity.getStyle().equals("-fx-border-color: green")) {
            txtBevQuantity.setStyle("-fx-border-color: null");
        }
        if (!lbl13.getText().startsWith("Invalid") && !lbl14.getText().startsWith("Invalid") && !lbl15.getText().startsWith("Invalid") && !lbl16.getText().startsWith("Invalid")) {
            try {
                txtBevId.setStyle("-fx-border-color: null");
                txtBevDescription.setStyle("-fx-border-color: null");
                txtBevUnitPrice.setStyle("-fx-border-color: null");
                txtBevQuantity.setStyle("-fx-border-color: null");
                if (!txtBevId.getText().isEmpty() && !txtBevDescription.getText().isEmpty() && !txtBevUnitPrice.getText().isEmpty() && !txtBevQuantity.getText().isEmpty()){
                    if (new ItemCrudController().addBeverage(new Beverage(txtBevId.getText(), drinkTab.getText(), txtBevDescription.getText(), Double.parseDouble(txtBevUnitPrice.getText()), Integer.parseInt(txtBevQuantity.getText())))) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully..!").show();
                        clearBeverage();
                        txtBevId.setStyle("-fx-border-color: null");
                        txtBevDescription.setStyle("-fx-border-color: null");
                        txtBevUnitPrice.setStyle("-fx-border-color: null");
                        txtBevQuantity.setStyle("-fx-border-color: null");
                        lbl13.setText("");
                        lbl14.setText("");
                        lbl15.setText("");
                        lbl16.setText("");
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.OK).show();
                        clearBeverage();
                        txtBevId.setStyle("-fx-border-color: null");
                        txtBevDescription.setStyle("-fx-border-color: null");
                        txtBevUnitPrice.setStyle("-fx-border-color: null");
                        txtBevQuantity.setStyle("-fx-border-color: null");
                        lbl13.setText("");
                        lbl14.setText("");
                        lbl15.setText("");
                        lbl16.setText("");
                    }
            }else{
                    new Alert(Alert.AlertType.ERROR, "Empty fields..!").show();
                }
            } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                //e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Try again..!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
            /*txtMealId.setStyle("-fx-border-color: null");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealQuantity.setStyle("-fx-border-color: null");*/
        }

        lastFoodId();
        try {
            cmbBeverage.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes(drinkTab.getText())));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void beverageUpdateOnAction(ActionEvent event) {
        if (txtBevQuantity.getStyle().equals("-fx-border-color: green")) {
            txtBevQuantity.setStyle("-fx-border-color: null");
        }
        if (!lbl13.getText().startsWith("Invalid") && !lbl14.getText().startsWith("Invalid") && !lbl15.getText().startsWith("Invalid") && !lbl16.getText().startsWith("Invalid")) {
            try {
                txtBevId.setStyle("-fx-border-color: null");
                txtBevDescription.setStyle("-fx-border-color: null");
                txtBevUnitPrice.setStyle("-fx-border-color: null");
                txtBevQuantity.setStyle("-fx-border-color: null");
                if (!txtBevId.getText().isEmpty() && !txtBevDescription.getText().isEmpty() && !txtBevUnitPrice.getText().isEmpty() && !txtBevQuantity.getText().isEmpty()){
                if (new ItemCrudController().updateBeverage(new Beverage(txtBevId.getText(), drinkTab.getText(), txtBevDescription.getText(), Double.parseDouble(txtBevUnitPrice.getText()), Integer.parseInt(txtBevQuantity.getText())), cmbBeverage.getValue().getFoodId())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated successfully..!").show();
                    txtBevId.setStyle("-fx-border-color: null");
                    clearBeverage();
                    txtBevDescription.setStyle("-fx-border-color: null");
                    txtBevUnitPrice.setStyle("-fx-border-color: null");
                    txtBevQuantity.setStyle("-fx-border-color: null");
                    lbl13.setText("");
                    lbl14.setText("");
                    lbl15.setText("");
                    lbl16.setText("");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Try again..!", ButtonType.OK).show();
                    clearBeverage();
                    txtBevId.setStyle("-fx-border-color: null");
                    txtBevDescription.setStyle("-fx-border-color: null");
                    txtBevUnitPrice.setStyle("-fx-border-color: null");
                    txtBevQuantity.setStyle("-fx-border-color: null");
                    lbl13.setText("");
                    lbl14.setText("");
                    lbl15.setText("");
                    lbl16.setText("");
                }
            }else{
                    new Alert(Alert.AlertType.WARNING, "Empty fields try again..!", ButtonType.CLOSE).show();
                }
            } catch (SQLException | ClassNotFoundException | NumberFormatException| NullPointerException e) {
                //e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Try again..!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
            /*txtMealId.setStyle("-fx-border-color: null");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealQuantity.setStyle("-fx-border-color: null");*/
        }
        lastFoodId();
    }

    public void beverageDeleteOnAction(ActionEvent event) {
        if (!lbl13.getText().startsWith("Invalid") && !lbl14.getText().startsWith("Invalid") && !lbl15.getText().startsWith("Invalid") && !lbl16.getText().startsWith("Invalid")) {
            if (!txtBevId.getText().isEmpty() && !txtBevDescription.getText().isEmpty() && !txtBevUnitPrice.getText().isEmpty() && !txtBevQuantity.getText().isEmpty()){
                try {
                    if (new ItemCrudController().deleteBeverage(cmbBeverage.getValue().getFoodId())) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Deleted successfully..!").show();
                        clearBeverage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Try again..!").show();
                        clearBeverage();
                    }
                } catch (SQLException | ClassNotFoundException | NumberFormatException | NullPointerException e) {
                    new Alert(Alert.AlertType.ERROR, "Try again..!").show();
                }
                try {
                    cmbBeverage.setItems(FXCollections.observableArrayList(new ItemCrudController().getFoodCodes(drinkTab.getText())));
                } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
                    //e.printStackTrace();
                }
            }else{
                new Alert(Alert.AlertType.WARNING, "Empty fields try again..!", ButtonType.CLOSE).show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please enter valid values to the textfields..!", ButtonType.CLOSE).show();
        }
        lastFoodId();
    }

    public void beverageClearSelectionOnAction(ActionEvent event) {
        clearBeverage();
        lastFoodId();
        txtBevId.setStyle("-fx-border-color: null");
        txtBevDescription.setStyle("-fx-border-color: null");
        txtBevUnitPrice.setStyle("-fx-border-color: null");
        txtBevQuantity.setStyle("-fx-border-color: null");
        lbl13.setText("");
        lbl14.setText("");
        lbl15.setText("");
        lbl16.setText("");
    }

    private void clearMeal() {
        txtMealId.clear();
        txtMealDescription.clear();
        txtMealUnitPrice.clear();
        txtMealQuantity.clear();
        cmbMeal.getSelectionModel().clearSelection();
        btnAddMeal.setDisable(true);
        btnUpdateMeal.setDisable(true);
        btnMealDelete.setDisable(true);
    }

    private void clearPizza() {
        txtPizzaId.clear();
        txtPizzaDescription.clear();
        txtPizzaQuantity.clear();
        txtPizzaUnitPrice.clear();
        cmbPizza.getSelectionModel().clearSelection();
        btnAddPizza.setDisable(true);
        btnUpdatePizza.setDisable(true);
        btnDeletePizza.setDisable(true);
    }

    private void clearBurger() {
        txtBurgerId.clear();
        txtBurgerDescription.clear();
        txtBurgerQuantity.clear();
        txtBurgerUnitPrice.clear();
        cmbBurger.getSelectionModel().clearSelection();
        btnAddBurger.setDisable(true);
        btnUpdateBurger.setDisable(true);
        btnDeleteBurger.setDisable(true);
    }

    private void clearBeverage() {
        txtBevId.clear();
        txtBevDescription.clear();
        txtBevUnitPrice.clear();
        txtBevQuantity.clear();
        cmbBeverage.getSelectionModel().clearSelection();
        btnAddBeverage.setDisable(true);
        btnUpdateBeverage.setDisable(true);
        btnDeleteBeverage.setDisable(true);
    }

    public void checkMealId(KeyEvent keyEvent) {
        String value = "^([F]{1}([0-9]{3,5}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtMealId.getText());
        if (matcher.matches()) {
            lbl1.setText("");
            txtMealId.setStyle("-fx-border-color: green");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealQuantity.setStyle("-fx-border-color: null");

            btnAddMeal.setDisable(false);
            btnUpdateMeal.setDisable(false);

            if (lbl2.getText().startsWith("Invalid") || txtMealDescription.getText().length()==0) {
                if(txtMealDescription.getText().length()==0){
                    lbl2.setText("*This field is required");
                }
                txtMealDescription.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl2.setText(" ");
            }
            if (lbl3.getText().startsWith("Invalid") || txtMealUnitPrice.getText().length()==0) {
                if(txtMealUnitPrice.getText().length()==0){
                    lbl3.setText("*This field is required");
                }
                txtMealUnitPrice.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl3.setText(" ");
            }
            if (lbl4.getText().startsWith("Invalid")|| txtMealQuantity.getText().length()==0) {
                if(txtMealQuantity.getText().length()==0){
                    lbl4.setText("*This field is required");
                }
                txtMealQuantity.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl4.setText(" ");
            }
        } else {
            lbl1.setText("Invalid Food Id..!");
            if(txtMealId.getText().isEmpty()){
                lbl1.setText("*This field is required");
            }
            txtMealId.setStyle("-fx-border-color: red");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealQuantity.setStyle("-fx-border-color: null");

            btnAddMeal.setDisable(true);
            btnUpdateMeal.setDisable(true);

            if (lbl2.getText().startsWith("Invalid") || txtMealDescription.getText().length()==0) {
                if(txtMealDescription.getText().length()==0){
                    lbl2.setText("*This field is required");
                }
                txtMealDescription.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl2.setText(" ");
            }
            if (lbl3.getText().startsWith("Invalid") || txtMealUnitPrice.getText().length()==0) {
                if(txtMealUnitPrice.getText().length()==0){
                    lbl3.setText("*This field is required");
                }
                txtMealUnitPrice.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl3.setText(" ");
            }
            if (lbl4.getText().startsWith("Invalid")|| txtMealQuantity.getText().length()==0) {
                if(txtMealQuantity.getText().length()==0){
                    lbl4.setText("*This field is required");
                }
                txtMealQuantity.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl4.setText(" ");
            }
        }
    }

    public void checkMealQuantity(KeyEvent keyEvent) {
        String value = "^[0-9]{1,4}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtMealQuantity.getText());
        if (matcher.matches()) {
            lbl4.setText("");
            txtMealQuantity.setStyle("-fx-border-color: green");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealId.setStyle("-fx-border-color: null");

            btnAddMeal.setDisable(false);
            btnUpdateMeal.setDisable(false);

            if (lbl2.getText().startsWith("Invalid")|| txtMealDescription.getText().length()==0) {
                if(txtMealDescription.getText().length()==0){
                    lbl2.setText("*This field is required");
                }

                txtMealDescription.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl2.setText(" ");
            }
            if (lbl3.getText().startsWith("Invalid")|| txtMealUnitPrice.getText().length()==0) {
                if(txtMealUnitPrice.getText().length()==0){
                    lbl3.setText("*This field is required");
                }

                txtMealUnitPrice.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl3.setText(" ");
            }
            if (lbl1.getText().startsWith("Invalid")|| txtMealId.getText().length()==0) {
                if(txtMealId.getText().length()==0){
                    lbl1.setText("*This field is required");
                }

                txtMealId.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl1.setText(" ");
            }
        } else {
            lbl4.setText("Invalid Quantity..!");
            if(txtMealQuantity.getText().isEmpty()){
                lbl4.setText("*This field is required");
            }
            txtMealQuantity.setStyle("-fx-border-color: red");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealId.setStyle("-fx-border-color: null");

            btnAddMeal.setDisable(true);
            btnUpdateMeal.setDisable(true);

            if (lbl2.getText().startsWith("Invalid")|| txtMealDescription.getText().length()==0) {
                if(txtMealDescription.getText().length()==0){
                    lbl2.setText("*This field is required");
                }

                txtMealDescription.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl2.setText(" ");
            }
            if (lbl3.getText().startsWith("Invalid")|| txtMealUnitPrice.getText().length()==0) {
                if(txtMealUnitPrice.getText().length()==0){
                    lbl3.setText("*This field is required");
                }

                txtMealUnitPrice.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl3.setText(" ");
            }
            if (lbl1.getText().startsWith("Invalid")|| txtMealId.getText().length()==0) {
                if(txtMealId.getText().length()==0){
                    lbl1.setText("*This field is required");
                }

                txtMealId.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl1.setText(" ");
            }
        }
    }

    public void checkMealDescription(KeyEvent keyEvent) {
        String value = "^[A-Za-z- /&]{3,50}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtMealDescription.getText());
        if (matcher.matches()) {
            lbl2.setText("");
            txtMealDescription.setStyle("-fx-border-color: green");
            txtMealQuantity.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealId.setStyle("-fx-border-color: null");

            btnAddMeal.setDisable(false);
            btnUpdateMeal.setDisable(false);

            if (lbl4.getText().startsWith("Invalid")|| txtMealQuantity.getText().length()==0) {
                if(txtMealQuantity.getText().length()==0){
                    lbl4.setText("*This field is required");
                }
                txtMealQuantity.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl4.setText("");
            }
            if (lbl3.getText().startsWith("Invalid")|| txtMealUnitPrice.getText().length()==0) {
                if(txtMealUnitPrice.getText().length()==0){
                    lbl3.setText("*This field is required");
                }
                txtMealUnitPrice.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl3.setText("");
            }
            if (lbl1.getText().startsWith("Invalid")|| txtMealId.getText().length()==0) {
                if(txtMealId.getText().length()==0){
                    lbl1.setText("*This field is required");
                }
                txtMealId.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl1.setText("");
            }
        } else {
            lbl2.setText("Invalid Food description..!");
            if(txtMealDescription.getText().isEmpty()){
                lbl2.setText("*This field is required");
            }
            txtMealDescription.setStyle("-fx-border-color: red");
            txtMealQuantity.setStyle("-fx-border-color: null");
            txtMealUnitPrice.setStyle("-fx-border-color: null");
            txtMealId.setStyle("-fx-border-color: null");

            btnAddMeal.setDisable(true);
            btnUpdateMeal.setDisable(true);

            if (lbl4.getText().startsWith("Invalid")|| txtMealQuantity.getText().length()==0) {
                if(txtMealQuantity.getText().length()==0){
                    lbl4.setText("*This field is required");
                }
                txtMealQuantity.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl4.setText("");
            }
            if (lbl3.getText().startsWith("Invalid")|| txtMealUnitPrice.getText().length()==0) {
                if(txtMealUnitPrice.getText().length()==0){
                    lbl3.setText("*This field is required");
                }
                txtMealUnitPrice.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl3.setText("");
            }
            if (lbl1.getText().startsWith("Invalid")|| txtMealId.getText().length()==0) {
                if(txtMealId.getText().length()==0){
                    lbl1.setText("*This field is required");
                }
                txtMealId.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl1.setText("");
            }
        }
    }

    public void checkMealUnitPrice(KeyEvent keyEvent) {
        String value = "^[1-9][0-9]*(.[0-9]{2})?$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtMealUnitPrice.getText());
        if (matcher.matches()) {
            lbl3.setText("");
            txtMealUnitPrice.setStyle("-fx-border-color: green");
            txtMealQuantity.setStyle("-fx-border-color: null");
            txtMealDescription.setStyle("-fx-border-color: null");
            txtMealId.setStyle("-fx-border-color: null");

            btnAddMeal.setDisable(false);
            btnUpdateMeal.setDisable(false);

            if (lbl4.getText().startsWith("Invalid")|| txtMealQuantity.getText().length()==0) {
                if(txtMealQuantity.getText().length()==0){
                    lbl4.setText("*This field is required");
                }

                txtMealQuantity.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl4.setText(" ");
            }
            if (lbl2.getText().startsWith("Invalid")|| txtMealDescription.getText().length()==0) {
                if(txtMealDescription.getText().length()==0){
                    lbl2.setText("*This field is required");
                }

                txtMealDescription.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl2.setText(" ");
            }
            if (lbl1.getText().startsWith("Invalid")|| txtMealId.getText().length()==0) {
                if(txtMealId.getText().length()==0){
                    lbl1.setText("*This field is required");
                }

                txtMealId.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl1.setText(" ");
            }
        } else {
            lbl3.setText("Invalid unit price..!");
            if(txtMealUnitPrice.getText().isEmpty()){
                lbl3.setText("*This field is required");
            }
            txtMealUnitPrice.setStyle("-fx-border-color: red");
            txtMealQuantity.setStyle("-fx-border-color: null");
            txtMealDescription.setStyle("-fx-border-color: null");

            btnAddMeal.setDisable(true);
            btnUpdateMeal.setDisable(true);

            if (lbl4.getText().startsWith("Invalid")|| txtMealQuantity.getText().length()==0) {
                if(txtMealQuantity.getText().length()==0){
                    lbl4.setText("*This field is required");
                }

                txtMealQuantity.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl4.setText(" ");
            }
            if (lbl2.getText().startsWith("Invalid")|| txtMealDescription.getText().length()==0) {
                if(txtMealDescription.getText().length()==0){
                    lbl2.setText("*This field is required");
                }

                txtMealDescription.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl2.setText(" ");
            }
            if (lbl1.getText().startsWith("Invalid")|| txtMealId.getText().length()==0) {
                if(txtMealId.getText().length()==0){
                    lbl1.setText("*This field is required");
                }

                txtMealId.setStyle("-fx-border-color: red");
                btnAddMeal.setDisable(true);
                btnUpdateMeal.setDisable(true);
            }else{
                lbl1.setText(" ");
            }
        }
    }

    public void txtMealIdOnAction(ActionEvent event) {
        txtMealDescription.requestFocus();
        txtMealId.setStyle("-fx-border-color: null");
    }

    public void txtMealDesOnAction(ActionEvent event) {
        txtMealUnitPrice.requestFocus();
        txtMealDescription.setStyle("-fx-border-color: null");
    }

    public void txtUnitPriceOnAction(ActionEvent event) {
        txtMealQuantity.requestFocus();
        txtMealUnitPrice.setStyle("-fx-border-color: null");
    }

    public void checkPizzaId(KeyEvent keyEvent) {
        String value = "^([F]{1}([0-9]{3,5}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtPizzaId.getText());
        if (matcher.matches()) {
            lbl5.setText("");
            txtPizzaId.setStyle("-fx-border-color: green");
            txtPizzaDescription.setStyle("-fx-border-color: null");
            txtPizzaUnitPrice.setStyle("-fx-border-color: null");
            txtPizzaQuantity.setStyle("-fx-border-color: null");


            btnAddPizza.setDisable(false);
            btnUpdatePizza.setDisable(false);

            if (lbl6.getText().startsWith("Invalid")|| txtPizzaDescription.getText().length()==0) {
                if(txtPizzaDescription.getText().length()==0){
                    lbl6.setText("*This field is required");
                }

                txtPizzaDescription.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl6.setText(" ");
            }
            if (lbl7.getText().startsWith("Invalid")|| txtPizzaUnitPrice.getText().length()==0) {
                if(txtPizzaUnitPrice.getText().length()==0){
                    lbl7.setText("*This field is required");
                }

                txtPizzaUnitPrice.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl7.setText(" ");
            }
            if (lbl8.getText().startsWith("Invalid")|| txtPizzaQuantity.getText().length()==0) {
                if(txtPizzaQuantity.getText().length()==0){
                    lbl8.setText("*This field is required");
                }

                txtPizzaQuantity.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl8.setText(" ");
            }
        } else {
            lbl5.setText("Invalid Food Id..!");
            if(txtPizzaId.getText().isEmpty()){
                lbl5.setText("*This field is required");
            }
            txtPizzaId.setStyle("-fx-border-color: red");
            txtPizzaDescription.setStyle("-fx-border-color: null");
            txtPizzaUnitPrice.setStyle("-fx-border-color: null");
            txtPizzaQuantity.setStyle("-fx-border-color: null");

            btnAddPizza.setDisable(true);
            btnUpdatePizza.setDisable(true);

            if (lbl6.getText().startsWith("Invalid")|| txtPizzaDescription.getText().length()==0) {
                if(txtPizzaDescription.getText().length()==0){
                    lbl6.setText("*This field is required");
                }

                txtPizzaDescription.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl6.setText(" ");
            }
            if (lbl7.getText().startsWith("Invalid")|| txtPizzaUnitPrice.getText().length()==0) {
                if(txtPizzaUnitPrice.getText().length()==0){
                    lbl7.setText("*This field is required");
                }

                txtPizzaUnitPrice.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl7.setText(" ");
            }
            if (lbl8.getText().startsWith("Invalid")|| txtPizzaQuantity.getText().length()==0) {
                if(txtPizzaQuantity.getText().length()==0){
                    lbl8.setText("*This field is required");
                }

                txtPizzaQuantity.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl8.setText(" ");
            }
        }
    }

    public void checkPizzaDescription(KeyEvent keyEvent) {
        String value = "^[A-Za-z- /&]{3,50}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtPizzaDescription.getText());
        if (matcher.matches()) {
            lbl6.setText("");
            txtPizzaDescription.setStyle("-fx-border-color: green");
            txtPizzaQuantity.setStyle("-fx-border-color: null");
            txtPizzaUnitPrice.setStyle("-fx-border-color: null");
            txtPizzaId.setStyle("-fx-border-color: null");

            btnAddPizza.setDisable(false);
            btnUpdatePizza.setDisable(false);

            if (lbl8.getText().startsWith("Invalid")|| txtPizzaQuantity.getText().length()==0) {
                if(txtPizzaQuantity.getText().length()==0){
                    lbl8.setText("*This field is required");
                }

                txtPizzaQuantity.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl8.setText(" ");
            }
            if (lbl7.getText().startsWith("Invalid")|| txtPizzaUnitPrice.getText().length()==0) {
                if(txtPizzaUnitPrice.getText().length()==0){
                    lbl7.setText("*This field is required");
                }

                txtPizzaUnitPrice.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl7.setText(" ");
            }
            if (lbl5.getText().startsWith("Invalid")|| txtPizzaId.getText().length()==0) {
                if(txtPizzaId.getText().length()==0){
                    lbl5.setText("*This field is required");
                }

                txtPizzaId.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl5.setText(" ");
            }
        } else {
            lbl6.setText("Invalid Food description..!");
            if(txtPizzaDescription.getText().isEmpty()){
                lbl6.setText("*This field is required");
            }
            txtPizzaDescription.setStyle("-fx-border-color: red");
            txtPizzaQuantity.setStyle("-fx-border-color: null");
            txtPizzaUnitPrice.setStyle("-fx-border-color: null");
            txtPizzaId.setStyle("-fx-border-color: null");

            btnAddPizza.setDisable(true);
            btnUpdatePizza.setDisable(true);

            if (lbl8.getText().startsWith("Invalid")|| txtPizzaQuantity.getText().length()==0) {
                if(txtPizzaQuantity.getText().length()==0){
                    lbl8.setText("*This field is required");
                }

                txtPizzaQuantity.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl8.setText(" ");
            }
            if (lbl7.getText().startsWith("Invalid")|| txtPizzaUnitPrice.getText().length()==0) {
                if(txtPizzaUnitPrice.getText().length()==0){
                    lbl7.setText("*This field is required");
                }

                txtPizzaUnitPrice.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl7.setText(" ");
            }
            if (lbl5.getText().startsWith("Invalid")|| txtPizzaId.getText().length()==0) {
                if(txtPizzaId.getText().length()==0){
                    lbl5.setText("*This field is required");
                }

                txtPizzaId.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl5.setText(" ");
            }
        }
    }

    public void checkPizzaUnitPrice(KeyEvent keyEvent) {
        String value = "^[1-9][0-9]*(.[0-9]{2})?$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtPizzaUnitPrice.getText());
        if (matcher.matches()) {
            lbl7.setText("");
            txtPizzaUnitPrice.setStyle("-fx-border-color: green");
            txtPizzaQuantity.setStyle("-fx-border-color: null");
            txtPizzaDescription.setStyle("-fx-border-color: null");
            txtPizzaId.setStyle("-fx-border-color: null");

            btnAddPizza.setDisable(false);
            btnUpdatePizza.setDisable(false);

            if (lbl8.getText().startsWith("Invalid")|| txtPizzaQuantity.getText().length()==0) {
                if(txtPizzaQuantity.getText().length()==0){
                    lbl8.setText("*This field is required");
                }

                txtPizzaQuantity.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl8.setText(" ");
            }
            if (lbl6.getText().startsWith("Invalid")|| txtPizzaDescription.getText().length()==0) {
                if(txtPizzaDescription.getText().length()==0){
                    lbl6.setText("*This field is required");
                }

                txtPizzaDescription.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl6.setText(" ");
            }
            if (lbl5.getText().startsWith("Invalid")|| txtPizzaId.getText().length()==0) {
                if(txtPizzaId.getText().length()==0){
                    lbl5.setText("*This field is required");
                }

                txtPizzaId.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl5.setText(" ");
            }
        } else {
            lbl7.setText("Invalid unit price..!");
            if(txtPizzaUnitPrice.getText().isEmpty()){
                lbl7.setText("*This field is required");
            }
            txtPizzaUnitPrice.setStyle("-fx-border-color: red");
            txtPizzaQuantity.setStyle("-fx-border-color: null");
            txtPizzaDescription.setStyle("-fx-border-color: null");
            txtPizzaId.setStyle("-fx-border-color: null");

            btnAddPizza.setDisable(true);
            btnUpdatePizza.setDisable(true);

            if (lbl8.getText().startsWith("Invalid")|| txtPizzaQuantity.getText().length()==0) {
                if(txtPizzaQuantity.getText().length()==0){
                    lbl8.setText("*This field is required");
                }

                txtPizzaQuantity.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl8.setText(" ");
            }
            if (lbl6.getText().startsWith("Invalid")|| txtPizzaDescription.getText().length()==0) {
                if(txtPizzaDescription.getText().length()==0){
                    lbl6.setText("*This field is required");
                }

                txtPizzaDescription.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl6.setText(" ");
            }
            if (lbl5.getText().startsWith("Invalid")|| txtPizzaId.getText().length()==0) {
                if(txtPizzaId.getText().length()==0){
                    lbl5.setText("*This field is required");
                }

                txtPizzaId.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl5.setText(" ");
            }
        }
    }

    public void checkPizzaQty(KeyEvent keyEvent) {
        String value = "^([0-9]{1,4})$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtPizzaQuantity.getText());
        if (matcher.matches()) {
            lbl8.setText("");
            txtPizzaQuantity.setStyle("-fx-border-color: green");
            txtPizzaDescription.setStyle("-fx-border-color: null");
            txtPizzaUnitPrice.setStyle("-fx-border-color: null");
            txtPizzaId.setStyle("-fx-border-color: null");

            btnAddPizza.setDisable(false);
            btnUpdatePizza.setDisable(false);

            if (lbl6.getText().startsWith("Invalid")|| txtPizzaDescription.getText().length()==0) {
                if(txtPizzaDescription.getText().length()==0){
                    lbl6.setText("*This field is required");
                }

                txtPizzaDescription.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl6.setText(" ");
            }
            if (lbl7.getText().startsWith("Invalid")|| txtPizzaUnitPrice.getText().length()==0) {
                if(txtPizzaUnitPrice.getText().length()==0){
                    lbl7.setText("*This field is required");
                }

                txtPizzaUnitPrice.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl7.setText(" ");
            }
            if (lbl5.getText().startsWith("Invalid")|| txtPizzaId.getText().length()==0) {
                if(txtPizzaId.getText().length()==0){
                    lbl5.setText("*This field is required");
                }

                txtPizzaId.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl5.setText(" ");
            }
        } else {
            lbl8.setText("Invalid Quantity..!");
            if(txtPizzaQuantity.getText().isEmpty()){
                lbl8.setText("*This field is required");
            }
            txtPizzaQuantity.setStyle("-fx-border-color: red");
            txtPizzaDescription.setStyle("-fx-border-color: null");
            txtPizzaUnitPrice.setStyle("-fx-border-color: null");
            txtPizzaId.setStyle("-fx-border-color: null");

            btnAddPizza.setDisable(true);
            btnUpdatePizza.setDisable(true);

            if (lbl6.getText().startsWith("Invalid")|| txtPizzaDescription.getText().length()==0) {
                if(txtPizzaDescription.getText().length()==0){
                    lbl6.setText("*This field is required");
                }

                txtPizzaDescription.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl6.setText(" ");
            }
            if (lbl7.getText().startsWith("Invalid")|| txtPizzaUnitPrice.getText().length()==0) {
                if(txtPizzaUnitPrice.getText().length()==0){
                    lbl7.setText("*This field is required");
                }

                txtPizzaUnitPrice.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl7.setText(" ");
            }
            if (lbl5.getText().startsWith("Invalid")|| txtPizzaId.getText().length()==0) {
                if(txtPizzaId.getText().length()==0){
                    lbl5.setText("*This field is required");
                }

                txtPizzaId.setStyle("-fx-border-color: red");
                btnAddPizza.setDisable(true);
                btnUpdatePizza.setDisable(true);
            }else{
                lbl5.setText(" ");
            }
        }
    }

    public void checkBurgerId(KeyEvent keyEvent) {
        String value = "^([F]{1}([0-9]{3,5}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtBurgerId.getText());
        if (matcher.matches()) {
            lbl9.setText("");
            txtBurgerId.setStyle("-fx-border-color: green");
            txtBurgerDescription.setStyle("-fx-border-color: null");
            txtBurgerUnitPrice.setStyle("-fx-border-color: null");
            txtBurgerQuantity.setStyle("-fx-border-color: null");

            btnAddBurger.setDisable(false);
            btnUpdateBurger.setDisable(false);

            if (lbl10.getText().startsWith("Invalid")|| txtBurgerDescription.getText().length()==0) {
                if(txtBurgerDescription.getText().length()==0){
                    lbl10.setText("*This field is required");
                }

                txtBurgerDescription.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl10.setText("");
            }
            if (lbl11.getText().startsWith("Invalid")|| txtBurgerUnitPrice.getText().length()==0) {
                if(txtBurgerUnitPrice.getText().length()==0){
                    lbl11.setText("*This field is required");
                }

                txtBurgerUnitPrice.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl11.setText("");
            }
            if (lbl12.getText().startsWith("Invalid")|| txtBurgerQuantity.getText().length()==0) {
                if(txtBurgerQuantity.getText().length()==0){
                    lbl12.setText("*This field is required");
                }

                txtBurgerQuantity.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl12.setText("");
            }
        } else {
            lbl9.setText("Invalid Food Id..!");
            if(txtBurgerId.getText().isEmpty()){
                lbl9.setText("*This field is required");
            }
            txtBurgerId.setStyle("-fx-border-color: red");
            txtBurgerDescription.setStyle("-fx-border-color: null");
            txtBurgerUnitPrice.setStyle("-fx-border-color: null");
            txtBurgerQuantity.setStyle("-fx-border-color: null");

            btnAddBurger.setDisable(true);
            btnUpdateBurger.setDisable(true);

            if (lbl10.getText().startsWith("Invalid")|| txtBurgerDescription.getText().length()==0) {
                if(txtBurgerDescription.getText().length()==0){
                    lbl10.setText("*This field is required");
                }

                txtBurgerDescription.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl10.setText("");
            }
            if (lbl11.getText().startsWith("Invalid")|| txtBurgerUnitPrice.getText().length()==0) {
                if(txtBurgerUnitPrice.getText().length()==0){
                    lbl11.setText("*This field is required");
                }

                txtBurgerUnitPrice.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl11.setText("");
            }
            if (lbl12.getText().startsWith("Invalid")|| txtBurgerQuantity.getText().length()==0) {
                if(txtBurgerQuantity.getText().length()==0){
                    lbl12.setText("*This field is required");
                }

                txtBurgerQuantity.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl12.setText("");
            }
        }
    }

    public void checkBurgerDescription(KeyEvent keyEvent) {
        String value = "^[A-Za-z- /&]{3,50}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtBurgerDescription.getText());
        if (matcher.matches()) {
            lbl10.setText("");
            txtBurgerDescription.setStyle("-fx-border-color: green");
            txtBurgerQuantity.setStyle("-fx-border-color: null");
            txtBurgerUnitPrice.setStyle("-fx-border-color: null");
            txtBurgerId.setStyle("-fx-border-color: null");

            btnAddBurger.setDisable(false);
            btnUpdateBurger.setDisable(false);

            if (lbl12.getText().startsWith("Invalid")|| txtBurgerQuantity.getText().length()==0) {
                if(txtBurgerQuantity.getText().length()==0){
                    lbl12.setText("*This field is required");
                }

                txtBurgerQuantity.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl12.setText(" ");
            }
            if (lbl11.getText().startsWith("Invalid")|| txtBurgerUnitPrice.getText().length()==0) {
                if(txtBurgerUnitPrice.getText().length()==0){
                    lbl11.setText("*This field is required");
                }

                txtBurgerUnitPrice.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl11.setText(" ");
            }
            if (lbl9.getText().startsWith("Invalid")|| txtBurgerId.getText().length()==0) {
                if(txtBurgerId.getText().length()==0){
                    lbl9.setText("*This field is required");
                }

                txtBurgerId.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl9.setText(" ");
            }
        } else {
            lbl10.setText("Invalid Food description..!");
            if(txtBurgerDescription.getText().isEmpty()){
                lbl10.setText("*This field is required");
            }
            txtBurgerDescription.setStyle("-fx-border-color: red");
            txtBurgerQuantity.setStyle("-fx-border-color: null");
            txtBurgerUnitPrice.setStyle("-fx-border-color: null");
            txtBurgerId.setStyle("-fx-border-color: null");

            btnAddBurger.setDisable(true);
            btnUpdateBurger.setDisable(true);

            if (lbl12.getText().startsWith("Invalid")|| txtBurgerQuantity.getText().length()==0) {
                if(txtBurgerQuantity.getText().length()==0){
                    lbl12.setText("*This field is required");
                }

                txtBurgerQuantity.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl12.setText(" ");
            }
            if (lbl11.getText().startsWith("Invalid")|| txtBurgerUnitPrice.getText().length()==0) {
                if(txtBurgerUnitPrice.getText().length()==0){
                    lbl11.setText("*This field is required");
                }

                txtBurgerUnitPrice.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl11.setText(" ");
            }
            if (lbl9.getText().startsWith("Invalid")|| txtBurgerId.getText().length()==0) {
                if(txtBurgerId.getText().length()==0){
                    lbl9.setText("*This field is required");
                }

                txtBurgerId.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl9.setText(" ");
            }
        }
    }

    public void checkBurgerUnitPrice(KeyEvent keyEvent) {
        String value = "^[1-9][0-9]*(.[0-9]{2})?$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtBurgerUnitPrice.getText());
        if (matcher.matches()) {
            lbl11.setText("");
            txtBurgerUnitPrice.setStyle("-fx-border-color: green");
            txtBurgerQuantity.setStyle("-fx-border-color: null");
            txtBurgerDescription.setStyle("-fx-border-color: null");
            txtBurgerId.setStyle("-fx-border-color: null");

            btnAddBurger.setDisable(false);
            btnUpdateBurger.setDisable(false);

            if (lbl12.getText().startsWith("Invalid")|| txtBurgerQuantity.getText().length()==0) {
                if(txtBurgerQuantity.getText().length()==0){
                    lbl12.setText("*This field is required");
                }

                txtBurgerQuantity.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl12.setText(" ");
            }
            if (lbl10.getText().startsWith("Invalid")|| txtBurgerDescription.getText().length()==0) {
                if(txtBurgerDescription.getText().length()==0){
                    lbl10.setText("*This field is required");
                }

                txtBurgerDescription.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl10.setText(" ");
            }
            if (lbl9.getText().startsWith("Invalid")|| txtBurgerId.getText().length()==0) {
                if(txtBurgerId.getText().length()==0){
                    lbl9.setText("*This field is required");
                }

                txtBurgerId.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl9.setText(" ");
            }
        } else {
            lbl11.setText("Invalid unit price..!");
            if(txtBurgerUnitPrice.getText().isEmpty()){
                lbl11.setText("*This field is required");
            }
            txtBurgerUnitPrice.setStyle("-fx-border-color: red");
            txtBurgerQuantity.setStyle("-fx-border-color: null");
            txtBurgerDescription.setStyle("-fx-border-color: null");
            txtBurgerId.setStyle("-fx-border-color: null");

            btnAddBurger.setDisable(true);
            btnUpdateBurger.setDisable(true);

            if (lbl12.getText().startsWith("Invalid")|| txtBurgerQuantity.getText().length()==0) {
                if(txtBurgerQuantity.getText().length()==0){
                    lbl12.setText("*This field is required");
                }

                txtBurgerQuantity.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl12.setText(" ");
            }
            if (lbl10.getText().startsWith("Invalid")|| txtBurgerDescription.getText().length()==0) {
                if(txtBurgerDescription.getText().length()==0){
                    lbl10.setText("*This field is required");
                }

                txtBurgerDescription.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl10.setText(" ");
            }
            if (lbl9.getText().startsWith("Invalid")|| txtBurgerId.getText().length()==0) {
                if(txtBurgerId.getText().length()==0){
                    lbl9.setText("*This field is required");
                }

                txtBurgerId.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl9.setText(" ");
            }
        }
    }

    public void checkBurgerQty(KeyEvent keyEvent) {
        String value = "^([0-9]{1,4})$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtBurgerQuantity.getText());
        if (matcher.matches()) {
            lbl12.setText("");
            txtBurgerQuantity.setStyle("-fx-border-color: green");
            txtBurgerDescription.setStyle("-fx-border-color: null");
            txtBurgerUnitPrice.setStyle("-fx-border-color: null");
            txtBurgerId.setStyle("-fx-border-color: null");

            btnAddBurger.setDisable(false);
            btnUpdateBurger.setDisable(false);

            if (lbl10.getText().startsWith("Invalid")|| txtBurgerDescription.getText().length()==0) {
                if(txtBurgerDescription.getText().length()==0){
                    lbl10.setText("*This field is required");
                }

                txtBurgerDescription.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl10.setText(" ");
            }
            if (lbl11.getText().startsWith("Invalid")|| txtBurgerUnitPrice.getText().length()==0) {
                if(txtBurgerUnitPrice.getText().length()==0){
                    lbl11.setText("*This field is required");
                }

                txtBurgerUnitPrice.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl11.setText(" ");
            }
            if (lbl9.getText().startsWith("Invalid")|| txtBurgerId.getText().length()==0) {
                if(txtBurgerId.getText().length()==0){
                    lbl9.setText("*This field is required");
                }

                txtBurgerId.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl9.setText(" ");
            }
        } else {
            lbl12.setText("Invalid Quantity..!");
            if(txtBurgerQuantity.getText().isEmpty()){
                lbl12.setText("*This field is required");
            }
            txtBurgerQuantity.setStyle("-fx-border-color: red");
            txtBurgerDescription.setStyle("-fx-border-color: null");
            txtBurgerUnitPrice.setStyle("-fx-border-color: null");
            txtBurgerId.setStyle("-fx-border-color: null");

            btnAddBurger.setDisable(true);
            btnUpdateBurger.setDisable(true);


            if (lbl10.getText().startsWith("Invalid")|| txtBurgerDescription.getText().length()==0) {
                if(txtBurgerDescription.getText().length()==0){
                    lbl10.setText("*This field is required");
                }

                txtBurgerDescription.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl10.setText(" ");
            }
            if (lbl11.getText().startsWith("Invalid")|| txtBurgerUnitPrice.getText().length()==0) {
                if(txtBurgerUnitPrice.getText().length()==0){
                    lbl11.setText("*This field is required");
                }

                txtBurgerUnitPrice.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl11.setText(" ");
            }
            if (lbl9.getText().startsWith("Invalid")|| txtBurgerId.getText().length()==0) {
                if(txtBurgerId.getText().length()==0){
                    lbl9.setText("*This field is required");
                }

                txtBurgerId.setStyle("-fx-border-color: red");
                btnAddBurger.setDisable(true);
                btnUpdateBurger.setDisable(true);
            }else{
                lbl9.setText(" ");
            }
        }
    }

    public void checkBeverageId(KeyEvent keyEvent) {
        String value = "^([F]{1}([0-9]{3,5}))$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtBevId.getText());
        if (matcher.matches()) {
            lbl13.setText("");
            txtBevId.setStyle("-fx-border-color: green");
            txtBevDescription.setStyle("-fx-border-color: null");
            txtBevUnitPrice.setStyle("-fx-border-color: null");
            txtBevQuantity.setStyle("-fx-border-color: null");

            btnAddBeverage.setDisable(false);
            btnUpdateBeverage.setDisable(false);

            if (lbl14.getText().startsWith("Invalid")|| txtBevDescription.getText().length()==0) {
                if(txtBevDescription.getText().length()==0){
                    lbl14.setText("*This field is required");
                }

                txtBevDescription.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl14.setText(" ");
            }
            if (lbl15.getText().startsWith("Invalid")|| txtBevUnitPrice.getText().length()==0) {
                if(txtBevUnitPrice.getText().length()==0){
                    lbl15.setText("*This field is required");
                }

                txtBevUnitPrice.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl15.setText(" ");
            }
            if (lbl16.getText().startsWith("Invalid")|| txtBevQuantity.getText().length()==0) {
                if(txtBevQuantity.getText().length()==0){
                    lbl16.setText("*This field is required");
                }

                txtBevQuantity.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl16.setText(" ");
            }
        } else {
            lbl13.setText("Invalid Food Id..!");
            if(txtBevId.getText().isEmpty()){
                lbl13.setText("*This field is required");
            }
            txtBevId.setStyle("-fx-border-color: red");
            txtBevDescription.setStyle("-fx-border-color: null");
            txtBevUnitPrice.setStyle("-fx-border-color: null");
            txtBevQuantity.setStyle("-fx-border-color: null");

            btnAddBeverage.setDisable(true);
            btnUpdateBeverage.setDisable(true);

            if (lbl14.getText().startsWith("Invalid")|| txtBevDescription.getText().length()==0) {
                if(txtBevDescription.getText().length()==0){
                    lbl14.setText("*This field is required");
                }

                txtBevDescription.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl14.setText(" ");
            }
            if (lbl15.getText().startsWith("Invalid")|| txtBevUnitPrice.getText().length()==0) {
                if(txtBevUnitPrice.getText().length()==0){
                    lbl15.setText("*This field is required");
                }

                txtBevUnitPrice.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl15.setText(" ");
            }
            if (lbl16.getText().startsWith("Invalid")|| txtBevQuantity.getText().length()==0) {
                if(txtBevQuantity.getText().length()==0){
                    lbl16.setText("*This field is required");
                }

                txtBevQuantity.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl16.setText(" ");
            }
        }
    }

    public void checkBeverageDescription(KeyEvent keyEvent) {
        String value = "^[A-Za-z-0-9- /&]{3,50}$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtBevDescription.getText());
        if (matcher.matches()) {
            lbl14.setText("");
            txtBevDescription.setStyle("-fx-border-color: green");
            txtBevQuantity.setStyle("-fx-border-color: null");
            txtBevUnitPrice.setStyle("-fx-border-color: null");
            txtBevId.setStyle("-fx-border-color: null");

            btnAddBeverage.setDisable(false);
            btnUpdateBeverage.setDisable(false);


            if (lbl16.getText().startsWith("Invalid")|| txtBevQuantity.getText().length()==0) {
                if(txtBevQuantity.getText().length()==0){
                    lbl16.setText("*This field is required");
                }

                txtBevQuantity.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl16.setText(" ");
            }
            if (lbl15.getText().startsWith("Invalid")|| txtBevUnitPrice.getText().length()==0) {
                if(txtBevUnitPrice.getText().length()==0){
                    lbl15.setText("*This field is required");
                }

                txtBevUnitPrice.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl15.setText(" ");
            }
            if (lbl13.getText().startsWith("Invalid")|| txtBevId.getText().length()==0) {
                if(txtBevId.getText().length()==0){
                    lbl13.setText("*This field is required");
                }

                txtBevId.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl13.setText(" ");
            }
        } else {
            lbl14.setText("Invalid Food description..!");
            if(txtBevDescription.getText().isEmpty()){
                lbl14.setText("*This field is required");
            }
            txtBevDescription.setStyle("-fx-border-color: red");
            txtBevQuantity.setStyle("-fx-border-color: null");
            txtBevUnitPrice.setStyle("-fx-border-color: null");
            txtBevId.setStyle("-fx-border-color: null");

            btnAddBeverage.setDisable(true);
            btnUpdateBeverage.setDisable(true);

            if (lbl16.getText().startsWith("Invalid")|| txtBevQuantity.getText().length()==0) {
                if(txtBevQuantity.getText().length()==0){
                    lbl16.setText("*This field is required");
                }

                txtBevQuantity.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl16.setText(" ");
            }
            if (lbl15.getText().startsWith("Invalid")|| txtBevUnitPrice.getText().length()==0) {
                if(txtBevUnitPrice.getText().length()==0){
                    lbl15.setText("*This field is required");
                }

                txtBevUnitPrice.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl15.setText(" ");
            }
            if (lbl13.getText().startsWith("Invalid")|| txtBevId.getText().length()==0) {
                if(txtBevId.getText().length()==0){
                    lbl13.setText("*This field is required");
                }

                txtBevId.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl13.setText(" ");
            }
        }
    }

    public void checkBeverageUnitPrice(KeyEvent keyEvent) {
        String value = "^[1-9][0-9]*(.[0-9]{2})?$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtBevUnitPrice.getText());
        if (matcher.matches()) {
            lbl15.setText("");
            txtBevUnitPrice.setStyle("-fx-border-color: green");
            txtBevQuantity.setStyle("-fx-border-color: null");
            txtBevDescription.setStyle("-fx-border-color: null");
            txtBevId.setStyle("-fx-border-color: null");

            btnAddBeverage.setDisable(false);
            btnUpdateBeverage.setDisable(false);

            if (lbl16.getText().startsWith("Invalid")|| txtBevQuantity.getText().length()==0) {
                if(txtBevQuantity.getText().length()==0){
                    lbl16.setText("*This field is required");
                }

                txtBevQuantity.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl16.setText(" ");
            }
            if (lbl14.getText().startsWith("Invalid")|| txtBevDescription.getText().length()==0) {
                if(txtBevDescription.getText().length()==0){
                    lbl14.setText("*This field is required");
                }

                txtBevDescription.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl14.setText(" ");
            }
            if (lbl13.getText().startsWith("Invalid")|| txtBevId.getText().length()==0) {
                if(txtBevId.getText().length()==0){
                    lbl13.setText("*This field is required");
                }

                txtBevId.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl13.setText(" ");
            }
        } else {
            lbl15.setText("Invalid unit price..!");
            if(txtBevUnitPrice.getText().isEmpty()){
                lbl15.setText("*This field is required");
            }
            txtBevUnitPrice.setStyle("-fx-border-color: red");
            txtBevQuantity.setStyle("-fx-border-color: null");
            txtBevDescription.setStyle("-fx-border-color: null");
            txtBevId.setStyle("-fx-border-color: null");

            btnAddBeverage.setDisable(true);
            btnUpdateBeverage.setDisable(true);

            if (lbl16.getText().startsWith("Invalid")|| txtBevQuantity.getText().length()==0) {
                if(txtBevQuantity.getText().length()==0){
                    lbl16.setText("*This field is required");
                }

                txtBevQuantity.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl16.setText(" ");
            }
            if (lbl14.getText().startsWith("Invalid")|| txtBevDescription.getText().length()==0) {
                if(txtBevDescription.getText().length()==0){
                    lbl14.setText("*This field is required");
                }

                txtBevDescription.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl14.setText(" ");
            }
            if (lbl13.getText().startsWith("Invalid")|| txtBevId.getText().length()==0) {
                if(txtBevId.getText().length()==0){
                    lbl13.setText("*This field is required");
                }

                txtBevId.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl13.setText(" ");
            }
        }
    }

    public void checkBeverageQty(KeyEvent keyEvent) {
        String value = "^([0-9]{1,4})$";
        Pattern compile = Pattern.compile(value);
        Matcher matcher = compile.matcher(txtBevQuantity.getText());
        if (matcher.matches()) {
            lbl16.setText("");
            txtBevQuantity.setStyle("-fx-border-color: green");
            txtBevDescription.setStyle("-fx-border-color: null");
            txtBevUnitPrice.setStyle("-fx-border-color: null");
            txtBevId.setStyle("-fx-border-color: null");

            btnAddBeverage.setDisable(false);
            btnUpdateBeverage.setDisable(false);

            if (lbl14.getText().startsWith("Invalid")|| txtBevDescription.getText().length()==0) {
                if(txtBevDescription.getText().length()==0){
                    lbl14.setText("*This field is required");
                }

                txtBevDescription.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl14.setText(" ");
            }
            if (lbl15.getText().startsWith("Invalid")|| txtBevUnitPrice.getText().length()==0) {
                if(txtBevUnitPrice.getText().length()==0){
                    lbl15.setText("*This field is required");
                }

                txtBevUnitPrice.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl15.setText(" ");
            }
            if (lbl13.getText().startsWith("Invalid")|| txtBevId.getText().length()==0) {
                if(txtBevId.getText().length()==0){
                    lbl13.setText("*This field is required");
                }

                txtBevId.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl13.setText(" ");
            }
        } else {
            lbl16.setText("Invalid Quantity..!");
            if(txtBevQuantity.getText().isEmpty()){
                lbl16.setText("*This field is required");
            }
            txtBevQuantity.setStyle("-fx-border-color: red");
            txtBevDescription.setStyle("-fx-border-color: null");
            txtBevUnitPrice.setStyle("-fx-border-color: null");
            txtBevId.setStyle("-fx-border-color: null");

            btnAddBeverage.setDisable(true);
            btnUpdateBeverage.setDisable(true);

            if (lbl14.getText().startsWith("Invalid")|| txtBevDescription.getText().length()==0) {
                if(txtBevDescription.getText().length()==0){
                    lbl14.setText("*This field is required");
                }

                txtBevDescription.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl14.setText(" ");
            }
            if (lbl15.getText().startsWith("Invalid")|| txtBevUnitPrice.getText().length()==0) {
                if(txtBevUnitPrice.getText().length()==0){
                    lbl15.setText("*This field is required");
                }

                txtBevUnitPrice.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl15.setText(" ");
            }
            if (lbl13.getText().startsWith("Invalid")|| txtBevId.getText().length()==0) {
                if(txtBevId.getText().length()==0){
                    lbl13.setText("*This field is required");
                }

                txtBevId.setStyle("-fx-border-color: red");
                btnAddBeverage.setDisable(true);
                btnUpdateBeverage.setDisable(true);
            }else{
                lbl13.setText(" ");
            }
        }
    }


    public void cmbMealDragOnAction(ActionEvent dragEvent) {
        btnMealDelete.setDisable(false);
        btnUpdateMeal.setDisable(false);
    }

    public void txtPizzaIdOnAction(ActionEvent event) {
        txtPizzaDescription.requestFocus();
        txtPizzaId.setStyle("-fx-border-color: null");
    }

    public void txtPizzaDesOnAction(ActionEvent event) {
        txtPizzaUnitPrice.requestFocus();
        txtPizzaDescription.setStyle("-fx-border-color: null");
    }

    public void txtPizzaUnitPriceOnAction(ActionEvent event) {
        txtPizzaQuantity.requestFocus();
        txtPizzaUnitPrice.setStyle("-fx-border-color: null");
    }

    public void cmbPizzaOnAction(ActionEvent event) {
        btnDeletePizza.setDisable(false);
        btnUpdatePizza.setDisable(false);
    }

    public void cmbBurgerOnAction(ActionEvent event) {
        btnDeleteBurger.setDisable(false);
        btnUpdateBurger.setDisable(false);
    }

    public void txtBurgerIdOnAction(ActionEvent event) {
        txtBurgerDescription.requestFocus();
        txtBurgerId.setStyle("-fx-border-color: null");
    }

    public void txtBurgerDesOnAction(ActionEvent event) {
        txtBurgerUnitPrice.requestFocus();
        txtBurgerDescription.setStyle("-fx-border-color: null");
    }

    public void txtBurgerUnitPriceOnAction(ActionEvent event) {
        txtBurgerQuantity.requestFocus();
        txtBurgerUnitPrice.setStyle("-fx-border-color: null");
    }

    public void cmbBeverageOnAction(ActionEvent event) {
        btnDeleteBeverage.setDisable(false);
        btnUpdateBeverage.setDisable(false);
    }

    public void txtBeverageIdOnAction(ActionEvent event) {
        txtBevDescription.requestFocus();
        txtBevId.setStyle("-fx-border-color: null");
    }

    public void txtBevDesOnAction(ActionEvent event) {
        txtBevUnitPrice.requestFocus();
        txtBevDescription.setStyle("-fx-border-color: null");
    }

    public void txtBevUnitPriceOnAction(ActionEvent event) {
        txtBevQuantity.requestFocus();
        txtBevUnitPrice.setStyle("-fx-border-color: null");
    }
}
