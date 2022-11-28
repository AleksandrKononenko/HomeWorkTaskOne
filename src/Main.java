import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void setAttribute(Element element) {
        String name = element.getAttribute("name");
        String surname = element.getAttribute("surname");
        element.setAttribute("name", name + " " + surname);
        element.removeAttribute("surname");
    }
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        DocumentBuilder b = f.newDocumentBuilder();
        Document doc = b.parse(new File("Person.xml"));
        doc.getDocumentElement().normalize();
        NodeList person = doc.getElementsByTagName("person");
        for (int i = 0; i < person.getLength(); i++) {
            setAttribute((Element) person.item(i));
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File("prettyprint.xsl")));
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("NewPerson.xml"));
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        transformer.transform(source, result);
    }
}