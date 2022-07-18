package controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.PackageDetailTM;

public class ViewPackageItemsFormController {

    public TableView<PackageDetailTM> tblPackageDetails;
    public static TableView<PackageDetailTM> detailTbl=new TableView<>();
    public TableColumn colFoodCode;
    public TableColumn colFoodDes;
    public TableColumn colFoodUnitPrice;
    public TableColumn colFoodQty;
    public TableColumn colTotalCost;
    public TableColumn colFoodOption;

    public void initialize(){
        detailTbl=tblPackageDetails;
        colFoodCode.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colFoodDes.setCellValueFactory(new PropertyValueFactory<>("foodDescription"));
        colFoodUnitPrice.setCellValueFactory(new PropertyValueFactory<>("uniPrice"));
        colFoodQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colFoodOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
}
