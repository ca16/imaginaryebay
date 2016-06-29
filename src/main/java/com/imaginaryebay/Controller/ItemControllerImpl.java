package com.imaginaryebay.Controller;

import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.Models.Category;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Repository.ItemRepository;
import com.imaginaryebay.Repository.UserrRepository;

import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Chloe on 6/23/16.
 */
@Transactional
public class ItemControllerImpl implements ItemController {

    private ItemRepository itemRepository;
    public void setItemRepository(ItemRepository itemRepository){
        this.itemRepository=itemRepository;
    }

    @Override
    public void save(Item item) {
        this.itemRepository.save(item);
    }

    @Override
    public Item getItemByID(Long id){
        return this.itemRepository.findByID(id);
    }

    @Override
    public Double getPriceByID(Long id){
        return this.itemRepository.findPriceByID(id);
    }

    @Override
    public Category getCategoryByID(Long id){
        return this.itemRepository.findCategoryByID(id);
    }

    @Override
    public String getDescriptionByID(Long id){
        return this.itemRepository.findDescriptionByID(id);
    }

    @Override
    public Timestamp getEndtimeByID(Long id){
        return this.itemRepository.findEndtimeByID(id);
    }

    @Override
    public Item updateItemByID(Long id, Item item){
        return this.itemRepository.updateItemByID(id, item);
    }

    @Override
    public List<Item> getAllItems(Category cat){
        if (null != cat){
            return this.itemRepository.findAllItemsByCategory(cat);
        }
        return this.itemRepository.findAllItems();
    }
}