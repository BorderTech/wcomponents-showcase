package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPartialDateField;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.layout.ColumnLayout;
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
		return new PropertiesPanel(sample.getPanel());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends WPanel implements SampleContainer<ColumnLayout> {

		private final WPanel panel;

		public SamplePanel() {

			WPanel box = new WPanel(Type.FEATURE);
			add(box);

			// SAMPLE-START
			panel = new WPanel();
			panel.setLayout(new ColumnLayout(new int[]{33, 34, 33}, 6, 6));
			for (int i = 1; i < 4; i++) {
//				panel.add(new WText("Child " + i));
				WPanel child = new WPanel(Type.BOX);
				child.add(new WText("Child " + i));
				panel.add(child);
			}
			// SAMPLE-FINISH

			box.add(panel);
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

		private final WDropdown drpAlignment = new WDropdown();

		public PropertiesPanel(final WPanel panel) {
			super(panel);
			List<ColumnLayout.Alignment> alignments = new ArrayList<>(Arrays.asList(ColumnLayout.Alignment.values()));
			alignments.add(0, null);
			drpAlignment.setOptions(alignments);
			drpAlignment.setSelected(ColumnLayout.Alignment.CENTER);
			addPropertyWidget("Alignment", drpAlignment);
		}

		@Override
		protected void configWidgetProperties(final ColumnLayout widget) {
			super.configWidgetProperties(widget);
			int hgap = getHGap();
			int vgap = getVGap();
			ColumnLayout.Alignment[] alignments = null;
			ColumnLayout.Alignment selected = (ColumnLayout.Alignment) drpAlignment.getSelected();
			if (selected != null) {
				alignments = new ColumnLayout.Alignment[3];
				Arrays.fill(alignments, selected);
			}

			ColumnLayout layout = new ColumnLayout(new int[]{33, 34, 33}, alignments, hgap, vgap);
			getPanel().setLayout(layout);
		}

	}

}
