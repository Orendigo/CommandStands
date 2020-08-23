
/**
 * ProtocolLibManager Class
 */

package net.orendigo.commandstands.externalmanagers;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.orendigo.commandstands.CommandStands;

/**
 * @author Orendigo
 */
public class ProtocolLibManager {
    
    private final CommandStands plugin = CommandStands.getInstance();
    
    public ProtocolManager protocolManager;
    
    public ProtocolLibManager() {}
    
    public void setup() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }
    
    
    
}
