package group47.cs2212.petgame;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the pet management and interaction in the pet game.
 * This class handles user interactions, pet status updates, and animations.
 */
public class PetController {

    @FXML
    private ImageView petView;
    @FXML
    private VBox progressBarsContainer;
    @FXML
    private Label petNameLabel;
    @FXML
    private Label petConditionLabel;
    @FXML
    private Group petInteractionMenu;
    @FXML
    private Group petInteractionSubMenu;
    @FXML
    private Group gameMenu;
    @FXML
    private TextArea usernamePrompt;
    @FXML
    private TextArea emailPrompt;
    @FXML
    private Label invalidInputPrompt;
    @FXML
    private Button openInteractionMenuButton;
    @FXML
    private Label echoUser;
    @FXML
    private Label echoEmail;
    @FXML
    private Label echoScore;
    @FXML
    private Group statusTextGroup;
    private boolean firstIteration = true;
    private ScheduledExecutorService scheduler;
    private ScheduledExecutorService petAnimationsScheduler;
    ProgressBar[] progressBars = new ProgressBar[4];
    Map<String, MenuData> interactionMenus = new HashMap<>();
    private Pet pet;
    private Player user;
    private Image petFine;
    private Image petAngry;
    private Image petSad;
    private Image petSleeping;
    private Image petDead;
    private Image petFineFlipped;
    private Image petAngryFlipped;
    private Image petSadFlipped;
    private UILoader uiLoader;
    private int lastPlayed;
    private GameManagement management;
    @FXML
    private VBox miniGamesContainer;

    /**
     * Loads mini-games into the UI.
     */
    private void loadMiniGames() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScene.fxml"));
            Node gameScene = loader.load();

            miniGamesContainer.getChildren().clear();
            miniGamesContainer.getChildren().add(gameScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load mini-games.");
        }
    }

    /**
     * Toggles visibility of the mini-games container.
     *
     * @param event action event used to determine what was clicked
     */
    @FXML
    private void toggleMiniGames(ActionEvent event) {
        if (!miniGamesContainer.isVisible()) {
            loadMiniGames();
        }
        miniGamesContainer.setVisible(!miniGamesContainer.isVisible());
    }

    /**
     * Loads and shows the mini-games container.
     *
     * @param event action event used to determine what was clicked
     */
    @FXML
    private void handleShowMiniGames(ActionEvent event) {
        loadMiniGames();
        miniGamesContainer.setVisible(true);
    }

    /**
     * Initializes the controller, setting up the game management and UI.
     */
    public void initialize() {
        uiLoader = new UILoader();
        management = new GameManagement("saveDirectory");
        gameUISetup();
    }

    /**
     * Closes the user prompt and starts the game with the provided username and email.
     *
     * @param event  action event used to determine what was clicked
     */
    @FXML
    private void closeUserPrompt(ActionEvent event) {
        String username = usernamePrompt.getText();
        String email = emailPrompt.getText();

        if (username.isEmpty() || email.isEmpty()){
            invalidInputPrompt.setVisible(true);
        } else {
            String id = (username+"_"+email);
            user = new Player(id, username, email);
            user.setPet(pet);
            management.startNewGame(user);
            if (usernamePrompt.getParent() instanceof Group group){
                group.setVisible(false);
                openInteractionMenuButton.setDisable(false);
                updateUserLabels();
                startScheduler();
                startPetAnimations();
            }
        }
    }

    /**
     * Updates the user labels on the UI.
     */
    private void updateUserLabels(){
        echoUser.setText("User: "+ user.getUsername());
        echoEmail.setText("Email: "+ user.getEmail());
        echoScore.setText("Score: "+ user.getCurrentScore());
    }

    /**
     * Sets up the UI elements such as progress bars and interaction menus.
     */
    private void gameUISetup(){
        int index = 0;
        for (Node node : progressBarsContainer.getChildren()) {
            if (node instanceof ProgressBar) {
                progressBars[index++] = (ProgressBar) node;
            }
        }
        index = 0;
        for (Node node : petInteractionSubMenu.getChildren()) {
            if (node instanceof Group) {
                switch(index){
                    case 0:
                        interactionMenus.put("foodMenu", new MenuData((Group) node));
                        index++;
                        break;
                    case 1:
                        interactionMenus.put("playMenu", new MenuData((Group) node));
                        index++;
                        break;
                    case 2:
                        interactionMenus.put("exerciseMenu", new MenuData((Group) node));
                        index++;
                        break;
                    case 3:
                        interactionMenus.put("sleepMenu", new MenuData((Group) node));
                        index++;
                        break;
                    case 4:
                        interactionMenus.put("healthMenu", new MenuData((Group) node));
                        index++;
                        break;
                    case 5:
                        interactionMenus.put("giftMenu", new MenuData((Group) node));
                        index++;
                        break;
                }
            }
        }
    }

    /**
     * Starts the scheduler to periodically update the pet's status and user data.
     */
    private void startScheduler() {
        scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                if (firstIteration) {
                    // Skip the first iteration
                    firstIteration = false;
                } else {
                    updatePetAttributes();  // Perform the update after the first iteration
                    lastPlayed+=1;
                    updateUserLabels();
                }
            });
        }, 0, 1, TimeUnit.MINUTES);  // Start immediately with no delay
    }

    /**
     * Starts the pet animations scheduler, triggering periodic animations.
     */
    private void startPetAnimations() {
        petAnimationsScheduler = Executors.newScheduledThreadPool(1);

        petAnimationsScheduler.scheduleAtFixedRate(() -> {
           Platform.runLater(this::runAnimation);
        }, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * Sets the pet for the user and updates the UI with its data.
     *
     * @param pet Pet object for loading data into the UI
     */
    public void setPet(Pet pet) throws RuntimeException {
        this.pet = pet;
        //petView.setImage(pet.petImage);

        Integer[] petAttributes = this.pet.getAttributes();
        setProgressBar(progressBars, petAttributes);

        petNameLabel.setText(pet.name);
        petConditionLabel.setText(pet.name + "'s Condition");
        loadPetPictures();
        updateImage();
    }

    /**
     * Loads pet images for different states (e.g., fine, angry, sad).
     */
    private void loadPetPictures() throws RuntimeException {
        try {
            petFine = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pet_pictures/" + pet.id + "_idle.png")));
            petAngry = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pet_pictures/" + pet.id + "_angry.png")));
            petSad = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pet_pictures/" + pet.id + "_sad.png")));
            petSleeping = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pet_pictures/" + pet.id + "_sleeping.png")));
            petDead = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pet_pictures/" + pet.id + "_dead.png")));
            petFineFlipped = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pet_pictures/" + pet.id + "_idle_flip.png")));
            petAngryFlipped = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pet_pictures/" + pet.id + "_angry_flip.png")));
            petSadFlipped = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pet_pictures/" + pet.id + "_sad_flip.png")));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the user for the game and updates the UI accordingly.
     *
     * @param player player object to be set as player and load data with
     */
    public void setUser(Player player){
        this.user = player;
        management.startNewGame(user);
        if (this.user != null){
            //Get rid of new user prompt
            Group group = (Group) usernamePrompt.getParent();
            group.setVisible(false);
            openInteractionMenuButton.setDisable(false);

            //Load UI with user data
            setPet(user.getPet());
            updateUserLabels();
            startScheduler();
            startPetAnimations();
        }
    }

    /**
     * Updates the pet's status based on its current state and attributes.
     */
    private void updatePetStatus(){
        Integer[] attributes = pet.getAttributes();
        if (pet.enterDeadState()){
            pet.setState("dead");
            stopScheduler();
        } else if (pet.isInForcedState()) {
            if (Objects.equals(pet.getState(), "sleeping")){
                //Checks if the pet is in a forced sleep state and continues it until it has fully recovered
                if (attributes[1] == 100){
                    pet.setForcedState(false);
                    updatePetStatus();
                }
            } else if (Objects.equals(pet.getState(), "angry")){
                //Checks if the pet is in a forced angry state which is always and continues until it has fully recovered
                if (attributes[0] >= 50) {
                    pet.setForcedState(false);
                    updatePetStatus();
                }
            }
        } else if (Objects.equals(pet.getState(), "sleeping")){
            //Checks if the pet is sleeping and if so, checks if it has fully recovered, makes sure it doesn't get changed back to fine
            // after just 1 point in sleep
            if (attributes[1] == 100){
                pet.setState("fine");
                updatePetStatus();
            }
        } else if (pet.enterSleepState()){
            //Puts the pet into a forced sleep state one reaching 0 on sleep
            pet.setState("sleeping");
            pet.updateHealth(-25);
            pet.setForcedState(true);
            user.updateScore(-5);
        } else if (pet.enterAngryState()){
            //Put the pet into a forced anger state once reaching 0 on happiness
            pet.setState("angry");
            pet.setForcedState(true);
            user.updateScore(-5);
        } else if (pet.enterHungryState()){
            //Puts the pet into the hungry state, exiting upon reaching more than 0 on hunger
            pet.setState("hungry");
            user.updateScore(-5);
        } else {
            //Resets the pet into fine state should all other reasons not apply
            pet.setState("fine");
            user.updateScore(1);
        }
        updateImage();
        showStatusMessage();
        updateButtons();
    }

    /**
     * Updates the pet's attributes based on its actions and conditions.
     */
    private void updatePetAttributes(){
        String petState = pet.getState();
        Integer[] attributeDecay = pet.getAttributeDecay();
        Integer[] attributeDecaySleep = pet.getAttributeDecaySleep();
        if (Objects.equals(petState, "dead")){
            return;
        } else if (Objects.equals(petState, "sleeping")){
            // Attribute Decay for Sleeping Pets
            pet.updateHunger(attributeDecaySleep[0]);
            pet.updateEnergy(attributeDecaySleep[1]);
            pet.updateHappiness(attributeDecaySleep[2]);
        } else {
            // Attribute Decay for awake Pets
            pet.updateHunger(attributeDecay[0]);
            pet.updateEnergy(attributeDecay[1]);
            pet.updateHappiness(attributeDecay[2]);
        }

        if (pet.enterHungryState()){
            pet.updateHealth(-5);
        }
        if (Objects.equals(pet.getState(), "hungry")){
            pet.updateHappiness(-5);
        }
        updatePetStatus();
        setProgressBar(progressBars, pet.getAttributes());
    }

    /**
     * Update accessibility for buttons based on pet state
     */
    private void updateButtons(){
        if (Objects.equals(pet.state, "dead") || (pet.getState().equals("sleeping") && pet.isInForcedState())){
            petInteractionSubMenu.setDisable(true);
        } else if (Objects.equals(pet.state, "sleeping")){
            for (String key : interactionMenus.keySet()) {
                if (!Objects.equals(key, "sleepMenu")){
                    interactionMenus.get(key).getGroup().setDisable(true);
                }
            }
        } else if (Objects.equals(pet.state, "angry")){
            for (String key : interactionMenus.keySet()) {
                if (!Objects.equals(key, "playMenu") && !Objects.equals(key, "giftMenu")) {
                    interactionMenus.get(key).getGroup().setDisable(true);
                }
            }
        } else {
            petInteractionSubMenu.setDisable(false);
            for (String key : interactionMenus.keySet()) {
                interactionMenus.get(key).getGroup().setDisable(false);
            }
        }
    }

    /**
     * Runs a pet animation based on its current condition.
     */
    private void runAnimation() {
        if (Objects.equals(pet.state, "fine")){
            animationRunner(petFineFlipped);
        } else if (Objects.equals(pet.state, "angry")){
            animationRunner(petAngryFlipped);
        } else if (Objects.equals(pet.state, "hungry")) {
            animationRunner(petSadFlipped);
        }
    }

    /**
     * Uses random generation to add unpredictable animations to the pet to make it look alive
     *
     * @param newImage the new updated image to run the animation
     */
    private void animationRunner(Image newImage){
        petView.setImage(newImage);

        // Generate a random delay between 200ms to 1000ms
        int randomDelay = ThreadLocalRandom.current().nextInt(200, 1000);

        PauseTransition pause = new PauseTransition(Duration.millis(randomDelay));
        pause.setOnFinished(event -> {
            // Randomly decide whether to switch back
            boolean shouldSwitchBack = ThreadLocalRandom.current().nextBoolean();

            if (shouldSwitchBack) {
                petView.setImage(pet.petImage); // Switch back to the original image
            }
        });
        pause.play();
    }

    /**
     * Updates the pet image based on its condition.
     */
    private void updateImage() {
        switch(pet.state){
            case "dead":
                pet.petImage = petDead;
                break;
            case "sleeping":
                pet.petImage = petSleeping;
                break;
            case "angry":
                pet.petImage = petAngry;
                break;
            case "hungry":
                pet.petImage = petSad;
                break;
            case "fine":
                pet.petImage = petFine;
                break;
        }
        petView.setImage(pet.petImage);
    }

    /**
     * Sets the progress bar for the pet's attributes (hunger, happiness, energy, health).
     * Adjusts the progress bar color based on the attribute value.
     *
     * @param progressBars Array of progress bars to be updated.
     * @param attributes Array of pet attribute values (hunger, happiness, energy, health).
     */
    private void setProgressBar(ProgressBar[] progressBars, Integer[] attributes) {
        for (int i = 0; i < progressBars.length; i++) {
            float attributePercent = Float.parseFloat(String.valueOf(attributes[i])) / 100;
            //Safety for preventing the progress being a negative number
            if (attributePercent <= 0) {
                attributePercent = 0;
            }
            progressBars[i].setProgress(attributePercent);
            if (attributePercent >= 0.55) {
                progressBars[i].setStyle("-fx-accent: green;");
            } else if (attributePercent <= 0.25) {
                progressBars[i].setStyle("-fx-accent: red;");
            } else {
                progressBars[i].setStyle("-fx-accent: yellow;");
            }
        }
    }


    /**
     * Used to simulate a minute passing for testing
     */
    @FXML
    private void runSchedulerTest(){
        stopScheduler();
        startScheduler();
        startPetAnimations();
    }

    /**
     * Stops the scheduler and pet animation scheduler to halt any periodic updates.
     */
    @FXML
    public void stopScheduler(){
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        if (petAnimationsScheduler != null && !petAnimationsScheduler.isShutdown()){
            petAnimationsScheduler.shutdown();
        }
    }

    /**
     * Displays a status message in the UI based on the pet's current state.
     */
    @FXML
    private void showStatusMessage(){
        Label text = null;
        for (int i = 0; i < statusTextGroup.getChildren().size(); i++) {
            Node node = statusTextGroup.getChildren().get(i);
            if (node instanceof Label){
                text = (Label) node;
            }
        }
        if (Objects.equals(pet.state, "dead")) {
            statusTextGroup.setVisible(false);
        } else if (Objects.equals(pet.state, "sleeping")){
            if (text != null) {
                statusTextGroup.setVisible(true);
                text.setText("Zzz...");
            }
        } else {
            if (!pet.isHealthy()){
                if (text != null){
                    statusTextGroup.setVisible(true);
                    text.setText("I'm Not Well...");
                }
            } else if (!pet.isNotSleepy()) {
                if (text != null) {
                    statusTextGroup.setVisible(true);
                    text.setText("I'm Tired...");
                }
            } else if (!pet.isNotHungry()){
                if (text != null){
                    statusTextGroup.setVisible(true);
                    text.setText("Hungry...");
                }
            } else if (!pet.isHappy()){
                if (text != null){
                    statusTextGroup.setVisible(true);
                    text.setText("I'm not Happy...");
                }
            } else {
                statusTextGroup.setVisible(false);
            }
        }
    }

    /**
     * Opens the game menu, stops the scheduler when the menu is opened, and restarts it when the menu is closed.
     */
    @FXML
    private void openGameMenu() {
        if (!gameMenu.isVisible()) {
            gameMenu.setVisible(true);
            stopScheduler();
        } else {
            gameMenu.setVisible(false);
            firstIteration = true;
            startScheduler();
            startPetAnimations();
        }
    }

    /**
     * Closes the game menu and restarts the scheduler and pet animations.
     */
    @FXML
    private void closeGameMenu() {
        firstIteration = true;
        startScheduler();
        startPetAnimations();
        gameMenu.setVisible(false);
    }

    /**
     * Switches to the main menu UI.
     *
     * @param event The action event that triggered the switch.
     * @throws Exception If an error occurs while switching UI.
     */
    @FXML
    private void switchMain(ActionEvent event) throws Exception{
        stopScheduler();
        uiLoader.changeUI(event, "main.fxml", null);
    }

    /**
     * Saves the current game state.
     *
     * @param event The action event that triggered the save.
     * @throws Exception If an error occurs while saving the game.
     */
    @FXML
    private void saveGame(ActionEvent event) throws Exception{
        management.saveGame();
    }

    /**
     * Switches to the load game menu.
     *
     * @param event The action event that triggered the switch.
     * @throws Exception If an error occurs while switching UI.
     */
    @FXML
    private void switchLoadGame(ActionEvent event) throws Exception{
        stopScheduler();
        uiLoader.changeUI(event, "LoadGame.fxml", null);
    }

    /**
     * Switches to the parental controls menu.
     *
     * @param event The action event that triggered the switch.
     * @throws Exception If an error occurs while switching UI.
     */
    @FXML
    private void switchParentalControls(ActionEvent event) throws Exception{
        stopScheduler();
        uiLoader.changeUI(event, "ParentalControls.fxml", null);
    }

    /**
     * Opens or closes the pet interaction menu.
     * If the menu is open, closes any other open submenus.
     *
     * @throws Exception If an error occurs while opening/closing the menu.
     */
    @FXML
    private void openInteractionMenu() throws Exception{
        if (!petInteractionMenu.isVisible()) {
            petInteractionMenu.setVisible(true);
        } else {
            petInteractionMenu.setVisible(false);
            for (MenuData menu : interactionMenus.values()) {
                if(menu.isShowing()){
                    menu.getGroup().setVisible(false);
                    menu.setShowing(false);
                }
            }
        }
    }

    /**
     * Opens the food menu for pet interaction (related to hunger).
     *
     * @throws Exception If an error occurs while opening the food menu.
     */
    @FXML
    private void openFoodMenu() throws Exception{openMenu("foodMenu", "hunger");}

    /**
     * Opens the play menu for pet interaction (related to happiness).
     *
     * @throws Exception If an error occurs while opening the play menu.
     */
    @FXML
    private void openPlayMenu() throws Exception {openMenu("playMenu", "happiness");}

    /**
     * Opens the exercise menu for pet interaction (related to health).
     *
     * @throws Exception If an error occurs while opening the exercise menu.
     */
    @FXML
    private void openExerciseMenu() throws Exception{openMenu("exerciseMenu", "health");}

    /**
     * Opens the sleep menu for pet interaction (related to energy).
     *
     * @throws Exception If an error occurs while opening the sleep menu.
     */
    @FXML
    private void openSleepMenu() throws Exception{openMenu("sleepMenu", "energy");}

    /**
     * Opens the health menu for pet interaction (related to health).
     *
     * @throws Exception If an error occurs while opening the health menu.
     */
    @FXML
    private void openHealthMenu() throws Exception{openMenu("healthMenu", "health");}

    /**
     * Opens the gift menu for pet interaction (related to happiness).
     *
     * @throws Exception If an error occurs while opening the gift menu.
     */
    @FXML
    private void openGiftMenu() throws Exception{openMenu("giftMenu", "happiness");}

    /**
     * Opens a specified interaction menu and updates the UI accordingly based on the pet's attributes.
     *
     * @param key The key identifying the menu (e.g., "foodMenu", "playMenu").
     * @param attribute The pet attribute being interacted with (e.g., "hunger", "happiness").
     * @throws Exception If an error occurs while opening the menu.
     */
    private void openMenu(String key, String attribute) throws Exception{
        Integer[] attributes = pet.getAttributes();
        if (!interactionMenus.get(key).getGroup().isVisible()) {
            for (MenuData menu : interactionMenus.values()) {
                if(menu.isShowing()){
                    menu.getGroup().setVisible(false);
                    menu.setShowing(false);
                }
            }
            Group newOpenMenu = new Group();
            newOpenMenu = interactionMenus.get(key).getGroup();
            for (int i = 0; i < newOpenMenu.getChildren().size(); i++) {
                Node node = newOpenMenu.getChildren().get(i);
                if (node instanceof ImageView imageView && "subMenuPetImage".equals(node.getId())) {
                    imageView.setImage(pet.petImage);
                }
                if (node instanceof Button b && Objects.equals(attribute, "energy")){
                    if (!Objects.equals(pet.getState(), "sleeping")) {
                        b.setText("Put to Sleep");
                    }
                }
                if (node instanceof ProgressBar progressBar) {
                    float attributePercent = 0;
                    switch (attribute){
                        case "hunger":
                            attributePercent = Float.parseFloat(String.valueOf(attributes[2])) / 100;
                            //Safety for preventing the progress being a negative number
                            if (attributePercent <= 0) {
                                attributePercent = 0;
                            }
                            break;
                        case "happiness":
                            attributePercent = Float.parseFloat(String.valueOf(attributes[0])) / 100;
                            //Safety for preventing the progress being a negative number
                            if (attributePercent <= 0) {
                                attributePercent = 0;
                            }
                            break;
                        case "energy":
                            attributePercent = Float.parseFloat(String.valueOf(attributes[1])) / 100;
                            //Safety for preventing the progress being a negative number
                            if (attributePercent <= 0) {
                                attributePercent = 0;
                            }
                            break;
                        case "health":
                            attributePercent = Float.parseFloat(String.valueOf(attributes[3])) / 100;
                            //Safety for preventing the progress being a negative number
                            if (attributePercent <= 0) {
                                attributePercent = 0;
                            }
                            break;
                    }
                    progressBar.setProgress(attributePercent);
                    if (attributePercent >= 0.55) {
                        progressBar.setStyle("-fx-accent: green;");
                    } else if (attributePercent <= 0.25) {
                        progressBar.setStyle("-fx-accent: red;");
                    } else {
                        progressBar.setStyle("-fx-accent: yellow;");
                    }
                }
                if (node instanceof Label label) {
                    switch (attribute){
                        case "hunger":
                            if ("petHungerLabel".equals(label.getId())) {
                                label.setText(pet.name + "'s Hunger= " + pet.getAttributes()[2] +"%");
                            }
                            break;
                        case "happiness":
                            if ("lastPlayedLabel".equals(label.getId())) {
                                label.setText("Last Time Played With: " + lastPlayed + " minutes ago!");
                            }
                            if ("petHappinessLabel".equals(label.getId())) {
                                label.setText(pet.name + "'s Happiness= " + pet.getAttributes()[0] +"%");
                            }
                            break;
                        case "energy":
                            if ("petEnergyLabel".equals(label.getId())) {
                                label.setText(pet.name + "'s Energy= " + pet.getAttributes()[1] +"%");
                            }
                            break;
                        case "health":
                            if ("petHealthLabel".equals(label.getId())) {
                                label.setText(pet.name + "'s Health= " + pet.getAttributes()[3] +"%");
                            }
                            break;
                    }
                }
            }
            newOpenMenu.setVisible(true);
            interactionMenus.get(key).setShowing(true);
        } else {
            interactionMenus.get(key).getGroup().setVisible(false);
            interactionMenus.get(key).setShowing(false);
        }
    }

    /**
     * Simulates a pet being fed and updates the hunger status.
     * Increases the pet's hunger by 10 and updates the progress bar accordingly.
     *
     * @throws Exception If there is an error while updating the pet's hunger or progress bar.
     */
    @FXML
    private void foodTestMenu() throws Exception {
        pet.updateHunger(10);
        setProgressBar(progressBars, pet.getAttributes());
    }

    /**
     * Opens the inventory menu and loads the player's inventory data.
     *
     * @param event The action event triggered by opening the inventory.
     * @throws Exception If there is an error while loading the inventory.
     */
    @FXML
    private void openInventoryMenu(ActionEvent event) throws Exception {
        UILoader inventoryLoader = new UILoader() {
            @Override
            protected void applyControllerLogic(Object controller, Object controllerData) {
                if (controller instanceof InventoryController inventoryController) {
                    inventoryController.loadPlayer((Player) controllerData);
                }
            }
        };

        inventoryLoader.changeUI(event, "Inventory.fxml", user);
    }

    /**
     * Simulates playing with the pet and increases its happiness.
     * Updates the pet's happiness attribute, progress bar, and UI elements such as the pet image and labels.
     * The pet can only be played with every 5 minutes, else a warning is shown.
     *
     * @param event The action event triggered by the play button.
     */
    @FXML
    private void playWithPet(ActionEvent event) {
        Label warning = null;
        Label lastPlayedLabel = null;
        Label petHappinessLabel = null;
        ProgressBar progressBar = null;
        ImageView petImage = null;
        Object source = event.getSource();
        if (source instanceof Button button) {
            Parent parent = button.getParent(); // Use Parent for flexibility

            if (parent instanceof Group group) {
                for (Node child : group.getChildren()) {
                    if (child instanceof Label label) {
                        if ("increasedAttrLabel".equals(label.getId())) { // Compare with the label's id
                            warning = label; // Assign the correct Label
                        }
                        if ("lastPlayedLabel".equals(label.getId())) {
                            lastPlayedLabel = label;
                        }
                        if ("petHappinessLabel".equals(label.getId())) {
                            petHappinessLabel = label;
                        }
                    }
                    if (child instanceof ProgressBar progressBar1){
                        progressBar = progressBar1;
                    }
                    if (child instanceof ImageView imageView){
                        if ("subMenuPetImage".equals(imageView.getId())){
                            petImage = imageView;
                        }
                    }
                }
            } else {
                System.err.println("Parent is not a Group. It is: " + parent.getClass().getSimpleName());
            }
        }

        if (warning == null) {
            System.err.println("Warning label with id 'increasedAttrLabel' not found!");
            return;
        }

        if (lastPlayed >= 5) {
            lastPlayed = 0;
            pet.updateHappiness(20);
            warning.setVisible(true);

            warning.setText(pet.getName() + "'s Happiness Increased by: 20%");
            lastPlayedLabel.setText("Last Time Played With: 0 minutes ago!");
            petHappinessLabel.setText(pet.getName() + "'s Happiness = " + pet.getAttributes()[0] + "%");

            int attributePercent = (int) Float.parseFloat(String.valueOf(pet.getAttributes()[0] / 100));
            progressBar.setProgress(attributePercent);
            if (attributePercent >= 0.55) {
                progressBar.setStyle("-fx-accent: green;");
            } else if (attributePercent <= 0.25) {
                progressBar.setStyle("-fx-accent: red;");
            } else {
                progressBar.setStyle("-fx-accent: yellow;");
            }
            progressBar.setProgress(attributePercent);

            user.updateScore(5);
            updatePetStatus();
            setProgressBar(progressBars, pet.getAttributes());
            updateUserLabels();

            petImage.setImage(pet.getImage());

            PauseTransition pause = new PauseTransition(Duration.millis(3000));
            Label finalWarning = warning;
            pause.setOnFinished(e -> finalWarning.setVisible(false));
            pause.play();
        } else {
            warning.setVisible(true);
            warning.setText("Please wait 5 minutes before playing with pet again!");

            PauseTransition pause = new PauseTransition(Duration.millis(3000));
            Label finalWarning1 = warning;
            pause.setOnFinished(e -> finalWarning1.setVisible(false));
            pause.play();
        }
    }

    /**
     * Simulates exercising the pet, which increases its health while reducing hunger and energy.
     * The pet must have enough energy and hunger to exercise, and the health attribute must be below a threshold.
     *
     * @param event The action event triggered by the exercise button.
     */
    @FXML
    private void exercisePet(ActionEvent event) {
        Label warning = null;
        Label petHealthLabel = null;
        ProgressBar progressBar = null;
        ImageView petImage = null;
        Object source = event.getSource();
        if (source instanceof Button button) {
            Parent parent = button.getParent(); // Use Parent for flexibility

            if (parent instanceof Group group) {
                for (Node child : group.getChildren()) {
                    if (child instanceof Label label) {
                        if ("increasedAttrLabel".equals(label.getId())) { // Compare with the label's id
                            warning = label; // Assign the correct Label
                        }
                        if ("petHealthLabel".equals(label.getId())) {
                            petHealthLabel = label;
                        }
                    }
                    if (child instanceof ProgressBar progressBar1){
                        progressBar = progressBar1;
                    }
                    if (child instanceof ImageView imageView){
                        if ("subMenuPetImage".equals(imageView.getId())){
                            petImage = imageView;
                        }
                    }
                }
            } else {
                System.err.println("Parent is not a Group. It is: " + parent.getClass().getSimpleName());
            }
        }

        if (warning == null) {
            System.err.println("Warning label with id 'increasedAttrLabel' not found!");
            return;
        }
        if (pet.getAttributes()[2] < 10 || pet.getAttributes()[1] < 10 || pet.getAttributes()[3] > 90){
            warning.setVisible(true);
            warning.setText(pet.getName() + " doesn't want to exercise right now.");
            PauseTransition pause = new PauseTransition(Duration.millis(3000));
            Label finalWarning = warning;
            pause.setOnFinished(e -> finalWarning.setVisible(false));
            pause.play();
        } else {
            pet.updateHealth(10);
            pet.updateHunger(-10);
            pet.updateEnergy(-10);
            warning.setVisible(true);

            warning.setText(pet.getName() + "'s Health Increased by: 10%");
            petHealthLabel.setText(pet.getName() + "'s Health = " + pet.getAttributes()[3] + "%");

            int attributePercent = (int) Float.parseFloat(String.valueOf(pet.getAttributes()[3] / 100));
            progressBar.setProgress(attributePercent);
            if (attributePercent >= 0.55) {
                progressBar.setStyle("-fx-accent: green;");
            } else if (attributePercent <= 0.25) {
                progressBar.setStyle("-fx-accent: red;");
            } else {
                progressBar.setStyle("-fx-accent: yellow;");
            }
            progressBar.setProgress(attributePercent);

            user.updateScore(5);
            updatePetStatus();
            setProgressBar(progressBars, pet.getAttributes());
            updateUserLabels();

            petImage.setImage(pet.getImage());

            PauseTransition pause = new PauseTransition(Duration.millis(3000));
            Label finalWarning = warning;
            pause.setOnFinished(e -> finalWarning.setVisible(false));
            pause.play();
        }
    }

    /**
     * Simulates putting the pet to bed, which affects its state.
     * The pet can only be put to bed if its energy is below a certain threshold.
     * If the pet is already asleep, it will wake up.
     *
     * @param event The action event triggered by the sleep button.
     */
    @FXML
    private void putToBed(ActionEvent event) {
        Label warning = null;
        ImageView petImage = null;
        Button sleepButton = null;
        Object source = event.getSource();
        if (source instanceof Button button) {
            Parent parent = button.getParent(); // Use Parent for flexibility

            if (parent instanceof Group group) {
                for (Node child : group.getChildren()) {
                    if (child instanceof Label label) {
                        if ("increasedAttrLabel".equals(label.getId())) { // Compare with the label's id
                            warning = label; // Assign the correct Label
                        }
                    }
                    if (child instanceof ImageView imageView){
                        if ("subMenuPetImage".equals(imageView.getId())){
                            petImage = imageView;
                        }
                    }
                    if (child instanceof Button b){
                        sleepButton = b;
                    }
                }
            } else {
                System.err.println("Parent is not a Group. It is: " + parent.getClass().getSimpleName());
            }
        }

        if (warning == null) {
            System.err.println("Warning label with id 'increasedAttrLabel' not found!");
            return;
        }
        if (pet.getState() == "sleeping") {
            sleepButton.setText("Put to Bed");
            pet.setState("fine");
            updatePetStatus();
            petImage.setImage(pet.getImage());

            warning.setVisible(true);
            warning.setText(pet.getName() + " has woken up!");

            PauseTransition pause = new PauseTransition(Duration.millis(3000));
            Label finalWarning = warning;
            pause.setOnFinished(e -> finalWarning.setVisible(false));
            pause.play();
        } else if (pet.getAttributes()[1] > 90) {
            warning.setVisible(true);
            warning.setText(pet.getName() + " doesn't want to sleep right now.");
            PauseTransition pause = new PauseTransition(Duration.millis(3000));
            Label finalWarning = warning;
            pause.setOnFinished(e -> finalWarning.setVisible(false));
            pause.play();
        } else {
            sleepButton.setText("Wake up Pet");
            pet.setState("sleeping");
            updatePetStatus();
            petImage.setImage(pet.getImage());

            warning.setVisible(true);
            warning.setText(pet.getName() + " is asleep...");

            PauseTransition pause = new PauseTransition(Duration.millis(3000));
            Label finalWarning = warning;
            pause.setOnFinished(e -> finalWarning.setVisible(false));
            pause.play();
        }
    }

    /**
     * Simulates taking the pet to the vet, increasing its health.
     * The pet's health can only be increased if it is below a threshold.
     *
     * @param event The action event triggered by the vet button.
     */
    @FXML
    private void takeToVet(ActionEvent event) {
        Label warning = null;
        Label petHealthLabel = null;
        ProgressBar progressBar = null;
        ImageView petImage = null;
        Object source = event.getSource();
        if (source instanceof Button button) {
            Parent parent = button.getParent(); // Use Parent for flexibility

            if (parent instanceof Group group) {
                for (Node child : group.getChildren()) {
                    if (child instanceof Label label) {
                        if ("increasedAttrLabel".equals(label.getId())) { // Compare with the label's id
                            warning = label; // Assign the correct Label
                        }
                        if ("petHealthLabel".equals(label.getId())) {
                            petHealthLabel = label;
                        }
                    }
                    if (child instanceof ProgressBar progressBar1){
                        progressBar = progressBar1;
                    }
                    if (child instanceof ImageView imageView){
                        if ("subMenuPetImage".equals(imageView.getId())){
                            petImage = imageView;
                        }
                    }
                }
            } else {
                System.err.println("Parent is not a Group. It is: " + parent.getClass().getSimpleName());
            }
        }

        if (warning == null) {
            System.err.println("Warning label with id 'increasedAttrLabel' not found!");
            return;
        }
        if (pet.getAttributes()[3] > 90){
            warning.setVisible(true);
            warning.setText(pet.getName() + " doesn't need to go to the vet right now.");
            PauseTransition pause = new PauseTransition(Duration.millis(3000));
            Label finalWarning = warning;
            pause.setOnFinished(e -> finalWarning.setVisible(false));
            pause.play();
        } else {
            String formerState = pet.getState();
            pet.updateHealth(25);
            warning.setVisible(true);

            warning.setText(pet.getName() + "'s Health Increased by: 25%");
            petHealthLabel.setText(pet.getName() + "'s Health = " + pet.getAttributes()[3] + "%");

            int attributePercent = (int) Float.parseFloat(String.valueOf(pet.getAttributes()[3] / 100));
            progressBar.setProgress(attributePercent);
            if (attributePercent >= 0.55) {
                progressBar.setStyle("-fx-accent: green;");
            } else if (attributePercent <= 0.25) {
                progressBar.setStyle("-fx-accent: red;");
            } else {
                progressBar.setStyle("-fx-accent: yellow;");
            }
            progressBar.setProgress(attributePercent);

            user.updateScore(-10);
            if (formerState == pet.getState()){
                updatePetStatus();
            }
            setProgressBar(progressBars, pet.getAttributes());
            updateUserLabels();

            petImage.setImage(pet.getImage());

            PauseTransition pause = new PauseTransition(Duration.millis(3000));
            Label finalWarning = warning;
            pause.setOnFinished(e -> finalWarning.setVisible(false));
            pause.play();
        }
    }

}