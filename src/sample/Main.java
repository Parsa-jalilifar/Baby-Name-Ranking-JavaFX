/**
 * @author Parsa Jalilifar
 */
package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    // this makes a layout just like html (top, center, left, right, bottom)
    BorderPane bor;

    public static void main(String args[]) {
        // launch the application
        launch(args);
    }

    // launch the application
    public void start(Stage primaryStage) throws Exception {

        // To set title of screen
        primaryStage.setTitle("Baby Name Ranking");

        // this layout is just like a table which has cells
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 80));

        // Space among elements
        grid.setVgap(30);
        grid.setVgap(10);

        // Year label
        Label yearLabel = new Label("Select a year:");
        GridPane.setConstraints(yearLabel, 0, 0);
        yearLabel.setPadding(new Insets(0, 10, 0, 0));



        // Year input
        ChoiceBox<Integer> yearBox = new ChoiceBox<>();
        yearBox.getItems().addAll(2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010);
        yearBox.setValue(2001);
        GridPane.setConstraints(yearBox, 1, 0);

        // Gender label
        Label genLabel = new Label("Boy or girl?");
        GridPane.setConstraints(genLabel, 0, 1);

        // Gender box
        ChoiceBox<String> genBox = new ChoiceBox<>();
        genBox.getItems().addAll("Male", "Female");
        genBox.setValue("Male");
        GridPane.setConstraints(genBox, 1, 1);

        // Name label
        Label nameLabel = new Label("Enter a name:");
        GridPane.setConstraints(nameLabel, 0, 2);

        // name input
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 2);

        // Button
        Button searchButton = new Button("Find Ranking");
        searchButton.setOnAction(e -> {
            try {
                getChoice(yearBox, genBox, nameInput);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        GridPane.setConstraints(searchButton, 1, 3);

        // Adding elements to layouts
        grid.getChildren().addAll(yearLabel, yearBox, genLabel, genBox, nameLabel, nameInput, searchButton);

        bor = new BorderPane();
        bor.setCenter(grid);

        Scene scene = new Scene(bor, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // to get value of selected
    private void getChoice(ChoiceBox<Integer> yearBox, ChoiceBox<String> genBox, TextField nameInput) throws IOException {

        int year = yearBox.getValue();
        String gender = genBox.getValue();
        String name = nameInput.getText();

        // opening the file according the year that use entered
        BufferedReader inputStream = new BufferedReader(new FileReader("babynamesranking2001to2010/babynamesranking" + year + ".txt"));

        String data = "";
        String rank = "";
        String str = "";


        while ((data = inputStream.readLine()) != null) {

            String[] arr = data.split("\\s+");

            if (gender.equalsIgnoreCase("Male")) {
                if (name.equalsIgnoreCase(arr[1]))
                    rank = arr[0];
                str = "Boy name " + name + " ranked #";
            } else {
                if (name.equalsIgnoreCase(arr[3]))
                    rank = arr[0];
                str = "Girl name " + name + " is ranked #";
            }
        }

        str += rank + " in year " + year;

//        String[] mName = new String[10];
//        String[] fName = new String[10];
//
//        int i = 0;
//        int j = 0;
//
//        if (gender.equalsIgnoreCase("Male")) {
//            mName[i] = name;
//            i++;
//        } else {
//            fName[j] = name;
//            j++;
//        }

        display(str);
    }

    private void display(String r) {

        GridPane grid2 = new GridPane();
        grid2.setPadding(new Insets(10, 30, 10, 30));

        // Space among elements
        grid2.setVgap(30);
        grid2.setVgap(10);

        // Define a text to see the result of out search in files
        Text result = new Text(r);
        result.setFont(Font.font("Times", FontPosture.REGULAR, 14));

        grid2.getChildren().add(result);
        bor.setBottom(grid2);

    }

}
