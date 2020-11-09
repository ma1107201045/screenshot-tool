package com.lingyi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainController {

    public static Stage screenshotStage;

    public void showCaptureWindow() throws IOException {
        Main.primaryStage.hide();//隐藏主窗口
        Parent root = FXMLLoader.load(MainController.class.getResource("../../screenshot.fxml"));
        Scene scene = new Scene(root);//新建场景
        scene.setFill(Paint.valueOf("#00000000"));
        KeyCodeCombination kcc = new KeyCodeCombination(KeyCode.ESCAPE);//注册热键
        scene.getAccelerators().put(kcc, () -> {
            screenshotStage.close();
            Main.primaryStage.show();
        });
        screenshotStage = new Stage(StageStyle.TRANSPARENT);//新建舞台
        screenshotStage.setTitle("正在截图...");
        screenshotStage.setFullScreen(true);
        screenshotStage.setFullScreenExitHint("");
        screenshotStage.setScene(scene);
        screenshotStage.show();
    }


}
