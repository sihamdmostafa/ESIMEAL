package Graphism;
import Nouyau.* ;
import javafx.scene.image.Image;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.*;
import javafx.scene.layout.HBox ;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.ComboBoxListCell;
import java.time.LocalTime;
import java.io.*;
import java.time.* ;
import java.util.ArrayList;
public class Commandeint extends Stage {
    private Label [] lab = new Label[4] ;
    private TextField [] text = new TextField[3] ;
    private RadioButton [] radio =new  RadioButton[2] ;
    private Button [] bouton = new Button[4] ;
    private  int i ;
     private boolean Vp=false ;
    private Commandext cmnd = new Commandext() ;
    private Client c ;
    TitledPane [] titledPane = new TitledPane[500] ;
    ScrollPane [] scrollpane = new ScrollPane[500];
    ListView listView [] = new ListView[500] ;
    private   VBox vbox1 [] = new VBox[500] ;
    private ObservableList names =
            FXCollections.observableArrayList();
    ArrayList<ArrayList<Met>> pers =new ArrayList<ArrayList<Met>>();
    public Commandeint(Client c,int i) {
        this.setTitle("Commande Intereuire");
        this.setResizable(false);
        this.i = i ;
        this.c = c ;
        try {
            Fichier.Recupere();
        }
        catch (ClassNotFoundException ee){}
        names.addAll(Esimeal.affichMet()) ;
        lab[0] =creerlabel("heure") ;
        lab[1]=creerlabel("minutes") ;
        lab[2]= creerlabel("Nombre des Personne") ;
        lab[3] = creerlabel("Type de la Table") ;
        text[0] = creefield() ;
        text[0].setPrefSize(50,10);
        text[1]=creefield() ;
        text[1].setPrefSize(50,10);
        text[2]=creefield() ;
        radio[0]=creeredio("Table interieure");
        radio[1] =creeredio("Table Exteriure");
        ToggleGroup radioGroup = new ToggleGroup();
        radio[0].setToggleGroup(radioGroup);
        radio[1].setToggleGroup(radioGroup);
        bouton[0]=creerbuton("Confirmer",c);
        bouton[1]=creerbuton("Précédent",c);
         Scene scene = new Scene(creelayout(lab,text,radio,bouton),1200,600) ;
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
        JFXTextField text = new JFXTextField() ;
         text.setFont(Font.font("Verdana", 12));
        return text ;
    }
    public Button creerbuton(String s,Client c) {
        JFXButton bouton = new JFXButton(s);
        bouton.setPrefSize(150, 30);
        bouton.setFont(Font.font("Verdana", 20));
        if("Précédent".equals(s))
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                {
                    ClientUi client = new  ClientUi() ;
                    hide();
                    client.show();
                }} );
        }
        else
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                {

                    boolean ext=false ;
                    boolean je = true ;
                    int j=0 ;
                    LocalTime heure =LocalTime.of(0,0);

                    for(int i=0 ; i<3;i++)
                    {
                        if((text[i].getText() == null || text[i].getText().isEmpty()) || (!estEntier(text[i].getText())))
                        {
                            j++ ;
                        }
                    }
                    if(j != 0)
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Attention");
                        alert.setHeaderText("Erreure");
                        alert.setContentText("Veuillez Écrire le bon Format Pour Les nombre");
                        alert.showAndWait();
                    }
                    if(radio[0].isSelected())
                    {
                        ext = false ;
                    }
                    else if(radio[0].isSelected())
                    {
                        ext = true ;
                    }
                    else
                    {
                        j=1 ;
                    }

                    if(j == 0) {
                        try {
                            je=true ;
                            heure = LocalTime.of(Integer.parseInt(text[0].getText()), Integer.parseInt(text[1].getText()));
                            cmnd = new Commandext(c, Integer.parseInt(text[2].getText()), i, heure, ext);
                            cmnd.setHeure(heure) ;
                            cmnd.SetList(pers);
                            cmnd.setDate();
                            cmnd.Setid();
                            cmnd.setType(0);
                            AffichMenu(names);
                            Esimeal.ajoutecmnd(cmnd);
                            Fichier.Sauvgarde();
                             //valider et l'ajouter dans esimeal ;;
                            //ajouter les met dans cmnd avat l'ajouter Dans esimeal ;;
                        } catch (NonFidelException e) {
                            je=false ;
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Code de fidelité n'existe pas (Ou bien Vous étes pas un client fidel)");
                            alert.setContentText("Confirmer Votre Code avant l'inscription");
                            alert.showAndWait();
                        } catch (Tempnonfavorable e) {
                            je = false ;
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Heure Non Accepter");
                            alert.setContentText("Pour Une commande Consommer Au Restaurant l'heure d'arriver ne doit Pas depasser 22h00 et" +
                                    "pour la laivraison l'heure de la reception ne doit pas depasser le minuit ");
                            alert.showAndWait();
                        }catch (RetardHeure e)
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Heure Non Accepte");
                            alert.setContentText("Une Commande doit étre effectuer au moins une heure avant sa date de consommation sur palce");
                            alert.showAndWait();
                        }
                        catch (Exceptionchaisse e) {
                            je=false ;
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Manque des chaisses Ou table");
                            alert.setContentText("Désolé il n'a pas un nombre suffisant de chaisses Ou de Table");
                            alert.showAndWait();

                        } catch (EventToday e) {
                            je=false ;
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Evenement");
                            alert.setContentText("Désolé il ya un evenement aujourdhui");
                            alert.showAndWait();
                        }

                        catch(ClassNotFoundException e)
                        {
                            e.getStackTrace() ;
                        }


                    }
                }} );
        }

        return bouton ;
    }
    public RadioButton creeredio(String s)
    {
        JFXRadioButton btn = new JFXRadioButton(s);
        return btn ;
    }
    public void AffichConformation()
    {
        Stage stage = new Stage();
         Label  lab =creerlabel("Votre Commande Est bien enregestrer") ;
        Label  lab1 =creerlabel("A Bien tot") ;
        lab.setFont(Font.font("Verdana", 20));
        Button closeButton = creerbuton("Close",c) ;
        closeButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent)
            {

                try
                {Acuille accl1 = new Acuille() ;
                    stage.close();
                    hide() ;
                    accl1.show();}
                catch(ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }} );
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lab,lab1, closeButton);
        Scene scene = new Scene(root, 1200, 600);
        scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
        stage.setScene(scene) ;
        stage.show();
    }

    public TitledPane creeTitlepan(ObservableList names,int j)
    {
        TitledPane  menu = new TitledPane();
        ScrollPane scroll = new ScrollPane() ;
        ObservableList data =
                FXCollections.observableArrayList();
        vbox1[j]=new VBox(20) ;
        HBox [] hBox = new HBox[names.size()];
        for(int i=0;i< names.size();i++) {
            VBox vBox2 = new VBox(10)  ;
            CheckBox box1 = new CheckBox() ;
            String s = (String)names.get(i);
            int r = Integer.parseInt(s.substring(0, 1));
            box1.setText((String)names.get(i));
            hBox[i]=new HBox(10) ;
            hBox[i].getChildren().add(box1) ;
            if(Esimeal.getMt().get(r) instanceof Repas) {
                for (int k = 0; k < ((Repas)Esimeal.getMt().get(r)).getSupplm1().size() ; k++) {
                    CheckBox box = new CheckBox(k+((Repas)Esimeal.getMt().get(r)).getSupplm1().get(k).getNom());
                    vBox2.getChildren().add(box);

                }
                hBox[i].getChildren().add(vBox2);
            }
        }
        vbox1[j].getChildren().addAll(hBox) ;
        HBox hbox = new HBox(20) ;
        menu = new TitledPane() ;
        Button PrixButton = creerbuton("Prix et Nombre de Calories",c);
        Button MenuValid = creerbuton("Valider Le menu",c);
        PrixButton.setPrefSize(300, 50);
        MenuValid.setPrefSize(200, 50);
        hbox.getChildren().addAll(PrixButton,MenuValid) ;
        vbox1[j].getChildren().addAll(hbox);
        MenuValid.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Vp=true;
                ArrayList<Met> list = new ArrayList<Met>() ;
                ArrayList<Suplment> list1 = new ArrayList<Suplment>() ;
                for(int i=0;i<names.size();i++)
                {
                    Met mt = new Met() ;
                    if(((CheckBox)hBox[i].getChildren().get(0)).isSelected())
                    {
                        String s = (((CheckBox)hBox[i].getChildren().get(0)).getText()) ;

                        int k = Integer.parseInt(s.substring(0,1)) ;
                        mt = Esimeal.getMt().get(k) ;
                        if(mt instanceof Repas) {
                            mt = new Met() ;
                            mt = new Repas(((Repas)Esimeal.getMt().get(k)).getNom(),((Repas)Esimeal.getMt().get(k)).getPrix(),((Repas)Esimeal.getMt().get(k)).isDispo(),((Repas)Esimeal.getMt().get(k)).getNbcalories(),((Repas)Esimeal.getMt().get(k)).getType()) ;
                             VBox vBox = ((VBox) hBox[i].getChildren().get(1));
                            for (int j = 0; j < vBox.getChildren().size(); j++) {
                                 if (((CheckBox) vBox.getChildren().get(j)).isSelected()) {
                                    String s1 = ((CheckBox) vBox.getChildren().get(j)).getText();
                                     int k1 = Integer.parseInt(s1.substring(0, 1));
                                     list1.add(((Repas)Esimeal.getMt().get(k)).getsup(k1));
                                }
                            }
                            ((Repas)mt).setSupplm(list1);
                        }
                        Esimeal.getMt().get(k).setUnit(Esimeal.getMt().get(k).getUnit()-1);
                         for(int v=0;v<Integer.parseInt(text[2].getText());v++)
                        {
                            Refrech(v) ;
                        }
                        try
                        {
                            Fichier.Sauvgarde();
                        }
                        catch (ClassNotFoundException e){}
                        list.add(mt);
                    }
                }
                if(list.size() == 0)
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Attention");
                    alert.setHeaderText("Votre Menu est vide");
                    alert.showAndWait();
                }
                else {

                    pers.add(j,list);
                }

            }});
        PrixButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                Vp = false ;
                Stage stage1 = new Stage();
                int l = 0;
                double n = 0;
                int cl = 0;
                ArrayList<Met> list = new ArrayList<Met>();
                ArrayList<Suplment> list1 = new ArrayList<Suplment>();
                for(int i=0;i<names.size();i++)
                {
                    Met mt = new Met() ;
                    if(((CheckBox)hBox[i].getChildren().get(0)).isSelected())
                    {
                        String s = (((CheckBox)hBox[i].getChildren().get(0)).getText()) ;

                        int k = Integer.parseInt(s.substring(0,1)) ;
                        mt = Esimeal.getMt().get(k) ;
                        if(mt instanceof Repas) {
                            mt = new Met() ;
                            mt = new Repas(((Repas)Esimeal.getMt().get(k)).getNom(),((Repas)Esimeal.getMt().get(k)).getPrix(),((Repas)Esimeal.getMt().get(k)).isDispo(),((Repas)Esimeal.getMt().get(k)).getNbcalories(),((Repas)Esimeal.getMt().get(k)).getType()) ;
                            ///  ((Repas) mt).setIngrid(((Repas)Esimeal.getMt().get(k)).getIngrid());
                            VBox vBox = ((VBox) hBox[i].getChildren().get(1));
                            for (int j = 0; j < vBox.getChildren().size(); j++) {

                                if (((CheckBox) vBox.getChildren().get(j)).isSelected()) {
                                    String s1 = ((CheckBox) vBox.getChildren().get(j)).getText();

                                    int k1 = Integer.parseInt(s1.substring(0, 1));

                                    list1.add(((Repas)Esimeal.getMt().get(k)).getsup(k1));
                                }
                            }
                            ((Repas)mt).setSupplm(list1);
                        }
                        list.add(mt);
                    }
                }
                n = cmnd.calcul1(list,j) ;
                for(Met i : list)
                {
                    l = l+i.getNbcalories() ;
                    if(i instanceof Repas)
                    {
                        for(Suplment sup : ((Repas)i).getSupplm1())
                        {
                            l+=sup.getNbcalories() ;
                        }
                    }
                }
                Label lab = creerlabel("Le prix de Cette Menu est " + n + " Le nombre de calories Est " + l);
                lab.setFont(Font.font("Verdana", 20));
                Button closeButton = creerbuton("Close", c);
                closeButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        stage1.close();
                        hide();
                    }
                });
                VBox root = new VBox(20);
                root.setAlignment(Pos.CENTER);
                root.getChildren().addAll(lab, closeButton);
                Scene scene = new Scene(root, 1200, 600);
                scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
                stage1.setScene(scene);
                stage1.show();
            }

        });
        scroll.setContent(vbox1[j]);
        menu.setContent(scroll);
        return menu ;
    }

    public void AffichMenu(ObservableList names) {
        Stage stage = new Stage();
        Button closButton = creerbuton("Close",c) ;
        Button confirme = creerbuton("Confirme",c) ;
        VBox vbox = new VBox(10) ;
        Accordion accordion = new Accordion() ;
        for(int i=0;i<Integer.parseInt(text[2].getText());i++) {
            titledPane[i] = creeTitlepan(names,i) ;
            accordion.getPanes().add(titledPane[i]) ;
        }
        closButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent)
            {

                stage.hide();
                show();
            }} );
        confirme.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent)
            {

                stage.hide();
                AffichConformation();
            }} );
        vbox.getChildren().add(accordion) ;
        vbox.getChildren().addAll(closButton,confirme) ;
        Scene scene = new Scene(vbox, 1200, 600);
        scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void Refrech(int j)
    {
        for(int i=0;i<vbox1[j].getChildren().size()-1;i++)
        {
            HBox hBox = (HBox)vbox1[j].getChildren().get(i) ;
            String s =(((CheckBox)hBox.getChildren().get(0)).getText()) ;
            int r = Integer.parseInt(s.substring(0, 1));
            if(Esimeal.getMt().get(r).getUnit() <= 0)
            {
                vbox1[j].getChildren().remove(i);
            }

        }

    }
    public boolean estEntier(String ch) {
        try {
            Integer.parseInt(ch);
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }
    public VBox creelayout(Label[] lab,TextField  [] text,RadioButton [] radbtn,Button [] bouton)
    {
        HBox hboxpr = new HBox(40);
        VBox vbox = new VBox(20);
        VBox vbox1=new VBox(20) ;
        VBox vbox2=new VBox(20) ;
        HBox hbox1=new HBox(20) ;
        HBox hbox2=new HBox(20) ;
        HBox hbox3 = new HBox(20) ;
        hbox2.getChildren().addAll(bouton[1],bouton[0]) ;
        hbox3.getChildren().addAll(lab[0],text[0],lab[1],text[1]);
        vbox.getChildren().add(hbox3) ;
        vbox1.getChildren().addAll(lab[2],lab[3]) ;
        vbox2.getChildren().add(text[2]) ;
        vbox2.getChildren().addAll(radbtn) ;
        hbox1.getChildren().add(vbox1) ;
        hbox1.getChildren().add(vbox2) ;
        vbox.getChildren().add(hbox1) ;
         vbox.getChildren().add(hbox2) ;
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20,20,20,20));
        return vbox ;
    }
}
