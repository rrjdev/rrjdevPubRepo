package jsonConvPkg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

public class JSONXMLConverter {
    public JSONXMLConverter() {
    }


    public static String xml2json(String strXML,String rootName) {
        XMLSerializer xmlSer = new XMLSerializer();
        xmlSer.setRootName(rootName);
        xmlSer.setTypeHintsEnabled(false);
        xmlSer.setSkipNamespaces(true);
        xmlSer.setRemoveNamespacePrefixFromElements(true);
        JSONObject json = (JSONObject)xmlSer.read(strXML);
        return json.toString();
    }


    public static String json2xmlGeneric(String strJSON, String nameSpacePrefix,
                                         String rootName, String nameSpace) {
        String strippedJSONString = strJSON.replaceAll("\\r\\n|\\r|\\n", "");
        net.sf.json.JSON json = JSONSerializer.toJSON(strippedJSONString);
        XMLSerializer xmlSer = new XMLSerializer();
        xmlSer.setRootName(nameSpacePrefix + ":" + rootName);
        xmlSer.addNamespace(nameSpacePrefix, nameSpace);
        xmlSer.setElementName("iterElement");
        xmlSer.setTypeHintsEnabled(false);

        String xml = xmlSer.write(json);

        StringBuffer outString = new StringBuffer();
        Pattern pattern = Pattern.compile("(</?)(\\w+)>");
        Matcher matcher = pattern.matcher(xml);

        while (matcher.find())
        {
            matcher.appendReplacement(outString, "$1" + nameSpacePrefix + ":$2>");

         }
         matcher.appendTail(outString);

        return outString.toString();

    }
    
    
    public static void main(String args[]){
            try
            {
            JSONXMLConverter obj=new JSONXMLConverter();
            /*String result_1 = obj.xml2json("<a:WorkRequestCreationDetails xmlns:a=\"http://www.w3.org/2001/XMLSchema\"> \n" + 
            "<a:sysparm_quantity>1</a:sysparm_quantity>\n" + 
            "<a:variables>\n" + 
            "<a:Requestor>fmw-bau1</a:Requestor>\n" + 
            "<a:User>test user</a:User>\n" + 
            "<a:Userid>user123</a:Userid>\n" + 
            "</a:variables>\n" + 
            "</a:WorkRequestCreationDetails>","WorkRequestCreationDetails");
            System.out.println("XMLtoJSON:\n"+result_1+"\n");
            */
            //String result_op = "{\"Response\" : {\"success\":true, \"ws_id\":\"184\"}}";
                
            String result_op = "{\n" + 
            "   \"items\":    [\n" + 
            "            {\n" + 
            "         \"HoldId\": 300000033093068,\n" + 
            "         \"InvoiceNumber\": \"BPS2414\",\n" + 
            "         \"BusinessUnit\": \"N Brown Group\",\n" + 
            "         \"Supplier\": \"USA SUPPLIER-TEST\",\n" + 
            "         \"LineHeld\": null,\n" + 
            "         \"HoldDate\": \"2020-05-26T07:45:26.002+00:00\",\n" + 
            "         \"HoldDetails\": null,\n" + 
            "         \"HoldReason\": \"Hold applied on invoices interface to Bank System.\",\n" + 
            "         \"ReleaseReason\": \"Supplier finance Release Hold Reason\",\n" + 
            "         \"WorkflowStatus\": null,\n" + 
            "         \"ReleaseName\": \"SUPPLIER_FINANCE_RELEASE\",\n" + 
            "         \"HoldName\": \"SUPPLIER_FINANCE_HOLD\",\n" + 
            "         \"ReleaseDate\": \"2020-06-08T10:16:38.101+00:00\",\n" + 
            "         \"HeldBy\": \"ERP_INTEGRATION_USER\",\n" + 
            "         \"CreatedBy\": \"ERP_INTEGRATION_USER\",\n" + 
            "         \"CreationDate\": \"2020-05-26T07:45:26.002+00:00\",\n" + 
            "         \"LastUpdateDate\": \"2020-06-08T10:16:38.101+00:00\",\n" + 
            "         \"LastUpdatedBy\": \"ERP_INTEGRATION_USER\",\n" + 
            "         \"LastUpdateLogin\": \"A26C794599A0DA0DE053867F6B0A6AC9\",\n" + 
            "         \"PurchaseOrderNumber\": null,\n" + 
            "         \"PurchaseOrderLineNumber\": null,\n" + 
            "         \"PurchaseOrderScheduleLineNumber\": null,\n" + 
            "         \"ReceiptNumber\": null,\n" + 
            "         \"ReceiptLineNumber\": null,\n" + 
            "         \"ConsumptionAdviceNumber\": null,\n" + 
            "         \"ConsumptionAdviceLineNumber\": null,\n" + 
            "         \"links\":          [\n" + 
            "                        {\n" + 
            "               \"rel\": \"self\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033093068\",\n" + 
            "               \"name\": \"invoiceHolds\",\n" + 
            "               \"kind\": \"item\",\n" + 
            "               \"properties\": {\"changeIndicator\": \"ACED0005737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A65787000000001770400000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000278\"}\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"canonical\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033093068\",\n" + 
            "               \"name\": \"invoiceHolds\",\n" + 
            "               \"kind\": \"item\"\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"lov\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033093068/lov/HoldNameLookupVVO1\",\n" + 
            "               \"name\": \"HoldNameLookupVVO1\",\n" + 
            "               \"kind\": \"collection\"\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"lov\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033093068/lov/ReleaseNameVVO1\",\n" + 
            "               \"name\": \"ReleaseNameVVO1\",\n" + 
            "               \"kind\": \"collection\"\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"child\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033093068/child/invoiceHoldDff\",\n" + 
            "               \"name\": \"invoiceHoldDff\",\n" + 
            "               \"kind\": \"collection\"\n" + 
            "            }\n" + 
            "         ]\n" + 
            "      },\n" + 
            "            {\n" + 
            "         \"HoldId\": 300000033132057,\n" + 
            "         \"InvoiceNumber\": \"BPS2414\",\n" + 
            "         \"BusinessUnit\": \"N Brown Group\",\n" + 
            "         \"Supplier\": \"USA SUPPLIER-TEST\",\n" + 
            "         \"LineHeld\": null,\n" + 
            "         \"HoldDate\": \"2020-06-11T09:52:33.002+00:00\",\n" + 
            "         \"HoldDetails\": null,\n" + 
            "         \"HoldReason\": \"Hold applied on invoices interface to Bank System.\",\n" + 
            "         \"ReleaseReason\": null,\n" + 
            "         \"WorkflowStatus\": null,\n" + 
            "         \"ReleaseName\": null,\n" + 
            "         \"HoldName\": \"SUPPLIER_FINANCE_HOLD\",\n" + 
            "         \"ReleaseDate\": null,\n" + 
            "         \"HeldBy\": \"ERP_INTEGRATION_USER\",\n" + 
            "         \"CreatedBy\": \"ERP_INTEGRATION_USER\",\n" + 
            "         \"CreationDate\": \"2020-06-11T09:52:33.002+00:00\",\n" + 
            "         \"LastUpdateDate\": \"2020-06-11T09:52:33.170+00:00\",\n" + 
            "         \"LastUpdatedBy\": \"ERP_INTEGRATION_USER\",\n" + 
            "         \"LastUpdateLogin\": \"9E5723E90024E87BE053867F6B0A72A0\",\n" + 
            "         \"PurchaseOrderNumber\": null,\n" + 
            "         \"PurchaseOrderLineNumber\": null,\n" + 
            "         \"PurchaseOrderScheduleLineNumber\": null,\n" + 
            "         \"ReceiptNumber\": null,\n" + 
            "         \"ReceiptLineNumber\": null,\n" + 
            "         \"ConsumptionAdviceNumber\": null,\n" + 
            "         \"ConsumptionAdviceLineNumber\": null,\n" + 
            "         \"links\":          [\n" + 
            "                        {\n" + 
            "               \"rel\": \"self\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033132057\",\n" + 
            "               \"name\": \"invoiceHolds\",\n" + 
            "               \"kind\": \"item\",\n" + 
            "               \"properties\": {\"changeIndicator\": \"ACED0005737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A65787000000001770400000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000178\"}\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"canonical\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033132057\",\n" + 
            "               \"name\": \"invoiceHolds\",\n" + 
            "               \"kind\": \"item\"\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"lov\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033132057/lov/HoldNameLookupVVO1\",\n" + 
            "               \"name\": \"HoldNameLookupVVO1\",\n" + 
            "               \"kind\": \"collection\"\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"lov\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033132057/lov/ReleaseNameVVO1\",\n" + 
            "               \"name\": \"ReleaseNameVVO1\",\n" + 
            "               \"kind\": \"collection\"\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"child\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033132057/child/invoiceHoldDff\",\n" + 
            "               \"name\": \"invoiceHoldDff\",\n" + 
            "               \"kind\": \"collection\"\n" + 
            "            }\n" + 
            "         ]\n" + 
            "      },\n" + 
            "            {\n" + 
            "         \"HoldId\": 300000033132056,\n" + 
            "         \"InvoiceNumber\": \"BPS2414\",\n" + 
            "         \"BusinessUnit\": \"N Brown Group\",\n" + 
            "         \"Supplier\": \"USA SUPPLIER-TEST\",\n" + 
            "         \"LineHeld\": null,\n" + 
            "         \"HoldDate\": \"2020-06-11T09:26:18.002+00:00\",\n" + 
            "         \"HoldDetails\": null,\n" + 
            "         \"HoldReason\": \"Hold applied on invoices interface to Bank System.\",\n" + 
            "         \"ReleaseReason\": \"Supplier finance Release Hold Reason\",\n" + 
            "         \"WorkflowStatus\": null,\n" + 
            "         \"ReleaseName\": \"SUPPLIER_FINANCE_RELEASE\",\n" + 
            "         \"HoldName\": \"SUPPLIER_FINANCE_HOLD\",\n" + 
            "         \"ReleaseDate\": \"2020-06-11T09:51:38.073+00:00\",\n" + 
            "         \"HeldBy\": \"ERP_INTEGRATION_USER\",\n" + 
            "         \"CreatedBy\": \"ERP_INTEGRATION_USER\",\n" + 
            "         \"CreationDate\": \"2020-06-11T09:26:18.002+00:00\",\n" + 
            "         \"LastUpdateDate\": \"2020-06-11T09:51:38.073+00:00\",\n" + 
            "         \"LastUpdatedBy\": \"ERP_INTEGRATION_USER\",\n" + 
            "         \"LastUpdateLogin\": \"9E57240F16C3E834E053867F6B0AE078\",\n" + 
            "         \"PurchaseOrderNumber\": null,\n" + 
            "         \"PurchaseOrderLineNumber\": null,\n" + 
            "         \"PurchaseOrderScheduleLineNumber\": null,\n" + 
            "         \"ReceiptNumber\": null,\n" + 
            "         \"ReceiptLineNumber\": null,\n" + 
            "         \"ConsumptionAdviceNumber\": null,\n" + 
            "         \"ConsumptionAdviceLineNumber\": null,\n" + 
            "         \"links\":          [\n" + 
            "                        {\n" + 
            "               \"rel\": \"self\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033132056\",\n" + 
            "               \"name\": \"invoiceHolds\",\n" + 
            "               \"kind\": \"item\",\n" + 
            "               \"properties\": {\"changeIndicator\": \"ACED0005737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A65787000000001770400000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000278\"}\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"canonical\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033132056\",\n" + 
            "               \"name\": \"invoiceHolds\",\n" + 
            "               \"kind\": \"item\"\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"lov\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033132056/lov/HoldNameLookupVVO1\",\n" + 
            "               \"name\": \"HoldNameLookupVVO1\",\n" + 
            "               \"kind\": \"collection\"\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"lov\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033132056/lov/ReleaseNameVVO1\",\n" + 
            "               \"name\": \"ReleaseNameVVO1\",\n" + 
            "               \"kind\": \"collection\"\n" + 
            "            },\n" + 
            "                        {\n" + 
            "               \"rel\": \"child\",\n" + 
            "               \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds/300000033132056/child/invoiceHoldDff\",\n" + 
            "               \"name\": \"invoiceHoldDff\",\n" + 
            "               \"kind\": \"collection\"\n" + 
            "            }\n" + 
            "         ]\n" + 
            "      }\n" + 
            "   ],\n" + 
            "   \"count\": 3,\n" + 
            "   \"hasMore\": false,\n" + 
            "   \"limit\": 25,\n" + 
            "   \"offset\": 0,\n" + 
            "   \"links\": [   {\n" + 
            "      \"rel\": \"self\",\n" + 
            "      \"href\": \"https://elix-dev1.fa.em3.oraclecloud.com:443/fscmRestApi/resources/11.13.18.05/invoiceHolds\",\n" + 
            "      \"name\": \"invoiceHolds\",\n" + 
            "      \"kind\": \"collection\"\n" + 
            "   }]\n" + 
            "}\n";    
                
            String result_2 = obj.json2xmlGeneric(result_op,"n0","InvoiceHoldDetails","http://www.w3.org/2001/XMLSchema");
            System.out.println("JSONtoXML:\n"+result_2);
            }
            catch(JSONException jex) 
            {
                System.out.println(jex.toString());
            }
    }
}