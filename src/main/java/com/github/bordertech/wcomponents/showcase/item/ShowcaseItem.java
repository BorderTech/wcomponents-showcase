package com.github.bordertech.wcomponents.showcase.item;

import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.showcase.ItemPanel;
import java.io.Serializable;

/**
 *
 * @author jonathan
 */
public interface ShowcaseItem extends Serializable {

	String getShowcaseId();

	String getName();

	WPanel getPropertiesInstance(ItemPanel showcasePanel);

	ItemPanel getItemInstance();
}
