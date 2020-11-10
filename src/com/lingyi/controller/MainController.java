package com.lingyi.controller;

import com.lingyi.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainController {

    public static Stage screenshotStage;

    public void showCaptureWindow() throws IOException {
        Main.primaryStage.hide();//隐藏主窗口
        ScreenshotController.list.forEach(Stage::hide);//隐藏每一个创建的ImageView
        Parent root = FXMLLoader.load(MainController.class.getResource("/screenshot.fxml"));
        Scene scene = new Scene(root);//新建场景
        scene.setFill(null);
        KeyCodeCombination kcc = new KeyCodeCombination(KeyCode.ESCAPE);//注册热键
        scene.getAccelerators().put(kcc, () -> {
            screenshotStage.close();
            Main.primaryStage.show();
            ScreenshotController.list.forEach(Stage::show);
        });
        screenshotStage = new Stage(StageStyle.TRANSPARENT);//新建舞台
        screenshotStage.setTitle("正在截图...");
        screenshotStage.setFullScreen(true);
        screenshotStage.setFullScreenExitHint("");
        screenshotStage.setScene(scene);
        screenshotStage.show();
    }

    public void closeImageView() {
        ScreenshotController.list.forEach(Stage::close);
    }


}
