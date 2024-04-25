package com.test;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.json.JSONObject;
import org.json.XML;

public class Xml_to_json extends AbstractMediator { 

	public boolean mediate(MessageContext context) { 
		String XML_Msg=null;
		String JSON_Msg=null;
		
		Object in_msg=context.getProperty("Xml_Msg");
		
		if (in_msg!=null) {
			XML_Msg=in_msg.toString();
			
		}else {
			throw new NullPointerException("Request Payload is Empty");
		}
		try {
			JSON_Msg=convertToJSON(XML_Msg);
			
			if(JSON_Msg!=null) {
				context.setProperty("JSON_Msg", JSON_Msg);
			}else {
				context.setProperty("JSON_Msg",new NullPointerException("XML TO JSON NOT CONVERTED....!"));
				//throw new NullPointerException("XML TO JSON NOT CONVERTED....! ");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//handleException("An error occured ClassMediator.", context);
		}
		
		
		
		return true;
	}

	private String convertToJSON(String msg) {
		// TODO Auto-generated method stub
		
		JSONObject json=XML.toJSONObject(msg);
		String jsonstring=json.toString();
		return jsonstring;
	}
}
