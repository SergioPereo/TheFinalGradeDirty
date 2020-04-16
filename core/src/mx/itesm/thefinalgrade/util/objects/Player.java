package mx.itesm.thefinalgrade.util.objects;

import com.badlogic.gdx.graphics.Texture;

public class Player extends Element{

    private float yAcceleration = 0f;
    private MoveState state = MoveState.WALK;

    public Player(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    public void move(float dx, float dy){
        sprite.setPosition(sprite.getX()+dx, sprite.getY()+dy);
    }

    public void gravity(float force){
        yAcceleration += force;
        System.out.println("Y Acceleration: " + yAcceleration);
        sprite.setY(sprite.getY()+yAcceleration);
    }

    public void setYAcceleration(float acceleration){
        yAcceleration = acceleration;
    }

    public float getyAcceleration(){
        return yAcceleration;
    }
    //No está siendo utilizado
    public void setState(MoveState walking){
        this.state = walking;
    }

    public enum MoveState
    {
        STOP,
        WALK
    }
}
