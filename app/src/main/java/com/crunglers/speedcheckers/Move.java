package com.crunglers.speedcheckers;

public class Move {

    int pieceY;
    int pieceX;
    int diffY;
    int diffX;

    //dir = 0 is left, dir = 1 is right
    public Move(Piece piece, int dir){
        pieceY = piece.y;
        pieceX = piece.x;
        if ( dir == 0 ) {
            diffY = -2;
            diffX = -2;
        }
        else {
            diffY = -2;
            diffX = 2;
        }
    }

    public Move(){}

    Move mirroredMove(){
        Move mMove = new Move();
        mMove.diffY = this.diffY*(-1);
        mMove.diffX = this.diffX*(-1);
        mMove.pieceX = 7 - pieceX;
        mMove.pieceY = 7 - pieceX;
        return new Move();
    }
}
