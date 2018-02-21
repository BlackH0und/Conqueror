/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.blackh0und;

import ch.blackh0und.server.Client;
import ch.blackh0und.server.Server;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.SocketAddress;
import java.util.Random;

/**
 *
 * @author marc3
 */
public class Conqueror extends Application {

    private boolean isserver = true;
    private double seedOne, seedTwo;

    private SocketAddress serverAddress;
    
    //endField for AttackingTroops needs to be selected
    @Override
    public void start(Stage primaryStage) {
        Random r = new Random();
        seedOne = r.nextDouble();
        seedTwo = r.nextDouble();
        Button start = new Button("Start");
        HBox h = new HBox(start);
        Scene scene = new Scene(h);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Server s = new Server(6725);
        s.setSeeds(seedOne, seedTwo);
        Client c = new Client(serverAddress);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
