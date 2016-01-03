package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WCheckBox;
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
public class WTextAreaShowcase extends AbstractShowcase<WTextArea> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WTextField.class));

	public WTextAreaShowcase() {
		super(WTextArea.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<WTextArea> itemPanel) {
		return new PropertiesPanel(itemPanel.getWidget());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends WPanel implements SampleContainer<WTextArea> {

		private final WTextArea widget;

		public SamplePanel() {
			// SAMPLE-START
			WFieldLayout layout = new WFieldLayout();
			layout.setLabelWidth(30);
			add(layout);

			widget = new WTextArea();
			layout.addField("Textarea", widget);
			// SAMPLE-FINISH
		}

		@Override
		public WTextArea getWidget() {
			return widget;
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return widget;
		}

	}

	public static class PropertiesPanel extends WTextFieldShowcase.PropertiesPanel {

		private final WNumberField numRows = new WNumberField();
		private final WCheckBox chbRichText = new WCheckBox();

		public PropertiesPanel(final WTextArea widget) {
			super(widget);

			addPropertyWidget("Rows", numRows);
			addPropertyWidget("Rich text", chbRichText);
		}

		@Override
		protected void configWidgetProperties(final WTextField widget) {
			super.configWidgetProperties(widget);

			WTextArea area = (WTextArea) widget;

			int rows = numRows.getValue() == null ? 0 : numRows.getValue().intValue();
			area.setRows(rows);

			area.setRichTextArea(chbRichText.isSelected());
		}

	}

}
