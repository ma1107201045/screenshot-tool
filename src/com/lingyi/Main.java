package com.lingyi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../main.fxml"));
        Scene scene = new Scene(root);

        KeyCodeCombination kcc = new KeyCodeCombination(KeyCode.A, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.ALT_DOWN);//注册热键
        AnchorPane anchorPane = (AnchorPane) root;
        Button button = (Button) anchorPane.getChildren().get(0);
        Mnemonic mnemonic = new Mnemonic(button, kcc);
        scene.addMnemonic(mnemonic);

        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("截图工具");
        primaryStage.show();
        Main.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
