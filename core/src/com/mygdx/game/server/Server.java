package com.mygdx.game.server;

import com.google.gson.JsonObject;

import java.util.Date;

import  io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;


import org.json.JSONObject;


public class Server {
    private static Server _server;
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
                    System.out.println(args[0]);
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {}

            });
            socket.connect();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        this.getHighscores();

    }

    private static String getAlphaNumericString(int n)
    {

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
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    private static String generateId() {
        return new Date().getTime() + "_"+ Server.getAlphaNumericString(5);
    }

    public static Server getInstance() {
        if (_server == null) {
            _server = new Server();
        }
        return _server;
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
        this.game_id =  Server.generateId();
        this.player_id = Server.generateId();
        JSONObject dataObj = new JSONObject();
        dataObj.put("player_name", "Fred");

        this.send(this.createJSONObject("new_game", dataObj));

        return new String [] {
            this.player_id,
           this.game_id
        };
    }

    public void getHighscores() {
        this.send(this.createJSONObject("get_highscores", null));
    }

    public void updatePlayerLocation(float x, float y) {
        JSONObject dataObj = new JSONObject();
        dataObj.put( "x", x );
        dataObj.put("y", y  );
        this.send(this.createJSONObject("update_location", dataObj));
    }

    public void addObstacle(float deltatime, String type){
        JSONObject dataObj = new JSONObject();
        dataObj.put("deltatime", deltatime);
        dataObj.put("type", type);
        this.send(this.createJSONObject("add_obstacle", dataObj));
    }


    public void endGame(int score){
        JSONObject dataObj = new JSONObject();
        dataObj.put("score", score);
        this.send(this.createJSONObject("end_game", dataObj));
    }
    private void send(JSONObject message) {
        System.out.println((message));
        try { socket.send(message);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
