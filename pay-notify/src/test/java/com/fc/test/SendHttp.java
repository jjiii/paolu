package com.fc.test;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;




public class SendHttp {
//  public static void main(String[] args) {
//	ParallelClient pc = new ParallelClient();
//    pc.prepareHttpGet("/pay-web-gateway/test")
//            .setConcurrency(1000)
//            .setTargetHostsFromString("test.carisok.com")
////            .setHttpPollable(true)
//            .setHttpPort(8082)
//            .execute(new ParallecResponseHandler() {
//                public void onCompleted(ResponseOnSingleTask res,
//                        Map<String, Object> responseContext) {
//                    System.err.println(res.getResponseContent());
//                    System.err.println(res.getStatusCode());
//                }
//            });
//    pc.releaseExternalResources();
//	
//	ParallelClient pc = new ParallelClient();
//	
//	ParallelTaskBuilder pb  = pc.prepareHttpGet("/pay-shop/test")
//	.setTargetHostsFromString("localhost")
//	.setHttpPort(8081);
//	
//	pb.execute(new ParallecResponseHandler() {
//	    public void onCompleted(ResponseOnSingleTask res,
//	        Map<String, Object> responseContext) {
//	    	System.err.println(res.getResponseContent());
//	        System.err.println( res.toString() );  }
//	});
//}
	
	public static void main(String[] args) throws Exception {
//		CloseableHttpClient client = HttpClients.createDefault();
//	    HttpPost httpPost = new HttpPost("http://localhost:8082/pay-gateway/receive/test");
//	 
//	    String json = "{\"a\":6}";
	    String json = "{\"id\":1,\"name\":\"John\"}";
//	    
//	    StringEntity entity = new StringEntity(json);
//	    httpPost.setEntity(entity);
//	    httpPost.setHeader("Accept", "application/json");
//	    httpPost.setHeader("Content-type", "application/json");
//	 
//	    CloseableHttpResponse response = client.execute(httpPost);
//	    System.out.println(response.getStatusLine().getStatusCode());;
//	    client.close();
		
//		HttpResponse response = 
//			      Request.Post("http://localhost:8082/pay-gateway/receive/test").bodyForm(
//			        Form.form().add("username", "John").build())
//			        .execute().returnResponse();
//		System.out.println(response.getStatusLine().getStatusCode());
	    
//	    String s = Request.Post("http://localhost:8082/pay-gateway/receive/test")
//	    			.bodyString(json, ContentType.APPLICATION_JSON)
//	    			.execute()
//	    			.returnContent()
//	    			.asString();
	    
	    Form from = Form.form().add("sstore_ids", "2,3,4,5,6,325").add("password", "secret");
	    Form.form();
	    
	    String s = Request.Post("https://test.carisok.com/ssms-services/api/ssms-open/service/sales/sstore/getBySstroeIds")
    			.bodyForm(from.build())
    			.execute()
    			.returnContent()
    			.asString();
	    System.out.println(s);

	}
}
