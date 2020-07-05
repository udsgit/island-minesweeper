package J3Emmanuel;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class J3EmmanuelMain extends Application {

    final int tamanhoCeldasJuego = 64;
    final int tamanhoInicialY = tamanhoCeldasJuego * 2;
    int numFilas, numColumnas, numMinas, posBotonesJuegoX = 0, posBotonesJuegoY = tamanhoInicialY;
    double tamanhoVentanaX = 450, tamanhoVentanaY = 450;

    int tamanhoMaxTableroX = 0, tamanhoMaxTableroY = 0;
    int banderasRestantes;

    @Override
    public void start(Stage primaryStage) {

        //Se crean deslizadores con sus etiquetas
        Label[] etiquetasMenu = new Label[3];
        Slider[] deslizadores = new Slider[3];
        Label[] etiquetas = new Label[3];
        for (int i = 0; i < deslizadores.length; i++) {
            etiquetasMenu[i] = new Label();
            deslizadores[i] = new Slider();
            deslizadores[i].setMin(1);
            deslizadores[i].setMax(100);
            deslizadores[i].setShowTickLabels(true);
            deslizadores[i].setShowTickMarks(true);
            deslizadores[i].setMajorTickUnit(49);
            deslizadores[i].setMinorTickCount(5);
            deslizadores[i].setBlockIncrement(10);
            etiquetas[i] = new Label(String.valueOf((int) deslizadores[i].getValue()));
            final int num = i;
            deslizadores[i].valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    etiquetas[num].setText(String.valueOf((int) deslizadores[num].getValue()));
                }
            });
        }

        //Modificamos las etiquetas y creamos el boton de crear partida
        etiquetasMenu[0].setText("Filas?");
        etiquetasMenu[1].setText("Columnas?");
        etiquetasMenu[2].setText("Minas?");
        Button botonCrearTablero = new Button();
        botonCrearTablero.setMinSize(50, 50);

        //Se crea la ventana del menu y se añaden objetos
        TilePane menu = new TilePane();
        menu.setPadding(new Insets(15));
        menu.setPrefColumns(3);
        menu.setPrefRows(4);
        menu.getChildren().addAll(etiquetasMenu[0], deslizadores[0], etiquetas[0]);
        menu.getChildren().addAll(etiquetasMenu[1], deslizadores[1], etiquetas[1]);
        menu.getChildren().addAll(etiquetasMenu[2], deslizadores[2], etiquetas[2]);
        menu.getChildren().addAll(new Label(), botonCrearTablero);

        //Se crea la ventana del menu con su contenido y le damos al play
        Scene menuScene = new Scene(menu, tamanhoVentanaX, tamanhoVentanaY);
        primaryStage.setTitle("BuscaMinas 3.0");
        primaryStage.setScene(menuScene);
        primaryStage.show();

        //Se crea la ventana del juego y su panel deslizable
        Pane juego = new Pane();
        ScrollPane panelDeslizable = new ScrollPane(juego);
        Scene juegoScene = new Scene(panelDeslizable);

        //Se crea la ventana final
        Label gameOver = new Label();
        TilePane pantallaFinal = new TilePane();
        pantallaFinal.getChildren().add(gameOver);
        Scene finalScene = new Scene(pantallaFinal, 230, 100);
        Stage finalStage = new Stage();
        finalStage.setScene(finalScene);
        finalStage.initModality(Modality.WINDOW_MODAL);
        finalStage.initOwner(primaryStage);
        finalStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
//                primaryStage.setMaxHeight(tamanhoVentanaX);
//                primaryStage.setHeight(tamanhoVentanaX);
//                primaryStage.setMaxWidth(tamanhoVentanaY);
//                primaryStage.setWidth(tamanhoVentanaY);
                primaryStage.setScene(menuScene);
                juego.getChildren().clear();
                posBotonesJuegoY = tamanhoInicialY;
            }
        });

        //Al pulsar el boton se crea el tablero y cambiamos la Scene a la del juego
        botonCrearTablero.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                numFilas = (Integer.valueOf(etiquetas[0].getText()));
                numColumnas = (Integer.valueOf(etiquetas[1].getText()));
                numMinas = (Integer.valueOf(etiquetas[2].getText()));
                banderasRestantes = numMinas;
                if (numMinas < (numFilas * numColumnas) - 1) {
                    primaryStage.setScene(juegoScene);
                    Tablero unTablero = new Tablero(numFilas, numColumnas, numMinas);
                    tamanhoMaxTableroX = (tamanhoCeldasJuego * numFilas) + tamanhoCeldasJuego;
                    tamanhoMaxTableroY = (tamanhoCeldasJuego * numColumnas) + tamanhoCeldasJuego;
//                    primaryStage.setMaxHeight(tamanhoMaxTableroX + posBotonesJuegoY);
//                    primaryStage.setHeight(tamanhoMaxTableroX + posBotonesJuegoY);
//                    primaryStage.setMaxWidth(tamanhoMaxTableroY);
//                    primaryStage.setWidth(tamanhoMaxTableroY);
                    primaryStage.setFullScreen(true);

                    ImageView portaImagenConBandera = new ImageView(unTablero.getImgBandera());
                    portaImagenConBandera.setFitHeight(tamanhoCeldasJuego * 2);
                    portaImagenConBandera.setFitWidth(tamanhoCeldasJuego * 2);

                    Label muestraBanderasRestantes = new Label(String.valueOf(banderasRestantes), portaImagenConBandera);
                    muestraBanderasRestantes.setMinHeight(tamanhoCeldasJuego * 2);
                    muestraBanderasRestantes.setMinWidth(tamanhoCeldasJuego * 2);
                    muestraBanderasRestantes.setFont(new Font("Digital-7 Mono", tamanhoCeldasJuego * 2));
                    muestraBanderasRestantes.relocate((tamanhoMaxTableroY / 2) - (tamanhoCeldasJuego * 2), 0);

                    juego.getChildren().add(muestraBanderasRestantes);

                    for (int i = 0; i < numFilas; i++) {
                        for (int j = 0; j < numColumnas; j++) {
                            unTablero.getCeldas()[i][j].getBoton().setMinSize(tamanhoCeldasJuego, tamanhoCeldasJuego);
                            unTablero.getCeldas()[i][j].getBoton().setPadding(Insets.EMPTY);
                            unTablero.getCeldas()[i][j].getBoton().relocate(posBotonesJuegoX, posBotonesJuegoY);
                            posBotonesJuegoX = (int) unTablero.getCeldas()[i][j].getBoton().getLayoutX();
                            posBotonesJuegoY = (int) unTablero.getCeldas()[i][j].getBoton().getLayoutY();
                            unTablero.getCeldas()[i][j].getPortaImagenNumero().setFitHeight(tamanhoCeldasJuego);
                            unTablero.getCeldas()[i][j].getPortaImagenNumero().setFitWidth(tamanhoCeldasJuego);
                            unTablero.getCeldas()[i][j].getPortaImagenNumero().relocate(posBotonesJuegoX, posBotonesJuegoY);
                            unTablero.getCeldas()[i][j].getPortaImagenNumero().setVisible(false);

                            unTablero.getCeldas()[i][j].getPortaImagenFondo().setFitHeight(tamanhoCeldasJuego);
                            unTablero.getCeldas()[i][j].getPortaImagenFondo().setFitWidth(tamanhoCeldasJuego);
                            unTablero.getCeldas()[i][j].getPortaImagenFondo().relocate(posBotonesJuegoX, posBotonesJuegoY);
                            unTablero.getCeldas()[i][j].getPortaImagenFondo().setVisible(false);

                            juego.getChildren().addAll(unTablero.getCeldas()[i][j].getPortaImagenFondo(), unTablero.getCeldas()[i][j].getPortaImagenNumero(), unTablero.getCeldas()[i][j].getBoton());
                            posBotonesJuegoX = posBotonesJuegoX + tamanhoCeldasJuego;
                            final int fila = i;
                            final int columna = j;
                            unTablero.getCeldas()[i][j].getBoton().setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    MouseButton botonRaton = event.getButton();
                                    if (botonRaton == MouseButton.PRIMARY) {
                                        unTablero.comprobarCelda(fila, columna, 'A');
                                        for (int i = 0; i < numFilas; i++) {
                                            for (int j = 0; j < numColumnas; j++) {
                                                if (unTablero.getCeldas()[i][j].getVisible()) {
                                                    unTablero.getCeldas()[i][j].getBoton().setVisible(false);
                                                    unTablero.getCeldas()[i][j].getPortaImagenFondo().setVisible(true);
                                                    unTablero.getCeldas()[i][j].getPortaImagenNumero().setVisible(true);
                                                }
                                            }
                                        }
                                        if (unTablero.getSeguir() == false) {
                                            if (unTablero.isWin()) {
                                                finalStage.show();
                                                gameOver.setText("HAZ GANADO, PUNTUACION: " + unTablero.getPuntuacion() + "/" + unTablero.getMaxPuntuacion());
                                            } else {
                                                for (int i = 0; i < numFilas; i++) {
                                                    for (int j = 0; j < numColumnas; j++) {
                                                        if (unTablero.getCeldas()[i][j].getVisible() == false) {
                                                            unTablero.getCeldas()[i][j].getBoton().setVisible(false);
                                                            unTablero.getCeldas()[i][j].getPortaImagenFondo().setVisible(true);
                                                            unTablero.getCeldas()[i][j].getPortaImagenNumero().setVisible(true);
                                                        }
                                                    }
                                                }
                                                finalStage.show();
                                                gameOver.setText("GAME OVER, PUNTUACION : " + unTablero.getPuntuacion() + "/" + unTablero.getMaxPuntuacion());
                                            }
                                        }
                                    } else if (botonRaton == MouseButton.SECONDARY) {
                                        if (unTablero.getCeldas()[fila][columna].isBandera() == false && unTablero.getCeldas()[fila][columna].isInterrogante() == false) {
                                            unTablero.getCeldas()[fila][columna].getBoton().setGraphic(new ImageView(unTablero.getImgBandera()));
                                            unTablero.getCeldas()[fila][columna].setBandera(true);
                                            banderasRestantes--;
                                            muestraBanderasRestantes.setText(String.valueOf(banderasRestantes));
                                        } else if (unTablero.getCeldas()[fila][columna].isBandera() == true && unTablero.getCeldas()[fila][columna].isInterrogante() == false) {
                                            unTablero.getCeldas()[fila][columna].setBandera(false);
                                            unTablero.getCeldas()[fila][columna].getBoton().setGraphic(new ImageView(unTablero.getImgInterrogante()));
                                            unTablero.getCeldas()[fila][columna].setInterrogante(true);
                                            banderasRestantes++;
                                            muestraBanderasRestantes.setText(String.valueOf(banderasRestantes));
                                        } else if (unTablero.getCeldas()[fila][columna].isBandera() == false && unTablero.getCeldas()[fila][columna].isInterrogante() == true) {
                                            unTablero.getCeldas()[fila][columna].setInterrogante(false);
                                            unTablero.getCeldas()[fila][columna].getBoton().setGraphic(new ImageView(unTablero.getImgVacio()));
                                        }
                                    }
                                }
                            });
                        }
                        posBotonesJuegoY = posBotonesJuegoY + tamanhoCeldasJuego;
                        posBotonesJuegoX = 0;
                    }
                }
            }

        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
