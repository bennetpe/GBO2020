package gui.graphics.sinus;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SinusView extends BorderPane
{

    private SinusPresenter presenter;

    private Pane drawingPane;

    private Label formel;

    private Label amplitudeL, frequenzL, phaseL, zoomL;

    private Slider ampSlider, freqSlider, phaseSlider, zoomSlider;

    private VBox sliderBox;

    private Line xAchse, yAchse;

    private Rectangle clippingRecatangle;

    public void setPresenter(SinusPresenter p)
    {
        this.presenter = p;
    }

    public SinusView()
    {
        this.setWidth(400);
        this.setHeight(400);
        initView();
        initSlider();
        initSliderBox();
        addNodes();
        initClipping();
        initCoordinateSystem();
        drawingPane.getChildren().add(new Rectangle(400, 200));
        drawingPane.widthProperty().addListener((obs, oldValue, newValue) ->
        {
            drawingPane.getChildren().clear();
            xAchse = new Line(0, drawingPane.getHeight() / 2, drawingPane.getWidth(), drawingPane.getHeight() / 2);
            yAchse = new Line(drawingPane.getWidth() / 2, 0, drawingPane.getWidth() / 2, drawingPane.getHeight());
            xAchse.setFill(Color.RED);
            yAchse.setFill(Color.GREEN);
            drawingPane.getChildren().addAll(xAchse, yAchse);
        });
        drawingPane.heightProperty().addListener((obs, oldValue, newValue) ->
        {
            drawingPane.getChildren().clear();
            xAchse = new Line(0, drawingPane.getHeight() / 2, drawingPane.getWidth(), drawingPane.getHeight() / 2);
            yAchse = new Line(drawingPane.getWidth() / 2, 0, drawingPane.getWidth() / 2, drawingPane.getHeight());
            xAchse.setFill(Color.RED);
            yAchse.setFill(Color.GREEN);
            drawingPane.getChildren().addAll(xAchse, yAchse);
        });
        // changeWindowSize();
        changeListener();
    }

    public void initClipping()
    {
        clippingRecatangle = new Rectangle(drawingPane.getWidth(), drawingPane.getHeight());
        clippingRecatangle.widthProperty().bind(drawingPane.widthProperty());
        clippingRecatangle.heightProperty().bind(drawingPane.heightProperty());
        drawingPane.setClip(clippingRecatangle);
    }

    public void initView()
    {
        sliderBox = new VBox(10);
        amplitudeL = new Label("Amplitude: \t");
        frequenzL = new Label("Frequenz: \t");
        phaseL = new Label("Phase: \t");
        zoomL = new Label("Zoom: \t");

        drawingPane = new Pane();
        formel = new Label("");
    }

    public void initSlider()
    {
        ampSlider = initSlider(-6, 6, 0, Orientation.VERTICAL);
        ampSlider.setId("amplitude");
        freqSlider = initSlider(0, 40, 0, Orientation.HORIZONTAL);
        freqSlider.setId("frequency");
        phaseSlider = initSlider(-10, 10, 0, Orientation.VERTICAL);
        phaseSlider.setId("phase");
        zoomSlider = initSlider(10, 210, 10, Orientation.HORIZONTAL);
        zoomSlider.setId("zoom");
    }

    public void changeListener()
    {
        ampSlider.valueProperty().addListener((ov, o, n) ->
        {
            fillModel();
            presenter.setFormelText();
            drawSinus();
        });
        freqSlider.valueProperty().addListener((ov, o, n) ->
        {
            fillModel();
            presenter.setFormelText();
            drawSinus();
        });
        phaseSlider.valueProperty().addListener((ov, o, n) ->
        {
            fillModel();
            presenter.setFormelText();
            drawSinus();
        });
        zoomSlider.valueProperty().addListener((ov, o, n) ->
        {
            fillModel();
            presenter.setFormelText();
            drawSinus();
        });
    }

    public void setFormelText(String s)
    {
        formel.setText(s);
    }

    public Slider initSlider(double min, double max, double value, Orientation orientation)
    {
        Slider s = new Slider(min, max, value);
        s.setShowTickLabels(true);
        s.setShowTickMarks(true);
        s.setOrientation(orientation);
        return s;
    }

    public void initSliderBox()
    {
        sliderBox.getChildren().add(new HBox(amplitudeL, ampSlider));
        sliderBox.getChildren().add(new HBox(frequenzL, freqSlider));
        sliderBox.getChildren().add(new HBox(phaseL, phaseSlider));
        sliderBox.getChildren().add(new HBox(zoomL, zoomSlider));
    }

    public void addNodes()
    {
        this.setPadding(new Insets(10));
        this.setTop(formel);
        this.setCenter(drawingPane);
        this.setBottom(sliderBox);
    }

    public void initCoordinateSystem()
    {
        xAchse = new Line(0, drawingPane.getHeight() / 2, drawingPane.getWidth(), drawingPane.getHeight() / 2);
        yAchse = new Line(drawingPane.getWidth() / 2, 0, drawingPane.getWidth() / 2, drawingPane.getHeight());
        xAchse.setFill(Color.RED);
        yAchse.setFill(Color.GREEN);
        drawingPane.getChildren().addAll(xAchse, yAchse);
    }

    public void fillModel()
    {
        presenter.setModel(ampSlider.getValue(), freqSlider.getValue(), zoomSlider.getValue(), phaseSlider.getValue());
    }

    public void drawSinus()
    {
        drawingPane.getChildren().clear();
        initCoordinateSystem();
        double yWert, nextyWert;
        for (int i = 0; i < this.getWidth(); i++)
        {
            yWert = this.presenter.setErgebnisSinusFnct(i);
            nextyWert = this.presenter.setErgebnisSinusFnct(i + 1);
            this.drawingPane.getChildren().add(new Line(i, yWert, i + 1, nextyWert));
            yWert = nextyWert;
        }
    }

    public Pane getDarwingPane()
    {
        return drawingPane;
    }
}
