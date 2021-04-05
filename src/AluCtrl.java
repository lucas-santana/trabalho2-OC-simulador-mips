public class AluCtrl {

    private static String functHex;


    /**
     * Valor que será usado pela AluCtrl para setar o outAluCtrl
     */
    private static String aluOp;


    /**
     * Valor que será usado pela Alu para decidir qual operação efetuar
     */
    private static String outAluCtrl;


    /**
     * Seta o valor de entrada da AluCtrl
     */
    public static void initializeAluCtrl() throws Exception {
        AluCtrl.aluOp = ControlUnit.getOutputControl();
        AluCtrl.functHex = Instruction.getFunctHex();
        AluCtrl.setOutAluCtrl();
    }

    public static void setOutAluCtrl() throws Exception {
        /*
        - Instruções
            Tipo I:
                lw - 23h, sw - 2bh, beq - 4h,
            Tipo R:
                add - 20h, sub - 22h, and - 24h, or - 25h, slt - 2ah
            Tipo J:
                j - 2h
         */


        switch (AluCtrl.getAluOp()){
            //Add (00), para load/store
            case "0":
                AluCtrl.outAluCtrl = "2";
                break;

            //Sub (01), para beq
            case "1":
                AluCtrl.outAluCtrl = "6";
                break;

            //Instructions Type R
            //Determinada por funct (10), instruções tipo R
            case "2":
                String valorHexaFunc = Instruction.getFunctHex();
                switch (valorHexaFunc){
                    //ADD
                    case "20":
                        AluCtrl.outAluCtrl = "2";
                        break;

                    //SUB
                    case "22":
                        AluCtrl.outAluCtrl = "6";
                        break;

                    //AND
                    case "24":
                        AluCtrl.outAluCtrl = "0";
                        break;

                    //OR
                    case "25":
                        AluCtrl.outAluCtrl = "1";
                        break;

                    //SLT
                    case "2a":
                        AluCtrl.outAluCtrl = "7";
                        break;
                }
                break;
        }

    }
    private static String getAluOp() {
        return aluOp;
    }

    public static String getOutAluCtrl() {
        return outAluCtrl;
    }
}
