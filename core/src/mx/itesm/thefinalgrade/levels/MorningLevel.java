package mx.itesm.thefinalgrade.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.menus.MainMenu;
import mx.itesm.thefinalgrade.util.objects.Bonus;
import mx.itesm.thefinalgrade.util.objects.Obstacle;
import mx.itesm.thefinalgrade.util.objects.Platform;
import mx.itesm.thefinalgrade.util.objects.Player;

public class MorningLevel extends Level {


    public MorningLevel(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);
        this.backgroundPath = backgroundPath;
    }


    private void createObstacles(){
        obstacles = new Array<>();
        obstacles.add(new Obstacle(obstacleTexture, ANCHO/3, ALTO/3));

    }

    private void createBonus(){
        bonus = new Array<>();
        bonus.add(new Bonus(hojaBuenaTexture, ANCHO/2+300, ALTO-500));
        bonus.add(new Bonus(hojaBuenaTexture,200, 80));
    }


    public void show() {
        super.show();
        createLevel();
        createHUD();
        Gdx.input.setInputProcessor(escenaHUD);

    }
    @Override
    protected void createLevel() {
        levelStage = new Stage(vista);
        super.loadTextures();
        super.createPlayer();
        createObstacles();
        createPlatforms();
        createBonus();
        createHUD();
        createButton();
        Gdx.input.setInputProcessor(levelStage);
    }
    protected void createPlatforms() {
        // Must be the same lengths unless you want the game to crash or do strange platforms
        float[] platformsXCoordinates = {0, ANCHO/3-100, ANCHO/2, ANCHO/2+300, ANCHO-platformTexture.getWidth()};
        float[] platformsYCoordinates = {0, ALTO/3-100, ALTO-400, ALTO-600, ALTO-platformTexture.getHeight()*2+50};
        platforms = new Array<>(5);

        for (int i = 0; i < platformsXCoordinates.length ; i++){
            platforms.add(new Platform(platformTexture, platformsXCoordinates[i], platformsYCoordinates[i]));
        }

    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        updates(delta);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background,0,0);
        player.render(batch);

        for(Platform platform: platforms){
            platform.render(batch);
        }

        for(Obstacle obstacle: obstacles){
            obstacle.render(batch);
        }
        for (Bonus bonus : bonus){
            bonus.render(batch);
        }
        batch.end();
        //HUD
        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();
        levelStage.draw();
    }
    @Override
    public void dispose() {
        background.dispose();
    }

}


