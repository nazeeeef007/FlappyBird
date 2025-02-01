package com.example.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.BitmapFactory;
import android.content.Context;
import java.lang.Math;
public class Bird {
    private int x, y;
    private int velocity;
    private boolean isFlapping;
    private boolean isGameOver;
    private Bitmap birdBitmap;

    public Bird(int startX, int startY, Context context) {
        this.x = startX;
        this.y = startY;
        this.velocity = 0;
        this.isGameOver = false;

        // Load and scale the bird image
        birdBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird);
        int newWidth = birdBitmap.getWidth() / 3;
        int newHeight = birdBitmap.getHeight() / 3;
        birdBitmap = Bitmap.createScaledBitmap(birdBitmap, newWidth, newHeight, false);
    }

    public void update() {
        if (isGameOver) {
            return;  // Stop updating if the game is over
        }

        if (isFlapping) {
            velocity = -25; // Upward force when flapping
            isFlapping = false;
            velocity += 0.5;
        }
        else {
            velocity = (int)Math.min(13, velocity + 1.5); // Gravity effect
            y += velocity;
        }
        // End game if bird goes out of bounds
        if (y < 0 || y > 2400) {
            isGameOver = true;
        }
    }

    public void flap() {
        isFlapping = true;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;  // Setter for game over state
    }

    public void draw(Canvas canvas, Paint paint) {
        if (isGameOver) {
//            paint.setColor(Color.RED);
//            paint.setTextSize(100);
//            canvas.drawText("GAME OVER", 300, 400, paint);
            return;
        }

        canvas.drawBitmap(birdBitmap, x - birdBitmap.getWidth() / 2, y - birdBitmap.getHeight() / 2, paint);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return birdBitmap.getWidth();
    }

    public int getHeight() {
        return birdBitmap.getHeight();
    }
}
