package Graphism;
import Nouyau.*;
import Nouyau.NonFidelException;
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

import java.time.LocalTime;
import java.util.ArrayList;

public class CommandextClient extends Stage {
    private TextField [] text = new TextField[5] ;
    private Label [] lab = new Label[5] ;
    private Label [] lab1 = new Label[2] ;
    private Button btn [] =new Button[4] ;
    private TitledPane [] titledPane = new TitledPane[500] ;
    private ListView listView [] = new ListView[500] ;
    private   VBox vbox1 [] = new VBox[500] ;
    private boolean entre = false ;
    private boolean Vp = false;
    private ObservableList names =
            FXCollections.observableArrayList();
    private Client c ;
    private  ArrayList<ArrayList<Met>> pers =new ArrayList<ArrayList<Met>>() ;

    private Commandelivre cmnd = new Commandelivre() ;
    public CommandextClient(Client c) {

        this.c = c ;
        this.setTitle("Commande Exterieure");
        this.setResizable(false);
        names.addAll(Esimeal.affichMet()) ;
        lab[0]= creerlabel("heure") ;
        lab[1]=creerlabel("minutes") ;
        lab[2] = creerlabel("La distance") ;
        lab[3] = creerlabel("L'adresse") ;
        lab[4] = creerlabel("Nombre de Personnes") ;
        text[0] = creefield() ;
        text[1] = creefield() ;
        text[2] = creefield() ;
        text[3] = creefield() ;
        text[4] = creefield() ;
        btn[0] = creerbuton("Confirmer",c) ;
        btn[1] = creerbuton("Précédent",c) ;
         Scene scene = new Scene(creelayout(lab,text,btn),1200,600) ;
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
        TextField text = new TextField() ;
        text.setFont(Font.font("Verdana", 12));
        return text ;
    }
    public Button creerbuton(String s,Client c) {
        Button bouton = new Button(s);
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
        else if("Confirmer".equals(s))
        {
            bouton.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent actionEvent)
                {
                    int j=0 ;
                    LocalTime heure =LocalTime.of(0,0);

                    for(int i=0 ; i<3;i++)
                    {
                        if((text[i].getText() == null || text[i].getText().isEmpty()))
                        {
                            j++ ;
                        }
                    }
                    if(!estEntier(text[0].getText()) || !estEntier(text[1].getText()) || !estEntier(text[2].getText()) || !estEntier(text[4].getText()))
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Attention");
                        alert.setHeaderText("Erreure");
                        alert.setContentText("Veuillez Écrire le bon Format Pour Les nombre");
                        alert.showAndWait();
                        j++ ;
                    }
                    if(j == 0)
                    {

                        try {
                            heure = LocalTime.of(Integer.parseInt(text[0].getText()),Integer.parseInt(text[1].getText())) ;
                            cmnd = new Commandelivre(c, Integer.parseInt(text[4].getText()), 0,text[3].getText(),Integer.parseInt(text[2].getText()),heure);
                            cmnd.setHeure(heure);
                            cmnd.setDate();
                            cmnd.Setid();
                            cmnd.setType(1);
                            AffichMenu(names);
                            cmnd.SetList(pers);
                            Esimeal.ajoutecmnd(cmnd);
                            Fichier.Sauvgarde();

                         }
                        catch(NonFidelException e)
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Code de fidelité n'existe pas (Ou bien Vous étes pas un client fidel)");
                            alert.setContentText("Confirmer Votre Code avant l'inscription");
                            alert.showAndWait();
                        }
                        catch(Tempnonfavorable e)
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Heure Non Accepter");
                            alert.setContentText("Pour Une commande Consommer Au Restaurant l'heure d'arriver ne doit Pas depasser 22h00 et" +
                                    "pour la laivraison l'heure de la reception ne doit pas depasser le minuit ");
                            alert.showAndWait();
                        }
                        catch (RetardHeure e)
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Heure Non Accepte");
                            alert.setContentText("Une Commande doit étre effectuer au moine une heure et demie avant l'heure de livraison");
                            alert.showAndWait();
                        }
                        catch(EventToday e)
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Evenement");
                            alert.setContentText("Désolé il ya un evenement aujourdhui");
                            alert.showAndWait();
                        }
                        catch(DistanceException e)
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Probleme de Distance");
                            alert.setContentText("Désolé la Distance Doit etre infireire a 20 km");
                            alert.showAndWait();
                        }catch (ClassNotFoundException e)
                        {
                            e.getStackTrace() ;
                        }
                    }
                }} );
        }


        return bouton ;
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
        root.getChildren().addAll(lab,lab1,closeButton);
        Scene scene = new Scene(root, 1200, 600);
        scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
        stage.setScene(scene) ;
        stage.show();
    }
    public RadioButton creeredio(String s)
    {
        RadioButton btn = new RadioButton(s);
        return btn ;
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
    public void AffichMenu(ObservableList names) {
        Stage stage = new Stage();
        Button closButton = creerbuton("Close",c) ;
        Button confirme = creerbuton("Confirmer",c) ;
        VBox vbox = new VBox(10) ;
        Accordion accordion = new Accordion() ;
        for(int i=0;i<Integer.parseInt(text[4].getText());i++) {
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
                AffichConformation() ;            }} );
        vbox.getChildren().add(accordion) ;
        vbox.getChildren().addAll(confirme,closButton) ;
        Scene scene = new Scene(vbox, 1200, 600);
        scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public VBox creelayout(Label[] lab,TextField  [] text,Button[] btn)
    {
        VBox vbox = new VBox(20);
        VBox vbox1=new VBox(20) ;
        VBox vbox2=new VBox(20) ;
        HBox hbox1=new HBox(20) ;
        HBox hbox2 = new HBox(15) ;
        hbox2.getChildren().addAll(btn[1],btn[0]) ;
        vbox1.getChildren().addAll(lab) ;
        vbox2.getChildren().addAll(text) ;
        hbox1.getChildren().add(vbox1) ;
        hbox1.getChildren().add(vbox2) ;
        vbox.getChildren().add(hbox1) ;
         vbox.getChildren().addAll(hbox2) ;
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20,20,20,20));
        return vbox ;
    }
}