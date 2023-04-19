public class Sensor {
    public Sensor(){
        this(SwitchState.OPEN);
    }
    public Sensor(SwitchState s){
        this.setState(s);
    }
    public SwitchState getState(){
        return this.state;
    }
    protected void setState(SwitchState s) {
        this.state = s;
    }
    public String toString(){
        if (getState() == SwitchState.CLOSE)
            return "1";
        else
            return "0";
    }
    private SwitchState state;
}
