package org.isisaddons.module.stringinterpolator.dom;

class Address {
    private int houseNumber;

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    private String town;

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    private String postalCode;

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}