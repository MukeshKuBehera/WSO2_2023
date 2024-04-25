package com.test;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.json.JSONObject;
import org.json.XML;

public class Json_to_xml extends AbstractMediator { 

	public boolean mediate(MessageContext context) {
		 String jsonMsg = null;
		 String XML = null;
		
		Object msg=context.getProperty("Request_Message");
		
		if(msg!=null) {
			
			 jsonMsg=msg.toString();
		}else {
			
			throw new NullPointerException("INCOMING MESSAGE IS NULL....!");
		}
		// TODO Implement your mediation logic here 
		
		
		try {
			
		
		XML=convertToXML(jsonMsg);
		
		if(XML!=null) {
			context.setProperty("XML_Msg", XML);
		}else {
			
			throw new NullPointerException("JSON TO XML NOT CONVERTED....! ");
		}
		
		} catch (Exception e) {
			// TODO: handle exception
			handleException("An error occurred in MyClassMediator.", e, context);
		}
		
		
		return true;
		
		
		
	}

	private String convertToXML(String inputMsg) {
		// TODO Auto-generated method stub
JSONObject jsonObject = new JSONObject(inputMsg); 
		
		//String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<root>" + XML.toString(jsonObject) + "</root>";  
		String xml =  XML.toString(jsonObject);  

		return xml;
	}
}
