package me.silmoon.shopmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ShopItemController {
    @Autowired
    private ShopItemRepo shopItemRepo;

    @GetMapping("/")
    public String getIndex(Model model) {
        return "index";
    }

    @GetMapping("/v1/items?title={itemName}")
    public String searchItem(Model model) {
        return "index";
    }

    @PostMapping("/api/v1/items")
    public @ResponseBody String addNewItem(@RequestParam String title, @RequestParam String description, @RequestParam Integer price,
                                           @RequestParam String item_image_url, @RequestParam Date created_at)
    {
        ShopItem newItem = new ShopItem();
        if (title.length()>100 || description.length()>500)
            return "";  //todo: return an error code

        newItem.setTitle(title);
        newItem.setDescription(description);
        Date now = new Date();
        DateFormat dFormat = new SimpleDateFormat("yyyy-mm-ddTHH:mm:ss:SSSZ");  //todo: format check
        newItem.setCreated_at(dFormat.format(now));
        return "";
    }

}
