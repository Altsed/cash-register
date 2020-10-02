package entity;

public class User {
    private int  id;
    private String login;
    private String password;
    private int role;
    private boolean isValid;
    private String message;

    public User(String login, String password, int role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }


    public User() {
    }

    public User(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public User(String login) {
        this.login = login;
    }

    public int getRole() {
        return role;
    }

    public boolean validate(){
        if (password.length() < 8) {
            message = "Password must have at least 8 characters";
            return isValid = false;
        }
        else if (password.contains("\\w*\\s+\\w*")) {
            message = "Password cannot contain space";
            return isValid = false;
        }
        else if (!login.matches("[A-Za-zА-ЯІЇЄа-яіїє']*")) {
            message = "Login must contains only letters";
            return isValid = false;
        }

        return isValid = true;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public void setPassword(String hashPassword) {
        password = hashPassword;
    }
}
