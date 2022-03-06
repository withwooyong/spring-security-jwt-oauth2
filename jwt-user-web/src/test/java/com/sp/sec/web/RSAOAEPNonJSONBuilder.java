package com.sp.sec.web;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSAEncrypter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

public class RSAOAEPNonJSONBuilder {
    public static void main(String[] args) throws ParseException, JOSEException, NoSuchAlgorithmException {

        Security.addProvider(new BouncyCastleProvider());
        buildEncryptedJWT(generateKeyPair().getPublic());
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {

        // instantiate KeyPairGenerate with RSA algorithm.
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");

        // set the key size to 1024 bits.
        keyGenerator.initialize(1024);

        // generate and return private/public key pair.
        return keyGenerator.genKeyPair();
    }

    public static String buildEncryptedJWT(PublicKey publicKey) throws JOSEException {

        // create JWE header with RSA-OAEP and AES/GCM.
        JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.RSA_OAEP, EncryptionMethod.A128GCM);

        // create encrypter with the RSA public key.
        JWEEncrypter encrypter = new RSAEncrypter((RSAPublicKey) publicKey);

        // create a JWE object with a non-JSON payload
        JWEObject jweObject = new JWEObject(jweHeader, new Payload("Hello world!"));

        // encrypt the JWT.
        jweObject.encrypt(encrypter);

        // serialize into base64-encoded text.
        String jwtInText = jweObject.serialize();

        // print the value of the JWT.
        System.out.println(jwtInText);

        return jwtInText;
    }

}
