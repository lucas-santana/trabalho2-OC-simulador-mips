import java.util.*;

public class InstructionMemory {

    /**
     * A key do hashmap é o endereço da instrução e o value é o valor hexadecimal da instrução
     */
    private static Map<String, String> instructions;

    /**
     * Carrega o atributo instructions com as instruções em hexadecimal lidas do arquivo
     *
     * @param instructionsHex arraylist de instrucoes em hexadecimal
     */
    public static void loadInstructionMemory(ArrayList<String> instructionsHex) {
        InstructionMemory.instructions = new LinkedHashMap<String, String>(64);
        String addressInstruction = "400000";
        for (String instructionHex : instructionsHex) {
            InstructionMemory.instructions.put(addressInstruction, instructionHex);
            long valHex = Long.parseLong(addressInstruction, 16);
            addressInstruction = Long.toHexString(valHex + 0x4);
        }
    }

    /**
     * Passo o endereço da instrução na memória e retorno o valor da instrução
     * @param address endereço da instrução na memória
     * @return valor da instrução em hexadecimal
     */
    public static String getInstruction(String address){
        return InstructionMemory.instructions.get(address);
    }

    /**
     * Retorna a instrucao atual, endereçada pelo endereço anterior do PC
     * @return valor da instrução em hexadecimal
     */
    public static String getCurrentInstruction(){
        return InstructionMemory.instructions.get(PC.getPreviousAddress());
    }

}
