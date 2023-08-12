package it.petrillo.jbomberman.util;

public class UserData {

    private String nickname;
    private String avatarPath;
    private int win, lose;
    public UserData(String nickname) {
        this.nickname = nickname;
    }

    public UserData(String nickname, int win, int lose) {
        this.nickname = nickname;
        this.win = win;
        this.lose = lose;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }
}
