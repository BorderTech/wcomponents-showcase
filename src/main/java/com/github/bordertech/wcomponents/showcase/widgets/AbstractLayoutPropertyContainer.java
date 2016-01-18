package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WCheckBoxSelect;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.layout.LayoutManager;
import com.github.bordertech.wcomponents.showcase.common.MarginCheckboxSelect;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;

/**
 *
 * @author jonathan
 */
public abstract class AbstractLayoutPropertyContainer<T extends LayoutManager> extends AbstractPropertyContainer<T> {

	private static final int DEFAULT_HGAP = 12;
	private static final int DEFAULT_VGAP = 6;

	private final WCheckBoxSelect cbsMargin = new MarginCheckboxSelect();
	private final WCheckBox chbSpacing = new WCheckBox();
	private final WPanel panel;

	public AbstractLayoutPropertyContainer(final SampleContainer<T> sampleContainer, final WPanel panel) {
		super(sampleContainer);
		this.panel = panel;

		addPropertyWidget("Margin", cbsMargin);
		addPropertyWidget("Spacing", chbSpacing);
	}

	@Override
	protected void configWidgetProperties(final T widget) {
		MarginCheckboxSelect.configMargin(getPanel(), cbsMargin.getSelected());
	}

	protected WPanel getPanel() {
		return panel;
	}

	protected boolean isUseSpacing() {
		return chbSpacing.isSelected();
	}

	protected int getHGap() {
		return isUseSpacing() ? DEFAULT_HGAP : 0;
	}

	protected int getVGap() {
		return isUseSpacing() ? DEFAULT_VGAP : 0;
	}

	@Override
	protected T getWidget() {
		return (T) panel.getLayout();
	}

}
