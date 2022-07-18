package controller;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CartTM;
import model.Item;

import java.sql.SQLException;

public class MainFormController {
    public TableView<CartTM> tblMostMovable;
    public TableColumn colMostFoodID;
    public TableColumn colMostDesc;
    public TableColumn colMostQty;
    public TableColumn colMostUnitPrice;
    public TableView<CartTM> tblLeastMovable;
    public TableColumn colleastFoodID;
    public TableColumn colLeastDescription;
    public TableColumn colLeastQty;
    public TableColumn colLeastUnitPrice;

    public Label lblTotalOrder;
    public Label lblPaidOrder;
    public Label lblFoodItems;
    public Label lblPendingDelivery;

    public void initialize(){
        try{
            lblTotalOrder.setText(String.valueOf(new OrderCrudController().totalOrderCount()));
            lblPaidOrder.setText(String.valueOf(new OrderCrudController().paidOrdersCount()));
            lblFoodItems.setText(String.valueOf(new ItemCrudController().getTotalFoodItems()));
            lblPendingDelivery.setText(String.valueOf(new OrderCrudController().pendingDeliveries()));


        }catch (SQLException | ClassNotFoundException e){

        }


        colMostFoodID.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colMostDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colMostQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colMostUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        colleastFoodID.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colLeastDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLeastQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colLeastUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        try{
            tblMostMovable.setItems(new ItemCrudController().setMostMovableItems());
            tblLeastMovable.setItems(new ItemCrudController().setLeastMovableItems());

        }catch (SQLException | ClassNotFoundException e){

        }
    }

    // select foodId,description,unitPrice, SUM(qty) from orderdetail group by foodId order by SUM(qty) desc limit 5;
}
