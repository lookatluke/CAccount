/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.views;

import com.e.caccount.CAccounting;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author trito
 */
public class HelpController implements Initializable {
    

    @FXML
    private SplitPane HelpSplitPane;
    @FXML
    private Label intro;
    @FXML
    private Label BasicIntro;
    @FXML
    private Label NetworkIntro;
    @FXML
    private FlowPane afterward;
    @FXML
    private FlowPane backward;
    @FXML
    private AnchorPane ImagePane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setArrows();
        startIntro();
    }

    public void setArrows() {
        Image afterWardImg = new Image(CAccounting.class.getResourceAsStream("resources/_forward.png"));
        Image backWardImg = new Image(CAccounting.class.getResourceAsStream("resources/_backward.png"));
        ImageView afterWardImageView = new ImageView(afterWardImg);
        ImageView backWardImageView = new ImageView(backWardImg);
        afterward.getChildren().add(afterWardImageView);
        backward.getChildren().add(backWardImageView);
    }

    public void cleanImagePane() {
        ImagePane.getChildren().clear();
    }

    public void startIntro() {
        Label tempLabel = new Label("현재 페이지 개발 중입니다.!");
        ImagePane.getChildren().add(tempLabel);
    }

}
