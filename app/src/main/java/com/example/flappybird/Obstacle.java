package com.example.flappybird;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle {
    private int x, width, height;
    private int speed = 7;  // Speed at which obstacles move left
    private Rect upperPipe, lowerPipe;
    private static final int PIPE_WIDTH = 150;
    private static final int GAP_HEIGHT = 500;  // Space between pipes

    public Obstacle(int startX) {
        this.x = startX;
        int pipeHeight = (int) (Math.random() * 800) + 200; // Random height for the upper pipe

        this.width = PIPE_WIDTH;
        this.height = pipeHeight;

        // Define upper and lower pipes
        this.upperPipe = new Rect(x, 0, x + width, height);
        this.lowerPipe = new Rect(x, height + GAP_HEIGHT, x + width, 2400);
    }

    public void update() {
        x -= speed;  // Move pipes to the left
        upperPipe.set(x, 0, x + width, height);
        lowerPipe.set(x, height + GAP_HEIGHT, x + width, 2400);
    }

    public void draw(Canvas canvas, Paint paint) {
        // Pipe Color & Style
        paint.setColor(Color.rgb(34, 139, 34));  // Forest Green for pipe color
        paint.setStyle(Paint.Style.FILL);

        // Draw Pipes
        canvas.drawRect(upperPipe, paint);
        canvas.drawRect(lowerPipe, paint);

        // Draw Pipe Borders for Depth Effect
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        canvas.drawRect(upperPipe, paint);
        canvas.drawRect(lowerPipe, paint);
    }

    public boolean isOutOfScreen() {
        return x + width < 0;
    }

    public boolean collidesWith(Bird bird) {
        Rect birdRect = new Rect(bird.getX(), bird.getY(), bird.getX() + bird.getWidth(), bird.getY() + bird.getHeight());
        return Rect.intersects(upperPipe, birdRect) || Rect.intersects(lowerPipe, birdRect);
    }

    public int getX() {
        return x;
    }
}
