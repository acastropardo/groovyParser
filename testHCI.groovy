import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.*;
import groovy.util.*;
import groovy.xml.*;
import com.sap.gateway.ip.core.customdev.util.Message;
import org.codehaus.*;
import java.util.HashMap;
def Message processData(Message message) {

//Properties
	def pMap = message.getProperties();
	def Period_Start_Date_ext = pMap.get("Date_d_execution_depuis_le");
	def Period_End_Date_ext = pMap.get("Date_d_execution_jusquau");
    
//Body
    def body_xml= message.getBody(java.lang.String) as String;
	Node root = new XmlParser().parseText(body_xml);
	
    root.appendNode("period_start_date", [:], Period_Start_Date_ext);
	root.appendNode("period_end_date", [:], Period_End_Date_ext);
	
	def xml = XmlUtil.serialize(root);
	message.setBody(xml); 

return message;

}