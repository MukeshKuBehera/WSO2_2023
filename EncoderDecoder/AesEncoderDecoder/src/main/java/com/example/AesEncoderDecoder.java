package com.example;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;

public class AesEncoderDecoder extends AbstractMediator { 

		private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
		private static final String AES_KEY = "0123456789ABCDEF";

		public boolean mediate(MessageContext context) {
			// TODO Implement your mediation logic here

			try {

				// Retrieve the request payload from the property
				String payload = context.getProperty("RequestData").toString();

				// Encode the payload using AES encryption
				String encryptedPayload = encrypt(payload);

				log.debug("Value obtained from sequence is :" + encryptedPayload);

				// Set the decoded payload as the message payload
				String decodedPayload = decrypt(encryptedPayload);
				log.debug("Value Obtained from DebugCode :" + decodedPayload);
				// Set the encoded payload back to the message context
				context.setProperty("EncodedValue", encryptedPayload);
				context.setProperty("DecodedValue", decodedPayload);
			} catch (Exception e) {
				handleException("Error occurred during mediation.", e, context);
				return false;
			}

			return true;
		}

		// encrypt the request data
		private String encrypt(String plaintext) throws Exception {
			try {
				SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(StandardCharsets.UTF_8), "AES");
				Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
				cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
				byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
				return Base64.getEncoder().encodeToString(encryptedBytes);
			} catch (Exception e) {
				handleException("Error occurred during encryption.", e, null);
				throw e;
			}
		}

		// decrypt the encoded data

		private String decrypt(String ciphertext) throws Exception {
			try {
				SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(StandardCharsets.UTF_8), "AES");
				Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
				byte[] decodedBytes = Base64.getDecoder().decode(ciphertext);
				byte[] decryptedBytes = cipher.doFinal(decodedBytes);
				return new String(decryptedBytes, StandardCharsets.UTF_8);
			} catch (Exception e) {
				handleException("Error occurred during decryption.", e, null);
				throw e;
			}
		}

	}
