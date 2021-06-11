package Graphism;

import Nouyau.Esimeal;
import Nouyau.Fichier;
import Nouyau.Main;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.*;
import javafx.scene.layout.HBox ;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class Acuille extends Stage {
    public Acuille() throws ClassNotFoundException {
        GridPane root = new GridPane();
        Fichier.Recupere();
        this.setTitle("Accueil");
        this.setResizable(false);
        Button clietfidel=creerBouton("Client Fidèle") ;
        Button client=creerBouton("Client");
        Button gerant=creerBouton("Gérant") ;
        Button[] boutons={clietfidel,client,gerant};
        HBox btn = creelayout(boutons);
        Esimeal.mtajouretab() ;
        Esimeal.ajoutCmndEffect();
        ImageView bg = new ImageView(new Image(ClassMain.class.getResourceAsStream("bg.jpg")));
        root.add(bg,0,0);
        root.add(btn,0,0);
        Scene scene = new Scene(root,1200,600) ;
        scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
        this.setScene(scene);
    }
    public Button creerBouton(String s) {
        Button bouton = new Button(s);
        bouton.setPrefSize(200, 40);
        bouton.setFont(Font.font("Verdana", 20));
        if("Client".equals(s)){
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                { ClientUi clientui=new ClientUi();
                    hide();
                    clientui.show();
                }} );
        }
        else if("Client Fidèle".equals(s))
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                { ClientFidelUi clientfidelui=new ClientFidelUi();
                    hide();
                    clientfidelui.show();
                }} );
        }
        else
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                { GerantUI clientfidelui=new GerantUI();
                    hide();
                    clientfidelui.show();
                }} );
        }
        return bouton ;
    }
    public HBox creelayout(Button[] btn)
    {
        HBox hbox=new HBox(60);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(550,20,20,20));
        hbox.getChildren().setAll(btn) ;
        return hbox ;
    }
}
