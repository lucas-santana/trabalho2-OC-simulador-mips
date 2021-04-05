import java.util.ArrayList;

public class Registers {
    private static ArrayList<Register> registers;
    public static String RS;
    public static String RT;
    public static String SHAMT;
    public static String FUNCT;

    /**
     *  REG_DST
     *  Determina se deve pegar o dado do RD (1) ou não (0)
     *  Registrador onde será escrito o dado
     *  1 -  RD se for instrução TIPO R
     *  0 - RT se for instrução lw
     */
    public static String REG_DST;

    /**O
     *   ORIG_ALU
     *   Determima se a entrada B da alu deve pegar o dado do IMM (1) ou não (0)
     *   sinal para saber se a entrada B da alu deve pegar o RT ou o valor do IMM
     *      1 - lw/sw
     *      0 - Tipo R/beq
     */
    public static String ORIG_ALU;


    /**
     *   MemPara REG
     *   Determina se o dado a ser gravado no registrador vem da 1 - memória (READ_DATA) ou se vem da 0 - ALU (alu_result)
     *  1 - lw
     *  0 - Tipo R, sw, beq
     */
    public static String MEM_FOR_REG;


    /**
    *   Escreve_Reg
     *   Determina se o dado vai ser gravado no registrador (1) ou não (0)
    *   sinal que diz se o dado vai ser ou não gravado no registrador REG_DST
    *      1 - lw, Tipo R
    *      0 - sw, beq
    */
    public static String REG_WRITE;

    /**
     *  LeMem
     *  Determina se pode efetuar leitura na memória  (1) ou não (0)
     *   1 - lw
     *   0 - Tipo R, sw, beq
     *
     */
    public static String READ_MEM;

    /**
     *  EscreveMem
     *  Determina se pode gravar na memória (1) ou não (0)
     *  1 - sw
     *  0 - Tipo R, lw, beq
     */
    public static String WRITE_MEM;


    /**
     *  Branch
     *  1 - Se for uma instrução branch
     *  0 - Se for Tipo R, lw ou sw
     */
    public static String BRANCH;


    /**
     * Inicializa os registradores
     */
    public static void setupRegisters(){
        Registers.registers = new ArrayList<Register>();

        Registers.registers.add(new Register("$zero", 0, null));

        Registers.registers.add(new Register("$t0", 8,null));
        Registers.registers.add(new Register("$t1", 9, null));
        Registers.registers.add(new Register("$t2", 10, null));
        Registers.registers.add(new Register("$t3", 11, null));
        Registers.registers.add(new Register("$t4", 12, null));
        Registers.registers.add(new Register("$t5", 13, null));
        Registers.registers.add(new Register("$t6", 14, null));
        Registers.registers.add(new Register("$t7", 15, null));

        Registers.registers.add(new Register("$s0", 16, "10010000"));
        Registers.registers.add(new Register("$s1", 17, "00000006"));
        Registers.registers.add(new Register("$s2", 18, "00000003"));
        Registers.registers.add(new Register("$s3", 19, "00000300"));
        Registers.registers.add(new Register("$s4", 20, "00000400"));
        Registers.registers.add(new Register("$s5", 21, "00000500"));
        Registers.registers.add(new Register("$s6", 22, "00000600"));
        Registers.registers.add(new Register("$s7", 23, "00000700"));

        Registers.registers.add(new Register("$t8", 24, null));
        Registers.registers.add(new Register("$t9", 25, null));
    }

    public static ArrayList<Register> getRegisters(){
        return Registers.registers;
    }

    public static Register getRegisterByName(String name) throws Exception {
        return Registers.registers.stream().filter(search -> {
            return name.equals(search.getName());
        }).findFirst().orElseThrow(()->new Exception("Registrador não encontrado: "+name));
    }

    public static Register getRegisterByNumber(Integer number) throws Exception {
        Register registerFind = Registers.registers.stream().filter(search -> {
            return number.equals(search.getNumber());
        }).findFirst().orElseThrow(()->new Exception("Registrador não encontrado: "+number));

        return registerFind;
    }

    public static void writeData() throws Exception {
        if(Registers.REG_WRITE.equals("1")){
            long numDecRegister = 0;

            if(Registers.REG_DST.equals("0")){//pega o numero decimal do registrador RT
                numDecRegister = Help.getDecFromBinary(Instruction.getRtBinary());
            }
            if(Registers.REG_DST.equals("1")){//pega o numero decimal do registrador RD
                numDecRegister = Help.getDecFromBinary(Instruction.getRdBinary());
            }

            Register registerFind = Registers.getRegisterByNumber((int) numDecRegister);

            if(Registers.MEM_FOR_REG.equals("0")){//0 pega do alu_result
                registerFind.setValue(Alu.getAluResult());
            }

            if(Registers.MEM_FOR_REG.equals("1")){//1 seta o READ_DATA do Data Memory no registrador apenas se MemPara Reg estiver 1
                registerFind.setValue(DataMemory.getReadData());
            }


        }
    }



    /**
     * Seta os registradores de acordo com a instrução atual
     * @throws Exception
     */
    public static void initializeRegistersFromInstruction() throws Exception {
        if(InstructionMemory.getCurrentInstruction().length() < 8){
            throw new Exception("Instução hexa menor do que 32 caracteres");
        }

        String opcode = Help.getBitsFromInstructionHex(InstructionMemory.getCurrentInstruction(), Constant.OPCODE_RANGE_BIT[0], Constant.OPCODE_RANGE_BIT[1]);
        Registers.RS = Help.getBitsFromInstructionHex(InstructionMemory.getCurrentInstruction(),Constant.RS_RANGE_BIT[0], Constant.RS_RANGE_BIT[1]);
        if( Long.parseLong(opcode) == Constant.OPCODE_INSTRUCTION_R){
            //Seta o numero dos registradores em binário
            Registers.RT = Help.getBitsFromInstructionHex(InstructionMemory.getCurrentInstruction(),Constant.RT_RANGE_BIT[0], Constant.RT_RANGE_BIT[1]);

            Registers.SHAMT = Help.getBitsFromInstructionHex(InstructionMemory.getCurrentInstruction(),Constant.SHAMT_RANGE_BIT[0], Constant.SHAMT_RANGE_BIT[1]);
            Registers.FUNCT = Help.getBitsFromInstructionHex(InstructionMemory.getCurrentInstruction(),Constant.FUNCT_RANGE_BIT[0], Constant.FUNCT_RANGE_BIT[1]);
        }

    }

    //Instrução do TIPO R - FORMATO
    //    0   2   3     2    8     0    2   0
    //  0000 0010 0011 0010 1000 0000 0010 0000
    //  0    5    6  10   11 15   16 20   21 25   26  31
    //  000000    10001   10010   10000   00000   100000
    //  0           17      18      16      0       32
    //  OP          RS      RT      RD      SHAMT   FUNCT
    //  0           $s1     $s2     $s0     0       add



    //Instrução do Tipo I - Formato
    //beq $s2, $zero, 1001
    //  1   0    1     2    0   0    0    10
    //0001 0000 0001 0010 0000 0000 0000 1001
    //  4      0     18            10
    //000100 00000 10010 0000000000001001
    // OP       RS  RT        IMM
    // 4     $zero  $s2     10


    /*
            lw $s1, 4($s0)
            8       e       1       1       0       0       0       4
            1000    1110    0001    0001    0000    0000    0000    0100

            10001110000100010000000000000100
            10001110000100010000000000000100


     */
 }
