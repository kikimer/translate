package com.freeCodder;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by yurka on 07.08.17.
 */
public class FxApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final int defWidth = 400;
        final int defHeight = defWidth*2;
        VBox layout = new VBox(10);
        Label lb = new Label("");
        translate = new Text();
        translate.setFont(new Font(15));
        translate.wrappingWidthProperty().bind(layout.widthProperty());
        layout.getChildren().addAll(lb, translate);

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Переводчик");
        primaryStage.setWidth(defWidth);
        primaryStage.setHeight(defHeight);
        Rectangle2D scrBounds =Screen.getPrimary().getVisualBounds();
        primaryStage.setX(scrBounds.getWidth()-defWidth);
        primaryStage.setY(0);
        primaryStage.show();

        new ChangeSelectedObserver(new YandexTranslateClient(), s->Platform.runLater(()->translate.setText(s)));
    }

    public void NewTranslate(String text){
        Platform.runLater(()-> translate.setText(text));
    }


    private Text translate;
}
