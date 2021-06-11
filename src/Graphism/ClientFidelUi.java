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
import javafx.scene.control.Alert.AlertType ;
public class ClientFidelUi extends Stage {

    private Button btn[] =new Button[3] ;
    private Label lab[]=new Label[2] ;
    private TextField text ;
    public ClientFidelUi() {
        this.setTitle("ClientFidel");
        this.setResizable(false);
        btn[0] =creerbuton("Suivant") ;
        btn[1] = creerbuton("Deveniez Un Client Fidel") ;
        btn[2] = creerbuton("Retour") ;
        lab[0] = creerlabel("OU") ;
        lab[1] =creerlabel("Votre Code De Fidelité : ");
        text = creefield();
        text.setPromptText("Votre Code De Fidelité");
        Scene scene = new Scene(creeLayout(text,btn,lab),1200,600) ;
        scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
        this.setScene(scene);

    }
    public Button creerbuton(String s) {
        Button bouton = new Button(s);
        bouton.setPrefSize(300, 30);
        bouton.setFont(Font.font("Verdana", 20));
        if("Deveniez Un Client Fidel".equals(s))
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                {
                    Deveniezfidel accl = new Deveniezfidel() ;
                    hide();
                    accl.show();
                }} );
        }
        else if("Suivant".equals(s))
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                {
                    if(!text.getText().isEmpty()) {
                         if(!estEntier(text.getText()))
                         {
                             Alert alert = new Alert(Alert.AlertType.ERROR);
                             alert.setTitle("Attention");
                             alert.setHeaderText("Erreure");
                             alert.setContentText("Veuillez Écrire le bon Format Pour Les nombre");
                             alert.showAndWait();
                         }
                        else if(Esimeal.rechClientfidel(Integer.parseInt(text.getText()))==null)
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Code de fidelité n'existe pas (Ou bien Vous étes pas un client fidel)");
                            alert.setContentText("Confirmer Votre Code avant l'inscription");
                            alert.showAndWait();
                        }
                        else {
                            ClientfidelCommand accl1 = new ClientfidelCommand(Integer.parseInt(text.getText()));
                            hide();
                            accl1.show();
                        }
                    }
                }} );
        }
        else
        {
        	   bouton.setOnAction(new EventHandler<ActionEvent>(){
                   public void handle(ActionEvent actionEvent)
                   {
                      try
                      {Acuille accl = new  Acuille() ;
                      hide();
                      accl.show();}
                      catch(Exception e)
                      {
                    	  
                      }
                       
                   }} );
        }
        return bouton ;
    }
    public Label creerlabel(String s) {
        Label etiq = new Label(s);
        etiq.setAlignment(Pos.CENTER);
        etiq.setFont(Font.font("Verdana", 17));
        return etiq ;
    }
    public TextField creefield()
    {
        TextField text = new TextField() ;
          text.setFont(Font.font("Verdana", 20));
        text.setPromptText("Votre Code De Fidelité");
        return text ;
    }
    public VBox creeLayout(TextField txt,Button btn[],Label lab[])
    {
        VBox vbox = new VBox(15) ;
        vbox.getChildren().add(lab[1]);
        vbox.getChildren().add(txt) ;
        vbox.getChildren().add(btn[0]) ;
        vbox.getChildren().add(lab[0]);
        vbox.getChildren().add(btn[1]) ;
        vbox.getChildren().add(btn[2]) ;
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(40,40,40,40));
        return vbox ;
    }
    public boolean estEntier(String ch) {
        try {
            Integer.parseInt(ch);
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }
    public VBox creeLyout(Label[] lab,TextField  [] text,CheckBox etude,Button bouton[])
    {
        VBox vbox=new VBox(15);
        HBox hbox=new HBox(15);
        HBox hbox1=new HBox(15);
        HBox vbox3=new HBox(15);
        VBox vbox1=new VBox(20) ;
        VBox vbox2=new VBox(20) ;
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
