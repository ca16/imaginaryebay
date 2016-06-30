package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.ItemPicture;

/**
 * Created by Brian on 6/29/2016.
 */
public interface ItemPictureDAO {
    public void merge(ItemPicture itemPicture);

    public void persist(ItemPicture itemPicture);

    public void refresh(ItemPicture itemPicture);
}
