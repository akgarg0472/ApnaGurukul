package com.akgarg.apnagurukul.controllers.book;

import com.akgarg.apnagurukul.entity.SellBookAd;
import com.akgarg.apnagurukul.model.ResponseMessage;
import com.akgarg.apnagurukul.service.SellBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Author: Akhilesh Garg
 * GitHub: https://github.com/akgarg0472
 */

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@Controller
public class SellBookController {

    private final SellBookService sellBookService;

    @Autowired
    public SellBookController(SellBookService sellBookService) {
        this.sellBookService = sellBookService;
    }


    @RequestMapping(value = "/process-sell-book", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage processSellBook(Principal principal,
                                           @ModelAttribute SellBookAd sellBookAd,
                                           @RequestParam("image") String image) {
        return this.sellBookService.processBookSell(principal, sellBookAd, image);
    }
}