import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;
    private Item item;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    /**
     * add items to rooms.
     */
    public void addItem(Item item)
    {
        items.add(item);
    }

    /**
     * remove items from rooms
     */
    public void removeItem(Item item){
        items.remove(item);
    }

    /**
     * Checks ArrayList items for the name of the item
     * @param name of the item in question
     * @return the item with the same name
     */
    public Item getItem(String word){
        for(Item name : items){
            if(name.getName().equals(word)){
                return name;
            }
        }
        return null;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, of the form:
     * You are in the kitchen.
     * Exits: north west
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + ". \n"
        + getIDescription(); 
    }

    /**
     * @return the description of the item
     */
    public String getIDescription()
    {
        String returnString = "";
        for(Item item : items){
            returnString += "\n" + item.getDescription();
        }
        return returnString;
    }

    /**
     * @return the name of the item
     */
    public String getIName()
    {
        String returnString = "";
        for(Item item : items){
            returnString += "" + item.getName();
        }
        return returnString;
    }

    /**
     * @return the weight of the item
     */
    public String getIWeight()
    {
        String returnString = "";
        for(Item item : items){
            returnString += "" + item.getWeight();
        }
        return returnString;
    }

    /**
     * @return all the items in the room
     */
    public String getInfo()
    {
        if(items.size() > 0){
            String returnString = "The items in this area: ";
            for(Item item : items){
                returnString += "" + item.getInfo();
            }
            return returnString;
        } else {
            return "There are no items in the area.";
        }
    }

    public Room getExit(String direction){
        return exits.get(direction);
    }

    /**
     * Return a description of the room’s exits,
     * for example, "Exits: north west".
     * @return A description of the available exits.
     */
    public String getExitString(){
        String directions = "Exits: ";
        Set<String> keys = exits.keySet();
        for(String exit : keys){
            directions += " " + exit;
        }
        return directions;
    }
    
    /**
     * Checks wether the room contains an item
     * @param the name of the item
     * @return true if it does, false if it don't
     */
    public boolean roomContain(String word){
        if(items.contains(getItem(word))){
            return true;
        }
        return false;
    }
}
