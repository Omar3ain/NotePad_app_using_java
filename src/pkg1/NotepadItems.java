/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.Scanner;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Omar
 */
public class NotepadItems extends Application {

    MenuBar bar;
    Menu file;
    Menu edit;
    Menu help;
    TextArea area;
    BorderPane pane;
    Scene scene;

    @Override
    public void init() {
        bar = new MenuBar();
        file = new Menu("File");
        edit = new Menu("Edit");
        help = new Menu("Help");

        MenuItem fileMenuItem1 = new MenuItem("New");
        fileMenuItem1.setAccelerator(KeyCombination.keyCombination("Ctrl+n"));
        MenuItem fileMenuItem2 = new MenuItem("Open");
        fileMenuItem2.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));
        MenuItem fileMenuItem3 = new MenuItem("Save");
        fileMenuItem3.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
        SeparatorMenuItem sep1 = new SeparatorMenuItem();
        MenuItem fileMenuItem4 = new MenuItem("Exit");

        MenuItem editMenuItem1 = new MenuItem("Undo");
        editMenuItem1.setAccelerator(KeyCombination.keyCombination("Ctrl+z"));
        SeparatorMenuItem sep2 = new SeparatorMenuItem();
        MenuItem editMenuItem2 = new MenuItem("Cut");
        editMenuItem2.setAccelerator(KeyCombination.keyCombination("Ctrl+x"));
        MenuItem editMenuItem3 = new MenuItem("Copy");
        editMenuItem3.setAccelerator(KeyCombination.keyCombination("Ctrl+c"));
        MenuItem editMenuItem4 = new MenuItem("Paste");
        editMenuItem4.setAccelerator(KeyCombination.keyCombination("Ctrl+v"));
        MenuItem editMenuItem5 = new MenuItem("Delete");
        SeparatorMenuItem sep3 = new SeparatorMenuItem();
        MenuItem editMenuItem6 = new MenuItem("Select All");
        editMenuItem6.setAccelerator(KeyCombination.keyCombination("Ctrl+a"));

        MenuItem helpMenuItem1 = new MenuItem("About Notepad");

        file.getItems().addAll(fileMenuItem1, fileMenuItem2, fileMenuItem3, sep1, fileMenuItem4);
        edit.getItems().addAll(editMenuItem1, sep2, editMenuItem2, editMenuItem3, editMenuItem4, editMenuItem5, sep3, editMenuItem6);
        help.getItems().addAll(helpMenuItem1);
        bar.getMenus().addAll(file, edit, help);
        area = new TextArea();
        area.setPrefColumnCount(15);
        area.setPrefHeight(400);
        area.setPrefWidth(300);

        pane = new BorderPane();
        pane.setTop(bar);
        pane.setCenter(area);

        scene = new Scene(pane, 300, 400);
    }

    public void fileEvents(Stage stage) {
        file.getItems().get(0).setOnAction((ActionEvent event1) -> {
            area.clear();
        });

        FileChooser file_chooser = new FileChooser();
        file_chooser.setTitle("Open File");
        file_chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Document", "*.txt"));
        EventHandler<ActionEvent> event
                = (ActionEvent e) -> {
                    File file_input = file_chooser.showOpenDialog(stage);
                    initialize(file_input.getAbsolutePath(), area);
                };

        file.getItems().get(1).setOnAction(event);

        FileChooser fileSaver = new FileChooser();
        fileSaver.setTitle("Save");
        fileSaver.getExtensionFilters().addAll(new ExtensionFilter("Text Document", "*.txt"));

        file.getItems().get(2).setOnAction((ActionEvent event1) -> {
            File file_output = fileSaver.showSaveDialog(stage);
            fileWriter(file_output, area);
        });

        file.getItems().get(4).setOnAction((ActionEvent event1) -> {
            stage.close();
        });
    }

    public void editEvents(Stage stage) {
        edit.getItems().get(0).setOnAction((ActionEvent event1) -> {
            area.undo();
        });
        edit.getItems().get(2).setOnAction((ActionEvent event1) -> {
            area.cut();
        });
        edit.getItems().get(3).setOnAction((ActionEvent event1) -> {
            area.copy();
        });
        
        edit.getItems().get(4).setOnAction((ActionEvent event1) -> {
            area.paste();
        });
        edit.getItems().get(5).setOnAction((ActionEvent event1) -> {
            area.replaceSelection("");
        });
        edit.getItems().get(7).setOnAction((ActionEvent event1) -> {
            area.selectAll();
        });
    }

    public void aboutEvents(Stage stage) {
        Image img = new Image("E:\\ITI\\JAVA GUI\\day_2\\lab\\Notepad\\src\\images\\logo.jpg");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setFitWidth(50);
        Dialog dialog = new Dialog();

        dialog.setTitle("About Notepad");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);

        dialog.setContentText("Created by Omar medhat © all rights reserved 2023");

        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.setGraphic(view);
        help.setOnAction((ActionEvent event1) -> {
            dialog.showAndWait();
        });
    }

    public void fileWriter(File savePath, TextArea textArea) {
        try {
            try (BufferedWriter bf = new BufferedWriter(new FileWriter(savePath))) {
                bf.write(textArea.getText());
                bf.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void initialize(String url, TextArea area) {
        try {
            try (Scanner input = new Scanner(new File(url))) {
                while (input.hasNext()) {
                    area.appendText(input.nextLine() + '\n');
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: ");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        fileEvents(stage);
        editEvents(stage);
        aboutEvents(stage);
        stage.setTitle("Notepad");
        stage.setScene(scene);
        stage.show();
    }

}
