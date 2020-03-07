package me.silmoon.shopmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Vector;

@RestController
public class ShopItemController {
    @Autowired
    private ShopItemRepo shopItemRepo;

    @Autowired
    FileService fileService;

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
    // e.g. http://localhost:8080/search?title=test
    @GetMapping("/search")
    public Vector<ShopItem> searchItem(@RequestParam("title") String itemTitle) {
        Vector<ShopItem> resultList = new Vector<ShopItem>();
        for (ShopItem shopItem : shopItemRepo.findAllByTitle(itemTitle))
            resultList.add(shopItem);
        return resultList;
    }

    @PostMapping("/deleteItem")
    public String deleteToDoItem(@RequestParam String targetId) {
        int tId = Integer.parseInt(targetId);
        Optional<ShopItem> targetItem = shopItemRepo.findById(tId);
        if (targetItem.isEmpty())
            return "not found";
        shopItemRepo.delete(targetItem.get());
        return "successful";
    }

    @PostMapping("/addItem")
    public String addNewItem(@RequestParam String title, @RequestParam String description, @RequestParam Double price,
                                           @RequestParam String item_image_url)
    {
        ShopItem newItem = new ShopItem();
        if (title.length()>100 || description.length()>500)
            return HttpStatus.BAD_REQUEST.toString();

        newItem.setTitle(title);
        newItem.setDescription(description);
        newItem.setPrice(price);
        newItem.setItem_image_url(item_image_url);
        Date now = new Date();
        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newItem.setCreated_at(dFormat.format(now));
        newItem.setUpdated_at(dFormat.format(now));

        shopItemRepo.save(newItem);
        return "successful";
    }

    // e.g. http://localhost:8080/editItem?id=3
    @PostMapping("/editItem")
    public String editItem(@RequestParam("id") String strId, @RequestParam String title,
                           @RequestParam String description, @RequestParam Double price, @RequestParam String item_image_url)
    {
        int itemId = Integer.parseInt(strId);
        if (shopItemRepo.findById(itemId).isEmpty())
            return "HttpStatus.NOT_FOUND.toString()";

        ShopItem editItem = shopItemRepo.findById(itemId).get();

        if (title.length()>100 || description.length()>500)
            return HttpStatus.BAD_REQUEST.toString();

        editItem.setTitle(title);
        editItem.setDescription(description);
        editItem.setPrice(price);
        editItem.setItem_image_url(item_image_url);
        Date now = new Date();
        DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        editItem.setUpdated_at(dFormat.format(now));

        shopItemRepo.save(editItem);
        return "successful";
    }

    @PostMapping("/uploadImage")
    public String uploadFile(@RequestParam("image") MultipartFile image) {

        if (image.isEmpty())
            return "File not exists";
        else if (!Objects.requireNonNull(image.getContentType()).equalsIgnoreCase("image/jpeg"))
            return "Invalid file";
        Date now = new Date();
        DateFormat dFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = dFormat.format(now);
        fileService.uploadFile(image, timestamp);

        String originalName = image.getOriginalFilename();
        String fileExt = originalName.substring(originalName.lastIndexOf("."));
        return timestamp+fileExt;
    }
}
