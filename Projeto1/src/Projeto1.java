import java.time.*;
import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Projeto1{

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException{
        LocalDateTime dataAtual = LocalDateTime.now();
        String formataData = DateTimeFormatter.ofPattern("dd/MM/uuuu").format(dataAtual);
        String formataHora = DateTimeFormatter.ofPattern("HH:mm:ss").format(dataAtual);
        
        Scanner saida = new Scanner(System.in);
        String local;
        System.out.println("Qual o local a ser armazenado os resultados?");
        local = saida.nextLine(); 
        FileWriter dados = new FileWriter (local);// Diretorio da saida de dados
        PrintWriter gravarArq = new PrintWriter(dados);
        
        Scanner scan = new Scanner(System.in);
        String diretorio;
        System.out.println("Qual o diretorio absoluto dos arquivos a serem feitos a HASH?");
        diretorio = scan.nextLine();
        //String diretorio = "C:/PROJETO1"; //Diretório onde vai fazer o HASH dos arquivos
        
        gravarArq.printf("Aluno: Marcus Vinícius Ribeiro | R.A.: 688487\nDisciplina: Segurança e Criptografia | Projeto 1\n");
        gravarArq.printf("\nData: %s | Hora: %s\n",formataData, formataHora);
        gravarArq.printf("------------------------------------------------------\n");
        gravarArq.printf("Diretório Absoluto: %s\n",diretorio);
        gravarArq.printf("------------------------------------------------------\n");
        pastas(diretorio, gravarArq); 
        gravarArq.printf("------------------------------------------------------\n");  
    dados.close();
    System.out.println("SUCESSO!");
    }
    
    static void pastas(String diretorio, PrintWriter gravarArq) throws NoSuchAlgorithmException{
        String novoDiretorio = "";
        File pasta = new File(diretorio);
        File[] listaArq = pasta.listFiles();
        
        for (File arq : listaArq)
        {
            if(arq.isDirectory())
            {
                novoDiretorio = diretorio.concat("/").concat(arq.getName());
                pastas(novoDiretorio, gravarArq);
            }
            if(arq.isFile())
            {
                novoDiretorio = diretorio.concat("/").concat(arq.getName());
                geraArq(novoDiretorio, calculaHash(arq),gravarArq);
            }        
        }
    }
    
    public static void geraArq(String nomeArq, String hash, PrintWriter gravaArq)
    {
        gravaArq.printf("Caminho {%s}: HASH[%s]\n", nomeArq, hash);
    }
    
    static String calculaHash(File nomeArq) throws NoSuchAlgorithmException {
        byte[] bytesEntradaHash = null;
        byte[] bytesSaidaHash = null;
        StringBuilder hashHexadecimal;
        String hash = null;
        
        try {
            bytesEntradaHash = Files.readAllBytes(nomeArq.toPath());
            MessageDigest algoritmoHash = MessageDigest.getInstance("MD5");
            bytesSaidaHash = algoritmoHash.digest(bytesEntradaHash);

            hashHexadecimal = new StringBuilder();
            for (byte b : bytesSaidaHash) {
                hashHexadecimal.append(String.format("%02X", 0xFF & b));
            }

            //System.out.println("Sequência de Bytes da HASH Gerada pelo Algoritmo MD5");
            hash = hashHexadecimal.toString();
            //System.out.print(hash + "\n");
            //System.out.println(" ");

        } catch (IOException erro) {
        }
        return hash;
    }
}
