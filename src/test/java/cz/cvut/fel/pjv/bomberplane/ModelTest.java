package cz.cvut.fel.pjv.bomberplane;

import static org.junit.jupiter.api.Assertions.*;

import cz.cvut.fel.pjv.bomberplane.gameobjects.Killer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;

/**
 *
 */
class ModelTest {
    Image imgTest = null;
    Controller controller = null;

    @Test
    public void deathshouldSetPlaneNumberOfConcurrentBombsToZero() {
        Model gameModel = new Model(controller);
        gameModel.death();
        assertEquals(1, gameModel.getPlane().getNumOfConcurrentBombsToDrop());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 10, 20, 100})
    public void listOfBombsNotBiggerThanNumOfConcurrentBombs(int numBombs) {
        Model gameModel = new Model(controller);
        gameModel.getPlane().setNumOfConcurrentBombsToDrop(numBombs);
        for (int i = 0; i < numBombs * 2; i++) {
            gameModel.getPlane().shoot();
        }
        assertTrue(gameModel.getPlane().getBombs().size() <= numBombs);
    }

    @Test
    public void numOfAtomicBombsAfterDeathIsZero() {
        Model gameModel = new Model(controller);
        gameModel.getPlane().setNumOfAtomicBombs(100);
        gameModel.getPlane().setDying(true);
        gameModel.playGame();
        assertEquals(0, gameModel.getPlane().getNumOfAtomicBombs());
    }

    @Test
    public void killerKillsPlane() {
        Model gameModel = new Model(controller);
        Killer killer = new Killer(imgTest, gameModel.getPlane().getPositionX(), gameModel.getPlane().getPositionY(), -1);
        killer.setDefaultSpeed(5);
        killer.setPositionY(killer.getPositionY() + killer.getDefaultSpeed());
        gameModel.getKillers().add(killer);
        gameModel.playGame();
        assertTrue(gameModel.getPlane().isDying());
    }

    @Test
    public void planeCanNotShootAtomicIfNumofAtomicBombsIsZero() {
        Model gameModel = new Model(controller);
        gameModel.getPlane().setNumOfAtomicBombs(0);
        gameModel.planeShootAtomic();
        gameModel.playGame();
        assertEquals(0, gameModel.getPlane().getBombs().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 10, 20, 100})
    public void ifnumOfAtomicBombsIsGreaterThanZeroPlaneShootsAtomic() {
        Model gameModel = new Model(controller);
        gameModel.getPlane().setNumOfAtomicBombs(0);
        gameModel.planeShootAtomic();
        gameModel.playGame();
        assertEquals(0, gameModel.getPlane().getBombs().size());
    }
}