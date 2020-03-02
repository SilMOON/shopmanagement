package me.silmoon.shopmanagement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Vector;


@Transactional
@Repository
public interface ShopItemRepo extends CrudRepository<ShopItem, Integer> {
    @Query("from ShopItem where title like CONCAT('%',:title,'%')")
    public Iterable<ShopItem> findAllByTitle(String title);

}
