package encryptdecrypt;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


interface Algorithm{
    String encrypt(String text);
    String decrypt(String text);
}

public class Main {
    private static List<String> arguments = List.of("-mode","-data","-key", "-in", "-out", "-alg");
    private static String mode;
    private static String data;
    private static int key;
    private static String inputFileName;
    private static String outputFileName;
    private static Algorithm algo;

    public static void main(String[] args) {
        try{
            initialize(args);
            outputData(mode.equals("dec") ? algo.decrypt(data) : algo.encrypt(data));
        }catch(Exception e){
            System.out.println("error!");
            System.out.println(e.getMessage());
        }
    }

    private static void outputData(String output){
        if (outputFileName == null){
            System.out.println(output);
        }else{
            File outputFile = new File(".\\" + outputFileName);
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(output);
                System.out.println(output);
            } catch (IOException e) {
                System.out.printf("An IO write exception occurs %s", e.getMessage());
            }
        }
    }

    private static void initialize(String[] args){
        for (int i = 0; i < args.length; i += 2){
            if (arguments.contains(args[i])){
                switch (args[i]){
                    case ("-mode"):
                        if (args[i + 1].equals("enc") || args[i + 1].equals("dec")){
                            mode = args[i + 1];
                        }
                        break;
                    case ("-data"):
                        data = args[i + 1];
                        break;
                    case ("-key"):
                        key = Integer.parseInt(args[i + 1]);
                        break;
                    case ("-in"):
                        inputFileName = args[i + 1];
                        break;
                    case ("-out"):
                        outputFileName = args[i + 1];
                        break;
                    case ("-alg"):
                        algo = chooseAlgorithm(args[i + 1]);
                        break;
                    default:
                        break;
                }
            }
        }

        if (data == null && inputFileName == null){
            Scanner s = new Scanner(System.in);
            data = s.nextLine();
            key = s.nextInt();
        }else if (data == null){
            try{
                data = readFileAsString(inputFileName);
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private static Algorithm chooseAlgorithm(String input){
        Algorithm output;
        switch (input){
            case "shift":
                output = new ShiftAlgorithm();
                break;
            case "unicode":
            default:
                output = new UnicodeAlgorithm();
                break;
        }
        return output;
    }


    private static class UnicodeAlgorithm implements Algorithm{
        public String encrypt(String text){
            return text.chars()
                    .mapToObj(e -> {
                        e += key;
                        return (char) e;
                    })
                    .map(Object::toString)
                    .collect(Collectors.joining(""));
        }
        public String decrypt(String text){
            return text.chars()
                    .mapToObj(e -> {
                        e -= key;
                        return (char) e;
                    })
                    .map(Object::toString)
                    .collect(Collectors.joining(""));
        }
    }

    private static class ShiftAlgorithm implements Algorithm{
        public String encrypt(String text){
            int localKey = key % 26;
            char[] textCharArr = text.toCharArray();
            char[] outputCharArr = new char[textCharArr.length];
            for (int i = 0; i < textCharArr.length; i++){
                char el = textCharArr[i];
                if (el < 97 || el > 122){
                    outputCharArr[i] = el;
                }else{
                    el += localKey;
                    if (el > 122){
                        el = (char) (96 + el % 122);
                    }
                    outputCharArr[i] = el;
                }
            }
            return new String(outputCharArr);
        }
        public String decrypt(String text){
            int localKey = key % 26;
            char[] textCharArr = text.toCharArray();
            char[] outputCharArr = new char[textCharArr.length];
            for (int i = 0; i < textCharArr.length; i++){
                char el = textCharArr[i];
                if (el < 97 || el > 122){
                    outputCharArr[i] = el;
                }else{
                    el -= localKey;
                    if (el < 97){
                        el = (char) (123 - 97 % el);
                    }
                    outputCharArr[i] = el;
                }
            }
            return new String(outputCharArr);
        }
    }

    private static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(".\\" + fileName)));
    }
}
