import java.util.ArrayList;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player {
    private int WEIGHTLIMIT;
    private int currentWeight;
    private Room currentRoom;
    private ArrayList<Item> inventorys;
    private Room tRoom;
    private int charged;

    /**
     * Create the player and initialise its internal map.
     */
    public Player(Room room) {
        WEIGHTLIMIT = 25;
        currentRoom = room;
        inventorys = new ArrayList<>();
        charged = 0;
    }

    /**
     * Printes the players inventory in the form:
     * Name, description, weight
     * currentWeight of weightlimit 
     */
    public void printInventory(){
        System.out.println("Inventory:");
        for(Item item : inventorys){
            System.out.println(getName(item));
            System.out.println(getDescription(item));
            System.out.println(getWeight(item));
        }
        System.out.println("Currently using " + currentWeight + "/" + WEIGHTLIMIT);
    }

    private void addWeight(Item item)
    {
        // put your code here
        currentWeight += getWeight(item);
    }

    private void removeWeight(Item item)
    {
        // put your code here
        currentWeight -= getWeight(item);
    }

    /**
     * Removes the item from the inventory and adds it to the currentRoom
     * @Param name of the item to drop
     */
    public void dropItem(String items)
    {
        Item item = inventory((items));
        inventorys.remove(item);
        currentRoom.addItem(item);
        removeWeight(item);
        System.out.println("You have dropped " + items);
    }

    /**
     * Adds the item from the currentRoom and adds it to the inventory
     * @Param name of the item to take
     */
    public void takeItem(Item item)
    {
        if(WEIGHTLIMIT >= currentWeight + getWeight(item)){
            inventorys.add(item);
            currentRoom.removeItem(item);
            addWeight(item);
            System.out.println("You have picked up " + getName(item));
        } else {
            System.out.println("You do not have the inventory capacity to pick it up.");
        }
    }

    /**
     * Removes the item from inventory
     * @param name of the item to remove
     */
    public void eatItem(String items){
        Item item = inventory((items));
        inventorys.remove(item);
        removeWeight(item);
        System.out.println("You have eaten " + items);
    }

    public void setRoom(Room room){
        currentRoom = room;
    }

    public Room getRoom(){
        return currentRoom;
    }

    /**
     * Returns the description of the item
     * @param the item object
     * @return the decription as string
     */
    private String getDescription(Item item)
    {
        return item.getDescription();
    }

    /**
     * Returns the name of the item
     * @param the item object
     * @return the name as string
     */
    public String getName(Item item)
    {
        return item.getName();
    }

    /**
     * Returns the weight of the item
     * @param the item object
     * @return the weight as string
     */
    private Integer getWeight(Item item)
    {
        return item.getWeight();
    }

    /**
     * Checks the inventory for an item
     * @param name of the item to find
     * @return the item with the same name
     */
    public Item inventory(String word){
        for(Item name : inventorys){
            if(name.getName().equals(word)){
                return name;
            }
        }
        return null;
    }

    public void changeCapacity(){
        WEIGHTLIMIT += 5;
    }

    /**
     * Charges the teleporter with the location of the currentRoom
     * if you have it in your inventory
     */
    public void charge() {
        if(inventorys.contains(inventory("Teleporter"))){
            tRoom = getRoom();
            System.out.println("Your teleporter has been charged, it will let \nyou teleport back to "
                + tRoom.getDescription() + ".");
            charged += 1;
        } else{
            System.out.println("You do not have a Teleporter in your inventory.");
        }
    }

    /**
     * Teleports the player to the charge if the teleporter
     * is in the inventory
     */
    public void fire() {
        if(inventorys.contains(inventory("Teleporter")) && charged > 0){
            setRoom(tRoom);
            Item item = inventory("Teleporter");
            inventorys.remove(item);
            removeWeight(item);
            System.out.println("You have been teleported to \n" + tRoom.getDescription() + ".");
        } else {
            System.out.println("You do not have a Teleporter in your inventory.");
        }
    }
    
    /**
     * Checks if the inventory contains an item
     * @param name of the item to search for
     * @return true if it contains, false if it does not
     */
    public boolean inventoryContain(String word){
        if(inventorys.contains(inventory(word))){
            return true;
        }
        return false;
    }
}
