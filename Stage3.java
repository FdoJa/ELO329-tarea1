import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Stage3 {
    public Stage3() {
        doors = new ArrayList<Door>();
        windows = new ArrayList<Window>();
        PIRs = new ArrayList<PIR_detector>();
        people = new ArrayList<Person>();
    }

    public void readConfiguration(Scanner in){
        // reading <#_doors> <#_windows> <#_PIRs>
        central = new Central();

        int numDoors, numWindows, numPIRs;
        numDoors = in.nextInt();
        numWindows = in.nextInt();
        numPIRs = in.nextInt();

        // Parameters to read the next lines
        float x,y;
        int paramsPIR = 5, direction_angle, sensing_angle, sensing_range;

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

        // Reading <x> <y> <direction_angle> <sensing_angle> <sensing_range>
        for (int i = 0; i < numPIRs; i++){
            in.nextLine();
            x = in.nextFloat();
            y = in.nextFloat();
            direction_angle = in.nextInt();
            sensing_angle = in.nextInt();
            sensing_range = in.nextInt();
            PIRs.add(new PIR_detector(x,y,direction_angle,sensing_angle,sensing_range));
            central.addNewPIR(PIRs.get(i));
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
        int step=0, doorNumber, windowNumber, personNumber;
        float x,y;
        String arrow;
        boolean done = false;
        printHeader(out);
        System.out.println("--------------- Bienvenido ---------------");
        while (!done) {
            printState(step++, out);
            System.out.println("\nIngresa el comando a realizar:\n  (k) Armar zona(s)\n  (d) Abrir/cerrar puertas\n  (w) Abrir/cerrar ventanas\n  (c) Crear persona\n  (p) Mover persona\n  (x) Salir");
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
                case 'c':
                    System.out.println("Ingresa la posición \"x\" donde se ubicará la persona:");
                    x = in.nextFloat();
                    System.out.println("Ingresa la posición \"y\" donde se ubicará la persona:");
                    y = in.nextFloat();
                    people.add(new Person(x,y));
                    System.out.println("  - Persona añadida con exito");
                    break;
                case 'p':
                    System.out.println("¿Que persona se va a mover? Existen: " + people.size());
                    personNumber = in.nextInt();
                    System.out.println("¿hacia que dirección se moverá? (← | ↑ | ↓ | →)");
                    arrow = in.nextLine();
                    switch (arrow) {
                        case "↑":
                            people.get(personNumber).moveY(0.5F);
                            break;
                        case "↓":
                            people.get(personNumber).moveY(-0.5F);
                            break;
                        case "→":
                            people.get(personNumber).moveX(0.5F);
                            break;
                        case "←":
                            people.get(personNumber).moveX(-0.5F);
                            break;
                        default:
                            System.out.println("Dirección invalida");
                            break;
                    }
                    break;
                case 'x': done=true;   // Added to finish the program
            }
            central.checkZone(people);
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
        for (int i = 0; i < PIRs.size(); i++){
            out.print(String.format("\t%-4s", PIRs.get(i).getHeader()));
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
        for (int i = 0; i < PIRs.size(); i++){
            out.print(String.format("\t%-4d", PIRs.get(i).parseState()));
        }

        out.print(String.format("\t%-4s", siren.getState()));
        out.print(String.format("\t%-4s", central.getState()));

        out.println();
    }

    public static void main(String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Stage3 <config.txt>");
            System.exit(-1);
        }
        Scanner in = new Scanner(new File(args[0]));
        //System.out.println("File: " + args[0]);
        Stage3 stage = new Stage3();
        stage.readConfiguration(in);
        stage.executeUserInteraction(new Scanner(System.in), new PrintStream(new File("output.csv")));
    }

    private ArrayList<Door> doors;
    private ArrayList<Window> windows;
    private ArrayList<PIR_detector> PIRs;
    private ArrayList<Person> people;
    private Central central;
    private Siren siren;
}
