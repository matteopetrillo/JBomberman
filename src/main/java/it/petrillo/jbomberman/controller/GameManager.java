package it.petrillo.jbomberman.controller;

import com.google.gson.*;
import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.model.CollisionListener;
import it.petrillo.jbomberman.model.GameStateListener;
import it.petrillo.jbomberman.util.CustomObserver;
import it.petrillo.jbomberman.util.UserData;
import it.petrillo.jbomberman.view.*;

import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.io.*;

import static it.petrillo.jbomberman.util.GameSettings.*;


/**
 * The GameManager class manages the overall game flow, including level loading, player interactions, and game state transitions.
 */
public class GameManager implements CustomObserver, GameStateListener {

    public static GameState GAME_STATE = GameState.MENU;
    private final GameFrame gameFrame = new GameFrame();
    private final GamePanel gamePanel = new GamePanel();
    private final GameMenu gameMenu = new GameMenu();
    private final GameMenuPanel gameMenuPanel = new GameMenuPanel();
    private final PlayerPanel playerPanel = new PlayerPanel();
    private final Bomberman bomberman = Bomberman.getPlayerInstance();
    private final LevelManager levelManager = LevelManager.getInstance();
    private UserData currentPlayerData;
    private JsonObject database;

    /**
     * Initializes the GameManager by setting up UI components and registering observers.
     */
    public GameManager() {
        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(gamePanel,BorderLayout.CENTER);
        gameFrame.add(playerPanel,BorderLayout.NORTH);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameMenu.add(gameMenuPanel);
        gameMenu.pack();
        bomberman.addObserver(playerPanel);
        bomberman.addObserver(this);
        levelManager.setGameStateListener(this);
        playerPanel.setGameStateListener(this);
        uploadDatabase();
    }

    /**
     * Opens the game menu, allowing players to choose their nickname and avatar before starting the game.
     */
    public void openGame() {
        gameMenuPanel.getPlayButton().addActionListener(e -> {
            String nick = gameMenuPanel.getNickname();
            String chosenAvatar = gameMenuPanel.getChosenAvatarPath();
            if (nick != null && !nick.equals("") && chosenAvatar != null && !chosenAvatar.equals("")) {
                setCurrentPlayerData(nick);
                currentPlayerData.setAvatarPath(chosenAvatar);
                playerPanel.uploadPlayerData(currentPlayerData);
                SwingUtilities.invokeLater(this::startGame);
                gameMenu.setVisible(false);
            }
            else
                System.out.println("Inserire Nick e Scegliere un Avatar");
        });
        gameMenu.setLocationRelativeTo(null);
        gameMenu.setVisible(true);
    }

    /**
     * Starts the game by transitioning from the menu to the actual gameplay.
     */
    public void startGame() {
        GAME_STATE = GameState.LOADING;
        gamePanel.startThread();
        gameFrame.setVisible(true);
        System.out.println("******** Game Started! ********");
        Timer startingTimer = new Timer(4000,
                e -> {
                    onPlaying();
                    });
        startingTimer.setRepeats(false);
        startingTimer.start();
    }

    /**
     * Sets the current player's data based on the provided nickname.
     *
     * @param nickname The nickname of the current player.
     */
    private void setCurrentPlayerData (String nickname) {
        JsonObject playerRecord = getPlayerRecordIfExist(nickname);
        if (playerRecord != null)
            currentPlayerData = parsePlayerRecord(playerRecord);
        else
            currentPlayerData = new UserData(nickname);
    }

    /**
     * Sets the current player's data based on the provided nickname.
     *
     * @param nickname The nickname of the current player.
     */
    private JsonObject getPlayerRecordIfExist(String nickname) {
        JsonArray players = database.getAsJsonArray("players");
        for (JsonElement record : players) {
            JsonObject playerRecord = record.getAsJsonObject();
            if (playerRecord.get("nickname").getAsString().equals(nickname))
                return playerRecord;
        }
        return null;
    }

    /**
     * Parses a player record JsonObject into a UserData object.
     *
     * @param playerRecord The JsonObject containing player record data.
     * @return The parsed UserData object.
     */
    private UserData parsePlayerRecord(JsonObject playerRecord) {
        String nick = playerRecord.get("nickname").getAsString();
        int win = playerRecord.get("win").getAsInt();
        int lose = playerRecord.get("lose").getAsInt();
        return new UserData(nick,win,lose);
    }

    /**
     * Uploads the game database from a JSON file.
     */
    private void uploadDatabase() {
        String projectDir = System.getProperty("user.dir");
        String filePath = Paths.get(projectDir, DB_PATH).toString();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            String jsonString = jsonContent.toString();
            this.database = JsonParser.parseString(jsonString).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the game database with the current player's data.
     */
    private void updateDatabase() {
        JsonArray playersArray = database.getAsJsonArray("players");
        boolean found = false;
        for (JsonElement playerElement : playersArray) {
            JsonObject playerObject = playerElement.getAsJsonObject();
            if (playerObject.get("nickname").getAsString().equals(currentPlayerData.getNickname())) {
                playerObject.addProperty("win", currentPlayerData.getWin());
                playerObject.addProperty("lose", currentPlayerData.getLose());
                found = true;
                break;
            }
        }

        if (!found) {
            JsonObject newPlayerObject = new JsonObject();
            newPlayerObject.addProperty("nickname", currentPlayerData.getNickname());
            newPlayerObject.addProperty("win", currentPlayerData.getWin());
            newPlayerObject.addProperty("lose", currentPlayerData.getLose());
            playersArray.add(newPlayerObject);
        }
        saveDatabase();
    }

    /**
     * Saves the updated game database to a JSON file.
     */
    private void saveDatabase() {
        String projectDir = System.getProperty("user.dir");
        String filePath = Paths.get(projectDir, DB_PATH).toString();
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(database.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(NotificationType notificationType, Object arg) {
        switch (notificationType) {
            case FINISH_LEVEL -> levelManager.loadNextLevel();
            case GAME_OVER -> onLosing();
        }
    }

    @Override
    public void onLosing() {
        System.out.println("Hai Perso!");
        currentPlayerData.lose();
        GAME_STATE = GameState.GAME_OVER;
        Timer exitTimer = new Timer(1500, e -> {
            updateDatabase();
            System.exit(0);
        });
        exitTimer.setRepeats(false);
        exitTimer.start();
    }

    @Override
    public void onWinning() {
        System.out.println("Hai Vinto!");
        currentPlayerData.win();
        GAME_STATE = GameState.GAME_OVER;
        Timer exitTimer = new Timer(1500, e -> {
            updateDatabase();
            System.exit(0);
        });
        exitTimer.setRepeats(false);
        exitTimer.start();
    }

    @Override
    public void onLoading() {
        GAME_STATE = GameState.LOADING;
        playerPanel.resetTimer();
    }

    @Override
    public void onPlaying() {
        GAME_STATE = GameState.PLAYING;
        gamePanel.setLoadingPanel(null);
        playerPanel.startTimer();
    }

}
