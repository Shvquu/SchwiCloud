package eu.schwicloud.networking.packet;


import io.netty.channel.Channel;

public interface NettyAdaptor {
    void handle(Channel channel, Packet packet);
}
