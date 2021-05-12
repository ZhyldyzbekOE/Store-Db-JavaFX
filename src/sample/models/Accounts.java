package sample.models;

public class Accounts {

    private int userId;
    private String login;
    private String password;
    private int active;
    private Users users;
    private String name;

    public Accounts() { }

    public Accounts(int userId, String login, String password, int active) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.active = active;
    }

    public Accounts(String login, String users, int active, int userId) {
        this.login = login;
        this.name = users;
        this.active = active;
        this.userId = userId;
    }

    public Accounts(String login, int active, int userId) {
        this.login = login;
        this.active = active;
        this.userId = userId;
    }

    public Accounts(String login, String users, int active) {
        this.login = login;
        this.name = users;
        this.active = active;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
