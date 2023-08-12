package it.petrillo.jbomberman.controller;

import com.google.gson.*;
import it.petrillo.jbomberman.model.Bomberman;
import it.petrillo.jbomberman.util.UserData;
import it.petrillo.jbomberman.view.*;

import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.io.*;

import static it.petrillo.jbomberman.util.GameSettings.*;


public class GameManager {

    private final GameFrame gameFrame = new GameFrame();
    private final GamePanel gamePanel = new GamePanel();
    private final GameMenu gameMenu = new GameMenu();
    private final GameMenuPanel gameMenuPanel = new GameMenuPanel();
    private final PlayerPanel playerPanel = new PlayerPanel();
    private UserData currentPlayerData;
    private JsonObject database;

    public GameManager() {
        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(gamePanel,BorderLayout.CENTER);
        gameFrame.add(playerPanel,BorderLayout.NORTH);
        gameFrame.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT+188));
        gameFrame.setLocationRelativeTo(null);
        gameMenu.add(gameMenuPanel);
        gameMenu.pack();
        Bomberman bomberman = Bomberman.getPlayerInstance();
        bomberman.addObserver(playerPanel);
        bomberman.addObserver(gamePanel);
        uploadDatabase();
    }

    public void openGame() {
        gameMenuPanel.getPlayButton().addActionListener(e -> {
            String nick = gameMenuPanel.getNickname();
            String chosenAvatar = gameMenuPanel.getChosenAvatarPath();
            if (nick != null && !nick.equals("") && chosenAvatar != null && !chosenAvatar.equals("")) {
                setCurrentPlayerData(nick);
                currentPlayerData.setAvatarPath(chosenAvatar);
                playerPanel.uploadPlayerData(currentPlayerData);
                SwingUtilities.invokeLater(this::startGame);
                updateDatabase();
                gameMenu.setVisible(false);
            }
            else
                System.out.println("Inserire Nick e Scegliere un Avatar");
        });
        gameMenu.setLocationRelativeTo(null);
        gameMenu.setVisible(true);
    }
    public void startGame() {
        GAME_STATE = GameState.LOADING;
        gamePanel.startThread();
        gameFrame.setVisible(true);
        System.out.println("******** Game Started! ********");
        Timer startingTimer = new Timer(1500,
                e -> {
                    GAME_STATE = GameState.PLAYING;
                    playerPanel.startTimer();
                    });
        startingTimer.setRepeats(false);
        startingTimer.start();
    }

    private void setCurrentPlayerData (String nickname) {
        JsonObject playerRecord = getPlayerRecordIfExist(nickname);
        if (playerRecord != null)
            currentPlayerData = parsePlayerRecord(playerRecord);
        else
            currentPlayerData = new UserData(nickname);
    }

    private JsonObject getPlayerRecordIfExist(String nickname) {
        JsonArray players = database.getAsJsonArray("players");
        for (JsonElement record : players) {
            JsonObject playerRecord = record.getAsJsonObject();
            if (playerRecord.get("nickname").getAsString().equals(nickname))
                return playerRecord;
        }
        return null;
    }
    private UserData parsePlayerRecord(JsonObject playerRecord) {
        String nick = playerRecord.get("nickname").getAsString();
        int win = playerRecord.get("win").getAsInt();
        int lose = playerRecord.get("lose").getAsInt();
        return new UserData(nick,win,lose);
    }

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

}
