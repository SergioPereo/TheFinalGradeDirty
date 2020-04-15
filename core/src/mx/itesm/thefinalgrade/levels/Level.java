package mx.itesm.thefinalgrade.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.event.ChangeEvent;

import mx.itesm.thefinalgrade.TheFinalGrade;
import mx.itesm.thefinalgrade.util.Pantalla;
import mx.itesm.thefinalgrade.util.objects.Bonus;
import mx.itesm.thefinalgrade.util.objects.Obstacle;
import mx.itesm.thefinalgrade.util.objects.Platform;
import mx.itesm.thefinalgrade.util.objects.Player;

public abstract class Level extends Pantalla {

    protected TheFinalGrade game;
    protected String backgroundPath;
    protected Stage levelStage;
    //Personaje
    protected Player player;
    //Elementos
    protected Array<Obstacle> obstacles;
    protected Array<Platform> platforms;
    protected Array<Bonus> bonus;
    private float playerPaddingLeft = 20, playerPaddingBottom = 20;
    //Texturas
    protected Texture background;
    Texture playerTexture;
    Texture platformTexture;
    Texture obstacleTexture;
    Texture hojaBuenaTexture;

    // HUD (joystick virtual)
    protected Stage escenaHUD;    // controles
    protected OrthographicCamera camaraHUD;
    protected Viewport vistaHUD;

    private float movementMultiplicator = 60f;
    private Movement playerMovement = Movement.IDLE;
    private boolean isGrounded = true;

    public Level(TheFinalGrade game, String backgroundPath) {
        this.game = game;
        this.backgroundPath = backgroundPath;
    }
    @Override
    public void show() {
        if (backgroundPath.length() > 0 && backgroundPath != null) {
            background = new Texture(this.backgroundPath);
            createLevel();

        }
        createHUD();
        //Gdx.input.setInputProcessor(new CustomInputProcessor());//Para el Touchdown
        Gdx.input.setInputProcessor(escenaHUD);
    }

    public void createHUD() {
        camaraHUD = new OrthographicCamera(ANCHO, ALTO);
        camaraHUD.position.set(ANCHO/2, ALTO/2, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO, ALTO, camaraHUD);

        Skin skin = new Skin();
        skin.add("fondo", new Texture("padBack.png"));
        skin.add("boton", new Texture("padKnob.png"));
        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("fondo");
        estilo.knob = skin.getDrawable("boton");
        // Crear el pad
        Touchpad pad = new Touchpad(64, estilo);
        pad.setBounds(16,16,256,256); //limites del pad
        pad.setColor(1,1,1,0.7f);
        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                if(pad.getKnobPercentX()>0){
                    playerMovement = Movement.RIGHT;
                }
                else if(pad.getKnobPercentX()<0){
                    playerMovement = Movement.LEFT;
                }
                else if(pad.getKnobPercentY()>200){
                    playerMovement = Movement.JUMP;
                }
                else{
                    playerMovement = Movement.IDLE;
                }
            }
        });

        // EVENTOS
        escenaHUD = new Stage(vistaHUD);
        escenaHUD.addActor(pad);
    }

    protected void loadTextures(){
        platformTexture = new Texture("Plataforma 1.png");
        obstacleTexture = new Texture("HojaRota.png");
        playerTexture = new Texture("Niño/Niño.png");
        hojaBuenaTexture = new Texture("Hoja.png");


    }

    protected void createPlayer(){
        player = new Player(playerTexture, playerTexture.getWidth() +
                playerPaddingLeft, playerTexture.getHeight() + playerPaddingBottom);
    }

    protected abstract void createPlatforms();

    protected abstract void createLevel();

    @Override
    public void render(float delta) {

    }

    protected boolean collisions() {
        Rectangle playerRect = new Rectangle(player.getTextureX(), player.getTextureY(),
                player.getTextureWidth(), player.getTextureHeight());
        boolean colliding = false;

        if (playerRect.getY() < 0){
            game.setScreen(new Loser(game, "Failed_Niño.png"));
        }

        for (Platform platform : platforms) {
            Rectangle platformRect = new Rectangle(platform.getTextureX(), platform.getTextureY(),
                    platform.getTextureWidth(), platform.getTextureHeight()-48);
            if (playerRect.overlaps(platformRect)) {
                System.out.println("Colliiiiiiiiiiiiidiiiiiiiiiiiiing");
                colliding = true;
                break;
            }
        }
        return colliding;
    }

    public void updates(float delta) {
        movePlayer(delta);
        if (!collisions()) {
            player.gravity(-movementMultiplicator * delta);
            isGrounded = false;
        } else {
            player.gravity(-player.getyAcceleration());
            isGrounded = true;
        }

    }
    //Movimiento
    public enum Movement{
        //Constantes
        RIGHT,
        LEFT,
        JUMP,
        IDLE
    }

    protected void movePlayer(float delta) {
        switch (playerMovement) {
            case IDLE:
                break;
            case RIGHT:
                player.move(movementMultiplicator * delta, 0);
                System.out.println("MOVING RIGHT: " + movementMultiplicator * delta);
                break;
            case LEFT:
                player.move(-movementMultiplicator * delta, 0);
                System.out.println("MOVING LEFT: " + -movementMultiplicator * delta);
                break;
            case JUMP:
                player.move(movementMultiplicator*delta, 45);
                break;
            default:
                break;
        }
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
        levelStage.dispose();
    }
    protected class CustomInputProcessor implements InputProcessor {
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
            if (v.y >= ALTO / 2) {
                playerMovement = Movement.JUMP;

                player.setYAcceleration(10f);
                System.out.println("JUMP");
            } /*else {
                if (v.x >= ANCHO / 2) {
                    playerMovement = Movement.RIGHT;
                    System.out.println("RIGHT");
                } else {
                    playerMovement = Movement.LEFT;
                    System.out.println("LEFT");
                }
            }*/

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
