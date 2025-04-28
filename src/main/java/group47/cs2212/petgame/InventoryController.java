package group47.cs2212.petgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.nio.file.attribute.UserPrincipal;
import java.util.Objects;

/**
 * The InventoryController class handles the inventory screen UI and interactions.
 */
public class InventoryController {

    @FXML
    private ListView<String> inventoryListView;

    @FXML
    private Label selectedItemLabel;

    @FXML
    private ImageView selectedItemImage;

    @FXML
    private Button useItemButton;

    @FXML
    private ImageView petImage;

    @FXML
    private Label petName;

    @FXML
    private Label petHunger;

    @FXML
    private ProgressBar hungerBar;

    @FXML
    private Label petHappiness;

    @FXML
    private ProgressBar happinessBar;

    private Inventory inventory;

    private Player user;

    private void setupInventory() {
        inventory = user.getInventory();
        populateInventoryList();
    }

    /**
     * Populates the inventory ListView with items from the inventory.
     */
    private void populateInventoryList() {
        inventoryListView.getItems().clear();
        String[] inventoryDetails = inventory.getInventoryDetails();
        inventoryListView.getItems().addAll(inventoryDetails);
    }

    /**
     * Handles the event when an item is selected from the ListView.
     */
    @FXML
    private void handleItemSelection() {
        String selectedItem = inventoryListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItemLabel.setText("Selected: " + selectedItem);
            updateSelectedItemImage(selectedItem);
        } else {
            selectedItemLabel.setText("No item selected.");
            selectedItemImage.setImage(null);
        }
    }

    /**
     * Updates the ImageView based on the selected item.
     *
     * @param selectedItem The name of the selected item.
     */
    private void updateSelectedItemImage(String selectedItem) {
        Image image;
        switch (selectedItem.split(" ")[0]) { // Extract the item name
            case "Food1" ->
                    image = new Image(Objects.requireNonNull(getClass().getResource("/group47/cs2212/petgame/Pet_inventory/food1.png")).toExternalForm());
            case "Peach" ->
                    image = new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/group47/cs2212/petgame/Pet_inventory/peach.png"))).toExternalForm());
            case "Giftbox" ->
                    image = new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/group47/cs2212/petgame/Pet_inventory/Giftbox.png"))).toExternalForm());
            default -> image = null;
        }
        selectedItemImage.setImage(image);
    }

    /**
     * Handles the event when the "Use Item" button is clicked.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void handleUseItem(ActionEvent event) {
        String selectedItem = inventoryListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            selectedItemLabel.setText("No item selected to use.");
            return;
        }

        String itemName = selectedItem.split(" ")[0]; // Extract the item name

        if (inventory.removeItem(itemName, 1)) {
            switch (itemName) {
                case "Food1":
                    user.getPet().updateHunger(10);
                    break;
                case "Peach":
                    user.getPet().updateHunger(15);
                    break;
                case "Giftbox":
                    user.getPet().updateHappiness(10);
                    break;
            }
            selectedItemLabel.setText(itemName + " used!");
            populateInventoryList();
            updateAttributes();
        } else {
            selectedItemLabel.setText("Failed to use " + itemName);
        }
    }

    /**
     * Handles the loading of user data more specifically the pet
     *
     * @param user The user owner of the pet used to get pet information
     */
    public void loadPlayer(Player user){
        this.user = user;
        Pet pet = user.getPet();
        petImage.setImage(pet.getImage());
        petName.setText(pet.getName());
        setupInventory();
        updateAttributes();
    }

    /**
     * Updates the pet attributes for the UI in Inventory
     */
    private void updateAttributes(){
        Pet pet = user.getPet();
        Integer[] petAttributes = pet.getAttributes();

        petHunger.setText(pet.getName() + "'s Hunger: " + petAttributes[2]);
        petHappiness.setText(pet.getName() + "'s Happiness: " + petAttributes[0]);
        float attributePercentHunger = (float) petAttributes[2] / 100f;
        hungerBar.setProgress(attributePercentHunger);
        if (attributePercentHunger >= 0.55) {
            hungerBar.setStyle("-fx-accent: green;");
        } else if (attributePercentHunger <= 0.25) {
            hungerBar.setStyle("-fx-accent: red;");
        } else {
            hungerBar.setStyle("-fx-accent: yellow;");
        }
        hungerBar.setProgress(attributePercentHunger);

        float attributePercentHappiness = (float) petAttributes[0] / 100f;
        happinessBar.setProgress(attributePercentHappiness);
        if (attributePercentHappiness >= 0.55) {
            happinessBar.setStyle("-fx-accent: green;");
        } else if (attributePercentHappiness <= 0.25) {
            happinessBar.setStyle("-fx-accent: red;");
        } else {
            happinessBar.setStyle("-fx-accent: yellow;");
        }
        happinessBar.setProgress(attributePercentHappiness);
    }

    /**
     * Handles the event when the "Back" button is clicked.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    private void handleBackButton(ActionEvent event) throws Exception {
        UILoader petuiLoader = new UILoader() {
            @Override
            protected void applyControllerLogic(Object controller, Object controllerData) {
                if (controller instanceof PetController petController) {
                    petController.setUser((Player) controllerData);
                }
            }
        };

        petuiLoader.changeUI(event, "Pet.fxml", user);
    }
}
