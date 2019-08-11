package space.wgao.simpleapi.utils;

import java.nio.ByteBuffer;

/**
 * SimpleApi
 *
 * @author w.gao Copyright (c) 2018.05
 * @version 1.0
 */
public class Binary {

    public static int readShort(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 8) + (bytes[1] & 0xFF);
    }

    public static byte[] writeShort(int s) {
        return new byte[]{
                (byte) ((s >>> 8) & 0xFF),
                (byte) (s & 0xFF)
        };
    }

    public static int readInt(byte[] bytes) {
        return ((bytes[0] & 0xff) << 24) +
                ((bytes[1] & 0xff) << 16) +
                ((bytes[2] & 0xff) << 8) +
                (bytes[3] & 0xff);
    }

    public static byte[] writeInt(int i) {
        return new byte[]{
                (byte) ((i >>> 24) & 0xFF),
                (byte) ((i >>> 16) & 0xFF),
                (byte) ((i >>> 8) & 0xFF),
                (byte) (i & 0xFF)
        };
    }

    public static float readFloat(byte[] bytes) {
        return readInt((bytes)) / 300;
    }

    public static byte[] writeFloat(float f) {
        return writeInt((int) (f * 300));
    }

    public static double readDouble(byte[] bytes) {
        return readInt(bytes) / 1e6;
    }

    public static byte[] writeDouble(double d) {
        return writeInt((int) (d * 1e6));
    }

    public static byte[] appendBytes(byte[][] bytes) {
        int length = 0;
        for (byte[] b : bytes) {
            length += b.length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(length);
        for (byte[] b : bytes) {
            buffer.put(b);
        }
        return buffer.array();
    }

    public static byte[] appendBytes(byte byte1, byte[]... bytes2) {
        int length = 1;
        for (byte[] bytes : bytes2) {
            length += bytes.length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.put(byte1);
        for (byte[] bytes : bytes2) {
            buffer.put(bytes);
        }
        return buffer.array();
    }

    public static byte[] appendBytes(byte[] bytes1, byte[]... bytes2) {
        int length = bytes1.length;
        for (byte[] bytes : bytes2) {
            length += bytes.length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.put(bytes1);
        for (byte[] bytes : bytes2) {
            buffer.put(bytes);
        }
        return buffer.array();
    }

    public static String bytesToHexString(byte[] src) {
        return bytesToHexString(src, false);
    }

    public static String bytesToHexString(byte[] src, boolean blank) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }

        for (byte b : src) {
            if (!(stringBuilder.length() == 0) && blank) {
                stringBuilder.append(" ");
            }
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

}
