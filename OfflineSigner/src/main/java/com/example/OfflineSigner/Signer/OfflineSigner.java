package com.example.OfflineSigner.Signer;

import org.apache.commons.codec.digest.DigestUtils;

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
        return DigestUtils.sha256Hex(hashTX + privateKey);

    }











}
