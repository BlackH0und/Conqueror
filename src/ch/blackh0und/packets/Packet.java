/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.blackh0und.packets;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blackh0und
 */
public abstract class Packet {
    
    private ArrayList<SocketAddress> targets = new ArrayList<SocketAddress>();

    public abstract void serialize(ByteBuffer buffer);

    public abstract void deserialize(ByteBuffer buffer);

    public ArrayList<SocketAddress> getTargets() {
        return targets;
    }
    
    public void clearTargets(){
        targets.clear();
    }
    
    public void addTarget(SocketAddress address) {
        targets.add(address);
    }

    public static String readString(ByteBuffer buffer) {
        byte[] data = new byte[buffer.getInt()];
        buffer.get(data);
        return new String(data);
    }
    
    public static void writeString(String val, ByteBuffer buffer) {
        byte[] data = val.getBytes();
        buffer.putInt(data.length);
        buffer.put(data);
    }
    
    public static ByteBuffer compilePacket(Packet packet, ByteBuffer buffer) {
        writeString(packet.getClass().getName(), buffer);
        packet.serialize(buffer);
        return buffer;
    }
    
    public static Packet createPacket(String className) {
        try {
            Class<Packet> packetClass = Class.forName(className).asSubclass((Class)Packet.class);
            return packetClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Packet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Packet decompilePacket(ByteBuffer buffer) {
        Packet packet = createPacket(readString(buffer));
        packet.deserialize(buffer);
        return packet;
    }

}
