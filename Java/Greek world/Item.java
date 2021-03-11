import java.util.HashMap;
import java.util.ArrayList;
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private String description;
    private int weight;
    private String name;

    /**
     * Create a item
     */
    public Item(String name, String description, int weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    /**
     * returns information of the item
     * @return a string for the items name and weight
     */
    public String getInfo()
    {
        String info = "\n" + name + ",\nItem weight: " + weight;
        return info;
    }

    public int getWeight()
    {
        return weight;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }
}
