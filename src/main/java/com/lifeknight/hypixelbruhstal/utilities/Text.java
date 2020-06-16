package com.lifeknight.hypixelbruhstal.utilities;

public class Text {

	
	public static String removeAll(String msg, String rmv) {
		msg = msg.replaceAll(rmv, "");
		return msg;
	}

	public static String removeFormattingCodes(String input) {
		String formattingSymbol = "";
		formattingSymbol += '\u00A7';

		input = removeAll(input, formattingSymbol + "0");
		input = removeAll(input, formattingSymbol + "1");
		input = removeAll(input, formattingSymbol + "2");
		input = removeAll(input, formattingSymbol + "3");
		input = removeAll(input, formattingSymbol + "4");
		input = removeAll(input, formattingSymbol + "5");
		input = removeAll(input, formattingSymbol + "6");
		input = removeAll(input, formattingSymbol + "7");
		input = removeAll(input, formattingSymbol + "8");
		input = removeAll(input, formattingSymbol + "9");
		input = removeAll(input, formattingSymbol + "a");
		input = removeAll(input, formattingSymbol + "b");
		input = removeAll(input, formattingSymbol + "c");
		input = removeAll(input, formattingSymbol + "d");
		input = removeAll(input, formattingSymbol + "e");
		input = removeAll(input, formattingSymbol + "f");
		input = removeAll(input, formattingSymbol + "k");
		input = removeAll(input, formattingSymbol + "l");
		input = removeAll(input, formattingSymbol + "m");
		input = removeAll(input, formattingSymbol + "n");
		input = removeAll(input, formattingSymbol + "o");
		input = removeAll(input, formattingSymbol + "r");

		return input;
	}
}
