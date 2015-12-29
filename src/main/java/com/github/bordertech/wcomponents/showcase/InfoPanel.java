package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WLink;
import com.github.bordertech.wcomponents.WList;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.showcase.util.SourceUtil;
import com.github.bordertech.wcomponents.showcase.widgets.Showcase;
import java.util.ArrayList;
import java.util.List;

/**
 * This component displays info about the widget.
 *
 * @author Jonathan Austin
 */
public class InfoPanel extends WPanel {

	private final WText source = new WText();

	private final WList relatedLinks = new WList(WList.Type.STACKED);
	private final WList apiSuperLinks = new WList(WList.Type.STACKED);
	private final WList apiInterfaceLinks = new WList(WList.Type.STACKED);

	/**
	 * Creates a SourcePanel.
	 */
	public InfoPanel() {
		setBeanProperty(".");

		source.setEncodeText(false);
		add(source);
		add(new WHeading(HeadingLevel.H2, "Related"));
		add(relatedLinks);
		relatedLinks.setRepeatedComponent(new WText());

		add(new WHeading(HeadingLevel.H2, "API"));
		add(apiSuperLinks);

		add(new WHeading(HeadingLevel.H2, "Interfaces"));
		add(apiInterfaceLinks);

		apiSuperLinks.setRepeatedComponent(new ApiLink());
		apiInterfaceLinks.setRepeatedComponent(new ApiLink());

		relatedLinks.setSearchAncestors(false);
		apiSuperLinks.setSearchAncestors(false);
		apiInterfaceLinks.setSearchAncestors(false);

	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			Showcase item = getShowcase();
			// Javadoc for Info Text
			String rawSource = SourceUtil.getSource(item.getWidgetClass().getName());
			String info = SourceUtil.getJavaDoc(rawSource);
			source.setText(info);
			// API Links
			setupApiLinks(item.getWidgetClass());
			setInitialised(true);
		}
	}

	private Showcase getShowcase() {
		return (Showcase) getBean();
	}

	private void setupApiLinks(final Class clazz) {
		List<Class> superClasses = new ArrayList<>();
		List<Class> interfaceClasses = new ArrayList<>();

		getApiClasses(superClasses, interfaceClasses, clazz);

		apiSuperLinks.setBean(superClasses);
		apiInterfaceLinks.setBean(interfaceClasses);
	}

	private void getApiClasses(final List<Class> superClasses, List<Class> interfaceClasses, final Class clazz) {
		if (clazz == null) {
			return;
		}

		if (!clazz.getPackage().getName().startsWith("com.github.bordertech.wcomponents")) {
			return;
		}

		// Classes
		superClasses.add(clazz);

		// Interfaces
		for (Class inter : clazz.getInterfaces()) {
			if (inter.getPackage().getName().startsWith("com.github.bordertech.wcomponents")) {
				interfaceClasses.add(inter);
			}
		}

		// Recurse up super classes
		getApiClasses(superClasses, interfaceClasses, clazz.getSuperclass());
	}

	public static class ApiLink extends WLink {

		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				Class clazz = (Class) getBean();
				setText(clazz.getSimpleName());
				String htmlName = '/' + clazz.getName().replace('.', '/') + ".html";
				setUrl("http://bordertech.github.io/wcomponents/apidocs" + htmlName);
				setInitialised(true);
			}
		}

	}

}
