import java.io.File;
import java.net.URL;

public class Siren {
    public Siren (String soundFileName){
        try {
            dir = new File(soundFileName).toURI().toURL();
        }
        catch (Exception exc){
            exc.printStackTrace(System.out);
        }
        isSounding = false;
    }

    public void play(){
        if(!isSounding) {
            /* Se comenta a que empiece la alarma ya que me produce una excepción :(
            aWave = new AePlayWave(dir);
            aWave.start();
            */
            isSounding = true;
        }
    }

    public void stop(){
        if(isSounding) {
            /* Lo mismo acá, ya no está sonando realmente no se llama a la función.
            aWave.stopSounding();
            */
            isSounding = false;
        }
    }

    public String getHeader() {
        return "Siren";
    }

    public int getState() {
        return isSounding ? 1 : 0;
    }

    private URL dir;
    private boolean isSounding;
    private AePlayWave aWave;
}
