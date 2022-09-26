import java.util.concurrent.ThreadLocalRandom;

public class Snake extends Obstacle{

    public Snake() {

        super("Yılan",0 ,12,0,5);
        int randDamage = ThreadLocalRandom.current().nextInt(3,6 + 1);
        setDamage(randDamage);


    }
}
