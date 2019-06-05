package com.example.laptop.khadamat.entities;

public class Review {

    private String idReview;
    private String comment;
    private int starsNumber;
    private String publicationDate;
    private String idUser;
    private String idService;

    public String getIdReview() {
        return idReview;
    }

    public String getComment() {
        return comment;
    }

    public int getStarsNumber() {
        return starsNumber;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdReview(String idReview) {
        this.idReview = idReview;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStarsNumber(int starsNumber) {
        this.starsNumber = starsNumber;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }
}
