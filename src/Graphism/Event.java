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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event extends Stage {
    Label lab[] = new Label[3] ;
    Label lab1 = new Label() ;
    TextField [] text = new TextField[1] ;
    Button btn[] = new Button[3] ;
    RadioButton radio[]=new RadioButton[4] ;
    DatePicker date ;
    TitledPane menu = new TitledPane() ;
    ScrollPane pane = new ScrollPane() ;
    boolean Vp = false ;
    VBox vbox1  = new VBox(20) ;
     Evenment cmnd=new Evenment() ;
    ArrayList<Met> list = new ArrayList<Met>() ;
    private ObservableList names =
            FXCollections.observableArrayList();
    private Client c ;
    private int i  ;
    public Event(Client c,int i) {
        this.c = c ;
        this.i = i ;

        try {
            Fichier.Recupere();
        }
        catch (ClassNotFoundException ee)
        {}
        this.setTitle("Evenement");
        this.setResizable(false);
        ObservableList names =
                FXCollections.observableArrayList();
          lab[0]=creerlabel("La date") ;
        lab[1] = creerlabel("La thématique");
        lab[2] = creerlabel("Nombre de Personnes");
        text[0] = creefield();
        btn[0]=creerbuton("Confirmer",c) ;
        btn[1]=creerbuton("Précédent",c) ;
        date = new DatePicker() ;
        radio[0] =creeredio("anniversaire");
        radio[1] =creeredio("obteniondediplom");
        radio[2]=creeredio("dinerdemariaage");
        radio[3] =creeredio("dinerdedepartalaretraite");
        ToggleGroup radioGroup = new ToggleGroup();
        radio[0].setToggleGroup(radioGroup);
        radio[1].setToggleGroup(radioGroup);
        radio[2].setToggleGroup(radioGroup);
        radio[3].setToggleGroup(radioGroup);
        Scene scene = new Scene(creelayout(lab,radio,date,text,btn),1200,400) ;
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
        TextField text = new TextField() ;
        //     text.setAlignment(Pos.CENTER);
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
                    int j=0,k=0 ;
                    LocalTime heure =LocalTime.of(0,0);

                        if((text[0].getText() == null || text[0].getText().isEmpty()) || !estEntier(text[0].getText()))
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Erreure");
                            alert.setContentText("Veuillez Écrire le bon Format Pour Les nombre");
                            alert.showAndWait();
                            j++ ;
                        }

                    if(radio[0].isSelected())
                    {
                        k=0 ;
                    }
                    else if(radio[1].isSelected()) k=1 ;
                    else if(radio[2].isSelected()) k=2 ;
                    else j=1 ;
                    if(j == 0)
                    {

                        try {
                            cmnd = new Evenment(c, Integer.parseInt(text[0].getText()), 0,EventE.valueOf(radio[k].getText()),date.getValue());
                            cmnd.setHeure(LocalTime.now()) ;
                            cmnd.setDate();
                            cmnd.Setid();
                            cmnd.setType(2);
                             creeTitlepan() ;

                        }
                        catch(NonFidelException e)
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Code de fidelité n'existe pas (Ou bien Vous étes pas un client fidel)");
                            alert.setContentText("Confirmer Votre Code avant l'inscription");
                            alert.showAndWait();
                        }
                        catch (RetardHeure e)
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Date Non Accepte");
                            alert.setContentText("Une Commande doit etre effectuer au moins 15 Jour avant l'événement");
                            alert.showAndWait();
                        }
                        catch (MaxPersonne e)
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Attention");
                            alert.setHeaderText("Probleme de nombre de personne");
                            alert.setContentText("le nombre de Personne doit etre au moin 30 et au plus 80");
                            alert.showAndWait();
                        }


                    }
                    else if(j != 0)
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Attention");
                        alert.setHeaderText("VEILLER Remplir Tout Les Champs");
                        alert.showAndWait();
                    }

                }} );
        } else
        {
            bouton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent actionEvent) {
                    Stage stage1 = new Stage();
                    ArrayList<Met> list1 = new ArrayList<Met>() ;
                    int l=0 ;
                    int n=0 ;
                    Label  lab =creerlabel("Le prix de Cette Menu est "+n+" Le nombre de calories Est "+l) ;
                    lab.setFont(Font.font("Verdana", 20));
                    Button closeButton = creerbuton("Close",c) ;
                    closeButton.setOnAction(new EventHandler<ActionEvent>(){
                        public void handle(ActionEvent actionEvent)
                        {
                            stage1.close();
                        }} );
                    VBox root = new VBox(20);
                    root.setAlignment(Pos.CENTER);
                    root.getChildren().addAll(lab, closeButton);
                    Scene scene = new Scene(root, 1200, 600);
                    scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
                    stage1.setScene(scene) ;
                    stage1.show();

                }
            });
        }
        return bouton ;
    }
    public void creeTitlepan()
    {
        names.addAll(Esimeal.affichMet());
        Refrech();
        Stage stage = new Stage();
        TitledPane  menu = new TitledPane();
        ObservableList data =
                FXCollections.observableArrayList();
        HBox [] hBox = new HBox[names.size()];
         for(int i=0;i< names.size();i++) {
            VBox vBox2 = new VBox(20)  ;
            CheckBox box1 = new CheckBox() ;
            String s = (String)names.get(i);
             int r = Integer.parseInt(s.substring(0, 1));
            box1.setText((String)names.get(i));
            hBox[i]=new HBox(20) ;
            hBox[i].getChildren().add(box1) ;
            if(Esimeal.getMt().get(r) instanceof Repas) {
                for (int k = 0; k < ((Repas)Esimeal.getMt().get(r)).getSupplm1().size() ; k++) {
                    CheckBox box = new CheckBox(k+((Repas)Esimeal.getMt().get(r)).getSupplm1().get(k).getNom());
                    vBox2.getChildren().add(box);

                }
                hBox[i].getChildren().add(vBox2);
            }
        }
        vbox1.getChildren().addAll(hBox) ;
        HBox hbox = new HBox(20) ;
        menu = new TitledPane() ;
        Button PrixButton = creerbuton("Prix et Nombre de Calories",c);
        Button MenuValid = creerbuton("Valider Le menu",c);
        PrixButton.setPrefSize(300, 50);
        MenuValid.setPrefSize(200, 50);
        hbox.getChildren().addAll(PrixButton,MenuValid) ;
        vbox1.getChildren().addAll(hbox);
        MenuValid.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Vp=true;
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
                if(list.size() ==0)
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Attention");
                    alert.setHeaderText("Votre Menu est vide");
                    alert.showAndWait();
                }
            }});
        PrixButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                     Stage stage1 = new Stage();
                    int l = 0;
                    double n = 0;
                    int cl = 0;

                    ArrayList<Met> list = new ArrayList<Met>();
                    ArrayList<Suplment> list1 = new ArrayList<Suplment>();
                    for (int i = 0; i < names.size(); i++) {
                        Met mt = new Met();
                        if (((CheckBox) hBox[i].getChildren().get(0)).isSelected()) {
                            String s = (((CheckBox) hBox[i].getChildren().get(0)).getText());

                            int k = Integer.parseInt(s.substring(0, 1));
                            mt = Esimeal.getMt().get(k);
                            if (mt instanceof Repas) {
                                mt = new Met();
                                mt = new Repas(((Repas) Esimeal.getMt().get(k)).getNom(), ((Repas) Esimeal.getMt().get(k)).getPrix(), ((Repas) Esimeal.getMt().get(k)).isDispo(), ((Repas) Esimeal.getMt().get(k)).getNbcalories(), ((Repas) Esimeal.getMt().get(k)).getType());
                                VBox vBox = ((VBox) hBox[i].getChildren().get(1));
                                for (int j = 0; j < vBox.getChildren().size(); j++) {
                                    if (((CheckBox) vBox.getChildren().get(j)).isSelected()) {
                                        String s1 = ((CheckBox) vBox.getChildren().get(j)).getText();
                                        int k1 = Integer.parseInt(s1.substring(0, 1));
                                        list1.add(((Repas) Esimeal.getMt().get(k)).getsup(k1));
                                    }
                                }
                                ((Repas) mt).setSupplm(list1);
                            }
                            list.add(mt);
                            n = cmnd.calcule1(list);
                            for (Met met : list) {
                                l += met.getNbcalories();
                                if(met instanceof Repas)
                                {
                                    for(Suplment sup : ((Repas)met).getSupplm1())
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
                    }
                }});
        VBox vbox = new VBox(10) ;
        pane.setContent(vbox1);
        menu.setContent(pane);
        vbox.getChildren().add(menu) ;
        Button closButton = creerbuton("Close",c) ;
        Button confirme = creerbuton("Confirmer",c) ;
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
                AffichConformation() ;
            }} );
        vbox.getChildren().addAll(confirme,closButton) ;
        Scene scene = new Scene(vbox, 1200, 600);
        scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public RadioButton creeredio(String s)
    {
        RadioButton btn = new RadioButton(s);
        return btn ;
    }
    public void AffichConformation()
    {
        Stage stage = new Stage();
        cmnd.ajoutmet(list);
        Esimeal.ajoutecmnd(cmnd);
        try {
            Fichier.Sauvgarde();
        }catch (Exception ee){}
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
    public void Refrech()
    {
        for(int i=0;i<vbox1.getChildren().size()-1;i++)
        {
            HBox hBox = (HBox)vbox1.getChildren().get(i) ;
            String s =(((CheckBox)hBox.getChildren().get(0)).getText()) ;
            int r = Integer.parseInt(s.substring(0, 1));
            if(Esimeal.getMt().get(r).getUnit() <= 0)
            {
                vbox1.getChildren().remove(i);
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
    public HBox creelayout(Label [] lab,RadioButton [] radion,DatePicker date,TextField [] text,Button [] btn)
    {
        HBox hbox = new HBox(40) ;
        VBox vbox = new VBox(20) ;
        VBox vbox1 = new VBox(20);
        VBox vbox2=new VBox(20);
        VBox vbox3=new VBox(20) ;
        VBox vbox4=new VBox(20) ;
        HBox hbox1=new HBox(20) ;
        HBox hbox2=new HBox(20) ;
        hbox2.getChildren().addAll(btn[0],btn[1]) ;
        vbox1.getChildren().addAll(lab[0],lab[1]) ;
        vbox2.getChildren().add(date) ;
        vbox2.getChildren().addAll(radion) ;
        hbox1.getChildren().add(vbox1) ;
        hbox1.getChildren().add(vbox2) ;
          vbox.getChildren().add(hbox1) ;
        vbox.getChildren().addAll(new HBox(15,lab[2],text[0])) ;
        vbox.getChildren().add(hbox2) ;
        hbox.getChildren().add(vbox) ;
        hbox.getChildren().add(vbox3) ;
        hbox.getChildren().add(vbox4) ;
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setPadding(new Insets(20,20,20,20));
        return hbox ;
    }
}