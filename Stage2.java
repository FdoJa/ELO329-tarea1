import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Stage2 {
    public Stage2() {
        doors = new ArrayList<Door>();
        windows = new ArrayList<Window>();
    }

    public void readConfiguration(Scanner in){
        // reading <#_doors> <#_windows> <#_PIRs>
        central = new Central();

        int numDoors, numWindows;
        numDoors = in.nextInt();
        numWindows = in.nextInt();

        for (int i = 0; i < numDoors; i++) {
            doors.add(new Door());
            if (i == 0){
                central.addNewSensorToZone(doors.get(i).getMagneticSensor(),0);
            }
            central.addNewSensorToZone(doors.get(i).getMagneticSensor(),1);
        }

        for (int i = 0; i < numWindows; i++) {
            windows.add(new Window());
            central.addNewSensorToZone(windows.get(i).getMagneticSensor(), 1);
        }

        // Add and set alarm
        in.nextLine();
        String soundFile = in.next();
        siren = new Siren(soundFile);
        central.setSiren(siren);

        in.close();
    }

    public void executeUserInteraction (Scanner in, PrintStream out){
        char command, parameter;
        int step=0, doorNumber, windowNumber;
        boolean done = false;
        printHeader(out);
        System.out.println("--------------- Bienvenido ---------------");
        while (!done) {
            printState(step++, out);
            System.out.println("\nIngresa el comando a realizar:\n  (k) Armar zona(s)\n  (d) Abrir/cerrar puertas\n  (w) Abrir/cerrar ventanas\n  (x) Salir");
            command = in.next().charAt(0);
            switch (command) {
                case 'd':
                    System.out.println("¿Que puerta quieres modificar? Existen: " + doors.size());
                    doorNumber = in.nextInt();
                    System.out.println("¿Quieres abrir o cerrar la puerta? (o = abrir, c = cerrar)");
                    parameter = in.next().charAt(0);
                    if (parameter == 'o') {
                        doors.get(doorNumber).open();
                        System.out.println("  - La puerta ha sido abierta");
                    } else {
                        doors.get(doorNumber).close();
                        System.out.println("  - La puerta ha sido cerrada");
                    }
                    break;
                case 'w':
                    System.out.println("¿Que ventana quieres modificar? Existen: " + windows.size());
                    windowNumber = in.nextInt();
                    System.out.println("¿Quieres abrir o cerrar la ventana? (o = abrir, c = cerrar)");
                    parameter = in.next().charAt(0);
                    if (parameter == 'o') {
                        windows.get(windowNumber).open();
                        System.out.println("  - La ventana ha sido abierta");
                    } else {
                        windows.get(windowNumber).close();
                        System.out.println("  - La ventana ha sido cerrada");
                    }
                    break;
                case 'k':
                    System.out.println("¿Que zona quieres armar?\n  (a) Armar todo\n  (p) Armar perimetro\n  (d) Desarmar todo");
                    parameter = in.next().charAt(0);
                    switch (parameter){
                        case 'a':
                            central.armAll();
                            System.out.println("  - Se han armado todas las zonas.");
                            break;
                        case 'p':
                            central.armPerimeter();
                            System.out.println("  - Se han armado las zonas perimetrales");
                            break;
                        case 'd':
                            central.disarm();
                            System.out.println("  - Se han desarmado las zonas");
                            break;
                        default:
                            System.out.println("Ingrese un parametro valido");
                            break;
                    }
                    break;
                case 'x': done=true;   // Added to finish the program
            }
            central.checkZone();
        }
        System.out.println("Cerrando el sistema...");
    }

    public void printHeader(PrintStream out) {
        out.print(String.format("%-4s", "Step"));
        for (int i = 0; i < doors.size(); i++) {
            out.print(String.format("\t%-4s", doors.get(i).getHeader()));
        }
        for (int i = 0; i < windows.size(); i++) {
            out.print(String.format("\t%-4s", windows.get(i).getHeader()));
        }
        out.print(String.format("\t%-4s", siren.getHeader()));
        out.print(String.format("\t%-4s", central.getHeader()));

        out.println();
    }

    public void printState(int step, PrintStream out) {
        out.print(String.format("%-4d", step));
        for (int i = 0; i < doors.size(); i++) {
            out.print(String.format("\t%-4d", doors.get(i).getState()));
        }
        for (int i = 0; i < windows.size(); i++) {
            out.print(String.format("\t%-4d", windows.get(i).getState()));
        }
        out.print(String.format("\t%-4s", siren.getState()));
        out.print(String.format("\t%-4s", central.getState()));

        out.println();
    }

    public static void main(String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Stage2 <config.txt>");
            System.exit(-1);
        }
        Scanner in = new Scanner(new File(args[0]));
        //System.out.println("File: " + args[0]);
        Stage2 stage = new Stage2();
        stage.readConfiguration(in);
        stage.executeUserInteraction(new Scanner(System.in), new PrintStream(new File("output.csv")));
    }

    private ArrayList<Door> doors;
    private ArrayList<Window> windows;
    private Central central;
    private Siren siren;
}
