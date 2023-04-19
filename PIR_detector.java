public class PIR_detector extends Sensor {
    public PIR_detector(float x, float y, int direction_angle, int sensing_angle, int sensing_range) {
        this.x = x;
        this.y = y;
        this.direction_angle = direction_angle;
        this.sensing_angle = sensing_angle;
        this.sensing_range = sensing_range;
        notInAreaOfDetection();
    }
    {
        id = nextId++;
    }

    public void inAreaOfDetection(){
        setState(SwitchState.OPEN);
        state = State.OPEN;
    }

    public void notInAreaOfDetection(){
        setState(SwitchState.CLOSE);
        state = State.CLOSE;
    }

    public void detect(float x, float y) {
        double dx = x - this.x;
        double dy = y - this.y;
        double distance = Math.sqrt(dx*dx + dy*dy);
        double angle = Math.atan2(dy, dx);
        double angle_diff = Math.abs(angle - direction_angle);
        if (angle_diff > Math.PI) {
            angle_diff = 2*Math.PI - angle_diff;
        }

        if (angle_diff <= sensing_angle/2. && distance <= sensing_range)
            inAreaOfDetection();
        else
            notInAreaOfDetection();

    }

    public String getHeader(){
        return "Pir"+id;
    }

    public int parseState(){
        if (getState() == SwitchState.CLOSE)
            return 0;
        else
            return 1;
    }


    private float x, y;
    private int direction_angle, sensing_angle, sensing_range;
    private State state;
    private final int id;
    private static int nextId=0;
}
