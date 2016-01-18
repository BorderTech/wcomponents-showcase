package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPartialDateField;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.layout.FlowLayout;
import com.github.bordertech.wcomponents.layout.GridLayout;
import com.github.bordertech.wcomponents.showcase.common.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;
import com.github.bordertech.wcomponents.showcase.util.PropertyUtil;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class GridLayoutShowcase extends AbstractShowcase<GridLayout> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WPartialDateField.class));

	public GridLayoutShowcase() {
		super(GridLayout.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<GridLayout> itemPanel) {
		SamplePanel sample = (SamplePanel) itemPanel;
		return new PropertiesPanel(itemPanel, sample.getPanel());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends AbstractLayoutSample<GridLayout> {

		private final WPanel panel;

		public SamplePanel() {

			// SAMPLE-START
			panel = new WPanel();
			panel.setLayout(new GridLayout(0, 3));
			for (int i = 1; i < 10; i++) {
				WPanel child = new WPanel(Type.BOX);
				child.setLayout(new FlowLayout(FlowLayout.Alignment.CENTER));
				child.add(new WText("Child " + i));
				panel.add(child);
			}
			// SAMPLE-FINISH

			add(panel);
		}

		@Override
		public GridLayout getWidget() {
			return (GridLayout) panel.getLayout();
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return panel;
		}

		public WPanel getPanel() {
			return panel;
		}
	}

	public static class PropertiesPanel extends AbstractLayoutPropertyContainer<GridLayout> {

		private final WNumberField numCols = new WNumberField() {
			@Override
			public void validate(final List<Diagnostic> diags) {
				super.validate(diags);
				if (diags.isEmpty()) {
					if (numCols.getValue() == null && numRows.getValue() == null) {
						diags.add(createErrorDiagnostic("At least a row or col must be provided."));
					}
				}
			}

		};
		private final WNumberField numRows = new WNumberField() {
			@Override
			public void validate(final List<Diagnostic> diags) {
				super.validate(diags);
				if (diags.isEmpty()) {
					if (numCols.getValue() == null && numRows.getValue() == null) {
						diags.add(createErrorDiagnostic("At least a row or col must be provided."));
					}
				}
			}
		};

		public PropertiesPanel(final SampleContainer<GridLayout> sampleContainer, final WPanel panel) {
			super(sampleContainer, panel);
			numCols.setMinValue(1);
			numCols.setNumber(3);
			numRows.setMinValue(1);

			addPropertyWidget("Cols", numCols);
			addPropertyWidget("Rows", numRows);
		}

		@Override
		protected void configWidgetProperties(final GridLayout widget) {
			super.configWidgetProperties(widget);
			if (PropertyUtil.isPropertyValid(numRows) && PropertyUtil.isPropertyValid(numCols)) {
				int rows = PropertyUtil.getPropertyIntValue(numRows);
				int cols = PropertyUtil.getPropertyIntValue(numCols);
				int hgap = getHGap();
				int vgap = getVGap();
				GridLayout layout = new GridLayout(rows, cols, hgap, vgap);
				getPanel().setLayout(layout);
			}
		}

	}

}
