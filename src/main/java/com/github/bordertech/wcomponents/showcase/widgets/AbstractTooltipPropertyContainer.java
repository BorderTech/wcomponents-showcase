package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTextField;

/**
 *
 * @author jonathan
 */
public abstract class AbstractTooltipPropertyContainer<T extends WComponent> extends AbstractPropertyContainer<T> {

	// Tooltip
	private final WTextField txtTooltip = new WTextField();

	public AbstractTooltipPropertyContainer(final T widget, final AjaxTarget target) {
		super(widget, target);

		// Tooltip
		addPropertyWidget("Tooltip", txtTooltip);
	}

	protected void configWidgetProperties(final T widget) {
		widget.setToolTip(getPropertyStringValue(txtTooltip));
	}

}
