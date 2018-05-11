package pojo;

import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RssHandler extends DefaultHandler {
	private final static Stack<String> STACK = new Stack<String>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private Item item = new Item();
	private StringBuilder stringBuilder = new StringBuilder();

	public RssHandler(ArrayList<Item> items) {
		this.items = items;
	}

	public void startDocument() {
	}

	public void endDocument() {
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("item")) {
			items.add(item);
			item = new Item();
		}
		if (qName.equals("title") || qName.equals("link"))
			STACK.push(qName);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (!STACK.isEmpty() && (qName.equals("title") || qName.equals("link")))
			STACK.pop();
		if (qName.equals("title")) {
			item.setTitle(stringBuilder.toString());
			stringBuilder = new StringBuilder();
		}
		if (qName.equals("link")) {
			item.setLink(stringBuilder.toString());
			stringBuilder = new StringBuilder();
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		if (!STACK.isEmpty()) {
			String qName = STACK.peek();
			if (qName.equals("title"))
				stringBuilder.append(new String(ch, start, length));
			if (qName.equals("link"))
				stringBuilder.append(new String(ch, start, length));
		}

	}

}
