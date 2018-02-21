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

    private int seedOne;
    private int seedTwo;

    public PacketSeed() {
    }

    public PacketSeed(int seedOne, int seedTwo) {
        this.seedOne = seedOne;
        this.seedTwo = seedTwo;
    }

    public int getSeedOne() {
        return seedOne;
    }

    public int getSeedTwo() {
        return seedTwo;
    }
    
    @Override
    public void serialize(ByteBuffer buffer) {
        buffer.putInt(seedOne);
        buffer.putInt(seedTwo);
    }

    @Override
    public void deserialize(ByteBuffer buffer) {
        seedOne = buffer.getInt();
        seedTwo = buffer.getInt();
    }
    
}
