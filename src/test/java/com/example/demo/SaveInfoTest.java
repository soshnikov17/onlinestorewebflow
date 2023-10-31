package com.example.demo;

import com.example.demo.handler.OrderHandler;
import com.example.demo.model.CompanyInfo;
import com.example.demo.model.OrderModel;
import com.example.demo.model.PersonalInfo;
import com.example.demo.model.ProductInfo;
import com.example.demo.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaveInfoTest {
    private OrderHandler orderHandler;
    private OrderModel orderModel;
    private OrderRepository orderRepository;
    private ProductInfo productInfo;
    private PersonalInfo personalInfo;
    private CompanyInfo companyInfo;

    @Before
    public void setUp() {
        orderRepository = new OrderRepository();
        orderHandler = new OrderHandler(orderRepository);
        orderModel = orderHandler.init();
        productInfo = new ProductInfo();
        productInfo.setProduct("Часы");
        companyInfo = new CompanyInfo();
        companyInfo.setCompany("DPD");
        personalInfo = new PersonalInfo();
        personalInfo.setFirstname("Андрей");
    }

    @Test
    public void testAddProductInfo() {
        orderHandler.addProductInfo(orderModel, productInfo);
        Assert.assertEquals("Часы", orderModel.getProductInfo().getProduct());
    }

    @Test
    public void testAddPersonalInfo() {
        orderHandler.addPersonalInfo(orderModel, personalInfo);
        Assert.assertEquals("Андрей", orderModel.getPersonalInfo().getFirstname());
    }

    @Test
    public void testAddCompanyInfo() {
        orderHandler.addCompanyInfo(orderModel, companyInfo);
        Assert.assertEquals("DPD", orderModel.getCompanyInfo().getCompany());
    }

    @Test
    public void testSaveAll() {
        orderHandler.saveAll(orderModel);
        orderHandler.saveAll(orderModel);
        Assert.assertEquals(2, orderRepository.findAll().size());
    }
}
