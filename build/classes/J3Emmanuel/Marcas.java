package J3Emmanuel;

public enum Marcas {
    SINMARCA("●"),
    CONMINA("⮾"),
    DUDAS("Ⓓ");

    private final String value;

    private Marcas(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
