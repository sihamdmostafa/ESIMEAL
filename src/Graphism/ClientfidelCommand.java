package Graphism;
import Nouyau.* ;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.*;
import javafx.scene.layout.HBox ;

import java.io.FileNotFoundException;

public class ClientfidelCommand extends Stage {
    private RadioButton[] radio = new RadioButton[3];
    private Button [] bouton = new Button[3] ;
    private Label[] lab = new Label[4];
    private Clientfidel c;
    public ClientfidelCommand(int i) {
         c = Esimeal.rechClientfidel(i) ;
          //recuprer le client fidel de numiros i ;
        lab[0]=creerlabel("Votre Nom est : "+c.getNom()) ;
        lab[1]=creerlabel("Votre prenom est : "+c.getPrenom()) ;
        lab[2]=creerlabel("Votre numiros telephone : "+c.getNumtelph()) ;
        lab[3]=creerlabel("Veuillez Choisir le type de la commande : ") ;
        ToggleGroup radioGroup = new ToggleGroup();
        radio[0] = creeredio("Commande Sur place") ;
        radio[1] = creeredio("Commande livrée") ;
        radio[2] = creeredio("Evenement") ;
        radio[0].setToggleGroup(radioGroup);
        radio[1].setToggleGroup(radioGroup);
        radio[2].setToggleGroup(radioGroup);
        bouton[0] = creerbuton("Suivant") ;
        bouton[1] = creerbuton("Annuler") ;
        bouton[2] = creerbuton("Mettre a Joure") ;
        bouton[2].setPrefSize(300,50);
        Scene scene = new Scene(creelayot(lab,radio,bouton),1200,600) ;
        scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());        this.setScene(scene);
    }
    public Label creerlabel(String s) {
        Label etiq = new Label(s);
        etiq.setAlignment(Pos.CENTER);
        etiq.setFont(Font.font("Verdana", 17));
        return etiq ;
    }
    public Button creerbuton(String s) {
        Button bouton = new Button(s);
        bouton.setPrefSize(150, 30);
        bouton.setFont(Font.font("Verdana", 20));
        if("Annuler".equals(s))
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                {
                    ClientFidelUi accl = new ClientFidelUi() ;
                    hide();
                    accl.show();
                }} );
        }
        else if("Mettre a Joure".equals(s))
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                {
                    Chanfinfo();
                    hide() ;
                }} );
                }

        else {
            bouton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent actionEvent) {
                    if (radio[0].isSelected()) {
                        Commandeint cmnd = new Commandeint(c,c.getCodefidel());
                        hide();
                        cmnd.show();
                    } else if (radio[1].isSelected()) {
                        CommandeExtfidel cmnd =new CommandeExtfidel(c.getCodefidel()) ;
                        hide();
                        cmnd.show();
                    } else {
                        Event cmnd = new Event(c,c.getCodefidel());
                        hide();
                        cmnd.show();
                    }

                }
            });
        }

        return bouton ;
    }
    public RadioButton creeredio(String s)
    {
        RadioButton btn = new RadioButton(s);
        return btn ;
    }
    public TextField creefield()
    {
        TextField text = new TextField() ;
        //     text.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Verdana", 12));
        return text ;
    }
    public void Chanfinfo()
    {
        Stage stage = new Stage();
        Label lab = creerlabel("numiros telephone") ;
        Label lab2 = creerlabel("adresse") ;
        TextField text = creefield() ;
        ComboBox lis = new ComboBox();
        lis.getItems().addAll(c.getAdr()) ;
        lis.setEditable(true);
        Button btn1 = creerbuton("Ajoute l'adresse") ;
        btn1.setPrefSize(300,20);
        Button btn2 = creerbuton("Supprimer l'adresse") ;
        btn2.setPrefSize(300,20);
        Button btn3 = creerbuton("Confirmer");
        Button btn4 = creerbuton("Annuler") ;
        btn1.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent)
            {
                int j = 0;
                if(lis.getValue() != null && !lis.getValue().toString().isEmpty())
                {
                    for(int i=0;i<lis.getItems().size();i++)
                    {
                        if(lis.getItems().get(i).equals(lis.getValue()))
                        {
                            j++ ;
                        }
                    }
                    if(j==0)
                    {
                        lis.getItems().add(lis.getValue()) ;
                        c.ajouteadr((String)lis.getValue());

                    }
                }
            }} );
        btn2.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent)
            {
                int j = 0;
                if(lis.getValue() != null && !lis.getValue().toString().isEmpty())
                {
                    for(int i=0;i<lis.getItems().size();i++)
                    {
                        if(lis.getItems().get(i).equals(lis.getValue()))
                        {
                            j++ ;
                        }
                    }
                    if(j != 0) {
                      lis.getItems().remove(lis.getValue()) ;
                      c.removadr((String)lis.getValue());
                    }
                    }

            }} );
        btn3.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent)
            {
               if((!estEntier(text.getText())) && (!text.getText().isEmpty()))
               {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Attention");
                   alert.setHeaderText("Erreure");
                   alert.setContentText("Veuillez Écrire le bon Format Pour les textes");
                   alert.showAndWait();
               }
               else
               {
                   c.setNumtelph(text.getText());
                 try {
                     Fichier.Sauvgarde();
                 } catch (ClassNotFoundException e)
                 {

                 }
                   stage.hide();
                   ClientfidelCommand client = new ClientfidelCommand(c.getCodefidel()) ;
                   client.show();

               }
            }} );
        btn4.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent)
            {
                stage.hide();
                show();
            }} );
          HBox hbox = new HBox(20) ;
          VBox vBox = new VBox(20) ;
          VBox vBox1 = new VBox(20) ;
          VBox vBox2 = new VBox(20) ;
          vBox1.getChildren().addAll(lab,lab2) ;
          vBox2.getChildren().addAll(text,lis) ;
          hbox.getChildren().addAll(vBox1,vBox2) ;
          vBox.getChildren().add(hbox) ;
          vBox.getChildren().addAll(new HBox(20,btn1,btn2),new HBox(20,btn3,btn4)) ;
          vBox.setPadding(new Insets(20,20,20,20));
           Scene scene = new Scene(vBox, 1200, 600);
        scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
        stage.setScene(scene) ;
          stage.show();


            }
    public boolean estEntier(String ch) {
        try {
            Integer.parseInt(ch);
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }

    public VBox creelayot(Label [] lab,RadioButton [] rad,Button [] btn)
    {
        VBox vbox = new VBox(30) ;
        HBox hbox=new HBox(15) ;
        HBox hbox1 = new HBox(25) ;
        vbox.getChildren().addAll(lab) ;
        vbox.getChildren().addAll(btn[2]) ;
        hbox.getChildren().addAll(rad) ;
        hbox1.getChildren().addAll(btn[0],btn[1]) ;
        vbox.getChildren().add(hbox) ;
        vbox.getChildren().add(hbox1) ;
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20,20,20,20));
        return vbox ;
    }


}