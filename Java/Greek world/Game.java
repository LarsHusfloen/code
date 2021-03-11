import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
/**
 *  This class is the main class of the "Greek World" application. 
 *  "Greek World" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery and interact with some items. 
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Lars B. Husfloen
 * @version 09.03.2020
 */

public class Game 
{
    private Parser parser;
    private Item item;
    private Player player;
    private Stack<Room> lastRoom;
    int moves;
    int limit;
    private Random rand;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        lastRoom = new Stack<>();
        limit = 20;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room Olympus,Tartaros,Underworld,Othrys,Earth,Elysium, Transit;

        // create the rooms
        Olympus = new Room("at Olympus, the home of the greek gods");
        Tartaros = new Room("in Tartaros, a deep abyss beneath the underworld");
        Underworld = new Room("in the Underworld, the realm of Hades");
        Othrys = new Room("at Othrys, the abode of the titans");
        Earth = new Room("on Earth, the realm inhabited by the humans");
        Elysium = new Room("at Elysium, the isle of paradise reserved for heroes\nand the blessed");
        Transit = new Room("in a transistpoint to a random room");

        // initialise room exits
        Olympus.setExits("earth", Earth);
        Olympus.setExits("pit", Othrys);

        Tartaros.setExits("underworld", Underworld);

        Underworld.setExits("earth", Earth);
        Underworld.setExits("elysium", Elysium);
        Underworld.setExits("tartaros", Tartaros);

        Othrys.setExits("earth", Earth);

        Earth.setExits("olympus", Olympus);
        Earth.setExits("othrys", Othrys);
        Earth.setExits("underworld", Underworld);
        Earth.setExits("transit", Transit);

        Elysium.setExits("underworld", Underworld);

        Transit.setExits("olympus", Olympus);
        Transit.setExits("othrys", Othrys);
        Transit.setExits("underworld", Underworld);
        Transit.setExits("earth", Earth);
        Transit.setExits("elysium", Elysium);
        Transit.setExits("tartaros", Tartaros);

        // make items   new item(name, description, weight)
        Olympus.addItem(new Item("The-Caduceus", "Erected in the middle of Olympos you see a short staff\ninterwoven by two serpents with two wings at its top", 10));
        Underworld.addItem(new Item("The-Helm-of-Hades", "A beautifully adorned helmet in pure black\nis desplayed on a pedestal eracted behind the colossal Ceberus.", 5));
        Tartaros.addItem(new Item("Kronos'-Scythe", "Your bones chill from the milleniums old hatred radiating\nfrom the depth of the old and destroyed fortress.", 15));
        Elysium.addItem(new Item("The-Aegis","Starring at the head placed in the middle og\nthe shield you feel yourself slowing turning to stone.", 15));
        Earth.addItem(new Item("Magic-Cookie", "Laying on the ground.", 1));
        Elysium.addItem(new Item("Apple-of-Youth", "Dangling on the lower branches of the golden tree.", 2));
        Earth.addItem(new Item("Teleporter","Let's you teleport back to where you charge it. Singel use.", 5));
        Earth.addItem(new Item("Key", "Let's you open locked doors", 2));

        player = new Player(Earth);  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            //Stops the game if number of moves surpasses limit
            if(moves >= limit){
                finished = true;
                System.out.println("GAME OVER! \nYou have run out of moves!");
                return ;
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Greece!");
        System.out.println("World of Greece is a collection of importent locations\nin the Greek world.");
        System.out.println("Type " + CommandWord.HELP + " if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
            System.out.println("I don't know what you mean...");
            break;

            case HELP:
            printHelp();
            break;

            case GO:
            goRoom(command);
            break;

            case LOOK:
            look();
            break;

            case EAT:
            eat(command);
            break;

            case INFO:
            info();
            break;

            case BACK:
            back();
            break;

            case TAKE:
            take(command);
            break;

            case DROP:
            drop(command);
            break;

            case INVENTORY:
            inventory();
            break;

            case MOVESLEFT:
            movesLeft();
            break;

            case CHARGE:
            player.charge();
            break;

            case FIRE:
            player.fire();
            break;

            case QUIT:
            wantToQuit = quit(command);
            break;   
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the world of Greece.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        moves += 1;
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else if(nextRoom == player.getRoom().getExit("elysium")){
            //checks if you are going to elysium
            if(player.inventoryContain("Key")){
                //checks if you have a key in the inventory
                lastRoom.push(player.getRoom());
                player.setRoom(nextRoom);
                printLocationInfo();
            }else{
                System.out.println("You need a key to enter Elysium.");
            }
        } else if(nextRoom == player.getRoom().getExit("transit")){
            //checks if you are going to transit
            lastRoom.push(player.getRoom());
            player.setRoom(nextRoom);
            printLocationInfo();
            player.setRoom(player.getRoom().getExit(randomRoom()));
            printLocationInfo();
            while(!lastRoom.empty()){
                //deletes the back function
                lastRoom.pop();
            }
        } else {
            lastRoom.push(player.getRoom());
            player.setRoom(nextRoom);
            printLocationInfo();
        }
    }
    
    /**
     * picks up items from the room
     * checks if the room contains the item
     */
    private void take(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        String itemWord = command.getSecondWord();
        
        if(player.getRoom().roomContain(itemWord)){
            player.takeItem(player.getRoom().getItem(itemWord));
        } else {
            System.out.println(itemWord + " is not an avaliable item.");
        }
    }

    /**
     * drops the item in the current room
     * checks if you have it in your inventory
     */
    private void drop(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
        String itemWord = command.getSecondWord();

        if(player.inventoryContain(itemWord)){
            player.dropItem(itemWord);
        } else {
            System.out.println("You do not have this item in your inventory.");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void printLocationInfo(){
        System.out.println(player.getRoom().getLongDescription());
    }

    private void look(){
        printLocationInfo();
    }

    /**
     * Consumes the second word
     * Different effects depending on the item
     */
    private void eat(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("Eat what?");
            return;
        }
        String itemWord = command.getSecondWord();

        if(itemWord.equals(player.inventory(itemWord).getName())){
            if(itemWord.equals("Magic-Cookie")){
                player.eatItem(itemWord);
                player.changeCapacity();
            } else if(itemWord.equals("Apple-of-Youth")){
                player.eatItem(itemWord);
            }
        } else {
            System.out.println("You do not have this item in your inventory.");
        }
    }

    private void info(){
        System.out.println(player.getRoom().getInfo());
    }

    /**
     * Let's the user go back rooms until back at start
     */
    private void back(){
        if(!lastRoom.empty()){
            player.setRoom(lastRoom.pop());
            printLocationInfo();
            moves += 1;
        } else {
            System.out.println("You are already at the start.");
        }
    }

    private void inventory(){
        player.printInventory();
    }

    private void movesLeft(){
        System.out.print("Moves left: " + moves + " / " + limit + ".\n");
    }

    /**
     * Generates a random location
     * @return the random location in a string
     */
    private String randomRoom(){
        rand = new Random();
        int num = rand.nextInt(5);
        String room = "";
        switch (num) {
            case 0:
            room = "olympus";
            break;

            case 1:
            room = "elysium";
            break;

            case 2:
            room = "othrys";
            break;

            case 3:
            room = "underworld";
            break;

            case 4:
            room = "tartaros";
            break;
        }
        return room;
    }
}
