package graf;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class PannableCanvas extends Pane {

    DoubleProperty myScale = new SimpleDoubleProperty(1.0);

    final Integer X = 460;
    final Integer Y = 555;

    public PannableCanvas() {
        setPrefSize(X, Y);
        setStyle("-fx-background-color: lightgrey; -fx-border-color: blue;");

        // add scale transform
        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);
    }

    /**
     * Add a grid to the canvas, send it to back
     */
    public void addGrid() {

        double w = getBoundsInLocal().getWidth();
        double h = getBoundsInLocal().getHeight();

        // add grid
        Canvas grid = new Canvas(X, Y);

        // don't catch mouse events
        grid.setMouseTransparent(true);

        GraphicsContext gc = grid.getGraphicsContext2D();

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);

        // draw grid lines
        double offset = 50;
        for( double i=offset; i < w; i+=offset) {
            gc.strokeLine( i, 0, i, h);
            gc.strokeLine( 0, i, w, i);
        }

        getChildren().add( grid);

        grid.toBack();
    }

    public double getScale() {
        return myScale.get();
    }

    public void setScale( double scale) {
        myScale.set(scale);
    }

    public void setPivot( double x, double y) {
        setTranslateX(getTranslateX()-x);
        setTranslateY(getTranslateY()-y);
    }
}


/**
 * Mouse drag context used for scene and nodes.
 */
class DragContext {

    double mouseAnchorX;
    double mouseAnchorY;

    double translateAnchorX;
    double translateAnchorY;

}

/**
 * Listeners for making the nodes draggable via left mouse button. Considers if parent is zoomed.
 */
class NodeGestures {

    private DragContext nodeDragContext = new DragContext();

    PannableCanvas canvas;

    public NodeGestures( PannableCanvas canvas) {
        this.canvas = canvas;

    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {

            // left mouse button => dragging
            if( !event.isPrimaryButtonDown())
                return;

            nodeDragContext.mouseAnchorX = event.getSceneX();
            nodeDragContext.mouseAnchorY = event.getSceneY();

            Node node = (Node) event.getSource();

            ((Shape) node).setFill(Color.RED);
            nodeDragContext.translateAnchorX = node.getTranslateX();
            nodeDragContext.translateAnchorY = node.getTranslateY();

        }

    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {

            // left mouse button => dragging
            if( !event.isPrimaryButtonDown())
                return;
            double scale = canvas.getScale();

            Node node = (Node) event.getSource();

            node.setTranslateX(nodeDragContext.translateAnchorX + (( event.getSceneX() - nodeDragContext.mouseAnchorX) / scale));
            node.setTranslateY(nodeDragContext.translateAnchorY + (( event.getSceneY() - nodeDragContext.mouseAnchorY) / scale));

            event.consume();

        }
    };
}

/**
 * Listeners for making the scene's canvas draggable and zoomable
 */
class SceneGestures {

    private static final double MAX_SCALE = 1000.0d;
    private static final double MIN_SCALE = .1d;

    private DragContext sceneDragContext = new DragContext();

    PannableCanvas canvas;

    public SceneGestures( PannableCanvas canvas) {
        this.canvas = canvas;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {

            // right mouse button => panning
            if( !event.isSecondaryButtonDown())
                return;

            sceneDragContext.mouseAnchorX = event.getSceneX();
            sceneDragContext.mouseAnchorY = event.getSceneY();

            sceneDragContext.translateAnchorX = canvas.getTranslateX();
            sceneDragContext.translateAnchorY = canvas.getTranslateY();

        }

    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {

            // right mouse button => panning
            if( !event.isSecondaryButtonDown())
                return;

            canvas.setTranslateX(sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX);
            canvas.setTranslateY(sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY);

            event.consume();
        }
    };

    /**
     * Mouse wheel handler: zoom to pivot point
     */
    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

        @Override
        public void handle(ScrollEvent event) {

            double delta = 1.2;

            double scale = canvas.getScale(); // currently we only use Y, same value is used for X
            double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = clamp( scale, MIN_SCALE, MAX_SCALE);

            double f = (scale / oldScale)-1;

            double dx = (event.getSceneX() - (canvas.getBoundsInParent().getWidth()/2 + canvas.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (canvas.getBoundsInParent().getHeight()/2 + canvas.getBoundsInParent().getMinY()));

            canvas.setScale( scale);

            // note: pivot value must be untransformed, i. e. without scaling
            canvas.setPivot(f*dx, f*dy);

            event.consume();

        }

    };


    public static double clamp( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }
}



/**
 * An application with a zoomable and pannable canvas.
 */
public class Zoom extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    final Double X = 460.0;
    final Double Y = 555.0;

    int x = 10;
    int y = 15;
    int n = x*y;
    int k = 0;
    double skala = 0;
    double xd = x;
    double yd = y;

    Circle[] circArray = new Circle[n];
    Line[] lineArray1 = new Line[n-y];
    Line[] lineArray2 = new Line[n-x];
    Text text = new Text();

    @Override
    public void start(Stage stage) {

        Group group = new Group();

        // create canvas
        PannableCanvas canvas = new PannableCanvas();

        // we don't want the canvas on the top/left in this example => just
        // translate it a bit
        canvas.setTranslateX(100);
        canvas.setTranslateY(100);

        // create sample nodes which can be dragged
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
        }
        else{
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



        }

        canvas.getChildren().addAll(circArray);
        canvas.getChildren().addAll(text);

        k=0;
        for(int i = 0; i<x-1; i++) {
            for(int j =0;j<y;j++){
                canvas.getChildren().addAll(lineArray1[k]);
                k++;
            }
        }



        k=0;
        for(int i = 0; i<x; i++) {
            for(int j =0;j<y-1;j++){

                canvas.getChildren().addAll(lineArray2[k]);
                k++;
            }
        }


        group.getChildren().add(canvas);

        // create scene which can be dragged and zoomed
        Scene scene = new Scene(group, 1024, 768);

        SceneGestures sceneGestures = new SceneGestures(canvas);
        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scene.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        stage.setScene(scene);
        stage.show();

        canvas.addGrid();

    }

}