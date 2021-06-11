package Graphism;
import Nouyau.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.stage.Stage;
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
public class Deveniezfidel extends Stage {
    private Button [] bouton=new Button[2] ;
    private Label [] lab=new Label[3] ;
    private TextField [] tex=new TextField[3];
    private Client c ;
    Scene scene ;
    CheckBox etud ;
    public Deveniezfidel() {
        this.setTitle("Client");
        this.setResizable(false);
        Label nom = creerlabel("Nom") ;
        Label prenom = creerlabel("Prénom") ;
        Label num = creerlabel("Numiros Tl") ;
         tex[0]= creefield() ;
          tex[1] = creefield() ;
         tex[2] = creefield() ;
         Button btn = creerbuton("Suivant") ;
        Button btn1 = creerbuton("Retour") ;
        etud =new CheckBox("Etudiant") ;
        etud.setFont(Font.font("Verdana", 15));
        ToggleGroup radioGroup = new ToggleGroup();
        lab[0]=nom;lab[1]=prenom;lab[2]=num;
         bouton[0]=btn ; bouton[1]=btn1 ;
          scene = new Scene(creelaayout(lab,tex,etud,bouton),1200,600) ;
        scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
        this.setScene(scene);
    }
    public TextField creefield()
    {
        TextField text = new TextField() ;
        //     text.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Verdana", 12));
        return text ;
    }
    public Button creerbuton(String s) {
              Button bouton = new Button(s);

        bouton.setPrefSize(150, 30);
        bouton.setFont(Font.font("Verdana", 20));
        if("Suivant".equals(s))
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                {
                      int j =0 ;
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
                       try {
                           showDialog();
                       }
                       catch(ClassNotFoundException e)
                       {
                           e.getStackTrace() ;
                       }
                    }
                 }} );
        }
        else
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                {
                    ClientFidelUi accl = new ClientFidelUi() ;
                    hide();
                    accl.show();
                }} );
        }
        return bouton;
    }
    public Label creerlabel(String s) {
        Label etiq = new Label(s);
        etiq.setAlignment(Pos.CENTER);
        etiq.setFont(Font.font("Verdana", 17));
        return etiq ;
    }
    public VBox creelaayout(Label[] lab,TextField  [] text,CheckBox etude,Button bouton[])
    {
        VBox vbox=new VBox(15);
        HBox hbox=new HBox(15);
        HBox hbox1=new HBox(15);
         VBox vbox1=new VBox(20) ;
        VBox vbox2=new VBox(20) ;
         vbox1.getChildren().addAll(lab) ;
        vbox2.getChildren().addAll(text) ;
        hbox1.getChildren().setAll(bouton) ;
        hbox.getChildren().add(vbox1) ;
        hbox.getChildren().add(vbox2) ;
        vbox.getChildren().add(hbox) ;
         vbox.getChildren().add(etude) ;
        vbox.getChildren().add(hbox1) ;
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20,20,20,20));
        return vbox ;
    }
    private void showDialog() throws ClassNotFoundException{

        Stage stage = new Stage();
        Clientfidel c = Esimeal.ajoutfidel(tex[0].getText(),tex[1].getText(),tex[2].getText()) ;
        Fichier.Sauvgarde();
         if(etud.isSelected()) c.setEtudiant(true);
        else c.setEtudiant(false);
         Label  lab =creerlabel("Votre Code De fidelité Est : "+c.getCodefidel()) ;
         Label  lab1 =creerlabel("  Merci Pour L'inscriptions") ;
        lab.setFont(Font.font("Verdana", 20));
        Button closeButton = creerbuton("Close") ;
        closeButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent)
            {


                ClientfidelCommand accl1 = new ClientfidelCommand(c.getCodefidel()) ;
                stage.close();
                hide() ;
                 accl1.show();
            }} );
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lab, closeButton);
        Scene scene = new Scene(root, 600, 300);
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
    public VBox creerLayout1(Button btn,Label lab)
    {
        VBox vbox = new VBox(15) ;
        vbox.getChildren().add(lab) ;
        vbox.getChildren().add(btn) ;
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20,20,20,20));
        return vbox ;
    }
}
