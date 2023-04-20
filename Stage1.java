import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Stage1 {
    public Stage1() {
        doors = new ArrayList<Door>();
        windows = new ArrayList<Window>();
    }

    public void readConfiguration(Scanner in){
        // reading <#_doors> <#_windows> <#_PIRs>
        int numDoors, numWindows;
        numDoors = in.nextInt();
        numWindows = in.nextInt();

        for (int i = 0; i < numDoors; i++)
            doors.add(new Door());

        for (int i = 0; i < numWindows; i++)
            windows.add(new Window());

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
            System.out.println("\nIngresa el sistema de seguridad a cambiar:\n  (d) Puertas\n  (w) Ventanas\n  (x) Salir del sistema");
            command = in.next().charAt(0);
            switch (command) {
                case 'd':
                    System.out.println("¿Que puerta quieres abrir? Existen: " + doors.size());
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
                    System.out.println("¿Que ventana quieres abrir? Existen: " + windows.size());
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
                case 'x': done=true;   // Added to finish the program
            }
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
        out.println();
    }

    public static void main(String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Stage1 <config.txt>");
            System.exit(-1);
        }
        Scanner in = new Scanner(new File(args[0]));
        //System.out.println("File: " + args[0]);
        Stage1 stage = new Stage1();
        stage.readConfiguration(in);
        stage.executeUserInteraction(new Scanner(System.in), new PrintStream(new File("output.csv")));
    }

    private ArrayList<Door> doors;
    private ArrayList<Window> windows;
}
