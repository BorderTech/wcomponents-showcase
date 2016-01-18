package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPartialDateField;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.layout.ColumnLayout;
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
public class ColumnLayoutShowcase extends AbstractShowcase<ColumnLayout> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WPartialDateField.class));

	public ColumnLayoutShowcase() {
		super(ColumnLayout.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<ColumnLayout> itemPanel) {
		SamplePanel sample = (SamplePanel) itemPanel;
		return new PropertiesPanel(itemPanel, sample.getPanel());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends AbstractLayoutSample<ColumnLayout> {

		private final WPanel panel;

		public SamplePanel() {

			// SAMPLE-START
			panel = new WPanel();
			panel.setLayout(new ColumnLayout(new int[]{33, 34, 33}));
			for (int i = 1; i < 7; i++) {
				WPanel child = new WPanel(Type.BOX);
				child.add(new WText("Child " + i));
				panel.add(child);
			}
			// SAMPLE-FINISH

			add(panel);
		}

		@Override
		public ColumnLayout getWidget() {
			return (ColumnLayout) panel.getLayout();
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return panel;
		}

		public WPanel getPanel() {
			return panel;
		}
	}

	public static class PropertiesPanel extends AbstractLayoutPropertyContainer<ColumnLayout> {

		private final TypeWDropdown<ColumnLayout.Alignment> drpAlignment = new TypeWDropdown<>(ColumnLayout.Alignment.values(), true);

		public PropertiesPanel(final SampleContainer<ColumnLayout> sampleContainer, final WPanel panel) {
			super(sampleContainer, panel);
			drpAlignment.setSelected(ColumnLayout.Alignment.CENTER);
			addPropertyWidget("Alignment", drpAlignment);
		}

		@Override
		protected void configWidgetProperties(final ColumnLayout widget) {
			super.configWidgetProperties(widget);
			int hgap = getHGap();
			int vgap = getVGap();
			ColumnLayout.Alignment[] alignments = null;
			ColumnLayout.Alignment selected = drpAlignment.getSelected();
			if (selected != null) {
				alignments = new ColumnLayout.Alignment[3];
				Arrays.fill(alignments, selected);
			}

			ColumnLayout layout = new ColumnLayout(new int[]{33, 34, 33}, alignments, hgap, vgap);
			getPanel().setLayout(layout);
		}

	}

}
