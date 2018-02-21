/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.blackh0und.server;

import ch.blackh0und.packets.Packet;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 5im16maschifferle
 */
public class Client implements Runnable {

    private SocketAddress serverAddress;
    private ArrayList<Packet> queue;

    private HandlePacket handlePacket;

    public Client(SocketAddress serverAddress) {
        this.serverAddress = serverAddress;
        queue = new ArrayList<>();
        handlePacket = new HandlePacket();
    }

    public void queuePacket(Packet packet) {
        queue.add(packet);
    }

    public SocketAddress getServerAddress() {
        return serverAddress;
    }
    
    

    @Override
    public void run() {
        try {
            SocketChannel channel = SocketChannel.open();
            channel.configureBlocking(false);

            channel.connect(serverAddress);

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);

            ByteBuffer readBuffer = ByteBuffer.allocate(4096);
            ByteBuffer writeBuffer = ByteBuffer.allocate(4096);

            while (true) {
                if (selector.selectNow() > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        

                        if (key.isConnectable()) {
                            channel.finishConnect();
                        } else if (key.isReadable()) {
                            // TODO read packet
                            SocketChannel sChannel = (SocketChannel) key.channel();
                            
                            readBuffer.position(0);
                            readBuffer.limit(readBuffer.capacity());
                            sChannel.read(readBuffer);
                            readBuffer.flip();

                            Packet packet = Packet.decompilePacket(readBuffer);
                            handlePacket.handlePacket(serverAddress, packet);
                        }
                        iterator.remove();
                    }
                }

                if (!queue.isEmpty()) {
                    // TODO send packets
                    Iterator<Packet> packetIterator = queue.iterator();
                    while (packetIterator.hasNext()) {
                        Packet packet = packetIterator.next();
                        writeBuffer.position(0).limit(writeBuffer.capacity());
                        
                        Packet.compilePacket(packet, writeBuffer);
                        writeBuffer.flip();

                        channel.write(writeBuffer);

                        packetIterator.remove();
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
