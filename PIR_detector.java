public class PIR_detector extends Sensor {
    public PIR_detector(float x, float y, int direction_angle, int sensing_angle, int sensing_range) {
        this.x = x;
        this.y = y;
        this.direction_angle = Math.toRadians(direction_angle);
        this.sensing_angle = Math.toRadians(sensing_angle);
        this.sensing_range = sensing_range;
        notInAreaOfDetection();
    }
    {
        id = nextId++;
    }

    public void inAreaOfDetection(){
        setState(SwitchState.OPEN);
    }

    public void notInAreaOfDetection(){
        setState(SwitchState.CLOSE);
    }

    public void detect(float x, float y) {
        double dx = x - this.x;
        double dy = y - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > sensing_range) {
            notInAreaOfDetection();
            return;
        }

        if (x == this.x && y == this.y){
            System.out.println("Se ha activado un PIR");
            inAreaOfDetection();
            return;
        }

        double half_sensing_angle = sensing_angle / 2.0;
        double lower_angle = direction_angle - half_sensing_angle;
        double upper_angle = direction_angle + half_sensing_angle;

        double angle = Math.atan2(dy, dx);

        if (angle > lower_angle && angle < upper_angle){
            System.out.println("Se ha activado un PIR");
            inAreaOfDetection();
        } else {
            notInAreaOfDetection();
        }
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
    private double direction_angle, sensing_angle, sensing_range;
    private final int id;
    private static int nextId=0;
}
