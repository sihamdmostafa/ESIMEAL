package Graphism;
import Graphism.Acuille;
import Nouyau.Client;
import Nouyau.Main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class ClassMain extends Application {

    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException  {
        Acuille stage = new Acuille() ;
        stage.getIcons().add(new Image(ClassMain.class.getResourceAsStream("icone.png")));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}