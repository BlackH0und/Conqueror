/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.blackh0und;

import java.net.SocketAddress;
import javafx.scene.paint.Color;

/**
 *
 * @author blackh0und
 */
public class Player {
    
    private String username;
    private Color color;
    private SocketAddress address;

    public Player(String username, Color color, SocketAddress address) {
        this.username = username;
        this.color = color;
        this.address = address;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }

    public Color getColor() {
        return color;
    }

    public String getUsername() {
        return username;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
