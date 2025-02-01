package com.example.flappybird;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.Iterator;

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles;
    private final int spawnX = 1200;  // X position where new obstacles appear
    private final int obstacleSpacing = 700;  // Distance between obstacles
    private Bird bird;  // Reference to the bird

    public ObstacleManager(Bird bird) {
        this.bird = bird;
        obstacles = new ArrayList<>();
        spawnObstacle();  // Spawn first obstacle
    }

    public void update() {
        if (bird.isGameOver()) {
            return; // Stop updating obstacles if the game is over
        }

        // Spawn a new obstacle if the last one has moved far enough
        if (obstacles.isEmpty() || spawnX - obstacles.get(obstacles.size() - 1).getX() >= obstacleSpacing) {
            spawnObstacle();
        }

        // Update all obstacles
        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.update();

            // Check for collision
            if (obstacle.collidesWith(bird)) {
                bird.setGameOver(true);  // End the game if the bird hits an obstacle
            }

            // Remove obstacles that are out of the screen to free memory
            if (obstacle.isOutOfScreen()) {
                iterator.remove();
            }
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(canvas, paint);
        }
    }

    // Spawns a new obstacle at the designated x position
    private void spawnObstacle() {
        obstacles.add(new Obstacle(spawnX));
    }
}
