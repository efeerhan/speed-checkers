package com.crunglers.speedcheckers;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class Cell extends AppCompatImageView {
    public Cell(Context context) {
        super(context);
    }
    public Cell(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    Piece currentPiece;
}
