package eu.schwicloud.networking;

import eu.schwicloud.networking.client.NettyClient;
import eu.schwicloud.networking.packet.PacketDriver;
import eu.schwicloud.networking.server.NettyServer;

import java.util.ArrayList;

public class NettyDriver {
    private static NettyDriver instance;

    public NettyServer nettyServer;

    private ArrayList<String> whitelist;

    public NettyClient nettyClient;
    private PacketDriver packetDriver;

    public NettyDriver() {
        instance = this;
        packetDriver = new PacketDriver();
        whitelist = new ArrayList<>();
    }

    public static NettyDriver getInstance() {
        return instance;
    }

    public ArrayList<String> getWhitelist() {
        return whitelist;
    }

    public PacketDriver getPacketDriver() {
        return packetDriver;
    }


}
