package util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CheckTime {

	private static Document openXML() throws ParserConfigurationException, SAXException, IOException{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse( getFile() );
		return doc;
	}
	
	private static String getFile(){
		String path = System.getProperty("user.dir");
		path += File.separator + "sendTime.xml";
		return path;
	}
	
	private static void saveXML( Document doc ) throws TransformerException{
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer trans = factory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(getFile());
		trans.transform(source, result);
	}
	@SuppressWarnings("deprecation")
	public static boolean isSendTime(){
		
		try {
			int week = getWeek();
			Date now = new Date();
			if( now.getDay() == week ){
				Date start = new Date( now.getYear(), now.getMonth(), now.getDate(), 21, 32 );
				Date end = new Date( now.getYear(), now.getMonth(), now.getDate(), 23, 30 );
				if( now.compareTo(start) > 0  && now.compareTo(end) < 0 )return true;
			}
		}catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	public static long waitTime(){
		
		try {
			int week = getWeek();
			Date now = new Date();
			int dist = week - now.getDay();
			if( dist < 0 ) dist += 7;
			Date next = new Date();
			next.setDate( now.getDate() + dist );
			next.setHours(21);
			next.setMinutes(40);
			return next.getTime() - now.getTime();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	private static int getWeek() throws ParserConfigurationException, SAXException, IOException{
		Document doc = openXML();
		Element root = doc.getDocumentElement();
		NodeList nodes = root.getChildNodes();
		for( int i = 0; i < nodes.getLength(); i++ ){
			if( "week".equals( nodes.item(i).getNodeName()) ){
				Element node = (Element) nodes.item(i);
				String value = node.getTextContent();
				int week = Integer.parseInt(value);
				return week;
			}
		}
		return -1;
	}
	public static void turnTime(){
		
		try {
			Document doc = openXML();
			Element root = doc.getDocumentElement();
			NodeList times = root.getElementsByTagName("week");
			Node time = times.item(0);
			root.removeChild(time);
			root.appendChild(time);
			saveXML(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
