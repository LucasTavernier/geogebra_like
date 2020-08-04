package demo;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.glass.ui.Pixels.Format;
import com.sun.org.apache.xpath.internal.operations.Number;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import transforms.Composition;
import transforms.IComposition;
import transforms.LibraryException;
import transforms.elementaires.Homothetie;
import transforms.elementaires.Rotation;
import transforms.elementaires.Transformation;
import transforms.elementaires.Translation;
import transforms.mobile.Motif;

public class DemoController {

	@FXML
	Pane pane;

	@FXML
	ToggleButton displayAll;

	@FXML
	ToggleButton zoom;



	@FXML
	TitledPane translation;
	@FXML
	TextField tfHT;

	@FXML
	TextField tfVT;

	@FXML
	Slider sliderHT;

	@FXML
	Slider sliderVT;




	@FXML
	TitledPane rotation;
	@FXML
	TextField tfHR;
	@FXML
	TextField tfVR;
	@FXML
	Slider sliderHR;
	@FXML
	Slider sliderVR;
	@FXML
	TextField cyR;



	@FXML
	TitledPane homothetie;
	@FXML
	TextField tfHH;
	@FXML
	TextField tfVH;
	@FXML
	Slider sliderHH;
	@FXML
	Slider sliderVH;
	@FXML
	TextField cyH;



	@FXML
	Slider zoomSlider;	

	@FXML
	Button ajouter;

	@FXML
	Button effacer;

	@FXML
	ListView<String> list = new ListView<String>();

	@FXML
	Label MatricesTitle;

	@FXML
	Label MatriceContenu;

	private IComposition composition = new Composition();
	private ArrayList<Transformation> mvts = new ArrayList<Transformation>();
	private List<Node> allNodes;
	private ArrayList<Boolean> display = new ArrayList<>();
	private NumberFormat format = NumberFormat.getInstance();

	public void initialize() {
		pane.getChildren().add(composition.getGrille(pane));
		format.setMaximumFractionDigits(2);
		display.add(true);
	}

	@SuppressWarnings("unused")
	public void doMontrer(ActionEvent actionEvent) {
		if (allNodes == null && (!mvts.isEmpty())) {
			try {
				allNodes = composition.draw(display);
			} catch (LibraryException e) {
			}
		}
		try {
			if (displayAll.isSelected()) {
				pane.getChildren().addAll(allNodes);
			} else {
				pane.getChildren().removeAll(allNodes);
			}
		} catch (NullPointerException e) {
		}
	}

	public void doMatrices(ActionEvent actionEvent) {
		if(!mvts.isEmpty() && (!display.isEmpty())) {
			try {
				MatricesTitle.setDisable(false);
				MatricesTitle.setVisible(true);
				MatriceContenu.setDisable(false);
				MatriceContenu.setVisible(true);
				MatriceContenu.setText(Arrays.deepToString(composition.getComposedMatrix(mvts.size())));
			} catch (LibraryException e) {
			}
		}
	}

	public void doAnimer(ActionEvent actionEvent) {
		if(!mvts.isEmpty() && !display.isEmpty()) {
			if(!displayAll.isSelected()) {
				displayAll.setSelected(true);
				displayAll.setSelected(false);
			}
			try {
				final Motif mobile = composition.getStep(0);
				mobile.setStroke(Color.BLUE);
				pane.getChildren().add(mobile.toGroup());
				composition.animate(mobile.toGroup(), 0, (mvts.size()), e -> pane.getChildren().remove(mobile.toGroup())).play(); // Animation

			} catch (LibraryException e) {
			}
		}
	}

	public void showCoord(MouseEvent mouseEvent) {
		double xMouse = mouseEvent.getX();
		double yMouse = mouseEvent.getY();
		System.out.println("X vaut : " + composition.xMouseToMath(xMouse));
		System.out.println("Y vaut : " + composition.yMouseToMath(yMouse));
	}

	public void doZoom(ActionEvent actionEvent) {
		if (!zoom.isSelected()) {
			zoomSlider.setDisable(true);
			zoomSlider.setOpacity(0);
		} else {
			zoomSlider.setDisable(false);
			zoomSlider.setOpacity(1);
		}
	}


	public void updateSliderHT() {
		sliderHT.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				tfHT.setText("" + (format.format(sliderHT.getValue())).replace(',', '.') );
			}
		});
	}

	public void updateSliderVT() {
		sliderVT.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				tfVT.setText("" +(format.format(sliderVT.getValue())).replace(',', '.') );
			}
		});
	}

	public void updateTFHT(ActionEvent action) {
		try {
			if (Double.parseDouble(tfHT.getText()) > 100) {
				tfHT.setText("100.0");
			}
			if (Double.parseDouble(tfHT.getText()) < -100) {
				tfHT.setText("-100.0");
			}
			sliderHT.setValue(Double.parseDouble(tfHT.getText()));
		} catch (NumberFormatException j) {
			sliderHT.setValue(0.0);
			tfHT.setText("0.0");
		}
	}

	public void updateTFVT() {
		try {
			if (Double.parseDouble(tfVT.getText()) > 100) {
				tfVT.setText("100.0");
			}
			if (Double.parseDouble(tfVT.getText()) < -100) {
				tfVT.setText("-100.0");
			}
			sliderVT.setValue(Double.parseDouble(tfVT.getText()));
		} catch (NumberFormatException j) {
			sliderVT.setValue(0.0);
			tfVT.setText("0.0");
		}
	}



	public void updateSliderHR() {
		sliderHR.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				tfHR.setText("" +(format.format(sliderHR.getValue())).replace(',', '.') );
			}
		});
	}

	public void updateTFHR(ActionEvent action) {
		try {
			if (Double.parseDouble(tfHR.getText()) > 360) {
				tfHR.setText("360.0");
			}
			if (Double.parseDouble(tfHR.getText()) < -360) {
				tfHR.setText("-360.0");
			}
			sliderHR.setValue(Double.parseDouble(tfHR.getText()));
		} catch (NumberFormatException j) {
			sliderHR.setValue(0.0);
			tfHR.setText("0.0");
		}
	}

	public void updateTFVR() {
		try {
			if (Double.parseDouble(tfVR.getText()) > 100) {
				tfVR.setText("100.0");
			}
			if (Double.parseDouble(tfVR.getText()) < -100) {
				tfVR.setText("-100.0");
			}
		} catch (NumberFormatException j) {
			tfVR.setText("0.0");
		}
	}




	public void updateSliderHH() {
		sliderHH.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				tfHH.setText("" + (format.format(sliderHH.getValue())).replace(',', '.') );
			}
		});
	}

	public void updateTFHH(ActionEvent action) {
		try {
			if (Double.parseDouble(tfHH.getText()) > 100) {
				tfHH.setText("100.0");
			}
			if (Double.parseDouble(tfHH.getText()) < -100) {
				tfHH.setText("-100.0");
			}
			sliderHH.setValue(Double.parseDouble(tfHH.getText()));
		} catch (NumberFormatException j) {
			sliderHH.setValue(0.0);
			tfHH.setText("0.0");
		}
	}

	public void updateTFVH() {
		try {
			if (Double.parseDouble(tfVH.getText()) > 100) {
				tfVH.setText("100.0");
			}
			if (Double.parseDouble(tfVH.getText()) < -100) {
				tfVH.setText("-100.0");
			}
		} catch (NumberFormatException j) {
			sliderVH.setValue(0.0);
			tfVH.setText("0.0");
		}
	}


	public void updateTranslation(MouseEvent action) {
		if (translation.isExpanded()) {
			tfHT.setText("0.0");
			tfVT.setText("0.0");
			sliderHT.setValue(0.0);
			sliderVT.setValue(0.0);
		}
	}

	public void updateRotation(MouseEvent action) {
		if (rotation.isExpanded()) {
			tfHR.setText("0.0");
			tfVR.setText("0.0");
			cyR.setText("0.0");
			sliderHR.setValue(0.0);
		}
	}

	public void updateHomothetie(MouseEvent action) {
		if (homothetie.isExpanded()) {
			tfHH.setText("0.0");
			tfVH.setText("0.0");
			cyH.setText("0.0");
			sliderHH.setValue(0.0);
		}
	}

	public void ajouter() {
		Transformation t = null;

		if(displayAll.isSelected() && allNodes !=null) {
			pane.getChildren().removeAll(allNodes);
			displayAll.setSelected(false);
			displayAll.setSelected(true);
		}
		
		if(MatricesTitle.isVisible()) {
			MatricesTitle.setDisable(true);
			MatricesTitle.setVisible(false);
			MatriceContenu.setDisable(true);
			MatriceContenu.setVisible(false);
		}
		try {
			if (translation.isExpanded()) {
				t = new Translation(Double.parseDouble(tfHT.getText()), Double.parseDouble(tfVT.getText()));
				mvts.add(t);

				list.getItems().add("Translation (" + Double.parseDouble(tfHT.getText()) + " ; " + Double.parseDouble(tfVT.getText()) + ")");
				composition.add(t);
				display.add(true);
			}

			else if (rotation.isExpanded()) {
				t = new Rotation(Double.parseDouble(tfHR.getText()), Double.parseDouble(tfVR.getText()),
						Double.parseDouble(cyR.getText()));
				mvts.add(t);

				list.getItems().add("Rotation (" + Double.parseDouble(tfHR.getText()) + " ; " + Double.parseDouble(tfVR.getText()) + "; "
						+ Double.parseDouble(cyR.getText()) + ")");

				composition.add(t);
				display.add(true);
			} 
			else if (homothetie.isExpanded()) {
				t = new Homothetie(Double.parseDouble(tfHH.getText()), Double.parseDouble(tfVH.getText())*-1,
						Double.parseDouble(cyH.getText())*-1);
				mvts.add(t);

				list.getItems().add("Homothéthie (" + Double.parseDouble(tfHH.getText()) + " ; " + Double.parseDouble(tfVH.getText()) + " ; " +
						Double.parseDouble(cyH.getText()));
				composition.add(t);
				display.add(true);
			}

			allNodes = null;
		} catch (NumberFormatException n) {
			tfHT.setText("0.0");
			tfVT.setText("0.0");
			sliderHT.setValue(0.0);
			sliderVT.setValue(0.0);
			
			
			tfHR.setText("0.0");
			tfVR.setText("0.0");
			cyR.setText("0.0");
			sliderHR.setValue(0.0);

			tfHH.setText("0.0");
			tfVH.setText("0.0");
			cyH.setText("0.0");
			sliderHH.setValue(0.0);
		}
	}

	public void effacer() {
		if(MatricesTitle.isVisible()) {
			MatricesTitle.setDisable(true);
			MatricesTitle.setVisible(false);
			MatriceContenu.setDisable(true);
			MatriceContenu.setVisible(false);
		}

		if(displayAll.isSelected()) {
			pane.getChildren().removeAll(allNodes);
			displayAll.setSelected(false);
			displayAll.setSelected(true);
		}
		mvts.clear();
		composition.clear();
		display.clear();
		display.add(true);
		allNodes = null;

		list.getItems().clear();
		tfHT.setText("0.0");
		tfVT.setText("0.0");
		sliderHT.setValue(0.0);
		sliderVT.setValue(0.0);

		tfHR.setText("0.0");
		tfVR.setText("0.0");
		cyR.setText("0.0");
		sliderHR.setValue(0.0);

		tfHH.setText("0.0");
		tfVH.setText("0.0");
		cyH.setText("0.0");
		sliderHH.setValue(0.0);
	}

	public void zoomer() {
		zoomSlider.valueProperty().addListener(e->{
			composition.setZoom(zoomSlider.getValue(), pane.getWidth()/2,pane.getHeight()/2);
		});
		
	}
}