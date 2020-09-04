package personal_web.entity;

public class accounts {
    private String username;
    private String password;
    public accounts(String username, String password){
        this.password=password;
        this.username=username;
    }
    public void updatePassword(String password){
        this.password=password;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    public String getUsername(){
        return this.username;
    }
}
