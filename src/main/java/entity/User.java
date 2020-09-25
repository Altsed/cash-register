package entity;

public class User {
    private int  id;
    private String login;
    private String password;
    private int role;
    private String message;

    public User(String login, String password, int role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public boolean validate(){
        if (password.length() < 8) {
            message = "Password must have at least 8 characters";
            return false;
        }
        else if (password.contains("\\w*\\s+\\w*")) {
            message = "Password cannot contain space";
            return false;
        }
        if (!login.matches("[A-Z][a-z][А-ЯІЇЄ][а-яіїє']{1,20}$")) {
            message = "Login must contains only letters and numbers";
            return false;
        }
        return true;
    }

    public String getMessage() {
        return message;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
