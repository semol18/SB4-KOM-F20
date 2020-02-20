/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import static dk.sdu.mmmi.cbse.asteroid.AsteroidType.*;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;

/**
 *
 * @author sebastian
 */
public class AsteroidSplitter implements IEntityProcessingService {

    private Random rd = new Random();

    @Override
    public void process(GameData gd, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            Asteroid theAsteroid = (Asteroid) asteroid;
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            SplitterPart splitter = asteroid.getPart(SplitterPart.class);

            if (theAsteroid.getSize().equals("LARGE") && splitter.ShouldSplit()) {
                splitter.setShouldSplit(false);
                Asteroid mediumAsteroidOne = createMediumAsteroid(positionPart.getX(), positionPart.getY());
                Asteroid mediumAsteroidTwo = createMediumAsteroid(positionPart.getX(), positionPart.getY());
                world.addEntity(mediumAsteroidOne);
                world.addEntity(mediumAsteroidTwo);
            }

            if (theAsteroid.getSize().equals("MEDIUM") && splitter.ShouldSplit()) {
                splitter.setShouldSplit(false);
                Asteroid smallAsteroidOne = createSmallAsteroid(positionPart.getX(), positionPart.getY());
                Asteroid smallAsteroidTwo = createSmallAsteroid(positionPart.getX(), positionPart.getY());
                world.addEntity(smallAsteroidOne);
                world.addEntity(smallAsteroidTwo);
            }
        }
    }

    private Asteroid createMediumAsteroid(float x, float y) {
        float speed = (float) Math.random() * 10f + 40f;
        float radians = 3.1415f / 2 + (float) Math.random();

        Entity asteroid = new Asteroid(MEDIUM);
        //asteroid.setColor(new float[]{255f, 0f, 160f, 1f});
        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x + rd.nextInt(50), y + rd.nextInt(50), radians));
        asteroid.add(new LifePart(4, 69));
        asteroid.add(new SplitterPart());
        asteroid.setRadius(10);

        return (Asteroid) asteroid;
    }

    private Asteroid createSmallAsteroid(float x, float y) {
        float speed = (float) Math.random() * 10f + 13f;
        float radians = (float) (Math.PI / 2 + Math.random());

        Entity asteroid = new Asteroid(SMALL);
        //asteroid.setColor(new float[]{255f, 0f, 160f, 1f});
        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x + rd.nextInt(50), y + rd.nextInt(50), radians));
        asteroid.add(new LifePart(2, 69));
        asteroid.add(new SplitterPart());
        asteroid.setRadius(5);

        return (Asteroid) asteroid;
    }

}
