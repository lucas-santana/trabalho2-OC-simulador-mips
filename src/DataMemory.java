import javax.xml.crypto.Data;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataMemory {
    /**
     * A key do hashmap é o endereço do dado na memória variando de 4 em 4 e o value é o valor do dado
     */
    private static Map<String, String> dataMemory = new LinkedHashMap<String, String>(64);

    /**
     * igual ao WRITE_MEM do Registers
     */
    private static String MEM_WRITE;

    /**
     * igual ao READ_MEM do Registers
     */
    private static String MEM_READ;

    private static String READ_DATA;

    public static void setupDataMemory(){
        Integer key = 0x10010000;
        for(int i =0;i<64;i++){
            if(Integer.toHexString(key).equals("10010000")){
                DataMemory.dataMemory.put(Integer.toHexString(key),"abc");
            }else{
                DataMemory.dataMemory.put(Integer.toHexString(key),"");
            }

            key += 0x4;
        }
        String teste = "testado";
    }

    /**
     * o WRITE_DATA vem do RT e o endereço vem do alu_result
     */
    public static void writeDataMemory() throws Exception {
        if(Registers.WRITE_MEM.equals("1")){
            String address = Alu.getAluResult();

            long numDecRegister = Help.getDecFromBinary(Instruction.getRtBinary());
            String value = Registers.getRegisterByNumber((int)(numDecRegister)).getValue();

            DataMemory.dataMemory.put(address, value);
        }
    }

    /**
     * metodo para setar o valor de saida do READ_DATA do DataMemory
     */
    public static void setReadData(){
        DataMemory.READ_DATA = DataMemory.dataMemory.get(Alu.getAluResult());
    }


    /**
     * Pego o dado gravado no endereço apontado pela alu_result da Alu
     * Apenas se o READ_MEM for igua a 1
     * @return valor do dado na memória
     */

    public static String getReadData(){
        if(Registers.READ_MEM.equals("1")){//retorna o READ_DATA do Data Memory apenas se MemPara Reg estiver 1
            return DataMemory.READ_DATA;
        }
        return null;
    }

    public static Map<String, String> getDataMemory() {
        return dataMemory;
    }
}
