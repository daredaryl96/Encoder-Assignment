class EncoderDecoder { 
    private static final String originalReferenceTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789()*+,-./";
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
        char[] encodedText = new char[plainText.length()];
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            int index = originalReferenceTable.indexOf(Character.toUpperCase(c));
            if (index != -1) {
                encodedText[i] = referenceTable.charAt(index);
            } else {
                encodedText[i] = c; // If character is not in referenceTable, keep it unchanged. 
            }
        }
        return new String(encodedText);
    }


    public String decode(String encodedText) {
        char[] decodedText = new char[encodedText.length()];
        for (int i = 0; i < encodedText.length(); i++) {
            char c = encodedText.charAt(i);
            int index = referenceTable.indexOf(Character.toUpperCase(c));
            if (index != -1) {
                decodedText[i] = originalReferenceTable.charAt(index);
            } else {
                decodedText[i] = c; // If character is not in referenceTable, keep it unchanged
            }
        }
        return new String(decodedText);
    }
}