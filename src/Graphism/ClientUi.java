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

import java.awt.event.MouseEvent;

public class ClientUi extends Stage {
    private  RadioButton [] radio=new RadioButton[3] ;
    private Button [] bouton=new Button[2] ;
    private Label [] lab=new Label[4] ;
    private TextField [] tex=new TextField[3];
    private Client c = new Client() ;
    CheckBox etud ;
     public ClientUi() {
        this.setTitle("Client");
        this.setResizable(false);
        Label nom = creerlabel("Nom") ;
        Label prenom = creerlabel("Prénom") ;
        Label num = creerlabel("Numiros Tl") ;
         Label typecmnd = creerlabel("Veuillez Choisir le type de la commande : ") ;
        TextField nomt= creefield() ;
        TextField prenomt = creefield() ;
        TextField numt = creefield() ;
         Button btn = creerbuton("Suivant") ;
        Button btn1 = creerbuton("Retour") ;
        RadioButton cmndint =creeredio("Commande Sur place");
        RadioButton cmndext =creeredio("Commande livrée");
        RadioButton  event =creeredio("Evenement");
        etud =new CheckBox("Etudiant") ;
        ToggleGroup radioGroup = new ToggleGroup();
        cmndext.setToggleGroup(radioGroup);
        cmndint.setToggleGroup(radioGroup);
        event.setToggleGroup(radioGroup);
        lab[0]=nom;lab[1]=prenom;lab[2]=num;lab[3]=typecmnd ;
        tex[0]=nomt ;tex[1]=prenomt;tex[2]=numt ;
         radio[0]=cmndint;radio[1]=cmndext;radio[2]=event;
         bouton[0]=btn ; bouton[1]=btn1 ;
        Scene scene = new Scene(creelayout(lab,tex,radio,etud,bouton),1200,600) ;
         scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
         this.setScene(scene);
    }

    public Label creerlabel(String s) {
        Label etiq = new Label(s);
        etiq.setAlignment(Pos.CENTER);
        etiq.setFont(Font.font("Verdana", 17));

        return etiq ;
    }
    public TextField creefield()
    {
        TextField text = new TextField();
        text.setFont(Font.font("Verdana", 12));
        return text ;
    }
    public RadioButton creeredio(String s)
    {
        RadioButton btn = new RadioButton(s);
        return btn ;
    }
    public Button creerbuton(String s)
    {
        Button bouton = new Button(s);
        bouton.setPrefSize(150, 30);
        bouton.setFont(Font.font("Verdana", 20));
        if("Retour".equals(s)){
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                {
                    try {
                        Acuille accl = new Acuille();
                        hide();
                        accl.show();
                    }
                    catch (ClassNotFoundException e)
                    {
                        e.getStackTrace() ;
                    }
                    }} );
        }
        else
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                {
                    int j=0 ;
                    for(int i=0 ; i<3;i++)
                    {
                        if((tex[i].getText() == null || tex[i].getText().isEmpty()))
                        {
                            j++ ;
                        }
                    }
                    if(!estEntier(tex[2].getText()))
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Attention");
                        alert.setHeaderText("Erreure");
                        alert.setContentText("Veuillez Écrire le bon Format Pour Les nombre");
                        alert.showAndWait();
                        j++ ;
                    }
                    if(j==0) {
                        c = new Client(tex[0].getText(),tex[1].getText(),tex[2].getText());
                        if(etud.isSelected()) c.setEtudiant(true);
                        else c.setEtudiant(false);
                        if (radio[0].isSelected()) {
                            Commandeint cmnd = new Commandeint(c,0);
                            hide();
                            cmnd.show();
                        } else if (radio[1].isSelected()) {
                            CommandextClient cmnd = new CommandextClient(c);
                            hide();
                            cmnd.show();

                        } else if (radio[2].isSelected()) {
                            Event cmnd = new Event(c,0);
                            hide();
                            cmnd.show();
                        }
                    }

                }} );
        }
       return bouton ;
    }
    public boolean estEntier(String ch) {
        try {
            Integer.parseInt(ch);
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }
    public VBox creelayout(Label[] lab,TextField  [] text,RadioButton[]btn,CheckBox etude,Button bouton[])
    {
        VBox vbox=new VBox(15);
        HBox hbox=new HBox(15);
        HBox hbox1=new HBox(15);
        HBox vbox3=new HBox(15);
        VBox vbox1=new VBox(20) ;
        VBox vbox2=new VBox(20) ;
        vbox3.getChildren().addAll(btn) ;
        vbox1.getChildren().addAll(lab) ;
        vbox2.getChildren().addAll(text) ;
        hbox1.getChildren().setAll(bouton) ;
        hbox.getChildren().add(vbox1) ;
        hbox.getChildren().add(vbox2) ;
        vbox.getChildren().add(hbox) ;
         vbox.getChildren().add(vbox3) ;
        vbox.getChildren().add(etude) ;
        vbox.getChildren().add(hbox1) ;
         vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20,20,20,20));
        return vbox ;
    }

}