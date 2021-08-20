package main.controller;

public final class UserHolder {

    private User user;

    private final static UserHolder inst = new UserHolder();

    private UserHolder() {}

    public static UserHolder getInstance() {
        return inst;
    }

    public void setUser(User u) {
//        System.out.println("In setUser");
        this.user = u;
    }

    public User getUser() {
//        System.out.println("In getUser");
        return this.user;
    }

}