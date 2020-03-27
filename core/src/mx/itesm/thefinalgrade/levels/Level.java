package mx.itesm.thefinalgrade.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.Pantalla;
import mx.itesm.thefinalgrade.util.objects.Obstacle;
import mx.itesm.thefinalgrade.util.objects.Platform;
import mx.itesm.thefinalgrade.util.objects.Player;

public abstract class Level extends Pantalla {

    protected TheFinalGrade game;
    protected String backgroundPath;
    protected Texture background;
    protected Stage levelStage;
    protected Player player;
    protected Array<Obstacle> obstacles;
    protected Array<Platform> platforms;

    public Level(TheFinalGrade game, String backgroundPath){
        this.game = game;
        this.backgroundPath = backgroundPath;
    }

    @Override
    public void show() {
        if(backgroundPath.length() > 0 && backgroundPath != null){
            background = new Texture(this.backgroundPath);
            createLevel();
        }
    }

    protected abstract void createLevel();

    @Override
    public void render(float delta) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
