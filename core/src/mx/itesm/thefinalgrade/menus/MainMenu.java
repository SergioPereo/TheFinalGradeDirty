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
import mx.itesm.thefinalgrade.levels.MorningLevel;

public class MainMenu extends Menu {

    public MainMenu(TheFinalGrade game, String backgroundPath) {
        super(game, backgroundPath);

    }
    //Botones
    private Texture textContinueButton; private Texture textContinueButton2;
    private Texture textNewGameButton; private Texture textNewGameButton2;
    private Texture textOptionsButton; private Texture textOptionsButton2;
    private Texture textCreditsButton; private Texture textCreditsButton2;
    private Texture textExit; private Texture textExit2;
    private Texture textcalendar;
    private Texture textsheet;
    private Texture textniño;
    private float paddingPlayRight = 20f;


    @Override
    protected void createMenu() {
        //la escena se conecta con la vista
        menuStage = new Stage(vista);
        levelStage = new Stage(vista);


        //boton continuar
        textContinueButton = new Texture("button_jugar.png");
        TextureRegionDrawable trdcontinue = new TextureRegionDrawable(
                new TextureRegion(textContinueButton));

        //imagen btn continuar presionado
        textContinueButton2 = new Texture("button_jugarP.png");
        TextureRegionDrawable trdcontinue2 = new TextureRegionDrawable(
                new TextureRegion(textContinueButton2));
        ImageButton continuebtn = new ImageButton(trdcontinue, trdcontinue2);
        continuebtn.setPosition(ANCHO/2-continuebtn.getWidth()/2,2*ALTO/3);

        //boton NUEVO JUEGO
        textNewGameButton = new Texture("Sprites/button_nuevo-juego.png");
        TextureRegionDrawable trdnewG = new TextureRegionDrawable(
                new TextureRegion(textNewGameButton));

        //imagen btn NUEVO JUEGO presionado
        textNewGameButton2 = new Texture("Sprites/button_nuevo-juego 2.png");
        TextureRegionDrawable trdNewG2 = new TextureRegionDrawable(
                new TextureRegion(textNewGameButton2));
        ImageButton newGameBtn = new ImageButton(trdnewG, trdNewG2);
        newGameBtn.setPosition(ANCHO/2-newGameBtn.getWidth()/2,2*ALTO/3);

        //boton OPCIONES
        textOptionsButton = new Texture("Sprites/button_opciones.png");
        TextureRegionDrawable trdoption = new TextureRegionDrawable(
                new TextureRegion(textOptionsButton));

        //imagen btn OPCIONES presionado
        textOptionsButton2 = new Texture("Sprites/button_opciones2.png");
        TextureRegionDrawable trdoption2 = new TextureRegionDrawable(
                new TextureRegion(textOptionsButton2));

        ImageButton optionBtn = new ImageButton(trdoption, trdoption2);
        optionBtn.setPosition(ANCHO/2-optionBtn.getWidth()/2,2*ALTO/3);

        //boton CREDITOS
        textCreditsButton = new Texture("Sprites/button_creditos.png");
        TextureRegionDrawable trdCredits = new TextureRegionDrawable(
                new TextureRegion(textCreditsButton));
        //imagen btn CREDITOS presionado
        textCreditsButton2 = new Texture("Sprites/button_creditos2.png");
        TextureRegionDrawable trdCredits2 = new TextureRegionDrawable(
                new TextureRegion(textCreditsButton2));
        ImageButton creditsBtn = new ImageButton(trdCredits, trdCredits2);
        continuebtn.setPosition(ANCHO/2-creditsBtn.getWidth()/2,2*ALTO/3);

        //boton SALIR
        textExit = new Texture("Sprites/button_salir.png");
        TextureRegionDrawable trdExit = new TextureRegionDrawable(
                new TextureRegion(textExit));

        //imagen btn SALIR presionado
        textExit2 = new Texture("Sprites/button_salir2.png");
        TextureRegionDrawable trdExit2 = new TextureRegionDrawable(
                new TextureRegion(textExit2));
        ImageButton exitBtn = new ImageButton(trdcontinue, trdcontinue2);
        continuebtn.setPosition(ANCHO/2-exitBtn.getWidth()/2,2*ALTO/3);


        //imagen Niño
        textniño = new Texture("Sprites/niño.png");
        //imagen calendario
        textcalendar = new Texture("Sprites/Calendario.png");

        // Add actors
        levelStage.addActor(newGameBtn);

        // Add listeners
        newGameBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                game.setScreen(new MorningLevel(game, "Sprites/Fondo.png"));
            }
        });

        // Add input processor
        Gdx.input.setInputProcessor(levelStage);


    }
    @Override
    public void render(float delta){
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(background,0,0);
        batch.draw(textniño,550,0);

        batch.draw(textContinueButton,550,600);
        batch.draw(textNewGameButton,550,500);
        batch.draw(textOptionsButton,550,400);
        batch.draw(textCreditsButton,550,300);
        batch.draw(textExit,550,200);
        batch.end();


    }
    @Override
    public void dispose() {
        background.dispose();
        textContinueButton.dispose();
        textContinueButton2.dispose();
        textNewGameButton.dispose();
        textNewGameButton2.dispose();
        textOptionsButton.dispose();
        textOptionsButton2.dispose();
        textCreditsButton.dispose();
        textCreditsButton2.dispose();
        textExit.dispose();
        textExit2.dispose();
        textcalendar.dispose();
    }
}
