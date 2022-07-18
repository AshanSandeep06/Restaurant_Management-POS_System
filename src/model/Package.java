package model;

import java.util.ArrayList;

public class Package {
    private String packageId;
    private String packageName;
    private double packagePrice;
    private ArrayList<PackageDetail> packageDetails;

    public Package() {

    }

    public Package(String packageId, String packageName, double packagePrice) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.packagePrice = packagePrice;
    }

    public Package(String packageId, String packageName, double packagePrice, ArrayList<PackageDetail> packageDetails) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.packagePrice = packagePrice;
        this.packageDetails = packageDetails;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(double packagePrice) {
        this.packagePrice = packagePrice;
    }

    public ArrayList<PackageDetail> getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(ArrayList<PackageDetail> packageDetails) {
        this.packageDetails = packageDetails;
    }

    public String toString(){
        return packageId;
    }

    /*public boolean equals(Object obj){
        return this.packageId == ((Package) obj).getPackageId();
    }*/
}
