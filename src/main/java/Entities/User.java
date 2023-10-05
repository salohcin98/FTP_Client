package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String userid;
    private String password;

    public User(int id, String userid, String password) {
        this.id = id;
        this.userid = userid;
        this.password = password;
    }

    @Override
    public String toString(){
        return userid;
    }
}
