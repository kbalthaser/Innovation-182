package org.openinfobutton.responder.controller;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openinfobutton.app.model.Asset;
import org.openinfobutton.app.model.AssetProperty;
import org.openinfobutton.responder.service.ResponderService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *
 * @author rick
 */
public class OpenInfobuttonResponderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ResponderService responderService;

    @InjectMocks
    private OpenInfobuttonResponderController openInfobuttonResponderController;



    @Before
    public void setup() {

        // Process mock annotations
        MockitoAnnotations.initMocks(this);

        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(openInfobuttonResponderController).build();
    }



    @Test
    public void testOpenInfobuttonRequestHandlerGetIndexPage() throws Exception {

        this.mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(forwardedUrl(OpenInfobuttonResponderController.INDEX_PAGE));

    }



    @Test
    public void testOpenInfobuttonRequestHandlerReturnsAtomPage() throws Exception {

        when(responderService.getKnowledgeRequestParameterMap(any(Map.class))).thenReturn(
                getValidMockFlatRequestParameters());

        this.mockMvc.perform(post("/responder")).andExpect(status().isOk())
                .andExpect(forwardedUrl(OpenInfobuttonResponderController.ATOM_PAGE));

    }



    @Test
    public void testOpenInfobuttonRequestHandlerReturnsNoCache() throws Exception {

        when(responderService.getKnowledgeRequestParameterMap(any(Map.class))).thenReturn(
                getValidMockFlatRequestParameters());

        this.mockMvc.perform(post("/responder")).andExpect(status().isOk())
                .andExpect(header().string("Cache-Control", "no-cache"));

    }



    @Test
    public void testOpenInfobuttonRequestHandlerReturnsHtmlPage() throws Exception {

        when(responderService.getKnowledgeRequestParameterMap(any(Map.class))).thenReturn(
                getValidMockFlatRequestParametersWithHtmlParameter());

        this.mockMvc.perform(post("/responder")).andExpect(status().isOk())
                .andExpect(forwardedUrl(OpenInfobuttonResponderController.HTML_PAGE));

    }



    @Test
    public void testOpenInfobuttonRequestHandleHttpMediaTypeNotSupportedException()
            throws Exception {

        // should cause HttpMediaTypeNotSupportedException
        when(responderService.getKnowledgeRequestParameterMap(any(Map.class))).thenReturn(
                getInValidMockFlatRequestKnowledgeResponseTypeParameter());

        this.mockMvc.perform(post("/responder")).andExpect(status().isUnsupportedMediaType());

    }



    @Test
    public void testOpenInfobuttonRequestHandleMissingServletRequestParameterException()
            throws Exception {

        when(responderService.getKnowledgeRequestParameterMap(any(Map.class))).thenThrow(
                new MissingServletRequestParameterException("testParm", "testType"));

        this.mockMvc.perform(post("/responder")).andExpect(status().isBadRequest());

    }



    @Test
    public void testOibRequestHandleIllegalArgumentException() throws Exception {

        when(responderService.getKnowledgeRequestParameterMap(any(Map.class))).thenThrow(
                new IllegalArgumentException("Testing IllegalArgumentException"));

        this.mockMvc.perform(post("/responder")).andExpect(status().isBadRequest());

    }



    private Map<String, Map<String, String>> getValidMockIndexPropertyMap() {

        Map<String, Map<String, String>> indexPropertyMap = new HashMap<String, Map<String, String>>();

        Map<String, String> p1 = new HashMap<String, String>();
        p1.put("CODE", "mainSearchCriteria.v.c");
        p1.put("CODE_SYSTEM", "mainSearchCriteria.v.cs");
        p1.put("DISPLY_NAME", "mainSearchCriteria.v.dn");
        indexPropertyMap.put("mainSearchCriteria.v", p1);

        Map<String, String> p2 = new HashMap<String, String>();
        p2.put("CODE", "taskContext.c.c");
        p2.put("CODE_SYSTEM", "taskContext.c.cs");
        indexPropertyMap.put("taskContext.c", p2);

        return indexPropertyMap;
    }



    private Map<String, String> getInValidMockFlatRequestKnowledgeResponseTypeParameter() {

        Map<String, String> requestParameters = getValidMockFlatRequestParameters();
        requestParameters.put("knowledgeResponseType", "bogus-mime-type");

        return requestParameters;
    }



    private Map<String, String> getValidMockFlatRequestParametersWithHtmlParameter() {

        Map<String, String> requestParameters = getValidMockFlatRequestParameters();
        requestParameters.put("knowledgeResponseType", "text/html");

        return requestParameters;
    }



    private Map<String, String> getValidMockFlatRequestParameters() {

        Map<String, String> requestParameters = new HashMap<String, String>();
        requestParameters.put("mainSearchCriteria.v.c", "47505003");
        requestParameters.put("mainSearchCriteria.v.cs", "2.16.840.1.113883.6.96");
        requestParameters.put("taskContext.c.c", "PROBLISTREV");

        return requestParameters;
    }



    private List<Asset> getValidMockAssets() {

        List<Asset> assets = new ArrayList<Asset>();

        Asset asset = new Asset();
        asset.setAssetId(BigDecimal.ZERO);
        asset.setDisplayName("Test asset");
        asset.setNamespaceCd("TEST");
        asset.setAssetUrl("http://test.org/TEST");

        List<AssetProperty> assetProperties = new ArrayList<AssetProperty>();
        AssetProperty ap1 = new AssetProperty();
        ap1.setAssetPropertyId(BigDecimal.ZERO);
        ap1.setAsset(asset);
        ap1.setPropertyType("CODE");
        ap1.setPropertyName("mainSearchCriteria.v");
        ap1.setCode("47505003");
        ap1.setCodeSystem("2.16.840.1.113883.6.96");

        AssetProperty ap2 = new AssetProperty();
        ap2.setAssetPropertyId(BigDecimal.ONE);
        ap2.setAsset(asset);
        ap2.setPropertyType("CODE");
        ap2.setPropertyName("taskContext.c");
        ap2.setCode("PROBLISTREV");
        ap2.setCodeSystem("2.16.840.1.113883.5.4");

        assetProperties.add(ap1);
        assetProperties.add(ap2);
        asset.setAssetProperties(assetProperties);

        assets.add(asset);

        return assets;

    }



    private Properties getMockAtomFeedProperties() {

        Properties atomFeedMetadata = new Properties();
        atomFeedMetadata.put("id.urn", "urn:oib.test.org:responder");
        atomFeedMetadata.put("entry.id.urnPrefix", "urn:oib.test.org:0");
        atomFeedMetadata.put("title", "TestAomFeedTitle");
        atomFeedMetadata.put("author.name", "TestAuthorName");
        atomFeedMetadata.put("author.uri", "http://oib.test.org/author.uri");
        atomFeedMetadata.put("xml.base", "http://oib.test.org/xml.base");

        return atomFeedMetadata;

    }
}