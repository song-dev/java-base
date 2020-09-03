package com.song.utils.hex;

public final class  HexBin {
    static private final int BASE_LENGTH = 128;
    static private final int LOOKUP_LENGTH = 16;
    static final private byte [] hexNumberTable    = new byte[BASE_LENGTH];
    static final private char [] lookUpHexAlphabet = new char[LOOKUP_LENGTH];


    static {
        for (int i = 0; i < BASE_LENGTH; i++ ) {
            hexNumberTable[i] = -1;
        }
        for ( int i = '9'; i >= '0'; i--) {
            hexNumberTable[i] = (byte) (i-'0');
        }
        for ( int i = 'F'; i>= 'A'; i--) {
            hexNumberTable[i] = (byte) ( i-'A' + 10 );
        }
        for ( int i = 'f'; i>= 'a'; i--) {
           hexNumberTable[i] = (byte) ( i-'a' + 10 );
        }

        for(int i = 0; i<10; i++ ) {
            lookUpHexAlphabet[i] = (char)('0'+i);
        }
        for(int i = 10; i<=15; i++ ) {
            lookUpHexAlphabet[i] = (char)('A'+i -10);
        }
    }

    /**
     * Encode a byte array to hex string
     *
     * @param binaryData array of byte to encode
     * @return return encoded string
     */
    static public String encode(byte[] binaryData) {
        if (binaryData == null)
            return null;
        int lengthData   = binaryData.length;
        int lengthEncode = lengthData * 2;
        char[] encodedData = new char[lengthEncode];
        int temp;
        for (int i = 0; i < lengthData; i++) {
            temp = binaryData[i];
            if (temp < 0)
                temp += 256;
            encodedData[i*2] = lookUpHexAlphabet[temp >> 4];
            encodedData[i*2+1] = lookUpHexAlphabet[temp & 0xf];
        }
        return new String(encodedData);
    }

    /**
     * Decode hex string to a byte array
     *
     * @param encoded encoded string
     * @return return array of byte to encode
     */
    static public byte[] decode(String encoded) {
        if (encoded == null)
            return null;
        int lengthData = encoded.length();
        if (lengthData % 2 != 0)
            return null;

        char[] binaryData = encoded.toCharArray();
        int lengthDecode = lengthData / 2;
        byte[] decodedData = new byte[lengthDecode];
        byte temp1, temp2;
        char tempChar;
        for( int i = 0; i<lengthDecode; i++ ){
            tempChar = binaryData[i*2];
            temp1 = (tempChar < BASE_LENGTH) ? hexNumberTable[tempChar] : -1;
            if (temp1 == -1)
                return null;
            tempChar = binaryData[i*2+1];
            temp2 = (tempChar < BASE_LENGTH) ? hexNumberTable[tempChar] : -1;
            if (temp2 == -1)
                return null;
            decodedData[i] = (byte)((temp1 << 4) | temp2);
        }
        return decodedData;
    }
}