public class PC {

    /**
     * valor do contador de programas
     */
    private static String value;


    public static void setupPC(){
        PC.value = "400000";
    }

    public static String getValue() {
        return value;
    }

    /**
     * Incrementa o PC de 4 unidades
     */
    public static void increaseAndGetValue() {
        String pcAtual = PC.value;
        //converte da string hexadecimal para int na base 16
        long valorHexa = Long.parseLong(value, 16);

        //incrementa o valor hexadecimal de 4 e converte para string novamente
        PC.value = Long.toHexString(valorHexa + 0x4);
    }

    public static String getPreviousAddress(){
        //decrementa o valor hexadecimal de 4 e converte para string novamente
        return Long.toHexString(Long.parseLong(value, 16) - 0x4);
    }


    public static void setPCFrombeq() throws Exception {
        if(Registers.BRANCH.equals("1") && Alu.zero.equals("1")){//ADD essa pequena correção no arquivo enviado, só dar branch se Alu.zero = 1, o que significa que o resultado do SUB deu zero
            int imm = Integer.valueOf(Instruction.getImmHex(),16).shortValue();
            Integer result = Integer.parseInt(PC.value, 16) + imm*4;
            PC.value = Integer.toHexString(result);
        }
    }


}
