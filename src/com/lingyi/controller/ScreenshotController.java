package com.lingyi.controller;

import com.lingyi.Main;
import com.lingyi.util.FileNameUtil;
import com.sun.imageio.plugins.common.ImageUtil;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScreenshotController {

    private double oldSceneX;

    private double oldSceneY;

    private double imageWidth;

    private double imageHeight;

    public static List<Stage> list = new ArrayList<>();
    @FXML
    private AnchorPane anchorPane;


    public void setOldCoordinates(MouseEvent mouseEvent) {
        this.anchorPane.getChildren().clear();
        this.oldSceneX = mouseEvent.getSceneX();
        this.oldSceneY = mouseEvent.getSceneY();
    }


    public void setBorder(MouseEvent mouseEvent) {
        this.anchorPane.getChildren().clear();
        double width = Math.abs(mouseEvent.getSceneX() - this.oldSceneX);//防止负数
        double height = Math.abs(mouseEvent.getSceneY() - this.oldSceneY);

        Label label = new Label(width + " × " + height);
        label.setLayoutX(this.oldSceneX);
        label.setLayoutY(this.oldSceneY - 15.0);
        label.setTextFill(Paint.valueOf("#FFFFFF"));
        AnchorPane area = new AnchorPane();
        area.setLayoutX(this.oldSceneX);
        area.setLayoutY(this.oldSceneY);
        area.setPrefWidth(width);
        area.setPrefHeight(height);
        area.setBorder(new Border(new BorderStroke(
                Color.RED,
                Color.RED,
                Color.RED,
                Color.RED,
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID,
                null,
                new BorderWidths(2.0),
                null)));
        this.anchorPane.getChildren().addAll(label, area);

    }

    public void setNewCoordinates(MouseEvent mouseEvent) {
        this.imageWidth = Math.abs(mouseEvent.getSceneX() - this.oldSceneX);
        this.imageHeight = Math.abs(mouseEvent.getSceneY() - this.oldSceneY);
        if (!(imageWidth == 0.0 || imageHeight == 0.0)) {
            Button button = new Button("完成");
            button.setLayoutX(this.oldSceneX + this.imageWidth - 40.0);
            button.setLayoutY(this.oldSceneY + this.imageHeight);
            button.setOnMouseClicked(mEvent -> {
                MainController.screenshotStage.close();
                this.screenshot();
                Main.primaryStage.show();
            });
            this.anchorPane.getChildren().addAll(button);
        }
    }

    public void screenshot() {
        Robot robot;
        try {
            robot = new Robot();
            Rectangle rec = new Rectangle((int) this.oldSceneX, (int) this.oldSceneY, (int) (this.imageWidth), (int) (this.imageHeight));
            BufferedImage bufferedImage = robot.createScreenCapture(rec);
            WritableImage wi = SwingFXUtils.toFXImage(bufferedImage, null);
            /* 获取系统剪切板 */
            Clipboard cb = Clipboard.getSystemClipboard();
            /* 将图片放在剪切板上 */
            ClipboardContent content = new ClipboardContent();
            content.putImage(wi);
            cb.setContent(content);
            this.showImageView(wi);//创建ImageView
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    public void showImageView(Image image) {
        Parent root;
        try {
            root = FXMLLoader.load(MainController.class.getResource("/imageView.fxml"));
            AnchorPane anchorPane = (AnchorPane) root;
            anchorPane.setPrefWidth(image.getWidth());
            anchorPane.setPrefHeight(image.getHeight());

            ImageView imageView = (ImageView) anchorPane.getChildren().get(0);
            imageView.setFitWidth(image.getWidth());
            imageView.setFitHeight(image.getHeight());
            imageView.setImage(image);
            imageView.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) {//双击关闭
                    this.closeImageView(imageView);
                } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    this.saveImage(image);
                }
            });

            Scene scene = new Scene(root);//新建场景
            Stage stage = new Stage(StageStyle.UNDECORATED);//新建舞台
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.show();
            list.forEach(Stage::show);
            list.add(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeImageView(ImageView imageView) {
        Stage stage = ((Stage) imageView.getScene().getWindow());
        stage.close();
        list.remove(stage);
    }

    public void saveImage(Image image) {
        try {
            String pathName = FileNameUtil.get();
            File file = new File(pathName);
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
