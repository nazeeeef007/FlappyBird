package com.example.flappybird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Bird bird;
    private ObstacleManager obstacleManager;
    private int screenWidth, screenHeight;
    private boolean isGameOver;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        bird = new Bird(200, 300, context);
        obstacleManager = new ObstacleManager(bird);
        isGameOver = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isGameOver) {
                restartGame(); // Restart game if it's over
            } else {
                bird.flap(); // Make the bird flap
            }
        }
        return true;
    }

    public void update() {
        if (!isGameOver) {
            bird.update();
            obstacleManager.update();

            if (bird.isGameOver()) {
                isGameOver = true;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        canvas.drawColor(Color.CYAN); // Background color

        bird.draw(canvas, paint);
        obstacleManager.draw(canvas, paint);

        if (isGameOver) {
            paint.setColor(Color.RED);
            paint.setTextSize(100);
            canvas.drawText("GAME OVER! Tap to Restart", screenWidth / 4, screenHeight / 2, paint);
        }
    }

    private void restartGame() {
        bird = new Bird(200, 300, getContext());
        obstacleManager = new ObstacleManager(bird);
        isGameOver = false;
    }
}
