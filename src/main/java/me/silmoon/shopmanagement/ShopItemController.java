package me.silmoon.shopmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

@RestController
public class ShopItemController {
    @Autowired
    private ShopItemRepo shopItemRepo;

    @GetMapping("/")
    public ModelAndView getIndex(Model model) {
        return new ModelAndView("index");
    }

    @GetMapping("/itemList")
    public Vector<ShopItem> listItems() {
        Vector<ShopItem> itemList = new Vector<ShopItem>();
        for (ShopItem shopItem : shopItemRepo.findAll())
            itemList.add(shopItem);
        return itemList;
    }

    @GetMapping("/search?title={itemTitle}")
    public Vector<ShopItem> searchItem(@RequestParam("itemTitle") String itemTitle) {
        Vector<ShopItem> resultList = new Vector<ShopItem>();
        for (ShopItem shopItem : shopItemRepo.findAllByTitle(itemTitle))
            resultList.add(shopItem);
        return resultList;
    }

    @PostMapping("/addItem")
    public String addNewItem(@RequestParam String title, @RequestParam String description, @RequestParam Integer price,
                                           @RequestParam String item_image_url)
    {
        ShopItem newItem = new ShopItem();
        if (title.length()>100 || description.length()>500)
            return HttpStatus.NOT_FOUND.toString();

        newItem.setTitle(title);
        newItem.setDescription(description);
        newItem.setPrice(price);
        newItem.setItem_image_url(item_image_url);
        Date now = new Date();
        DateFormat dFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        newItem.setCreated_at(dFormat.format(now));
        newItem.setUpdated_at(dFormat.format(now));

        shopItemRepo.save(newItem);
        return "redirect:/";
    }

    @PutMapping("/editItem?id={itemId}")
    public String editItem(@RequestParam("itemId") Integer itemId, @RequestParam String title,
                           @RequestParam String description, @RequestParam Integer price, @RequestParam String item_image_url)
    {
        if (shopItemRepo.findById(itemId).isEmpty())
            return HttpStatus.NOT_FOUND.toString();

        ShopItem editItem = shopItemRepo.findById(itemId).get();

        if (title.length()>100 || description.length()>500)
            return HttpStatus.NOT_FOUND.toString();

        editItem.setTitle(title);
        editItem.setDescription(description);
        editItem.setPrice(price);
        editItem.setItem_image_url(item_image_url);
        Date now = new Date();
        DateFormat dFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        editItem.setCreated_at(dFormat.format(now));
        editItem.setUpdated_at(dFormat.format(now));

        shopItemRepo.save(editItem);
        return "redirect:/";
    }

}
