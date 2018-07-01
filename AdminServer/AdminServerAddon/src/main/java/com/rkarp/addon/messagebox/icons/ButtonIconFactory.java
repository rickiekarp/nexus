package com.rkarp.addon.messagebox.icons;

import com.vaadin.server.Resource;
import com.rkarp.addon.messagebox.ButtonType;

import java.io.Serializable;

/**
 * This interface defines the essential methods for a ButtonIconFactory  
 * 
 * @author Dieter Steinwedel
 */
public interface ButtonIconFactory extends Serializable {
	
	/**
	 * Loads the resource for the given buttonType.
	 * 
	 * @param buttonType The ButtonType
	 * @return The resource
	 */
	public Resource getIcon(ButtonType buttonType);
	
}