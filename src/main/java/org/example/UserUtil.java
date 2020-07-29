package org.example;

public class UserUtil {
    private static UserUtil instance;
    private String user;
    private String password;

    private UserUtil(){}


    public static UserUtil getInstance (){
        if (instance == null){
            instance = new UserUtil();
        }
        return instance;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserUtil{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
