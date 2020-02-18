package me.silmoon.shopmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class ShopItemController {
    @Autowired
    private ShopItemRepo shopItemRepo;

    @GetMapping("/")
    public String getIndex(Model model) {
        return "index";
    }

    @GetMapping("/itemList")
    public ArrayList<ShopItem> listItems() {
        ArrayList<ShopItem> itemList = new ArrayList<ShopItem>();
        for (ShopItem shopItem : shopItemRepo.findAll())
            itemList.add(shopItem);
        return itemList;
    }

    @GetMapping("/items?title={itemName}")
    public String searchItem(Model model) {
        return "index";
    }

    @PostMapping("/addItems")
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
        DateFormat dFormat = new SimpleDateFormat("yyyy-mm-ddTHH:mm:ss:SSSZ");  //todo: format check
        newItem.setCreated_at(dFormat.format(now));
        newItem.setUpdated_at(dFormat.format(now));

        shopItemRepo.save(newItem);
        return "redirect:/";
    }

}
