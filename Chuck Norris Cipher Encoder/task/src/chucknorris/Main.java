package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            EncoderProcessor processor = new EncoderProcessor(scanner);
            processor.run();
        }
    }
}