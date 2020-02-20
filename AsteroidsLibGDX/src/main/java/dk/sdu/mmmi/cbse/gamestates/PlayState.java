package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.entities.Asteroid;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.main.Game;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;
import java.util.ArrayList;

public class PlayState extends GameState {

    private ShapeRenderer sr;
    private Player player;
    private ArrayList<Bullet> playerBullets;
    private ArrayList<Bullet> enemyBullets;
    private Enemy enemy;
    private int enemyShootTimer;
    private ArrayList<Asteroid> asteroids;
    private int level;
    private int totalAsteroids;
    private int numAsteroidsLeft;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {

        sr = new ShapeRenderer();

        playerBullets = new ArrayList<Bullet>();
        player = new Player(playerBullets);

        enemyBullets = new ArrayList<Bullet>();
        enemy = new Enemy(enemyBullets);

        asteroids = new ArrayList<Asteroid>();

        spawnAsteroids();

    }

    private void spawnAsteroids() {
        asteroids.clear();
        int numToSpawn = 4;
        totalAsteroids = numToSpawn * 7;
        numAsteroidsLeft = totalAsteroids;

        for (int i = 0; i < numToSpawn; i++) {
            float x = MathUtils.random(Game.WIDTH);
            float y = MathUtils.random(Game.HEIGHT);

            float dx = x - player.getx();
            float dy = y - player.gety();
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            while (dist < 100) {
                x = MathUtils.random(Game.WIDTH);
                y = MathUtils.random(Game.HEIGHT);
                dx = x - player.getx();
                dy = y - player.gety();
                dist = (float) Math.sqrt(dx * dx + dy * dy);
            }
            
            asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
        }
    }

    @Override
    public void update(float dt) {

        // Get user input.
        handleInput();

        // Update player.
        player.update(dt);

        // Update player bullets.
        for (int i = 0; i < playerBullets.size(); i++) {
            playerBullets.get(i).update(dt);
            if (playerBullets.get(i).shouldRemove()) {
                playerBullets.remove(i);
                i--;
            }
        }

        // Update enemy.
        enemy.update(dt);

        // Update enemy bullets.
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).update(dt);
            if (enemyBullets.get(i).shouldRemove()) {
                enemyBullets.remove(i);
                i--;
            }
        }

        // Update asteroids.
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).update(dt);
            if (asteroids.get(i).shouldRemove()) {
                asteroids.remove(i);
                i--;
            }
        }
    }

    public void draw() {

        // Draw player.
        player.draw(sr);

        // Draw player bullets.
        for (int i = 0; i < playerBullets.size(); i++) {
            playerBullets.get(i).draw(sr);
        }

        // Draw enemy.
        enemy.draw(sr);

        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).draw(sr);
        }

        // Draw asteroids.
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).draw(sr);
        }
    }

    public void handleInput() {

        // Player movement.
        player.setLeft(GameKeys.isDown(GameKeys.LEFT));
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));
        player.setUp(GameKeys.isDown(GameKeys.UP));
        player.setDown(GameKeys.isDown(GameKeys.DOWN));

        // Player shoot.
        if (GameKeys.isPressed(GameKeys.SPACE)) {
            player.shoot();
        }

        // Enemy movement.
        enemy.move();

        // Enemy shoot.
        if (enemyShootTimer == 0) {
            enemy.shoot();
            enemyShootTimer = 60;
        }
        enemyShootTimer--;
    }

    public void dispose() {
    }

}
