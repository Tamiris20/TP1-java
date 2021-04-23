package po.leit;

import org.w3c.dom.ls.LSOutput;
import po.leit.ui.Le;
import po.leit.ui.MyCommand;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TP1 {

    private static MyCommand interC;
    static final int MAX_ALUNOS = 35;
    private static int alunosLidos=0;
    private static int notaMax = 0;
    private static int notaMin = 0;
    private static int notaAvg = 0;

    private static String[] nomeAlunos = new String[MAX_ALUNOS];
    private static int[] notasAlunos = new int[MAX_ALUNOS];

    public static void main(String[] args) {
        boolean querSair=false;

        interC=new MyCommand();

        do {
            interC.limparEcra();
            interC.showPrompt();
            String[] cmdEscrito = interC.lerComando();
            ArrayList<String> cmd = interC.validarComando(cmdEscrito);

            if (cmd == null) {
                interC.showMsg("Comando inválido. Digite help para ajuda");

            } else {
                if  ( cmd.get(0).equalsIgnoreCase("carregar") ) {
                    alunosLidos = loadData(nomeAlunos, "turmaLeit.txt");
                    int notA = loadData(notasAlunos);
                    if ( alunosLidos != notA ) {
                        System.out.println("alunos = " + alunosLidos);
                        System.out.println("notaA = " + notA);
                        interC.showMsg("Erro carregando dados");
                    }
                        
                    else

                        interC.showMsg("Dados carregados OK!");
                } else if (cmd.get(0).equalsIgnoreCase("listar") ) {
                    mostrarAlunos();

                } else if (cmd.get(0).equalsIgnoreCase("paginar") ) {
                    String input = JOptionPane.showInputDialog("Nũmeros estudantes por pãgina :");
                    int numeroU = Integer.parseInt(input);
                    mostrarAlunos(numeroU);

                } else if (cmd.get(0).equalsIgnoreCase("mostrarp") ) {
                    mostrarPauta();

                } else if (cmd.get(0).equalsIgnoreCase("mostrarr") ) {
                    mostraResumo();

                } else if (cmd.get(0).equalsIgnoreCase("top") ) {
                    mostrarTop();

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnome") ) {
                    String nomePesq = JOptionPane.showInputDialog("O que procura  :");
                    pesquisar(nomePesq);

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnota") ) {
                    String vaPesq = JOptionPane.showInputDialog("O que procura  :");
                    int notaPesq = Integer.parseInt(vaPesq);
                    pesquisar(notaPesq);
                } else if (cmd.get(0).equalsIgnoreCase("help") ) {
                    interC.showHelp();

                } else if (cmd.get(0).equalsIgnoreCase("terminar") ) {
                    querSair = true;
                }
            }

        } while (!querSair);

    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array e um ficheiro
     * Lẽ cada linha do ficheiro e guarda no array. Retorna o número
     * de linhas que forma lidas do ficheiro.
     * @param lAlunos
     * @param nomeFicheiro
     * @return quantos nomes foram lidos do ficheiro -1 se não possível ler ficheiro
     */
    public static int loadData(String[] lAlunos, String nomeFicheiro) {
        Scanner in = null;
        File inputFile = new File(nomeFicheiro);
        //PrintWriter out = new PrintWriter(outputFileName);
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (in.hasNextLine()) {
            String nomeAl = in.nextLine();
            if ( (nomeAl != null) && !(nomeAl.isBlank()) && !(nomeAl.isEmpty() ) ) {
                lAlunos[i] = nomeAl;
                i++;
            }

        }
        in.close();
        return i;
    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array de inteiros e vai gerar aleatoriamente valores inteiros entre
     * 0 e 20 que representam a nota de cada aluno.
     * @param lNotas
     * @return how much name was read from the files -1 if was not able to read the file
     */
    public static int loadData(int[] lNotas) {
        Random rand = new Random();
        int cont = 0;
        for (cont=0; cont < alunosLidos; cont++) {
            int randomNum = rand.nextInt(20) + 1;
            notasAlunos[cont] = randomNum;
        }
        return cont;
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados"
     * @param
     * @return
     */
    public static void mostrarAlunos() {
        if (nomeAlunos[0]== null){
            interC.showMsg("Não há dados");
        }
        else{
            System.out.printf( "codigo"+"\tEstudante");
            System.out.print("\n");
            int contador=0;
            for(int i=0;i<= nomeAlunos.length-1;i++){
                if(nomeAlunos[i]!=null){
                    contador=contador+1;
                    System.out.println("" + contador + "" + "\t" + nomeAlunos[i]);
                }
            }
        }
        interC.showMsg(" ");
    }

    /**
     * Método a ser implementando no TP1
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados".
     * Neste método os dados não são mostrados todos de uma só vez. Devem ser apresentados até encher a tela.
     * Vamos supor que 10 nomes enchem a tela. Então deverá ser apresentados de 10 em 10. Esse número
     * que indica quantos nomes enchem a tela é um parâmetro do método.
     * @param tela é um inteiro que indica quantos alunos são mostrados.
     */
    public static void mostrarAlunos(int tela) {
        if (nomeAlunos[0] == null) {
            interC.showMsg("Não há dados ");
        }
        if (nomeAlunos!=null) {
            if (tela > 0) {
                int alidos = 0;
                int contador = 1;
                System.out.println(" " + "Codigo" + "Estudante");
                for (String aluno : nomeAlunos) {
                    if (aluno == null)
                        break;
                    alidos = alidos + 1;

                    System.out.println(" " + contador++ + " " + aluno);
                    if (alidos>= tela) {
                        interC.showMsg("Para a proxima pag digite enter");

                        System.out.println("  \n");
                        alidos = 0;
                    }
                }
            }
        }
            interC.showMsg(" ");
        }



    /**
     * Método a ser implementando no TP1.
     * O método deverá percorrer o array de notas, calcular o valor da média aritmética de notas, a nota máximo e
     * a nota mínima.
     * Os valores calculados devem ser guaraddos na variáveis notaAVG (média),
     * notaMax (nota máxima) e notaMin(nota mínima)
     * Devem validar se o array de notas tem elementos. Se estiver vazio devem somente apresentar
     * a mensagem "Não há dados"
     */
    private static void calcularMaxMinAvg() {
        int contador=0;
        int somatorio=0;
        if (nomeAlunos[0] == null){
            System.out.println("Não há dados");
        }
        else{
            for (int i=0; i<=nomeAlunos.length-1; i++){
                if(notasAlunos[i]>notaMax && nomeAlunos[i] != null){
                    notaMax = notasAlunos[i];
                }
                else if (notasAlunos[i] < notaMin && nomeAlunos[i] != null){
                    notaMin= notasAlunos[i];
                }
                somatorio=somatorio+notasAlunos[i];
                contador+=1;
            }
            notaAvg=somatorio/ contador;
            System.out.println("Maxima nota:" + notaMax);
            System.out.println("Minima nota:" + notaMin);
            System.out.println("Media:" + notaAvg);
        }
        interC.showMsg(" ");

    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar um resumo da avaliação;
     * Nota máxima, Nota mínima, Nota média. Número de alunos com nota superior a média e número de alunos com
     * nota inferior a média.
     * a mensagem "Não há dados"
     */
    public static void mostraResumo() {
        int Nmax = 0, Nmin = 0;
        calcularMaxMinAvg();
        for (int i = 0; i <= notasAlunos.length - 1; i++) {
            if (nomeAlunos[0] != null) {
                Nmax += 1;
            } else {
                Nmin += 1;
            }
        }

        System.out.println("Numero de alunos com nota inferior a media:" + Nmin);
        System.out.println("Numero de alunos com a nota superior a media:" + Nmax);
    interC.showMsg(" ");

    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar o nome dos três alunos que têm as melhores notas.
     */
    public static void mostrarTop() {
        interC.showMsg(" a ser implementado...");

    }
    /**
     * Método a ser implementando no TP1.
     * Apresentar a pauta com nomes dos alunos e á frente cada nome a respectiva nota obtida.
     */
    public static void mostrarPauta() {
        if (nomeAlunos[0] == null) {
            interC.showMsg("Não há dados ");

        }
        if (nomeAlunos!= null){
            int alidos=0;
            int contador=1;
            System.out.println("Codigo"+"Estudante");
            for(int i=0; i<= nomeAlunos.length-1;i++){
                if(nomeAlunos[i]==null)
                    break;
                alidos += 1;
                System.out.println(" "+ contador++ + " " + nomeAlunos[i] +":"+ " Nota_ " + notasAlunos[i]);
                if (alidos >= 10){
                    System.out.println("   \n");
                    alidos=0;
                }
            }
        }
        interC.showMsg(" ");

    }
    /**
     * Método a ser implementando no TP1
     * Apresentar para um aluno específico em que o nome é dado como parâmetro a nota de avaliação
     * @param nome é uma string contendo o nome do aluno que queremos apresentar a sua nota
     * @return
     */
    public static void mostrarDetalhesAluno(String nome) {
        interC.showMsg("A ser implementado ...");

    }

    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(String nome) {
        if (nomeAlunos[0]==null){
            System.out.println("Não há dados");
        }
        else{
            for (int i=0; i <= nomeAlunos.length -1; i++){
                int cod = i+1;
                if(nome.equalsIgnoreCase(nomeAlunos[i])){
                    System.out.println("Nome encontrado");

                    System.out.println("Nº" + cod + " " + nomeAlunos[i]);

                }
            }
        }
        interC.showMsg(" ");

    }

    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(int nota) {
        if (nomeAlunos[0] == null) {
            interC.showMsg("Não há dados");
        }

        else {
            int contador = 0;
            for (int i = 0; i <= nomeAlunos.length - 1; i++) {
                contador += 1;
                if (nota == notasAlunos[i] && nomeAlunos[i] != null) {
                    System.out.println(" " + contador + " " + nomeAlunos[i] + " " + notasAlunos[i]);
                }
            }
        }

        interC.showMsg(" ");
    }




    private String[] searchByName(String nome) {
        return null;
    }

}
