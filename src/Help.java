public class Help {

    /**
     * Completa a string a esquerda com o carater passado como argumento no tamnho passado no argumento length
     * @param string
     * @param stringPad
     * @param length
     * @return
     */
    public static String padLeft(String string, char stringPad, int length) {
        return String.format("%1$" + length + "s", string).replace(' ', '0');//completa com zeros a esquerda
    }

    /**
     * Converte um valor hexadecimal em binário
     * @param stringHex valor hexadecimal
     * @return valor em binário
     */
    public static String getBinaryFromHex(String stringHex){
        return Long.toBinaryString(Long.parseLong(stringHex, 16));
    }

    /**
     * Converte um valor binário  em hexadecimal
     * @param stringBinary valor hexadecimal
     * @return valor em hexadecimal
     */
    public static String getHexFromBinary(String stringBinary){
        return Long.toHexString(Long.parseLong(stringBinary, 2));
    }

    /**
     * Converte um valor binário  em decimal
     * @param stringBinary valor decimal
     * @return valor em binário
     */
    public static long getDecFromBinary(String stringBinary){
        return Long.parseLong(stringBinary, 2);
    }

    /**
     *  Retorna o range de bits especificados por begin e end
     * @param instructionHex
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    public static String getBitsFromInstructionHex(String instructionHex, int begin, int end) throws Exception {
        if(instructionHex.length()<8){
            throw new Exception("Instução hexa menor doque 8 caracteres");
        }

        String instructionBinary = Help.padLeft(getBinaryFromHex(instructionHex), '0', 32);

        if(instructionBinary.length() < 32){
            throw new Exception("Instução hexa menor do que 32 caracteres");
        }
        String teste = "abcdefghijklmnopqrstuvxwyz123456";

        return instructionBinary.substring(begin, end+1);
    }
}
