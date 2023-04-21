package chucknorris;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncoderProcessor {
    private final Scanner scanner;

    public EncoderProcessor(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        boolean loop = true;

        while (loop) {
            System.out.println("Please input operation (encode/decode/exit):");
            String input = scanner.nextLine();

            switch (input.trim().toLowerCase()) {
                case "encode" -> encode();
                case "decode" -> decode();
                case "exit" -> {
                    System.out.println("Bye!");
                    loop = false;
                }
                default -> System.out.printf("There is no '%s' operation\n", input);
            }
        }
    }

    private void encode() {
        String input;
        System.out.println("Input string:");

        input = scanner.nextLine();
        System.out.println("Encoded string:");

        String binaryString = stringToBinary(input);
        System.out.println(binaryToUnary(binaryString));
        System.out.println();
    }

    private String stringToBinary(String input) {
        StringBuilder builder = new StringBuilder();
        char[] charArray = input.toCharArray();

        for (char c : charArray) {
            String binary = charToBinary(c);
            builder.append(binary);
        }

        return builder.toString();
    }

    private String charToBinary(char c) {
        StringBuilder stringBuilder = new StringBuilder();
        String conversion = Integer.toBinaryString(c);

        stringBuilder.append("0".repeat(Math.max(0, 7 - conversion.length())));

        stringBuilder.append(conversion);
        return stringBuilder.toString();
    }

    private void decode() {
        String input;
        String binaryString;
        String decodedString;

        System.out.println("Input encoded string:");
        input = scanner.nextLine();

        if(!isValidUnaryString(input)){
            return;
        }

        binaryString = decodeUnary(input);

        if (!isValidBinaryString(binaryString)) {
            return;
        }

        decodedString = decodeBinary(binaryString);
        System.out.println("Decoded string:");
        System.out.println(decodedString);
        System.out.println();
    }


    private String binaryToUnary(String input) {
        StringBuilder builder = new StringBuilder();

        if (input == null || input.length() == 0) {
            return "";
        }

        appendPrefix(builder, input.charAt(0));
        builder.append("0");

        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i - 1) != input.charAt(i)) {
                builder.append(" ");
                appendPrefix(builder, input.charAt(i));
            }
            builder.append("0");
        }

        return builder.toString();
    }

    private void appendPrefix(StringBuilder builder, char prefix) {
        if (prefix == '1') {
            builder.append("0 ");
        } else {
            builder.append("00 ");
        }
    }

    private String decodePrefix(String prefix) {
        return prefix.equals("00") ? "0" : "1";
    }


    private String decodeUnary(String unaryString) {
        String[] chunks = unaryString.split(" ");
        StringBuilder builder = new StringBuilder();
        String prefix = "";

        for (int i = 0; i < chunks.length; i++) {
            if (i % 2 == 0) {
                prefix = decodePrefix(chunks[i]);
            } else {
                builder.append(prefix.repeat(chunks[i].length()));
            }
        }

        return builder.toString();
    }

    private String decodeBinary(String binaryString) {
        StringBuilder decoded = new StringBuilder();
        int length = binaryString.length();

        for (int i = 0; i < length; i += 7) {
            String ascii = binaryString.substring(0, 7);
            binaryString = binaryString.substring(7);
            int binary = Integer.parseInt(ascii, 2);
            decoded.append((char) binary);
        }
        return decoded.toString();
    }

    private boolean isValidUnaryString(String unaryString) {
        String[] chunks = unaryString.split(" ");
        Pattern pattern = Pattern.compile("^([0\\s]+)$");
        Matcher matcher = pattern.matcher(unaryString);

        if (chunks.length % 2 == 1 || !matcher.find()) {
            System.out.println("Encoded string is not valid.\n");
            return false;
        }

        for (int i = 0; i < chunks.length; i += 2) {
            if (chunks[i].length() < 1 || chunks[i].length() > 2) {
                System.out.println("Encoded string is not valid.\n");
                return false;
            }
        }

        return true;
    }

    private boolean isValidBinaryString(String binaryString) {
        if (binaryString == null || binaryString.length() % 7 > 0) {
            System.out.println("Encoded string is not valid.");
            return false;
        }
        return true;
    }
}