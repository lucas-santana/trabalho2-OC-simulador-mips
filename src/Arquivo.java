import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Arquivo {

    public ArrayList<String> lerArquivo(String pathFile) {
        BufferedReader buffRead;
        ArrayList<String> arquivoLido = new ArrayList<String>();
        try {
            buffRead = new BufferedReader(new FileReader(pathFile));
            String linha = (buffRead.readLine()).trim();
            int indexCerquilha;

            while (linha != null) {
                linha = linha.trim();
                if (linha.length() > 0) {
                    indexCerquilha = linha.indexOf('#');
                    if(indexCerquilha == -1){//não encontrou cerquilha, não encontrou comentários na linha
                        arquivoLido.add(linha);
                    }else{
                        linha = linha.substring(0, indexCerquilha);
                        if (linha.length() > 0) {//para os casos onde a linha começa com #
                            arquivoLido.add(linha);//se encontrou cerquilha, pega a string até a cerquilha
                        }

                    }
                }
                linha = buffRead.readLine();
            }
            buffRead.close();

            return arquivoLido;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<String> getInstructionsHex(ArrayList<String> arrayListInstructions) throws Exception {
        int indexCharacter;
        String operation;
        ArrayList<String> arrayListInstuctionsHex = new ArrayList<String>();


        for (String string:arrayListInstructions) {
            //1 - Pegar o mnemonic
            indexCharacter = string.indexOf(' ');
            if(indexCharacter == -1){
                continue;
            }

            String mnemonic = string.substring(0, indexCharacter);

            operation = string.substring(indexCharacter).trim();

            if(Help.isInstructionRByMnemonic(mnemonic)){
                arrayListInstuctionsHex.add(this.parserInstructionR(mnemonic, operation));
            }
            if(Help.isInstructionIByMnemonic(mnemonic)){
                arrayListInstuctionsHex.add(this.parserInstrucaoTipoI(mnemonic, operation));
            }
        }

        return arrayListInstuctionsHex;
    }

    private String parserInstructionR(String mnemonic, String operation) throws Exception {
        int indexCharacter;
        String opcode = "000000";//Instrução do tipo R, o OPCODE = 000000
        String shamt = "00000";//Shamt só usa na instrução sll e nosso set de instrução não tem sll
        String funct = Help.getBinaryFromHex(Help.getFunctHexFromMnemonic(mnemonic));

        //Pegar o RD
        indexCharacter = operation.indexOf(',');
        if(indexCharacter == -1){
            throw new Exception("Falha ao encontrar vírgula para encontrar o RD...");
        }

        String stringRd = operation.substring(0, indexCharacter);

        Register registradorRd = Registers.getRegisters().stream().filter(search -> {
            return stringRd.equals(search.getName());
        }).findFirst().orElseThrow(()->new Exception("Registrador não encontrado: "+stringRd));

        operation = operation.substring(indexCharacter+1).trim();

        //Pegar o RS
        indexCharacter = operation.indexOf(',');
        if(indexCharacter == -1){
            throw new Exception("Falha ao encontrar vírgula para encontrar o RS...");
        }

        String stringRs = operation.substring(0, indexCharacter);

        Register registradorRs = Registers.getRegisters().stream().filter(search -> {
            return stringRs.equals(search.getName());
        }).findFirst().orElseThrow(()->new Exception("Registrador não encontrado: "+stringRs));

        //Pegar o RT
        String stringRt = operation.substring(indexCharacter + 1).trim();

        Register registradorRt = Registers.getRegisters().stream().filter(search -> {
            return stringRt.equals(search.getName());
        }).findFirst().orElseThrow(()->new Exception("Registrador não encontrado: "+stringRt));

        String binario =  opcode + registradorRs.getBinaryFromDecimal() + registradorRt.getBinaryFromDecimal() + registradorRd.getBinaryFromDecimal() + shamt + funct;


        int binarioDecimal = Integer.parseInt(binario, 2);
        String decimalHexa = Integer.toString(binarioDecimal, 16);
        decimalHexa = Help.padLeft(decimalHexa, '0',8);
        return decimalHexa;
    }

    private String parserInstrucaoTipoI(String mnemonic, String operation) throws Exception {
        int indexCharacter;
        String opcode = Help.padLeft(Help.getBinaryFromHex(Help.getOpcodeHexFromMnemonic(mnemonic)), '0', 6);

        //Pegar o RT
        indexCharacter = operation.indexOf(',');
        if(indexCharacter == -1){
            throw new Exception("Falha ao encontrar vírgula para encontrar o RT...");
        }

        String stringRt = operation.substring(0, indexCharacter);

        Register registradorRt = Registers.getRegisters().stream().filter(search -> {
            return stringRt.equals(search.getName());
        }).findFirst().orElseThrow(()->new Exception("Registrador não encontrado: "+stringRt));

        operation = operation.substring(indexCharacter+1).trim();

        Register registradorRs = null;
        String stringImm = null;

        if(mnemonic.equals("lw") || mnemonic.equals("sw")){
            //Pegar o valor do IMM == Offset
            indexCharacter = operation.indexOf('(');
            stringImm = operation.substring(0, indexCharacter).trim();
            stringImm = Help.getBinaryFromDec(stringImm);

            //deixa string no formato ($s0)
            operation = operation.substring(indexCharacter).trim();

            String stringRs =  operation.substring(operation.indexOf("(") + 1, operation.indexOf(")"));
            registradorRs = Registers.getRegisters().stream().filter(search -> {
                return stringRs.equals(search.getName());
            }).findFirst().orElseThrow(()->new Exception("Registrador não encontrado: "+stringRs));
        }
        if(mnemonic.equals("beq") ){
            //Pegar o RS
            indexCharacter = operation.indexOf(',');
            if(indexCharacter == -1){
                throw new Exception("Falha ao encontrar vírgula para encontrar o RS...");
            }

            String stringRs = operation.substring(0, indexCharacter);

            registradorRs = Registers.getRegisters().stream().filter(search -> {
                return stringRs.equals(search.getName());
            }).findFirst().orElseThrow(()->new Exception("Registrador não encontrado: "+stringRs));

            //Pegar o IMM
            //TODO: calcular o endereço do label
            stringImm = operation.substring(indexCharacter + 1).trim();
            stringImm = stringImm.replace("0x","");
            stringImm = Help.getBinaryFromHex(stringImm);
        }



        String binario =  opcode + registradorRs.getBinaryFromDecimal() + registradorRt.getBinaryFromDecimal()+Help.padLeft(stringImm, '0',16);

        long binarioDecimal = Long.parseLong(binario, 2);
        String decimalHexa = Long.toString(binarioDecimal, 16);
        decimalHexa = Help.padLeft(decimalHexa, '0',8);
        return decimalHexa;
    }
}
