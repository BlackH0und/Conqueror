/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.blackh0und.frame;

import ch.blackh0und.AttackingArmee;
import ch.blackh0und.Field;
import ch.blackh0und.Player;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author 5im16maschifferle
 */
public class GameFrame {
    private int width, height;
    private Group group;
    
    private double startX = -1, startY, scrolled = -1;
    
    private Label valet;
    private int money = 1000;
    private AttackingArmee attackingArmee;
    
    public HBox sliders;
    public boolean attacking = false;
    public boolean isserver = false;
    
    public Field[][] hexagons;
    
    public Player thisPlayer;
    public Player[] players;
    
    
    public void start(Stage primaryStage) {
        Random r = new Random();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        width = (int) dim.getWidth();
        height = (int) dim.getHeight() - 450;

        group = new Group();

        //group.setTranslateX(width);
        AnchorPane parent = new AnchorPane();

        Pane root = new Pane();
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
        parent.getChildren().add(root);

        AnchorPane bar = new AnchorPane();
        parent.getChildren().add(bar);
        parent.setBottomAnchor(bar, 0d);
        parent.setRightAnchor(bar, 0d);
        parent.setLeftAnchor(bar, 0d);
        parent.setPrefHeight(200);

        Pane background = new Pane();
        bar.getChildren().add(background);
        bar.setTopAnchor(background, 0d);
        bar.setBottomAnchor(background, 0d);
        bar.setRightAnchor(background, 0d);
        bar.setLeftAnchor(background, 0d);
        background.setStyle("-fx-background-color: gray;");
        background.setOpacity(0.75);

        StackPane menu = new StackPane();
        bar.getChildren().add(menu);
        bar.setTopAnchor(menu, 0d);
        bar.setBottomAnchor(menu, 0d);
        bar.setRightAnchor(menu, 0d);
        bar.setLeftAnchor(menu, 0d);

        VBox main = new VBox();
        main.setMaxWidth(200);
        menu.getChildren().add(main);

        valet = new Label("" + money);
        valet.setMinWidth(50);
        main.getChildren().add(valet);

        HBox buttons = new HBox();
        buttons.setMaxWidth(200);
        main.getChildren().add(buttons);

        sliders = new HBox();
        sliders.setMaxWidth(200);
        main.getChildren().add(sliders);

        Button b = new Button("Recruit");
        b.setOnMouseClicked(event -> {
            for (Field[] h : hexagons) {
                for (Field p : h) {
                    if (p.getHex().getId().equals("selected")) {
                        if (p.getPlayer() == null) {
                            return;
                        } else if (!(p.getPlayer().equals(thisPlayer))) {
                            return;
                        }
                    }
                }
            }

            sliders.getChildren().clear();
            int max = (int) money / 100;
            Slider s = new Slider(0, max, 0);
            sliders.getChildren().add(s);

            Label l = new Label("0/" + max);
            l.setMinWidth(50);
            sliders.getChildren().add(l);

            s.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    l.setText(newValue.intValue() + "/" + max);
                }
            });

            Button rb = new Button("Recruit");
            rb.setMinWidth(100);
            rb.setOnMouseClicked(event2 -> {

                for (Field[] h : hexagons) {
                    for (Field p : h) {
                        if (p.getHex().getId().equals("selected")) {
                            if (money >= (s.valueProperty().intValue()*100)) {
                                money -= s.valueProperty().intValue() * 100;
                                valet.setText("" + money);
                                p.setTroopCount(p.getTroopCount() + s.valueProperty().intValue());
                                p.getTroops().setText("" + p.getTroopCount());
                                return;
                            }
                        }
                    }
                }
            });
            sliders.getChildren().add(rb);
        });
        buttons.getChildren().add(b);

        Button attackB = new Button("Attack");
        attackB.setOnMouseClicked(event -> {
            for (Field[] h : hexagons) {
                for (Field p : h) {

                    //p.getHex().setFill(((Color)p.getHex().getFill()).darker());
                    if (p.getHex().getId().equals("selected")) {
                        if (p.getPlayer() == null) {
                            return;
                        } else if (!(p.getPlayer().equals(thisPlayer))) {
                            return;
                        }
                        /*p.getHex().setFill(((Color)p.getHex().getFill()).brighter());
                        Field[] neighbors = Hexagon.getNeighbors(p.getRow(), p.getColumn());
                        for (int i = 0; i < neighbors.length; i++) {
                            neighbors[i].getHex().setFill(((Color)neighbors[i].getHex().getFill()).brighter());
                            
                        }*/

                    }
                }
            }

            sliders.getChildren().clear();
            attacking = true;
            for (Field[] h : hexagons) {
                for (Field p : h) {
                    if (p.getHex().getId().equals("selected")) {

                        Label l = new Label("0/" + p.getTroopCount());
                        l.setMinWidth(100);

                        Slider s = new Slider(0, (int) p.getTroopCount(), 0);
                        s.valueProperty().addListener(new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                l.setText(newValue.intValue() + "/" + p.getTroopCount());
                                attackingArmee = new AttackingArmee(newValue.intValue(), p, null);
                            }
                        });
                        s.setMinWidth(100);
                        sliders.getChildren().add(s);
                        sliders.getChildren().add(l);

                    }
                }
            }

        });
        buttons.getChildren().add(attackB);

        root.getChildren().add(group);
        root.setOnMouseDragged(event -> {
            if (startX >= 0) {
                double distanceX = (event.getX() - startX);
                double distanceY = (event.getY() - startY);
                group.setTranslateX(distanceX + group.getTranslateX());
                group.setTranslateY(distanceY + group.getTranslateY());

                System.out.println(distanceX + " " + distanceY);
            }

            startX = event.getX();
            startY = event.getY();
        });
        root.setOnMouseReleased(event -> {
            startX = -1;
        });
        group.setOnScroll(event -> {
            /*if (scrolled >= 0) {
                group.setScaleX(scrolled + group.getScaleX());
                group.setScaleY(scrolled + group.getScaleY());
            }

            scrolled = group.getScaleY();*/
            double scale = group.getScaleX() + event.getDeltaY() / 500.0;
            if (scale < 0.2 || scale > 2) {
                return;
            }

            group.setScaleX(scale);
            group.setScaleY(scale);

            System.out.println(group.getScaleX() + " " + group.getScaleY());

        });
        group.setOnScrollFinished(event -> {
            scrolled = -1;
        });

        Scene scene = new Scene(parent, width, height);
        primaryStage.setFullScreen(true);

        /*Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        group.getChildren().clear();
                        Framework.generateWorld(r.nextDouble(), group, hexsize, 25, 50, r.nextDouble());
                    }
                });
            }
        }, 0, 2000);*/
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
