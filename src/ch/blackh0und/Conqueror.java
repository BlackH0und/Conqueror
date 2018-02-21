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

/**
 *
 * @author marc3
 */
public class Conqueror extends Application {
    
    //endField for AttackingTroops needs to be selected
    @Override
    public void start(Stage primaryStage) {
        Button start = new Button("Start");
        HBox h = new HBox(start);
        Scene scene = new Scene(h);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Server s = new Server(6725);
        Client c = new Client()
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
