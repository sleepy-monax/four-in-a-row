package models.Users;

import java.util.Objects;

public class User {

    private String login;
    private String mdp;

    public User(String login, String mdp) {
        setLogin(login);
        setMdp(mdp);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(mdp, user.mdp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, mdp);
    }
}
