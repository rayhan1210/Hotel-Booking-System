package main.controller;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class TableBookingInfoForUsers {

    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty table = new SimpleStringProperty();
    private final SimpleStringProperty role = new SimpleStringProperty();
    private final SimpleStringProperty status = new SimpleStringProperty();
    private final SimpleStringProperty dateOfBook = new SimpleStringProperty();
    private final SimpleStringProperty confirmDate = new SimpleStringProperty();
    private final SimpleStringProperty firstName = new SimpleStringProperty();
    private final SimpleStringProperty lastName = new SimpleStringProperty();
    private final SimpleStringProperty passWrd = new SimpleStringProperty();
    private final SimpleStringProperty secretQs = new SimpleStringProperty();
    private final SimpleStringProperty secretAns = new SimpleStringProperty();
    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty whiteList = new SimpleStringProperty();
    public String getWhiteList() {
        return whiteList.get();
    }

    public SimpleStringProperty whiteListProperty() {
        return whiteList;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList.set(whiteList);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPassWrd() {
        return passWrd.get();
    }

    public SimpleStringProperty passWrdProperty() {
        return passWrd;
    }

    public void setPassWrd(String passWrd) {
        this.passWrd.set(passWrd);
    }

    public String getSecretQs() {
        return secretQs.get();
    }

    public SimpleStringProperty secretQsProperty() {
        return secretQs;
    }

    public void setSecretQs(String secretQs) {
        this.secretQs.set(secretQs);
    }

    public String getSecretAns() {
        return secretAns.get();
    }

    public SimpleStringProperty secretAnsProperty() {
        return secretAns;
    }

    public void setSecretAns(String secretAns) {
        this.secretAns.set(secretAns);
    }

    public String getConfirmDate() {
        return confirmDate.get();
    }

    public SimpleStringProperty confirmDateProperty() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate.set(confirmDate);
    }

    public String getDateOfBook() {
        return dateOfBook.get();
    }

    public SimpleStringProperty dateOfBookProperty() {
        return dateOfBook;
    }

    public void setDateOfBook(String dateOfBook) {
        this.dateOfBook.set(dateOfBook);
    }
//    private final SimpleStringProperty status = new SimpleStringProperty();

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getTable() {
        return table.get();
    }

    public SimpleStringProperty tableProperty() {
        return table;
    }

    public void setTable(String table) {
        this.table.set(table);
    }

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }


}
