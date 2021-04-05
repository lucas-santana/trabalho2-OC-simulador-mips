public class Alu {
    private static String A;
    private static String B;
    private static String aluControlCode;
    private static String aluResult;


    public static void setInputAlu() throws Exception {
        long numDecRegister = Help.getDecFromBinary(Registers.RS);
        A = Registers.getRegisterByNumber((int)(numDecRegister)).getValue();

        if(Registers.ORIG_ALU.equals("0")){
            numDecRegister = Help.getDecFromBinary(Registers.RT);
            B = Registers.getRegisterByNumber((int)numDecRegister).getValue();
        }

        if(Registers.ORIG_ALU.equals("1")){
            //TODO: fazer lógica do signal extend???
            //Min 22:30 da aula 14
            //Se o bit MSB do IMM for 0 completa com zero a esquerda, se for 1 completa com
            B = Instruction.sinalExtendImmHex();
        }


        Alu.aluControlCode = AluCtrl.getOutAluCtrl();
    }

    public static void setAluResult() throws Exception {
        switch (aluControlCode){
            //ADD
            case "2":
                String result = Long.toHexString(Long.parseLong(Alu.A, 16) + Long.parseLong(Alu.B, 16));
                Alu.aluResult = Help.padLeft(result,'0', 8);

                break;

            //SUB
            case "6":
                Alu.aluResult = Long.toHexString(Long.parseLong(Alu.A, 16) - Long.parseLong(Alu.B, 16));
                break;

            //AND
            case "0":
                //AluCtrl.outAluCtrl = "0";
                break;

            //OR
            case "1":
                //AluCtrl.outAluCtrl = "1";
                break;

            //SLT
            case "7":
                //AluCtrl.outAluCtrl = "7";
                break;
        }
    }


    public static String getAluResult() {
        return aluResult;
    }
}