package com.crunglers.speedcheckers;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

public class Piece extends AppCompatImageView {
    boolean isPlayer;
    boolean isKing;
    int health = 20;
    int x;
    int y;

    public Piece(Context context){
        super(context);
    }

    public Piece(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public void init(boolean isPlayerArg, Board board){
        isPlayer = isPlayerArg;
        isKing = false;
        if ( isPlayer )
            setImageResource(R.drawable.blue);
        else
            setImageResource(R.drawable.red);
        setOnClickListener( v -> {
            if ( isPlayer && board.playerTurn )
                board.enterMoveMode(this);
        });
    }

    public void init(boolean isPlayerArg ,boolean isKingArg, Board board){
        isPlayer = isPlayerArg;
        isKing = isKingArg;
        if ( isPlayer )
            setImageResource(R.drawable.blue);
        else
            setImageResource(R.drawable.red);
        setOnClickListener( v -> {
            if ( isPlayer )
                board.enterMoveMode(this);
        });
    }

    public void setYX(int y, int x){
        this.x = x;
        this.y = y;
    }
}
