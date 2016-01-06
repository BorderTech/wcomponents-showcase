/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Marginable;
import com.github.bordertech.wcomponents.WCheckBoxSelect;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class MarginCheckboxSelect extends WCheckBoxSelect {

	enum LOCATION {
		NORTH,
		EAST,
		SOUTH,
		WEST
	}

	public MarginCheckboxSelect() {
		super(LOCATION.values());
	}

	public static void configMargin(final Marginable widget, final List<?> locations) {
		int north = locations.contains(LOCATION.NORTH) ? 24 : 0;
		int east = locations.contains(LOCATION.EAST) ? 24 : 0;
		int south = locations.contains(LOCATION.SOUTH) ? 24 : 0;
		int west = locations.contains(LOCATION.WEST) ? 24 : 0;
		Margin margin = new Margin(north, east, south, west);
		widget.setMargin(margin);
	}

}
