package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WCheckBoxSelect;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPartialDateField;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.layout.FlowLayout;
import com.github.bordertech.wcomponents.showcase.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.SampleContainer;
import java.util.ArrayList;
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
		return new PropertiesPanel(itemPanel.getWidget());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends WPanel implements SampleContainer<WPanel> {

		private final WPanel widget;

		public SamplePanel() {

			WPanel box = new WPanel(Type.FEATURE);
			add(box);

			// SAMPLE-START
			widget = new WPanel();
			widget.setLayout(new FlowLayout(FlowLayout.Alignment.CENTER));
			widget.add(new WText("Content"));
			// SAMPLE-FINISH

			box.add(widget);

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

		private final WDropdown drpType = new WDropdown();
		private final WTextField txtTitle = new WTextField();
		private final WDropdown drpMode = new WDropdown();

		private final WCheckBoxSelect cbsMargin = new MarginCheckboxSelect();

		public PropertiesPanel(final WPanel widget) {
			super(widget, widget);

			List<Type> types = new ArrayList<>(Arrays.asList(Type.values()));
			types.add(0, null);
			drpType.setOptions(types);

			List<PanelMode> modes = new ArrayList<>(Arrays.asList(PanelMode.values()));
			modes.add(0, null);
			drpMode.setOptions(modes);

			addPropertyWidget("Type", drpType);
			addPropertyWidget("Title", txtTitle);
			addPropertyWidget("Mode", drpMode);
			addPropertyWidget("Margin", cbsMargin);

		}

		@Override
		protected void configWidgetProperties(final WPanel widget) {
			widget.setType((Type) drpType.getSelected());
			widget.setTitleText(txtTitle.getText());
			widget.setMode((PanelMode) drpMode.getSelected());
			MarginCheckboxSelect.configMargin(widget, cbsMargin.getSelected());
		}

	}

}
