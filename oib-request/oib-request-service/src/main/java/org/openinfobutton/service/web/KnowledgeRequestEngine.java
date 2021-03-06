package org.openinfobutton.service.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import org.hl7.v3.AggregateKnowledgeResponse;
import org.openinfobutton.schema.KnowledgeRequest;
import org.openinfobutton.service.matching.AccessCheckHandler;
import org.openinfobutton.service.matching.ContextProfileHandler;
import org.openinfobutton.service.matching.RequestResult;
import org.openinfobutton.service.matching.TaskCheckHandler;
import org.openinfobutton.service.response.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class KnowledgeRequestEngine {

	@Autowired
	ResponseGenerator rg;
	/**
	 * 
	 * @param knowledgeRequest built from the input request parameters
	 * @return Document which contains the aggregateKnowledgeResponse after processing the infobutton request
	 */
	public AggregateKnowledgeResponse getResponse(KnowledgeRequest knowledgeRequest) {
		List<RequestResult> result = returnResult(knowledgeRequest);
		Collections.sort(result);
		AggregateKnowledgeResponse responseType = new AggregateKnowledgeResponse();
		try {
			if (!result.isEmpty()) 
				responseType = rg.returnResponse(knowledgeRequest, result);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseType;
	}

	/**
	 * Based on the knowledge Request, access and task checks are done.
	 * Then based on the qualified resource profiles, matching is done to produce the results.
	 * @param request
	 * @return List of Results
	 */
	private List<RequestResult> returnResult(KnowledgeRequest request) {
		
		List<RequestResult> result = new ArrayList<RequestResult>();
		if (AccessCheckHandler.handleRequest(request)) {
			return result;
		} else if (TaskCheckHandler.handleRequest(request)) {
			return result;
		} else {
			ContextProfileHandler contextProfileHandlerObject = new ContextProfileHandler();
			result = contextProfileHandlerObject.handleRequest(request);
			return result;
		}	
	}
}
