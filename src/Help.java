import java.util.ArrayList;
import java.util.List;

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
     * Converte um valor decimal em binário
     * @param stringHex valor hexadecimal
     * @return valor em binário
     */
    public static String getBinaryFromDec(String stringHex){
        return Long.toBinaryString((Long.parseLong(stringHex, 10)));
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

        return instructionBinary.substring(begin, end+1);
    }

    /**
     * Retorna o valor hexadecimal do funct pelo nome da instrução
     * @return valor hexadecimal do funct pelo nome da instrução
     */
    public static String getFunctHexFromMnemonic(String mnemonic) throws Exception {

        return switch (mnemonic) {
            case "add" -> "20";
            case "sub" -> "22";
            case "and" -> "24";
            case "or" -> "25";
            case "slt" -> "2a";
            default -> throw new Exception("Mnemonic não encontrado: "+mnemonic);
        };

    }

    /**
     * Retorna o valor hexadecimal do opcode pelo nome da instrução
     * @return valor hexadecimal do opcode pelo nome da instrução
     */
    public static String getOpcodeHexFromMnemonic(String mnemonic) throws Exception {

        return switch (mnemonic) {
            case "lw" -> "23";
            case "sw" -> "2b";
            case "beq" -> "4";
            default -> throw new Exception("Mnemonic não encontrado: "+mnemonic);
        };

    }

    public static boolean isInstructionRByMnemonic(String mnemonic){
        return List.of("add", "sub", "and", "or", "slt").contains(mnemonic);
    }

    public static boolean isInstructionIByMnemonic(String mnemonic){
        return List.of("lw", "sw", "beq").contains(mnemonic);
    }

    public static void printData(){
        System.out.println("PC: "+PC.getValue()+"\t\t\t\tALU_A: "+Alu.getA()+"\t\t\t\tALU_B: "+Alu.getB()+"\t\t\t\tALU_OUT: "+Alu.getAluResult());

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("Banco de registradores");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        int count=0;
        for(Register register:Registers.getRegisters()){
            count++;
            System.out.printf("%-20s%s", "Registrador: "+register.getName(), "Valor: "+register.getValue());

            if(count%2==0){
                System.out.print("\n");
            }else{
                System.out.printf("%-20s","");
            }
        }
        System.out.print("\n");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("Memória de Instrução");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        count=0;
        for(String s: InstructionMemory.getInstructions().keySet()){
            System.out.println("Endereço:"+s+" Valor: "+InstructionMemory.getInstructions().get(s));
        }
        System.out.print("\n");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");


        System.out.println("Memória de Dados");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        count=0;
        for(String s: DataMemory.getDataMemory().keySet()){
            System.out.println("Endereço:"+s+" Valor: "+DataMemory.getDataMemory().get(s));
        }
        System.out.print("\n");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }

    public static String getStringLabelFromArquivoLido() throws Exception {
        ArrayList<String> arquivoLido = Arquivo.arquivoLido;
        for (int i = 0; i < arquivoLido.size(); i++) {
            String linha = arquivoLido.get(i);
            if (linha.contains(":")) {
                return linha.substring(0, linha.indexOf(":")+1);
            }
        }
        throw new Exception("label não encontrado parao beq");
    }
    public static int getIndexLabelFromArquivoLido( String label) throws Exception {
        ArrayList<String> arquivoLido = Arquivo.arquivoLido;
        for (int i = 0; i < arquivoLido.size(); i++) {
            String linha = arquivoLido.get(i);
            if (linha.contains(label)) {
                //return linha.indexOf(label + ":");
                return i;
            }
        }
        throw new Exception("não encontrado linha com label: "+label);
    }

    public static int getIndexbeqWithLabelFromArquivoLido(String label) throws Exception {
        ArrayList<String> arquivoLido = Arquivo.arquivoLido;
        label = label.replace(":","");
        for (int i = 0; i < arquivoLido.size(); i++) {
            String linha = arquivoLido.get(i);
            if (linha.contains("beq")) {
                int indexFoundLabel =  linha.indexOf(label);
                String strinFoundLabel = linha.substring(indexFoundLabel).trim();
                if(label.equals(strinFoundLabel)){
                    return i;
                }
            }
        }
        throw new Exception("não encontrado linha beq para esse label: "+label);
    }
}
