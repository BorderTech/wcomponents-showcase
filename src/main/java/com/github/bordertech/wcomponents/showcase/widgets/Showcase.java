package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.WComponent;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jonathan
 */
public interface Showcase<T extends WComponent> extends Serializable {

	String getWidgetName();

	Class<T> getWidgetClass();

	PropertyContainer getPropertyContainerInstance(WidgetContainer<T> showcasePanel);

	WidgetContainer<T> getWidgetContainerInstance();

	List<Class> getRelatedWidgets();

	String getPseudoCode();

}
