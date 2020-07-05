package J3Emmanuel;

import javafx.scene.image.Image;

public class Tablero {

    //atributos
    private final Celda[][] celdas;
    private final int numFilas;
    private final int numColumnas;
    private int randomFila;
    private int randomColumna;
    private int puntuacion;
    private int maxPuntuacion;
    private int contadorBusquedas;
    private int listadoProximasBusquedas;
    private int contadorFila;
    private int numMinas;
    private final int[][] posBombas;
    private int[][] busquedas;
    private boolean seguir;
    private boolean win;
    
  
    private Image imgMina = new Image("resources/volcan.png");
    private Image imgUno = new Image("resources/1.png");
    private Image imgDos = new Image("resources/2.png");
    private Image imgTres = new Image("resources/3.png");
    private Image imgCuatro = new Image("resources/4.png");
    private Image imgCinco = new Image("resources/5.png");
    private Image imgSeis = new Image("resources/6.png");
    private Image imgSiete = new Image("resources/7.png");
    private Image imgOcho = new Image("resources/8.png");
    private Image imgBandera = new Image("resources/bandera.png");
    private Image imgInterrogante = new Image("resources/interrogante.png");
    private Image imgVacio = new Image("resources/vacio.png");

    private Image imgFondo1 = new Image("resources/tierra1.png");
    private Image imgFondo2 = new Image("resources/tierra2.png");
    private Image imgFondo3 = new Image("resources/tierra3.png");
    private Image imgFondo4 = new Image("resources/tierra4.png");
    private Image imgFondo5 = new Image("resources/tierra5.png");
    private Image imgFondo6 = new Image("resources/tierra6.png");
    private Image imgFondo7 = new Image("resources/tierra7.png");
    private Image imgFondo8 = new Image("resources/tierra8.png");
    private Image imgFondo9 = new Image("resources/tierra9.png");
    private Image imgFondo10 = new Image("resources/tierra10.png");
    private Image imgFondo11 = new Image("resources/tierra11.png");
    private Image imgFondo12 = new Image("resources/tierra12.png");
    private Image imgFondo13 = new Image("resources/tierra13.png");
    private Image imgFondo14 = new Image("resources/tierra14.png");
    private Image imgFondo15 = new Image("resources/tierra15.png");
    private Image imgFondo16 = new Image("resources/tierra16.png");
    private Image imgFondo17 = new Image("resources/tierra17.png");
    private Image imgFondo18 = new Image("resources/tierra18.png");
    private Image imgFondo19 = new Image("resources/tierra19.png");
    private Image imgFondo20 = new Image("resources/tierra20.png");
    private Image imgFondo21 = new Image("resources/tierra21.png");
    private Image imgFondo22 = new Image("resources/tierra22.png");
    private Image imgFondo23 = new Image("resources/tierra23.png");

    private boolean costaNO, costaN, costaNE, costaO, costaE, costaSO, costaS, costaSE;

    //constructor
    public Tablero(int filas, int columnas, int minas) {

        numMinas = minas;
        celdas = new Celda[filas][columnas];
        numFilas = celdas.length;
        numColumnas = celdas[0].length;
        puntuacion = 0;
        maxPuntuacion = ((filas * columnas) - numMinas);
        seguir = true;
        win = false;
        posBombas = new int[numMinas][2];
        busquedas = new int[filas * columnas][2];
        contadorFila = 0;
        listadoProximasBusquedas = 0;
        contadorBusquedas = -1;

        //se crean las celdas vacias
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                celdas[i][j] = new Celda();
            }
        }

        //se añaden las minas aleatoriamente
        for (int i = 0; i < numMinas; i++) {
            boolean minaPlantada = false;

            do {
                randomFila = (int) (Math.random() * numFilas);
                randomColumna = (int) (Math.random() * numColumnas);

                if (celdas[randomFila][randomColumna].getValor() == Valores.VACIO) {
                    celdas[randomFila][randomColumna].setValor(Valores.MINA);
                    celdas[randomFila][randomColumna].getPortaImagenNumero().setImage(imgMina);
                    posBombas[i][0] = randomFila;
                    posBombas[i][1] = randomColumna;
                    minaPlantada = true;
                }
            } while (minaPlantada == false);
        }

        //se cuenta las minas que hay alrededor
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                int contadorMinasAlrededor = 0;
                if (celdas[i][j].getValor() == Valores.VACIO) {
                    if (i != 0) {
                        if (j != 0) {
                            //▲◀
                            if (celdas[i - 1][j - 1].getValor() == Valores.MINA) {
                                contadorMinasAlrededor++;
                            }
                        }
                        //▲
                        if (celdas[i - 1][j].getValor() == Valores.MINA) {
                            contadorMinasAlrededor++;
                        }
                        //▲▶
                        if (j != (numColumnas - 1)) {
                            if (celdas[i - 1][j + 1].getValor() == Valores.MINA) {
                                contadorMinasAlrededor++;
                            }
                        }
                    }
                    //◀
                    if (j != 0) {
                        if (celdas[i][j - 1].getValor() == Valores.MINA) {
                            contadorMinasAlrededor++;
                        }
                    }
                    //▶
                    if (j != numColumnas - 1) {
                        if (celdas[i][j + 1].getValor() == Valores.MINA) {
                            contadorMinasAlrededor++;
                        }
                    }
                    if (i != (numFilas - 1)) {
                        if (j != 0) {
                            //▼◀
                            if (celdas[i + 1][j - 1].getValor() == Valores.MINA) {
                                contadorMinasAlrededor++;
                            }
                        }
                        //▼
                        if (celdas[i + 1][j].getValor() == Valores.MINA) {
                            contadorMinasAlrededor++;
                        }

                        if (j != (numColumnas - 1)) {
                            //▼▶
                            if (celdas[i + 1][j + 1].getValor() == Valores.MINA) {
                                contadorMinasAlrededor++;
                            }
                        }
                    }
                }

                //se rellena alrededor de las minas con los numeros correspondientes
                switch (contadorMinasAlrededor) {
                    case 1:
                        celdas[i][j].setValor(Valores.UNO);
                        celdas[i][j].getPortaImagenNumero().setImage(imgUno);
                        break;
                    case 2:
                        celdas[i][j].setValor(Valores.DOS);
                        celdas[i][j].getPortaImagenNumero().setImage(imgDos);
                        break;
                    case 3:
                        celdas[i][j].setValor(Valores.TRES);
                        celdas[i][j].getPortaImagenNumero().setImage(imgTres);
                        break;
                    case 4:
                        celdas[i][j].setValor(Valores.CUATRO);
                        celdas[i][j].getPortaImagenNumero().setImage(imgCuatro);
                        break;
                    case 5:
                        celdas[i][j].setValor(Valores.CINCO);
                        celdas[i][j].getPortaImagenNumero().setImage(imgCinco);
                        break;
                    case 6:
                        celdas[i][j].setValor(Valores.SEIS);
                        celdas[i][j].getPortaImagenNumero().setImage(imgSeis);
                        break;
                    case 7:
                        celdas[i][j].setValor(Valores.SIETE);
                        celdas[i][j].getPortaImagenNumero().setImage(imgSiete);
                        break;
                    case 8:
                        celdas[i][j].setValor(Valores.OCHO);
                        celdas[i][j].getPortaImagenNumero().setImage(imgOcho);
                        break;
                    default:
                        break;
                }

            }
        }

        //se añade el fondo a cada numero
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                if (celdas[i][j].getValor() != Valores.VACIO) {
                    costaNO = false;
                    costaN = false;
                    costaNE = false;
                    costaO = false;
                    costaE = false;
                    costaSO = false;
                    costaS = false;
                    costaSE = false;

                    if (i != 0) {
                        if (j != 0) {
                            //▲◀
                            if (celdas[i - 1][j - 1].getValor() == Valores.VACIO) {
                                costaNO = true;
                            }
                        }
                        //▲
                        if (celdas[i - 1][j].getValor() == Valores.VACIO) {
                            costaN = true;
                        }
                        //▲▶
                        if (j != (numColumnas - 1)) {
                            if (celdas[i - 1][j + 1].getValor() == Valores.VACIO) {
                                costaNE = true;
                            }
                        }
                    }
                    //◀
                    if (j != 0) {
                        if (celdas[i][j - 1].getValor() == Valores.VACIO) {
                            costaO = true;
                        }
                    }
                    //▶
                    if (j != numColumnas - 1) {
                        if (celdas[i][j + 1].getValor() == Valores.VACIO) {
                            costaE = true;
                        }
                    }
                    if (i != (numFilas - 1)) {
                        if (j != 0) {
                            //▼◀
                            if (celdas[i + 1][j - 1].getValor() == Valores.VACIO) {
                                costaSO = true;
                            }
                        }
                        //▼
                        if (celdas[i + 1][j].getValor() == Valores.VACIO) {
                            costaS = true;
                        }

                        if (j != (numColumnas - 1)) {
                            //▼▶
                            if (celdas[i + 1][j + 1].getValor() == Valores.VACIO) {
                                costaSE = true;
                            }
                        }
                    }

                    if (costaNO && costaSE && costaN == false && costaS == false && costaO == false && costaE == false) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo10);
                    } else if (costaNE && costaSO && costaN == false && costaS == false && costaO == false && costaE == false) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo13);
                    } else if (costaN && costaSE && costaO == false && costaS == false && costaE == false) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo16);
                    } else if (costaN && costaSO && costaO == false && costaS == false && costaE == false) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo17);
                    } else if (costaS && costaNE && costaO == false && costaE == false && costaN == false) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo18);
                    } else if (costaS && costaNO && costaO == false && costaE == false && costaN == false) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo19);
                    } else if (costaO && costaSE && costaN == false && costaS == false && costaE == false) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo20);
                    } else if (costaO && costaNE && costaN == false && costaS == false && costaE == false) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo22);
                    } else if (costaE && costaSO && costaN == false && costaS == false && costaO == false) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo21);
                    } else if (costaE && costaNO && costaN == false && costaS == false && costaO == false) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo23);
                    } else if (costaN && costaO) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo1);
                    } else if (costaN && costaE) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo3);
                    } else if (costaS && costaO) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo7);
                    } else if (costaS && costaE) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo9);
                    } else if (costaN) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo2);
                    } else if (costaO) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo4);
                    } else if (costaE) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo6);
                    } else if (costaS) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo8);
                    } else if (costaNO) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo11);
                    } else if (costaNE) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo15);
                    } else if (costaSO) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo14);
                    } else if (costaSE) {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo12);
                    } else {
                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo5);
                    }

                }

//                    if (costaN == false && costaS == false && costaE == false && costaO == false && costaNO == false && costaNE == false && costaSO == false && costaSE == false) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo5);
//                    }
//                    if (i != 0 && celdas[i - 1][j].getValor() == Valores.VACIO) {
//                        //◀▲
//                        if (j != 0 && celdas[i][j - 1].getValor() == Valores.VACIO) {
//                            celdas[i][j].getPortaImagenFondo().setImage(imgFondo1);
//                            //▲▶
//                        } else if (j != numColumnas - 1 && celdas[i][j + 1].getValor() == Valores.VACIO) {
//                            celdas[i][j].getPortaImagenFondo().setImage(imgFondo3);
//                            //▲
//                        } else {
//                            celdas[i][j].getPortaImagenFondo().setImage(imgFondo2);
//                        }
//                        costaN = true;
//                    } else if (i != (numFilas - 1) && celdas[i + 1][j].getValor() == Valores.VACIO) {
//                        //◀▼
//                        if (j != 0 && celdas[i][j - 1].getValor() == Valores.VACIO) {
//                            celdas[i][j].getPortaImagenFondo().setImage(imgFondo7);
//                            //▼▶
//                        } else if (j != numColumnas - 1 && celdas[i][j + 1].getValor() == Valores.VACIO) {
//                            celdas[i][j].getPortaImagenFondo().setImage(imgFondo9);
//                            //▼
//                        } else {
//                            celdas[i][j].getPortaImagenFondo().setImage(imgFondo8);
//                        }
//                        costaS = true;
//                        //◀
//                    } else if (j != 0 && celdas[i][j - 1].getValor() == Valores.VACIO) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo4);
//                        costaO = true;
//                        //▶
//                    } else if (j != numColumnas - 1 && celdas[i][j + 1].getValor() == Valores.VACIO) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo6);
//                        costaE = true;
//                    }
//
//                    if (costaN == false && costaS == false && costaE == false && costaO == false) {
//
//                        if (i != 0) {
//                            //◀▲
//                            if (j != 0 && celdas[i - 1][j - 1].getValor() == Valores.VACIO) {
//                                celdas[i][j].getPortaImagenFondo().setImage(imgFondo11);
//                                costaNO = true;
//                            } //▲▶
//                            else if (j != (numColumnas - 1) && celdas[i - 1][j + 1].getValor() == Valores.VACIO) {
//                                celdas[i][j].getPortaImagenFondo().setImage(imgFondo15);
//                                costaNE = true;
//                            }
//                        }
//
//                        if (i != (numFilas - 1)) {
//                            //◀▼
//                            if (j != 0 && celdas[i + 1][j - 1].getValor() == Valores.VACIO) {
//                                celdas[i][j].getPortaImagenFondo().setImage(imgFondo14);
//                                costaSO = true;
//                            } //▼▶
//                            else if (j != (numColumnas - 1) && celdas[i + 1][j + 1].getValor() == Valores.VACIO) {
//                                celdas[i][j].getPortaImagenFondo().setImage(imgFondo12);
//                                costaSE = true;
//                            }
//                        }
//
//                    }
//                    if (costaNO && costaSE) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo10);
//                    } else if (costaNE && costaSO) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo13);
//                    } else if (costaN && costaSE) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo16);
//                    } else if (costaN && costaSO) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo17);
//                    } else if (costaS && costaNE) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo18);
//                    } else if (costaS && costaNO) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo19);
//                    } else if (costaO && costaSE) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo20);
//                    } else if (costaE && costaSO) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo21);
//                    } else if (costaO && costaNE) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo22);
//                    } else if (costaO && costaNO) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo23);
//                    }
//
//                    if (costaN == false && costaS == false && costaE == false && costaO == false && costaNO == false && costaNE == false && costaSO == false && costaSE == false) {
//                        celdas[i][j].getPortaImagenFondo().setImage(imgFondo5);
//                    }
            }

        }
    }
//metodos

    public String stringTablero() {
        String cadena = "    ";
        for (int i = 0; i < numColumnas + 2; i++) {
            cadena = cadena + "❂ ";
        }
        cadena = cadena + System.lineSeparator();
        for (int i = 0; i < numFilas; i++) {
            if (contadorFila < 10) {
                cadena = cadena + "  " + contadorFila + " ❂ ";
            } else if (contadorFila > 9 && contadorFila < 100) {
                cadena = cadena + " " + contadorFila + " ❂ ";
            } else if (contadorFila > 99 && contadorFila < 1000) {
                cadena = cadena + "" + contadorFila + " ❂ ";
            }
            for (int j = 0; j < numColumnas; j++) {
                if (seguir == false) {
                    cadena = cadena + celdas[i][j].getValor() + " ";
                } else {
                    if (celdas[i][j].getVisible()) {
                        cadena = cadena + celdas[i][j].getValor() + " ";
                    } else {
                        cadena = cadena + celdas[i][j].getMarca() + " ";
                    }
                }
            }
            cadena = cadena + "❂ " + contadorFila++ + System.lineSeparator();
        }
        cadena = cadena + "    ";
        for (int i = 0; i < (numColumnas + 2); i++) {
            cadena = cadena + "❂ ";
        }
        contadorFila = 0;
        return cadena;
    }

    public String getPosBombas() {
        String cadena = "    ▛ ▘▘▘▘▘▘▘▘▘▘▘▜" + System.lineSeparator() + "     💣LISTADO DE MINAS💣 \t " + System.lineSeparator() + "    ▙ ▖▖▖▖▖▖▖▖▖▖▖▟" + System.lineSeparator() + "    ▛ ▘▘▘▘▘▘▘▘▘▘▘▜" + System.lineSeparator() + "       Nº\tCOORDS" + System.lineSeparator();
        for (int i = 0; i < posBombas.length; i++) {
            if (((i) + 1) < 10) {
                cadena = cadena + "     " + "  " + ((i) + 1) + "\t" + "[" + posBombas[i][0] + "," + posBombas[i][1] + "]" + "\t" + System.lineSeparator();
            } else if (((i) + 1) > 9 && ((i) + 1) < 100) {
                cadena = cadena + "     " + " " + ((i) + 1) + "\t" + "[" + posBombas[i][0] + "," + posBombas[i][1] + "]" + "\t" + System.lineSeparator();
            } else if (((i) + 1) > 99 && ((i) + 1) < 1000) {
                cadena = cadena + "     " + "" + ((i) + 1) + "\t" + "[" + posBombas[i][0] + "," + posBombas[i][1] + "]" + "\t" + System.lineSeparator();
            }
        }
        cadena = cadena + "    ▙ ▖▖▖▖▖▖▖▖▖▖▖▟";
        return cadena;
    }

    public void marcarCasilla(int i, int j, char letra) {
        if (celdas[i][j].getVisible() == false) {
            switch (letra) {
                case 'S':
                case 's':
                    celdas[i][j].setMarca(Marcas.SINMARCA);
                    break;
                case 'M':
                case 'm':
                    celdas[i][j].setMarca(Marcas.CONMINA);
                    break;
                case 'D':
                case 'd':
                    celdas[i][j].setMarca(Marcas.DUDAS);
                    break;
                default:
                    break;
            }
        }
    }

    public boolean comprobarCelda(int i, int j, char letra) {

        if (celdas[i][j].getVisible() == false && (letra == 'A' || letra == 'a')) {
            celdas[i][j].setVisible();
            celdas[i][j].getValor();
            if (celdas[i][j].getValor() != Valores.MINA) {
                puntuacion++;
                if (celdas[i][j].getValor() == Valores.VACIO) {
                    revelarCeldasAlrededor(i, j);
                }
                if (puntuacion == maxPuntuacion) {
                    comprobarVictoria();
                }
            } else {
                maxPuntuacion = maxPuntuacion + numMinas;
                seguir = false;
            }
        }
        return seguir;
    }

    public void revelarCeldasAlrededor(int i, int j) {
        do {
            if (celdas[i][j].getValor() == Valores.VACIO) {
                if (i != 0) {
                    if (j != 0) {
                        //▲◀
                        if (celdas[i - 1][j - 1].getValor() != Valores.MINA && celdas[i - 1][j - 1].getVisible() == false) {
                            celdas[i - 1][j - 1].setVisible();
                            puntuacion++;
                            if (celdas[i - 1][j - 1].getValor() == Valores.VACIO) {
                                busquedas[listadoProximasBusquedas][0] = (i - 1);
                                busquedas[listadoProximasBusquedas][1] = (j - 1);
                                listadoProximasBusquedas++;
                            }
                        }
                    }
                    //▲
                    if (celdas[i - 1][j].getValor() != Valores.MINA && celdas[i - 1][j].getVisible() == false) {
                        celdas[i - 1][j].setVisible();
                        puntuacion++;
                        if (celdas[i - 1][j].getValor() == Valores.VACIO) {
                            busquedas[listadoProximasBusquedas][0] = (i - 1);
                            busquedas[listadoProximasBusquedas][1] = j;
                            listadoProximasBusquedas++;
                        }
                    }
                    //▲▶
                    if (j != (numColumnas - 1)) {
                        if (celdas[i - 1][j + 1].getValor() != Valores.MINA && celdas[i - 1][j + 1].getVisible() == false) {
                            celdas[i - 1][j + 1].setVisible();
                            puntuacion++;
                            if (celdas[i - 1][j + 1].getValor() == Valores.VACIO) {
                                busquedas[listadoProximasBusquedas][0] = (i - 1);
                                busquedas[listadoProximasBusquedas][1] = (j + 1);
                                listadoProximasBusquedas++;
                            }
                        }
                    }
                }
                if (j != 0) {

                    //◀
                    if (celdas[i][j - 1].getValor() != Valores.MINA && celdas[i][j - 1].getVisible() == false) {
                        celdas[i][j - 1].setVisible();
                        puntuacion++;
                        if (celdas[i][j - 1].getValor() == Valores.VACIO) {
                            busquedas[listadoProximasBusquedas][0] = i;
                            busquedas[listadoProximasBusquedas][1] = (j - 1);
                            listadoProximasBusquedas++;
                        }
                    }
                }
                if (j != numColumnas - 1) {
                    //▶
                    if (celdas[i][j + 1].getValor() != Valores.MINA && celdas[i][j + 1].getVisible() == false) {
                        celdas[i][j + 1].setVisible();
                        puntuacion++;
                        if (celdas[i][j + 1].getValor() == Valores.VACIO) {
                            busquedas[listadoProximasBusquedas][0] = i;
                            busquedas[listadoProximasBusquedas][1] = (j + 1);
                            listadoProximasBusquedas++;
                        }
                    }
                }
                if (i != (numFilas - 1)) {
                    if (j != 0) {
                        //▼◀
                        if (celdas[i + 1][j - 1].getValor() != Valores.MINA && celdas[i + 1][j - 1].getVisible() == false) {
                            celdas[i + 1][j - 1].setVisible();
                            puntuacion++;
                            if (celdas[i + 1][j - 1].getValor() == Valores.VACIO) {
                                busquedas[listadoProximasBusquedas][0] = (i + 1);
                                busquedas[listadoProximasBusquedas][1] = (j - 1);
                                listadoProximasBusquedas++;
                            }
                        }
                    }
                    //▼
                    if (celdas[i + 1][j].getValor() != Valores.MINA && celdas[i + 1][j].getVisible() == false) {
                        celdas[i + 1][j].setVisible();
                        puntuacion++;
                        if (celdas[i + 1][j].getValor() == Valores.VACIO) {
                            busquedas[listadoProximasBusquedas][0] = (i + 1);
                            busquedas[listadoProximasBusquedas][1] = j;
                            listadoProximasBusquedas++;
                        }
                    }
                    if (j != (numColumnas - 1)) {
                        //▼▶
                        if (celdas[i + 1][j + 1].getValor() != Valores.MINA && celdas[i + 1][j + 1].getVisible() == false) {
                            celdas[i + 1][j + 1].setVisible();
                            puntuacion++;
                            if (celdas[i + 1][j + 1].getValor() == Valores.VACIO) {
                                busquedas[listadoProximasBusquedas][0] = (i + 1);
                                busquedas[listadoProximasBusquedas][1] = (j + 1);
                                listadoProximasBusquedas++;
                            }
                        }
                    }
                }
            }
            contadorBusquedas++;
            i = busquedas[contadorBusquedas][0];
            j = busquedas[contadorBusquedas][1];
        } while (contadorBusquedas < listadoProximasBusquedas);
        contadorBusquedas--;
    }

    public void comprobarVictoria() {
        if (maxPuntuacion == puntuacion) {
            win = true;
            seguir = false;
            puntuacion = puntuacion + numMinas;
            maxPuntuacion = maxPuntuacion + numMinas;
        }
    }

    public int getMaxPuntuacion() {
        if (maxPuntuacion < 0) {
            maxPuntuacion = 0;
        }
        return maxPuntuacion;
    }

    public void setMaxPuntuacion(int maxPuntuacion) {
        this.maxPuntuacion = maxPuntuacion;
    }

    public Celda[][] getCeldas() {
        return celdas;
    }

    public int getNumMinas() {
        return numMinas;
    }

    public void setNumMinas(int numMinas) {
        this.numMinas = numMinas;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public boolean isWin() {
        return win;
    }

    public boolean getSeguir() {
        return seguir;
    }

    public Image getImgBandera() {
        return imgBandera;
    }

    public Image getImgInterrogante() {
        return imgInterrogante;
    }
    
      public Image getImgVacio() {
        return imgVacio;
    }
}
