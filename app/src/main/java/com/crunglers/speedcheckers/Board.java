package com.crunglers.speedcheckers;

import android.app.Activity;
import android.widget.LinearLayout;

import androidx.appcompat.widget.LinearLayoutCompat;

public class Board {
    Cell[][] grid = new Cell[8][8];
    LinearLayout[] rows = new LinearLayout[8];

    public Board(Activity activity){

        //get Rows
        rows[0] = activity.findViewById(R.id.r0);
        rows[1] = activity.findViewById(R.id.r1);
        rows[2] = activity.findViewById(R.id.r2);
        rows[3] = activity.findViewById(R.id.r3);
        rows[4] = activity.findViewById(R.id.r4);
        rows[5] = activity.findViewById(R.id.r5);
        rows[6] = activity.findViewById(R.id.r6);
        rows[7] = activity.findViewById(R.id.r7);


        for ( int k = 0; k < 8; k++ ) {
            for ( int l = 0; l < 8; l++ ) {
                grid[k][l] = new Cell(activity);
            }
        }


        //set src
        LinearLayout boardMaster = activity.findViewById(R.id.boardMaster);
        for ( int i = 0; i < 8; i++ ) {
            LinearLayout row = (LinearLayout)boardMaster.getChildAt(i);
            for ( int j = 0; j < 8; j++ ) {
                Cell cell = (Cell)row.getChildAt(j);
                if ( i % 2 == 0 ) {
                    if (j % 2 == 0) {
                        cell.setImageResource(R.drawable.cat);
                    }
                    else {
                        cell.setImageResource(R.drawable.jet);
                    }
                }
                else {
                    if (j % 2 != 0) {
                        cell.setImageResource(R.drawable.cat);
                    }
                    else {
                        cell.setImageResource(R.drawable.jet);
                    }
                }
            }
        }


    }

    public void movePiece(Move move){

    }
}
