package be.beanleaf.vim.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

  private static final Random RANDOM = new SecureRandom();
  private static final int ITERATIONS = 10000;
  private static final int KEY_LENGTH = 256;

  /**
   * Returns a random salt to be used to hash a password.
   *
   * @return a 16 bytes random salt
   */
  private byte[] getNextSalt() {
    byte[] salt = new byte[16];
    RANDOM.nextBytes(salt);
    return salt;
  }

  /**
   * Returns a salted and hashed password using the provided hash.<br> Note - side effect: the
   * password is destroyed (the char[] is filled with zeros)
   *
   * @param input the password to be hashed
   * @param salt  a 16 bytes salt, ideally obtained with the getNextSalt method
   * @return the hashed password with a pinch of salt
   */
  private byte[] hash(String input, byte[] salt) {
    char[] password = input.toCharArray();
    PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
    Arrays.fill(password, Character.MIN_VALUE);
    try {
      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      return skf.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
    } finally {
      spec.clearPassword();
    }
  }

  /**
   * Returns true if the given password and salt match the hashed value, false otherwise.<br>
   *
   * @param input        the password to check
   * @param salt         the salt used to hash the password
   * @param expectedHash the expected hashed value of the password
   * @return true if the given password and salt match the hashed value, false otherwise
   */
  public boolean isExpectedPassword(String input, byte[] salt, byte[] expectedHash) {
    byte[] pwdHash = hash(input, salt);
    if (pwdHash.length != expectedHash.length) {
      return false;
    }
    for (int i = 0; i < pwdHash.length; i++) {
      if (pwdHash[i] != expectedHash[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Generates a random password of a given length, using letters and digits.
   *
   * @param length the length of the password
   * @return a random password
   */
  public String generateRandomPassword(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int c = RANDOM.nextInt(62);
      if (c <= 9) {
        sb.append(c);
      } else if (c < 36) {
        sb.append((char) ('a' + c - 10));
      } else {
        sb.append((char) ('A' + c - 36));
      }
    }
    return sb.toString();
  }

  public Pair<byte[], byte[]> createHashSaltPair(String input) {
    byte[] salt = getNextSalt();
    return Pair.of(salt, hash(input, salt));
  }
}
