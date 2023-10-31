package com.example.demo.handler;

import com.example.demo.model.CompanyInfo;
import com.example.demo.model.OrderModel;
import com.example.demo.model.ProductInfo;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Component;

import com.example.demo.model.PersonalInfo;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderHandler {
    @Autowired
    private final OrderRepository orderRepository;

    public OrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderModel init() {
        return new OrderModel();
    }

    public void addPersonalInfo(OrderModel orderModel, PersonalInfo personalInfo) {
        orderModel.setPersonalInfo(personalInfo);
    }

    public void addProductInfo(OrderModel orderModel, ProductInfo productInfo) {
        orderModel.setProductInfo(productInfo);
    }

    public void addCompanyInfo(OrderModel orderModel, CompanyInfo companyInfo) {
        orderModel.setCompanyInfo(companyInfo);
    }

    public String saveAll(OrderModel orderModel) {
        orderRepository.save(orderModel);

        return "success";
    }

    public String validatePersonalInfo(PersonalInfo personalInfo, MessageContext error) {
        String transitionValue = "success";

        if (personalInfo.getFirstname().equals("")) {
            error.addMessage(new MessageBuilder().
                    error() //
                    .source("firstname")
                    .defaultText("Введите имя!")
                    .build());

            transitionValue = "failure";
        }

        if (personalInfo.getLastname().equals("")) {
            error.addMessage(new MessageBuilder().
                    error()
                    .source("lastname")
                    .defaultText("Введите фамилию!")
                    .build());

            transitionValue = "failure";
        }

        if (personalInfo.getPhone().equals("")) {
            error.addMessage(new MessageBuilder().
                    error()
                    .source("phone")
                    .defaultText("Введите номер телефона!")
                    .build());

            transitionValue = "failure";
        }

        if (personalInfo.getEmail().equals("")) {
            error.addMessage(new MessageBuilder().
                    error()
                    .source("email")
                    .defaultText("Введите email!")
                    .build());

            transitionValue = "failure";
        }

        if (personalInfo.getAddress().equals("")) {
            error.addMessage(new MessageBuilder().
                    error()
                    .source("address")
                    .defaultText("Введите адрес!")
                    .build());

            transitionValue = "failure";
        }
        return transitionValue;
    }

    public String validateProductInfo(ProductInfo productInfo, MessageContext error) {
        String transitionValue = "success";

        List<String> products = new ArrayList<>();
        products.add("Гиря");
        products.add("Мяч");
        products.add("Телевизор");
        products.add("Компьютер");
        products.add("Гитара");
        products.add("Пианино");
        products.add("Кресло");
        products.add("Диван");
        products.add("Стол");
        products.add("Стул");


        if (productInfo.getProduct().equals("")) {
            error.addMessage(new MessageBuilder().
                    error()
                    .source("product")
                    .defaultText("Введите название продукта!") //
                    .build());

            transitionValue = "failure";
        } else if (!products.contains(productInfo.getProduct())) {
            error.addMessage(new MessageBuilder().
                    error()
                    .source("product")
                    .defaultText("Такого продукта нет в каталоге!") //
                    .build());

            transitionValue = "failure";
        }

        return transitionValue;
    }

    public String validateCompanyInfo(CompanyInfo companyInfo, MessageContext error) {
        String transitionValue = "success";

        List<String> companies = new ArrayList<>();
        companies.add("DPD");
        companies.add("Почта России");
        companies.add("FedEx");
        companies.add("Горизонт");
        companies.add("СДЭК");

        if (companyInfo.getCompany().equals("")) {
            error.addMessage(new MessageBuilder().
                    error()
                    .source("company")
                    .defaultText("Введите название компании!") //
                    .build());

            transitionValue = "failure";
        } else if (!companies.contains(companyInfo.getCompany())) {
            error.addMessage(new MessageBuilder().
                    error()
                    .source("company")
                    .defaultText("Такой компании нет в списке!") //
                    .build());

            transitionValue = "failure";
        }

        return transitionValue;
    }
}
