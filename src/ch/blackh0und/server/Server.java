/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.blackh0und.server;

import ch.blackh0und.packets.Packet;
import ch.blackh0und.packets.PacketSeed;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blackh0und
 */
public class Server implements Runnable {

    private ServerSocketChannel channel;
    private ArrayList<Packet> queue;

    private HashMap<SocketAddress, SocketChannel> clients;

    private int port;
    private boolean running = false;

    private HandlePacket handlePacket;

    public Server(int port) {
        this.port = port;
        running = true;
        handlePacket = new HandlePacket(this);
        clients = new HashMap<>();
        queue = new ArrayList<>();
        try {
            channel = ServerSocketChannel.open();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HashMap<SocketAddress, SocketChannel> getClients() {
        return clients;
    }

    public void queuePacket(Packet packet) {
        queue.add(packet);
    }

    @Override
    public void run() {
        try {
            channel.bind(new InetSocketAddress(InetAddress.getLocalHost(), port));
            channel.configureBlocking(false);

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);

            ByteBuffer readBuffer = ByteBuffer.allocate(2048);
            ByteBuffer writeBuffer = ByteBuffer.allocate(2048);

            while (running) {
                if (selector.selectNow() > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            try {
                                System.out.println("User Found");

                                SocketChannel sChannel = channel.accept();
                                SocketAddress sender = sChannel.getRemoteAddress();

                                sChannel.configureBlocking(false);

                                sChannel.register(selector, SelectionKey.OP_READ);

                                //Seed versenden
                                PacketSeed ps = new PacketSeed(port, port);
                                
                                clients.put(sChannel.getRemoteAddress(), sChannel);
                            } catch (ClosedChannelException ex) {
                                System.out.println("User disconnected");
                            }
                        }
                        if (key.isReadable()) {
                            SocketChannel sChannel = (SocketChannel) key.channel();

                            readBuffer.position(0);
                            readBuffer.limit(readBuffer.capacity());
                            sChannel.read(readBuffer);
                            readBuffer.flip();

                            Packet packet = Packet.decompilePacket(readBuffer);
                            packet.clearTargets();
                            for(SocketAddress a : clients.keySet()){
                                if(!a.equals(clients.get(sChannel))){
                                    packet.addTarget(a);
                                }
                            }
                            queuePacket(packet);

                        }
                        iterator.remove();
                    }
                }

                if (!queue.isEmpty()) {
                    Iterator<Packet> packetIterator = queue.iterator();
                    while (packetIterator.hasNext()) {
                        Packet packet = packetIterator.next();
                        writeBuffer.position(0);
                        writeBuffer.limit(writeBuffer.capacity());
                        Packet.compilePacket(packet, writeBuffer);
                        writeBuffer.flip();

                        Iterator<SocketAddress> targetIterator = packet.getTargets().iterator();

                        while (targetIterator.hasNext()) {
                            SocketAddress target = targetIterator.next();
                            System.out.println(target);
                            clients.get(target).write(writeBuffer);
                            writeBuffer.flip();
                        }

                        packetIterator.remove();
                    }
                }
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("User disconnected");
        }

    }

}
