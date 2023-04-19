import java.util.ArrayList;
import java.util.Arrays;

public class Central {
    public Central(){
        zone0 = new ArrayList<Sensor>();
        zone1 = new ArrayList<Sensor>();
        activeZones = new boolean[3];

        isArmed = false;
        siren = null;
    }

    public void armAll(){
        Arrays.fill(activeZones, true);
        isArmed = true;
    }

    public void armPerimeter(){
        activeZones[0] = true;
        activeZones[1] = true;
        activeZones[2] = false;

        isArmed = true;
    }

    public void disarm() {
        Arrays.fill(activeZones, false);
        isArmed = false;

        if (siren != null && siren.getState() == 1){
            siren.stop();
        }
    }

    public void setSiren(Siren s) {
        siren = s;
    }

    public void addNewSensorToZone(Sensor s, int zoneIndex){
        switch (zoneIndex){
            case 0:
                zone0.add(s);
                break;
            case 1:
                zone1.add(s);
                break;
            default:
                System.out.println("Zona invalida.");
        }
    }

    public void checkZone(){
        if (!isArmed){
            return;
        }
        System.out.println("Revisando zona(s)");
        boolean triggerAlarm = false;

        System.out.println("¿Zonas activas?" + activeZones[0] + activeZones[1]);
        if (activeZones[0]) {
            for (Sensor s : zone0){
                if (s.isTriggered()){
                    triggerAlarm = true;
                    break;
                }
            }
        }

        if (activeZones[1]){
            for (Sensor s : zone1){
                if (s.isTriggered()){
                    triggerAlarm = true;
                    break;
                }
            }
        }

        System.out.println("¿Alarma?" + triggerAlarm);
        if (triggerAlarm){
            System.out.println("Enecendiendo alarma...");
            siren.play();
        }
    }
    public String getHeader(){
        return "Central";
    }
    public int getState(){
        return isArmed?1:0;
    }

    private ArrayList<Sensor> zone0, zone1;
    private boolean[] activeZones;
    private boolean isArmed;
    private Siren siren;
}
