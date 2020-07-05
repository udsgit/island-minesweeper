package J3Emmanuel;

import java.util.Scanner;

public class BuscaMinas {

    //Atributos
    private int menu;
    private int filas;
    private int columnas;
    private int numMinas;
    private char letra;
    private boolean salir;
    private boolean trucos;
    private final Scanner sc;

    //Constructor
    public BuscaMinas() {
        sc = new Scanner(System.in);
        salir = false;
        trucos = false;
    }

    //metodos
    public void jugarModoConsola() {
        System.out.println("💣 BuscaMinas 2.0 💣");
        System.out.println();
        do {
            imprimirMenu();
            menu = sc.nextInt();
            System.out.println();
            switch (menu) {
                case 1:
                    trucos = true;
                case 2:
                    do {
                        System.out.println("INTRODUZCA EL TAMAÑO DEL TABLERO Y SUS MINAS EJ: 2 2 4 = 2X2 CON 2 MINAS");
                        System.out.println();
                        filas = sc.nextInt();
                        columnas = sc.nextInt();
                        numMinas = sc.nextInt();
                        System.out.println();
                    } while (numMinas > (filas * columnas) - 1);
                    Tablero unTablero = new Tablero(filas, columnas, numMinas);
                    System.out.println();
                    do {
                        if (trucos) {
                            System.out.println(unTablero.getPosBombas());
                            System.out.println();
                        }
                        System.out.println(unTablero.stringTablero());
                        System.out.println();
                        System.out.println("INTRODUZCA COORDS, EJ: '2 3' = [2,3]");
                        System.out.println();
                        filas = sc.nextInt();
                        columnas = sc.nextInt();
                        sc.nextLine();
                        System.out.println();
                        System.out.println("[A = ABRIR CELDA] [M = ⮾] [D = Ⓓ] [S = ●]");
                        System.out.println();
                        letra = sc.nextLine().charAt(0);
                        System.out.println();
                        if (letra == 'A' || letra == 'a') {
                            unTablero.comprobarCelda(filas, columnas, letra);
                        } else {
                            unTablero.marcarCasilla(filas, columnas, letra);
                        }

                    } while (unTablero.getSeguir());
                    trucos = false;
                    if (unTablero.isWin()) {

                        System.out.println("HAZ GANADO, PUNTUACION: " + unTablero.getPuntuacion() + "/" + unTablero.getMaxPuntuacion());
                    } else {
                        System.out.println("GAME OVER, PUNTUACION : " + unTablero.getPuntuacion() + "/" + unTablero.getMaxPuntuacion());
                    }
                    System.out.println();
                    System.out.println(unTablero.stringTablero());
                    System.out.println();
                    break;
                case 3:
                    System.out.println("A = ABRIR CELDA");
                    System.out.println("M = MINA / ⮾ ");
                    System.out.println("D = DUDA / Ⓓ ");
                    System.out.println("S = SIN MARCAR / ● ");
                    System.out.println();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    break;
            }
        } while (salir != true);
    }

    public final void imprimirMenu() {
        System.out.println("1. Nueva Partida con CHETOS");
        System.out.println("2. Nueva Partida");
        System.out.println("3. Ayuda");
        System.out.println("4. Salir");
        System.out.println();
    }

    public int getNumMinas() {
        return numMinas;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
}
