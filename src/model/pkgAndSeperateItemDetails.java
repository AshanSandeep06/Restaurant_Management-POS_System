package model;

import java.util.ArrayList;

public class pkgAndSeperateItemDetails {
    private ArrayList<ArrayList<PackageDetail>> packageItemsArrayList;
    private ArrayList<CartTM> seperateItemsArrayList;

    public pkgAndSeperateItemDetails() {

    }

    public pkgAndSeperateItemDetails(ArrayList<ArrayList<PackageDetail>> packageItemsArrayList, ArrayList<CartTM> seperateItemsArrayList) {
        this.setPackageItemsArrayList(packageItemsArrayList);
        this.setSeperateItemsArrayList(seperateItemsArrayList);
    }

    public ArrayList<ArrayList<PackageDetail>> getPackageItemsArrayList() {
        return packageItemsArrayList;
    }

    public void setPackageItemsArrayList(ArrayList<ArrayList<PackageDetail>> packageItemsArrayList) {
        this.packageItemsArrayList = packageItemsArrayList;
    }

    public ArrayList<CartTM> getSeperateItemsArrayList() {
        return seperateItemsArrayList;
    }

    public void setSeperateItemsArrayList(ArrayList<CartTM> seperateItemsArrayList) {
        this.seperateItemsArrayList = seperateItemsArrayList;
    }
}
