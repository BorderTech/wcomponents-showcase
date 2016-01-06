package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.WCheckBoxSelect;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.layout.LayoutManager;
import com.github.bordertech.wcomponents.showcase.util.PropertyUtil;

/**
 *
 * @author jonathan
 */
public abstract class AbstractLayoutPropertyContainer<T extends LayoutManager> extends AbstractPropertyContainer<T> {

	private final WCheckBoxSelect cbsMargin = new MarginCheckboxSelect();
	private final WNumberField numHGap = new WNumberField();
	private final WNumberField numVGap = new WNumberField();
	private final WPanel panel;

	public AbstractLayoutPropertyContainer(final WPanel panel) {
		super(null, panel);
		this.panel = panel;

		numHGap.setMinValue(0);
		numVGap.setMinValue(0);

		numHGap.setNumber(6);
		numVGap.setNumber(6);

		addPropertyWidget("Margin", cbsMargin);
		addPropertyWidget("Horizontal gap", numHGap);
		addPropertyWidget("Vertical gap", numVGap);
	}

	@Override
	protected void configWidgetProperties(final T widget) {
		MarginCheckboxSelect.configMargin(getPanel(), cbsMargin.getSelected());
	}

	protected WPanel getPanel() {
		return panel;
	}

	protected int getHGap() {
		return PropertyUtil.getPropertyIntValue(numHGap);
	}

	protected int getVGap() {
		return PropertyUtil.getPropertyIntValue(numVGap);
	}

	@Override
	protected T getWidget() {
		return (T) panel.getLayout();
	}

}
