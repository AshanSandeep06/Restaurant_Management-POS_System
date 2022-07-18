package controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class FinancialReportsFormController {

    public AnchorPane context;
    public JFXDatePicker dailyReportsDatePicker;
    public ComboBox<String> cmbMonthlyYear;
    public ComboBox<String> cmbMonthlyMonth;
    public ComboBox<String> cmbAnnuallyYear;

    public void initialize(){
        /*boolean b = new FinanceController().setIncome_Expense_NetIncome();
        System.out.println(b);*/

        cmbMonthlyYear.getItems().addAll("2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033");
        cmbAnnuallyYear.getItems().addAll("2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033");
        cmbMonthlyMonth.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    }

    public void dailyReportsOnAction(ActionEvent event) {
        /*stage.getIcons().add(new Image("assets/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        Notifications information = NotificationBuilder.notifyMassage("CONFIRMATION", "Log in successful");
        information.showInformation();*/

        /*Stage stage = new Stage(StageStyle.DECORATED);
        stage.getIcons().add(new Image("assets/logo.png"));
        stage.initModality(Modality.APPLICATION_MODAL);*/
        if(dailyReportsDatePicker.getValue()!=null){
           try{
               NumberFormat nf = NumberFormat.getInstance();
               nf.setMinimumFractionDigits(2);

               String day = dailyReportsDatePicker.getValue().toString();
               /*System.out.println(dailyReportsDatePicker.getValue().toString());   // 2022-03-25
               System.out.println(dailyReportsDatePicker.getValue());*/              // 2022-03-25

               double ordersIncome = new OrderCrudController().getOrdersIncome(day);
               double grossIncome = ordersIncome;
               double expenses =new FinanceController().getExpenses(day);


               String OrdIncome = nf.format(ordersIncome);
               String GroIncome = nf.format(grossIncome);


               String expense = "0.00";
               double tblExpense = 0.00;
               Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Enter expenses",ButtonType.OK,ButtonType.CANCEL).showAndWait();
               if(buttonType.get().equals(ButtonType.OK)){
                   Scanner input = new Scanner(System.in);
                   System.out.print("Enter your expenses : ");
                   tblExpense = input.nextDouble();
                   expenses += tblExpense;
                   new FinanceController().setExpenses(day,tblExpense);
               }
               expense = nf.format(expenses);

               double netIncome = grossIncome - expenses;
               String netIn = nf.format(netIncome);

               HashMap map = new HashMap();
               map.put("date",day);
               map.put("ordersIncome",OrdIncome);
               map.put("grossIncome",GroIncome);
               map.put("expenses",expense);
               map.put("netIncome",netIn);

              JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("../view/reports/DailyIncomeReport.jasper"));
              JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, map, new JREmptyDataSource(1));
              JasperViewer.viewReport(jasperPrint,false);

              dailyReportsDatePicker.setValue(null);

           }catch (SQLException | ClassNotFoundException | JRException e){

           }
       }else{
           new Alert(Alert.AlertType.ERROR,"Select the Date and get the report..!").show();
       }
    }

    public void monthlyReportsOnAction(ActionEvent event) {
        if(cmbMonthlyMonth.getValue()!=null && cmbMonthlyYear.getValue()!=null){
            try{
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMinimumFractionDigits(2);
                String year = cmbMonthlyYear.getValue();
                String month = cmbMonthlyMonth.getValue();

                double ordersIncome = new OrderCrudController().getOrdersIncome(year,month);
                double grossIncome = ordersIncome;
                double employeeExpenses = new EmployeeSalaryCrudController().getAllPays(year,month);
                double otherExpenses=new FinanceController().getExpenses(year,month);
                double expenses = employeeExpenses+otherExpenses;
                double netIncome = grossIncome - expenses;

                String OrdIncome = nf.format(ordersIncome);
                String GroIncome = nf.format(grossIncome);

                String other = nf.format(otherExpenses);
                String employeeEx = nf.format(employeeExpenses);

                String netIn = nf.format(netIncome);

                HashMap map = new HashMap();
                map.put("year", year);
                map.put("month", month);
                map.put("ordersIncome",OrdIncome);
                map.put("grossIncome",GroIncome);
                map.put("expenses",employeeEx);
                map.put("otherExpenses",other);
                map.put("netIncome",netIn);

                JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("../view/reports/MonthlyIncomeReport.jasper"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, map, new JREmptyDataSource(1));
                JasperViewer.viewReport(jasperPrint,false);

                cmbMonthlyMonth.setValue(null);
                cmbMonthlyYear.setValue(null);

            }catch (SQLException | ClassNotFoundException | JRException e){

            }

        }else{
            new Alert(Alert.AlertType.ERROR,"Select the Year & Month and get the report..!").show();
        }
    }

    public void annuallyReportsOnAction(ActionEvent event) {
        if(cmbAnnuallyYear.getValue()!=null){
            try{
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMinimumFractionDigits(2);
                int year = Integer.parseInt(cmbAnnuallyYear.getValue());

                double ordersIncome = new OrderCrudController().getAnullayOrdersIncome(year);
                double grossIncome = ordersIncome;
                double employeeExpenses = new EmployeeSalaryCrudController().getAnuallySalaryPayments(year);
                double otherExpenses=new FinanceController().getAnnuallyExpenses(year);
                double expenses = employeeExpenses+otherExpenses;
                double netIncome = grossIncome - expenses;

                String OrdIncome = nf.format(ordersIncome);
                String GroIncome = nf.format(grossIncome);
                String other = nf.format(otherExpenses);
                String employeeEx = nf.format(employeeExpenses);
                String netIn = nf.format(netIncome);

                HashMap map = new HashMap();
                map.put("year", year);
                map.put("ordersIncome",OrdIncome);
                map.put("grossIncome",GroIncome);
                map.put("expenses",employeeEx);
                map.put("otherExpenses",other);
                map.put("netIncome",netIn);

                JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream("../view/reports/AnnuallyIncomeReport.jasper"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, map, new JREmptyDataSource(1));
                JasperViewer.viewReport(jasperPrint,false);

                cmbAnnuallyYear.setValue(null);

            }catch (SQLException | ClassNotFoundException | JRException e){

            }

        }else{
            new Alert(Alert.AlertType.ERROR,"Select the Year and get the report..!").show();
        }
    }
}
