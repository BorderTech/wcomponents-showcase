package com.github.bordertech.wcomponents.showcase.common;

import com.github.bordertech.wcomponents.Container;

/**
 *
 * @author jonathan
 */
public interface PropertyContainer<T> extends Container {

	void configWidget();

	SampleContainer<T> getSampleContainer();
}
