package com.akgarg.apnagurukul.entity;

import com.akgarg.apnagurukul.helper.DateAndTimeMethods;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Author: Akhilesh Garg
 * GitHub: https://github.com/akgarg0472
 */

@SuppressWarnings("unused")
@Entity
public class SellBookAd {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private String bookClass;
    private String bookStream;
    private String additionalBookInfo;
    private String bookQuality;
    private boolean donateBoook;
    private int originalPrice;
    private int sellingPrice;
    private String bookPhoto;
    private boolean isBookSold;

    private String sellerName;
    private String sellerCity;
    private String sellerState;
    private String sellerCountry;
    private String sellerPincode;

    private String publishingDate;
    private String sellDate;

    public SellBookAd() {
        this.isBookSold = false;
        this.publishingDate = DateAndTimeMethods.getCurrentDate();
    }


    public SellBookAd(String bookTitle,
                      String bookAuthor,
                      String bookPublisher,
                      String bookClass,
                      String bookStream,
                      String additionalBookInfo,
                      String bookQuality,
                      boolean donateBoook,
                      int originalPrice,
                      int sellingPrice,
                      String bookPhoto,
                      String sellerName,
                      String sellerCity,
                      String sellerState,
                      String sellerCountry,
                      String sellerPincode) {
        this();
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookClass = bookClass;
        this.bookStream = bookStream;
        this.additionalBookInfo = additionalBookInfo;
        this.bookQuality = bookQuality;
        this.donateBoook = donateBoook;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.bookPhoto = bookPhoto;
        this.sellerName = sellerName;
        this.sellerCity = sellerCity;
        this.sellerState = sellerState;
        this.sellerCountry = sellerCountry;
        this.sellerPincode = sellerPincode;
        this.isBookSold = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookClass() {
        return bookClass;
    }

    public void setBookClass(String bookClass) {
        this.bookClass = bookClass;
    }

    public String getBookStream() {
        return bookStream;
    }

    public void setBookStream(String bookStream) {
        this.bookStream = bookStream;
    }

    public String getAdditionalBookInfo() {
        return additionalBookInfo;
    }

    public void setAdditionalBookInfo(String additionalBookInfo) {
        this.additionalBookInfo = additionalBookInfo;
    }

    public String getBookQuality() {
        return bookQuality;
    }

    public void setBookQuality(String bookQuality) {
        this.bookQuality = bookQuality;
    }

    public boolean isDonateBoook() {
        return donateBoook;
    }

    public void setDonateBoook(boolean donateBoook) {
        this.donateBoook = donateBoook;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getBookPhoto() {
        return bookPhoto;
    }

    public void setBookPhoto(String bookPhoto) {
        this.bookPhoto = bookPhoto;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerCity() {
        return sellerCity;
    }

    public void setSellerCity(String sellerCity) {
        this.sellerCity = sellerCity;
    }

    public String getSellerState() {
        return sellerState;
    }

    public void setSellerState(String sellerState) {
        this.sellerState = sellerState;
    }

    public String getSellerCountry() {
        return sellerCountry;
    }

    public void setSellerCountry(String sellerCountry) {
        this.sellerCountry = sellerCountry;
    }

    public String getSellerPincode() {
        return sellerPincode;
    }

    public void setSellerPincode(String sellerPincode) {
        this.sellerPincode = sellerPincode;
    }

    public boolean isBookSold() {
        return isBookSold;
    }

    public void setBookSold(boolean bookSold) {
        isBookSold = bookSold;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate() {
        this.publishingDate = DateAndTimeMethods.getCurrentDate();
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate() {
        this.sellDate = DateAndTimeMethods.getCurrentDate();
    }

    public void setSellerInformation(String sellerName,
                                     String sellerCity,
                                     String sellerState,
                                     String sellerCountry,
                                     String sellerPincode) {
        this.sellerName = sellerName;
        this.sellerCity = sellerCity;
        this.sellerState = sellerState;
        this.sellerCountry = sellerCountry;
        this.sellerPincode = sellerPincode;
    }

    @Override
    public String toString() {
        return "SellBookAd{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPublisher='" + bookPublisher + '\'' +
                ", bookClass='" + bookClass + '\'' +
                ", bookStream='" + bookStream + '\'' +
                ", additionalBookInfo='" + additionalBookInfo + '\'' +
                ", bookQuality='" + bookQuality + '\'' +
                ", donateBoook=" + donateBoook +
                ", originalPrice=" + originalPrice +
                ", sellingPrice=" + sellingPrice +
                ", bookPhoto='" + bookPhoto + '\'' +
                ", isBookSold=" + isBookSold +
                ", sellerName='" + sellerName + '\'' +
                ", sellerCity='" + sellerCity + '\'' +
                ", sellerState='" + sellerState + '\'' +
                ", sellerCountry='" + sellerCountry + '\'' +
                ", sellerPincode='" + sellerPincode + '\'' +
                '}';
    }
}
