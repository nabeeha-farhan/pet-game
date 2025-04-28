package group47.cs2212.petgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

/**
 * Controller for the New Game screen in the Pet Game application.
 * <p>
 * This class manages the logic for selecting a pet, viewing pet details, naming the pet,
 * and transitioning between the tutorial, main game, and other scenes.
 * </p>
 *
 * @version 1.0
 */
public class NewGameController {

    /** Group displaying the tutorial reminder. */
    @FXML
    private Group tutorialReminder;

    /** Group displaying the pet selection screen. */
    @FXML
    private Group petSelection;

    /** Group displaying the pet view screen with details about a selected pet. */
    @FXML
    private Group petView;

    /** ImageView for the first pet's image in the selection screen. */
    @FXML
    private ImageView pet1Image;

    /** ImageView for the second pet's image in the selection screen. */
    @FXML
    private ImageView pet2Image;

    /** ImageView for the third pet's image in the selection screen. */
    @FXML
    private ImageView pet3Image;

    /** Label displaying the type of the first pet. */
    @FXML
    private Label pet1Type;

    /** Label displaying the type of the second pet. */
    @FXML
    private Label pet2Type;

    /** Label displaying the type of the third pet. */
    @FXML
    private Label pet3Type;

    /** Button to view details about the first pet. */
    @FXML
    private Button viewPet1;

    /** Button to view details about the second pet. */
    @FXML
    private Button viewPet2;

    /** Button to view details about the third pet. */
    @FXML
    private Button viewPet3;

    /** ImageView displaying the selected pet's image in the pet description view. */
    @FXML
    private ImageView petDescriptionImage;

    /** Label displaying the selected pet's type in the pet description view. */
    @FXML
    private Label petDescriptionType;

    /** Label displaying the description of the selected pet. */
    @FXML
    private Label petDescriptionText;

    /** Label displaying a message when a pet is selected. */
    @FXML
    private Label petSelectionText;

    /** ImageView displaying the selected pet in the confirmation screen. */
    @FXML
    private ImageView selectedPetImage;

    /** TextField for entering the name of the selected pet. */
    @FXML
    private TextField petInputName;

    /** Group displaying the selected pet confirmation screen. */
    @FXML
    private Group petSelectedGroup;

    /** Label displaying an error message if the entered pet name is invalid. */
    @FXML
    private Label invalidNameText;

    /** The currently selected pet. */
    private Pet selectedPet;

    /** Instance of UILoader to handle UI transitions. */
    private UILoader uiLoader;

    /** Image for the first pet. */
    Image petImage1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pet_pictures/pet1_idle.png")));

    /** Image for the second pet. */
    Image petImage2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pet_pictures/pet2_idle.png")));

    /** Image for the third pet. */
    Image petImage3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pet_pictures/pet3_idle.png")));

    /** The first pet object. */
    Pet pet1 = new Pet(null, "pet1", "mouse", petImage1);

    /** The second pet object. */
    Pet pet2 = new Pet(null, "pet2", "bee", petImage2);

    /** The third pet object. */
    Pet pet3 = new Pet(null, "pet3", "dragon", petImage3);

    /**
     * Initializes the controller by setting up the images and details for the pets.
     */
    public void initialize() {
        uiLoader = new UILoader();
        pet1Image.setImage(pet1.petImage);
        pet2Image.setImage(pet2.petImage);
        pet3Image.setImage(pet3.petImage);
        pet1Type.setText(pet1.type);
        pet2Type.setText(pet2.type);
        pet3Type.setText(pet3.type);
        viewPet1.setText("View Info on " + pet1.type);
        viewPet2.setText("View Info on " + pet2.type);
        viewPet3.setText("View Info on " + pet3.type);
    }

    /**
     * Switches to the main game screen.
     *
     * @param event the ActionEvent triggered by the user
     * @throws LoadUIException if there is an error loading the main game screen
     */
    @FXML
    private void switchMain(ActionEvent event) throws LoadUIException{
        try{
            uiLoader.changeUI(event, "Main.fxml", null);
        } catch (Exception e) {
            System.out.println("Error loading new game: " + e.getMessage());

            throw new LoadUIException("Failed loading new game", e);
        }
    }

    /**
     * Switches to the tutorial screen.
     *
     * @param event the ActionEvent triggered by the user
     * @throws Exception if there is an error loading the tutorial screen
     */
    @FXML
    private void switchTutorial(ActionEvent event) throws Exception{
        try{
            uiLoader.changeUI(event, "Tutorial.fxml", null);
        } catch (Exception e) {
            System.out.println("Error loading new game: " + e.getMessage());

            throw new LoadUIException("Failed loading new game", e);
        }
    }

    /**
     * Starts the pet selection process by hiding the tutorial reminder
     * and showing the pet selection group.
     */
    @FXML
    private void startPetSelection() {
        tutorialReminder.setVisible(false);
        petView.setVisible(false);
        petSelection.setVisible(true);
    }

    /**
     * Displays the details of a selected pet.
     *
     * @param event the ActionEvent triggered by clicking a view button
     */
    @FXML
    private void viewPet(ActionEvent event) {
        petSelection.setVisible(false);
        petView.setVisible(true);
        Button source = (Button) event.getSource();
        String buttonId = source.getId();
        switch (buttonId){
            case "viewPet1":
                petDescriptionType.setText(pet1.type);
                petDescriptionImage.setImage(pet1.petImage);
                petDescriptionText.setText("You've Selected " + pet1.type + "! This resourceful little mouse is a " +
                        "model of efficiency, needing food half as often as its peers thanks to its slow hunger build-up. " +
                        "However, it tends to lose happiness twice as fast, requiring a bit of extra attention and care to " +
                        "keep its spirits high. It's an excellent companion for those who value practicality and resource " +
                        "management but don’t mind offering a little extra affection along the way.");
                selectedPet = pet1;
                break;
            case "viewPet2":
                petDescriptionType.setText(pet2.type);
                petDescriptionImage.setImage(pet2.petImage);
                petDescriptionText.setText("You've Selected " + pet2.type + "! This lively bee is a bundle of " +
                        "energy and joy! It loses energy twice as fast as others, buzzing about with endless enthusiasm, " +
                        "but its happiness lasts twice as long, even in the face of hard work. It's the perfect partner for " +
                        "quick tasks and spreading positivity in the hive. Just make sure to give it frequent breaks to " +
                        "recharge its buzzing energy!");
                selectedPet = pet2;
                break;
            case "viewPet3":
                petDescriptionType.setText(pet3.type);
                petDescriptionImage.setImage(pet3.petImage);
                petDescriptionText.setText("You've Selected " + pet3.type + "! This remarkable dragon thrives " +
                        "on its insatiable appetite and boundless stamina. While it consumes food twice as fast as other " +
                        "dragons, its energy reserves are exceptional, depleting at half the normal rate. This makes it an " +
                        "ideal companion for long adventures, where staying active is key. Be sure to keep it well-fed, as " +
                        "its powerful build and unyielding spirit are fueled by its voracious hunger!");
                selectedPet = pet3;
                break;
        }
    }

    /**
     * Confirms the selected pet and displays it in the confirmation screen.
     */
    @FXML
    private void selectPet() {
        petView.setVisible(false);
        petSelectedGroup.setVisible(true);
        petSelectionText.setText("You've Selected " + selectedPet.type + "!");
        selectedPetImage.setImage(selectedPet.petImage);
    }

    /**
     * Assigns a name to the selected pet and transitions to the pet screen.
     *
     * @param event the ActionEvent triggered by clicking the name button
     * @throws Exception if there is an error loading the pet screen
     */
    @FXML
    private void namePet(ActionEvent event) throws Exception {
        String petName = petInputName.getText();
        if (petName.isEmpty()) {
            invalidNameText.setVisible(true);
        } else {
            selectedPet.setName(petName);

            UILoader petuiLoader = new UILoader() {
                @Override
                protected void applyControllerLogic(Object controller, Object controllerData) {
                    if (controller instanceof PetController petController) {
                        petController.setPet((Pet) controllerData);
                    }
                }
            };

            petuiLoader.changeUI(event, "Pet.fxml", selectedPet);
        }
    }


}
