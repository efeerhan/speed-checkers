package com.crunglers.speedcheckers;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class Board {
    Cell[][] grid = new Cell[8][8];
    LinearLayout[] rows = new LinearLayout[8];
    GameActivity activity;
    boolean playerTurn = true;

    public Board(GameActivity activity){

        this.activity = activity;

        //get Rows
        rows[0] = activity.findViewById(R.id.r0);
        rows[1] = activity.findViewById(R.id.r1);
        rows[2] = activity.findViewById(R.id.r2);
        rows[3] = activity.findViewById(R.id.r3);
        rows[4] = activity.findViewById(R.id.r4);
        rows[5] = activity.findViewById(R.id.r5);
        rows[6] = activity.findViewById(R.id.r6);
        rows[7] = activity.findViewById(R.id.r7);

        for ( int i = 0; i < 8; i++ ){
            for ( int j = 0; j < 8; j++ ) {
                grid[i][j] = (Cell)(rows[i].getChildAt(j));
            }
        }

        //populate rows
        for ( int i = 0; i < 8; i++ ){
            if ( i % 2 == 0 ) {
                Cell cell = (Cell) rows[0].getChildAt(i);
                grid[0][i].currentPiece = new Piece(activity);
                grid[0][i].currentPiece.init(false, this);
                grid[0][i].currentPiece.setYX(0,i);
                cell.addView(grid[0][i].currentPiece);
            }
        }
        for ( int i = 0; i < 8; i++ ){
            if ( i % 2 != 0 ) {
                Cell cell = (Cell) rows[1].getChildAt(i);
                grid[1][i].currentPiece = new Piece(activity);
                grid[1][i].currentPiece.init(false, this);
                grid[1][i].currentPiece.setYX(1,i);
                cell.addView(grid[1][i].currentPiece);
            }
        }
        for ( int i = 0; i < 8; i++ ){
            if ( i % 2 == 0 ) {
                Cell cell = (Cell) rows[2].getChildAt(i);
                grid[2][i].currentPiece = new Piece(activity);
                grid[2][i].currentPiece.init(false, this);
                grid[2][i].currentPiece.setYX(2,i);

                cell.addView(grid[2][i].currentPiece);
            }
        }
        for ( int i = 0; i < 8; i++ ){
            if ( i % 2 == 0 ) {
                Cell cell = (Cell) rows[5].getChildAt(i);
                grid[5][i].currentPiece = new Piece(activity);
                grid[5][i].currentPiece.init(true, this);
                grid[5][i].currentPiece.setYX(5,i);
                cell.addView(grid[5][i].currentPiece);
            }
        }
        for ( int i = 0; i < 8; i++ ){
            if ( i % 2 != 0 ) {
                Cell cell = (Cell) rows[6].getChildAt(i);
                grid[6][i].currentPiece = new Piece(activity);
                grid[6][i].currentPiece.init(true, this);
                grid[6][i].currentPiece.setYX(6,i);

                cell.addView(grid[6][i].currentPiece);
            }
        }
        for ( int i = 0; i < 8; i++ ){
            if ( i % 2 == 0 ) {
                Cell cell = (Cell) rows[7].getChildAt(i);
                grid[7][i].currentPiece = new Piece(activity);
                grid[7][i].currentPiece.init(true, this);
                grid[7][i].currentPiece.setYX(7,i);

                cell.addView(grid[7][i].currentPiece);
            }
        }
    }

    public void damage(Cell cell, int time){
        Piece damaged = (Piece)cell.getChildAt(0);
        damaged.health -= (20*(time/5));
        if (damaged.health <= 0)
            cell.removeAllViews();
    }

    public void movePiece(Move move, Piece piece){
        grid[move.pieceY][move.pieceX].removeView(piece);
        grid[move.pieceY + move.diffY][move.pieceX + move.diffX].addView(piece);
        damage(grid[(int) (move.pieceY + (0.5*move.diffY))][(int) (move.pieceX + (0.5*move.diffX))], activity.time);
        piece.setYX(move.pieceY + move.diffY, move.pieceX + move.diffX);
    }

    public void enterMoveMode(Piece piece){

        Cell availableLeft = null;
        Cell availableRight = null;

        try{
            availableLeft = grid[piece.y-2][piece.x-2];
        } catch (Exception ignored) {}

        try{
            availableRight = grid[piece.y-2][piece.x+2];
        } catch (Exception ignored) {}

        Cell finalAvailableLeft = availableLeft;
        Cell finalAvailableRight = availableRight;

        if ( availableLeft != null ) {
            availableLeft.setOnClickListener(v -> {
                Move moveLeft = new Move(piece, 0);
                movePiece(moveLeft, piece);
                finalAvailableLeft.setOnClickListener(null);
                if ( finalAvailableRight != null )
                    finalAvailableRight.setOnClickListener(null);
                playerTurn = false;
            });
        }

        if ( availableRight != null ) {
            availableRight.setOnClickListener(v -> {
                Move moveRight = new Move(piece, 1);
                movePiece(moveRight, piece);
                if ( finalAvailableLeft != null )
                    finalAvailableLeft.setOnClickListener(null);
                finalAvailableRight.setOnClickListener(null);
                playerTurn = false;
            });
        }

    }
}
