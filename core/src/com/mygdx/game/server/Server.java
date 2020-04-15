package com.mygdx.game.server;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class Server {
    private static Server _server;
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

                    //clone arraylist so the listener can safely remove itself without causing iteration problems
                    ArrayList<Emitter.Listener> i = (ArrayList<Emitter.Listener>) listeners.get(message.getString("type")).clone();

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
        return new Date().getTime() + "_" + Server.getAlphaNumericString(5);
    }

    public static Server getInstance() {
        if (_server == null) {
            _server = new Server();
        }
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

    public String[] startGame() {
        this.game_id = Server.generateId();
        this.player_id = Server.generateId();
        JSONObject dataObj = new JSONObject();
        dataObj.put("player_name", "Fred");

        this.send(this.createJSONObject("new_game", dataObj));

        return new String[]{
                this.player_id,
                this.game_id
        };
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
        //System.out.println((message));
        try {
            socket.send(message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
