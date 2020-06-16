package com.lifeknight.hypixelbruhstal.utilities;

import java.util.ArrayList;

public class Logic {
	public static ArrayList<String> returnStartingEntries(ArrayList<String> arrayList, String input) {
		ArrayList<String> result = new ArrayList<String>();
		if (!input.equals("") && arrayList != null) {
			for (String element: arrayList) {
				try {
					if (element.toLowerCase().startsWith(input.toLowerCase())) {
						result.add(element);
					}
				} catch (Exception ignored) {

				}
			}
		} else {
			result.addAll(arrayList);
		}
		return result;
	}
}
