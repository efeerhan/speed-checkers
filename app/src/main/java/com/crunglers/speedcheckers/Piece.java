package com.crunglers.speedcheckers;

public class Piece {
    boolean isPlayer;
    boolean isKing;
    int health = 20;

    public Piece(boolean isPlayerArg){
        isPlayer = isPlayerArg;
        isKing = false;
    }

    public Piece(boolean isPlayerArg ,boolean isKingArg){
        isPlayer = isPlayerArg;
        isKing = isKingArg;
    }
}
