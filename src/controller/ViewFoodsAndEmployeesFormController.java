package controller;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Employee;
import model.Food;

import java.sql.SQLException;

public class ViewFoodsAndEmployeesFormController {


    public Tab FoodItemTab;
    public TableView<Food> tblMeal;
    public TableColumn colMealId;
    public TableColumn colMealName;
    public TableColumn colMealUnitPrice;
    public TableColumn colMealQtyOnHand;
    public TableColumn colMealDiscountedPrice;
    public Tab EmployeeTab;
    public TableView<Food> tblPizza;
    public TableColumn colPizzaId;
    public TableColumn colPizzaName;
    public TableColumn colPizzaUnitPrice;
    public TableColumn colPizzaQtyOnHand;
    public TableColumn colPizzaDiscountedPrice;
    public Tab burgerTab;
    public TableView<Food> tblBurger;
    public TableColumn colBurgerId;
    public TableColumn colBurgerName;
    public TableColumn colBurgerUnitPrice;
    public TableColumn colBurgerQtyOnHand;
    public TableColumn colBurgerDisPrice;
    public Tab beverageTab;
    public TableView<Food> tblDrink;
    public TableColumn colDrinkId;
    public TableColumn colDrinkName;
    public TableColumn colDrinkUnitPrice;
    public TableColumn colDrinkQtyOnHand;
    public TableColumn colDrinkDisPrice;

    public void initialize(){
        colMealId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colMealName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colMealUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colMealQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colMealDiscountedPrice.setCellValueFactory(new PropertyValueFactory<>("discountedPrice"));

        colPizzaId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colPizzaName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPizzaUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colPizzaQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colPizzaDiscountedPrice.setCellValueFactory(new PropertyValueFactory<>("discountedPrice"));

        colBurgerId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colBurgerName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colBurgerUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colBurgerQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colBurgerDisPrice.setCellValueFactory(new PropertyValueFactory<>("discountedPrice"));

        colDrinkId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colDrinkName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDrinkUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDrinkQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDrinkDisPrice.setCellValueFactory(new PropertyValueFactory<>("discountedPrice"));

        try{
            tblMeal.setItems(new ItemCrudController().getMeals("Meal",""));
            tblPizza.setItems(new ItemCrudController().getPizza("Pizza",""));
            tblBurger.setItems(new ItemCrudController().getBurgers("Burger",""));
            tblDrink.setItems(new ItemCrudController().getDrinks("Drink",""));

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
