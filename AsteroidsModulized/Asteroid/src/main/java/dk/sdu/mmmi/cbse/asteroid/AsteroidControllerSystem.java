/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author sebastian
 */
@ServiceProvider(service = IEntityProcessingService.class)
public class AsteroidControllerSystem implements IEntityProcessingService {

    private int numPoints = 6;
    private Random rd = new Random(10);
    private float angle = 0;

    @Override
    public void process(GameData gd, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);

            float speed = (float) Math.random() * 10f + 40f;

            if (rd.nextInt() < 8) {
                movingPart.setMaxSpeed(speed);
                movingPart.setUp(true);
            } else {
                movingPart.setLeft(true);
            }

            movingPart.process(gd, asteroid);
            positionPart.process(gd, asteroid);
            updateShape(asteroid);
            movingPart.setLeft(false);
            movingPart.setUp(false);
        }
    }

    private void updateShape(Entity asteroid) {
        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float[] shapex = new float[numPoints];
        float[] shapey = new float[numPoints];

        Asteroid asAsteroid = (Asteroid) asteroid;
        if (asAsteroid.getSize().equals("LARGE")) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 26;
                shapey[i] = y + (float) Math.sin(angle + radians) * 26;
                angle += 2 * Math.PI / numPoints;
            }
        }

        if (asAsteroid.getSize().equals("MEDIUM")) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 16;
                shapey[i] = y + (float) Math.sin(angle + radians) * 16;
                angle += 2 * Math.PI / numPoints;
            }
        }

        if (asAsteroid.getSize().equals("SMALL")) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 6;
                shapey[i] = y + (float) Math.sin(angle + radians) * 6;
                angle += 2 * Math.PI / numPoints;
            }
        }
        asteroid.setShapeX(shapex);
        asteroid.setShapeY(shapey);
    }

}
