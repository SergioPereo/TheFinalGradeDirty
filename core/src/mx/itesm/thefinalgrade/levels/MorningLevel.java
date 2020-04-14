package mx.itesm.thefinalgrade.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
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

    private float movementMultiplicator = 60f;

    private Movement playerMovement = Movement.IDLE;
    private boolean isGrounded = true;

    public enum Movement{
        RIGHT, LEFT, JUMP, IDLE
    }

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
        // Must be the same lengths unless you want the game to crash or do strange platforms
        float[] platformsXCoordinates = {0, ANCHO/3-100, ANCHO/2, ANCHO/2+300, ANCHO-platformTexture.getWidth()};
        float[] platformsYCoordinates = {0, ALTO/3-100, ALTO/2, ALTO-400, ALTO-platformTexture.getHeight()*2};
        platforms = new Array<>(5);

        for (int i = 0; i < platformsXCoordinates.length ; i++){
            platforms.add(new Platform(platformTexture, platformsXCoordinates[i], platformsYCoordinates[i]));
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
            Gdx.input.setInputProcessor(new CustomInputProcessor());
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
        batch.end();
    }

    public void updates(float delta){
        movePlayer(delta);
        if(!collisions()){
            player.gravity(-movementMultiplicator*delta);
            isGrounded = false;
        } else {
            player.gravity(-player.getyAcceleration());
            isGrounded = true;
        }

    }

    private boolean collisions() {
        Rectangle playerRect = new Rectangle(player.getTextureX(), player.getTextureY(),
                player.getTextureWidth(), player.getTextureHeight());
        boolean colliding = false;
        for(Platform platform : platforms) {
            Rectangle platformRect = new Rectangle(platform.getTextureX(), platform.getTextureY(),
                    platform.getTextureWidth(), platform.getTextureHeight());
            if (playerRect.overlaps(platformRect)) {
                System.out.println("Colliiiiiiiiiiiiidiiiiiiiiiiiiing");
                colliding = true;
                break;
            }
        }
        return colliding;
    }

    private void movePlayer(float delta) {
        switch (playerMovement){
            case IDLE:

                break;
            case RIGHT:
                player.move(movementMultiplicator*delta, 0);
                System.out.println("MOVING RIGHT: " + movementMultiplicator*delta);
                break;
            case LEFT:
                player.move(-movementMultiplicator*delta, 0);
                System.out.println("MOVING LEFT: " + -movementMultiplicator*delta);
                break;
            case JUMP:
                break;
            default:
                break;
        }
    }

    @Override
    public void dispose() {
        background.dispose();
    }

    private class CustomInputProcessor implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 v = new Vector3(screenX, screenY, 0);
            camara.unproject(v);
            if(v.y >= ALTO/2){
                playerMovement = Movement.JUMP;

                player.setYAcceleration(10f);
                System.out.println("JUMP");
            } else {
                if(v.x>=ANCHO/2){
                    playerMovement = Movement.RIGHT;
                    System.out.println("RIGHT");
                } else {
                    playerMovement = Movement.LEFT;
                    System.out.println("LEFT");
                }
            }

            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            playerMovement = Movement.IDLE;
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}

