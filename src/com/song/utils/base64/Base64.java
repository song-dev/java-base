package com.song.utils.base64;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * This class consists exclusively of static methods for obtaining
 * encoders and decoders for the Base64 encoding scheme. The
 * implementation of this class supports the following types of Base64
 * as specified in
 * <a href="http://www.ietf.org/rfc/rfc4648.txt">RFC 4648</a> and
 * <a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a>.
 *
 * <ul>
 * <li><a name="basic"><b>Basic</b></a>
 * <p> Uses "The Base64 Alphabet" as specified in Table 1 of
 *     RFC 4648 and RFC 2045 for encoding and decoding operation.
 *     The encoder does not add any line feed (line separator)
 *     character. The decoder rejects data that contains characters
 *     outside the base64 alphabet.</p></li>
 *
 * <li><a name="url"><b>URL and Filename safe</b></a>
 * <p> Uses the "URL and Filename safe Base64 Alphabet" as specified
 *     in Table 2 of RFC 4648 for encoding and decoding. The
 *     encoder does not add any line feed (line separator) character.
 *     The decoder rejects data that contains characters outside the
 *     base64 alphabet.</p></li>
 *
 * <li><a name="mime"><b>MIME</b></a>
 * <p> Uses the "The Base64 Alphabet" as specified in Table 1 of
 *     RFC 2045 for encoding and decoding operation. The encoded output
 *     must be represented in lines of no more than 76 characters each
 *     and uses a carriage return {@code '\r'} followed immediately by
 *     a linefeed {@code '\n'} as the line separator. No line separator
 *     is added to the end of the encoded output. All line separators
 *     or other characters not found in the base64 alphabet table are
 *     ignored in decoding operation.</p></li>
 * </ul>
 *
 * <p> Unless otherwise noted, passing a {@code null} argument to a
 * method of this class will cause a {@link java.lang.NullPointerException
 * NullPointerException} to be thrown.
 *
 * @author Xueming Shen
 * @since 1.8
 */

public class Base64 {

    private Base64() {
    }

    public static String encode(byte[] src) {
        return new String(getEncoder().encode(src), StandardCharsets.UTF_8);
    }

    public static byte[] decode(byte[] src) {
        return getDecoder().decode(src);
    }

    public static byte[] decode(String src) {
        return getDecoder().decode(src.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Returns a {@link Encoder} that encodes using the
     * <a href="#basic">Basic</a> type base64 encoding scheme.
     *
     * @return A Base64 encoder.
     */
    private static Encoder getEncoder() {
        return Encoder.RFC4648;
    }

    /**
     * Returns a {@link Decoder} that decodes using the
     * <a href="#basic">Basic</a> type base64 encoding scheme.
     *
     * @return A Base64 decoder.
     */
    private static Decoder getDecoder() {
        return Decoder.RFC4648;
    }

    /**
     * This class implements an encoder for encoding byte data using
     * the Base64 encoding scheme as specified in RFC 4648 and RFC 2045.
     *
     * <p> Instances of {@link Encoder} class are safe for use by
     * multiple concurrent threads.
     *
     * <p> Unless otherwise noted, passing a {@code null} argument to
     * a method of this class will cause a
     * {@link java.lang.NullPointerException NullPointerException} to
     * be thrown.
     *
     * @see Decoder
     * @since 1.8
     */
    public static class Encoder {

        private final boolean doPadding;

        private Encoder(boolean doPadding) {
            this.doPadding = doPadding;
        }

        /**
         * This array is a lookup table that translates 6-bit positive integer
         * index values into their "Base64 Alphabet" equivalents as specified
         * in "Table 1: The Base64 Alphabet" of RFC 2045 (and RFC 4648).
         */
        private static final char[] toBase64 = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
        };

        static final Encoder RFC4648 = new Encoder(true);

        private final int outLength(int srclen) {
            int len = 0;
            if (doPadding) {
                len = 4 * ((srclen + 2) / 3);
            } else {
                int n = srclen % 3;
                len = 4 * (srclen / 3) + (n == 0 ? 0 : n + 1);
            }
            return len;
        }

        /**
         * Encodes all bytes from the specified byte array into a newly-allocated
         * byte array using the {@link Base64} encoding scheme. The returned byte
         * array is of the length of the resulting bytes.
         *
         * @param src the byte array to encode
         * @return A newly-allocated byte array containing the resulting
         * encoded bytes.
         */
        public byte[] encode(byte[] src) {
            int len = outLength(src.length);          // dst array size
            byte[] dst = new byte[len];
            int ret = encode0(src, 0, src.length, dst);
            if (ret != dst.length)
                return Arrays.copyOf(dst, ret);
            return dst;
        }

        /**
         * Encodes all bytes from the specified byte array using the
         * {@link Base64} encoding scheme, writing the resulting bytes to the
         * given output byte array, starting at offset 0.
         *
         * <p> It is the responsibility of the invoker of this method to make
         * sure the output byte array {@code dst} has enough space for encoding
         * all bytes from the input byte array. No bytes will be written to the
         * output byte array if the output byte array is not big enough.
         *
         * @param src the byte array to encode
         * @param dst the output byte array
         * @return The number of bytes written to the output byte array
         * @throws IllegalArgumentException if {@code dst} does not have enough
         *                                  space for encoding all input bytes.
         */
        public int encode(byte[] src, byte[] dst) {
            int len = outLength(src.length);         // dst array size
            if (dst.length < len)
                throw new IllegalArgumentException(
                        "Output byte array is too small for encoding all input bytes");
            return encode0(src, 0, src.length, dst);
        }

        /**
         * Encodes the specified byte array into a String using the {@link Base64}
         * encoding scheme.
         *
         * <p> This method first encodes all input bytes into a base64 encoded
         * byte array and then constructs a new String by using the encoded byte
         * array and the {@link java.nio.charset.StandardCharsets#ISO_8859_1
         * ISO-8859-1} charset.
         *
         * <p> In other words, an invocation of this method has exactly the same
         * effect as invoking
         * {@code new String(encode(src), StandardCharsets.ISO_8859_1)}.
         *
         * @param src the byte array to encode
         * @return A String containing the resulting Base64 encoded characters
         */
        @SuppressWarnings("deprecation")
        public String encodeToString(byte[] src) {
            byte[] encoded = encode(src);
            return new String(encoded, 0, 0, encoded.length);
        }

        /**
         * Returns an encoder instance that encodes equivalently to this one,
         * but without adding any padding character at the end of the encoded
         * byte data.
         *
         * <p> The encoding scheme of this encoder instance is unaffected by
         * this invocation. The returned encoder instance should be used for
         * non-padding encoding operation.
         *
         * @return an equivalent encoder that encodes without adding any
         * padding character at the end
         */
        public Encoder withoutPadding() {
            if (!doPadding)
                return this;
            return new Encoder(false);
        }

        private int encode0(byte[] src, int off, int end, byte[] dst) {
            char[] base64 = toBase64;
            int sp = off;
            int slen = (end - off) / 3 * 3;
            int sl = off + slen;
            int dp = 0;
            while (sp < sl) {
                int sl0 = Math.min(sp + slen, sl);
                for (int sp0 = sp, dp0 = dp; sp0 < sl0; ) {
                    int bits = (src[sp0++] & 0xff) << 16 |
                            (src[sp0++] & 0xff) << 8 |
                            (src[sp0++] & 0xff);
                    dst[dp0++] = (byte) base64[(bits >>> 18) & 0x3f];
                    dst[dp0++] = (byte) base64[(bits >>> 12) & 0x3f];
                    dst[dp0++] = (byte) base64[(bits >>> 6) & 0x3f];
                    dst[dp0++] = (byte) base64[bits & 0x3f];
                }
                int dlen = (sl0 - sp) / 3 * 4;
                dp += dlen;
                sp = sl0;
            }
            if (sp < end) {               // 1 or 2 leftover bytes
                int b0 = src[sp++] & 0xff;
                dst[dp++] = (byte) base64[b0 >> 2];
                if (sp == end) {
                    dst[dp++] = (byte) base64[(b0 << 4) & 0x3f];
                    if (doPadding) {
                        dst[dp++] = '=';
                        dst[dp++] = '=';
                    }
                } else {
                    int b1 = src[sp++] & 0xff;
                    dst[dp++] = (byte) base64[(b0 << 4) & 0x3f | (b1 >> 4)];
                    dst[dp++] = (byte) base64[(b1 << 2) & 0x3f];
                    if (doPadding) {
                        dst[dp++] = '=';
                    }
                }
            }
            return dp;
        }
    }

    /**
     * This class implements a decoder for decoding byte data using the
     * Base64 encoding scheme as specified in RFC 4648 and RFC 2045.
     *
     * <p> The Base64 padding character {@code '='} is accepted and
     * interpreted as the end of the encoded byte data, but is not
     * required. So if the final unit of the encoded byte data only has
     * two or three Base64 characters (without the corresponding padding
     * character(s) padded), they are decoded as if followed by padding
     * character(s). If there is a padding character present in the
     * final unit, the correct number of padding character(s) must be
     * present, otherwise {@code IllegalArgumentException} (
     * {@code IOException} when reading from a Base64 stream) is thrown
     * during decoding.
     *
     * <p> Instances of {@link Decoder} class are safe for use by
     * multiple concurrent threads.
     *
     * <p> Unless otherwise noted, passing a {@code null} argument to
     * a method of this class will cause a
     * {@link java.lang.NullPointerException NullPointerException} to
     * be thrown.
     *
     * @see Encoder
     * @since 1.8
     */
    public static class Decoder {

        private Decoder() {
        }

        /**
         * Lookup table for decoding unicode characters drawn from the
         * "Base64 Alphabet" (as specified in Table 1 of RFC 2045) into
         * their 6-bit positive integer equivalents.  Characters that
         * are not in the Base64 alphabet but fall within the bounds of
         * the array are encoded to -1.
         */
        private static final int[] fromBase64 = new int[256];

        static {
            Arrays.fill(fromBase64, -1);
            for (int i = 0; i < Encoder.toBase64.length; i++)
                fromBase64[Encoder.toBase64[i]] = i;
            fromBase64['='] = -2;
        }

        static final Decoder RFC4648 = new Decoder();

        /**
         * Decodes all bytes from the input byte array using the {@link Base64}
         * encoding scheme, writing the results into a newly-allocated output
         * byte array. The returned byte array is of the length of the resulting
         * bytes.
         *
         * @param src the byte array to decode
         * @return A newly-allocated byte array containing the decoded bytes.
         * @throws IllegalArgumentException if {@code src} is not in valid Base64 scheme
         */
        public byte[] decode(byte[] src) {
            byte[] dst = new byte[outLength(src, 0, src.length)];
            int ret = decode0(src, 0, src.length, dst);
            if (ret != dst.length) {
                dst = Arrays.copyOf(dst, ret);
            }
            return dst;
        }

        /**
         * Decodes a Base64 encoded String into a newly-allocated byte array
         * using the {@link Base64} encoding scheme.
         *
         * <p> An invocation of this method has exactly the same effect as invoking
         * {@code decode(src.getBytes(StandardCharsets.ISO_8859_1))}
         *
         * @param src the string to decode
         * @return A newly-allocated byte array containing the decoded bytes.
         * @throws IllegalArgumentException if {@code src} is not in valid Base64 scheme
         */
        public byte[] decode(String src) {
            return decode(src.getBytes(StandardCharsets.ISO_8859_1));
        }

        /**
         * Decodes all bytes from the input byte array using the {@link Base64}
         * encoding scheme, writing the results into the given output byte array,
         * starting at offset 0.
         *
         * <p> It is the responsibility of the invoker of this method to make
         * sure the output byte array {@code dst} has enough space for decoding
         * all bytes from the input byte array. No bytes will be be written to
         * the output byte array if the output byte array is not big enough.
         *
         * <p> If the input byte array is not in valid Base64 encoding scheme
         * then some bytes may have been written to the output byte array before
         * IllegalargumentException is thrown.
         *
         * @param src the byte array to decode
         * @param dst the output byte array
         * @return The number of bytes written to the output byte array
         * @throws IllegalArgumentException if {@code src} is not in valid Base64 scheme, or {@code dst}
         *                                  does not have enough space for decoding all input bytes.
         */
        public int decode(byte[] src, byte[] dst) {
            int len = outLength(src, 0, src.length);
            if (dst.length < len)
                throw new IllegalArgumentException(
                        "Output byte array is too small for decoding all input bytes");
            return decode0(src, 0, src.length, dst);
        }

        private int outLength(byte[] src, int sp, int sl) {
            int[] base64 = fromBase64;
            int paddings = 0;
            int len = sl - sp;
            if (len == 0)
                return 0;
            if (len < 2) {
                throw new IllegalArgumentException(
                        "Input byte[] should at least have 2 bytes for base64 bytes");
            }
            if (src[sl - 1] == '=') {
                paddings++;
                if (src[sl - 2] == '=')
                    paddings++;
            }
            if (paddings == 0 && (len & 0x3) != 0)
                paddings = 4 - (len & 0x3);
            return 3 * ((len + 3) / 4) - paddings;
        }

        private int decode0(byte[] src, int sp, int sl, byte[] dst) {
            int[] base64 = fromBase64;
            int dp = 0;
            int bits = 0;
            int shiftto = 18;       // pos of first byte of 4-byte atom
            while (sp < sl) {
                int b = src[sp++] & 0xff;
                if ((b = base64[b]) < 0) {
                    if (b == -2) {         // padding byte '='
                        // =     shiftto==18 unnecessary padding
                        // x=    shiftto==12 a dangling single x
                        // x     to be handled together with non-padding case
                        // xx=   shiftto==6&&sp==sl missing last =
                        // xx=y  shiftto==6 last is not =
                        if (shiftto == 6 && (sp == sl || src[sp++] != '=') ||
                                shiftto == 18) {
                            throw new IllegalArgumentException(
                                    "Input byte array has wrong 4-byte ending unit");
                        }
                        break;
                    }
                    throw new IllegalArgumentException(
                            "Illegal base64 character " +
                                    Integer.toString(src[sp - 1], 16));
                }
                bits |= (b << shiftto);
                shiftto -= 6;
                if (shiftto < 0) {
                    dst[dp++] = (byte) (bits >> 16);
                    dst[dp++] = (byte) (bits >> 8);
                    dst[dp++] = (byte) (bits);
                    shiftto = 18;
                    bits = 0;
                }
            }
            // reached end of byte array or hit padding '=' characters.
            if (shiftto == 6) {
                dst[dp++] = (byte) (bits >> 16);
            } else if (shiftto == 0) {
                dst[dp++] = (byte) (bits >> 16);
                dst[dp++] = (byte) (bits >> 8);
            } else if (shiftto == 12) {
                // dangling single "x", incorrectly encoded.
                throw new IllegalArgumentException(
                        "Last unit does not have enough valid bits");
            }
            // anything left is invalid, if is not MIME.
            // if MIME, ignore all non-base64 character
            while (sp < sl) {
                throw new IllegalArgumentException(
                        "Input byte array has incorrect ending byte at " + sp);
            }
            return dp;
        }
    }

}
