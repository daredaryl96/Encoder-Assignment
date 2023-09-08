import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        EncoderDecoder encoderDecoder = new EncoderDecoder();
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter the offset character : ");
        char offset = scanner.next().charAt(0);
        offset = Character.toUpperCase(offset); // Ensure uppercase

        encoderDecoder.initializeReferenceTable(offset); // Initialize the reference table

        String referenceTable = encoderDecoder.getReferenceTable();
        System.out.println("The new reference table based on the offset character is " + referenceTable);

        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter the plaintext: ");
        String plainText = scanner.nextLine();
        
        // Initialize the dictionary
        encoderDecoder.initializeDictionary();


        String encodedText = encoderDecoder.encode(plainText);
        System.out.println("Encoded: " + offset + encodedText);
    
        System.out.print("Do you want to decode the encoded text? (yes/no): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        
        if (choice.equals("yes")) {
            String decodedText = encoderDecoder.decode(encodedText);
            System.out.println("Decoded: " + decodedText);
        } else {
            System.out.println("Okay, not decoding the text.");
        }
    
        scanner.close();
    }
}