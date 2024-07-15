package org.xmlComparator;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.SAXException;

public class Main {

    public static void main(String[] args) throws Exception {
        File file1 = new File("C:\\Users\\Snof_Bo\\Desktop\\XML_Comparator\\student1.xml");
        File file2 = new File("C:\\Users\\Snof_Bo\\Desktop\\XML_Comparator\\file3.xml");

        // parse xml file to document object
        Document doc1 = parseXML(file1);
        Document doc2 = parseXML(file2);

        Element root1 = doc1.getDocumentElement();
        Element root2 = doc2.getDocumentElement();
        // compare the root of both files
        if (compareElements(root1, root2, "")) {
            System.out.println("Les deux fichiers sont identiques.");
        } else {
            System.out.println("Les deux fichiers sont différents.");
        }
    }

    private static Document parseXML(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(file);
    }

    /*private static boolean compareElements(Element e1, Element e2, String path) {
        path += "/" + e1.getTagName();
        System.err.println("path : "+path);
        // Compare tag names
        if (!e1.getTagName().equals(e2.getTagName())) {
            System.out.println("Tag name différent dans " + path + ": " + e1.getTagName() + " vs " + e2.getTagName());
            return false;
        }

        // Compare attributes
        NamedNodeMap attributes1 = e1.getAttributes();
        NamedNodeMap attributes2 = e2.getAttributes();
        if (attributes1.getLength() != attributes2.getLength()) {
            System.out.println("Nombre des attributes sont différent dans " + path + ": " + attributes1.getLength() + " vs " + attributes2.getLength());
            return false;
        }
        for (int i = 0; i < attributes1.getLength(); i++) {
            Node attr1 = attributes1.item(i);
            Node attr2 = attributes2.getNamedItem(attr1.getNodeName());
            if (attr2 == null || !attr1.getNodeValue().equals(attr2.getNodeValue())) {
                System.out.println("La valeur de l'attribute est différent dans " + path + "@" + attr1.getNodeName() + ": " + attr1.getNodeValue() + " vs " + (attr2 != null ? attr2.getNodeValue() : "null"));
                System.err.println("Les fichiers doivent être du même type.");
                return false;
            }
        }

        // Handle special case for "class" elements
        if (e1.getTagName().equals("class")) {
            Map<String, Element> elementsMap1 = getElementsMap(e1, "object", "id");
            Map<String, Element> elementsMap2 = getElementsMap(e2, "object", "id");

            if (elementsMap1.size() != elementsMap2.size()) {
                System.out.println("Le nombre des étudiants est différent.");
                return false;
            }

            boolean equivalent = true;
            for (String key : elementsMap1.keySet()) {
                if (!elementsMap2.containsKey(key)) {
                    System.out.println("Étudiant avec ID " + key + " est manquant dans le deuxième fichier XML.");
                    equivalent = false;
                    continue;
                }

                Element student1 = elementsMap1.get(key);
                Element student2 = elementsMap2.get(key);

                if (!compareElements(student1, student2, path)) {
                    equivalent = false;
                }
            }
            return equivalent;
        }

        // Compare child nodes
        NodeList children1 = e1.getChildNodes();
        NodeList children2 = e2.getChildNodes();
        if (children1.getLength() != children2.getLength()) {
            System.out.println("Nombre d'enfants différent dans " + path + ": " + children1.getLength() + " vs " + children2.getLength());
            return false;
        }

        boolean equivalent = true;
        for (int i = 0; i < children1.getLength(); i++) {
            Node child1 = children1.item(i);
            Node child2 = children2.item(i);

            if (child1.getNodeType() != child2.getNodeType()) {
                System.out.println("Le type différent dans " + path + ": " + child1.getNodeType() + " vs " + child2.getNodeType());
                equivalent = false;
                continue;
            }

            if (child1.getNodeType() == Node.ELEMENT_NODE) {
                if (!compareElements((Element) child1, (Element) child2, path)) {
                    equivalent = false;
                }
            } else if (child1.getNodeType() == Node.TEXT_NODE) {
                if (!child1.getTextContent().trim().equals(child2.getTextContent().trim())) {
                    System.out.println("Le contenu de texte est différent dans " + path + ": " + child1.getTextContent().trim() + " vs " + child2.getTextContent().trim());
                    System.err.println("Le contenu de texte est différent dans " + path + ": " + child1.getAttributes().equals("name") + " vs " + child1.getAttributes().equals("name"));
                    equivalent = false;
                }

            }
        }
        return equivalent;
    }*/
    private static boolean compareElements(Element e1, Element e2, String path) {
        path += "/" + e1.getTagName();
        System.err.println("path : " + path);

        // Compare tag names
        if (!e1.getTagName().equals(e2.getTagName())) {
            System.out.println("Tag name différent dans " + path + ": " + e1.getTagName() + " vs " + e2.getTagName());
            return false;
        }

        // Compare attributes
        NamedNodeMap attributes1 = e1.getAttributes();
        NamedNodeMap attributes2 = e2.getAttributes();
        if (attributes1.getLength() != attributes2.getLength()) {
            System.out.println("Nombre des attributes sont différent dans " + path + ": " + attributes1.getLength() + " vs " + attributes2.getLength());
            return false;
        }
        for (int i = 0; i < attributes1.getLength(); i++) {
            Node attr1 = attributes1.item(i);
            Node attr2 = attributes2.getNamedItem(attr1.getNodeName());
            if (attr2 == null || !attr1.getNodeValue().equals(attr2.getNodeValue())) {
                System.out.println("La valeur de l'attribute est différent dans " + path + "@" + attr1.getNodeName() + ": " + attr1.getNodeValue() + " vs " + (attr2 != null ? attr2.getNodeValue() : "null"));
                System.err.println("Les fichiers doivent être du même type.");
                return false;
            }
        }

        // Handle special case for "class" elements
        if (e1.getTagName().equals("class")) {
            Map<String, Element> elementsMap1 = getElementsMap(e1, "object", "id");
            Map<String, Element> elementsMap2 = getElementsMap(e2, "object", "id");

            if (elementsMap1.size() != elementsMap2.size()) {
                System.out.println("Le nombre des étudiants est différent.");
                return false;
            }

            boolean equivalent = true;
            for (String key : elementsMap1.keySet()) {
                if (!elementsMap2.containsKey(key)) {
                    System.out.println("Étudiant avec ID " + key + " est manquant dans le deuxième fichier XML.");
                    equivalent = false;
                    continue;
                }

                Element student1 = elementsMap1.get(key);
                Element student2 = elementsMap2.get(key);

                if (!compareElements(student1, student2, path)) {
                    equivalent = false;
                }
            }
            return equivalent;
        }

        // Compare child nodes
        NodeList children1 = e1.getChildNodes();
        NodeList children2 = e2.getChildNodes();
        if (children1.getLength() != children2.getLength()) {
            System.out.println("Nombre d'enfants différent dans " + path + ": " + children1.getLength() + " vs " + children2.getLength());
            return false;
        }

        boolean equivalent = true;
        for (int i = 0; i < children1.getLength(); i++) {
            Node child1 = children1.item(i);
            Node child2 = children2.item(i);

            if (child1.getNodeType() != child2.getNodeType()) {
                System.out.println("Le type différent dans " + path + ": " + child1.getNodeType() + " vs " + child2.getNodeType());
                equivalent = false;
                continue;
            }

            if (child1.getNodeType() == Node.ELEMENT_NODE) {
                if (!compareElements((Element) child1, (Element) child2, path)) {
                    equivalent = false;
                }
            } else if (child1.getNodeType() == Node.TEXT_NODE) {
                String text1 = child1.getTextContent().trim();
                String text2 = child2.getTextContent().trim();
                if (!text1.equals(text2)) {
                    String parentElement = getParentElementInfo(child1);
                    System.out.println("Le contenu de texte est différent dans " + path + ": \"" + text1 + "\" vs \"" + text2 + "\"");
                    System.out.println("Changement détecté dans le champ: " + getTagNameWithAttributes(child1));
                    System.out.println(parentElement);
                    equivalent = false;
                }
            }
        }
        return equivalent;
    }

    private static String getTagNameWithAttributes(Node node) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return node.getNodeName();
        }

        Element element = (Element) node;
        NamedNodeMap attributes = element.getAttributes();
        StringBuilder sb = new StringBuilder();
        sb.append(element.getTagName());
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            sb.append(" [").append(attr.getNodeName()).append("=").append(attr.getNodeValue()).append("]");
        }
        return sb.toString();
    }

    private static String getParentElementInfo(Node node) {
        Node parent = node.getParentNode();
        if (parent != null && parent.getNodeType() == Node.ELEMENT_NODE) {
            return getTagNameWithAttributes(parent);
        }
        return "no parent";
    }

    public static Map<String, Element> getElementsMap(Element parentElement, String elementTag, String keyTag) {
        Map<String, Element> map = new HashMap<>();
        NodeList elementsList = parentElement.getElementsByTagName(elementTag);
        for (int i = 0; i < elementsList.getLength(); i++) {
            Element element = (Element) elementsList.item(i);
            String key = element.getElementsByTagName(keyTag).item(0).getTextContent();
            map.put(key, element);
        }
        return map;
    }
}
