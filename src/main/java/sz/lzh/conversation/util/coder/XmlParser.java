package sz.lzh.conversation.util.coder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlParser {


	public static Document parse(InputStream inputStream){

		try {
			SAXReader saxReader = new SAXReader();  
			//根据saxReader的read重写方法可知，既可以通过inputStream输入流来读取，也可以通过file对象来读取   
			//Document document = saxReader.read(inputStream);    
			Document document = saxReader.read(inputStream);
			return	document;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static XmlNode parseNode(InputStream inputStream){
		Document document = parse(inputStream);
		Element element = document.getRootElement();
		return new XmlNode(element);
	}
	public List<Element> parseAllNode(InputStream inputStream) {
		Document document = parse(inputStream);
		List<Element> elements = document.getRootElement().elements();
		return elements;
	}
	public List<Element> parseAllNode(InputStream inputStream,String Node) {
		Document document = parse(inputStream);
		List<Element> elements = document.selectNodes(Node);
		
		return elements;
	}
	public List<Element> parseNode(InputStream inputStream,String node) {
		Document document = parse(inputStream);
		List<Element> elements = document.getRootElement().elements(node);
		return elements;
	}
	public String parseElement(InputStream inputStream,String node,String key){
		Document document = parse(inputStream);
		Element e = document.getRootElement().element(node).element(key);
		return e.getStringValue();
	}

	public String parseElement(Element element,String key){
		Element e = element.element(key);
		return e.getStringValue();
	}

	public static  class XmlNode{

		private List<XmlNode> xNodeList;
		private Element element;

		public XmlNode(Element element){
			xNodeList = new ArrayList<XmlParser.XmlNode>();
			this.element = element;
			//子节点
			List<Element> childElements = element.elements();
			if(element.elements().size()>0){
				for(int i =0;i<childElements.size();i++){
					getxNodeList().add(new XmlNode(childElements.get(i)));
				}
			}
		}

		public Element getElement() {
			return element;
		}


		public List<XmlNode> getxNodeList() {
			return xNodeList;
		}

	}
}
