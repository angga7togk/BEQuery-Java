package angga7togk.bequery;

import lombok.Data;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;


@Data
public class BEQuery {
    private String gameName;
    private String hostName;
    private String protocol;
    private String version;
    private String players;
    private String maxPlayers;
    private String serverId;
    private String map;
    private String gameMode;
    private String nintendoLimited;
    private String ipv4Port;
    private String ipv6Port;
    private String extra;

    public static BEQuery connect(String host, int port, int timeout) throws BEQueryException {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(timeout);
            InetAddress address = InetAddress.getByName(host);

            byte[] buffer = createQueryPacket();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);

            byte[] receiveData = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            return parseResponse(receivePacket.getData());
        } catch (IOException e) {
            throw new BEQueryException("Failed to query server: " + e.getMessage(), e);
        }
    }

    public static BEQuery connect(String host, int port) throws BEQueryException{
        return connect(host, port, 4000);
    }

    public static BEQuery connect(String host) throws BEQueryException{
        return connect(host, 19132);
    }

    private static byte[] createQueryPacket() {
        byte[] OFFLINE_MESSAGE_DATA_ID = {
                0x00, (byte) 0xFF, (byte) 0xFF, 0x00, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE,
                (byte) 0xFD, (byte) 0xFD, (byte) 0xFD, (byte) 0xFD, 0x12, 0x34, 0x56, 0x78
        };

        ByteBuffer buffer = ByteBuffer.allocate(41);
        buffer.put((byte) 0x01);
        buffer.putLong(System.currentTimeMillis() / 1000); // 64-bit current time
        buffer.put(OFFLINE_MESSAGE_DATA_ID);
        buffer.putLong(2); // 64-bit guid

        return buffer.array();
    }

    private static BEQuery parseResponse(byte[] data) throws BEQueryException {
        if (data.length < 35) {
            throw new BEQueryException("Invalid response data");
        }

        if (data[0] != 0x1C) {
            throw new BEQueryException("First byte is not ID_UNCONNECTED_PONG");
        }

        byte[] OFFLINE_MESSAGE_DATA_ID = Arrays.copyOfRange(data, 17, 33);
        if (!Arrays.equals(OFFLINE_MESSAGE_DATA_ID, new byte[]{
                0x00, (byte) 0xFF, (byte) 0xFF, 0x00, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE,
                (byte) 0xFD, (byte) 0xFD, (byte) 0xFD, (byte) 0xFD, 0x12, 0x34, 0x56, 0x78
        })) {
            throw new BEQueryException("Magic bytes do not match");
        }

        String[] parts = new String(data, 35, data.length - 35).split(";");

        BEQuery model = new BEQuery();
        model.setGameName(parts.length > 0 ? parts[0] : null);
        model.setHostName(parts.length > 1 ? parts[1] : null);
        model.setProtocol(parts.length > 2 ? parts[2] : null);
        model.setVersion(parts.length > 3 ? parts[3] : null);
        model.setPlayers(parts.length > 4 ? parts[4] : null);
        model.setMaxPlayers(parts.length > 5 ? parts[5] : null);
        model.setServerId(parts.length > 6 ? parts[6] : null);
        model.setMap(parts.length > 7 ? parts[7] : null);
        model.setGameMode(parts.length > 8 ? parts[8] : null);
        model.setNintendoLimited(parts.length > 9 ? parts[9] : null);
        model.setIpv4Port(parts.length > 10 ? parts[10] : null);
        model.setIpv6Port(parts.length > 11 ? parts[11] : null);
        model.setExtra(parts.length > 12 ? parts[12] : null);

        return model;
    }
}