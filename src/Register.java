public class Register {
    private final String name;
    private final Integer number;

    /**
     * Valor do registrador em hexadecimal
     */
    private String value;


    public Register(String name, Integer number, String value) {
        this.name = name;
        this.number = number;
        this.value = value;
    }

    public String getBinaryFromDecimal() {
        return Help.padLeft(Long.toBinaryString(this.number), '0', 5);
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    public String getValue() {
        return value;
    }

    /**
     * Seta o valor do registrador
     * @param value valor do registrador
     */
    public void setValue(String value) {
        this.value = value;
    }
}
