package com.dev.Traveler.models;

public class Comment {
    private String comment;
    private String publisher;
    private String commentid;
   private String notificationid;


    public Comment(String comment, String publisher, String commentid, String notificationid) {
        this.comment = comment;
        this.publisher = publisher;
        this.commentid = commentid;
        this.notificationid = notificationid;
    }

    public Comment() {
    }
    public String getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(String notificationid) {
        this.notificationid = notificationid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }
}