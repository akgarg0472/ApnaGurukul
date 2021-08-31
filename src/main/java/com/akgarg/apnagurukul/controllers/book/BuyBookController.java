package com.akgarg.apnagurukul.controllers.book;

import com.akgarg.apnagurukul.model.BuyBookRequest;
import com.akgarg.apnagurukul.model.ResponseMessage;
import com.akgarg.apnagurukul.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Author: Akhilesh Garg
 * GitHub: https://github.com/akgarg0472
 */

@Controller
public class BuyBookController {

    private final BookService bookService;

    @Autowired
    public BuyBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/process-buy-book-req", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage processBuyBookRequest(Principal principal,
                                                 @RequestParam("id") String id,
                                                 @ModelAttribute BuyBookRequest buyBookRequest) {
        return this.bookService.processBuyBook(principal, id, buyBookRequest);
    }
}
