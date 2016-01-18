package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;
import com.github.bordertech.wcomponents.showcase.util.PropertyUtil;

/**
 *
 * @author jonathan
 */
public abstract class AbstractTooltipPropertyContainer<T extends WComponent> extends AbstractPropertyContainer<T> {

	// Tooltip
	private final WTextField txtTooltip = new WTextField();

	public AbstractTooltipPropertyContainer(final SampleContainer<T> sampleContainer) {
		super(sampleContainer);

		// Tooltip
		addPropertyWidget("Tooltip", txtTooltip);
	}

	protected void configWidgetProperties(final T widget) {
		widget.setToolTip(PropertyUtil.getPropertyStringValue(txtTooltip));
	}

}
