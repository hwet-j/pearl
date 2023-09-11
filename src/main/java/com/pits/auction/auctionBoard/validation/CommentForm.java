package com.pits.auction.auctionBoard.validation;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    @NotEmpty(message = "댓글 내용은 필수입니다.")
    private String comment;
}
