package academia.utilitarios;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

public class Criptografia {

    /**
     * Chave de composicao para formacao de criptografia e descriptografia
     */
    private static final byte[] CHAVE_CRIPTOGRAFIA = "*01000011000100*".getBytes();

    /**
     * Algoritmos para criptografia
     */
    private static final String ALGORITMO_AES = "AES";
    private static final String ALGORITMO_AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";

    /**
     * Metodo que cifra um texto usando a CHAVE_CRIPTOGRAFIA no formato de
     * criptografia AES
     *
     * @param texto String texto a ser cifrado
     *
     * @return String com o texto cifrado
     *
     * @throws Exception Caso ocorra alguma excessao ao executar o metodo
     */
    public static String cifrar(String texto) {
        String retorno = null;
        try {
            SecretKeySpec spec = new SecretKeySpec(CHAVE_CRIPTOGRAFIA, ALGORITMO_AES);

            AlgorithmParameterSpec paramSpec = new IvParameterSpec(new byte[16]);

            Cipher cifra = Cipher.getInstance(ALGORITMO_AES_CBC_PKCS5PADDING);
            cifra.init(Cipher.ENCRYPT_MODE, spec, paramSpec);

            byte[] cifrado = cifra.doFinal(texto.getBytes());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cifrado.length; i++) {
                sb.append(Integer.toString((cifrado[i] & 0xff) + 0x100, 16).substring(1));
            }
            retorno = sb.toString();
        }
        catch (NoSuchPaddingException ex) {
            System.out.println(ex);
        }
        catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
        catch (InvalidKeyException ex) {
            System.out.println(ex);
        }
        catch (InvalidAlgorithmParameterException ex) {
            System.out.println(ex);
        }
        catch (IllegalBlockSizeException ex) {
            System.out.println(ex);
        }
        catch (BadPaddingException ex) {
            System.out.println(ex);
        }

        return retorno;
    }

    /**
     * Metodo que decifra um texto cifrado atraves do medoto cifrar
     *
     * @param cifra String com o texto cifrado
     * @return String com o texto decifrado
     * @throws Exception Caso ocorra algum erro durante a execussao do metodo
     */
    public static String decifrar(String cifra) {
        String retorno = null;

        if (cifra != null) {
            try {
                SecretKeySpec skeySpec = new SecretKeySpec(CHAVE_CRIPTOGRAFIA, ALGORITMO_AES);

                AlgorithmParameterSpec paramSpec = new IvParameterSpec(new byte[16]);

                int len = cifra.length();
                byte[] decoded = new byte[len / 2];
                for (int i = 0; i < len; i += 2) {
                    decoded[i / 2] = (byte) ((Character.digit(cifra.charAt(i), 16) << 4)
                            + Character.digit(cifra.charAt(i + 1), 16));
                }

                Cipher cipher = Cipher.getInstance(ALGORITMO_AES_CBC_PKCS5PADDING);
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, paramSpec);

                retorno = new String(cipher.doFinal(decoded));
            }
            catch (NoSuchPaddingException ex) {
                System.out.println(ex);
            }
            catch (NoSuchAlgorithmException ex) {
                System.out.println(ex);
            }
            catch (InvalidKeyException ex) {
                System.out.println(ex);
            }
            catch (InvalidAlgorithmParameterException ex) {
                System.out.println(ex);
            }
            catch (IllegalBlockSizeException ex) {
                System.out.println(ex);
            }
            catch (BadPaddingException ex) {
                System.out.println(ex);
            }
        }

        return retorno;
    }
}
