package Graphism;

import Nouyau.*;
import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sun.misc.Cleaner;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static Nouyau.BoisonE.*;
import static Nouyau.EventE.anniversaire;
import static Nouyau.RepasE.dessert;
import static Nouyau.RepasE.entree;
import static Nouyau.RepasE.plat;


public class GerantUI extends Stage{

    TableView tableRepas;
    TableView tableBoissons;
    TableView tableCmdLivre;
    TableView tableCmdExt;
    TableView tableEvent;
    TableView tableTable;
    TableView tableSuplement;
    GridPane stats;
    Scene scene;
    LocalTime t = LocalTime.now();
    LocalDate d = LocalDate.now();

    public GerantUI(){
        this.setTitle("Gerant Dashboard");
        this.setResizable(false);
        VBox root  = new VBox();
        HBox switchBox = new HBox();
        JFXButton repas = new JFXButton("Liste des repas");
        JFXButton boisson = new JFXButton("Liste des boissons");
        JFXButton cmdExt = new JFXButton("Commandes sur place");
        JFXButton cmdLivre = new JFXButton("Commandes livrees");
        JFXButton cmdEvent = new JFXButton("Commandes evenements");
        JFXButton table = new JFXButton("Liste des tables");
        JFXButton supButton = new JFXButton("Liste des suplements");
        JFXButton Stats = new JFXButton("Statistiques");
        JFXButton back = new JFXButton("Retour");
        repas.setMinWidth(100);
        boisson.setMinWidth(100);
        cmdExt.setMinWidth(100);
        cmdLivre.setMinWidth(100);
        cmdEvent.setMinWidth(100);
        table.setMinWidth(100);
        supButton.setMinWidth(100);
        Stats.setMinWidth(100);
        back.setMinWidth(50);


        switchBox.getChildren().addAll(back,repas,boisson,cmdExt,cmdLivre,cmdEvent,table,supButton,Stats);
        switchBox.setAlignment(Pos.CENTER);
        switchBox.setSpacing(5);
        switchBox.setPadding(new Insets(20,20,20,20));


        /*********************************************************************************/
        //Les collomnes
        TableColumn<Repas, String> name = new TableColumn("Nom");
        name.setMinWidth(200);
        name.setCellValueFactory(new PropertyValueFactory<Repas,String>("nom"));

        //Les collomnes
        TableColumn<Repas, Double> prix = new TableColumn("Prix (DA)");
        prix.setMinWidth(100);
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        //Les collomnes
        TableColumn<Repas, String> dispo = new TableColumn("Disponibilite");
        dispo.setMinWidth(100);
        dispo.setCellValueFactory(new PropertyValueFactory<>("dispo"));

        //Les collomnes
        TableColumn<Repas, Integer> nbcalories = new TableColumn("Nb calories");
        nbcalories.setMinWidth(100);
        nbcalories.setCellValueFactory(new PropertyValueFactory<>("nbcalories"));

        TableColumn<Repas, RepasE> type = new TableColumn("Type");
        type.setMinWidth(100);
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Repas, Integer> unit = new TableColumn("Unites disponibles");
        unit.setMinWidth(100);
        unit.setCellValueFactory(new PropertyValueFactory<>("unit"));

        TableColumn<Repas, String> suplement = new TableColumn<>("Suplements");
        suplement.setMinWidth(200);
        suplement.setCellValueFactory(new PropertyValueFactory<>("supplm"));

        TableColumn<Repas, String> ingredients = new TableColumn<>("Ingredients");
        ingredients.setMinWidth(200);
        ingredients.setCellValueFactory(new PropertyValueFactory<>("ingrid"));


        JFXTextField nameInput = new JFXTextField();
        nameInput.setPromptText("Nom");
        JFXTextField priceInput = new JFXTextField();
        priceInput.setPromptText("Prix");
        JFXTextField unitInput = new JFXTextField();
        unitInput.setPromptText("Unites disponibles");
        JFXComboBox dispoInput = new JFXComboBox();
        dispoInput.setPromptText("Disponibilite");
        dispoInput.getItems().addAll("Disponible","Non disponible" );
        JFXTextField nbcaloriesInput = new JFXTextField();
        nbcaloriesInput.setPromptText("Nb Calories");
        JFXComboBox typeInput = new JFXComboBox();
        typeInput.setPromptText("Type");
        typeInput.getItems().addAll("entree","plat","dessert");

        JFXButton ajouter = new JFXButton("Ajouter");
        ajouter.setMinWidth(100);
        JFXButton supprimer = new JFXButton("Supprimer");
        supprimer.setMinWidth(100);

        HBox ing = new HBox();
        ing.setSpacing(20);
        ing.setPadding(new Insets(10,10,10,10));
        JFXTextField ingInput = new JFXTextField();
        ingInput.setPromptText("L'ingredient");
        JFXButton addIng = new JFXButton("+");
        addIng.setMinWidth(30);
        HBox ingElements = new HBox();
        ingElements.setSpacing(20);
        ing.getChildren().addAll(ingInput,addIng,ingElements);

        HBox sups = new HBox();
        sups.setSpacing(20);
        sups.setPadding(new Insets(10,10,10,10));
        setElementsSup(sups);
        ScrollPane sp = new ScrollPane();
        sp.setContent(sups);
        sp.setFitToHeight(true);
        sp.setMinHeight(60);
        sp.setPannable(true);

        HBox infosPlat = new HBox();
        infosPlat.setPadding(new Insets(10,10,10,10));
        infosPlat.setSpacing(10);
        infosPlat.getChildren().addAll(nameInput,priceInput,unitInput,dispoInput,nbcaloriesInput,typeInput);

        HBox btnPlat = new HBox();
        btnPlat.setSpacing(50);
        btnPlat.getChildren().addAll(ajouter,supprimer);

        VBox editingPlat = new VBox();
        editingPlat.setPadding(new Insets(10,10,10,10));
        editingPlat.setSpacing(10);
        editingPlat.getChildren().addAll(infosPlat,ing,sp,btnPlat);

        tableRepas = new TableView<Repas>();
        tableRepas.setItems(getRepas());
        tableRepas.getColumns().addAll(name,prix,dispo,nbcalories,type,unit,suplement,ingredients);
        tableRepas.setEditable(true);
        tableRepas.setPlaceholder(new Label("Vous n'avez ajoute aucun plat"));
        /***********************************************************************************/

        /***********************************************************************************/
        //Les collomnes
        TableColumn<Boisson, String> nameB = new TableColumn("Nom");
        nameB.setMinWidth(200);
        nameB.setCellValueFactory(new PropertyValueFactory<Boisson,String>("nom"));

        //Les collomnes
        TableColumn<Boisson, Double> prixB = new TableColumn("Prix (DA)");
        prixB.setMinWidth(100);
        prixB.setCellValueFactory(new PropertyValueFactory<>("prix"));

        TableColumn<Boisson, Integer> unitB = new TableColumn("Unites disponibles");
        unitB.setMinWidth(100);
        unitB.setCellValueFactory(new PropertyValueFactory<>("unit"));

        //Les collomnes
        TableColumn<Boisson, String> dispoB = new TableColumn("Disponibilite");
        dispoB.setMinWidth(100);
        dispoB.setCellValueFactory(new PropertyValueFactory<>("dispo"));

        //Les collomnes
        TableColumn<Boisson, Integer> nbcaloriesB = new TableColumn("Nb calories");
        nbcaloriesB.setMinWidth(100);
        nbcaloriesB.setCellValueFactory(new PropertyValueFactory<>("nbcalories"));

        TableColumn<Boisson, BoisonE> typeB = new TableColumn("Type");
        typeB.setMinWidth(100);
        typeB.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Boisson, String> marqueB = new TableColumn<>("La Marque");
        marqueB.setMinWidth(200);
        marqueB.setCellValueFactory(new PropertyValueFactory<>("marque"));

        TableColumn<Boisson,String> goutB = new TableColumn<>("Le gout");
        goutB.setMinWidth(200);
        goutB.setCellValueFactory(new PropertyValueFactory<>("goute"));


        JFXTextField nameInputB = new JFXTextField();
        nameInputB.setPromptText("Nom");
        JFXTextField priceInputB = new JFXTextField();
        priceInputB.setPromptText("Prix");
        JFXTextField unitInputB = new JFXTextField();
        unitInputB.setPromptText("Nb unites");
        JFXComboBox dispoInputB = new JFXComboBox();
        dispoInputB.setPromptText("Disponibilite");
        dispoInputB.getItems().addAll("Disponible","Non disponible");
        JFXTextField nbcaloriesInputB = new JFXTextField();
        nbcaloriesInputB.setPromptText("Nb Calories");
        JFXComboBox typeInputB = new JFXComboBox();
        typeInputB.setPromptText("Type");
        typeInputB.getItems().addAll("eaumineral","jus","boissongazeuse","cafe", "the");
        JFXTextField marqueInputB = new JFXTextField();
        marqueInputB.setPromptText("Marque");
        JFXTextField goutInputB = new JFXTextField();
        goutInputB.setPromptText("Gout");


        JFXButton ajouterB = new JFXButton("Ajouter");
        ajouterB.setMinWidth(100);
        JFXButton supprimerB = new JFXButton("Supprimer");
        supprimerB.setMinWidth(100);

        HBox editingBoisson = new HBox();
        editingBoisson.setPadding(new Insets(20,20,20,20));
        editingBoisson.setSpacing(10);
        editingBoisson.getChildren().addAll(nameInputB,priceInputB,unitInputB,dispoInputB,nbcaloriesInputB,typeInputB,marqueInputB,goutInputB,ajouterB,supprimerB);

        tableBoissons = new TableView<Boisson>();
        tableBoissons.getColumns().addAll(nameB,prixB,unitB,dispoB,nbcaloriesB,typeB,marqueB,goutB);
        tableBoissons.setEditable(true);
        tableBoissons.setItems(getBoisson());
        tableBoissons.setPlaceholder(new Label("Vous n'avez ajoute aucun boisson"));
        /*******************************************************************************************/

        /*******************************************************************************************/
        TableColumn<Commandext,String> client = new TableColumn<>("Client");
        client.setMinWidth(200);
        client.setCellValueFactory(new PropertyValueFactory<>("client"));

        TableColumn<Commandext,Integer> nbpers = new TableColumn<>("Nb personnes");
        nbpers.setMinWidth(200);
        nbpers.setCellValueFactory(new PropertyValueFactory<>("nbperson"));

        TableColumn<Commandext,String> ext = new TableColumn<>("Position de table");
        ext.setMinWidth(200);
        ext.setCellValueFactory(new PropertyValueFactory<>("ext"));

        TableColumn<Commandext,LocalTime> heure = new TableColumn<>("Heure");
        heure.setMinWidth(200);
        heure.setCellValueFactory(new PropertyValueFactory<>("heure"));

        tableCmdExt = new TableView();
        tableCmdExt.getColumns().addAll(client,nbpers,ext,heure);
        tableCmdExt.setEditable(true);
        tableCmdExt.setItems(getCmdExt());
        tableCmdExt.setPlaceholder(new Label("Vous n'avez ajoute aucune Commande"));
        /*******************************************************************************************/
        TableColumn<Commandelivre,Client> clientl = new TableColumn<>("Client");
        clientl.setMinWidth(200);
        clientl.setCellValueFactory(new PropertyValueFactory<>("client"));

        TableColumn<Commandelivre,Integer> nbpersl = new TableColumn<>("Nb personnes");
        nbpersl.setMinWidth(200);
        nbpersl.setCellValueFactory(new PropertyValueFactory<>("nbperson"));

        TableColumn<Commandelivre,Boolean> adr = new TableColumn<>("Adresse");
        adr.setMinWidth(200);
        adr.setCellValueFactory(new PropertyValueFactory<>("adr"));

        TableColumn<Commandelivre,Boolean> distance = new TableColumn<>("Distance (KM)");
        distance.setMinWidth(200);
        distance.setCellValueFactory(new PropertyValueFactory<>("dist"));

        tableCmdLivre = new TableView<>();
        tableCmdLivre.getColumns().addAll(clientl,nbpersl,adr,distance);
        tableCmdLivre.setEditable(true);
        tableCmdLivre.setItems(getCmdLivre());
        tableCmdLivre.setPlaceholder(new Label("Vous n'avez ajoute aucune Commande"));
        /*******************************************************************************************/

        /*******************************************************************************************/
        TableColumn<Evenment,Client> clientev = new TableColumn<>("Client");
        clientev.setMinWidth(200);
        clientev.setCellValueFactory(new PropertyValueFactory<>("client"));

        TableColumn<Evenment,Integer> nbpersev = new TableColumn<>("Nb personnes");
        nbpersev.setMinWidth(200);
        nbpersev.setCellValueFactory(new PropertyValueFactory<>("nbperson"));

        TableColumn<Evenment,EventE> decor = new TableColumn<>("Decor");
        decor.setMinWidth(200);
        decor.setCellValueFactory(new PropertyValueFactory<>("decore"));

        TableColumn<Evenment,LocalDate> date = new TableColumn<>("Date");
        date.setMinWidth(200);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableEvent = new TableView<>();
        tableEvent.getColumns().addAll(clientev,nbpersev,decor,date);
        tableEvent.setMinWidth(200);
        tableEvent.setItems(getEvent());
        tableEvent.setPlaceholder(new Label("Vous n'avez ajoute aucune Commande"));

        /*******************************************************************************************/

        TableColumn<Table, String> typeTable = new TableColumn<>("Type de table");
        typeTable.setMinWidth(200);
        typeTable.setCellValueFactory(new PropertyValueFactory<>("extAff"));

        TableColumn<Table, String> dispoTable = new TableColumn<>("Disponibilite");
        dispoTable.setMinWidth(200);
        dispoTable.setCellValueFactory(new PropertyValueFactory<>("dispoAff"));

        JFXComboBox typeInputTable = new JFXComboBox();
        typeInputTable.setPromptText("Type de table");
        typeInputTable.getItems().addAll("Interieure","Exterieure");

        JFXButton ajouteTable = new JFXButton("Ajouter");
        ajouteTable.setMinWidth(100);
        JFXButton supprimeTable = new JFXButton("Supprimer");
        supprimeTable.setMinWidth(100);

        HBox editingTable = new HBox();
        editingTable.setPadding(new Insets(20,20,20,20));
        editingTable.setSpacing(10);
        editingTable.getChildren().addAll(typeInputTable,ajouteTable,supprimeTable);

        tableTable = new TableView<>();
        tableTable.getColumns().addAll(typeTable,dispoTable);
        tableTable.setMinWidth(200);
        tableTable.setItems(getTable());
        tableTable.setPlaceholder(new Label("Vous n'avez ajoute aucune Table"));
        /*******************************************************************************************/

        /*******************************************************************************************/

        TableColumn<Suplment, String> nameSup = new TableColumn<>("Nom du suplement");
        nameSup.setMinWidth(200);
        nameSup.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Suplment, Double> prixSup = new TableColumn<>("Prix (DA)");
        prixSup.setMinWidth(200);
        prixSup.setCellValueFactory(new PropertyValueFactory<>("prix"));

        TableColumn<Suplment, Integer> nbSup = new TableColumn<>("Nb calories");
        nbSup.setMinWidth(200);
        nbSup.setCellValueFactory(new PropertyValueFactory<>("nbcalories"));

        JFXTextField nameInputSup = new JFXTextField();
        nameInputSup.setPromptText("Nom");

        JFXTextField prixInputSup = new JFXTextField();
        prixInputSup.setPromptText("Prix");

        JFXTextField nbInputSup = new JFXTextField();
        nbInputSup.setPromptText("Nb Calories");

        JFXButton ajouteSup = new JFXButton("Ajouter");
        ajouteSup.setMinWidth(100);
        JFXButton supprimeSup = new JFXButton("Supprimer");
        supprimeSup.setMinWidth(100);

        HBox editingSup = new HBox();
        editingSup.setPadding(new Insets(20,20,20,20));
        editingSup.setSpacing(10);
        editingSup.getChildren().addAll(nameInputSup,prixInputSup,nbInputSup,ajouteSup,supprimeSup);

        tableSuplement = new TableView<>();
        tableSuplement.getColumns().addAll(nameSup,prixSup,nbSup);
        tableSuplement.setMinWidth(200);
        tableSuplement.setItems(getSup());
        tableSuplement.setPlaceholder(new Label("Vous n'avez ajoute aucun Suplement"));
        /*******************************************************************************************/

        /*******************************************************************************************/
        stats = new GridPane();
        stats.setPadding(new Insets(10,10,10,10));
        stats.setHgap(50);
        stats.setVgap(50);

        JFXDatePicker dateDeb = new JFXDatePicker();
        dateDeb.setPromptText("La date de debut");
        JFXDatePicker dateFin = new JFXDatePicker();
        dateFin.setPromptText("La date de fin");
        JFXButton consulter = new JFXButton("Consulter");

        JFXListView<String> result = new JFXListView<String>();
        result.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        stats.add(dateDeb,2,1);
        stats.add(dateFin,3,1);
        stats.add(consulter,4,1);
        stats.add(result,2,2,3,1);

        /*******************************************************************************************/


        //Default values
        root.getChildren().add(switchBox);
        root.getChildren().add(tableRepas);
        root.getChildren().add(editingPlat);
        scene = new Scene(root,1200,600);
        scene.getStylesheets().add(ClassMain.class.getResource("style.css").toExternalForm());
        this.setScene(scene);

        repas.setOnMouseClicked(e -> {
            setElementsSup(sups);
            tableRepas.setItems(getRepas());
            root.getChildren().removeAll(stats,tableBoissons,tableRepas,tableCmdExt,tableCmdLivre,tableEvent,tableTable,tableSuplement,editingTable,editingBoisson,editingPlat,editingSup);
            root.getChildren().addAll(tableRepas,editingPlat);
        });
        boisson.setOnMouseClicked(e -> {
            tableBoissons.setItems(getBoisson());
            root.getChildren().removeAll(stats,tableBoissons,tableRepas,tableCmdExt,tableCmdLivre,tableEvent,tableTable,tableSuplement,editingTable,editingBoisson,editingPlat,editingSup);
            root.getChildren().addAll(tableBoissons,editingBoisson);
        });
        cmdExt.setOnMouseClicked(e ->{
            System.out.println(Esimeal.getCmnd().size());
            tableCmdExt.setItems(getCmdExt());
            root.getChildren().removeAll(stats,tableBoissons,tableRepas,tableCmdExt,tableCmdLivre,tableEvent,tableTable,tableSuplement,editingTable,editingBoisson,editingPlat,editingSup);
            root.getChildren().addAll(tableCmdExt);
        });
        cmdLivre.setOnMouseClicked(e ->{
            tableCmdLivre.setItems(getCmdLivre());
            root.getChildren().removeAll(stats,tableBoissons,tableRepas,tableCmdExt,tableCmdLivre,tableEvent,tableTable,tableSuplement,editingTable,editingBoisson,editingPlat,editingSup);
            root.getChildren().addAll(tableCmdLivre);
        });
        cmdEvent.setOnMouseClicked(e ->{
            tableEvent.setItems(getEvent());
            root.getChildren().removeAll(stats,tableBoissons,tableRepas,tableCmdExt,tableCmdLivre,tableEvent,tableTable,tableSuplement,editingTable,editingBoisson,editingPlat,editingSup);
            root.getChildren().addAll(tableEvent);
        });
        table.setOnMouseClicked(e ->{
            tableTable.setItems(getTable());
            root.getChildren().removeAll(stats,tableBoissons,tableRepas,tableCmdExt,tableCmdLivre,tableEvent,tableTable,tableSuplement,editingTable,editingBoisson,editingPlat,editingSup);
            root.getChildren().addAll(tableTable,editingTable);
        });
        supButton.setOnMouseClicked(e ->{
            tableSuplement.setItems(getSup());
            root.getChildren().removeAll(stats,tableBoissons,tableRepas,tableCmdExt,tableCmdLivre,tableEvent,tableTable,tableSuplement,editingTable,editingBoisson,editingPlat,editingSup);
            root.getChildren().addAll(tableSuplement,editingSup);
        });
        Stats.setOnMouseClicked(e ->{
            root.getChildren().removeAll(stats,tableBoissons,tableRepas,tableCmdExt,tableCmdLivre,tableEvent,tableTable,tableSuplement,editingTable,editingBoisson,editingPlat,editingSup);
            root.getChildren().addAll(stats);
        });
        ajouteSup.setOnMouseClicked(e ->{
            Suplment s ;
            try {
                if( (((String) nameInputSup.getText()).equals("") == false) && (((String) prixInputSup.getText()).equals("") == false) && (((String) nbInputSup.getText()).equals("") == false)) {
                    s = new Suplment(nameInputSup.getText(),Double.parseDouble(prixInputSup.getText()),Integer.parseInt(nbInputSup.getText()));
                    tableSuplement.getItems().add(s);
                    Esimeal.getSuplement().add(s);
                    Fichier.Sauvgarde();
                }
            }catch (Exception ee){}
            nameInputSup.clear();
            prixInputSup.clear();
            nbInputSup.clear();
        });
        supprimeSup.setOnMouseClicked(e ->{
            ObservableList<Suplment> allitems,selecteditems;
            allitems = tableSuplement.getItems();
            selecteditems = tableSuplement.getSelectionModel().getSelectedItems();
            selecteditems.forEach(allitems::remove);
            int i = tableSuplement.getSelectionModel().getSelectedIndex();
            Esimeal.getSuplement().remove(i);
            try
            {Fichier.Sauvgarde();}
            catch (ClassNotFoundException ee){}
        });
        ajouteTable.setOnMouseClicked(e ->{
            Table t = null;
            Boolean d = null;
            switch ((String) typeInputTable.getValue()){
                case "Interieure":
                    d = true;
                    break;
                case "Exterieure":
                    d = false;
                    break;
            }
            try {
                if(((String) typeInputTable.getValue()).equals("") == false) {
                    t = new Table(d);
                    tableTable.getItems().add(t);
                    Esimeal.getTable().add(t);
                    Fichier.Sauvgarde();

                }
            }catch (Exception ee){}
             typeInputTable.setValue(null);
        });
        supprimeTable.setOnMouseClicked(e ->{
            ObservableList<Table> allitems,selecteditems;
            allitems = tableTable.getItems();
            selecteditems = tableTable.getSelectionModel().getSelectedItems();
            selecteditems.forEach(allitems::remove);
            int i = tableTable.getSelectionModel().getSelectedIndex();
            Esimeal.getTable().remove(i);
            try
            {Fichier.Sauvgarde();}
            catch (ClassNotFoundException ee){}

        });
        ajouter.setOnMouseClicked(e -> {
            Repas r = null;
            RepasE typeR = null;
            Boolean d = null;
            ArrayList<Suplment> sup = new ArrayList<>();
            ArrayList<String> ingr = new ArrayList<>();
            int i = 0;
            switch ((String) typeInput.getValue()){
                case "entree":
                    typeR = entree;
                    break;
                case "plat":
                    typeR = plat;
                    break;
                case "dessert":
                    typeR = dessert;
                    break;
            }
            switch ((String) dispoInput.getValue()){
                case "Disponible":
                    d = true;
                    break;
                case "Non disponible":
                    d = false;
                    break;
            }
            for (Node j : sups.getChildren()){
                if (((CheckBox) j).isSelected()) sup.add(Esimeal.getSuplement().get(i));
                i++;
            }
            for (Node j : ingElements.getChildren()){
                ingr.add(((Label)j).getText());
            }
            //TODO check input and update esi meal
            try {
                if (((String) nameInput.getText()).equals("") == false && ((String) priceInput.getText()).equals("") == false && ((String) dispoInput.getValue()).equals("") == false && ((String) nbcaloriesInput.getText()).equals("") == false && ((String) typeInput.getValue()).equals("") == false
                    && ((String) unitInput.getText()).equals("") == false) {
                    r = new Repas(nameInput.getText(), Double.parseDouble((String) priceInput.getText()), d, Integer.parseInt((String) nbcaloriesInput.getText()), typeR);
                    r.setSupplm(sup);
                    r.setIngrid(ingr);
                    r.setUnit(Integer.parseInt(unitInput.getText()));
                    tableRepas.getItems().add(r);
                    Esimeal.getMt().add(r);
                    Fichier.Sauvgarde();
                }
            }catch(Exception exp){}
             nameInput.clear();
            priceInput.clear();
            dispoInput.setValue(null);
            typeInput.setValue(null);
            nbcaloriesInput.clear();
            ingElements.getChildren().clear();
            unitInput.clear();
        });
        supprimer.setOnMouseClicked(e -> {
            ObservableList<Repas> allitems,selecteditems;
            allitems = tableRepas.getItems();
            selecteditems = tableRepas.getSelectionModel().getSelectedItems();
            selecteditems.forEach(allitems::remove);
            int i = tableRepas.getSelectionModel().getSelectedIndex();
            Esimeal.getMt().remove(i);
            try
            {Fichier.Sauvgarde();}
            catch (ClassNotFoundException ee){}
         });
        ajouterB.setOnMouseClicked(e ->{
            Boisson b = null;
            BoisonE t = null;
            Boolean d = null;
            switch ((String)dispoInputB.getValue()){
                case "Disponible":
                    d = true;
                    break;
                case "Non disponible":
                    d = false;
                    break;
            }
            switch ((String)typeInputB.getValue()){
                case "eaumineral":
                    t = eaumineral;
                    break;
                case "jus":
                    t = jus;
                    break;
                case "boissongazeuse":
                    t = boissongazeuse;
                    break;
                case "cafe":
                    t = cafe;
                    break;
                case "the":
                    t = the;
                    break;
            }
            //TODO Same
            try {
                if (((String) nameInputB.getText()).equals("") == false && ((String) priceInputB.getText()).equals("") == false && ((String) dispoInputB.getValue()).equals("") == false && ((String) nbcaloriesInputB.getText()).equals("") == false && ((String) typeInputB.getValue()).equals("") == false
                        && ((String) marqueInputB.getText()).equals("") == false && ((String) goutInputB.getText()).equals("") == false && ((String) unitInputB.getText()).equals("") == false)
                    ;
                {
                    b = new Boisson(nameInputB.getText(), Double.parseDouble((String) priceInputB.getText()), d, Integer.parseInt((String) nbcaloriesInputB.getText()), t, marqueInputB.getText(), goutInputB.getText());
                    b.setUnit(Integer.parseInt(unitInputB.getText()));
                    tableBoissons.getItems().add(b);
                    Esimeal.getMt().add(b);
                    Fichier.Sauvgarde();
                 }
            }catch (Exception exp){}
            nameInputB.clear();
            priceInputB.clear();
            dispoInputB.setValue(null);
            typeInputB.setValue(null);
            nbcaloriesInputB.clear();
            marqueInputB.clear();
            goutInputB.clear();
            unitInputB.clear();
        });
        supprimerB.setOnMouseClicked(e ->{
            ObservableList<Boisson> allitems,selecteditems;
            allitems = tableBoissons.getItems();
            selecteditems = tableBoissons.getSelectionModel().getSelectedItems();
            selecteditems.forEach(allitems::remove);
            int i = tableBoissons.getSelectionModel().getSelectedIndex();
            Esimeal.getMt().remove(i);
            try
            {Fichier.Sauvgarde();}
            catch (ClassNotFoundException ee){}
        });
        addIng.setOnMouseClicked(e -> {
            Label chip = null;
            if (!ingInput.getText().equals("")) {
                chip = new Label(ingInput.getText());
                ingElements.getChildren().add(chip);
                ingInput.clear();
            }
        });
        consulter.setOnMouseClicked(e ->{
            LocalDate deb = dateDeb.getValue(), fin = dateFin.getValue();
                 if (!deb.toString().equals("") && !fin.toString().equals("")) {
                    result.getItems().add("Le nombre des commandes effectuees: " + Esimeal.nbrcommandeeffectuer(deb, fin));
                    result.getItems().add("Par type: Sur place: " + Esimeal.NbrcmndEffect(deb, fin, 0) + ", A domicile: " + Esimeal.NbrcmndEffect(deb, fin, 1) + ", Evenement: " +
                            Esimeal.NbrcmndEffect(deb, fin, 2));
                    result.getItems().add("Le montant totale des reductions offertes: " + Esimeal.montantTotalReduction(deb, fin));
                    result.getItems().add("Par type: Fidelite: " + Esimeal.montantTotalReductionType(deb, fin, 0) + ", Etudiant: " + Esimeal.montantTotalReductionType(deb, fin, 1)
                            + ", Groupe a domicile: " + Esimeal.montantTotalReductionType(deb, fin, 2) + ", Evenement: " + Esimeal.montantTotalReductionType(deb, fin, 3));
                    result.getItems().add("Le met le plus commande: " + Esimeal.Metpluscommander(deb, fin));
                    result.getItems().add("Le met le moins commande: " + Esimeal.Metmoincommander(deb, fin));
                    result.getItems().add("Le client ayant effectue le plus de commandes: " + Esimeal.PlusCmndClient(deb, fin));
                    result.getItems().add("Par type: Sur place: " + Esimeal.PlusCmndVarClient(deb, fin, 0) + ", A domicile: " + Esimeal.PlusCmndVarClient(deb, fin, 1)
                            + ", Evenement" + Esimeal.PlusCmndVarClient(deb, fin, 2));
                    result.getItems().add("Le client ayant beneficié le plus grand nombres de réductions: " + Esimeal.plusNbReduction(deb, fin));
                    result.getItems().add("Par type: Fidelité: " + Esimeal.plusNbReductionType(deb, fin, 0) + ", Etudiant: " + Esimeal.plusNbReductionType(deb, fin, 1)
                            + ", Groupe a domicile: " + Esimeal.plusNbReductionType(deb, fin, 2) + ", Evenement: " + Esimeal.plusNbReductionType(deb, fin, 3));
                }
             dateDeb.setValue(null);
            dateFin.setValue(null);
        });
        back.setOnMouseClicked(e ->{
            try {
                Acuille accl = new Acuille();
                hide();
                accl.show();
            }catch (Exception ee){}
        });
    }

    public ObservableList<Repas> getRepas(){
        ObservableList<Repas> list = FXCollections.observableArrayList();

        for (Met i : Esimeal.getMt()){
            if(i instanceof Repas) list.add((Repas) i);
        }
        return list;
    }

    public ObservableList<Commandext> getCmdExt(){
        ArrayList<Commande> l = new ArrayList<>(Esimeal.getCmnd());
        ObservableList<Commandext> list = FXCollections.observableArrayList();

        for (Commande i : l){
            if(i instanceof Commandext) list.add((Commandext) i);
        }
        return list;
    }
    public ObservableList<Boisson> getBoisson(){
        ObservableList<Boisson> list = FXCollections.observableArrayList();

        for (Met i : Esimeal.getMt()){
            if(i instanceof Boisson) list.add((Boisson) i);
        }
        return list;
    }public ObservableList<Commandelivre> getCmdLivre(){
        ObservableList<Commandelivre> list = FXCollections.observableArrayList();

        for (Commande i : Esimeal.getCmnd()){
            if(i instanceof Commandelivre) list.add((Commandelivre) i);
        }
        return list;
    }
    public ObservableList<Evenment> getEvent(){
        ObservableList<Evenment> list = FXCollections.observableArrayList();

        for (Commande i : Esimeal.getCmnd()){
            if(i instanceof Evenment) list.add((Evenment) i);
        }
        return list;
    }
    public ObservableList<Table> getTable(){
        ObservableList<Table> list = FXCollections.observableArrayList();

        for (Table i : Esimeal.getTable()){
            list.add((Table) i);
        }
        return list;
    }
    public ObservableList<Suplment> getSup(){
        ObservableList<Suplment> list = FXCollections.observableArrayList();

        for (Suplment i : Esimeal.getSuplement()){
            list.add((Suplment) i);
        }
        return list;
    }
    public void setElementsSup(HBox sups){
        sups.getChildren().clear();
        for (Suplment i : Esimeal.getSuplement()){
            CheckBox supCheckBox = new CheckBox(i.getNom());
            sups.getChildren().add(supCheckBox);
        }
    }

}
