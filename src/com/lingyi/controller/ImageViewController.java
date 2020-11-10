package com.lingyi.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ImageViewController {

    private Stage stage;

    private double offsetX;

    private double offsetY;
    @FXML
    private AnchorPane anchorPane;


    /**
     * 记录当前场景坐标
     *
     * @param mouseEvent 鼠标事件
     */
    public void setCurrentCoordinates(MouseEvent mouseEvent) {
        stage = (Stage) anchorPane.getScene().getWindow();
        this.offsetX = mouseEvent.getSceneX();
        this.offsetY = mouseEvent.getSceneY();
    }

    /**
     * 设置舞台坐标
     *
     * @param mouseEvent 鼠标事件
     */
    public void setStageCoordinates(MouseEvent mouseEvent) {
        stage.setX(mouseEvent.getScreenX() - this.offsetX);
        stage.setY(mouseEvent.getScreenY() - this.offsetY);
    }
}
