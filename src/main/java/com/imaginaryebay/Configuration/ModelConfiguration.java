package com.imaginaryebay.Configuration;

import com.imaginaryebay.Controller.*;
import com.imaginaryebay.DAO.*;
import com.imaginaryebay.Repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ben_Big on 6/27/16.
 */

@Configuration
public class ModelConfiguration {



    @Bean
    public UserrController userrController(){
        UserrController bean=new UserrControllerImpl();
        return bean;
    }



    @Bean
    public MessageDao messageDao(){
        MessageDaoImpl bean = new MessageDaoImpl();
        return bean;
    }


    @Bean
    public MessageRepository messageRepository(){
        MessageRepositoryImpl bean=new MessageRepositoryImpl();
        bean.setMessageDao(messageDao());
        return bean;
    }

    @Bean
    //public MessageRestController messageRestController(){
    public MessageController messageRestController(){
            MessageRestController bean=new MessageRestController();
        bean.setMessageRepository(messageRepository());
        return bean;
    }

    @Bean
    public ItemDAO itemDAO() {
        ItemDAOImpl bean = new ItemDAOImpl();
        return bean;
    }

    @Bean
    public ItemRepository itemRepository(){
        ItemRepositoryImpl bean = new ItemRepositoryImpl();
        bean.setItemDAO(itemDAO());
        return bean;
    }

    @Bean
    public ItemPictureDAO itemPictureDAO() {
        ItemPictureDAOImpl bean = new ItemPictureDAOImpl();
        return bean;
    }

    @Bean
    public ItemController itemService() {
        ItemControllerImpl bean = new ItemControllerImpl();
        bean.setItemRepository(itemRepository());
        bean.setItemPictureDAO(itemPictureDAO());
        return bean;
    }



    //FrontPageController has to be in ModelConfiguration
    @Bean
    public FrontPageController frontPageController(){
        FrontPageController bean=new FrontPageControllerImpl();
        return bean;
    }
}
