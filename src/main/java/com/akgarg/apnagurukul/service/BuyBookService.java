package com.akgarg.apnagurukul.service;


import com.akgarg.apnagurukul.entity.SellBookAd;
import com.akgarg.apnagurukul.helper.EmailMessages;
import com.akgarg.apnagurukul.helper.EmailSender;
import com.akgarg.apnagurukul.model.BuyBookRequest;
import com.akgarg.apnagurukul.model.ResponseMessage;
import com.akgarg.apnagurukul.repository.SellBookAdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuyBookService {

    private final SellBookAdRepository sellBookAdRepository;

    @Autowired
    public BuyBookService(SellBookAdRepository sellBookAdRepository) {
        this.sellBookAdRepository = sellBookAdRepository;
    }

    public ResponseMessage processBuyBook(String id, BuyBookRequest buyBookRequest) {
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
