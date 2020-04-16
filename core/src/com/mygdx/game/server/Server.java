package com.mygdx.game.server;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class Server {
    private static Server _server = new Server();
    private HashMap<String, ArrayList<Emitter.Listener>> listeners = new HashMap<>();
    private List<Emitter.Listener> unregister = new LinkedList<>();
    private Socket socket;

    private String game_id;
    private String player_id;

    private Server() {
        try {
            socket = IO.socket("http://localhost:8300");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println("Connected!");
                }

            }).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject message = (JSONObject) args[0];

                    ArrayList<Emitter.Listener> registeredListeners = listeners.get(message.getString("type"));

                    if (registeredListeners == null) return;
                    //clone arraylist so the listener can safely remove itself without causing iteration problems
                    ArrayList<Emitter.Listener> i = (ArrayList<Emitter.Listener>) registeredListeners.clone();

                    for (Emitter.Listener listener : i
                    ) {
                        listener.call(message);
                    }

                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                }

            });
            socket.connect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    private static String generateId() {
        char[] _base62chars =
                "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            result += _base62chars[random.nextInt(62)];
        }
        return result;
    }

    public static Server getInstance() {
        return _server;
    }

    public void removeListener(String type, Emitter.Listener listener) {
        if (!listeners.containsKey(type)) {
            return;
        }

        listeners.get(type).remove(listener);
    }

    private void addListener(String type, Emitter.Listener listener) {
        if (!listeners.containsKey(type)) {
            listeners.put(type, new ArrayList<Emitter.Listener>());
        }

        listeners.get(type).add(listener);
    }

    private JSONObject createJSONObject(String type, JSONObject data) {
        JSONObject obj = new JSONObject();
        obj.put("type", type);
        obj.put("game_id", this.game_id);
        obj.put("player_id", this.player_id);
        obj.put("data", data);
        return obj;
    }

    public String[] newGame(Emitter.Listener listener) {
        this.game_id = Server.generateId();
        this.player_id = Server.generateId();

        this.send(this.createJSONObject("new_game", null));

        this.addListener("join_game", listener);
        this.addListener("leave_game", listener);

        return new String[]{
                this.player_id,
                this.game_id
        };
    }

    public void leaveGame() {
        System.out.println("leave!");
        this.send(this.createJSONObject("leave_game", null));
    }

    public String[] joinGame(String game_id, String player_name, Emitter.Listener listener) {
        this.game_id = game_id;
        this.player_id = Server.generateId();

        JSONObject data = new JSONObject();
        data.put("player_name", player_name);

        this.send(this.createJSONObject("join_game", data));

        this.addListener("join_game", listener);
        this.addListener("add_game", listener);

        return new String[]{
                this.player_id,
                this.game_id
        };
    }

    public String[] startGame() {
        this.send(this.createJSONObject("start_game", null));
        return new String[]{
                this.player_id,
                this.game_id
        };
    }

    public void updatePlayerName(String player_name) {
        JSONObject data = new JSONObject();
        data.put("player_name", player_name);

        this.send(createJSONObject("update_player_name", data));
    }

    public void getHighscores(Emitter.Listener listener) {
        this.addListener("get_highscores", listener);
        this.send(this.createJSONObject("get_highscores", null));
    }

    public void updatePlayerLocation(float x, float y) {
        JSONObject dataObj = new JSONObject();
        dataObj.put("x", x);
        dataObj.put("y", y);
        this.send(this.createJSONObject("update_location", dataObj));
    }

    public void addObstacle(float deltatime, String type) {
        JSONObject dataObj = new JSONObject();
        dataObj.put("deltatime", deltatime);
        dataObj.put("type", type);
        this.send(this.createJSONObject("add_obstacle", dataObj));
    }


    public void endGame(int score) {
        JSONObject dataObj = new JSONObject();
        dataObj.put("score", score);
        this.send(this.createJSONObject("end_game", dataObj));
    }

    private void send(JSONObject message) {
        System.out.println((message));
        try {
            socket.send(message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
