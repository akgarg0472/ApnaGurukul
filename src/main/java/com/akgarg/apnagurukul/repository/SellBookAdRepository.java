package com.akgarg.apnagurukul.repository;

import com.akgarg.apnagurukul.entity.SellBookAd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: Akhilesh Garg
 * GitHub: https://github.com/akgarg0472
 */

public interface SellBookAdRepository extends JpaRepository<SellBookAd, Integer> {

    List<SellBookAd> getAllBySellerEmail(String sellerEmail);
}
