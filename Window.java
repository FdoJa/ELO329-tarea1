public class Window {
    public Window() {
        magneticSensor = new MagneticSensor();
        close();
    }
    {
        id = nextId++;
    }
    public void open() {
        magneticSensor.moveMagnetAwayFromSwitch();
        state = State.OPEN;
    }
    public void close() {
        magneticSensor.putMagnetNearSwitch();
        state = State.CLOSE;
    }
    public String getHeader(){
        return "w"+id;
    }
    public int getState(){
        return state.ordinal();
    }
    private MagneticSensor magneticSensor;
    private State state;
    private final int id;
    private static int nextId=0;
}
