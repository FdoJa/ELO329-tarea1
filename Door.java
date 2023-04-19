public class Door {
    public Door () {
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

    public MagneticSensor getMagneticSensor() {
        return this.magneticSensor;
    }

    public String getHeader(){
        return "d"+id;
    }
    public int getState(){
        return state.ordinal();
    }

    private MagneticSensor magneticSensor;
    private State state;
    private final int id;
    private static int nextId;
    static {
        nextId = 0;
    }
}
