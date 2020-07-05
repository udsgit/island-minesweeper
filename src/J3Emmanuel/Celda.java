package J3Emmanuel;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Celda {

    //atributos
    private Valores valor;
    private Marcas marca;
    private boolean visible;
    private boolean bandera;
    private boolean interrogante;
    private final Button boton;
    private final ImageView portaImagenNumero;
    private final ImageView portaImagenFondo;

    //constructor
    public Celda() {
        valor = Valores.VACIO;
        marca = Marcas.SINMARCA;
        visible = false;
        bandera = false;
        interrogante = false;
        boton = new Button();
        boton.setStyle("-fx-background-color: \n"
                + "        #090a0c,\n"
                + "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n"
                + "        linear-gradient(#20262b, #191d22),\n"
                + "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n"
                + "    -fx-background-radius: 0;\n"
                + "    -fx-background-insets: 0,1,2,0;\n"
                + "    -fx-text-fill: white;\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n"
                + "    -fx-font-family: \"Arial\";\n"
                + "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n"
                + "    -fx-font-size: 12px;\n"
                + "    -fx-padding: 0;");

        portaImagenNumero = new ImageView("resources/vacio.png");
        portaImagenFondo = new ImageView("resources/agua.png");
    }

    //metodos
    public void setVisible() {
        visible = true;
    }

    public boolean getVisible() {
        return visible;
    }

    public Valores getValor() {
        return valor;
    }

    public void setValor(Valores valor) {
        this.valor = valor;
    }

    public Marcas getMarca() {
        return marca;
    }

    public void setMarca(Marcas marca) {
        this.marca = marca;
    }

    public Button getBoton() {
        return boton;
    }

    public ImageView getPortaImagenNumero() {
        return portaImagenNumero;
    }

    public ImageView getPortaImagenFondo() {
        return portaImagenFondo;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public boolean isInterrogante() {
        return interrogante;
    }

    public void setInterrogante(boolean interrogante) {
        this.interrogante = interrogante;
    }

}
