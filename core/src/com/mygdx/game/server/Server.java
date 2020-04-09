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
    private Server() {
        try {
            socket = IO.socket("http://localhost:8300");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println("Connected!");
                }

            }).on("event", new Emitter.Listener() {

                @Override
                public void call(Object... args) {}

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {}

            });
            socket.connect();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

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

    private JSONObject createJSONObject(String type, String game_id, String player_id, JSONObject data) {
        JSONObject obj = new JSONObject();
        obj.put("type", type);
        obj.put("game_id", game_id);
        obj.put("player_id", player_id);
        obj.put("data", data);
        return obj;
    }

    public String[] startGame() {
        final String generated_game_id = Server.generateId();
        final String generated_player_id = Server.generateId();
        JSONObject dataObj = new JSONObject();
        dataObj.put("player_name", "Fred");

        this.send(this.createJSONObject("new_game", generated_game_id, generated_player_id, dataObj));
        return new String [] {
            generated_player_id,
           generated_game_id
        };
    }

    public void updatePlayerLocation(String game_id, String player_id, float x, float y) {

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
