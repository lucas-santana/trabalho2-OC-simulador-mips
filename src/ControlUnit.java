public class ControlUnit {

    /**
     * Valor do opcode em hexadecimal
     */
    private static String opcodeHex;

    /**
     * Esse valor é usado pela AluCtrl para decidir se vai usar ou não o funct
     */
    private static String outputControl;

    /**
     *  Seta a saida da Unidade de controle
     */
    public static void initializeControlUnit() throws Exception {
        /*
        - Instruções
            Tipo I:
                lw - 23h, sw - 2bh, beq - 4h,
            Tipo R:
                add - 20h, sub - 22h, and - 24h, or - 25h, slt - 2ah
            Tipo J:
                j - 2h
         */
        ControlUnit.opcodeHex = Instruction.getOpcodeHex();
        switch (opcodeHex){
            //Instrução do tipo R
            case "0":
                ControlUnit.outputControl = "2";//Determinada por funct (10), instruções tipo R
                Registers.REG_DST = "1";//1 para Instruções do tipo R, quem recebe é RD
                Registers.ORIG_ALU = "0";
                Registers.MEM_FOR_REG = "0";//0 - Tipo R pega da alu_result
                Registers.REG_WRITE = "1";//Todas instruções do Tipo R salvam no registrador
                Registers.READ_MEM = "0";// 0 para tipo R
                Registers.WRITE_MEM = "0"; //0 para tipo r
                Registers.BRANCH = "0";//0 para tipo r
                break;

            //Instrução do tipo I

            //lw
            case "23":
                ControlUnit.outputControl = "0";//Add (00), para load/store

                Registers.REG_DST = "0";//0 para instrução lw, quem recebe é RT
                Registers.ORIG_ALU = "1";
                Registers.MEM_FOR_REG = "1";//1 - lw pega da memória
                Registers.REG_WRITE = "1";//lw salva no registrador
                Registers.READ_MEM = "1";//1 para lw
                Registers.WRITE_MEM = "0"; //0 para lw
                Registers.BRANCH = "0";//0 para lw
                break;

            //sw
            case "2b":
                ControlUnit.outputControl = "0";//Add (00), para load/store

                Registers.REG_DST = "0";//0 para sw
                Registers.ORIG_ALU = "1";
                Registers.MEM_FOR_REG = "0";//0 - sw pega da alu_result
                Registers.REG_WRITE = "0";//sw não salva no registrador
                Registers.READ_MEM = "0";// 0 para sw
                Registers.WRITE_MEM = "1"; //1 para sw
                Registers.BRANCH = "0";//0 para sw
                break;

            //beq
            case "4":
                ControlUnit.outputControl = "1";//Sub (01), para beq

                Registers.REG_DST = "0";//0 para beq
                Registers.ORIG_ALU = "0";
                Registers.MEM_FOR_REG = "0";//0 - beq pega da alu_result
                Registers.REG_WRITE = "0";//beq não salva no registrador
                Registers.READ_MEM = "0";// 0 para beq
                Registers.WRITE_MEM = "0"; //0 para beq
                Registers.BRANCH = "1";//1 para beq
                break;
        }

    }

    public static String getOutputControl() {
        return outputControl;
    }
}
