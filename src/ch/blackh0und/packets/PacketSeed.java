/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.blackh0und.packets;

import java.nio.ByteBuffer;

/**
 *
 * @author 5im16maschifferle
 */
public class PacketSeed extends Packet{

    private double seedOne;
    private double seedTwo;

    public PacketSeed() {
    }

    public PacketSeed(double seedOne, double seedTwo) {
        this.seedOne = seedOne;
        this.seedTwo = seedTwo;
    }

    public double getSeedOne() {
        return seedOne;
    }

    public double getSeedTwo() {
        return seedTwo;
    }
    
    @Override
    public void serialize(ByteBuffer buffer) {
        buffer.putDouble(seedOne);
        buffer.putDouble(seedTwo);
    }

    @Override
    public void deserialize(ByteBuffer buffer) {
        seedOne = buffer.getDouble();
        seedTwo = buffer.getDouble();
    }
    
}
