package mx.itesm.thefinalgrade.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.objects.Obstacle;
import mx.itesm.thefinalgrade.util.objects.Platform;
import mx.itesm.thefinalgrade.util.objects.Player;

public class MorningLevel extends Level {

    // Player padding
    private float playerPaddingLeft = 20, playerPaddingBottom = 20;

    Texture playerTexture;
    Texture platformTexture;
    Texture obstacleTexture;

    public MorningLevel(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);
        this.backgroundPath = backgroundPath;
    }

    private void loadTextures(){

        playerTexture = new Texture("Sprites/niñoJuego.png");
        platformTexture = new Texture("Sprites/platform.png");
        obstacleTexture = new Texture("Sprites/Hoja.png");

    }

    private void createPlayer(){
        player = new Player(playerTexture, playerTexture.getWidth() +
                playerPaddingLeft, playerTexture.getHeight() + playerPaddingBottom);
    }

    private void createPlatforms(){

        float[] arrCoordenadasX = {0, ANCHO/3-100, ANCHO/2, ANCHO/2+300, ANCHO-platformTexture.getWidth()};
        float[] arrCoordenadasY = {0, ALTO/3-100, ALTO/2, ALTO-400, ALTO-platformTexture.getHeight()*2};

        platforms = new Array<>(5);

        for (int i = 0; i < arrCoordenadasX.length ; i++){
            platforms.add(new Platform(platformTexture, arrCoordenadasX[i], arrCoordenadasY[i]));
        }
    }

    private void createObstacles(){
        obstacles = new Array<>(1);
        obstacles.add(new Obstacle(obstacleTexture, ANCHO/3, ALTO/3));
    }

    @Override
    public void show() {
        if(backgroundPath.length() > 0 && backgroundPath != null){
            background = new Texture(this.backgroundPath);
            loadTextures();
            createPlayer();
            createLevel();
        }
    }

    @Override
    protected void createLevel() {

        levelStage = new Stage(vista);
        createPlayer();
        createObstacles();
        createPlatforms();

    }

    @Override
    public void render(float delta) {
        borrarPantalla();
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
        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
