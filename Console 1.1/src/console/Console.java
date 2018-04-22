/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package console;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Dr.Gero
 */
public class Console extends Application {
    
    List<Socket> consolesocket = new ArrayList<>();
    List<DataOutputStream> consoleout = new ArrayList<>();
    List<DataInputStream> consolein = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage) {
        //Create Controls
        final TextArea terminalwindow = new TextArea("►◄ Red Ribbon ►◄"); //terminal window
        terminalwindow.setPrefSize(625, 300);
        terminalwindow.setLayoutY(385);
        terminalwindow.setLayoutX(327.5);
        terminalwindow.setWrapText(true);
        terminalwindow.setEditable(false);
        final TextField terminal = new TextField(); //terminal
        terminal.setPrefWidth(625);
        terminal.setLayoutY(690);
        terminal.setLayoutX(327.5);
        final TextArea dpolog = new TextArea("Data Protocol Out"); //data protocol out log
        dpolog.setPrefSize(317.5, 365);
        dpolog.setLayoutY(10);
        dpolog.setLayoutX(0);
        dpolog.setWrapText(true);
        dpolog.setEditable(false);
        final TextArea dpilog = new TextArea("Data Protocol In"); //data protocol in log
        dpilog.setPrefSize(317.5, 365);
        dpilog.setLayoutY(10);
        dpilog.setLayoutX(962.5);
        dpilog.setWrapText(true);
        dpilog.setEditable(false);
        final ObservableList<identification> identifications = FXCollections.observableArrayList();  //androids identification table view
        TableColumn androidCol = new TableColumn();
        androidCol.setText("Android");
        androidCol.setCellValueFactory(new PropertyValueFactory("android"));
        androidCol.setMaxWidth(100);
        androidCol.setMinWidth(100);
        TableColumn statusCol = new TableColumn();
        statusCol.setText("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory("status"));
        statusCol.setMaxWidth(100);
        statusCol.setMinWidth(100);
        TableColumn ipCol = new TableColumn();
        ipCol.setText("IP");
        ipCol.setCellValueFactory(new PropertyValueFactory("ip"));
        ipCol.setMaxWidth(100);
        ipCol.setMinWidth(100);
        TableColumn osCol = new TableColumn();
        osCol.setText("OS");
        osCol.setCellValueFactory(new PropertyValueFactory("os"));
        osCol.setMaxWidth(100);
        osCol.setMinWidth(100);
        TableColumn countryCol = new TableColumn();
        countryCol.setText("Country");
        countryCol.setCellValueFactory(new PropertyValueFactory("country"));
        countryCol.setMaxWidth(100);
        countryCol.setMinWidth(100);
        TableColumn userCol = new TableColumn();
        userCol.setText("User");
        userCol.setCellValueFactory(new PropertyValueFactory("user"));
        userCol.setMaxWidth(100);
        userCol.setMinWidth(100);
        TableColumn computerCol = new TableColumn();
        computerCol.setText("Computer");
        computerCol.setCellValueFactory(new PropertyValueFactory("computer"));
        computerCol.setMaxWidth(100);
        computerCol.setMinWidth(100);
        TableColumn jreCol = new TableColumn();
        jreCol.setText("JRE");
        jreCol.setCellValueFactory(new PropertyValueFactory("jre"));
        jreCol.setMaxWidth(100);
        jreCol.setMinWidth(100);
        TableView androidsidtv = new TableView();
        androidsidtv.setItems(identifications);
        androidsidtv.getColumns().addAll(androidCol, statusCol, ipCol, osCol, countryCol, userCol, computerCol, jreCol);
        androidsidtv.setPrefSize(625, 365);
        androidsidtv.setLayoutY(10);
        androidsidtv.setLayoutX(327.5);
        /*Accordion accordion = new Accordion (); //accordion
        TitledPane tp = new TitledPane("Reconiasance", new Button("Button"));
        TitledPane xxx = new TitledPane("Espionage", new Button("Button"));
        TitledPane asd = new TitledPane("Sabotage", new TextArea());
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("First Name: "), 0, 0);
        grid.add(new TextField(), 1, 0);
        grid.add(new Label("Last Name: "), 0, 1);
        grid.add(new TextField(), 1, 1);
        grid.add(new Label("Email: "), 0, 2);
        grid.add(new TextField(), 1, 2);
        asd.setContent(grid);
        accordion.getPanes().addAll(tp,asd,xxx);
        accordion.setPrefSize(317.5, 327);
        accordion.setLayoutY(385);
        accordion.setLayoutX(962.5);
         Accordion accordion2 = new Accordion (); //accordion2
        TitledPane tp2 = new TitledPane("Tools", new Button("Button"));
        TitledPane xxx2 = new TitledPane("Settings", new Button("Button"));
        TitledPane asd2 = new TitledPane("Help", new TextArea());
        GridPane grid2 = new GridPane();
        grid2.setVgap(4);
        grid2.setPadding(new Insets(5, 5, 5, 5));
        grid2.add(new Label("First Name: "), 0, 0);
        grid2.add(new TextField(), 1, 0);
        grid2.add(new Label("Last Name: "), 0, 1);
        grid2.add(new TextField(), 1, 1);
        grid2.add(new Label("Email: "), 0, 2);
        grid2.add(new TextField(), 1, 2);
        asd2.setContent(grid2);
        accordion2.getPanes().addAll(tp2,asd2,xxx2);
        accordion2.setPrefSize(317.5, 327);
        accordion2.setLayoutY(385);
        accordion2.setLayoutX(0);*/
        
        //Initialize Stage
        Pane root = new Pane();
        root.getChildren().addAll(terminalwindow, terminal, dpolog, dpilog, androidsidtv);
        Scene scene = new Scene(root, 1280, 720);
        scene.setFill(null);
        primaryStage.centerOnScreen();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Console");
        primaryStage.setScene(scene);
        primaryStage.getScene().getStylesheets().add(getClass().getClassLoader().getResource("resources/black.css").toExternalForm());
        primaryStage.show();
        
        //Initialize Controls
        ScrollBar sblog = (ScrollBar)dpolog.lookup(".scroll-bar:vertical");
        sblog.setDisable(true);
        sblog.setStyle("-fx-opacity: 0;");
        ScrollBar sblog2 = (ScrollBar)dpilog.lookup(".scroll-bar:vertical");
        sblog2.setDisable(true);
        sblog2.setStyle("-fx-opacity: 0;");
        
        //StartUp
        
        //Terminal
        terminal.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle(javafx.event.ActionEvent event) {
                String command = terminal.getText();
                terminal.clear();
                terminalwindow.appendText("\n┌ " + command);
                
                if(command.equals("exit")){
                    System.exit(0);
                } else if(command.startsWith("connect")){
                    new connect(command, terminalwindow, consolesocket, consolein, consoleout, dpilog, dpolog, identifications).start();
                } else if(command.startsWith("disconnect")){
                    new disconnect(terminalwindow, consolesocket, consolein, consoleout, identifications).start();
                } else if (command.contains("►")||command.contains("◄")){
                    new directive(terminalwindow, dpolog, command, consoleout).start();
                } else {
                    terminalwindow.appendText("\n└─ Unknown Command");
                }
            }
            
        });
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
