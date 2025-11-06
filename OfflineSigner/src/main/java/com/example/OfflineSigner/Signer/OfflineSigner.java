package com.example.OfflineSigner.Signer;






import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;


public class OfflineSigner {

    public static void getSignature(){
        Scanner sc = new Scanner(System.in);
        System.out.println("<-- Signature Generator -->");

        System.out.print("PrivateKey: ");
        String privateKey = sc.nextLine();

        System.out.print("HashTX: ");
        String hashTX = sc.nextLine();


        System.out.println("Signature ⚠️🔑: " + makeSignature(privateKey, hashTX));
    }

    public static String makeSignature(String privateKey, String hashTX){
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKeyDecoded = keyFactory.generatePrivate(keySpec);

            Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
            ecdsaSign.initSign(privateKeyDecoded);
            ecdsaSign.update(hashTX.getBytes());
            byte[] signatureBytes = ecdsaSign.sign();

            return Base64.getEncoder().encodeToString(signatureBytes);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate signature", e);
        }
    }
}











