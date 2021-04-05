import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {



    public static void main(String[] args) {
        try {
            Main main = new Main();
            main.setup();
            main.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setup() {
        //Inicializa o PC no endereço 4000
        PC.setupPC();

        //Cria os objetos dos registradores
        Registers.setupRegisters();

        //Cria as posições de memória de dados
        DataMemory.setupDataMemory();

        //instruções para teste
        ArrayList<String> testeInstrucoes = new ArrayList<>();
        testeInstrucoes.add("0232b820");//add $s7, $s1, $s2
        testeInstrucoes.add("0232b022");//sub $s6, $s1,$s2
        testeInstrucoes.add("0232a825");//or $s5,$s1, $s2
        testeInstrucoes.add("0232a024");//and $s4, $s1, $s2
        testeInstrucoes.add("0232982a");//slt $s3, $s1,$s2 --> s1 < 2 ? s3 = 1: s3 = 0
        testeInstrucoes.add("8e110000");//lw $s1, 0($s0) carrega do endereço apontado por s0 para s1
        testeInstrucoes.add("ae510004");//sw $s1, 0($s2) salva o conteudo de s1 para endereço de memoria apontado por s2
        testeInstrucoes.add("0270902a");
        testeInstrucoes.add("04500004");
        testeInstrucoes.add("06500010");
        testeInstrucoes.add("02500020");
        testeInstrucoes.add("01800030");


        InstructionMemory.loadInstructionMemory(testeInstrucoes);

        /*for(String s: this.instructionMemory.getInstructions().keySet()){
            System.out.println("Chave:"+s+" Valor: "+this.instructionMemory.getInstructions().get(s));
        }*/
    }

    public void loop() throws Exception {
        Scanner input = new Scanner(System.in);
        String valor = input.nextLine();
        boolean clockSubida = false;//false - clock desceu true - clock subiu
        while (!valor.equals("quit")) {
            if (valor.equals("1")) {
                clockSubida = !clockSubida;
                if (clockSubida) {

                    System.out.println("Subiu: \u2191");

                    //1 - Pega o endereço da proxima instrução no PC e busca essa instrução através desse endereço na Instruction Memmory
                    PC.increaseAndGetValue();

                    String instruction = InstructionMemory.getCurrentInstruction();

                    if (instruction == null) {
                        System.out.println("Fim do programa...");
                        break;
                    }

                    //2 - Separar essa instrução em RS, RT, etc armazenando os valores nos registradores (REGISTERS)
                    Registers.initializeRegistersFromInstruction();

                    //3 - Inicializar a unidade de controle
                    ControlUnit.initializeControlUnit();

                    AluCtrl.initializeAluCtrl();

                    //Seta as entradas da Alu (A, B e aluControlCode)
                    Alu.setInputAlu();

                    //Seta o aluResult da ALU
                    Alu.setAluResult();

                    //seta a saida do Data Memory, READ_DATA
                    DataMemory.setReadData();

                    //escreve WRITE_DATA(RT) no endereço calculado pela alu_result
                    DataMemory.writeDataMemory();

                    //Escreve no registrador de destino de acordo com REG_DST (RT ou RD) o dado de acordo MEM_FOR_REG (alu_result ou red_data)
                    Registers.writeData();


                    System.out.println("Instrução: " + instruction);
                } else {
                    System.out.println("Desceu: \u2193");
                }
            }

            valor = input.nextLine();
        }


    }
}
