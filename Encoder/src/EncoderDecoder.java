import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

class EncoderDecoder { 
    private static final String originalReferenceTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789()*+,-./";

    private static final String DICTIONARY_FILE_PATH = "/Users/USER/Desktop/Encoder Assignment/Encoder/english_dictionary.txt"; // Change to the path of your dictionary file

    private static Set<String> dictionary;

    private String referenceTable;

    //Shifting of table based on offset character
    public void initializeReferenceTable(char offset) {
        int indexOffset = originalReferenceTable.indexOf(offset);
        int n = originalReferenceTable.length();
        if (indexOffset == -1) {
            // Handle invalid offset character
            referenceTable = originalReferenceTable; // Use the original reference table as fallback
        } else {    
            String shiftedPart = originalReferenceTable.substring(n - indexOffset);
            String prefixPart = originalReferenceTable.substring(0, n-indexOffset);
            referenceTable = shiftedPart + prefixPart;
        }
    }

    public String getReferenceTable() {
        return referenceTable;
    }

    public String encode(String plainText) {
        StringBuilder encodedText = new StringBuilder();
            for (int i = 0; i < plainText.length(); i++) {
                char c = plainText.charAt(i);
                int index = originalReferenceTable.indexOf(Character.toUpperCase(c));
                if (index != -1) {
                    encodedText.append(referenceTable.charAt(index));
                } else {
                    encodedText.append(c); // If character is not in referenceTable, keep it unchanged.
                }
            }
            return encodedText.toString();
        }


    public String decode(String encodedText) {
        for (int offsetAttempt = 0; offsetAttempt < originalReferenceTable.length(); offsetAttempt++) {
            char offset = originalReferenceTable.charAt(offsetAttempt);
            String decodedText = decodeWithReferenceTable(encodedText, offset);
    
            // Check if the decoded text contains valid characters or words
            if (isValidDecodedText(decodedText)) {
                return decodedText;
            }
        }
    
        return "Decoding failed. Unable to find a valid result in English.";
    }

    private String decodeWithReferenceTable(String encodedText, char offset) {
        StringBuilder decodedText = new StringBuilder();
        for (char c : encodedText.toCharArray()) {
            int index = originalReferenceTable.indexOf(c);
            if (index != -1) {
                int decodedIndex = (index - originalReferenceTable.indexOf(offset) + originalReferenceTable.length()) % originalReferenceTable.length();
                // calculates the index of the decoded character in the originalReferenceTable
                decodedText.append(originalReferenceTable.charAt(decodedIndex));
            } else {
                decodedText.append(c);
            }
        }
        return decodedText.toString();
    }
    
    public void initializeDictionary() {
        dictionary = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DICTIONARY_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.add(line.toUpperCase()); // Store words in uppercase for case-insensitive comparison
            }
        } catch (IOException e) {
            System.err.println("Error reading the dictionary file: " + e.getMessage());
        }
    }

    private static boolean isValidDecodedText(String decodedText) {
        String[] words = decodedText.split("\\s+"); // Split the decoded text into words
        for (String word : words) {
            if (!dictionary.contains(word.toUpperCase())) {
                return false; // If any word is not in the dictionary, consider it invalid
            }
        }
        return true; // All words are valid according to the dictionary
    }

}