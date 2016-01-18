package com.github.bordertech.wcomponents.showcase.common;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jonathan
 */
public interface Showcase<T> extends Serializable {

	Class<T> getWidgetClass();

	PropertyContainer<T> getPropertyContainerInstance(SampleContainer<T> showcasePanel);

	SampleContainer<T> getSampleContainerInstance();

	List<Class> getRelatedWidgets();
}
