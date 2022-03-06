package com.sp.sec.web;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NestedJWTBuilder {
    public static void main(String[] args) throws ParseException, JOSEException, NoSuchAlgorithmException {

        buildNestedJWT(generateKeyPair().getPublic(), buildHmacSha256SignedJWT(UUID.randomUUID().toString()));

    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {

        // instantiate KeyPairGenerate with RSA algorithm.
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");

        // set the key size to 1024 bits.
        keyGenerator.initialize(1024);

        // generate and return private/public key pair.
        return keyGenerator.genKeyPair();
    }

    public static SignedJWT buildHmacSha256SignedJWT(String sharedSecretString) throws JOSEException {

        // build audience restriction list.
        List<String> aud = new ArrayList<String>();
        aud.add("https://app1.foo.com");
        aud.add("https://app2.foo.com");

        Date currentTime = new Date();

        // create a claim set.
        JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder().
                // set the value of the issuer.
                        issuer("https://apress.com").
                // set the subject value - JWT belongs to this subject.
                        subject("john").
                // set values for audience restriction.
                        audience(aud).
                // expiration time set to 10 minutes.
                        expirationTime(new Date(new Date().getTime() + 1000 * 60 * 10)).
                // set the valid from time to current time.
                        notBeforeTime(currentTime).
                // set issued time to current time.
                        issueTime(currentTime).
                // set a generated UUID as the JWT identifier.
                        jwtID(UUID.randomUUID().toString()).build();

        // create JWS header with HMAC-SHA256 algorithm.
        JWSHeader jswHeader = new JWSHeader(JWSAlgorithm.HS256);

        // create signer with the provider shared secret.
        JWSSigner signer = new MACSigner(sharedSecretString);

        // create the signed JWT with the JWS header and the JWT body.
        SignedJWT signedJWT = new SignedJWT(jswHeader, jwtClaims);

        // sign the JWT with HMAC-SHA256.
        signedJWT.sign(signer);

        // serialize into base64-encoded text.
        String jwtInText = signedJWT.serialize();

        // print the value of the JWT.
        System.out.println(jwtInText);

        return signedJWT;
    }

    public static String buildNestedJWT(PublicKey publicKey, SignedJWT signedJwt) throws JOSEException {

        // create JWE header with RSA-OAEP and AES/GCM.
        JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.RSA_OAEP, EncryptionMethod.A128GCM);

        // create encrypter with the RSA public key.
        JWEEncrypter encrypter = new RSAEncrypter((RSAPublicKey) publicKey);

        // create a JWE object with a non-JSON payload
        JWEObject jweObject = new JWEObject(jweHeader, new Payload(signedJwt));

        // encrypt the JWT.
        jweObject.encrypt(encrypter);

        // serialize into base64-encoded text.
        String jwtInText = jweObject.serialize();

        // print the value of the JWT.
        System.out.println(jwtInText);

        return jwtInText;
    }

}
