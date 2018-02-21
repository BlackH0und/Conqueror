/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.blackh0und;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;

/**
 *
 * @author 5im16maschifferle
 */
public class Field extends Group{
    
    private Polygon hex;
    private Label troops;
    private int troopCount;
    private Player player;
    private int row, column;

    public Field(Polygon hex, Label troops, int troopCount, Player player, int row, int column) {
        this.hex = hex;
        this.troops = troops;
        this.troopCount = troopCount;
        this.player = player;
        this.row = row;
        this.column = column;
        
        this.getChildren().add(hex);
        this.getChildren().add(troops);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Polygon getHex() {
        return hex;
    }

    public void setHex(Polygon hex) {
        this.hex = hex;
    }

    public int getTroopCount() {
        return troopCount;
    }

    public void setTroopCount(int troopCount) {
        this.troopCount = troopCount;
    }

    public Label getTroops() {
        return troops;
    }

    public void setTroops(Label troops) {
        this.troops = troops;
    }
    
}
