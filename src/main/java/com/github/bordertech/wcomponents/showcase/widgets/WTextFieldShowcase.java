package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTextArea;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.showcase.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.SampleContainer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class WTextFieldShowcase extends AbstractShowcase<WTextField> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WTextArea.class));

	public WTextFieldShowcase() {
		super(WTextField.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<WTextField> itemPanel) {
		return new PropertiesPanel(itemPanel.getWidget());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends WPanel implements SampleContainer<WTextField> {

		private final WTextField widget;

		public SamplePanel() {

			// SAMPLE-START
			WFieldLayout layout = new WFieldLayout();
			layout.setLabelWidth(30);
			add(layout);

			widget = new WTextField();
			layout.addField("Textfield", widget);
			// SAMPLE-FINISH
		}

		@Override
		public WTextField getWidget() {
			return widget;
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return widget;
		}

	}

	public static class PropertiesPanel extends AbstractInputPropertyContainer<WTextField> {

		private final WNumberField numCols = new WNumberField();
		private final WNumberField numMaxLength = new WNumberField();
		private final WNumberField numMinLength = new WNumberField();

		public PropertiesPanel(final WTextField widget) {
			super(widget, widget);

			// Columns
			numCols.setMinValue(1);
			addPropertyWidget("Columns", numCols);
			addPropertyWidget("Max length", numMaxLength);
			addPropertyWidget("Min length", numMinLength);

//		private Pattern pattern;
//		private WSuggestions suggestions;
		}

		@Override
		protected void configWidgetProperties(final WTextField widget) {
			super.configWidgetProperties(widget);

			int cols = numCols.getValue() == null ? 0 : numCols.getValue().intValue();
			widget.setColumns(cols);
		}

	}

}
