package main.controller;

public class User {
    private String name;
    private String password;
    private String table;
    private String role;
    private String status;

    public String getWhiteList() {
        return this.whiteList;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList;
    }

    private String whiteList;
    private int index;
    private long date;

    public void setName(String name){
//        System.out.println("In setName");
        this.name = name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setDate(long date){
        this.date = date;
    }
    public String getName(){
//        System.out.println("In getName");
        return this.name;
    }
    public long getDate(){
//        System.out.println("In getDate");
        return this.date;
    }
    public void setTable(String table){
        this.table = table;
    }
    public String getTable(){
        return this.table;
    }
    public void setRole(String role){
        this.role = role;
    }
    public String getRole(){
        return this.role;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setTablePosIndex(int index){
        this.index = index;
    }
    public int getTablePosIndex(){
        return this.index;
    }
}
