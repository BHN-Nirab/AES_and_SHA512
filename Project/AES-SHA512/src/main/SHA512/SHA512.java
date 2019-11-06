package main.SHA512;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// This class is where the main body of the algorithm is defined
public class SHA512 {
    public static int DEBUG = 0;
    public static FileOutputStream fout=null;
    public static FileInputStream fin = null;
    public static String mymain(String msg) throws IOException {
       // String input= new String(Files.readAllBytes(Paths.get("gaan.mp3")));
        String input = msg;
        // Read the entire input file as UTF-8
        //String input = new String(Files.readAllBytes(inputFile.toPath()), StandardCharsets.UTF_8);

        // A strange bug occurs on Windows since it adds a carriage return as well as a newline
        // Thus we have to get rid of all carriage returns in the read input
        input = input.replaceAll("\r\n", "\n");

        // Do the hash|| passes a byte Array
        String hashed = SHA512.hash(input.getBytes());

        // Print it out
        return hashed;
    }


    // Does the actual hash
    public static String hash(byte[] input) {
        // First pad the input to the correct length, adding the bits specified in the SHA algorithm
        input = Logic.pad(input);

        // Break the padded input up into blocks
        long[][] blocks = Logic.toBlocks(input);

        // And get the expanded message blocks
        long[][] W = Logic.Message(blocks);

        // Set up the buffer which will eventually contain the final hash
        // Initially, it's set to the constants provided as part of the algorithm
        long[] buffer = Constants.IV.clone();

        // For every block
        for (int i = 0; i < blocks.length; i++) {
            // a-h is set to the buffer initially
            long a = buffer[0];
            long b = buffer[1];
            long c = buffer[2];
            long d = buffer[3];
            long e = buffer[4];
            long f = buffer[5];
            long g = buffer[6];
            long h = buffer[7];

            // Run 80 rounds of the SHA-512 compression function on a-h
            for (int j = 0; j < 80; j++) {
                long t1 = h + Logic.Sigma1(e) + Logic.Ch(e, f, g) + Constants.K[j] + W[i][j];
                long t2 = Logic.Sigma0(a) + Logic.Maj(a, b, c);
                h = g;
                g = f;
                f = e;
                e = d + t1;
                d = c;
                c = b;
                b = a;
                a = t1 + t2;
            }

            // After finishing the compression, save the state to the buffer
            buffer[0] = a + buffer[0];
            buffer[1] = b + buffer[1];
            buffer[2] = c + buffer[2];
            buffer[3] = d + buffer[3];
            buffer[4] = e + buffer[4];
            buffer[5] = f + buffer[5];
            buffer[6] = g + buffer[6];
            buffer[7] = h + buffer[7];
        }
        // After everything is done, return the final hash as a string
        String result = "";
        for (int i = 0; i < 8; i++) {
            result += String.format("%016x", buffer[i]);
        }

        return result;
    }
}
