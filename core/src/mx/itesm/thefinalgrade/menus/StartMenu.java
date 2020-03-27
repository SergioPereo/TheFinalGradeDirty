package mx.itesm.thefinalgrade.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.thefinalgrade.TheFinalGrade;

public class StartMenu extends Menu {

    // Declare padding's to avoid using constants in the methods
    private float paddingPlayRight = 20f;

    // Declare textures
    private Texture playTexture, playTextureP, textureNiño, textureCalendario;

    public StartMenu(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);
    }

    @Override
    protected void createMenu() {

        menuStage = new Stage(vista);

        // Play button
        playTexture = new Texture("Sprites/Start.png");
        TextureRegionDrawable playRegionDrawable = new TextureRegionDrawable(new TextureRegion(playTexture));

        playTextureP = new Texture("Sprites/StartClick.png");
        TextureRegionDrawable playRegionDrawableP = new TextureRegionDrawable(new TextureRegion(playTextureP));

        textureNiño = new Texture("Sprites/niño.png");
        textureCalendario = new Texture("Sprites/Calendario.png");

        ImageButton playButton = new ImageButton(playRegionDrawable, playRegionDrawableP);

        playButton.setPosition(ANCHO-playButton.getWidth()-paddingPlayRight, playButton.getHeight());

        // Add actors
        menuStage.addActor(playButton);

        // Add listeners
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new MainMenu(game, "Sprites/Fondo.png"));
            }
        });

        // Add input processor
        Gdx.input.setInputProcessor(menuStage);
    }
    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(textureNiño, 10, 0);
        batch.draw(textureCalendario, 375, 0);
        batch.end();


        menuStage.draw();
    }

    @Override
    public void dispose() {
        // Dispose resources to clean memory
        background.dispose();
        playTexture.dispose();
        playTextureP.dispose();
    }
}
