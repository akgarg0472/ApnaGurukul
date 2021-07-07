package com.akgarg.apnagurukul.controllers.book;

import com.akgarg.apnagurukul.entity.SellBookAd;
import com.akgarg.apnagurukul.helper.EmailMessages;
import com.akgarg.apnagurukul.helper.EmailSender;
import com.akgarg.apnagurukul.model.BuyBookRequest;
import com.akgarg.apnagurukul.model.ResponseMessage;
import com.akgarg.apnagurukul.repository.SellBookAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Author: Akhilesh Garg
 * GitHub: https://github.com/akgarg0472
 */

@Controller
public class BuyBookController {

    private final SellBookAdRepository sellBookAdRepository;

    @Autowired
    public BuyBookController(SellBookAdRepository sellBookAdRepository) {
        this.sellBookAdRepository = sellBookAdRepository;
    }


    @RequestMapping(value = "/process-buy-book-req", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage processBuyBookRequest(@RequestParam("id") String id,
                                                 @ModelAttribute BuyBookRequest buyBookRequest) {
        Optional<SellBookAd> byId = this.sellBookAdRepository.findById(Integer.parseInt(id));
        SellBookAd sellBookAd = byId.orElse(null);

        if (sellBookAd == null) {
            return new ResponseMessage("Something wrong happen, please try again later", "high");
        }

        if (sellBookAd.getSellerEmail().equals(buyBookRequest.getEmail())) {
            return new ResponseMessage("Sorry, You can't purchase your own book.", "high");
        }

        EmailSender.sendEmail(sellBookAd.getSellerEmail(), "Update on your book sell Id#" + id,
                EmailMessages.sellBookQueryMessage(sellBookAd.getSellerName(),
                        sellBookAd.getBookTitle(),
                        sellBookAd.getId(),
                        buyBookRequest));

        return new ResponseMessage("", "none");
    }
}
