package com.akgarg.apnagurukul.controllers.book;

import com.akgarg.apnagurukul.model.BuyBookRequest;
import com.akgarg.apnagurukul.model.ResponseMessage;
import com.akgarg.apnagurukul.service.BuyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Akhilesh Garg
 * GitHub: https://github.com/akgarg0472
 */

@Controller
public class BuyBookController {

    private final BuyBookService buyBookService;

    @Autowired
    public BuyBookController(BuyBookService buyBookService) {
        this.buyBookService = buyBookService;
    }

    @RequestMapping(value = "/process-buy-book-req", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage processBuyBookRequest(@RequestParam("id") String id,
                                                 @ModelAttribute BuyBookRequest buyBookRequest) {
        return this.buyBookService.processBuyBook(id, buyBookRequest);
    }
}
