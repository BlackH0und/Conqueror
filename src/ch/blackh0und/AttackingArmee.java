/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.blackh0und;

import ch.blackh0und.Field;

/**
 *
 * @author 5im16maschifferle
 */
public class AttackingArmee {
    private int troops;
    private Field from;
    private Field to;

    public AttackingArmee() {
    }

    public AttackingArmee(int troops, Field from, Field to) {
        this.troops = troops;
        this.from = from;
        this.to = to;
    }

    public Field getFrom() {
        return from;
    }

    public Field getTo() {
        return to;
    }

    public int getTroops() {
        return troops;
    }

    public void setTo(Field to) {
        this.to = to;
    }
    
    

    
}
