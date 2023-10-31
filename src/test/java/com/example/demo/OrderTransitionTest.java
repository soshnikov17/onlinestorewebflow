package com.example.demo;

import com.example.demo.handler.OrderHandler;
import com.example.demo.model.CompanyInfo;
import com.example.demo.model.OrderModel;
import com.example.demo.model.PersonalInfo;
import com.example.demo.model.ProductInfo;
import com.example.demo.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTransitionTest extends AbstractXmlFlowExecutionTests {
    private OrderModel orderModel;
    private OrderHandler orderHandler;
    private OrderRepository orderRepository;
    private ProductInfo productInfo;
    private PersonalInfo personalInfo;
    private CompanyInfo companyInfo;

    @Override
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
        return resourceFactory.createFileResource("src/main/resources/flows/order/test/flow-test.xml");
    }

    @Override
    protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext) {
        builderContext.registerBean("orderModel", orderModel);
        builderContext.registerBean("orderHandler", orderHandler);
    }

    @Before
    public void setUp(){
        orderRepository = new OrderRepository();
        orderHandler = new OrderHandler(orderRepository);
        orderModel = orderHandler.init();
        productInfo = new ProductInfo();
        personalInfo = new PersonalInfo();
        companyInfo = new CompanyInfo();
    }

    @Test
    public void testStartOrderFlow() {
        MutableAttributeMap input = new LocalAttributeMap();
        MockExternalContext context = new MockExternalContext();
        startFlow(input, context);

        assertCurrentStateEquals("product");
    }

    @Test
    public void testEnterProductInfo() {
        setCurrentState("product");

        ProductInfo productInfo = new ProductInfo();
        getFlowScope().put("orderModel", orderModel);
        getFlowScope().put("productInfo", productInfo);
        MockExternalContext context = new MockExternalContext();
        context.setEventId("next");
        resumeFlow(context);

        assertCurrentStateEquals("personal");
    }

    @Test
    public void testEnterPersonalInfo() {
        setCurrentState("personal");

        PersonalInfo personalInfo = new PersonalInfo();
        getFlowScope().put("orderModel", orderModel);
        getFlowScope().put("personalInfo", personalInfo);
        MockExternalContext context = new MockExternalContext();
        context.setEventId("next");
        resumeFlow(context);

        assertCurrentStateEquals("company");
    }

    @Test
    public void testEnterCompanyInfo() {
        setCurrentState("company");

        CompanyInfo companyInfo = new CompanyInfo();
        getFlowScope().put("orderModel", orderModel);
        getFlowScope().put("companyInfo", companyInfo);
        MockExternalContext context = new MockExternalContext();
        context.setEventId("next");
        resumeFlow(context);

        assertCurrentStateEquals("confirm");
    }

    @Test
    public void testOrderIntegration() {
        //start state = product
        MockExternalContext context = new MockExternalContext();
        startFlow(context);

        assertCurrentStateEquals("product");

        //transition to personal
        getFlowScope().put("orderModel", orderModel);
        getFlowScope().put("productInfo", productInfo);
        context.setEventId("next");
        resumeFlow(context);

        assertCurrentStateEquals("personal");

        //transition to company
        getFlowScope().put("orderModel", orderModel);
        getFlowScope().put("personalInfo", personalInfo);
        context.setEventId("next");
        resumeFlow(context);

        assertCurrentStateEquals("company");

        //transition to confirm
        getFlowScope().put("orderModel", orderModel);
        getFlowScope().put("companyInfo", companyInfo);
        context.setEventId("next");
        resumeFlow(context);

        assertCurrentStateEquals("confirm");
    }
}

