package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.showcase.SampleContainer;
import com.github.bordertech.wcomponents.showcase.PropertyContainer;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jonathan
 */
public interface Showcase<T> extends Serializable {

	Class<T> getWidgetClass();

	PropertyContainer getPropertyContainerInstance(SampleContainer<T> showcasePanel);

	SampleContainer<T> getSampleContainerInstance();

	List<Class> getRelatedWidgets();
}
