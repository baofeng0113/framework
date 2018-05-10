package cloud.dispatcher.base.framework.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class CodecUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private CodecUtil() {}

    public static final class Base64 {

        private static final java.util.Base64.Encoder ENCODER = java.util.Base64.getEncoder();
        private static final java.util.Base64.Decoder DECODER = java.util.Base64.getDecoder();

        private Base64() {}

        public static String encode(byte[] bytes) {
            if (Objects.isNull(bytes) || bytes.length == 0) {
                return StringUtils.EMPTY;
            } else {
                return ENCODER.encodeToString(bytes);
            }
        }

        public static String encode(String value) {
            if (StringUtils.isBlank(value)) {
                return StringUtils.EMPTY;
            } else {
                return encode(value.getBytes());
            }
        }

        public static byte[] decode(String value) {
            if (StringUtils.isBlank(value)) {
                return new byte[]{};
            }
            return DECODER.decode(value);
        }
    }

    public static final class MD5 {

        private MD5() {}

        public static String encode(byte[] bytes) {
            if (Objects.isNull(bytes) || bytes.length == 0) {
                return StringUtils.EMPTY;
            } else {
                return DigestUtils.md5Hex(bytes);
            }
        }

        public static String encode(String value) {
            if (StringUtils.isBlank(value)) {
                return StringUtils.EMPTY;
            } else {
                return encode(value.getBytes());
            }
        }
    }

    public static final class SHA {

        private SHA() {}

        public static String encode(byte[] bytes) {
            if (Objects.isNull(bytes) || bytes.length == 0) {
                return StringUtils.EMPTY;
            } else {
                return DigestUtils.shaHex(bytes);
            }
        }

        public static String encode(String value) {
            if (StringUtils.isBlank(value)) {
                return StringUtils.EMPTY;
            } else {
                return encode(value.getBytes());
            }
        }
    }

    public static final class AES {

        private static final String CIPHER_INSTANCE_CONFIG = "AES/CBC/PKCS5Padding";

        private AES() {}

        private static SecretKey createKey(String key) {
            try {
                return new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance(
                        "SHA-1").digest(key.getBytes("UTF-8")), 16), "AES");
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        public static byte[] encrypt(byte[] value, String key) {
            try {
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, createKey(key));
                return cipher.doFinal(value);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (NoSuchPaddingException e) {
                throw new RuntimeException(e);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        }

        public static byte[] decrypt(byte[] value, String key) {
            try {
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, createKey(key));
                return cipher.doFinal(value);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (NoSuchPaddingException e) {
                throw new RuntimeException(e);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static final class RSA {

        private RSA() {}

        public static PrivateKey getPrivateKey(String key) {
            try {
                return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(
                        Base64.decode(key)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static String encrypt(String value, String key) {
            try {
                PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                        Base64.decode(key));
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);

                Signature signature = Signature.getInstance("SHA1WithRSA");

                signature.initSign(privateKey);
                signature.update(value.getBytes());

                return Base64.encode(signature.sign());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static String decrypt(String value, String key) {
            try {
                PrivateKey privateKey = getPrivateKey(key);
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);

                InputStream stream = new ByteArrayInputStream(Base64
                        .decode(value));
                ByteArrayOutputStream writer = new ByteArrayOutputStream();
                byte[] buffer = new byte[128];
                int size;

                while ((size = stream.read(buffer)) != -1) {
                    byte[] block = null;
                    if (buffer.length == size) {
                        block = buffer;
                    } else {
                        block = new byte[size];
                        for (int i = 0; i < size; i++) {
                            block[i] = buffer[i];
                        }
                    }
                    writer.write(cipher.doFinal(block));
                }
                return new String(writer.toByteArray());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static final class JSON {

        private static final ObjectMapper mapper = new ObjectMapper();

        private JSON() {}

        public static final String DEFAULT_DATETIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

        static {
            mapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATETIME_FORMAT_YYYY_MM_DD_HH_MM_SS));
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        private static JavaType getCollectionType(Class<?> collectionClazz,
                                                  Class<?>... elementClazz) {
            return mapper.getTypeFactory().constructParametricType(
                    collectionClazz, elementClazz);
        }

        public static String encode(Object o) {
            try {
                return mapper.writeValueAsString(o);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static JsonNode readTree(String json) {
            try {
                return mapper.readTree(json);
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        }

        @SuppressWarnings("unchecked")
        public static <T> Collection<T> collectionValue(String json, Class<T> clazz) {
            JavaType javaType = getCollectionType(Collection.class, clazz);
            try {
                return (Collection<T>) mapper.readValue(json, javaType);
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        }

        public static <T> T decode(String json, TypeReference type) {
            try {
                return mapper.readValue(json, type);
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        }

        public static <T> T decode(String json, Class<T> clazz) {
            try {
                return mapper.readValue(json, clazz);
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        }

        public static <T> T treeToValue(JsonNode jsonNode, Class<T> clazz) {
            try {
                return mapper.treeToValue(jsonNode, clazz);
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        }

        public static <T> T convertValue(Object object, TypeReference type) {
            try {
                return mapper.convertValue(object, type);
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        }

        public static <T> T convertValue(Object object, Class<T> clazz) {
            try {
                return mapper.convertValue(object, clazz);
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        }
    }
}
