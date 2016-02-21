package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WCheckBoxSelect;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPartialDateField;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.layout.FlowLayout;
import com.github.bordertech.wcomponents.showcase.common.MarginCheckboxSelect;
import com.github.bordertech.wcomponents.showcase.common.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;
import com.github.bordertech.wcomponents.showcase.common.TypeWDropdown;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class WPanelShowcase extends AbstractShowcase<WPanel> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WPartialDateField.class));

	public WPanelShowcase() {
		super(WPanel.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<WPanel> itemPanel) {
		return new PropertiesPanel(itemPanel);
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends AbstractLayoutSample<WPanel> {

		private final WPanel widget;

		public SamplePanel() {

			// SAMPLE-START
			widget = new WPanel();
			widget.setLayout(new FlowLayout(FlowLayout.Alignment.CENTER));
			widget.add(new WText("Content"));
			// SAMPLE-FINISH

			add(widget);

		}

		@Override
		public WPanel getWidget() {
			return widget;
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return widget;
		}

	}

	public static class PropertiesPanel extends AbstractPropertyContainer<WPanel> {

		private final TypeWDropdown<Type> drpType = new TypeWDropdown<>(Type.values(), true);
		private final WTextField txtTitle = new WTextField();
		private final TypeWDropdown<PanelMode> drpMode = new TypeWDropdown<>(PanelMode.values(), true);

		private final WCheckBoxSelect cbsMargin = new MarginCheckboxSelect();

		public PropertiesPanel(final SampleContainer<WPanel> sampleContainer) {
			super(sampleContainer);

			addPropertyWidget("Margin", cbsMargin);
			addPropertyWidget("Type", drpType);
			addPropertyWidget("Title", txtTitle);
			addPropertyWidget("Mode", drpMode);

		}

		@Override
		protected void configWidgetProperties(final WPanel widget) {
			MarginCheckboxSelect.configMargin(widget, cbsMargin.getSelected());
			widget.setType(drpType.getSelected() == null ? WPanel.Type.PLAIN : drpType.getSelected());
			widget.setTitleText(txtTitle.getText());
			widget.setMode(drpMode.getSelected());
		}

	}

}
