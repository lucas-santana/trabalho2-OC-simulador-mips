public class Instruction {

    /**
     * Retorna o valor do opcode em hexadecimal
     * @return valor do opcode em hexadecimal
     */
    public static String getOpcodeHex() throws Exception {
        //usa o método help para pegar os bits do opcode da instrucao
        String opcode = Help.getBitsFromInstructionHex(InstructionMemory.getCurrentInstruction(), Constant.OPCODE_RANGE_BIT[0], Constant.OPCODE_RANGE_BIT[1]);

        //retorna o valor do opcode em hexadecimal
        return Long.toHexString(Long.parseLong(opcode, 2));
    }

    /**
     * Retorna o valor do funct em hexadecimal
     * @return valor do funct em hexadecimal
     */
    public static String getFunctHex() throws Exception {
        //usa o método help para pegar os bits do opcode da instrucao
        String funct = Help.getBitsFromInstructionHex(InstructionMemory.getCurrentInstruction(), Constant.FUNCT_RANGE_BIT[0], Constant.FUNCT_RANGE_BIT[1]);

        //retorna o valor do funct em hexadecimal
        return Long.toHexString(Long.parseLong(funct, 2));
    }

    /**
     * Retorna o número do registrador RT em binário
     * @return número do registrador RT em binário
     */
    public static String getRtBinary() throws Exception {
        //retorna o número do registrador RT em binário
        return Help.getBitsFromInstructionHex(InstructionMemory.getCurrentInstruction(), Constant.RT_RANGE_BIT[0], Constant.RT_RANGE_BIT[1]);
    }

    /**
     * Retorna o número do registrador RD em binário
     * @return número do registrador RD em binário
     */
    public static String getRdBinary() throws Exception {
        //retorna o número do registrador RD em binário
        return Help.getBitsFromInstructionHex(InstructionMemory.getCurrentInstruction(), Constant.RD_RANGE_BIT[0], Constant.RD_RANGE_BIT[1]);
    }

    /**
     * Retorna o valor do IMM em hexadecimal
     * @return valor do IMM em hexadecimal
     */
    public static String getImmHex() throws Exception {
        //usa o método help para pegar os bits do imm da instrucao
        String immBinary = Help.getBitsFromInstructionHex(InstructionMemory.getCurrentInstruction(), Constant.IMM_RANGE_BIT[0], Constant.IMM_RANGE_BIT[1]);

        //retorna o valor do IMM em hexadecimal
        return Long.toHexString(Long.parseLong(immBinary, 2));
    }

    public static String sinalExtendImmHex() throws Exception {
        String immBinary16Bits = Help.getBitsFromInstructionHex(InstructionMemory.getCurrentInstruction(), Constant.IMM_RANGE_BIT[0], Constant.IMM_RANGE_BIT[1]);
        String immHex32bits = null;

        if(immBinary16Bits.charAt(0) == '0'){
            immHex32bits = Help.padLeft(immBinary16Bits, '0', 32);
        }
        if(immBinary16Bits.charAt(0) == '1'){
            immHex32bits = Help.padLeft(immBinary16Bits, '1', 32);
        }

        immHex32bits = Long.toHexString(Long.parseLong(immHex32bits, 2));

        return immHex32bits;

    }
}
