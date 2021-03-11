/**
 * Representations for all the valid command words for the game.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public enum CommandWord
{
    // A value for each command word, plus one for unrecognized commands.
    GO("go"), 

    QUIT("quit"), 

    HELP("help"), 

    UNKNOWN("?"), 

    LOOK("look"), 

    EAT("eat"), 

    INFO("info"), 

    BACK("back"), 

    TAKE("take"),

    DROP("drop"), 

    INVENTORY("inventory"),
    
    CHARGE("charge"),
    
    FIRE("fire"),
    
    MOVESLEFT("movesleft");

    private String commandString;

    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
