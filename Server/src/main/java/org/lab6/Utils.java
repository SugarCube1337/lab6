package org.lab6;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

public class Utils {

    public static String escapeString(String originalString) {
        return originalString.replaceAll("\\\\", "\\\\u005C").replaceAll("\"", "\\\\u0022").replaceAll(",", "\\\\u002C");
    }

    public static String unescapeString(String escapedString) {
        return escapedString.replaceAll("u0022", "\"").replaceAll("u002C", ",").replaceAll("u005C", "\\\\");
    }

    public static byte[] intToBytes(final int i) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
    }

    public static byte[] serializeObject(Object o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        byte[] result = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.flush();
            result = bos.toByteArray();
        } catch (IOException ex) {
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
            }
        }
        return result;
    }

    public static Object deserializeObject(byte[] b) {
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        ObjectInput in = null;
        Object result = null;
        try {
            in = new ObjectInputStream(bis);
            result = in.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static byte[] joinByteArrays(List<byte[]> chunks) {
        int totalLength = 0;
        for (byte[] chunk : chunks) {
            totalLength += chunk.length;
        }

        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] chunk : chunks) {
            System.arraycopy(chunk, 0, result, offset, chunk.length);
            offset += chunk.length;
        }

        return result;
    }

    public static byte checkFirst4Bytes(byte[] arr) {
        if (arr.length < 4) return (byte) 255;
        if (arr[0] == arr[1] && arr[1] == arr[2] && arr[2] == arr[3])
            return arr[0];
        return (byte) 255;
    }

    public static int fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
}
