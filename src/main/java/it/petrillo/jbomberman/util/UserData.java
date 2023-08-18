package it.petrillo.jbomberman.util;

/**
 * The UserData class represents information about a user's profile and gameplay statistics.
 */
public class UserData {

    private String nickname;
    private String avatarPath;
    private int win, lose;

    /**
     * Constructs a UserData instance with the specified nickname.
     *
     * @param nickname The nickname associated with the user.
     */
    public UserData(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Constructs a UserData instance with the specified nickname, number of wins, and number of losses.
     *
     * @param nickname The nickname associated with the user.
     * @param win The number of wins achieved by the user.
     * @param lose The number of losses experienced by the user.
     */
    public UserData(String nickname, int win, int lose) {
        this.nickname = nickname;
        this.win = win;
        this.lose = lose;
    }

    /**
     * Returns the nickname of the user.
     *
     * @return The nickname of the user.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the nickname of the user.
     *
     * @param nickname The new nickname to set for the user.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the avatar path of the user.
     *
     * @return The avatar path of the user.
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    /**
     * Sets the avatar path of the user.
     *
     * @param avatarPath The new avatar path to set for the user.
     */
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    /**
     * Returns the number of wins achieved by the user.
     *
     * @return The number of wins achieved by the user.
     */
    public int getWin() {
        return win;
    }

    /**
     * Increases the number of wins by one.
     */
    public void win() {
        this.win++;
    }

    /**
     * Returns the number of losses experienced by the user.
     *
     * @return The number of losses experienced by the user.
     */
    public int getLose() {
        return lose;
    }

    /**
     * Increases the number of losses by one.
     */
    public void lose() {
        this.lose++;
    }
}
