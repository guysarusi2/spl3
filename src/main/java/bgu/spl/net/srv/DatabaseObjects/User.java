package bgu.spl.net.srv.DatabaseObjects;

public abstract class User {
    private String username;
    private String password;

    public User(String username, String password){
        username = username;
        password = password;
    }

    public String getPassword(){return password;}
    public String getUsername(){return username;}

}
