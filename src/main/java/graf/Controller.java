package graf;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    @FXML
    private TextField xtext;
    @FXML
    private TextField ytext;
    @FXML
    private TextField mintext;
    @FXML
    private TextField maxtext;
    @FXML
    private TextField ntext;
    @FXML
    private TextField outtext;
    @FXML
    private TextField intext;
    @FXML
    private TextField pstext;
    @FXML
    private TextField pktext;

    @FXML
    private TextField errorField;
    @FXML
    private TextField pathValueField;
    @FXML
    private TextField connectivityStatusField;

    @FXML
    Button generuj = new Button();
    @FXML
    Button sprawdz_sciezke = new Button();
    @FXML
    Button wczytaj = new Button();
    @FXML
    Button sprawdz_spojnosc = new Button();
    @FXML
    RadioButton generujgraf = new RadioButton();
    @FXML
    RadioButton wczytajgraf = new RadioButton();
    @FXML
    ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    private int getXtext() {
        if (xtext.getText().trim().equals(""))
            return -1;
        else
        {
        String xVal = xtext.getText();
            return Integer.parseInt(xVal);
        }
    }
    @FXML
    private int getYtext() {
        if(ytext.getText().trim().equals(""))
            return -1;
        else
        {
        String yVal  = ytext.getText();
            return Integer.parseInt(yVal);
        }
    }
    @FXML
    private double getmintext() {
        if(mintext.getText().trim().equals(""))
            return 0;

        String minVal  = mintext.getText();

        return Double.parseDouble(minVal);
    }
    @FXML
    private double getMaxText() {
        if(maxtext.getText().trim().equals(""))
            return 1;

        String maxVal  = maxtext.getText();

        return Double.parseDouble(maxVal);
    }
    @FXML
    private int getNText() {
        if(ntext.getText().trim().equals(""))
            return 1;

        String nVal  = ntext.getText();

        return Integer.parseInt(nVal);
    }
    @FXML
    private int getPStext() {
        if(pstext.getText().trim().equals(""))
            return 0;
        else
        {
            String psVal  = pstext.getText();
            return Integer.parseInt(psVal);
        }
    }

    @FXML
    private int getPKtext() {
        if(pktext.getText().trim().equals(""))
        {
            return -1;
        }
        else {
            String pkVal = pktext.getText();
            return Integer.parseInt(pkVal);
        }
    }
    @FXML
    private String getOutputText() {
        if(outtext.getText().trim().equals(""))
            return "dane/mygraph";

        return "dane/"+outtext.getText();
    }
    @FXML
    private String getInputText() {
        if(intext.getText().trim().equals(""))
            errorField.setText("Nie wprowadzono nazwy pliku wejściowego 'in'.");

        return "dane/"+intext.getText();
    }

    @FXML
    protected void onGenerujButtonClick()
    {
        if(generujgraf.isSelected()) {
            generuj.setOnAction(e -> {
                String path = getOutputText();
                File file = new File(path);
                String absolutePath = file.getAbsolutePath();
                int x = getXtext();
                int y = getYtext();
                double min = getmintext();
                double max = getMaxText();
                int n = getNText();
                int ps = getPStext();
                int pk = getPKtext();
                String output = getOutputText();

                if (max < 0 || max > 1) {
                    errorField.setText("Niepoprawna wartość parametru 'max'. (0 <= max <= 1)");
                } else if (min < 0 || min > 1) {
                    errorField.setText("Niepoprawna wartość parametru 'min'. (0 <= min <= 1)");
                } else if (y < 0 || y > 1000000) {
                    errorField.setText("Niepoprawna wartość parametru 'y'. (1 <= y <= 1000000)");
                } else if (x < 0 || x > 1000000) {
                    errorField.setText("Niepoprawna wartość parametru 'x'. (1 <= x <= 1000000)");
                } else if (x * y > 1000000) {
                    errorField.setText("Niepoprawna wartości parametrów 'x' oraz 'y'. (1 <= x*y <= 1000000)");
                } else if (n < 1 || n > x * y / 4) {
                    errorField.setText("Niepoprawna wartość parametru 'n'. (1 <= n <= x*y/4)");
                } else {
                    pathValueField.setText("");
                    connectivityStatusField.setText("");
                    intext.setText("");

                    Generate g  = new Generate();
                    g.generateGraph(x, y, min, max, n, output);
                    errorField.setText("Plik z grafem został zapisany w: " + output);

                                    Zoom z = new Zoom();
                                    try {
                                        z.start(new Stage(), x,y,ps,pk,path);
                                    } catch (FileNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                    }

                                    pstext.setText(""+10);


                }
            });




        }
    }

    @FXML
    protected void onSprawdzSciezkeButtonClick() {
        sprawdz_sciezke.setOnAction(e -> {
            int ps = getPStext();
            int pk = getPKtext();
            String filename = null;

            if(generujgraf.isSelected())
            {
                filename = getOutputText();
            }
            else if (wczytajgraf.isSelected())
            {
                filename = getInputText();
            }

            if (ps < 0 || ps > pk)
            {
                errorField.setText("Niepoprawna wartość parametru 'ps'.");
            }

            assert filename != null;
            if(!filename.trim().equals("") && ps>=0 && pk>=0)
            {
                ShortestPath shortestPath = new ShortestPath();
                try
                {
                    ArrayList<Number> pathInfo = shortestPath.getShortestPath(filename, ps, pk);
                    pathValueField.setText(String.valueOf(pathInfo.get(0)));
                }
                catch (FileNotFoundException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @FXML
    protected void onSprawdzSpojnoscButtonClick() {
        sprawdz_spojnosc.setOnAction(e -> {
            int ps = 0;
            String filename = null;

            if(generujgraf.isSelected())
            {
                filename = getOutputText();
            }
            else if (wczytajgraf.isSelected())
            {
                filename = getInputText();
            }

            assert filename != null;

            if(!filename.trim().equals(""))
            {
                Connectivity check = new Connectivity();
                try
                {
                    String status = check.CheckConnectivity(filename, ps);
                    connectivityStatusField.setText(status);
                }
                catch (FileNotFoundException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @FXML
    protected void onWczytajButtonClick() {
        String path = getInputText();
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();
        int ps = getPStext();
        int pk = getPKtext();

        FromFile check = new FromFile();
        if(wczytajgraf.isSelected()) {
            if (file.exists()) {
                if (!check.checkFileFormat(absolutePath)) {
                    errorField.setText("Zly format pliku wejściowego: " + absolutePath);
                } else {
                    wczytaj.setOnAction(e -> {
                        pathValueField.setText("");
                        connectivityStatusField.setText("");
                        Zoomv2 z = new Zoomv2();
                        try {
                            z.start1(new Stage(),path,ps,pk);
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                }
            }
        }
    }

    @FXML
    protected void onWczytajGrafRadioIsSelected()
    {
        if(wczytajgraf.isSelected())
        {
            xtext.setText("");
            ytext.setText("");
            mintext.setText("");
            maxtext.setText("");
            ntext.setText("");
            outtext.setText("");
            intext.setText("");
            pstext.setText("");
            pktext.setText("");
            pathValueField.setText("");
            connectivityStatusField.setText("");
            errorField.setText("");
            errorField.setText("Wybrano tryb: Wczytanie grafu z pliku.");

            xtext.setEditable(false);
            ytext.setEditable(false);
            mintext.setEditable(false);
            maxtext.setEditable(false);
            ntext.setEditable(false);
            outtext.setEditable(false);
            intext.setEditable(true);
        }
    }

    @FXML
    protected void onGenerujGrafRadioIsSelected() {
        if(generujgraf.isSelected())
        {
            xtext.setText("");
            ytext.setText("");
            mintext.setText("");
            maxtext.setText("");
            ntext.setText("");
            outtext.setText("");
            intext.setText("");
            pstext.setText("");
            pktext.setText("");
            pathValueField.setText("");
            connectivityStatusField.setText("");
            errorField.setText("Wybrano tryb: Generacja grafu");

            xtext.setEditable(true);
            ytext.setEditable(true);
            mintext.setEditable(true);
            maxtext.setEditable(true);
            ntext.setEditable(true);
            outtext.setEditable(true);
            intext.setEditable(false);
        }
    }




}