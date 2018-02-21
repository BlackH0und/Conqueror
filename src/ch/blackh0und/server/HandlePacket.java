/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.blackh0und.server;

import ch.blackh0und.packets.Packet;
import java.net.SocketAddress;

/**
 *
 * @author blackh0und
 */
public class HandlePacket {
    
    private Server server;

    public HandlePacket() {
    }

    public HandlePacket(Server server) {
        this.server = server;
    }

    public void handlePacket(SocketAddress address, Packet packet){
        
    }

}
