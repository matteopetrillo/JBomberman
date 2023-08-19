package it.petrillo.jbomberman.controller;

import com.google.gson.*;
import it.petrillo.jbomberman.model.characters.Bomberman;
import it.petrillo.jbomberman.util.observer.ModelObserver;
import it.petrillo.jbomberman.util.NotificationType;
import it.petrillo.jbomberman.util.UserData;
import it.petrillo.jbomberman.view.GameFrame;
import it.petrillo.jbomberman.view.ending.EndingPanel;
import it.petrillo.jbomberman.view.game.GamePanel;
import it.petrillo.jbomberman.view.game.PlayerPanel;
import it.petrillo.jbomberman.view.menu.MenuPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import javax.swing.*;
import java.awt.*;
import java.io.*;

import static it.petrillo.jbomberman.util.GameConstants.*;


/**
 * The GameManager class manages the overall game flow, including level loading, player interactions, and game state transitions.
 */
public class GameManager implements ModelObserver, GameStateListener {

    /**
     * Represents the different states of the game.
     */
    public enum GameState {
        MENU,PLAYING,GAME_FINISHED,LOADING
    }

    private static GameManager gameManagerInstance;
    public static GameState GAME_STATE = GameState.MENU;
    private final GameFrame gameFrame;
    private final GameFrame menuFrame;
    private GameFrame endingFrame;
    private final GamePanel gamePanel = new GamePanel();
    private final MenuPanel menuPanel = new MenuPanel();
    private final PlayerPanel playerPanel = new PlayerPanel();
    private final Bomberman bomberman = Bomberman.getPlayerInstance();
    private final LevelManager levelManager = LevelManager.getInstance();
    private final AudioManager audioManager = AudioManager.getAudioManagerInstance();
    private UserData currentPlayerData;
    private JsonObject database;

    /**
     * Initializes the GameManager by setting up UI components and registering observers.
     */
    private GameManager() {
        gameFrame = new GameFrame();
        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(gamePanel,BorderLayout.CENTER);
        gameFrame.add(playerPanel,BorderLayout.NORTH);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        menuFrame = new GameFrame();
        menuFrame.add(menuPanel);
        menuFrame.pack();
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
        System.out.println("****WELCOME TO JBOMBERMAN!****");
        audioManager.playMenuMusic();
        menuPanel.getPlayButton().addActionListener(e -> {
            String nick = menuPanel.getNickname();
            String chosenAvatar = menuPanel.getChosenAvatarPath();
            if (nick != null && !nick.equals("") && chosenAvatar != null && !chosenAvatar.equals("")) {
                setCurrentPlayerData(nick);
                currentPlayerData.setAvatarPath(chosenAvatar);
                playerPanel.uploadPlayerData(currentPlayerData);
                SwingUtilities.invokeLater(this::startGame);
                menuFrame.setVisible(false);
            }
            else
                System.out.println("Inserire Nick e Scegliere un Avatar");
        });
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);

    }

    /**
     * Starts the game by transitioning from the menu to the actual gameplay.
     */
    private void startGame() {
        System.out.println("****HAVE A GOOD TIME PLAYING JBOMBERMAN!****");
        gamePanel.startThread();
        gameFrame.setVisible(true);
        onLoading();
    }

    /**
     * Restarts the game, including resetting player stats and level progress.
     */
    private void restartGame() {
        levelManager.restartGame();
        playerPanel.resetTimer();
        bomberman.resetPlayer();
        startGame();
    }


    /**
     * Sets the current player's data based on the provided nickname.
     *
     * @param nickname The nickname of the current player.
     */
    private void setCurrentPlayerData (String nickname) {
        JsonObject playerRecord = getPlayerRecord(nickname);
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
    private JsonObject getPlayerRecord(String nickname) {
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

    /**
     * Handles the game state when the player loses.
     */
    @Override
    public void onLosing() {
        GAME_STATE = GameState.GAME_FINISHED;
        System.out.println("****YOU LOSE!!****");
        audioManager.fadeOutGamePlayMusic();
        audioManager.play("/SFX/losing_sfx.wav",-12f);
        audioManager.playMenuMusic();
        currentPlayerData.lose();
        updateDatabase();
        gamePanel.stopThread();
        Timer exitTimer = new Timer(1000, e -> {
            updateDatabase();
            EndingPanel endingPanel = new EndingPanel(false,currentPlayerData);
            showEndingFrame(endingPanel);
        });
        exitTimer.setRepeats(false);
        exitTimer.start();
    }

    /**
     * Handles the game state when the player wins.
     */
    @Override
    public void onWinning() {
        GAME_STATE = GameState.GAME_FINISHED;
        System.out.println("****YOU WON!****");
        audioManager.fadeOutGamePlayMusic();
        audioManager.play("/SFX/winning_sfx.wav",-8f);
        audioManager.playMenuMusic();
        currentPlayerData.win();
        updateDatabase();
        gamePanel.stopThread();
        Timer exitTimer = new Timer(1000, e -> {
            updateDatabase();
            EndingPanel endingPanel = new EndingPanel(true,currentPlayerData);
            showEndingFrame(endingPanel);
        });
        exitTimer.setRepeats(false);
        exitTimer.start();
    }

    /**
     * Handles the game state during the loading phase.
     */
    @Override
    public void onLoading() {
        GAME_STATE = GameState.LOADING;
        audioManager.fadeOutMenuMusic();
        audioManager.play("/SFX/loading_screen_music.wav",0f);
        Timer startingTimer = new Timer(2500,
                e -> onPlaying());
        startingTimer.setRepeats(false);
        startingTimer.start();
    }

    /**
     * Handles the game state during the playing phase.
     */
    @Override
    public void onPlaying() {
        GAME_STATE = GameState.PLAYING;
        audioManager.playGameplayMusic();
        gamePanel.setLoadingPanel(null);
        playerPanel.startTimer();
    }

    /**
     * Displays the ending frame and handles user interaction after the game ends.
     *
     * @param endingPanel The ending frame to be displayed.
     */
    private void showEndingFrame(EndingPanel endingPanel) {
        endingFrame = new GameFrame();

        endingPanel.getRestartButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("****RESTARTING!****");
                endingPanel.setVisible(false);
                playerPanel.uploadPlayerData(currentPlayerData);
                restartGame();
            }
        });
        endingFrame.add(endingPanel);
        endingFrame.pack();
        endingFrame.setLocationRelativeTo(null);
        endingFrame.setVisible(true);
    }

    /**
     * Retrieves the singleton instance of the GameManager.
     *
     * @return The GameManager instance.
     */
    public static GameManager getInstance() {
        if (gameManagerInstance == null)
            gameManagerInstance = new GameManager();
        return gameManagerInstance;
    }
}
