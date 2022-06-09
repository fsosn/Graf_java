package graf;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Zoomv2 extends Zoom{

    public void start1(Stage stage,String filename,int ps,int pk) throws FileNotFoundException {

        ShortestPath sp = new ShortestPath();
        ArrayList<Number> pathinfo = sp.getShortestPath(filename, ps, pk);

        final Double X = 460.0;
        final Double Y = 583.0;

        FromFile from = new FromFile();

        int x  = from.readX(filename);
        int y = from.readY(filename);
        int n = x*y;

        Circle[] circArray = new Circle[n];
        Line[] lineArray1 = new Line[n-y];
        Line[] lineArray2 = new Line[n-x];
        Text text = new Text();

        int k = 0;
        double skala = 0;
        double xd = x;
        double yd = y;

        Group group = new Group();


        PannableCanvas canvas = new PannableCanvas();


        canvas.setTranslateX(0);
        canvas.setTranslateY(0);


        NodeGestures nodeGestures = new NodeGestures( canvas);

        if(xd>yd){
            skala=yd/xd;
        }
        else if(yd>xd){
            skala=xd/yd;
        }
        else{
            skala=1;
        }

        if(x*y>x && x*y>y){
            k=0;
            for(int i = 0; i<x-1; i++) {
                for(int j =0;j<y;j++){
                    lineArray1[k] = new Line();
                    lineArray1[k].setStrokeWidth(Math.sqrt(((((X-60)*(Y-60))/(x*y)*0.66)/3.15))/20);
                    lineArray1[k].setStartX(30+(X-60)/(x-1)*i);
                    lineArray1[k].setStartY(30 + (Y-60)/(y-1)*j);
                    lineArray1[k].setEndX(30 + (X-60)/(x-1) +(X-60)/(x-1)*i);
                    lineArray1[k].setEndY(30 + (Y-60)/(y-1)*j);
                    k++;
                }
            }


            k=0;
            for(int i = 0; i<x; i++) {
                for(int j =0;j<y-1;j++){
                    lineArray2[k] = new Line();
                    lineArray2[k].setStrokeWidth(Math.sqrt(((((X-60)*(Y-60))/(x*y)*0.66)/3.15))/20);
                    lineArray2[k].setStartX(30 + (X-60)/(x-1)*i);
                    lineArray2[k].setStartY(30+(Y-60)/(y-1)*j);
                    lineArray2[k].setEndX(30 + (X-60)/(x-1)*i);
                    lineArray2[k].setEndY(30 + (Y-60)/(y-1) +(Y-60)/(y-1)*j);
                    k++;
                }
            }

            k=0;
            for(int i = 0; i<y; i++) {
                for(int j =0;j<x;j++){
                    circArray[k] = new Circle();//Adding this line to prevent NPE on circArray[i]
                    circArray[k].setRadius(Math.sqrt(((((X-60)*(Y-60))/(x*y)*0.66*skala)/3.15)));
                    circArray[k].setCenterX(30 + (X-60)/(x-1)*j);
                    circArray[k].setCenterY(30 + (Y-60)/(y-1)*i);
                    circArray[k].setFill(Color.GREEN);
                    circArray[k].addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
                    k++;

                }
            }

        }
        else{
            k=0;
            for(int i = 0; i<x-1; i++) {
                for(int j =0;j<y;j++){
                    lineArray1[k] = new Line();
                    lineArray1[k].setStrokeWidth(Math.sqrt(((((X-60)*(Y-60))/(x*y)*0.66)/3.15))/20);
                    lineArray1[k].setStartX(30 + (X-60)/(x)*i);
                    lineArray1[k].setStartY(Y/2 + (Y-60)/(y)*j);
                    lineArray1[k].setEndX(30 + (X-60)/(x) +(X-60)/(x)*i);
                    lineArray1[k].setEndY(Y/2 + (Y-60)/(y)*j);
                    k++;
                }
            }


            k=0;
            for(int i = 0; i<x; i++) {
                for(int j =0;j<y-1;j++){
                    lineArray2[k] = new Line();
                    lineArray2[k].setStrokeWidth(Math.sqrt(((((X-60)*(Y-60))/(x*y)*0.66)/3.15))/20);
                    lineArray2[k].setStartX(X/2 + (X-60)/(x)*i);
                    lineArray2[k].setStartY(30+(Y-60)/(y)*j);
                    lineArray2[k].setEndX(X/2 + (X-60)/(x)*i);
                    lineArray2[k].setEndY(30 + (Y-60)/(y) +(Y-60)/(y)*j);
                    k++;
                }
            }


            k=0;
            for(int i = 0; i<y; i++) {
                for(int j =0;j<x;j++){
                    circArray[k] = new Circle();//Adding this line to prevent NPE on circArray[i]
                    if(y==1){
                        circArray[k].setRadius(Math.sqrt(((((X-60)*(Y-60))/(x*y))/3.15))/(x));
                        circArray[k].setCenterX(30 + (X-60)/(x)*j);
                        circArray[k].setCenterY(Y/2 + (Y-60)/(y)*i);
                    }
                    else if(x==1){
                        circArray[k].setRadius(Math.sqrt(((((X-60)*(Y-60))/(x*y))/3.15))/(y));
                        circArray[k].setCenterX(X/2 + (X-60)/(x)*j);
                        circArray[k].setCenterY(30 + (Y-60)/(y)*i);
                    }
                    circArray[k].setFill(Color.GREEN);
                    circArray[k].addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
                    k++;

                }
            }
        }
        for (int i = 1; i < pathinfo.size(); i++) {
            circArray[(int) pathinfo.get(i)].setFill(Color.RED);
        }
        System.out.println(pathinfo.size());
        System.out.println(pathinfo);




        canvas.getChildren().addAll(lineArray1);
        canvas.getChildren().addAll(lineArray2);
        canvas.getChildren().addAll(circArray);

        group.getChildren().add(canvas);


        Scene scene = new Scene(group, 460, 583);

        SceneGestures sceneGestures = new SceneGestures(canvas);
        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scene.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        stage.setScene(scene);
        stage.show();

        canvas.addGrid();



    }
}